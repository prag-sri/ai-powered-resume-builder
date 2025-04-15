package com.example.resumeBuilder.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    public String getGptResponse(String prompt){
        // simple client from Spring for making REST calls
        RestTemplate restTemplate = new RestTemplate();

        // set up HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // Content type: JSON
        headers.setBearerAuth(apiKey);   // Auth: Bearer token (your API key)

        // create the request body
        Map<String, Object> body = new HashMap<>();
        body.put("model", "mistralai/mistral-7b-instruct");
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        // {
        //  "role": "user",
        //  "content": "your prompt here"
        // }

        // wraps the headers and body together into one HTTP entity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Sends a POST request to the OpenAI URL, with the request, expecting a response as a plain string
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        return response.getBody();
    }
}
