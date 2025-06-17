package com.example.smarttravelcompanion.data.api;

import android.util.Log;
import com.example.smarttravelcompanion.utils.Config;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OpenAIService {
    private static final String TAG = "OpenAIService";
    private static OpenAIService instance;
    private final OpenAiService service;

    private OpenAIService() {
        try {
            Log.d(TAG, "Initializing OpenAI service with API key: " + 
                (Config.OPENAI_API_KEY != null ? Config.OPENAI_API_KEY.substring(0, 5) + "..." : "null"));
            this.service = new OpenAiService(Config.OPENAI_API_KEY, Duration.ofSeconds(30));
        } catch (Exception e) {
            Log.e(TAG, "Error initializing OpenAI service: " + e.getMessage());
            throw new RuntimeException("Failed to initialize OpenAI service: " + e.getMessage());
        }
    }

    public static synchronized OpenAIService getInstance() {
        if (instance == null) {
            instance = new OpenAIService();
        }
        return instance;
    }

    public interface AIResponseCallback {
        void onResponse(String response);
        void onError(String error);
    }

    public void getTravelAdvice(String query, AIResponseCallback callback) {
        if (query == null || query.trim().isEmpty()) {
            callback.onError("Please enter a valid question");
            return;
        }

        try {
            Log.d(TAG, "Sending query to OpenAI: " + query);
            
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage("system", "You are a knowledgeable travel advisor. Provide helpful, accurate, and concise travel advice."));
            messages.add(new ChatMessage("user", query));

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(Config.OPENAI_MODEL)
                    .messages(messages)
                    .temperature(Config.OPENAI_TEMPERATURE)
                    .maxTokens(Config.OPENAI_MAX_TOKENS)
                    .build();

            String response = service.createChatCompletion(request)
                    .getChoices().get(0).getMessage().getContent();
            
            Log.d(TAG, "Received response from OpenAI");
            callback.onResponse(response);
            
        } catch (Exception e) {
            Log.e(TAG, "Error getting travel advice: " + e.getMessage(), e);
            String errorMessage = "Error: " + e.getMessage();
            if (e.getMessage().contains("API key")) {
                errorMessage = "API key error. Please check your OpenAI API key configuration.";
            } else if (e.getMessage().contains("timeout")) {
                errorMessage = "Request timed out. Please check your internet connection and try again.";
            } else if (e.getMessage().contains("rate limit")) {
                errorMessage = "Rate limit exceeded. Please try again in a few moments.";
            }
            callback.onError(errorMessage);
        }
    }

    // ... rest of the methods remain unchanged ...
} 