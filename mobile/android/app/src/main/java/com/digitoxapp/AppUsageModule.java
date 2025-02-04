package com.digitoxapp;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import androidx.room.Room;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.List;

public class AppUsageModule extends ReactContextBaseJavaModule {

    private final AppDatabase appUsageDatabase;

    public AppUsageModule(ReactApplicationContext reactContext) {
        super(reactContext);
        appUsageDatabase = Room.databaseBuilder(
                reactContext.getApplicationContext(),
                AppDatabase.class,
                "session-database"
        ).build();
    }

    @Override
    public String getName() {
        return "AppUsageModule";
    }

    @ReactMethod
    public void trackAppUsage(Callback successCallback, Callback errorCallback) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                UsageStatsManager usageStatsManager = (UsageStatsManager) getReactApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
                long currentTime = System.currentTimeMillis();
                long oneHourAgo = currentTime - (1000 * 60 * 60); // 1 hour range

                // Query usage stats for the last 1 hour
                List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, oneHourAgo, currentTime);

                if (usageStatsList != null && !usageStatsList.isEmpty()) {
                    for (UsageStats usageStats : usageStatsList) {
                        // Check if the app is in the foreground (actively used)
                        long startTime = usageStats.getLastTimeUsed();
                        long endTime = currentTime;  // Track until the current time
                        long totalDuration = usageStats.getTotalTimeInForeground();

                        // Create a new session for the app usage
                        AppUsageSession session = new AppUsageSession(
                                usageStats.getPackageName(),
                                startTime,
                                endTime,
                                totalDuration
                        );

                        // Insert the session into the database
                        new Thread(() -> appUsageDatabase.appUsageSessionDao().insertSession(session)).start();
                    }
                }

                successCallback.invoke("App usage tracked successfully");
            } else {
                errorCallback.invoke("Android version is lower than Lollipop, cannot track app usage.");
            }
        } catch (Exception e) {
            errorCallback.invoke(e.getMessage());
        }
    }
}
