package pt.brunojesus.productfetcherlib.parser.continente.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
