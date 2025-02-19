package app.VilaExplorer.controller;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
        private final HttpClient httpClient;

        public ApiClient(){
            this.httpClient = HttpClient.newHttpClient();
        }

    public HttpResponse<String> getRequest(String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .GET()
                .header("Content-Type", "application/json")
                .build();

         return  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

        public HttpResponse<String> getRequest(String endpoint, String authorization) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .build();

         return  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> postRequest(String endpoint, String jsonBody, String authorization) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .build();

         return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
