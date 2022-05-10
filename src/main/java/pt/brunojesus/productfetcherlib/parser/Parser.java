package pt.brunojesus.productfetcherlib.parser;

import pt.brunojesus.productfetcherlib.model.Product;

import java.util.Optional;
import java.util.function.Function;

/**
 * Blueprint for a Parser class
 *
 * @author Bruno Jesus
 * @since 1.0
 */
public interface Parser extends Function<String, Optional<Product>> {

    /**
     * Fetch the product from the given productUrl
     *
     * @param productUrl the url for the product page
     * @return the {@link Optional} containing the {@link Product}, empty if no product
     */
    @Override
    Optional<Product> apply(String productUrl);
}
