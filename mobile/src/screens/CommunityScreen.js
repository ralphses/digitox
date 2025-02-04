import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, TextInput, ActivityIndicator, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as Animatable from 'react-native-animatable'; // For animations
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Picker } from '@react-native-picker/picker'; // Import Picker from @react-native-picker/picker

// Function to format the date and time
const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(date).toLocaleString(undefined, options);
};

export default function CommunityScreen() {
    const navigation = useNavigation();
    const [loading, setLoading] = useState(true);
    const [userData, setUserData] = useState(null);
    const [posts, setPosts] = useState([]); // Array to store milestone posts
    const [newPostText, setNewPostText] = useState('');
    const [selectedMilestone, setSelectedMilestone] = useState(''); // State for milestone selection

    // Sample posts data
    const samplePosts = [
        { id: 1, user: 'John Doe', milestone: 'Completed my first 100 points!', likes: 15, comments: 5, date: new Date() },
        { id: 2, user: 'Jane Smith', milestone: 'Reached level 2 in the challenge!', likes: 10, comments: 3, date: new Date() },
        { id: 3, user: 'Alice Brown', milestone: 'Earned my first reward: Free Ebook!', likes: 20, comments: 8, date: new Date() },
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

    const createPost = () => {
        if (newPostText.trim() && selectedMilestone) {
            const newPost = {
                id: posts.length + 1,
                user: userData?.name || 'Anonymous',
                milestone: `${selectedMilestone}: ${newPostText}`,
                likes: 0,
                comments: 0,
                date: new Date(), // Add current date and time
            };
            setPosts([newPost, ...posts]);
            setNewPostText(''); // Clear input after posting
            setSelectedMilestone(''); // Clear the selected milestone
        } else {
            Alert.alert('Error', 'Please select a milestone and enter text before posting.');
        }
    };

    const likePost = (postId) => {
        const updatedPosts = posts.map((post) => {
            if (post.id === postId) {
                post.likes += 1;
            }
            return post;
        });
        setPosts(updatedPosts);
    };

    const commentOnPost = (postId) => {
        Alert.alert('Comment', `Post ID: ${postId} - Add your comment!`);
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
            {/* Create Milestone Post Button */}
            <Animatable.View animation="fadeIn" duration={100} style={styles.header}>
                <Text style={styles.title}>Community Milestones</Text>
                <TouchableOpacity style={styles.createPostButton} onPress={createPost}>
                    <Text style={styles.createPostText}>Create Milestone</Text>
                </TouchableOpacity>
            </Animatable.View>

            {/* New Post Text Input and Dropdown */}
            <View style={styles.newPostContainer}>
                {/* Dropdown for selecting milestone */}
                <Picker
                    selectedValue={selectedMilestone}
                    onValueChange={(itemValue) => setSelectedMilestone(itemValue)}
                    style={styles.milestonePicker}>
                    <Picker.Item label="Select Milestone" value="" />
                    <Picker.Item label="First 100 points" value="First 100 points" />
                    <Picker.Item label="Reached level 2" value="Reached level 2" />
                    <Picker.Item label="Earned a reward" value="Earned a reward" />
                    <Picker.Item label="Completed challenge" value="Completed challenge" />
                    {/* Add more options here */}
                </Picker>

                {/* Text Input for entering the milestone details */}
                <TextInput
                    style={styles.newPostInput}
                    placeholder="Share your milestone details..."
                    value={newPostText}
                    onChangeText={setNewPostText}
                    multiline
                />
            </View>

            {/* Posts Section */}
            <View style={styles.postsSection}>
                {samplePosts.length > 0 ? (
                    samplePosts.map((post) => (
                        <Animatable.View key={post.id} animation="zoomIn" delay={500} style={styles.postCard}>
                            <Text style={styles.postUser}>{post.user}</Text>
                            <Text style={styles.postDate}>{formatDate(post.date)}</Text> {/* Date formatted here */}
                            <Text style={styles.postText}>{post.milestone}</Text>

                            <View style={styles.postActions}>
                                <TouchableOpacity onPress={() => likePost(post.id)} style={styles.likeButton}>
                                    <Text style={styles.likeButtonText}>Like ({post.likes})</Text>
                                </TouchableOpacity>
                                <TouchableOpacity onPress={() => commentOnPost(post.id)} style={styles.commentButton}>
                                    <Text style={styles.commentButtonText}>Comment ({post.comments})</Text>
                                </TouchableOpacity>
                            </View>
                        </Animatable.View>
                    ))
                ) : (
                    <Text style={styles.noPostsText}>No posts yet. Be the first to share your milestone!</Text>
                )}
            </View>

            {/* Footer Section */}
            <View style={styles.footer}>
                <Text style={styles.footerText}>Join the community and share your progress!</Text>
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
    createPostButton: {
        backgroundColor: '#00796B',
        paddingVertical: 8,
        paddingHorizontal: 15,
        borderRadius: 20,
    },
    createPostText: {
        color: '#FFFFFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    newPostContainer: {
        marginVertical: 20,
        paddingHorizontal: 15,
    },
    milestonePicker: {
        height: 50,
        width: '100%',
        marginBottom: 20,
        borderWidth: 1,
        borderColor: '#00796B',
        borderRadius: 10,
        backgroundColor: '#FFFFFF',
    },
    newPostInput: {
        height: 100,
        borderWidth: 1,
        borderColor: '#00796B',
        borderRadius: 10,
        padding: 10,
        backgroundColor: '#FFFFFF',
        textAlignVertical: 'top',
    },
    postsSection: {
        marginBottom: 20,
    },
    postCard: {
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        padding: 15,
        marginBottom: 15,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 5,
    },
    postUser: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#00796B',
    },
    postDate: {
        fontSize: 12,
        color: '#777',
        marginBottom: 10,
    },
    postText: {
        fontSize: 14,
        color: '#333',
        marginVertical: 10,
    },
    postActions: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    likeButton: {
        backgroundColor: '#00796B',
        paddingVertical: 8,
        paddingHorizontal: 15,
        borderRadius: 20,
    },
    likeButtonText: {
        color: '#FFFFFF',
        fontSize: 14,
    },
    commentButton: {
        backgroundColor: '#B0BEC5',
        paddingVertical: 8,
        paddingHorizontal: 15,
        borderRadius: 20,
    },
    commentButtonText: {
        color: '#FFFFFF',
        fontSize: 14,
    },
    noPostsText: {
        fontSize: 16,
        color: '#00796B',
        fontStyle: 'italic',
        textAlign: 'center',
        marginTop: 20,
    },
    footer: {
        marginTop: 20,
        paddingBottom: 10,
        alignItems: 'center',
    },
    footerText: {
        fontSize: 14,
        color: '#00796B',
    },
});
