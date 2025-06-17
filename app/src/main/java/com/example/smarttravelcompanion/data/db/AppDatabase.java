package com.example.smarttravelcompanion.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.smarttravelcompanion.data.db.dao.TripDao;
import com.example.smarttravelcompanion.data.db.dao.UserPreferencesDao;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import com.example.smarttravelcompanion.data.db.entity.UserPreferences;

@Database(entities = {Trip.class, UserPreferences.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "smart_travel_db";
    private static AppDatabase instance;

    public abstract TripDao tripDao();
    public abstract UserPreferencesDao userPreferencesDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
} 