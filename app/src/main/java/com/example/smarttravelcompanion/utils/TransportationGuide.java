package com.example.smarttravelcompanion.utils;

import java.util.HashMap;
import java.util.Map;

public class TransportationGuide {
    private static final Map<String, TransportationInfo> TRANSPORTATION_INFO = new HashMap<>();
    
    static {
        // Paris transportation
        TransportationInfo parisInfo = new TransportationInfo(
            "Paris Metro and Bus",
            "€1.90 per ticket, €14.90 for 10 tickets",
            "Metro runs 5:30 AM - 1:15 AM, Bus runs 5:30 AM - 12:30 AM",
            "• Buy a carnet of 10 tickets for better value\n" +
            "• Use RATP app for real-time updates\n" +
            "• Validate your ticket before each journey\n" +
            "• Keep your ticket until the end of your journey"
        );
        TRANSPORTATION_INFO.put("paris", parisInfo);

        // Tokyo transportation
        TransportationInfo tokyoInfo = new TransportationInfo(
            "Tokyo Metro and JR Lines",
            "¥170-¥310 per ride, ¥1,000 for IC card deposit",
            "Metro runs 5:00 AM - 1:00 AM, JR runs 4:30 AM - 1:00 AM",
            "• Get a Suica or Pasmo IC card\n" +
            "• Use Japan Travel app for routes\n" +
            "• Avoid rush hour (8:00-9:30 AM)\n" +
            "• Stand on the left side of escalators"
        );
        TRANSPORTATION_INFO.put("tokyo", tokyoInfo);

        // Rome transportation
        TransportationInfo romeInfo = new TransportationInfo(
            "Rome Metro, Bus, and Tram",
            "€1.50 per ticket, €7 for 24-hour pass",
            "Metro runs 5:30 AM - 11:30 PM, Bus runs 5:30 AM - 12:00 AM",
            "• Buy tickets before boarding\n" +
            "• Validate your ticket in the machine\n" +
            "• Use ATAC app for schedules\n" +
            "• Consider Roma Pass for unlimited travel"
        );
        TRANSPORTATION_INFO.put("rome", romeInfo);

        // London transportation
        TransportationInfo londonInfo = new TransportationInfo(
            "London Underground and Bus",
            "£2.40-£6.80 per ride, £15.20 for day pass",
            "Tube runs 5:00 AM - 1:00 AM, Bus runs 24 hours",
            "• Get an Oyster card\n" +
            "• Use Citymapper app for routes\n" +
            "• Avoid peak hours (7:30-9:30 AM)\n" +
            "• Stand on the right side of escalators"
        );
        TRANSPORTATION_INFO.put("london", londonInfo);
    }

    public static class TransportationInfo {
        public final String system;
        public final String cost;
        public final String hours;
        public final String tips;

        public TransportationInfo(String system, String cost, String hours, String tips) {
            this.system = system;
            this.cost = cost;
            this.hours = hours;
            this.tips = tips;
        }
    }

    public static String getTransportationInfo(String destination) {
        destination = destination.toLowerCase();
        TransportationInfo info = TRANSPORTATION_INFO.get(destination);
        
        if (info == null) {
            return "Sorry, I don't have transportation information for " + destination + " yet.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("🚇 Transportation in ").append(destination.substring(0, 1).toUpperCase())
              .append(destination.substring(1)).append(":\n\n");
        result.append("System: ").append(info.system).append("\n");
        result.append("Cost: ").append(info.cost).append("\n");
        result.append("Operating Hours: ").append(info.hours).append("\n\n");
        result.append("Tips:\n").append(info.tips);
        
        return result.toString();
    }

    public static String getTaxiInfo(String destination) {
        destination = destination.toLowerCase();
        StringBuilder result = new StringBuilder();
        
        switch (destination) {
            case "paris":
                result.append("🚕 Paris Taxi Information:\n\n");
                result.append("• Official taxis are marked with a light on top\n");
                result.append("• Base fare: €2.60\n");
                result.append("• Per km rate: €1.05\n");
                result.append("• Airport to city: €50-60\n");
                result.append("• Use G7 or Taxi Bleu apps\n");
                result.append("• Tipping: Round up to nearest euro");
                break;
                
            case "tokyo":
                result.append("🚕 Tokyo Taxi Information:\n\n");
                result.append("• Look for green light on taxi\n");
                result.append("• Base fare: ¥410\n");
                result.append("• Per km rate: ¥80\n");
                result.append("• Airport to city: ¥20,000-30,000\n");
                result.append("• Use JapanTaxi app\n");
                result.append("• Tipping: Not expected");
                break;
                
            case "rome":
                result.append("🚕 Rome Taxi Information:\n\n");
                result.append("• Official taxis are white\n");
                result.append("• Base fare: €3.00\n");
                result.append("• Per km rate: €1.10\n");
                result.append("• Airport to city: €48\n");
                result.append("• Use MyTaxi app\n");
                result.append("• Tipping: Round up to nearest euro");
                break;
                
            default:
                result.append("Sorry, I don't have taxi information for ").append(destination).append(" yet.");
        }
        
        return result.toString();
    }
} 