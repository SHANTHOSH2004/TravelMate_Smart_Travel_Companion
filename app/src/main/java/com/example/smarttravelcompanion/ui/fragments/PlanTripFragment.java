package com.example.smarttravelcompanion.ui.fragments;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.smarttravelcompanion.R;
import com.example.smarttravelcompanion.data.db.AppDatabase;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import java.util.Calendar;

public class PlanTripFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_trip, container, false);

        EditText editDestination = view.findViewById(R.id.edit_destination);
        EditText editStartDate = view.findViewById(R.id.edit_start_date);
        EditText editEndDate = view.findViewById(R.id.edit_end_date);
        Button btnSaveTrip = view.findViewById(R.id.btn_save_trip);

        // Date picker for start date
        editStartDate.setOnClickListener(v -> showDatePicker(editStartDate));
        // Date picker for end date
        editEndDate.setOnClickListener(v -> showDatePicker(editEndDate));

        btnSaveTrip.setOnClickListener(v -> {
            String destination = editDestination.getText().toString().trim();
            String startDate = editStartDate.getText().toString().trim();
            String endDate = editEndDate.getText().toString().trim();

            if (destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Trip trip = new Trip(destination, startDate, endDate);
                AsyncTask.execute(() -> {
                    AppDatabase db = AppDatabase.getInstance(requireContext());
                    db.tripDao().insert(trip);
                });
                String msg = "Trip planned to " + destination + " from " + startDate + " to " + endDate + "!";
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
                // Optionally, clear fields or navigate away
            }
        });

        return view;
    }

    private void showDatePicker(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            String dateStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            targetEditText.setText(dateStr);
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
} 