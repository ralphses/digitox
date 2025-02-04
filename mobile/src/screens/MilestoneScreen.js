import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image, ActivityIndicator, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable'; // For animations
import AsyncStorage from '@react-native-async-storage/async-storage';


export default function MilestoneScreen() {
    const navigation = useNavigation();
    const [loading, setLoading] = useState(true);
    const [userData, setUserData] = useState(null);
    const [milestones, setMilestones] = useState(null);
    const [communitySuggestions, setCommunitySuggestions] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const storedUser = await AsyncStorage.getItem('user');
    
                if (storedUser) {
                    const parsedUser = JSON.parse(storedUser);
                    setUserData(parsedUser);
    
                    // Fetch milestones and community suggestions after user data is set
                    fetchUserMilestones(parsedUser.user.email);
                    fetchCommunitySuggestions(parsedUser.user.email);
                } else {
                    navigation.navigate("Login");
                }
            } catch (error) {
                console.error('Error fetching user data:', error);
                setLoading(false);
            }
        };
    
        const fetchUserMilestones = async (email) => {
            try {
                const response = await fetch(`https://9e19-102-89-33-27.ngrok-free.app/api/v1/milestone/user/${email}`, {
                    method: "GET",
                });
    
                const responseData = await response.json();
    
                if (response.ok && responseData.success) {
                    setMilestones(responseData.data);
                } else {
                    Alert.alert("Error", responseData.message || "Failed to fetch milestones");
                }
            } catch (error) {
                console.error('Error fetching milestones:', error);
                Alert.alert(
                    "Error",
                    "Unable to fetch milestones. Please try again later.",
                    [
                        {
                            text: "OK",
                            onPress: () => navigation.goBack(),
                        },
                    ]
                );
            } finally {
                setLoading(false);
            }
        };
    
        const fetchCommunitySuggestions = async (email) => {
            try {
                const response = await fetch(`https://9e19-102-89-33-27.ngrok-free.app/api/v1/milestone/community/${email}`, {
                    method: "GET",
                });
    
                const responseData = await response.json();
    
                if (response.ok && responseData.success) {
                    setCommunitySuggestions(responseData.data);
                } else {
                    Alert.alert("Error", responseData.message || "Failed to fetch community suggestions");
                }
            } catch (error) {
                console.error('Error fetching community suggestions:', error);
                Alert.alert(
                    "Error",
                    "Unable to fetch community suggestions. Please try again later.",
                    [
                        {
                            text: "OK",
                            onPress: () => navigation.goBack(),
                        },
                    ]
                );
            } finally {
                setLoading(false);
            }
        };
    
        fetchData();
    }, [navigation]);
    

    const handleCreateMilestone = () => {
        navigation.navigate("CreateMilestone")
    };

    const joinCommunityChallenge = async (suggestionId) => {
        const userEmail = userData.user.email;
        try {
            const payload = {
                userEmail: userEmail,
                milestoneId: suggestionId,
            };

            const response = await fetch("https://9e19-102-89-33-27.ngrok-free.app/api/v1/milestone/community", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            const responseData = await response.json();

            if (response.ok && responseData.success) {
                Alert.alert("Success", "You have joined Successfully", [
                    { text: "OK", onPress: () => navigation.goBack() },
                ]);
            } else {
                Alert.alert("Error", responseData.message || "Failed to create milestone");
            }
        } catch (error) {
            console.error(error);
            Alert.alert("Error", "An error occurred while creating the milestone");
        } finally {
            setLoading(false); // Hide spinner
        }
    };

    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#00796B" />
                <Text style={styles.loadingText}>Loading...</Text>
            </View>
        );
    }

    return (
        <ScrollView style={styles.container}>
            {/* Header Section */}
            <Animatable.View animation="fadeIn" duration={1000} style={styles.header}>
                <Text style={styles.title}>Your Milestones</Text>

                {/* Button to Create New Milestone */}
                <TouchableOpacity onPress={handleCreateMilestone} style={styles.createMilestoneButton}>
                    <Text style={styles.createMilestoneButtonText}>Create New Milestone</Text>
                </TouchableOpacity>
            </Animatable.View>

            {/* Milestones Section */}
            <View style={styles.milestones}>
                <Text style={styles.sectionTitle}>Your Milestones</Text>
                {milestones && milestones.length > 0 ? (
                    milestones.map((milestone) => (
                        <Animatable.View
                            key={milestone.id}
                            animation="zoomIn"
                            delay={500}
                            style={styles.milestoneCard}
                        >
                            <Text style={styles.milestoneLabel}>{milestone.label || 'No Label'}</Text>
                            <Text style={styles.milestoneDate}>Date: {milestone.date}</Text>
                            <View style={styles.milestoneProgressContainer}>
                                <View
                                    style={[styles.milestoneProgressBar, { width: `${milestone.progress}%` }]}
                                />
                            </View>
                            <Text style={styles.milestoneProgressText}>
                                {milestone.completed ? 'Completed' : `Progress: ${milestone.progress}%`}
                            </Text>
                            <Text style={styles.milestoneMaxScreenTime}>
                                Max Screen Time: {milestone.maxScreenTime} hours
                            </Text>
                            <Text style={styles.milestoneType}>Milestone Type: {milestone.type}</Text>
                        </Animatable.View>
                    ))
                ) : (
                    <Text style={styles.noMilestonesText}>No milestones found.</Text>
                )}
            </View>

            {/* Community Suggestions Section */}
            <View style={styles.communitySuggestions}>
                <Text style={styles.sectionTitle}>Community Milestone Suggestions</Text>
                {communitySuggestions.map((suggestion) => (
                    <Animatable.View
                        key={suggestion.id}
                        animation="fadeIn"
                        delay={700}
                        style={styles.suggestionCard}
                    >
                        <Text style={styles.suggestionLabel}>{suggestion.label}</Text>
                        <Text style={styles.suggestionMaxScreenTime}>
                            Maximum Screen Time: {suggestion.maxScreenTime} hours
                        </Text>
                        <TouchableOpacity
                            style={styles.joinButton}
                            onPress={() => joinCommunityChallenge(suggestion.id)}
                        >
                            <Text style={styles.joinButtonText}>Join Challenge</Text>
                        </TouchableOpacity>
                    </Animatable.View>
                ))}
            </View>
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
    createMilestoneButton: {
        backgroundColor: '#00796B',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        alignItems: 'center',
    },
    createMilestoneButtonText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: 'bold',
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
    milestones: {
        marginBottom: 20,
    },
    sectionTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#00796B',
        marginBottom: 10,
    },
    milestoneCard: {
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        padding: 15,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 5,
    },
    milestoneLabel: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#00796B',
    },
    milestoneProgressContainer: {
        width: '100%',
        height: 10,
        backgroundColor: '#E0E0E0',
        borderRadius: 5,
        marginVertical: 5,
    },
    milestoneProgressBar: {
        height: 10,
        backgroundColor: '#00796B',
        borderRadius: 5,
    },
    milestoneProgressText: {
        fontSize: 14,
        color: '#333',
    },
    communitySuggestions: {
        marginBottom: 20,
    },
    suggestionMaxScreenTime: {
        fontSize: 14,
        color: '#666',
        marginTop: 4,
        marginBottom: 8,
    },
    suggestionCard: {
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        padding: 15,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 5,
    },
    suggestionLabel: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#00796B',
    },
    suggestionDescription: {
        fontSize: 14,
        color: '#555',
        marginVertical: 5,
    },
    joinButton: {
        backgroundColor: '#00796B',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        alignItems: 'center',
        marginTop: 10,
    },
    joinButtonText: {
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
});
