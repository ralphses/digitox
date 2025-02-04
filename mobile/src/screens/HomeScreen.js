import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image, ActivityIndicator, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Icon from 'react-native-vector-icons/Ionicons';
import * as Animatable from 'react-native-animatable'; // For animations
import AsyncStorage from '@react-native-async-storage/async-storage';
import { AppState } from 'react-native';
import { NativeEventEmitter, NativeModules } from 'react-native';

const { UsageTracker } = NativeModules;

export default function HomeScreen() {
  const navigation = useNavigation();
  const [loading, setLoading] = useState(true);
  const [communityHighlights, setCommunityHighlights] = useState([]);
  const [userData, setUserData] = useState(null);
  const [activeTime, setActiveTime] = useState(0);  // Active time in seconds
  const [appState, setAppState] = useState(AppState.currentState);
  const [date, setDate] = useState(null);
  const [timeCountForDate, setTimeCountForDate] = useState(0);


  const FEATURES = [
    { icon: 'home-outline', label: 'Daily Milestone', route: 'DailyMilestone' },
    { icon: 'calendar-outline', label: 'My Rewards', route: 'RewardScreen' },
    { icon: 'stats-chart-outline', label: 'Reports', route: 'Reports' },
    { icon: 'people-outline', label: 'Community', route: 'Community' },
  ];


  const fetchTimeCountForDate = (date) => {
    UsageTracker.getTimeCountForDate(date)
      .then((result) => {
        if (result) {
          setTimeCountForDate(result.duration);
          console.log("result");

        } else {
          setTimeCountForDate(10);
        }
      })
      .catch((error) => {
        setErrorMessage(`Error: ${error.message}`);
        Alert.alert('Error', `Failed to fetch time count for`);
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const storedUser = await AsyncStorage.getItem('user');
        if (storedUser) {
          setUserData(JSON.parse(storedUser));
          const response = await fetch('https://your-api.com/community/highlights');
          const data = await response.json();
          setCommunityHighlights(data || []);

          setLoading(false);  // Stop loading once data is fetched
        }
        else {
          navigation.navigate("Login");
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false); // Stop loading even in case of error
      }
    };

    fetchData();

  }, []);  // Empty array ensures this runs once when the component mounts

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(UsageTracker);

    // Listen for screenActiveTime events
    const subscription = eventEmitter.addListener('screenActiveTime', (time) => {
      setActiveTime(time);
    });

    // Fetch the time count for the current date when the app goes to the foreground
    const today = new Date().toISOString().split('T')[0]; // Get today's date in 'YYYY-MM-DD' format
    setDate(today);
    if (appState === 'active') {
      console.log(today);
      
      fetchTimeCountForDate(today);
    }


    // Handle app state changes
    const appStateSubscription = AppState.addEventListener('change', (nextAppState) => {
      setAppState(nextAppState);

      if (nextAppState === 'active') {
        fetchTimeCountForDate(date);
      }
    });

    return () => {
      subscription.remove();
      appStateSubscription.remove();
    };
  }, [appState]);

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#00796B" />
        <Text style={styles.loadingText}>Loading...</Text>
      </View>
    );
  }

  const handleProfileClick = () => {
    navigation.navigate('Profile');  // Navigate to Profile screen
  };

  const highlights = communityHighlights.length > 0 ? communityHighlights : [
    { message: 'Join the challenge and win amazing rewards!' },
    { message: 'Complete your milestones for today and unlock new levels!' },
  ];

  return (
    <ScrollView style={styles.container}>
      {/* Header Section */}
      <Animatable.View animation="fadeIn" duration={1000} style={styles.header}>
        <Text style={styles.title}>Welcome {userData.user.name}!</Text>
        <TouchableOpacity onPress={handleProfileClick}>
          <Icon
            name="person-circle"  // Icon for user profile
            size={50}             // Size of the icon
            color="#00796B"       // Color of the icon
            style={styles.profileIcon}
          />
        </TouchableOpacity>
      </Animatable.View>

      {/* Motivational Banner */}
      <Animatable.View animation="slideInDown" duration={10} style={styles.banner}>
        <Text style={styles.bannerText}>Active Screen Time</Text>
        <Text style={styles.bannerSubText}>{activeTime}s</Text>
        <Text style={styles.bannerLevelText}>Today's Screen Time: {timeCountForDate}</Text>
        <Text style={styles.bannerLevelText}>Level: {userData.user.level}</Text>
      </Animatable.View>

      {/* Daily Highlights Section */}
      <View style={styles.highlights}>
        <Text style={styles.sectionTitle}>Today's Highlights</Text>
        <View style={styles.statsRow}>
          <Animatable.View animation="zoomIn" delay={500} style={styles.statCard}>
            <Text style={styles.statValue}>{userData.user.points}</Text>
            <Text style={styles.statLabel}>Total Points</Text>
          </Animatable.View>
          <Animatable.View animation="zoomIn" delay={500} style={styles.statCard}>
            <Text style={styles.statValue}>{userData.stats.screenTime}</Text>
            <Text style={styles.statLabel}>Milestones Won</Text>
          </Animatable.View>
          <Animatable.View animation="zoomIn" delay={500} style={styles.statCard}>
            <Text style={styles.statValue}>{userData.stats.screenTime}</Text>
            <Text style={styles.statLabel}>Rewards</Text>
          </Animatable.View>
        </View>
      </View>

      {/* Core Features Section */}
      <Animatable.View animation="fadeIn" delay={600} style={styles.features}>
        <Text style={styles.sectionTitle}>Explore Features</Text>
        <View style={styles.featureRow}>
          {FEATURES.slice(0, 2).map((feature, index) => (
            <TouchableOpacity
              key={index}
              style={styles.featureCard}
              onPress={() => navigation.navigate(feature.route)}
            >
              <Animatable.View animation="pulse" iterationCount="infinite" style={styles.iconContainer}>
                <Icon name={feature.icon} size={60} color="#00796B" />
              </Animatable.View>
              <Text style={styles.featureText}>{feature.label}</Text>
            </TouchableOpacity>
          ))}
        </View>
        <View style={styles.featureRow}>
          {FEATURES.slice(2).map((feature, index) => (
            <TouchableOpacity
              key={index}
              style={styles.featureCard}
              onPress={() => navigation.navigate(feature.route)}
            >
              <Animatable.View animation="pulse" iterationCount="infinite" style={styles.iconContainer}>
                <Icon name={feature.icon} size={60} color="#00796B" />
              </Animatable.View>
              <Text style={styles.featureText}>{feature.label}</Text>
            </TouchableOpacity>
          ))}
        </View>
      </Animatable.View>

      {/* Community Highlights Section */}
      <Animatable.View animation="fadeInUp" delay={800} style={styles.community}>
        <Text style={styles.sectionTitle}>Community Highlights</Text>
        {highlights.map((highlight, index) => (
          <Text key={index} style={styles.communityText}>{highlight.message}</Text>
        ))}
        <TouchableOpacity
          style={styles.joinChallengeButton}
          onPress={() => navigation.navigate('Community')}
        >
          <Text style={styles.joinChallengeText}>Join Today's Challenge</Text>
        </TouchableOpacity>
      </Animatable.View>

    </ScrollView>
  );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F0F4F8',
    paddingHorizontal: 20,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F0F4F8',
  },
  loadingText: {
    fontSize: 18,
    marginTop: 10,
    color: '#00796B',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 50,
    marginBottom: 20,
  },
  title: {
    flex: 1,
    fontSize: 24,
    fontWeight: 'bold',
    color: '#00796B',
  },
  profileImage: {
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  banner: {
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#f0f0f0',
    borderRadius: 10,
    margin: 10,
  },
  bannerText: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 10,
    color: '#333',
  },
  bannerSubText: {
    fontSize: 45,
    fontWeight: 'bold',
    color: '#007BFF',
    marginBottom: 5,
  },
  bannerLevelText: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#555',
  },
  highlights: {
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#00796B',
    marginBottom: 10,
  },
  statsRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  statCard: {
    width: '30%',
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 15,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },
  statValue: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#00796B',
  },
  statLabel: {
    fontSize: 12,
    color: '#0277BD',
  },
  features: {
    marginBottom: 20,
  },
  featureRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 10,
  },
  featureCard: {
    width: '45%',
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 15,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },
  iconContainer: {
    marginBottom: 5,
  },
  featureText: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#00796B',
  },
  community: {
    marginBottom: 20,
  },
  communityText: {
    fontSize: 14,
    marginBottom: 10,
    color: '#333',
  },
  joinChallengeButton: {
    backgroundColor: '#00796B',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 5,
    alignItems: 'center',
  },
  joinChallengeText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  footer: {
    marginTop: 20,
    alignItems: 'center',
    paddingVertical: 10,
    backgroundColor: '#00796B',
  },
  footerText: {
    color: '#fff',
    fontSize: 16,
  },
});
