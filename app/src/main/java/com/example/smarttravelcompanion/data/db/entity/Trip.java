package com.example.smarttravelcompanion.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.Ignore;

@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String userId;
    @NonNull
    public String destination;
    @NonNull
    public String startDate;
    @NonNull
    public String endDate;
    private String itinerary;
    private String notes;
    private String status; // PLANNED, UPCOMING, COMPLETED, CANCELLED
    private double budget;
    private String emergencyContacts;
    private String importantDocuments;
    private boolean isOfflineMapDownloaded;

    // No-argument constructor required by Room
    public Trip() {}

    // Full-argument constructor for convenience
    @Ignore
    public Trip(@NonNull String destination, @NonNull String startDate, @NonNull String endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getItinerary() { return itinerary; }
    public void setItinerary(String itinerary) { this.itinerary = itinerary; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getEmergencyContacts() { return emergencyContacts; }
    public void setEmergencyContacts(String emergencyContacts) { 
        this.emergencyContacts = emergencyContacts; 
    }

    public String getImportantDocuments() { return importantDocuments; }
    public void setImportantDocuments(String importantDocuments) { 
        this.importantDocuments = importantDocuments; 
    }

    public boolean isOfflineMapDownloaded() { return isOfflineMapDownloaded; }
    public void setOfflineMapDownloaded(boolean offlineMapDownloaded) { 
        this.isOfflineMapDownloaded = offlineMapDownloaded; 
    }
} 