package com.example.smarttravelcompanion.utils;

import java.util.HashMap;
import java.util.Map;

public class LocalPhraseTranslator {
    private static final Map<String, Map<String, String>> PHRASES = new HashMap<>();
    
    static {
        // French phrases
        Map<String, String> frenchPhrases = new HashMap<>();
        frenchPhrases.put("hello", "Bonjour");
        frenchPhrases.put("thank you", "Merci");
        frenchPhrases.put("goodbye", "Au revoir");
        frenchPhrases.put("please", "S'il vous plaît");
        frenchPhrases.put("excuse me", "Excusez-moi");
        frenchPhrases.put("where is", "Où est");
        frenchPhrases.put("how much", "Combien");
        frenchPhrases.put("delicious", "Délicieux");
        frenchPhrases.put("water", "Eau");
        frenchPhrases.put("bathroom", "Toilettes");
        PHRASES.put("french", frenchPhrases);

        // Japanese phrases
        Map<String, String> japanesePhrases = new HashMap<>();
        japanesePhrases.put("hello", "こんにちは (Konnichiwa)");
        japanesePhrases.put("thank you", "ありがとう (Arigatou)");
        japanesePhrases.put("goodbye", "さようなら (Sayonara)");
        japanesePhrases.put("please", "お願いします (Onegaishimasu)");
        japanesePhrases.put("excuse me", "すみません (Sumimasen)");
        japanesePhrases.put("where is", "どこですか (Doko desu ka)");
        japanesePhrases.put("how much", "いくらですか (Ikura desu ka)");
        japanesePhrases.put("delicious", "おいしい (Oishii)");
        japanesePhrases.put("water", "水 (Mizu)");
        japanesePhrases.put("bathroom", "トイレ (Toire)");
        PHRASES.put("japanese", japanesePhrases);

        // Italian phrases
        Map<String, String> italianPhrases = new HashMap<>();
        italianPhrases.put("hello", "Ciao");
        italianPhrases.put("thank you", "Grazie");
        italianPhrases.put("goodbye", "Arrivederci");
        italianPhrases.put("please", "Per favore");
        italianPhrases.put("excuse me", "Scusi");
        italianPhrases.put("where is", "Dov'è");
        italianPhrases.put("how much", "Quanto costa");
        italianPhrases.put("delicious", "Delizioso");
        italianPhrases.put("water", "Acqua");
        italianPhrases.put("bathroom", "Bagno");
        PHRASES.put("italian", italianPhrases);

        // Spanish phrases
        Map<String, String> spanishPhrases = new HashMap<>();
        spanishPhrases.put("hello", "Hola");
        spanishPhrases.put("thank you", "Gracias");
        spanishPhrases.put("goodbye", "Adiós");
        spanishPhrases.put("please", "Por favor");
        spanishPhrases.put("excuse me", "Disculpe");
        spanishPhrases.put("where is", "Dónde está");
        spanishPhrases.put("how much", "Cuánto cuesta");
        spanishPhrases.put("delicious", "Delicioso");
        spanishPhrases.put("water", "Agua");
        spanishPhrases.put("bathroom", "Baño");
        PHRASES.put("spanish", spanishPhrases);
    }

    public static String getPhrase(String language, String phrase) {
        language = language.toLowerCase();
        phrase = phrase.toLowerCase();
        
        Map<String, String> languagePhrases = PHRASES.get(language);
        if (languagePhrases == null) {
            return "Sorry, I don't have phrases for " + language + " yet.";
        }
        
        String translation = languagePhrases.get(phrase);
        if (translation == null) {
            return "Sorry, I don't know how to say '" + phrase + "' in " + language + ".";
        }
        
        return translation;
    }

    public static String getCommonPhrases(String language) {
        language = language.toLowerCase();
        Map<String, String> languagePhrases = PHRASES.get(language);
        
        if (languagePhrases == null) {
            return "Sorry, I don't have phrases for " + language + " yet.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Common ").append(language).append(" phrases:\n\n");
        
        for (Map.Entry<String, String> entry : languagePhrases.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return result.toString();
    }

    public static String getLanguageTips(String language) {
        language = language.toLowerCase();
        StringBuilder tips = new StringBuilder();
        
        switch (language) {
            case "french":
                tips.append("French Language Tips:\n\n");
                tips.append("1. Always greet with 'Bonjour' before starting a conversation\n");
                tips.append("2. Use 'vous' for formal situations, 'tu' for friends\n");
                tips.append("3. Practice your pronunciation of nasal sounds\n");
                tips.append("4. Learn basic numbers for shopping\n");
                tips.append("5. Remember to say 'Merci' and 'S'il vous plaît'");
                break;
                
            case "japanese":
                tips.append("Japanese Language Tips:\n\n");
                tips.append("1. Bow slightly when greeting\n");
                tips.append("2. Use honorifics (-san, -sama) appropriately\n");
                tips.append("3. Learn basic numbers and counting\n");
                tips.append("4. Practice saying 'Sumimasen' for getting attention\n");
                tips.append("5. Remember to say 'Itadakimasu' before eating");
                break;
                
            case "italian":
                tips.append("Italian Language Tips:\n\n");
                tips.append("1. Use hand gestures while speaking\n");
                tips.append("2. Practice rolling your 'r's\n");
                tips.append("3. Learn basic food vocabulary\n");
                tips.append("4. Use 'Ciao' for both hello and goodbye\n");
                tips.append("5. Remember to say 'Buon appetito' before meals");
                break;
                
            default:
                tips.append("General Language Tips:\n\n");
                tips.append("1. Learn basic greetings\n");
                tips.append("2. Practice numbers and counting\n");
                tips.append("3. Learn how to ask for directions\n");
                tips.append("4. Know how to order food\n");
                tips.append("5. Learn how to say thank you");
        }
        
        return tips.toString();
    }
} 