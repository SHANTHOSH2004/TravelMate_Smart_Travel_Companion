package com.example.smarttravelcompanion.data.api;

import android.util.Log;
import com.example.smarttravelcompanion.utils.BudgetCalculator;
import com.example.smarttravelcompanion.utils.CurrencyConverter;
import com.example.smarttravelcompanion.utils.LocalPhraseTranslator;
import com.example.smarttravelcompanion.utils.PackingListGenerator;
import com.example.smarttravelcompanion.utils.TripCountdown;
import com.example.smarttravelcompanion.utils.TravelChecklistGenerator;
import com.example.smarttravelcompanion.utils.TransportationGuide;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LocalTravelService {
    private static final String TAG = "LocalTravelService";
    private static LocalTravelService instance;
    private final Map<String, String[]> responseMap;
    private final Random random;

    private LocalTravelService() {
        responseMap = new HashMap<>();
        random = new Random();
        initializeResponses();
    }

    private void initializeResponses() {
        // Paris responses
        responseMap.put("paris", new String[]{
            "Paris is known for the Eiffel Tower, Louvre Museum, and Notre-Dame Cathedral. The best time to visit is spring (April-June) or fall (September-October).\n\n" +
            "Currency: Euro (EUR)\n" +
            "Local time: CET (UTC+1)\n" +
            "Language: French",
            "Must-visit places in Paris include the Eiffel Tower, Louvre Museum, Notre-Dame Cathedral, Champs-Élysées, and Montmartre. Don't forget to try French cuisine!\n\n" +
            "Packing tip: Bring comfortable walking shoes and dress clothes for dining.",
            "Paris is beautiful year-round, but spring and fall offer the best weather. The city is famous for its art, fashion, and delicious pastries.\n\n" +
            "Local currency: Euro (EUR)\n" +
            "Average daily budget: €100-150"
        });

        // Tokyo responses
        responseMap.put("tokyo", new String[]{
            "Tokyo is a vibrant city with amazing food, technology, and culture. Visit during spring for cherry blossoms or fall for pleasant weather.\n\n" +
            "Currency: Japanese Yen (JPY)\n" +
            "Local time: JST (UTC+9)\n" +
            "Language: Japanese",
            "Top attractions in Tokyo include Shibuya Crossing, Tokyo Skytree, Senso-ji Temple, and Tsukiji Outer Market. Try the local sushi and ramen!\n\n" +
            "Packing tip: Bring an umbrella and comfortable walking shoes.",
            "Tokyo is best visited in spring (March-May) or fall (September-November). The city offers a perfect blend of traditional and modern culture.\n\n" +
            "Local currency: Japanese Yen (JPY)\n" +
            "Average daily budget: ¥10,000-15,000"
        });

        // Rome responses
        responseMap.put("rome", new String[]{
            "Rome is famous for the Colosseum, Vatican City, and delicious Italian cuisine. Spring and fall are the best times to visit.\n\n" +
            "Currency: Euro (EUR)\n" +
            "Local time: CET (UTC+1)\n" +
            "Language: Italian",
            "Must-see places in Rome include the Colosseum, Vatican Museums, Trevi Fountain, and Roman Forum. Don't forget to try authentic pizza and pasta!\n\n" +
            "Packing tip: Bring modest clothing for visiting churches.",
            "Rome is best visited in spring (April-June) or fall (September-October) to avoid the summer crowds and heat.\n\n" +
            "Local currency: Euro (EUR)\n" +
            "Average daily budget: €80-120"
        });

        // London responses
        responseMap.put("london", new String[]{
            "London offers iconic attractions like Big Ben, Buckingham Palace, and the British Museum. The weather is best in summer, but spring and fall are also good.\n\n" +
            "Currency: British Pound (GBP)\n" +
            "Local time: GMT/BST (UTC+0/+1)\n" +
            "Language: English",
            "Top places to visit in London include the Tower of London, British Museum, Westminster Abbey, and the London Eye. Try traditional fish and chips!\n\n" +
            "Packing tip: Bring an umbrella and layers for changing weather.",
            "London is great to visit year-round, but summer (June-August) offers the best weather. Spring and fall are less crowded and still pleasant.\n\n" +
            "Local currency: British Pound (GBP)\n" +
            "Average daily budget: £80-120"
        });

        // General travel responses
        responseMap.put("general", new String[]{
            "That's a great question! Could you specify which destination you're interested in? I can help with:\n" +
            "- Must-visit places\n" +
            "- Best time to visit\n" +
            "- Local currency and costs\n" +
            "- Packing tips\n" +
            "- Trip planning advice",
            "I'd be happy to help you plan your trip. Which country or city would you like to know more about?\n\n" +
            "I can provide information about:\n" +
            "- Popular attractions\n" +
            "- Local customs\n" +
            "- Currency conversion\n" +
            "- Packing lists\n" +
            "- Weather patterns",
            "To give you the best advice, could you tell me where you're planning to travel? I can help with:\n" +
            "- Trip countdown\n" +
            "- Packing recommendations\n" +
            "- Currency information\n" +
            "- Local tips\n" +
            "- Weather advice",
            "I can help you with travel tips, must-visit places, and local recommendations. Where would you like to go?\n\n" +
            "I can assist with:\n" +
            "- Destination information\n" +
            "- Trip planning\n" +
            "- Currency conversion\n" +
            "- Packing lists\n" +
            "- Local customs"
        });
    }

    public static synchronized LocalTravelService getInstance() {
        if (instance == null) {
            instance = new LocalTravelService();
        }
        return instance;
    }

    public interface TravelResponseCallback {
        void onResponse(String response);
        void onError(String error);
    }

    public void getTravelAdvice(String query, TravelResponseCallback callback) {
        if (query == null || query.trim().isEmpty()) {
            callback.onError("Please enter a valid question");
            return;
        }

        try {
            Log.d(TAG, "Processing query: " + query);
            String response = generateResponse(query.toLowerCase());
            callback.onResponse(response);
        } catch (Exception e) {
            Log.e(TAG, "Error generating response: " + e.getMessage());
            callback.onError("I'm having trouble understanding. Could you rephrase your question?");
        }
    }

    private String generateResponse(String query) {
        // Check for checklist-related queries
        if (query.contains("checklist") || query.contains("what to do before") || query.contains("what to do during")) {
            return handleChecklistQuery(query);
        }
        
        // Check for transportation-related queries
        if (query.contains("transport") || query.contains("metro") || query.contains("bus") || query.contains("taxi")) {
            return handleTransportationQuery(query);
        }
        
        // Check for budget-related queries
        if (query.contains("budget") || query.contains("cost") || query.contains("money")) {
            return handleBudgetQuery(query);
        }
        
        // Check for language/phrase queries
        if (query.contains("how to say") || query.contains("translate") || query.contains("phrase")) {
            return handleLanguageQuery(query);
        }
        
        // Check for specific destinations
        for (String destination : responseMap.keySet()) {
            if (query.contains(destination)) {
                String[] responses = responseMap.get(destination);
                return responses[random.nextInt(responses.length)];
            }
        }

        // Check for specific feature requests
        if (query.contains("packing") || query.contains("what to bring")) {
            return generatePackingListResponse(query);
        } else if (query.contains("currency") || query.contains("money") || query.contains("exchange")) {
            return "I can help with currency conversion! Supported currencies: " + CurrencyConverter.getSupportedCurrencies() + 
                   "\n\nTry asking: 'Convert 100 USD to EUR' or 'What's the currency in Paris?'";
        } else if (query.contains("countdown") || query.contains("days until")) {
            return "I can help you count down to your trip! Try asking: 'How many days until my trip to Paris?' or 'When is my trip to Tokyo?'";
        }

        // If no specific destination is mentioned, return a general response
        String[] generalResponses = responseMap.get("general");
        return generalResponses[random.nextInt(generalResponses.length)];
    }

    private String handleChecklistQuery(String query) {
        String destination = "";
        String phase = "pre";
        
        // Extract destination
        for (String dest : responseMap.keySet()) {
            if (query.contains(dest)) {
                destination = dest;
                break;
            }
        }
        
        if (destination.isEmpty()) {
            return "I can help you with travel checklists! Try asking: 'Show me pre-trip checklist for Paris' or 'What to do during trip to Tokyo?'";
        }
        
        // Determine checklist phase
        if (query.contains("during") || query.contains("while")) {
            phase = "during";
        } else if (query.contains("after") || query.contains("post")) {
            phase = "post";
        }
        
        return TravelChecklistGenerator.generateChecklist(phase, destination);
    }

    private String handleTransportationQuery(String query) {
        String destination = "";
        
        // Extract destination
        for (String dest : responseMap.keySet()) {
            if (query.contains(dest)) {
                destination = dest;
                break;
            }
        }
        
        if (destination.isEmpty()) {
            return "I can help you with transportation information! Try asking: 'How to get around in Paris?' or 'Taxi information for Tokyo?'";
        }
        
        // Check if asking about taxis
        if (query.contains("taxi") || query.contains("cab")) {
            return TransportationGuide.getTaxiInfo(destination);
        }
        
        // Return general transportation info
        return TransportationGuide.getTransportationInfo(destination);
    }

    private String handleBudgetQuery(String query) {
        // Extract destination and duration from query
        String destination = "";
        int days = 7; // default duration
        String style = "mid-range"; // default style
        
        for (String dest : responseMap.keySet()) {
            if (query.contains(dest)) {
                destination = dest;
                break;
            }
        }
        
        if (destination.isEmpty()) {
            return "I can help you plan your budget! Try asking: 'What's the budget for 7 days in Paris?' or 'How much money do I need for Tokyo?'";
        }
        
        // Extract duration if mentioned
        if (query.contains("day")) {
            String[] words = query.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                try {
                    if (words[i].matches("\\d+") && i + 1 < words.length && words[i + 1].contains("day")) {
                        days = Integer.parseInt(words[i]);
                        break;
                    }
                } catch (NumberFormatException e) {
                    // Ignore parsing errors
                }
            }
        }
        
        // Extract style if mentioned
        if (query.contains("budget") || query.contains("cheap")) {
            style = "budget";
        } else if (query.contains("luxury") || query.contains("expensive")) {
            style = "luxury";
        }
        
        String budgetInfo = BudgetCalculator.calculateBudget(destination, days, style);
        String budgetTips = BudgetCalculator.getBudgetTips(destination);
        
        return budgetInfo + "\n\n" + budgetTips;
    }

    private String handleLanguageQuery(String query) {
        // Extract language and phrase from query
        String language = "";
        String phrase = "";
        
        // Check for language
        if (query.contains("french")) {
            language = "french";
        } else if (query.contains("japanese")) {
            language = "japanese";
        } else if (query.contains("italian")) {
            language = "italian";
        } else if (query.contains("spanish")) {
            language = "spanish";
        }
        
        if (language.isEmpty()) {
            return "I can help you with common phrases in French, Japanese, Italian, and Spanish. Try asking: 'How do you say hello in French?' or 'Show me common Japanese phrases'";
        }
        
        // Check if asking for specific phrase
        if (query.contains("how to say") || query.contains("translate")) {
            String[] words = query.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals("say") && i + 1 < words.length) {
                    phrase = words[i + 1];
                    break;
                }
            }
            
            if (!phrase.isEmpty()) {
                return LocalPhraseTranslator.getPhrase(language, phrase);
            }
        }
        
        // If no specific phrase, return common phrases
        String phrases = LocalPhraseTranslator.getCommonPhrases(language);
        String tips = LocalPhraseTranslator.getLanguageTips(language);
        
        return phrases + "\n\n" + tips;
    }

    private String generatePackingListResponse(String query) {
        StringBuilder response = new StringBuilder("Here's a suggested packing list:\n\n");
        
        // Determine trip type
        boolean isBeach = query.contains("beach") || query.contains("coast") || query.contains("island");
        boolean isWinter = query.contains("winter") || query.contains("cold") || query.contains("snow");
        
        // Generate packing list
        List<String> items = PackingListGenerator.generatePackingList(
            query, 7, isBeach, isWinter);
        
        for (String item : items) {
            response.append("• ").append(item).append("\n");
        }
        
        return response.toString();
    }
} 