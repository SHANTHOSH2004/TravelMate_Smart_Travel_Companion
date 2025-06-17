package com.example.smarttravelcompanion.utils;

import com.example.smarttravelcompanion.BuildConfig;

public class Config {
    // API Keys - Replace these with your actual API keys
    public static final String OPENWEATHER_API_KEY = "YOUR_OPENWEATHER_API_KEY";
    
    // OpenAI Configuration
    public static final String OPENAI_API_KEY = BuildConfig.OPENAI_API_KEY; // Get from BuildConfig
    public static final String OPENAI_MODEL = "gpt-3.5-turbo";
    public static final int OPENAI_MAX_TOKENS = 1000;
    public static final double OPENAI_TEMPERATURE = 0.7;
    
    // Weather API Configuration
    public static final String WEATHER_UNITS = "metric";
    public static final String WEATHER_LANG = "en";
    
    // Location Configuration
    public static final long LOCATION_UPDATE_INTERVAL = 10000; // 10 seconds
    public static final long LOCATION_FASTEST_INTERVAL = 5000; // 5 seconds
} 