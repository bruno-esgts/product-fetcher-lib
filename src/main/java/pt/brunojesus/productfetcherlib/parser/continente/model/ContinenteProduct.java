package pt.brunojesus.productfetcherlib.parser.continente.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Representation of a product from Continente's store
 *
 * @author Bruno Jesus
 * @since 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinenteProduct {

    @JsonProperty("@context")
    private String context;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("mpn")
    private String mpn;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("brand")
    private ContinenteBrand brand;

    @JsonProperty("image")
    private List<String> image;

    @JsonProperty("offers")
    private ContinenteOffers offers;
}
