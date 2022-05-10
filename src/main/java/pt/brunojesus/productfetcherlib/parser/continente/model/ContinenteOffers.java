package pt.brunojesus.productfetcherlib.parser.continente.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representation of a product offer from Continente's store
 *
 * @author Bruno Jesus
 * @since 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinenteOffers {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("priceCurrency")
    private String priceCurrency;

    @JsonProperty("price")
    private String price;

    @JsonProperty("priceValidUntil")
    private String priceValidUntil;

    @JsonProperty("availability")
    private String availability;
}
