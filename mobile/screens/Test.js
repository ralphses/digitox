import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import PushNotification from 'react-native-push-notification';
import BackgroundFetch from 'react-native-background-fetch';
import AsyncStorage from '@react-native-async-storage/async-storage';

const ScreenTimeTracker = () => {
  const [activeTime, setActiveTime] = useState(0);

  // Notification Configuration
  useEffect(() => {
    PushNotification.configure({
      onNotification: function (notification) {
        console.log('Notification:', notification);
      },
      requestPermissions: true,
    });
  }, []);

  // Fetch active time on component mount
  useEffect(() => {
    const fetchActiveTime = async () => {
      const time = await AsyncStorage.getItem('activeTime');
      setActiveTime(time ? parseInt(time) : 0);
    };

    fetchActiveTime();

    // Set up background task
    const configureBackgroundFetch = async () => {
      const onEvent = async (taskId) => {
        let activeTime = parseInt((await AsyncStorage.getItem('activeTime')) || '0');
        activeTime += 900; // Increment by 15 minutes (900 seconds)
        await AsyncStorage.setItem('activeTime', activeTime.toString());
        setActiveTime(activeTime);

        // Trigger a push notification every hour (3600 seconds)
        if (activeTime % 3600 === 0) {
          PushNotification.localNotification({
            message: `You have been active for ${activeTime / 60} minutes! Keep going!`,
            title: "Activity Update",
            largeIcon: "ic_launcher",
            smallIcon: "ic_notification",
          });
        }

        BackgroundFetch.finish(taskId);
      };

      const onTimeout = (taskId) => {
        console.log('Background Fetch task timed out:', taskId);
        BackgroundFetch.finish(taskId);
      };

      // Configure Background Fetch
      BackgroundFetch.configure(
        {
          minimumFetchInterval: 15, // Fetch interval in minutes
          stopOnTerminate: false, // Keep running after app is terminated
          startOnBoot: true, // Start background task on boot
          enableHeadless: true, // Enable headless mode
        },
        onEvent,
        onTimeout
      );
    };

    configureBackgroundFetch();
    BackgroundFetch.start(); // Start background task

    return () => {
      BackgroundFetch.stop(); // Stop background task on unmount
    };
  }, []);

  // Start background task manually
  const startTracking = () => {
    BackgroundFetch.start()
      .then(() => {
        console.log('Background task started');
      })
      .catch((error) => {
        console.error('Failed to start background task:', error);
      });
  };

  // Stop background task
  const stopTracking = () => {
    BackgroundFetch.stop()
      .then(() => {
        console.log('Background task stopped');
      })
      .catch((error) => {
        console.error('Failed to stop background task:', error);
      });
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Screen Time Tracker</Text>
      <Text style={styles.time}>Active Screen Time: {activeTime} seconds</Text>
      <View style={styles.buttonContainer}>
        <Button title="Start Tracking" onPress={startTracking} color="#4CAF50" />
        <Button title="Stop Tracking" onPress={stopTracking} color="#F44336" />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f4f4f4',
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 16,
    color: '#333',
  },
  time: {
    fontSize: 18,
    marginBottom: 20,
    color: '#555',
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '80%',
  },
});

export default ScreenTimeTracker;
