package com.example.smarttravelcompanion.util;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.smarttravelcompanion.R;

public class NotificationHelper extends BroadcastReceiver {
    public static final String CHANNEL_ID = "trip_reminders";
    public static final int NOTIF_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {
        String tripName = intent.getStringExtra("tripName");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Trip Reminders", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Upcoming Trip Reminder")
                .setContentText("Your trip to " + tripName + " is coming up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        manager.notify(NOTIF_ID, builder.build());
    }

    public static void scheduleTripNotification(Context context, long triggerAtMillis, String tripName) {
        Intent intent = new Intent(context, NotificationHelper.class);
        intent.putExtra("tripName", tripName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }
} 