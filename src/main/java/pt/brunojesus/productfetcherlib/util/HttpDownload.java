package pt.brunojesus.productfetcherlib.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;
import java.util.logging.Logger;

public class HttpDownload implements Function<URL, byte[]> {

    private static final Logger LOGGER = Logger.getLogger(HttpDownload.class.getName());
    private static final String USER_AGENT = "Mozilla/5.0 ProductFetcher";

    @Override
    public byte[] apply(URL url) {
        byte[] content = null;

        InputStream is = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", USER_AGENT);
            
            conn.connect();

            is = conn.getInputStream();
            content = IOUtils.toByteArray(is);
        } catch (IOException e) {
            LOGGER.warning(String.format("Cannot get %s: %s", url.toExternalForm(), e.getMessage()));
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }
}

