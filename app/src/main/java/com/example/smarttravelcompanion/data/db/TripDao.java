package com.example.smarttravelcompanion.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface TripDao {
    @Insert
    long insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips ORDER BY startDate ASC")
    LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM trips WHERE id = :tripId")
    LiveData<Trip> getTripById(int tripId);

    @Query("SELECT * FROM trips WHERE isCompleted = 0 ORDER BY startDate ASC")
    LiveData<List<Trip>> getUpcomingTrips();

    @Query("SELECT * FROM trips WHERE isCompleted = 1 ORDER BY endDate DESC")
    LiveData<List<Trip>> getPastTrips();

    @Query("SELECT * FROM trips WHERE startDate >= :currentDate ORDER BY startDate ASC LIMIT 1")
    LiveData<Trip> getNextUpcomingTrip(String currentDate);
} 