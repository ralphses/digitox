import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, ActivityIndicator, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable'; // For animations
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function RewardScreen() {
    const navigation = useNavigation();
    const [loading, setLoading] = useState(true);
    const [userData, setUserData] = useState(null);

    // Rewards based on user points
    const rewards = [
        { id: 1, label: 'Free Ebook', pointsRequired: 100, progress: 40, claimed: false },
        { id: 2, label: 'Discount Coupon', pointsRequired: 250, progress: 60, claimed: false },
        { id: 3, label: 'Exclusive Webinar Access', pointsRequired: 500, progress: 20, claimed: false },
        { id: 4, label: 'Gift Card', pointsRequired: 1000, progress: 80, claimed: true },
    ];

    useEffect(() => {
        const fetchData = async () => {
            try {
                const storedUser = await AsyncStorage.getItem('user');
                if (storedUser) {
                    setUserData(JSON.parse(storedUser));
                    setLoading(false);  // Stop loading once data is fetched
                } else {
                    navigation.navigate("Login");
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false); // Stop loading even in case of error
            }
        };

        fetchData();
    }, []);  // Empty array ensures this runs once when the component mounts

    const claimReward = (rewardId) => {
        Alert.alert('Claim Reward', `You have claimed the reward: ${rewardId}`);
        // Add logic to update the user's progress and handle reward claiming
    };

    if (loading) {
        return (
            <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#00796B" />
                <Text style={styles.loadingText}>Loading...</Text>
            </View>
        );
    }

    const totalPoints = userData?.points || 0;  // Assuming points are stored in the user data

    return (
        <ScrollView style={styles.container}>
            {/* Header Section */}
            <Animatable.View animation="fadeIn" duration={1000} style={styles.header}>
                <Text style={styles.title}>Your Rewards</Text>
                <View style={styles.pointsContainer}>
                    <Text style={styles.pointsText}>Current Points: {totalPoints}</Text>
                </View>
            </Animatable.View>

            {/* Rewards Section */}
            <View style={styles.rewards}>
                <Text style={styles.sectionTitle}>Available Rewards</Text>
                {rewards.map((reward, index) => (
                    <Animatable.View key={index} animation="zoomIn" delay={500} style={styles.rewardCard}>
                        <Text style={styles.rewardLabel}>{reward.label}</Text>
                        <View style={styles.rewardProgressContainer}>
                            <View
                                style={[styles.rewardProgressBar, { width: `${reward.progress}%` }]}
                            />
                        </View>
                        <Text style={styles.rewardProgressText}>
                            {reward.claimed ? 'Claimed' : `Points Needed: ${reward.pointsRequired} (Progress: ${reward.progress}%)`}
                        </Text>
                        <TouchableOpacity
                            style={[styles.claimButton, totalPoints >= reward.pointsRequired && !reward.claimed ? styles.claimButtonActive : styles.claimButtonDisabled]}
                            onPress={() => totalPoints >= reward.pointsRequired && !reward.claimed ? claimReward(reward.id) : null}
                            disabled={totalPoints < reward.pointsRequired || reward.claimed}
                        >
                            <Text style={styles.claimButtonText}>
                                {reward.claimed ? 'Reward Claimed' : totalPoints >= reward.pointsRequired ? 'Claim Reward' : 'Insufficient Points'}
                            </Text>
                        </TouchableOpacity>
                    </Animatable.View>
                ))}
            </View>

            {/* Footer Section */}
            <View style={styles.footer}>
                <Text style={styles.footerText}>Earn points by completing challenges and tasks!</Text>
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
    pointsContainer: {
        backgroundColor: '#00796B',
        paddingVertical: 8,
        paddingHorizontal: 15,
        borderRadius: 20,
    },
    pointsText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    rewards: {
        marginBottom: 20,
    },
    sectionTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#00796B',
        marginBottom: 10,
    },
    rewardCard: {
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        padding: 15,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 5,
    },
    rewardLabel: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#00796B',
    },
    rewardProgressContainer: {
        width: '100%',
        height: 10,
        backgroundColor: '#E0E0E0',
        borderRadius: 5,
        marginVertical: 5,
    },
    rewardProgressBar: {
        height: 10,
        backgroundColor: '#00796B',
        borderRadius: 5,
    },
    rewardProgressText: {
        fontSize: 14,
        color: '#333',
    },
    claimButton: {
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        alignItems: 'center',
        marginTop: 10,
    },
    claimButtonActive: {
        backgroundColor: '#00796B',
    },
    claimButtonDisabled: {
        backgroundColor: '#B0BEC5',
    },
    claimButtonText: {
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
        color: '#FFFFFF',
        fontSize: 14,
    },
});
