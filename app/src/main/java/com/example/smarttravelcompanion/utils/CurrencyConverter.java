package com.example.smarttravelcompanion.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private static final Map<String, Double> EXCHANGE_RATES = new HashMap<>();
    
    static {
        // Fixed exchange rates (for demonstration)
        EXCHANGE_RATES.put("EUR", 1.0);      // Euro (base currency)
        EXCHANGE_RATES.put("USD", 1.08);     // US Dollar
        EXCHANGE_RATES.put("GBP", 0.86);     // British Pound
        EXCHANGE_RATES.put("JPY", 161.0);    // Japanese Yen
        EXCHANGE_RATES.put("INR", 90.0);     // Indian Rupee
    }

    public static double convert(double amount, String fromCurrency, String toCurrency) {
        if (!EXCHANGE_RATES.containsKey(fromCurrency) || !EXCHANGE_RATES.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Unsupported currency");
        }

        // Convert to EUR first (base currency)
        double amountInEUR = amount / EXCHANGE_RATES.get(fromCurrency);
        // Convert from EUR to target currency
        return amountInEUR * EXCHANGE_RATES.get(toCurrency);
    }

    public static String getSupportedCurrencies() {
        return String.join(", ", EXCHANGE_RATES.keySet());
    }

    public static boolean isCurrencySupported(String currency) {
        return EXCHANGE_RATES.containsKey(currency);
    }
} 