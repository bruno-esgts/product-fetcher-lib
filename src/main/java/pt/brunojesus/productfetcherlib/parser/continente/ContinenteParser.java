package pt.brunojesus.productfetcherlib.parser.continente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import pt.brunojesus.productfetcherlib.model.Product;
import pt.brunojesus.productfetcherlib.parser.Parser;
import pt.brunojesus.productfetcherlib.parser.continente.model.ContinenteProduct;
import pt.brunojesus.productfetcherlib.util.HttpDownload;
import pt.brunojesus.productfetcherlib.util.XPathParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContinenteParser implements Parser {

    private static final String XPATH_PRODUCT_JSON = "//script[@type=\"application/ld+json\"]";
    private static final String XPATH_DATA_URL = "//a[@href=\"#collapsible-description-nutritional\"]/@data-url";

    private final XPathParser xPathParser;
    private final ObjectMapper objectMapper;

    public ContinenteParser(XPathParser xPathParser) {
        this.xPathParser = xPathParser;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<Product> apply(String productUrl) {
        String rawLdJson = getRawJson(productUrl);

        if (rawLdJson == null) {
            return Optional.empty();
        }

        final String[] split = rawLdJson.split("\\n");
        if (split.length > 1) {
            rawLdJson = split[1];
        }

        ContinenteProduct originalProduct = null;
        try {
            originalProduct = objectMapper.readValue(rawLdJson, ContinenteProduct.class);
        } catch (JsonProcessingException e) {
            System.err.printf("Mapping failed for url: %s with content: %s%n", productUrl, rawLdJson);
            e.printStackTrace();

            return Optional.empty();
        }

        if (originalProduct == null || originalProduct.getOffers() == null ||
                StringUtils.isBlank(originalProduct.getOffers().getPrice())) {
            return Optional.empty();
        }

        Double price = parse(originalProduct.getOffers().getPrice());

        if (StringUtils.isNotBlank(originalProduct.getName()) && price != null) {
            final String ean = ean();

            return Optional.of(Product.builder()
                    .name(originalProduct.getName())
                    .brand(originalProduct.getBrand() != null ? originalProduct.getBrand().getName() : null)
                    .image(ObjectUtils.isNotEmpty(originalProduct.getImage()) ? originalProduct.getImage().get(0) : null)
                    .url(productUrl)
                    .ean(ean == null ? null : List.of(ean))
                    .sku(originalProduct.getSku())
                    .currentPrice(price)
                    .currency(originalProduct.getOffers().getPriceCurrency())
                    .available(StringUtils.equals(originalProduct.getOffers().getAvailability(), "http://schema.org/InStock"))
                    .store("Continente")
                    .build());
        }

        return Optional.empty();
    }

    private Double parse(String value) {

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.printf("Cannot parse double %s%n", value);
            e.printStackTrace();
        }

        return null;
    }

    private String getRawJson(String url) {
        try {
            XPathParser parser = xPathParser.fromURL(url);
            String rawLdJson = parser.parse(XPATH_PRODUCT_JSON);

            if (rawLdJson == null) {
                System.err.printf("No raw json for %s%n", url);
                return null;
            }

            final String[] split = rawLdJson.split("\\n");
            if (split.length > 1) {
                return split[1];
            }

            return rawLdJson;
        } catch (Exception e) {
            System.err.printf("Error getting raw json for %s%n", url);
            e.printStackTrace();
        }

        return null;
    }

    private String ean() {
        final String fullUrl = xPathParser.parse(XPATH_DATA_URL);

        if (ObjectUtils.isEmpty(fullUrl)) {
            return null;
        }

        final Map<String, String> queryMap = getQueryMap(fullUrl.split("\\?")[1]);

        if (ObjectUtils.isEmpty(queryMap) || !queryMap.containsKey("ean")) {
            return null;
        }

        return queryMap.get("ean");
    }

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();

        for (String param : params) {
            final String[] splitParam = param.split("=");
            String name = splitParam[0];
            String value = splitParam.length > 1 ? splitParam[1] : "";
            map.put(name, value);
        }
        return map;
    }
}
