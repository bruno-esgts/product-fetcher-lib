package pt.brunojesus.productfetcherlib.parser.continente.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representation of a product brand from Continente's store
 *
 * @author Bruno Jesus
 * @since 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinenteBrand {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("name")
    private String name;
}
