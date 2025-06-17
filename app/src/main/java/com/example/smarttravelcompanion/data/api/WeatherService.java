package com.example.smarttravelcompanion.data.api;

import android.content.Context;
import com.example.smarttravelcompanion.utils.Config;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

public class WeatherService {
    private final OpenWeatherMapHelper weatherHelper;
    private static WeatherService instance;

    private WeatherService() {
        weatherHelper = new OpenWeatherMapHelper(Config.OPENWEATHER_API_KEY);
        weatherHelper.setUnits(Units.METRIC);
    }

    public static synchronized WeatherService getInstance() {
        if (instance == null) {
            instance = new WeatherService();
        }
        return instance;
    }

    public interface WeatherCallback {
        void onSuccess(String weatherInfo);
        void onError(String error);
    }

    public void getCurrentWeather(double latitude, double longitude, WeatherCallback callback) {
        weatherHelper.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                String weatherInfo = String.format("Temperature: %.1f°C\n" +
                        "Weather: %s\n" +
                        "Humidity: %d%%\n" +
                        "Wind: %.1f m/s",
                        currentWeather.getMain().getTemp(),
                        currentWeather.getWeather().get(0).getDescription(),
                        currentWeather.getMain().getHumidity(),
                        currentWeather.getWind().getSpeed());
                callback.onSuccess(weatherInfo);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onError("Failed to get weather data: " + throwable.getMessage());
            }
        });
    }

    public void getWeatherForCity(String city, WeatherCallback callback) {
        weatherHelper.getCurrentWeatherByCityName(city, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                String weatherInfo = String.format("Temperature: %.1f°C\n" +
                        "Weather: %s\n" +
                        "Humidity: %d%%\n" +
                        "Wind: %.1f m/s",
                        currentWeather.getMain().getTemp(),
                        currentWeather.getWeather().get(0).getDescription(),
                        currentWeather.getMain().getHumidity(),
                        currentWeather.getWind().getSpeed());
                callback.onSuccess(weatherInfo);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onError("Failed to get weather data: " + throwable.getMessage());
            }
        });
    }
} 