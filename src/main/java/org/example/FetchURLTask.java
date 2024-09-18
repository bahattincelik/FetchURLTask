package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchURLTask implements Runnable {
    private  String url;
    public FetchURLTask(String url) {
        this.url = url;
    }
    @Override
    public void run() {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            String first10Chars= responseBody.length() > 10 ? responseBody.substring(0,10) : responseBody;

            System.out.println("Response from " + url + ": " + first10Chars);
        } catch (Exception e) {
            System.err.println("Error fetching URL: " + e.getMessage());
        }







    }
}
