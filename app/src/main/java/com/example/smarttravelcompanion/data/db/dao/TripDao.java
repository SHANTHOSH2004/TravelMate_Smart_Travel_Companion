package com.example.smarttravelcompanion.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.smarttravelcompanion.data.db.entity.Trip;
import java.util.Date;
import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips WHERE userId = :userId")
    List<Trip> getUserTrips(String userId);

    @Query("SELECT * FROM trips WHERE id = :tripId")
    Trip getTripById(int tripId);

    @Query("SELECT * FROM trips WHERE status = :status AND userId = :userId")
    List<Trip> getTripsByStatus(String status, String userId);

    @Query("SELECT * FROM trips WHERE startDate >= :startDate AND endDate <= :endDate AND userId = :userId")
    List<Trip> getTripsByDateRange(Date startDate, Date endDate, String userId);

    @Query("SELECT * FROM trips WHERE destination LIKE '%' || :destination || '%' AND userId = :userId")
    List<Trip> searchTripsByDestination(String destination, String userId);

    @Query("UPDATE trips SET status = :status WHERE id = :tripId")
    void updateTripStatus(int tripId, String status);

    @Query("UPDATE trips SET itinerary = :itinerary WHERE id = :tripId")
    void updateTripItinerary(int tripId, String itinerary);

    @Query("UPDATE trips SET isOfflineMapDownloaded = :downloaded WHERE id = :tripId")
    void updateOfflineMapStatus(int tripId, boolean downloaded);

    @Query("SELECT * FROM trips WHERE isOfflineMapDownloaded = 1 AND userId = :userId")
    List<Trip> getTripsWithOfflineMaps(String userId);

    @Query("SELECT * FROM trips ORDER BY startDate ASC")
    List<Trip> getAllTrips();
} 