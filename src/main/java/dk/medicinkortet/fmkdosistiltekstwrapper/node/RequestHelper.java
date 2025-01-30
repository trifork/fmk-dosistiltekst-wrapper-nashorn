package dk.medicinkortet.fmkdosistiltekstwrapper.node;

import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.DTOHelper;
import dk.medicinkortet.fmkdosistiltekstwrapper.node.dto.responseobjects.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestHelper {
    private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);
    private final HttpClient client;

    public RequestHelper(HttpClient client) {
        this.client = client;
    }

    public String post(String endpoint, String inputJson, String methodName) {
        var request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("POST " + endpoint);

            checkForErrorResponse(response, methodName, inputJson);

            return response.body();
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("IOException in DosisTilTekstWrapperNode." + methodName + "()", e);
        } catch (InterruptedException e) {
            logger.error("", e);
            throw new RuntimeException("InterruptedException in DosisTilTekstWrapperNode." + methodName + "()", e);
        }
    }

    private void checkForErrorResponse(HttpResponse<String> response, String methodName, String inputJson) {
        if (response.statusCode() != 200) {
            var msg = getErrorMsg(response, methodName);
            var inputJsonSize = getStringByteSize(inputJson);

            logger.error("(Request body size: " + inputJsonSize + " bytes) Received a response with status code "
                    + response.statusCode() + " and message: " + msg);

            throw new RuntimeException("Received error code in DosisTilTekstWrapperNode." + methodName + "()");
        }
    }

    private String getErrorMsg(HttpResponse<String> response, String methodName) {
        try {
            var errorResponseDTO = DTOHelper.convertJsonToDTO(response.body(), ErrorResponseDTO.class, methodName);
            return errorResponseDTO.getMsg();
        } catch (Exception e) {
            return "";
        }
    }

    private int getStringByteSize(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException | NullPointerException e) {
            return 0;
        }
    }
}
