package com.example.smarttravelcompanion.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.smarttravelcompanion.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import androidx.navigation.Navigation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HomeFragment extends Fragment {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private OpenWeatherMapHelper weatherHelper;
    private TextView weatherTextView;
    private TextView locationTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        // Initialize views
        weatherTextView = view.findViewById(R.id.weather_text);
        locationTextView = view.findViewById(R.id.location_text);
        
        // Travel Tools buttons
        View btnTravelChecklist = view.findViewById(R.id.btn_travel_checklist);
        View btnTransportationGuide = view.findViewById(R.id.btn_transportation_guide);
        
        // Quick action buttons
        View btnPlanTrip = view.findViewById(R.id.btn_plan_trip);
        View btnViewItinerary = view.findViewById(R.id.btn_view_itinerary);
        
        // Travel Tools click handlers
        btnTravelChecklist.setOnClickListener(v -> showTravelChecklistDialog());
        btnTransportationGuide.setOnClickListener(v -> showTransportationGuideDialog());
        
        // Quick Actions click handlers
        btnPlanTrip.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_home_to_planTrip);
        });
        
        btnViewItinerary.setOnClickListener(v -> {
            // Switch to Itinerary tab
            requireActivity().findViewById(R.id.bottom_navigation).post(() -> {
                ((com.google.android.material.bottomnavigation.BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation))
                        .setSelectedItemId(R.id.navigation_itinerary);
            });
        });
        
        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        
        // Initialize weather helper
        weatherHelper = new OpenWeatherMapHelper("YOUR_API_KEY"); // Replace with your OpenWeatherMap API key
        weatherHelper.setUnits(Units.METRIC);
        
        // Check location permission and get location
        checkLocationPermission();
        
        return view;
    }

    private void showTravelChecklistDialog() {
        String[] options = {"Pre-trip Checklist", "During-trip Checklist", "Post-trip Checklist"};
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Travel Checklist")
            .setItems(options, (dialog, which) -> {
                String phase = "";
                switch (which) {
                    case 0:
                        phase = "pre";
                        break;
                    case 1:
                        phase = "during";
                        break;
                    case 2:
                        phase = "post";
                        break;
                }
                showDestinationDialog(phase);
            })
            .show();
    }

    private void showDestinationDialog(String phase) {
        String[] destinations = {"Paris", "Tokyo", "Rome", "London"};
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Destination")
            .setItems(destinations, (dialog, which) -> {
                String destination = destinations[which].toLowerCase();
                String checklist = com.example.smarttravelcompanion.utils.TravelChecklistGenerator.generateChecklist(phase, destination);
                showChecklistResult(checklist);
            })
            .show();
    }

    private void showChecklistResult(String checklist) {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Travel Checklist")
            .setMessage(checklist)
            .setPositiveButton("OK", null)
            .show();
    }

    private void showTransportationGuideDialog() {
        String[] destinations = {"Paris", "Tokyo", "Rome", "London"};
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Destination")
            .setItems(destinations, (dialog, which) -> {
                String destination = destinations[which].toLowerCase();
                String transportInfo = com.example.smarttravelcompanion.utils.TransportationGuide.getTransportationInfo(destination);
                showTransportationResult(transportInfo);
            })
            .show();
    }

    private void showTransportationResult(String transportInfo) {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle("Transportation Guide")
            .setMessage(transportInfo)
            .setPositiveButton("OK", null)
            .show();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) 
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
            @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission required for weather updates", 
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                updateLocationUI(location);
                                getWeatherData(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        }
    }

    private void updateLocationUI(Location location) {
        locationTextView.setText(String.format("Location: %.2f, %.2f", 
                location.getLatitude(), location.getLongitude()));
    }

    private void getWeatherData(double latitude, double longitude) {
        weatherHelper.getCurrentWeatherByGeoCoordinates(latitude, longitude, 
                new CurrentWeatherCallback() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        String weatherInfo = String.format("Temperature: %.1f°C\n" +
                                "Weather: %s\n" +
                                "Humidity: %d%%",
                                currentWeather.getMain().getTemp(),
                                currentWeather.getWeather().get(0).getDescription(),
                                currentWeather.getMain().getHumidity());
                        weatherTextView.setText(weatherInfo);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        weatherTextView.setText("Failed to get weather data");
                    }
                });
    }
} 