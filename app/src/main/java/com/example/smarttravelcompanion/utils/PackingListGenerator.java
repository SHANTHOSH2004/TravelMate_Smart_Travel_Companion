package com.example.smarttravelcompanion.utils;

import java.util.ArrayList;
import java.util.List;

public class PackingListGenerator {
    private static final String[] ESSENTIALS = {
        "Passport/ID",
        "Travel documents",
        "Credit cards",
        "Cash",
        "Phone charger",
        "Medications",
        "Toiletries"
    };

    private static final String[] CLOTHING = {
        "Underwear",
        "Socks",
        "T-shirts",
        "Pants/Shorts",
        "Sweater/Jacket",
        "Comfortable shoes",
        "Swimwear"
    };

    private static final String[] ELECTRONICS = {
        "Phone",
        "Camera",
        "Power bank",
        "Universal adapter",
        "Headphones"
    };

    private static final String[] BEACH_ITEMS = {
        "Sunscreen",
        "Beach towel",
        "Sunglasses",
        "Beach bag",
        "Flip flops"
    };

    private static final String[] WINTER_ITEMS = {
        "Winter coat",
        "Gloves",
        "Scarf",
        "Thermal underwear",
        "Winter boots"
    };

    public static List<String> generatePackingList(String destination, int duration, boolean isBeach, boolean isWinter) {
        List<String> packingList = new ArrayList<>();
        
        // Add essentials
        for (String item : ESSENTIALS) {
            packingList.add(item);
        }

        // Add clothing based on duration
        for (String item : CLOTHING) {
            packingList.add(item + (duration > 7 ? " (multiple)" : ""));
        }

        // Add electronics
        for (String item : ELECTRONICS) {
            packingList.add(item);
        }

        // Add destination-specific items
        if (isBeach) {
            for (String item : BEACH_ITEMS) {
                packingList.add(item);
            }
        }

        if (isWinter) {
            for (String item : WINTER_ITEMS) {
                packingList.add(item);
            }
        }

        // Add destination-specific recommendations
        if (destination.toLowerCase().contains("paris")) {
            packingList.add("Comfortable walking shoes");
            packingList.add("Dress clothes for dining");
        } else if (destination.toLowerCase().contains("tokyo")) {
            packingList.add("Comfortable walking shoes");
            packingList.add("Umbrella");
        } else if (destination.toLowerCase().contains("rome")) {
            packingList.add("Comfortable walking shoes");
            packingList.add("Modest clothing for churches");
        }

        return packingList;
    }
} 