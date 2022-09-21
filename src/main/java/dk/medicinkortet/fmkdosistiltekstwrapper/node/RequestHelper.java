package dk.medicinkortet.fmkdosistiltekstwrapper.node;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestHelper {

    public static String post(String endpoint, String inputJson, String methodName) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("IOException in DosisTilTekstWrapperNode." + methodName +"()", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

}
