# Product Fetcher Lib

Library for fetching product information from store
websites.

## Supported stores
 - Continente (https://continente.pt)
 - More to come, feel free to contribute with extra stores

## Example Usage

```java

package pt.brunojesus.productfetcherlib;

import org.apache.commons.lang3.StringUtils;
import pt.brunojesus.productfetcherlib.parser.ParserEnum;

public class Main {

    public static void main(String[] args) {
        final ProductFetcherAPI productFetcherAPI = new ProductFetcherAPI();

        productFetcherAPI.fetchProduct(
                "https://www.continente.pt/produto/pasta-de-dentes-protecao-total-original-colgate-6456840.html",
                ParserEnum.Continente
        ).ifPresentOrElse(product -> {
                    System.out.println("-------- Product -----------");
                    System.out.println("Brand: " + product.getBrand());
                    System.out.println("Name:  " + product.getName());
                    System.out.println("Price: " + product.getCurrentPrice() + " " + product.getCurrency());
                    System.out.println("EAN:   " + StringUtils.join(product.getEan(), ","));
                    System.out.println("SKU:   " + product.getSku());
                    System.out.println("Store: " + product.getStore());
                    System.out.println("Image: " + product.getImage());
                    System.out.println("URL:   " + product.getUrl());
                    System.out.println("---------------------------");
                },
                () -> {
                    System.err.println("Cannot fetch product");
                }
        );

    }
}
```

**Outputs:**
```text
-------- Product -----------
Brand: Colgate
Name:  Pasta de Dentes Proteção Total Original
Price: 4.98 EUR
EAN:   8718951465817
SKU:   6456840
Store: Continente
Image: https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw2f226a08/images/col/645/6456840-hero.jpg?sw=280&sh=280
URL:   https://www.continente.pt/produto/pasta-de-dentes-protecao-total-original-colgate-6456840.html
---------------------------
```

## Documentation
You can find the updated JavaDoc on the **Releases** page.


## How to import this library

### Using Maven
Add this dependency to your `pom.xml` file

```xml
<dependency>
  <groupId>pt.brunojesus</groupId>
  <artifactId>productfetcher-lib</artifactId>
  <version>1.1</version>
</dependency>
```

Since this library is not on Maven Central you will also need to add the following maven repository:
```xml
<repository>
  <id>mvn-brunojesus</id>
  <name>Bruno Jesus MVN Repo</name>
  <url>https://mvn.brunojesus.pt/releases</url>
</repository>
```
