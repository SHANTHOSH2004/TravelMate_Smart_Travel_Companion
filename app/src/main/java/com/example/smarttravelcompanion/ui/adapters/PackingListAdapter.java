package com.example.smarttravelcompanion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.R;
import java.util.List;

public class PackingListAdapter extends RecyclerView.Adapter<PackingListAdapter.PackingViewHolder> {
    public static class PackingItem {
        public String name;
        public boolean checked;
        public PackingItem(String name, boolean checked) {
            this.name = name;
            this.checked = checked;
        }
    }
    private List<PackingItem> items;
    public PackingListAdapter(List<PackingItem> items) {
        this.items = items;
    }
    public List<PackingItem> getItems() { return items; }
    public void addItem(PackingItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }
    @NonNull
    @Override
    public PackingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packing_check, parent, false);
        return new PackingViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PackingViewHolder holder, int position) {
        PackingItem item = items.get(position);
        holder.checkBox.setText(item.name);
        holder.checkBox.setChecked(item.checked);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.checked = isChecked);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    static class PackingViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        PackingViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_packing_item);
        }
    }
} 