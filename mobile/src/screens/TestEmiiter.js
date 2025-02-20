// import React, { useEffect, useState } from 'react';
// import { View, Text, StyleSheet, Alert, AppState } from 'react-native';
// import { NativeEventEmitter, NativeModules } from 'react-native';

// const { UsageTracker } = NativeModules;

// const ScreenActiveTime = () => {
//   const [activeTime, setActiveTime] = useState(0);
//   const [timeCountForDate, setTimeCountForDate] = useState(null);
//   const [errorMessage, setErrorMessage] = useState('');
//   const [appState, setAppState] = useState(AppState.currentState);

//   // Function to fetch time count for a specific date
//   const fetchTimeCountForDate = (date) => {
//     UsageTracker.getTimeCountForDate(date)
//       .then((result) => {
//         if (result) {
//           setTimeCountForDate(result.duration);
//         } else {
//           setTimeCountForDate(0);
//         }
//       })
//       .catch((error) => {
//         setErrorMessage(`Error: ${error.message}`);
//         Alert.alert('Error', `Failed to fetch time count for ${date}: ${error.message}`);
//       });
//   };

//   useEffect(() => {
//     const eventEmitter = new NativeEventEmitter(UsageTracker);

//     // Listen for screenActiveTime events
//     const subscription = eventEmitter.addListener('screenActiveTime', (time) => {
//       setActiveTime(time);
//     });

//     // Fetch the time count for a specific date when app goes to foreground
//     const date = '2025-01-04'; // Replace with the desired date
//     if (appState === 'active') {
//       fetchTimeCountForDate(date);
//     }

//     // Handle app state changes
//     const appStateSubscription = AppState.addEventListener('change', (nextAppState) => {
//       setAppState(nextAppState);

//       if (nextAppState === 'active') {
//         fetchTimeCountForDate(date);
//       }
//     });

//     return () => {
//       subscription.remove();
//       appStateSubscription.remove();
//     };
//   }, [appState]);

//   return (
//     <View style={styles.container}>
//       <Text style={styles.title}>Screen Active Time</Text>
//       <Text style={styles.time}>{activeTime} seconds</Text>

//       <View style={styles.dateContainer}>
//         <Text style={styles.dateTitle}>Time Count for Date</Text>
//         {timeCountForDate !== null ? (
//           <Text style={styles.date}>{`${timeCountForDate} seconds`}</Text>
//         ) : errorMessage ? (
//           <Text style={styles.error}>{errorMessage}</Text>
//         ) : (
//           <Text style={styles.loading}>Loading...</Text>
//         )}
//       </View>
//     </View>
//   );
// };

// const styles = StyleSheet.create({
//   container: {
//     flex: 1,
//     justifyContent: 'center',
//     alignItems: 'center',
//     backgroundColor: '#f5f5f5',
//   },
//   title: {
//     fontSize: 24,
//     fontWeight: 'bold',
//     marginBottom: 20,
//   },
//   time: {
//     fontSize: 48,
//     fontWeight: 'bold',
//     color: '#007BFF',
//   },
//   dateContainer: {
//     marginTop: 30,
//     alignItems: 'center',
//   },
//   dateTitle: {
//     fontSize: 20,
//     fontWeight: 'bold',
//     marginBottom: 10,
//   },
//   date: {
//     fontSize: 36,
//     color: '#FF6347',
//   },
//   error: {
//     fontSize: 18,
//     color: 'red',
//   },
//   loading: {
//     fontSize: 18,
//     color: '#555',
//   },
// });

// export default ScreenActiveTime;
