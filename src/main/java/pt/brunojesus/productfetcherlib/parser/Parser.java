package pt.brunojesus.productfetcherlib.parser;

import pt.brunojesus.productfetcherlib.model.Product;

import java.util.Optional;
import java.util.function.Function;

public interface Parser extends Function<String, Optional<Product>> {

    @Override
    Optional<Product> apply(String productUrl);
}
