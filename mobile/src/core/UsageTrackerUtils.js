// UsageTrackerUtils.js

import { NativeEventEmitter, NativeModules, AppState } from 'react-native';

const { UsageTracker } = NativeModules;

class UsageTrackerUtils {
  constructor() {
    this.eventEmitter = new NativeEventEmitter(UsageTracker);
    this.appStateSubscription = null;
    this.state = {
      activeTime: 0,
      timeCountForDate: null,
      errorMessage: '',
      appState: AppState.currentState,
    };
  }

  // Method to fetch time count for a specific date
  fetchTimeCountForDate(date, updateStateCallback) {
    UsageTracker.getTimeCountForDate(date)
      .then((result) => {
        if (result) {
          updateStateCallback({ timeCountForDate: result.duration, errorMessage: '' });
        } else {
          updateStateCallback({ timeCountForDate: 0, errorMessage: '' });
        }
      })
      .catch((error) => {
        updateStateCallback({ errorMessage: `Error: ${error.message}`, timeCountForDate: null });
      });
  }

  // Method to handle screen active time events
  listenForScreenActiveTime(updateStateCallback) {
    const subscription = this.eventEmitter.addListener('screenActiveTime', (time) => {
      updateStateCallback({ activeTime: time });
    });
    return subscription;
  }

  // Method to handle app state changes (background/foreground)
  handleAppStateChange(updateStateCallback) {
    this.appStateSubscription = AppState.addEventListener('change', (nextAppState) => {
      this.state.appState = nextAppState;
      if (nextAppState === 'active') {
        // Get today's date in YYYY-MM-DD format
        const today = new Date().toISOString().split('T')[0];
        // Fetch time count for today's date
        this.fetchTimeCountForDate(today, updateStateCallback);
      }
    });
  }
  

  // Cleanup method to remove listeners
  cleanup() {
    if (this.appStateSubscription) {
      this.appStateSubscription.remove();
    }
  }
}

export default new UsageTrackerUtils();
