package com.example.smarttravelcompanion.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.R;
import com.example.smarttravelcompanion.data.api.LocalTravelService;
import com.example.smarttravelcompanion.ui.adapters.ChatAdapter;
import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ChatAdapter chatAdapter;
    private final java.util.List<ChatAdapter.ChatMessage> messages = new ArrayList<>();
    private LocalTravelService travelService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        
        // Initialize travel service
        travelService = LocalTravelService.getInstance();
        
        RecyclerView recyclerView = view.findViewById(R.id.recycler_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setAdapter(chatAdapter);
        
        // Add welcome message with examples
        chatAdapter.addMessage(new ChatAdapter.ChatMessage(
            "👋 Welcome to Smart Travel Companion! Here are all the features you can use:\n\n" +
            "1. 📍 Destination Information\n" +
            "   • 'Tell me about Paris'\n" +
            "   • 'What should I know about Tokyo?'\n" +
            "   • 'Give me information about London'\n\n" +
            "2. 💰 Budget Planning\n" +
            "   • 'What's the budget for 7 days in Paris?'\n" +
            "   • 'How much money do I need for Tokyo?'\n" +
            "   • 'Budget tips for Rome'\n" +
            "   • 'Cost of 5 days in London luxury style'\n\n" +
            "3. 🧳 Packing Lists\n" +
            "   • 'What should I pack for Rome?'\n" +
            "   • 'Give me a packing list for a beach trip'\n" +
            "   • 'What to bring for winter travel?'\n\n" +
            "4. 💱 Currency & Money\n" +
            "   • 'What's the currency in London?'\n" +
            "   • 'Convert 100 USD to EUR'\n" +
            "   • 'How much money should I bring to Tokyo?'\n\n" +
            "5. 🗣️ Local Phrases\n" +
            "   • 'How do you say hello in French?'\n" +
            "   • 'Show me common Japanese phrases'\n" +
            "   • 'Language tips for Italian'\n" +
            "   • 'How to say thank you in Spanish'\n\n" +
            "6. ⏰ Trip Countdown\n" +
            "   • 'How many days until my trip to Paris?'\n" +
            "   • 'When is my trip to Tokyo?'\n\n" +
            "Try any of these questions or ask your own! How can I help you plan your trip today?", false));
        
        EditText editInput = view.findViewById(R.id.edit_chat_input);
        Button btnSend = view.findViewById(R.id.btn_send);
        
        // Add example questions as clickable suggestions
        Button btnExample1 = view.findViewById(R.id.btn_example1);
        Button btnExample2 = view.findViewById(R.id.btn_example2);
        Button btnExample3 = view.findViewById(R.id.btn_example3);
        
        if (btnExample1 != null) {
            btnExample1.setText("Budget for Paris");
            btnExample1.setOnClickListener(v -> {
                editInput.setText("What's the budget for 7 days in Paris?");
                btnSend.performClick();
            });
        }
        
        if (btnExample2 != null) {
            btnExample2.setText("Japanese Phrases");
            btnExample2.setOnClickListener(v -> {
                editInput.setText("Show me common Japanese phrases");
                btnSend.performClick();
            });
        }
        
        if (btnExample3 != null) {
            btnExample3.setText("Packing List");
            btnExample3.setOnClickListener(v -> {
                editInput.setText("What should I pack for Rome?");
                btnSend.performClick();
            });
        }
        
        btnSend.setOnClickListener(v -> {
            String userMsg = editInput.getText().toString().trim();
            if (!userMsg.isEmpty()) {
                // Add user message
                chatAdapter.addMessage(new ChatAdapter.ChatMessage(userMsg, true));
                editInput.setText("");
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                
                // Get travel advice
                travelService.getTravelAdvice(userMsg, new LocalTravelService.TravelResponseCallback() {
                    @Override
                    public void onResponse(String response) {
                        requireActivity().runOnUiThread(() -> {
                            chatAdapter.addMessage(new ChatAdapter.ChatMessage(response, false));
                            recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                            
                            // Add follow-up suggestions based on the response
                            if (response.toLowerCase().contains("paris")) {
                                addFollowUpSuggestions("Paris", recyclerView);
                            } else if (response.toLowerCase().contains("tokyo")) {
                                addFollowUpSuggestions("Tokyo", recyclerView);
                            } else if (response.toLowerCase().contains("rome")) {
                                addFollowUpSuggestions("Rome", recyclerView);
                            } else if (response.toLowerCase().contains("london")) {
                                addFollowUpSuggestions("London", recyclerView);
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                            chatAdapter.addMessage(new ChatAdapter.ChatMessage(
                                "I'm sorry, I couldn't understand that. Try asking about a specific destination like Paris, Tokyo, Rome, or London.", false));
                            recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                        });
                    }
                });
            }
        });
        
        return view;
    }

    private void addFollowUpSuggestions(String destination, RecyclerView recyclerView) {
        String[] suggestions = {
            "What should I pack for " + destination + "?",
            "What's the best time to visit " + destination + "?",
            "What's the currency in " + destination + "?"
        };
        
        StringBuilder message = new StringBuilder("You might also want to know:\n\n");
        for (String suggestion : suggestions) {
            message.append("• ").append(suggestion).append("\n");
        }
        
        chatAdapter.addMessage(new ChatAdapter.ChatMessage(message.toString(), false));
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }
} 