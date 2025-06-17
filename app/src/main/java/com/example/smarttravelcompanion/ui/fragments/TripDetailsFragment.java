package com.example.smarttravelcompanion.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.smarttravelcompanion.R;
import com.example.smarttravelcompanion.data.db.AppDatabase;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.ui.adapters.PackingListAdapter;
import java.util.ArrayList;
import com.example.smarttravelcompanion.util.NotificationHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TripDetailsFragment extends Fragment {
    public static final String ARG_TRIP_ID = "trip_id";
    private Trip trip;
    private int tripId;
    private EditText notesEditText;
    private PackingListAdapter packingListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        notesEditText = view.findViewById(R.id.edit_notes);
        TextView tvDestination = view.findViewById(R.id.tv_detail_destination);
        TextView tvDates = view.findViewById(R.id.tv_detail_dates);
        TextView tvWeather = view.findViewById(R.id.tv_weather);
        Spinner spinnerStatus = view.findViewById(R.id.spinner_status);
        RecyclerView recyclerPacking = view.findViewById(R.id.recycler_packing_list);

        if (getArguments() != null) {
            tripId = getArguments().getInt(ARG_TRIP_ID);
            AsyncTask.execute(() -> {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                trip = db.tripDao().getTripById(tripId);
                if (trip != null) {
                    requireActivity().runOnUiThread(() -> {
                        tvDestination.setText(trip.destination);
                        tvDates.setText(trip.startDate + " to " + trip.endDate);
                        notesEditText.setText(trip.getNotes() != null ? trip.getNotes() : "");
                        // Set mock weather info
                        String weatherInfo = getMockWeather(trip.destination);
                        tvWeather.setText(weatherInfo);
                        // Set status in spinner
                        int statusIndex = 0;
                        if (trip.getStatus() != null) {
                            String[] statuses = {"Upcoming", "Completed", "Cancelled"};
                            for (int i = 0; i < statuses.length; i++) {
                                if (statuses[i].equalsIgnoreCase(trip.getStatus())) {
                                    statusIndex = i;
                                    break;
                                }
                            }
                        }
                        spinnerStatus.setSelection(statusIndex);
                        // Set packing list checklist
                        packingListAdapter.getItems().clear();
                        if (trip.getImportantDocuments() != null && !trip.getImportantDocuments().isEmpty()) {
                            for (String item : trip.getImportantDocuments().split(",")) {
                                packingListAdapter.addItem(new PackingListAdapter.PackingItem(item.trim(), false));
                            }
                        }
                        packingListAdapter.notifyDataSetChanged();
                    });
                }
            });
        }

        String[] statuses = {"Upcoming", "Completed", "Cancelled"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        packingListAdapter = new PackingListAdapter(new ArrayList<>());
        recyclerPacking.setAdapter(packingListAdapter);
        view.findViewById(R.id.btn_add_packing_item).setOnClickListener(v -> addPackingItem());
        view.findViewById(R.id.btn_save_notes).setOnClickListener(v -> {
            saveNotesAndPackingList();
            // Save status
            trip.setStatus(spinnerStatus.getSelectedItem().toString());
            AsyncTask.execute(() -> {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                db.tripDao().update(trip);
            });
        });
        view.findViewById(R.id.btn_delete_trip).setOnClickListener(v -> confirmDeleteTrip());
        view.findViewById(R.id.btn_share_trip).setOnClickListener(v -> shareTrip());
        return view;
    }

    private void saveNotesAndPackingList() {
        if (trip == null) return;
        trip.setNotes(notesEditText.getText().toString());
        // Save packing list as comma-separated string
        StringBuilder sb = new StringBuilder();
        for (PackingListAdapter.PackingItem item : packingListAdapter.getItems()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(item.name);
        }
        trip.setImportantDocuments(sb.toString());
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            db.tripDao().update(trip);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Trip updated!", Toast.LENGTH_SHORT).show();
                scheduleTripNotificationIfUpcoming();
            });
        });
    }

    private void confirmDeleteTrip() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Delete Trip")
            .setMessage("Are you sure you want to delete this trip?")
            .setPositiveButton("Delete", (dialog, which) -> deleteTrip())
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void deleteTrip() {
        if (trip == null) return;
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            db.tripDao().delete(trip);
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Trip deleted", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            });
        });
    }

    private String getMockWeather(String destination) {
        // Simple mock: change weather based on destination name
        if (destination.toLowerCase().contains("paris")) return "Weather: Cloudy, 18°C";
        if (destination.toLowerCase().contains("london")) return "Weather: Rainy, 15°C";
        if (destination.toLowerCase().contains("delhi")) return "Weather: Sunny, 32°C";
        return "Weather: Sunny, 25°C";
    }

    private void shareTrip() {
        if (trip == null) return;
        String shareText = "Trip to " + trip.getDestination() + "\n" +
                "Dates: " + trip.getStartDate() + " to " + trip.getEndDate() + "\n" +
                "Notes: " + (trip.getNotes() != null ? trip.getNotes() : "") + "\n" +
                "Packing List: " + (trip.getImportantDocuments() != null ? trip.getImportantDocuments() : "");
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sendIntent, "Share Trip via"));
    }

    private void addPackingItem() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Add Packing Item");
        final EditText input = new EditText(requireContext());
        builder.setView(input);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String item = input.getText().toString().trim();
            if (!item.isEmpty()) {
                packingListAdapter.addItem(new PackingListAdapter.PackingItem(item, false));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void scheduleTripNotificationIfUpcoming() {
        try {
            String startDateStr = trip.getStartDate();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
            java.util.Date startDate = sdf.parse(startDateStr);
            long triggerAtMillis = startDate.getTime() - 24 * 60 * 60 * 1000; // 1 day before
            if (triggerAtMillis > System.currentTimeMillis()) {
                com.example.smarttravelcompanion.util.NotificationHelper.scheduleTripNotification(requireContext(), triggerAtMillis, trip.getDestination());
            }
        } catch (Exception e) {
            // Ignore parse errors
        }
    }
} 