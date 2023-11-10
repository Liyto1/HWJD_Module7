package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HttpImageStatusCli {
    public static void askStatus() throws IOException, URISyntaxException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter HTTP status code: ");
            int statusCode = scanner.nextInt();

            HttpStatusImageDownloader.downloadStatusImage(statusCode);
            System.out.println("Image downloaded successfully. Check the 'downloaded_images' folder.");
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
