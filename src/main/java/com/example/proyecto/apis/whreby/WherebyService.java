package com.example.proyecto.apis.whreby;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@Component
public class WherebyService {

    @Value("${WHEREBY_API_KEY}")
    private String wherebyApiKey;

    final private HttpClient httpClient = HttpClient.newHttpClient();


    public String createWherebyMeeting(LocalDateTime endmet) throws IOException, InterruptedException {

        LocalDateTime e = endmet.plusHours(3);
        String formattedDate = e.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z";

        var data = Map.of("endDate", formattedDate, "fields", Collections.singletonList("hostRoomUrl"));
        var request = HttpRequest.newBuilder(URI.create("https://api.whereby.dev/v1/meetings"))
                .header("Authorization", "Bearer " + wherebyApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(data)))
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            var responseBody = new ObjectMapper().readTree(response.body());
            return responseBody.get("hostRoomUrl").asText();
        } else {
            throw new RuntimeException("Error creating meeting" + response.statusCode());
        }
    }

}