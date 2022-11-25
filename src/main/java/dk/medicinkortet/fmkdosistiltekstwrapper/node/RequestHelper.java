package dk.medicinkortet.fmkdosistiltekstwrapper.node;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestHelper {
    private static final Logger logger = LogManager.getLogger(RequestHelper.class);

    public static String post(String endpoint, String inputJson, String methodName) {
        var request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .build();

        var client = HttpClient.newHttpClient();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("POST " + endpoint);
            return response.body();
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("IOException in DosisTilTekstWrapperNode." + methodName +"()", e);
        } catch (InterruptedException e) {
            logger.error("", e);
            throw new RuntimeException("InterruptedException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

}
