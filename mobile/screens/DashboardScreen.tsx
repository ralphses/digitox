import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable'; // For animations
import AsyncStorage from '@react-native-async-storage/async-storage'; // Import AsyncStorage

export default function HomeScreen() {
  const navigation = useNavigation();
  const [data, setData] = useState({
    profileImage: 'https://via.placeholder.com/150',
    bannerImage: 'https://via.placeholder.com/400x200',
    stats: [
      { label: 'Screen Time', value: '0s' },
      { label: 'Sleep Duration', value: '0s' },
      { label: 'Sleep Quality', value: '0%' }
    ],
    features: [
      { label: 'Screen Time', icon: 'time-outline', route: 'ScreenTimeLimit' },
      { label: 'Sleep Tracking', icon: 'bed-outline', route: 'SleepTracking' },
      { label: 'Mindful Mode', icon: 'leaf-outline', route: 'MindfulMode' },
      { label: 'Rewards', icon: 'gift-outline', route: 'Rewards' }
    ],
    communityHighlights: [
      { message: '🌟 *Anna* reduced her screen time by 2h today!' },
      { message: '🏆 *John* is leading the weekly leaderboard!' }
    ],
    name: "User",
    email: "user@example.com"
  });

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const userSession = await AsyncStorage.getItem('user');
        if (userSession) {
          const userData = JSON.parse(userSession);

          // Check if the user data and nested properties exist before accessing them
          if (userData && userData.user) {
            setData(prevData => ({
              ...prevData,
              profileImage: userData.user.profileImage || prevData.profileImage,
              bannerImage: userData.user.bannerImage || prevData.bannerImage,
              name: userData.user.name,
              email: userData.user.email,
              stats: [
                { label: 'Screen Time', value: userData.stats?.screenTime || '0s' },
                { label: 'Sleep Duration', value: userData.stats?.sleepDuration || '0s' },
                { label: 'Sleep Quality', value: userData.stats?.sleepQuality || '0%' }
              ]
            }));
          } else {
            Alert.alert('Error', 'User data is incomplete or malformed.');
            navigation.navigate('Login');
          }
        } else {
          Alert.alert('No user found', 'You need to log in to continue.');
          navigation.navigate('Login');
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
        Alert.alert('Error', 'An error occurred while fetching user data.');
      }
    };

    fetchUserData();
  }, [navigation]);

  const handleProfileClick = () => {
    navigation.navigate('Profile'); // Navigate to Profile screen
  };

  return (
    <ScrollView style={styles.container}>
      {/* Header Section */}
      <Animatable.View animation="fadeIn" duration={1000} style={styles.header}>
        <Text style={styles.title}>Welcome {data.name}!</Text>
        <TouchableOpacity onPress={handleProfileClick}>
          <Image
            source={{ uri: data.profileImage }}
            style={styles.profileImage}
            onError={() => console.log('Error loading image')}
          />
        </TouchableOpacity>
      </Animatable.View>

      {/* Motivational Banner */}
      <Animatable.View animation="slideInDown" duration={1200} style={styles.banner}>
        <Text style={styles.bannerText}>
          "Unplug, Relax, and Reclaim Your Time."
        </Text>
        <Image source={{ uri: data.bannerImage }} style={styles.bannerImage} />
      </Animatable.View>

      {/* Daily Highlights Section */}
      <View style={styles.highlights}>
        <Text style={styles.sectionTitle}>Today's Highlights</Text>
        <View style={styles.statsRow}>
          {data.stats.map((stat, index) => (
            <Animatable.View animation="zoomIn" delay={500} key={index} style={styles.statCard}>
              <Text style={styles.statValue}>{stat.value}</Text>
              <Text style={styles.statLabel}>{stat.label}</Text>
            </Animatable.View>
          ))}
        </View>
      </View>

      {/* Core Features Section */}
      <Animatable.View animation="fadeIn" delay={600} style={styles.features}>
        <Text style={styles.sectionTitle}>Explore Features</Text>
        <View style={styles.featureRow}>
          {data.features.slice(0, 2).map((feature, index) => (
            <TouchableOpacity
              key={index}
              style={styles.featureCard}
              onPress={() => navigation.navigate(feature.route)}
            >
              <Animatable.View animation="pulse" iterationCount="infinite" style={styles.iconContainer}>
                {/* <Icon name={feature.icon} size={60} color="#00796B" /> */}
              </Animatable.View>
              <Text style={styles.featureText}>{feature.label}</Text>
            </TouchableOpacity>
          ))}
        </View>
        <View style={styles.featureRow}>
          {data.features.slice(2).map((feature, index) => (
            <TouchableOpacity
              key={index}
              style={styles.featureCard}
              onPress={() => feature.route && navigation.navigate(feature.route)}
            >
              <Animatable.View animation="pulse" iterationCount="infinite" style={styles.iconContainer}>
                {/* <Icon name={feature.icon} size={60} color="#00796B" /> */}
              </Animatable.View>
              <Text style={styles.featureText}>{feature.label}</Text>
            </TouchableOpacity>
          ))}
        </View>
      </Animatable.View>

      {/* Community Highlights Section */}
      <Animatable.View animation="fadeInUp" delay={800} style={styles.community}>
        <Text style={styles.sectionTitle}>Community Highlights</Text>
        {data.communityHighlights.map((highlight, index) => (
          <Text key={index} style={styles.communityText}>
            {highlight.message}
          </Text>
        ))}
        <TouchableOpacity
          style={styles.joinChallengeButton}
          onPress={() => navigation.navigate('Community')}
        >
          <Text style={styles.joinChallengeText}>Join Today's Challenge</Text>
        </TouchableOpacity>
      </Animatable.View>

      {/* Footer Section */}
      <Animatable.View animation="fadeIn" delay={1000} style={styles.footer}>
        <Text style={styles.footerText}>Take small steps toward big change.</Text>
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
    backgroundColor: '#E3F2FD',
    borderRadius: 15,
    padding: 20,
    marginBottom: 20,
    alignItems: 'center',
  },
  bannerText: {
    fontSize: 18,
    fontStyle: 'italic',
    color: '#0277BD',
    marginBottom: 10,
  },
  bannerImage: {
    width: '100%',
    height: 150,
    borderRadius: 10,
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
    fontSize: 14,
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
    marginBottom: 10,
  },
  featureText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#00796B',
  },
  community: {
    marginBottom: 20,
  },
  communityText: {
    fontSize: 16,
    color: '#00796B',
    marginBottom: 5,
  },
  joinChallengeButton: {
    backgroundColor: '#00796B',
    paddingVertical: 10,
    paddingHorizontal: 30,
    borderRadius: 20,
    marginTop: 10,
    alignItems: 'center',
  },
  joinChallengeText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  footer: {
    marginTop: 20,
    alignItems: 'center',
    paddingBottom: 10,
  },
  footerText: {
    fontSize: 16,
    color: '#00796B',
    fontStyle: 'italic',
  }
});
