package com.example.proyecto.apis.tinyUrl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TinyUrlService {

    private final String TINYURL_API_URL = "https://api.tinyurl.com/create";

    public String shortenUrl(String longUrl) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = "https://tinyurl.com/api-create.php?url=" + longUrl;

        String shortenedUrl = restTemplate.getForObject(requestUrl, String.class);
        return shortenedUrl;
    }
}
