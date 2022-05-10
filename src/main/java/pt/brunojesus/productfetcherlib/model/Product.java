package pt.brunojesus.productfetcherlib.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Product representation returned by the {@link pt.brunojesus.productfetcherlib.parser.Parser}
 *
 * @author Bruno Jesus
 * @since 1.0
 */
@Data
@Builder
public class Product {
    private String name;
    private String brand;
    private String image;
    private String url;
    private List<String> ean;
    private String sku;
    private Double currentPrice;
    private String currency;
    private String store;
    private boolean available;
}
