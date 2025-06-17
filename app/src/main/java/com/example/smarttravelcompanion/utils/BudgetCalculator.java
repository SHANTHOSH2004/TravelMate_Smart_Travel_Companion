package com.example.smarttravelcompanion.utils;

import java.util.HashMap;
import java.util.Map;

public class BudgetCalculator {
    private static final Map<String, DailyBudget> DESTINATION_BUDGETS = new HashMap<>();
    
    static {
        // Daily budget ranges in USD (budget, mid-range, luxury)
        DESTINATION_BUDGETS.put("paris", new DailyBudget(80, 150, 300));
        DESTINATION_BUDGETS.put("tokyo", new DailyBudget(70, 130, 250));
        DESTINATION_BUDGETS.put("rome", new DailyBudget(75, 140, 280));
        DESTINATION_BUDGETS.put("london", new DailyBudget(85, 160, 320));
        DESTINATION_BUDGETS.put("bangkok", new DailyBudget(40, 80, 150));
        DESTINATION_BUDGETS.put("bali", new DailyBudget(35, 70, 140));
        DESTINATION_BUDGETS.put("new york", new DailyBudget(100, 200, 400));
        DESTINATION_BUDGETS.put("dubai", new DailyBudget(90, 180, 350));
    }

    public static class DailyBudget {
        public final int budget;
        public final int midRange;
        public final int luxury;

        public DailyBudget(int budget, int midRange, int luxury) {
            this.budget = budget;
            this.midRange = midRange;
            this.luxury = luxury;
        }
    }

    public static String calculateBudget(String destination, int days, String style) {
        destination = destination.toLowerCase();
        DailyBudget budget = DESTINATION_BUDGETS.get(destination);
        
        if (budget == null) {
            return "Sorry, I don't have budget information for " + destination + " yet.";
        }

        int dailyAmount;
        switch (style.toLowerCase()) {
            case "budget":
                dailyAmount = budget.budget;
                break;
            case "luxury":
                dailyAmount = budget.luxury;
                break;
            default:
                dailyAmount = budget.midRange;
        }

        int totalAmount = dailyAmount * days;
        
        StringBuilder result = new StringBuilder();
        result.append("Estimated budget for ").append(days).append(" days in ").append(destination).append(":\n\n");
        result.append("Daily budget (").append(style).append("): $").append(dailyAmount).append("\n");
        result.append("Total budget: $").append(totalAmount).append("\n\n");
        result.append("This includes:\n");
        result.append("• Accommodation\n");
        result.append("• Food and drinks\n");
        result.append("• Local transportation\n");
        result.append("• Basic activities\n\n");
        result.append("Note: This is an estimate and actual costs may vary.");

        return result.toString();
    }

    public static String getBudgetTips(String destination) {
        destination = destination.toLowerCase();
        StringBuilder tips = new StringBuilder();
        
        switch (destination) {
            case "paris":
                tips.append("Budget Tips for Paris:\n\n");
                tips.append("1. Visit museums on first Sunday of month (free entry)\n");
                tips.append("2. Use public transportation passes\n");
                tips.append("3. Eat at local bistros instead of tourist restaurants\n");
                tips.append("4. Stay in less touristy arrondissements\n");
                tips.append("5. Buy food from local markets");
                break;
                
            case "tokyo":
                tips.append("Budget Tips for Tokyo:\n\n");
                tips.append("1. Use the Japan Rail Pass for long-distance travel\n");
                tips.append("2. Eat at local ramen shops and convenience stores\n");
                tips.append("3. Stay in business hotels or hostels\n");
                tips.append("4. Visit free attractions like parks and temples\n");
                tips.append("5. Use IC cards for public transportation");
                break;
                
            case "rome":
                tips.append("Budget Tips for Rome:\n\n");
                tips.append("1. Visit churches (many are free)\n");
                tips.append("2. Use public transportation passes\n");
                tips.append("3. Eat at local trattorias\n");
                tips.append("4. Stay in less touristy neighborhoods\n");
                tips.append("5. Buy food from local markets");
                break;
                
            default:
                tips.append("General Budget Tips:\n\n");
                tips.append("1. Book accommodation in advance\n");
                tips.append("2. Use public transportation\n");
                tips.append("3. Eat where locals eat\n");
                tips.append("4. Look for free attractions\n");
                tips.append("5. Buy a city pass if available");
        }
        
        return tips.toString();
    }
} 