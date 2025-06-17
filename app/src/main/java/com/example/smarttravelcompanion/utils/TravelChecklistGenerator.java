package com.example.smarttravelcompanion.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelChecklistGenerator {
    private static final Map<String, List<String>> PRE_TRIP_CHECKLIST = new HashMap<>();
    private static final Map<String, List<String>> DURING_TRIP_CHECKLIST = new HashMap<>();
    private static final Map<String, List<String>> POST_TRIP_CHECKLIST = new HashMap<>();
    
    static {
        // Pre-trip checklist items
        List<String> preTripGeneral = new ArrayList<>();
        preTripGeneral.add("Book flights and accommodation");
        preTripGeneral.add("Check passport validity");
        preTripGeneral.add("Get travel insurance");
        preTripGeneral.add("Research destination");
        preTripGeneral.add("Book airport transfer");
        preTripGeneral.add("Notify bank of travel");
        preTripGeneral.add("Download offline maps");
        preTripGeneral.add("Check visa requirements");
        PRE_TRIP_CHECKLIST.put("general", preTripGeneral);

        // During-trip checklist items
        List<String> duringTripGeneral = new ArrayList<>();
        duringTripGeneral.add("Keep important documents safe");
        duringTripGeneral.add("Stay hydrated");
        duringTripGeneral.add("Take regular photos");
        duringTripGeneral.add("Keep emergency contacts handy");
        duringTripGeneral.add("Monitor local news");
        duringTripGeneral.add("Keep receipts for expenses");
        duringTripGeneral.add("Stay in touch with family");
        DURING_TRIP_CHECKLIST.put("general", duringTripGeneral);

        // Post-trip checklist items
        List<String> postTripGeneral = new ArrayList<>();
        postTripGeneral.add("Back up photos");
        postTripGeneral.add("Review expenses");
        postTripGeneral.add("Update travel journal");
        postTripGeneral.add("Share experiences");
        postTripGeneral.add("Check credit card statements");
        postTripGeneral.add("Plan next trip");
        POST_TRIP_CHECKLIST.put("general", postTripGeneral);
    }

    public static String generateChecklist(String phase, String destination) {
        StringBuilder result = new StringBuilder();
        
        switch (phase.toLowerCase()) {
            case "pre":
                result.append("📋 Pre-Trip Checklist:\n\n");
                addItems(result, PRE_TRIP_CHECKLIST.get("general"));
                addDestinationSpecificItems(result, destination, "pre");
                break;
                
            case "during":
                result.append("📋 During-Trip Checklist:\n\n");
                addItems(result, DURING_TRIP_CHECKLIST.get("general"));
                addDestinationSpecificItems(result, destination, "during");
                break;
                
            case "post":
                result.append("📋 Post-Trip Checklist:\n\n");
                addItems(result, POST_TRIP_CHECKLIST.get("general"));
                addDestinationSpecificItems(result, destination, "post");
                break;
                
            default:
                result.append("Please specify checklist phase: pre, during, or post");
        }
        
        return result.toString();
    }

    private static void addItems(StringBuilder result, List<String> items) {
        for (String item : items) {
            result.append("• ").append(item).append("\n");
        }
        result.append("\n");
    }

    private static void addDestinationSpecificItems(StringBuilder result, String destination, String phase) {
        destination = destination.toLowerCase();
        
        if (phase.equals("pre")) {
            switch (destination) {
                case "paris":
                    result.append("Paris-Specific Items:\n");
                    result.append("• Book museum tickets in advance\n");
                    result.append("• Learn basic French phrases\n");
                    result.append("• Get a Paris Museum Pass\n");
                    break;
                    
                case "tokyo":
                    result.append("Tokyo-Specific Items:\n");
                    result.append("• Get a Japan Rail Pass\n");
                    result.append("• Download Tokyo subway app\n");
                    result.append("• Learn basic Japanese phrases\n");
                    break;
                    
                case "rome":
                    result.append("Rome-Specific Items:\n");
                    result.append("• Book Vatican tickets\n");
                    result.append("• Get a Roma Pass\n");
                    result.append("• Learn basic Italian phrases\n");
                    break;
            }
        } else if (phase.equals("during")) {
            switch (destination) {
                case "paris":
                    result.append("Paris-Specific Tips:\n");
                    result.append("• Visit museums on first Sunday\n");
                    result.append("• Use public transportation\n");
                    result.append("• Try local bistros\n");
                    break;
                    
                case "tokyo":
                    result.append("Tokyo-Specific Tips:\n");
                    result.append("• Use IC cards for transport\n");
                    result.append("• Visit convenience stores\n");
                    result.append("• Try local ramen shops\n");
                    break;
                    
                case "rome":
                    result.append("Rome-Specific Tips:\n");
                    result.append("• Visit churches early morning\n");
                    result.append("• Use public transport passes\n");
                    result.append("• Try local trattorias\n");
                    break;
            }
        }
    }
} 