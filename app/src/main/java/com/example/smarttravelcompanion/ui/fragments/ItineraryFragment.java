package com.example.smarttravelcompanion.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smarttravelcompanion.R;
import com.example.smarttravelcompanion.data.db.AppDatabase;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import com.example.smarttravelcompanion.ui.adapters.TripAdapter;
import java.util.ArrayList;
import java.util.List;
import androidx.navigation.fragment.NavHostFragment;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

public class ItineraryFragment extends Fragment {
    private TripAdapter tripAdapter;
    private List<Trip> allTrips = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinerary, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        tripAdapter = new TripAdapter(new ArrayList<>());
        recyclerView.setAdapter(tripAdapter);
        tripAdapter.setOnTripClickListener(trip -> {
            Bundle args = new Bundle();
            args.putInt(TripDetailsFragment.ARG_TRIP_ID, trip.id);
            NavHostFragment.findNavController(this).navigate(R.id.action_itinerary_to_tripDetails, args);
        });
        Spinner spinnerFilter = view.findViewById(R.id.spinner_filter_status);
        String[] filterOptions = {"All", "Upcoming", "Completed", "Cancelled"};
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, filterOptions);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterTrips(filterOptions[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        loadTrips();
        return view;
    }

    private void loadTrips() {
        AsyncTask.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<Trip> trips = db.tripDao().getAllTrips();
            allTrips = trips;
            requireActivity().runOnUiThread(() -> filterTrips(getCurrentFilter()));
        });
    }

    private void filterTrips(String status) {
        if (status.equals("All")) {
            tripAdapter.setTripList(allTrips);
        } else {
            List<Trip> filtered = new ArrayList<>();
            for (Trip t : allTrips) {
                if (t.getStatus() != null && t.getStatus().equalsIgnoreCase(status)) {
                    filtered.add(t);
                }
            }
            tripAdapter.setTripList(filtered);
        }
    }

    private String getCurrentFilter() {
        Spinner spinner = getView().findViewById(R.id.spinner_filter_status);
        return spinner.getSelectedItem().toString();
    }
} 