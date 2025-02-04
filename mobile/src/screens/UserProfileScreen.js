import AsyncStorage from '@react-native-async-storage/async-storage';
import React, { useState, useEffect } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet, ScrollView } from 'react-native';

const UserProfileScreen = ({ navigation }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Simulate fetching user data
    const fetchUserData = () => {
      const dummyUserData = {
        name: "John Doe",
        email: "john.doe@example.com",
        phone_number: "+2348030000000",
        profilePicture: "https://via.placeholder.com/150",
        level: "Intermediate",
        points: 350,
        milestone_achievements: ["M001", "M002", "M003"]
      };
      setUser(dummyUserData);
    };

    fetchUserData();
  }, []);

  const handleLogout = () => {
    AsyncStorage.removeItem("user");
    navigation.replace('Login'); // Navigate to login screen
  };

  if (!user) {
    return (
      <View style={styles.loadingContainer}>
        <Text style={styles.loadingText}>Loading...</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Image source={{ uri: user.profilePicture }} style={styles.profilePicture} />
        <Text style={styles.name}>{user.name}</Text>
        <Text style={styles.email}>{user.email}</Text>
        <Text style={styles.phoneNumber}>{user.phone_number}</Text>
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
          <Text style={styles.infoLabel}>Milestones Achieved:</Text>
          <Text style={styles.infoValue}>{user.milestone_achievements.length}</Text>
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
    padding: 20,
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
  header: {
    alignItems: 'center',
    marginBottom: 30,
  },
  profilePicture: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 15,
  },
  name: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#00796B',
  },
  email: {
    fontSize: 16,
    color: '#555',
  },
  phoneNumber: {
    fontSize: 16,
    color: '#555',
    marginTop: 5,
  },
  infoContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 20,
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
  },
  logoutButtonText: {
    fontSize: 16,
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
});

export default UserProfileScreen;
