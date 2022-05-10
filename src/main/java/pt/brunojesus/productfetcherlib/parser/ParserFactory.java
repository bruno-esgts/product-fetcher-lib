package pt.brunojesus.productfetcherlib.parser;

import pt.brunojesus.productfetcherlib.parser.continente.ContinenteParser;
import pt.brunojesus.productfetcherlib.util.HttpDownload;
import pt.brunojesus.productfetcherlib.util.XPathParser;

/**
 * Creates new parser objects according to the specified parser class
 *
 * @author Bruno Jesus
 * @since 1.0
 */
public class ParserFactory {

    private final XPathParser xPathParser;

    public ParserFactory() {
        this.xPathParser = new XPathParser(
                new HttpDownload()
        );
    }

    /**
     * Creates a new parser according to the given parserClass
     *
     * @param parserClass the parser type to be created
     * @return the new parser of the type parserClass
     * @see ContinenteParser
     */
    public Parser getParser(Class<Parser> parserClass) {
        if (parserClass.equals(ContinenteParser.class)) {
            return new ContinenteParser(xPathParser);
        }

        return null;
    }
}
