import { NativeModules } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

// Access the UsageTracker module
const { UsageTracker } = NativeModules;

class UsageTrackerService {
  static async getSessions() {
    try {
      const result = await UsageTracker.getSessions();
      console.log("Fetched sessions");
      console.log(result);
      return result.map((session) => ({
        startTime: new Date(session.startTime).toLocaleString(),
        endTime: new Date(session.endTime).toLocaleString(),
        duration: session.duration,
      }));
    } catch (error) {
      console.error('Error fetching sessions:', error);
      throw new Error('Failed to fetch sessions');
    }
  }

  // Fetch and update the total duration with only the latest session
  static async getAndUpdateDuration() {
    const storedDuration = await AsyncStorage.getItem('totalDuration');
    const currentTime = await UsageTracker.getSessions();

    let newDuration = 0;

    // If there are sessions available, take the latest session's duration
    if (currentTime.length > 0) {
      const latestSession = currentTime[currentTime.length - 1]; // Get the latest session
      newDuration = latestSession.duration;
    }

    // Retrieve stored duration (default to 0 if not found)
    let totalDuration = storedDuration ? parseInt(storedDuration) : 0;
    
    // Add the new duration to the stored total duration
    totalDuration += newDuration;

    // Save the new total duration in AsyncStorage
    await AsyncStorage.setItem('totalDuration', totalDuration.toString());

    // Return the updated total duration
    return totalDuration;
  }

  // Format duration in hours, minutes, and seconds
  static formatDuration(duration) {
    const hours = Math.floor(duration / (1000 * 60 * 60));
    const minutes = Math.floor((duration % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((duration % (1000 * 60)) / 1000);

    if (hours > 0) {
      return `${hours}hrs ${minutes}mins ${seconds}s`;
    } else if (minutes > 0) {
      return `${minutes}mins ${seconds}s`;
    } else {
      return `${seconds}s`;
    }
  }

  // Function to get the current formatted duration from AsyncStorage
  static async getFormattedDuration() {
    const storedDuration = await AsyncStorage.getItem('totalDuration');
    const durationInMs = storedDuration ? parseInt(storedDuration) : "0";
    return this.formatDuration(durationInMs);
  }

  // Send duration to the backend when a new day starts
  static async checkNewDayAndSendDuration() {
    const storedDuration = await AsyncStorage.getItem('totalDuration');
    const lastStoredDate = await AsyncStorage.getItem('lastStoredDate');
    const currentDate = new Date().toISOString().split('T')[0]; // Get current date in YYYY-MM-DD format

    // If it's a new day, send the stored duration to the backend and reset
    if (lastStoredDate !== currentDate) {
      if (storedDuration) {
        const duration = parseInt(storedDuration);
        await this.sendDurationToBackend(duration);
        await AsyncStorage.setItem('totalDuration', '0'); // Reset stored duration
        await AsyncStorage.setItem('lastStoredDate', currentDate); // Update last stored date
      }
    }
  }

  // Send the stored duration to the backend
  static async sendDurationToBackend(duration) {
    try {
      const response = await fetch('https://digitox-app.up.railway.app/submit-duration', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ duration }), // Send duration in ms
      });
      const data = await response.json();
      console.log('Duration sent successfully:', data);
    } catch (error) {
      console.error('Failed to send duration to backend:', error);
    }
  }
}

export default UsageTrackerService;
