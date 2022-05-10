package pt.brunojesus.productfetcherlib.util;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class XPathParser {

    private Document document;
    private final Function<URL, byte[]> download;

    public XPathParser(Function<URL, byte[]> download) {
        this.download = download;
    }

    public XPathParser fromURL(String url) {
        this.document = retrieveHTML(url, download);
        return this;
    }

    public XPathParser fromHTML(String html) {
        this.document = createDocumentFromHtml(html);
        return this;
    }

    /**
     * Parses the HTML contents using the XPath expression.
     *
     * @param xpathExpression the xpath expression
     * @return the string
     */
    public String parse(String xpathExpression) {
        return StringUtils.join(parseAsList(xpathExpression), "\r\n");
    }

    /**
     * Parses the document using the XPath expression.
     * <p>
     * This parser supports custom XPath expressions not implemented by xsoup:
     *
     * <pre>
     * - price(99,9) : removes all the junk from its content and replaces the commas with dots
     * - string(MyString) : a way to use strings without getting xsoup errors
     * - concat(string(MyString), xpathXpression) : concatenate two expressions
     * </pre>
     * <p>
     * This function calls itself recursively until there's no more known
     * expressions
     *
     * @param xpathExpression the XPath expression
     * @return the string list
     */
    public List<String> parseAsList(String xpathExpression) {
        List<String> ret = new ArrayList<>();
        if (xpathExpression.startsWith("price(")) {
            String processingXpathExpression = xpathExpression.substring(6,
                    findClosingParenthesis(xpathExpression.toCharArray(), 5));
            List<String> expResult = parseAsList(processingXpathExpression);

            for (String elem : expResult) {
                // we only want numbers, commas and dots
                elem = elem.replace(",", ".").replaceAll("[^0-9.]", ""); // only allow numbers and dots

                // remove all dots except the last one, to prevent this: 1.000.32
                int lastDot = elem.lastIndexOf(".");
                int firstDot = elem.indexOf(".");
                if (lastDot > -1 && lastDot != firstDot) {
                    String firstPart = elem.substring(0, elem.lastIndexOf("."));
                    String lastPart = elem.substring(firstPart.lastIndexOf("."));
                    elem = elem.replace(".", "") + lastPart;
                }

                ret.add(elem);
            }

        } else if (xpathExpression.startsWith("concat(")) {
            String processingXpathExpression = xpathExpression.substring(7,
                    findClosingParenthesis(xpathExpression.toCharArray(), 6));

            String exp1 = processingXpathExpression.substring(0, processingXpathExpression.indexOf(','));
            String exp2 = processingXpathExpression.substring(processingXpathExpression.indexOf(',') + 1);

            String resultP1 = parseAsList(exp1).get(0);
            String resultP2 = parseAsList(exp2).get(0);

            ret.add(resultP1 + resultP2);

        } else if (xpathExpression.startsWith("string(")) {
            String processingXpathExpression = xpathExpression.substring(7,
                    findClosingParenthesis(xpathExpression.toCharArray(), 6));
            ret.add(processingXpathExpression);

        } else if (xpathExpression.startsWith("replace(")) {
            String processingXpathExpression = xpathExpression.substring(8,
                    findClosingParenthesis(xpathExpression.toCharArray(), 7));

            // start from the end to the beginning because the first may be an expression
            // with commas
            int commaPos = processingXpathExpression.lastIndexOf(',');
            String replaceable = processingXpathExpression.substring(commaPos + 1);
            processingXpathExpression = processingXpathExpression.substring(0, commaPos);

            commaPos = processingXpathExpression.lastIndexOf(',');
            String needle = processingXpathExpression.substring(commaPos + 1);

            List<String> haystacks = parseAsList(processingXpathExpression.substring(0, commaPos));

            for (String haystack : haystacks) {
                ret.add(haystack.replace(needle, replaceable));
            }

        } else if (xpathExpression.startsWith("substring-before(")) {
            String processingXpathExpression = xpathExpression.substring(17,
                    findClosingParenthesis(xpathExpression.toCharArray(), 16));

            String exp1 = processingXpathExpression.substring(0, processingXpathExpression.indexOf(','));
            String exp2 = processingXpathExpression.substring(processingXpathExpression.indexOf(',') + 1);

            String resultP1 = parseAsList(exp1).get(0);

            int exp2pos = resultP1.indexOf(exp2);

            String result = exp2pos > -1 ? resultP1.substring(0, exp2pos) : resultP1;
            ret.add(result);
        } else {
            ret.addAll(xSoupParse(xpathExpression));
        }

        return ret;
    }

    /**
     * XSoup parse.
     *
     * @param xpathExpression the xpath expression
     * @return the string
     */
    private List<String> xSoupParse(String xpathExpression) {
        return Xsoup.compile(xpathExpression).evaluate(document).list();
    }

    /**
     * Find closing parenthesis.
     *
     * @param text    the text
     * @param openPos the open parenthesis position
     * @return the matching parenthesis position
     */
    private int findClosingParenthesis(char[] text, int openPos) {
        int closePos = openPos;
        int counter = 1;
        while (counter > 0) {
            char c = text[++closePos];
            if (c == '(') {
                counter++;
            } else if (c == ')') {
                counter--;
            }
        }
        return closePos;
    }

    /**
     * Creates the document from html.
     *
     * @param html the html
     * @return the document
     */
    private Document createDocumentFromHtml(String html) {
        return Jsoup.parse(html);
    }

    /**
     * Retrieve HTML.
     *
     * @param url   the url
     * @return the document
     */
    private Document retrieveHTML(String url, Function<URL, byte[]> downloadFunction) {
        Document document = null;
        try {
            byte[] download = downloadFunction.apply(new URL(url));
            if (download != null) {
                document = Jsoup.parse(new String(download));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


    /**
     * Checks if is page downloaded.
     *
     * @return true, if is page downloaded
     */
    public boolean isPageDownloaded() {
        return document != null;
    }
}
