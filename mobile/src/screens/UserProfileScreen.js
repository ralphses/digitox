import AsyncStorage from '@react-native-async-storage/async-storage';
import React, { useState, useEffect } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet, ScrollView, Alert } from 'react-native';
import axios from 'axios';

const UserProfileScreen = ({ navigation }) => {
  const [user, setUser] = useState(null);
  const [userData, setUserData] = useState(null);
  const [stats, setStats] = useState(null);
  const [milestones, setMilestones] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const storedUser = await AsyncStorage.getItem('user');
        if (!storedUser) {
          navigation.navigate("Login");
          return;
        }
  
        const parsedUser = JSON.parse(storedUser);
        setUserData(parsedUser);
  
        // Ensure that parsedUser contains user data before proceeding
        if (!parsedUser?.user?.email) {
          throw new Error("User data is incomplete.");
        }
  
        const url = `https://digitox-app.up.railway.app/api/v1/auth/profile/${parsedUser.user.email}`;
        console.log(url);
        
        const response = await axios.get(url);
        console.log(response.status);
        
        if (response.data.success) {
          const { user, stats, milestones } = response.data.data;
  
          setUser(user);
          setStats(stats);
          setMilestones(milestones);
        } else {
          Alert.alert("Error", "Failed to fetch user data.");
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false); // Ensure loading is stopped in all cases
      }
    };
  
    fetchData();
  }, []);
  

  const handleLogout = async () => {
    await AsyncStorage.removeItem("user");
    navigation.replace('Login'); // Navigate to login screen
  };

  if (!user || !stats) {
    return (
      <View style={styles.loadingContainer}>
        <Text style={styles.loadingText}>Loading...</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <Image source={{ uri: user.bannerImage }} style={styles.bannerImage} />
      <View style={styles.header}>
        <Image source={{ uri: user.profileImage }} style={styles.profilePicture} />
        <Text style={styles.name}>{user.name}</Text>
        <Text style={styles.email}>{user.email}</Text>
        <Text style={styles.role}>{user.role}</Text>
      </View>

      <View style={styles.infoContainer}>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Level:</Text>
          <Text style={styles.infoValue}>{user.level}</Text>
        </View>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Total Points:</Text>
          <Text style={styles.infoValue}>{user.points}</Text>
        </View>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Screen Time:</Text>
          <Text style={styles.infoValue}>{stats.screenTime}</Text>
        </View>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Sleep Duration:</Text>
          <Text style={styles.infoValue}>{stats.sleepDuration}</Text>
        </View>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Sleep Quality:</Text>
          <Text style={styles.infoValue}>{stats.sleepQuality}</Text>
        </View>
        <View style={styles.infoRow}>
          <Text style={styles.infoLabel}>Milestones Achieved:</Text>
          <Text style={styles.infoValue}>{milestones.length}</Text>
        </View>
      </View>

      <TouchableOpacity style={styles.logoutButton} onPress={handleLogout}>
        <Text style={styles.logoutButtonText}>Log Out</Text>
      </TouchableOpacity>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E0F7FA',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  loadingText: {
    fontSize: 20,
    color: '#00796B',
  },
  bannerImage: {
    width: '100%',
    height: 150,
    resizeMode: 'cover',
  },
  header: {
    alignItems: 'center',
    marginVertical: 20,
  },
  profilePicture: {
    width: 100,
    height: 100,
    borderRadius: 50,
    borderWidth: 2,
    borderColor: '#00796B',
  },
  name: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#00796B',
    marginTop: 10,
  },
  email: {
    fontSize: 16,
    color: '#555',
  },
  role: {
    fontSize: 14,
    color: '#888',
    fontStyle: 'italic',
    marginTop: 5,
  },
  infoContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 20,
    marginHorizontal: 15,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
    marginBottom: 20,
  },
  infoRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 10,
  },
  infoLabel: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#00796B',
  },
  infoValue: {
    fontSize: 16,
    color: '#555',
  },
  logoutButton: {
    backgroundColor: '#00796B',
    padding: 15,
    borderRadius: 5,
    alignItems: 'center',
    marginHorizontal: 15,
  },
  logoutButtonText: {
    fontSize: 16,
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
});

export default UserProfileScreen;
