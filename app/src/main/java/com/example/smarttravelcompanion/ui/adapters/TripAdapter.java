package com.example.smarttravelcompanion.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.R;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> tripList;

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
    }
    private OnTripClickListener listener;
    public void setOnTripClickListener(OnTripClickListener listener) {
        this.listener = listener;
    }

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tvDestination.setText(trip.destination);
        holder.tvDates.setText(trip.startDate + " to " + trip.endDate);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTripClick(trip);
        });
    }

    @Override
    public int getItemCount() {
        return tripList == null ? 0 : tripList.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvDestination, tvDates;
        TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDestination = itemView.findViewById(R.id.tv_destination);
            tvDates = itemView.findViewById(R.id.tv_dates);
        }
    }
} 