package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpStatusImageDownloader {

    private static final String DOWNLOAD_FOLDER = "downloaded_images";
    static {
        try {
            Files.createDirectories(Path.of(DOWNLOAD_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create download folder: " + DOWNLOAD_FOLDER, e);
        }
    }

    public static void downloadStatusImage(int code) throws IOException, URISyntaxException, InterruptedException {
        String imageUrl;
        try {
            imageUrl = HttpStatusChecker.getStatusImage(code);
        } catch (RuntimeException e) {
            throw new RuntimeException("Image not found for status code " + code);
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new java.net.URI(imageUrl))
                .GET()
                .build();

        HttpResponse<Path> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofFile(getImagePath(code)));

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to download image. HTTP status code: " + response.statusCode());
        }
    }

    private static Path getImagePath(int code) {
        return Path.of(DOWNLOAD_FOLDER, getFileName(code));
    }

    private static String getFileName(int code) {
        return "status_image_" + code + ".jpg";
    }
}
