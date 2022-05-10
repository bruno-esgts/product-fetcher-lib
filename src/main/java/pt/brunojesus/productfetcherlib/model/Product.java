package pt.brunojesus.productfetcherlib.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

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
