package com.example.smarttravelcompanion.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.smarttravelcompanion.data.db.entity.UserPreferences;

@Dao
public interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences WHERE id = 1")
    LiveData<UserPreferences> getUserPreferences();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserPreferences userPreferences);

    @Update
    void update(UserPreferences userPreferences);

    @Query("UPDATE user_preferences SET darkMode = :darkMode WHERE id = 1")
    void updateDarkMode(boolean darkMode);

    @Query("UPDATE user_preferences SET notificationsEnabled = :enabled WHERE id = 1")
    void updateNotificationsEnabled(boolean enabled);

    @Query("UPDATE user_preferences SET preferredLanguage = :language WHERE id = 1")
    void updatePreferredLanguage(String language);

    @Query("UPDATE user_preferences SET defaultCurrency = :currency WHERE id = 1")
    void updateDefaultCurrency(String currency);
} 