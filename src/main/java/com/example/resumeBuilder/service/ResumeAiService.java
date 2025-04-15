package com.example.resumeBuilder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class ResumeAiService {

    private final OpenAiService openAiService;
    private final RedisTemplate<String, String> redisTemplate;

    public ResumeAiService(OpenAiService openAiService, RedisTemplate<String, String> redisTemplate) {
        this.openAiService = openAiService;
        this.redisTemplate = redisTemplate;
    }

    public String generateSummary(String userInput) {
        // Creates an MD5 hash of the user input
        // This hash becomes the key for Redis caching.
        String hashKey = DigestUtils.md5DigestAsHex(userInput.getBytes());

        // Fetch a previously saved summary
        String cached = redisTemplate.opsForValue().get(hashKey);

        if(cached != null) return cached;

        // Create a new prompt and generate GPT-4 API response
        String prompt = "Generate a less than 255 words professional resume summary for: "+userInput;
        String rawResponse = openAiService.getGptResponse(prompt);

        String summary = "ERROR: Unable to parse response";

        try {
             ObjectMapper mapper = new ObjectMapper();
             JsonNode root = mapper.readTree(rawResponse);
             summary = root.path("choices").get(0).path("message").path("content").asText();

             if(summary.length() >255)
                 summary = summary.substring(1,255);   // starting is just space
         } catch (Exception e){
            System.err.println("Failed to parse response: " + e.getMessage());
        }

        // Store the new response in Redis
        redisTemplate.opsForValue().set(hashKey, summary, 6, TimeUnit.HOURS);
        return summary;
    }
}

// Raw Response:
// {"id":"","provider":"DeepInfra","model":"mistralai/mistral-7b-instruct","object":"chat.completion",
// "created":1744721937,
// "choices":[{"logprobs":null,"finish_reason":"stop","native_finish_reason":"stop","index":0,
// "message":{"role":"assistant",
// "content":" Experienced Data Scientist specializing in Natural Language Processing (NLP) with proficiency in Python,
// TensorFlow, and Scikit-learn. Java programming skills also applied. Demonstrated success in building and implementing ML
// models for NLP applications, driving efficiency and innovation.","refusal":null,"reasoning":null}}],
// "usage":{"prompt_tokens":48,"completion_tokens":60,"total_tokens":108}}
