package com.example.smarttravelcompanion;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.smarttravelcompanion.R;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestNotificationPermissionIfNeeded();
        try {
            Log.d(TAG, "onCreate: Starting");
        setContentView(R.layout.activity_main);
            Log.d(TAG, "onCreate: Content view set");

        // Setup Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            if (bottomNav == null) {
                Log.e(TAG, "onCreate: Bottom navigation view not found");
                return;
            }
            Log.d(TAG, "onCreate: Bottom navigation view found");
            
            // Get NavHostFragment
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment == null) {
                Log.e(TAG, "onCreate: NavHostFragment not found");
                return;
            }
            
            // Get NavController
            navController = navHostFragment.getNavController();
            Log.d(TAG, "onCreate: Nav controller initialized");
            
            // Setup AppBarConfiguration
        appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_itinerary,
            R.id.navigation_chat,
            R.id.navigation_profile
        ).build();
            Log.d(TAG, "onCreate: App bar configuration created");

            // Setup ActionBar
            try {
                if (getSupportActionBar() != null) {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                    Log.d(TAG, "onCreate: Action bar setup complete");
                } else {
                    Log.w(TAG, "onCreate: Action bar is null");
                }
            } catch (Exception e) {
                Log.w(TAG, "onCreate: Could not setup ActionBar", e);
            }
            
            // Setup Bottom Navigation
        NavigationUI.setupWithNavController(bottomNav, navController);
            Log.d(TAG, "onCreate: Bottom navigation setup complete");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error", e);
            throw e;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        try {
            if (navController != null && appBarConfiguration != null) {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
            }
        } catch (Exception e) {
            Log.e(TAG, "onSupportNavigateUp: Error", e);
        }
        return super.onSupportNavigateUp();
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }
}