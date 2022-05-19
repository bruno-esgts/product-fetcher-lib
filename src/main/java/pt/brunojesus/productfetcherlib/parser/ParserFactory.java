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
     * @param parser the parser to be created
     * @return the new parser of the type parserClass
     * @see ContinenteParser
     */
    public Parser getParser(final ParserEnum parser) {
        switch (parser) {
            case Continente -> {
                return new ContinenteParser(xPathParser);
            }
            default -> {
                return null;
            }
        }
    }
}
