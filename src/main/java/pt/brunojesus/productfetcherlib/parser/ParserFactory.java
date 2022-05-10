package pt.brunojesus.productfetcherlib.parser;

import pt.brunojesus.productfetcherlib.parser.continente.ContinenteParser;
import pt.brunojesus.productfetcherlib.util.HttpDownload;
import pt.brunojesus.productfetcherlib.util.XPathParser;

public class ParserFactory {

    private final XPathParser xPathParser;

    public ParserFactory() {
        this.xPathParser = new XPathParser(
                new HttpDownload()
        );
    }

    public Parser getParser(Class<Parser> parserClass) {
        if (parserClass.equals(ContinenteParser.class)) {
            return new ContinenteParser(xPathParser);
        }

        return null;
    }
}
