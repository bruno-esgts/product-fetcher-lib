package pt.brunojesus.productfetcherlib.parser.continente.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinenteBrand {

    @JsonProperty("@type")
    private String type;

    @JsonProperty("name")
    private String name;
}
