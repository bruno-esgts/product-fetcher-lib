package pt.brunojesus.productfetcherlib;

import pt.brunojesus.productfetcherlib.model.Product;
import pt.brunojesus.productfetcherlib.parser.Parser;
import pt.brunojesus.productfetcherlib.parser.ParserEnum;
import pt.brunojesus.productfetcherlib.parser.ParserFactory;

import java.util.Optional;

/**
 * Library's facade, exposes all important features of the library.
 *
 * @author Bruno Jesus
 * @version 1.1
 * @since 1.0
 */
public class ProductFetcherAPI {

    private final ParserFactory parserFactory;

    public ProductFetcherAPI() {
        this.parserFactory = new ParserFactory();
    }

    /**
     * Fetches the product from the given product url
     *
     * @param productUrl  url for the product page on the website
     * @param parserEnum the parser to use
     * @return the {@link Optional} containing the {@link Product}, empty if no product
     *
     * @see pt.brunojesus.productfetcherlib.parser.continente.ContinenteParser
     */
    public Optional<Product> fetchProduct(String productUrl, ParserEnum parserEnum) {
        final Parser parser = parserFactory.getParser(parserEnum);

        if (parser == null) {
            return Optional.empty();
        }

        return parser.apply(productUrl);
    }
}
