import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, Modal, ActivityIndicator, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable';
import DatePicker from 'react-native-date-picker';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function CreateMilestoneScreen() {
    const navigation = useNavigation();
    const [userData, setUserData] = useState(null);
    const [date, setDate] = useState(new Date());
    const [showDatePicker, setShowDatePicker] = useState(false);
    const [milestoneType, setMilestoneType] = useState('Personal');
    const [maxScreenTime, setMaxScreenTime] = useState('');
    const [label, setLabel] = useState('');
    const [loading, setLoading] = useState(false);

    const handleCreateMilestone = async () => {
        // Validate inputs
        if (!label.trim() || !maxScreenTime.trim() || isNaN(parseInt(maxScreenTime, 10))) {
            Alert.alert("Validation Error", "All fields are required and max screen time must be a valid number.");
            return;
        }

        setLoading(true); // Show spinner

        try {
            const payload = {
                userEmail: userData?.user?.email || '',
                maxScreenTime: parseInt(maxScreenTime, 10),
                label: label.trim(),
                date: date.toISOString().split('T')[0],
                type: milestoneType.toLowerCase(),
            };

            const response = await fetch("https://2c40-102-89-33-27.ngrok-free.app/api/v1/milestone", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            const responseData = await response.json();

            if (response.ok && responseData.success) {
                Alert.alert("Success", "Milestone Created Successfully", [
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

    useEffect(() => {
        const fetchData = async () => {
            try {
                const storedUser = await AsyncStorage.getItem('user');
                if (storedUser) {
                    setUserData(JSON.parse(storedUser));
                } else {
                    navigation.navigate("Login");
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <View style={styles.container}>
            <Animatable.View animation="fadeIn" duration={1000} style={styles.header}>
                <Text style={styles.title}>Create New Milestone</Text>
            </Animatable.View>

            <View style={styles.inputContainer}>
                <Text style={styles.label}>What's this challenge about?</Text>
                <TextInput
                    style={styles.input}
                    value={label}
                    onChangeText={setLabel}
                    placeholder="Give it a description"
                />
            </View>

            <View style={styles.inputContainer}>
                <Text style={styles.label}>Max Screen Time (hours)</Text>
                <TextInput
                    style={styles.input}
                    keyboardType="numeric"
                    value={maxScreenTime}
                    onChangeText={setMaxScreenTime}
                    placeholder="Enter max screen time"
                />
            </View>

            <TouchableOpacity onPress={() => setShowDatePicker(true)} style={styles.inputContainer}>
                <Text style={styles.label}>Select Date</Text>
                <Text style={styles.dateText}>{date.toDateString()}</Text>
            </TouchableOpacity>

            <View style={styles.inputContainer}>
                <Text style={styles.label}>Milestone Type</Text>
                <TouchableOpacity onPress={() => setMilestoneType(milestoneType === 'Personal' ? 'Community' : 'Personal')} style={styles.dropdown}>
                    <Text style={styles.dropdownText}>{milestoneType}</Text>
                </TouchableOpacity>
            </View>

            {loading ? (
                <ActivityIndicator size="large" color="#00796B" style={styles.spinner} />
            ) : (
                <TouchableOpacity onPress={handleCreateMilestone} style={styles.createMilestoneButton}>
                    <Text style={styles.createMilestoneButtonText}>Create Milestone</Text>
                </TouchableOpacity>
            )}

            {showDatePicker && (
                <DatePicker
                    modal
                    open={showDatePicker}
                    date={date}
                    mode="date"
                    onConfirm={(selectedDate) => {
                        setDate(selectedDate);
                        setShowDatePicker(false);
                    }}
                    onCancel={() => setShowDatePicker(false)}
                />
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F0F4F8',
        padding: 20,
    },
    header: {
        marginTop: 50,
        marginBottom: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#00796B',
    },
    inputContainer: {
        marginBottom: 15,
    },
    label: {
        fontSize: 16,
        color: '#00796B',
        marginBottom: 5,
    },
    input: {
        height: 40,
        borderColor: '#00796B',
        borderWidth: 1,
        borderRadius: 5,
        paddingHorizontal: 10,
        fontSize: 16,
        color: '#333',
    },
    dateText: {
        fontSize: 16,
        padding: 10,
        color: '#333',
        backgroundColor: '#E0E0E0',
        borderRadius: 5,
    },
    dropdown: {
        height: 40,
        borderColor: '#00796B',
        borderWidth: 1,
        borderRadius: 5,
        justifyContent: 'center',
        paddingHorizontal: 10,
    },
    dropdownText: {
        fontSize: 16,
        color: '#00796B',
    },
    createMilestoneButton: {
        backgroundColor: '#00796B',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        alignItems: 'center',
        marginTop: 20,
    },
    createMilestoneButtonText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    spinner: {
        marginTop: 20,
    },
});
