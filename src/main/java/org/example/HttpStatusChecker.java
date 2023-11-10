package org.example;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStatusChecker {

    public static String getStatusImage(int code) throws URISyntaxException, IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        String url = "https://http.cat/" + code + ".jpg";

        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {
            throw new RuntimeException("Image not found for status code " + code);
        }

        return url;
    }
}
