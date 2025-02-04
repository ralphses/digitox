import React, { useEffect, useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, StyleSheet } from 'react-native';
import HomeScreen from './src/screens/HomeScreen';
import Onboarding from './src/screens/Onboarding';
import CreateAccount from './src/screens/RegisterScreen';
import Login from './src/screens/LoginScreen';
import UserProfileScreen from './src/screens/UserProfileScreen';
import UsageTrackerService from './src/core/UsageTracker';
import MilestoneScreen from './src/screens/MilestoneScreen';
import RewardsScreen from './src/screens/RewardsScreen';
import CommunityScreen from './src/screens/CommunityScreen';
import UserReportScreen from './src/screens/ReportScreen';
import CreateMilestoneScreen from './src/screens/CreateMilestone';

const Stack = createNativeStackNavigator();

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(null);

  useEffect(() => {
    const checkLoginStatus = async () => {
      try {
        const user = await AsyncStorage.getItem('user');
        setIsLoggedIn(!!user);
      } catch (error) {
        console.error('Error checking login status', error);
        setIsLoggedIn(false); // Handle error by treating as logged out
      }
    };

    const initializeApp = async () => {
      await UsageTrackerService.checkNewDayAndSendDuration();
      await UsageTrackerService.getSessions();
      const totalDuration = await UsageTrackerService.getAndUpdateDuration();
      console.log(totalDuration);
    };

    initializeApp();
    checkLoginStatus();
  }, []);

  // Wait until `isLoggedIn` state is determined
  if (isLoggedIn === null) {
    return <Text style={styles.loadingText}>Loading...</Text>;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName={isLoggedIn ? 'Home' : 'Onboarding'}>
          {/* Common screens */}
          <Stack.Screen
            name="Home"
            component={HomeScreen}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="Profile"
            component={UserProfileScreen}
            options={{ title: 'User Profile' }}
          />
          <Stack.Screen
            name="RewardScreen"
            component={RewardsScreen}
            options={{ title: 'Rewards' }}
          />

          <Stack.Screen
            name="DailyMilestone"
            component={MilestoneScreen}
            options={{ title: 'Daily Milestone' }}
          />
          <Stack.Screen
            name="Reports"
            component={UserReportScreen}
            options={{ title: 'Reports' }}
          />

          <Stack.Screen
            name="Community"
            component={CommunityScreen}
            options={{ title: 'Community' }}
          />

          {/* Screens for users who are not logged in */}
          <Stack.Screen
            name="Onboarding"
            component={Onboarding}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="CreateAccount"
            component={CreateAccount}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="CreateMilestone"
            component={CreateMilestoneScreen}
            options={{ title: "Add New Milestone" }}
          />
          <Stack.Screen
            name="Login"
            component={Login}
            options={{ headerShown: false }}
          />
        </Stack.Navigator>
      </NavigationContainer>
    </GestureHandlerRootView>
  );
}

const styles = StyleSheet.create({
  loadingText: {
    flex: 1,
    textAlign: 'center',
    textAlignVertical: 'center',
    fontSize: 18,
  },
});
