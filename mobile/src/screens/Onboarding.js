import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ImageBackground,
  TouchableOpacity,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable';
import AsyncStorage from '@react-native-async-storage/async-storage';  // Import AsyncStorage

export default function Onboarding() {
  const navigation = useNavigation();
  const [currentSlide, setCurrentSlide] = useState(0);

  const slides = [
    {
      key: 1,
      background: require('./assets/logo.jpg'), // Replace with your actual image file
      title: 'Track Screen Time',
      description: 'Monitor and manage your screen usage effectively.',
    },
    {
      key: 2,
      background: require('./assets/logo.jpg'), // Replace with your actual image file
      title: 'Improve Sleep Quality',
      description: 'Adopt healthier digital habits for better sleep.',
    },
    {
      key: 3,
      background: require('./assets/logo.jpg'), // Replace with your actual image file
      title: 'Join Our Community',
      description: 'Connect with like-minded individuals for growth.',
    },
  ];

  // Check AsyncStorage on component mount
  useEffect(() => {
    const checkOnboardingStatus = async () => {
      try {
        const created = await AsyncStorage.getItem('created');
        if (created === 'true') {
          navigation.navigate('Home');
        }
      } catch (error) {
        console.error('Error checking AsyncStorage', error);
      }
    };
    
    checkOnboardingStatus();
  }, []);

  const handleNext = async () => {
    if (currentSlide < slides.length - 1) {
      setCurrentSlide(currentSlide + 1);
    } else {
      // Mark onboarding as complete
      await AsyncStorage.setItem('created', 'true');
      navigation.navigate('Home'); // Navigate to the home screen
    }
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={slides[currentSlide].background}
        style={styles.background}
        resizeMode="cover"
      >
        <Animatable.View animation="fadeInUp" style={styles.footer}>
          <Text style={styles.title}>{slides[currentSlide].title}</Text>
          <Text style={styles.description}>{slides[currentSlide].description}</Text>

          {currentSlide < slides.length - 1 ? (
            <TouchableOpacity style={styles.nextButton} onPress={handleNext}>
              <Text style={styles.nextText}>Next</Text>
            </TouchableOpacity>
          ) : (
            <TouchableOpacity style={styles.getStartedButton} onPress={handleNext}>
              <Text style={styles.getStartedText}>Get Started</Text>
            </TouchableOpacity>
          )}
        </Animatable.View>
      </ImageBackground>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  background: {
    flex: 1,
    justifyContent: 'flex-end',
  },
  footer: {
    backgroundColor: 'rgba(255, 255, 255, 0.8)',
    paddingVertical: 30,
    paddingHorizontal: 20,
    alignItems: 'center',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#000000',
    textAlign: 'center',
    marginBottom: 10,
  },
  description: {
    fontSize: 16,
    color: '#000000',
    textAlign: 'center',
    marginBottom: 20,
  },
  nextButton: {
    backgroundColor: '#00796B',
    paddingVertical: 12,
    paddingHorizontal: 50,
    borderRadius: 25,
  },
  nextText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  getStartedButton: {
    backgroundColor: '#00796B',
    paddingVertical: 12,
    paddingHorizontal: 50,
    borderRadius: 25,
  },
  getStartedText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: 'bold',
  },
});
