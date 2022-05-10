package pt.brunojesus.productfetcherlib;

import pt.brunojesus.productfetcherlib.model.Product;
import pt.brunojesus.productfetcherlib.parser.Parser;
import pt.brunojesus.productfetcherlib.parser.ParserFactory;

import java.util.Optional;

public class ProductFetcher {

    private final ParserFactory parserFactory;

    public ProductFetcher() {
        this.parserFactory = new ParserFactory();
    }

    public Optional<Product> fetchProduct(String productUrl, Class<Parser> parserClass) {
        final Parser parser = parserFactory.getParser(parserClass);
        return parser.apply(productUrl);
    }
}
