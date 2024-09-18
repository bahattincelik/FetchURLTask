package org.example;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {

        List<String> urls= Arrays.asList(
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://jsonplaceholder.typicode.com/posts/2",
                "https://jsonplaceholder.typicode.com/posts/3");

//------------------------------------------------------------------------------------------------------------------------------------------------------------------
        for (String url : urls) {
            FetchURLTask task = new FetchURLTask(url);
            Thread thread = new Thread(task);
            thread.start();
        }

        System.out.println("All threads started.");


//---------------------------------------------------------------------------------------------------------------------------------------------------------------





        HttpClient client = HttpClient.newHttpClient();

        urls.forEach(url-> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URL(url).toURI())
                            .build();

                    HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    String responseBody = response.body();
                    String first10Chars= responseBody.length() > 10 ? responseBody.substring(0,10) : responseBody;

                    System.out.println("Data from: " + url + " - First 10 chars: " + first10Chars);


                } catch (Exception e) {
                    System.err.println("Error fetching data from: " + url);
                    e.printStackTrace();
                }
             });
            });

        try {
            Thread.sleep(5000);

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.println("All requests completed.");

        }

    }

