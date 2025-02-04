package com.digitoxapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsageTrackerModule extends ReactContextBaseJavaModule implements SensorEventListener {

    private final ReactApplicationContext reactContext;
    private final SessionDao sessionDao;
    private final TimeCountDoa timeCountDao;
    private boolean isDeviceUsed = false;
    private boolean isScreenActive = false;
    private long sessionStartTime;
    private long screenActiveStartTime;
    private long timeElapsed;
    private final ExecutorService executorService;

    public UsageTrackerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        executorService = Executors.newSingleThreadExecutor();

        // Initialize database
        AppDatabase appDatabase = Room.databaseBuilder(reactContext, AppDatabase.class, "session-database").build();
        sessionDao = appDatabase.sessionDao();
        timeCountDao = appDatabase.timeCountDoa();

        // Initialize sensor manager
        SensorManager sensorManager = (SensorManager) reactContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }

        // Register for screen ON/OFF events
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        reactContext.registerReceiver(new ScreenReceiver(), filter);
    }

    @NonNull
    @Override
    public String getName() {
        return "UsageTracker";
    }

    @ReactMethod
    public void getSessions(Promise promise) {
        executorService.execute(() -> {
            try {
                List<SessionEntity> sessions = sessionDao.getAllSessions();
                WritableArray sessionArray = Arguments.createArray();
                for (SessionEntity session : sessions) {
                    WritableMap sessionMap = Arguments.createMap();
                    sessionMap.putDouble("startTime", session.getStartTime());
                    sessionMap.putDouble("endTime", session.getEndTime());
                    sessionMap.putDouble("duration", session.getDuration());
                    sessionArray.pushMap(sessionMap);
                }
                promise.resolve(sessionArray);
            } catch (Exception e) {
                promise.reject("DATABASE_ERROR", "Failed to fetch sessions.", e);
            }
        });
    }

    @ReactMethod
    public void emitScreenActiveTime() {
        new Thread(() -> {
            while (isScreenActive) {
                long elapsedTime = (System.currentTimeMillis() - screenActiveStartTime) / 1000; // Seconds
                timeElapsed = elapsedTime; // Save the elapsed time for inactive state handling
                sendEvent("screenActiveTime", (double) elapsedTime);
                try {
                    Thread.sleep(1000); // Emit every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void handleInactiveState() {
        executorService.execute(() -> {
            try {
                long currentDateMillis = System.currentTimeMillis();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(currentDateMillis));
                TimeCount existingRecord = timeCountDao.getByDate(currentDate);

                if (existingRecord != null) {
                    long updatedDuration = existingRecord.getDuration() + timeElapsed;
                    existingRecord.setDuration(updatedDuration);
                    timeCountDao.update(existingRecord);
                } else {
                    TimeCount newRecord = new TimeCount(currentDate, timeElapsed);
                    timeCountDao.insert(newRecord);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @ReactMethod
    public void getTimeCountForDate(String date, Promise promise) {
        executorService.execute(() -> {
            try {
                TimeCount record = timeCountDao.getByDate(date);
                if (record != null) {
                    WritableMap timeCountMap = Arguments.createMap();
                    timeCountMap.putString("date", record.getDate());
                    timeCountMap.putDouble("duration", record.getDuration());
                    promise.resolve(timeCountMap);
                } else {
                    promise.reject("NOT_FOUND", "No record found for the given date.");
                }
            } catch (Exception e) {
                promise.reject("DATABASE_ERROR", "Failed to fetch time count.", e);
            }
        });
    }

    private void sendEvent(String eventName, Object data) {
        if (data instanceof Long) {
            data = ((Long) data).doubleValue();
        }
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, data);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            boolean isPhoneMoving = Math.abs(event.values[0]) > 1 || Math.abs(event.values[1]) > 1 || Math.abs(event.values[2]) > 1;
            if (isPhoneMoving && !isDeviceUsed) {
                startNewSession();
            } else if (!isPhoneMoving && isDeviceUsed) {
                endCurrentSession();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                screenActiveStartTime = System.currentTimeMillis();
                isScreenActive = true;
                emitScreenActiveTime();
            } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                isScreenActive = false;
                handleInactiveState();
            }
        }
    }

    private void startNewSession() {
        sessionStartTime = System.currentTimeMillis();
        isDeviceUsed = true;
        sendEvent("sessionStarted", (double) sessionStartTime);
    }

    private void endCurrentSession() {
        isDeviceUsed = false;
        long sessionEndTime = System.currentTimeMillis();
        executorService.execute(() -> {
            SessionEntity session = new SessionEntity(sessionStartTime, sessionEndTime);
            sessionDao.insert(session);
            sendEvent("sessionEnded", (double) session.getDuration());
        });
    }
}
