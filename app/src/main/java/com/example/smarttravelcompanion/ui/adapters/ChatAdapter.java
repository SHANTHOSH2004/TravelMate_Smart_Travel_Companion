package com.example.smarttravelcompanion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    public static class ChatMessage {
        public String message;
        public boolean isUser;
        public ChatMessage(String message, boolean isUser) {
            this.message = message;
            this.isUser = isUser;
        }
    }
    private List<ChatMessage> messages;
    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }
    public void addMessage(ChatMessage msg) {
        messages.add(msg);
        notifyItemInserted(messages.size() - 1);
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage msg = messages.get(position);
        holder.tvMessage.setText(msg.message);
        holder.tvMessage.setBackgroundResource(msg.isUser ? R.drawable.bg_user_message : R.drawable.bg_assistant_message);
        holder.tvMessage.setTextAlignment(msg.isUser ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }
    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_chat_message);
        }
    }
} 