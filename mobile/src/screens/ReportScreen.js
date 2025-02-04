import React, { useState, useEffect } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet, ScrollView } from 'react-native';
import DatePicker from 'react-native-date-picker';

const UserReportScreen = ({ navigation }) => {
  const [user, setUser] = useState(null);
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [filteredReport, setFilteredReport] = useState(null);
  const [openStartDate, setOpenStartDate] = useState(false);
  const [openEndDate, setOpenEndDate] = useState(false);

  useEffect(() => {
    // Simulate fetching user data
    const fetchUserData = () => {
      const dummyUserData = {
        name: "John Doe",
        email: "john.doe@example.com",
        profilePicture: "https://via.placeholder.com/150",
        performance: {
          community: "Active Contributor",
          points: 350,
          milestones: [
            { name: "Completed Course A", date: "2024-01-01" },
            { name: "Contributed to Project B", date: "2024-02-10" },
          ],
          rewards: [
            { name: "Gold Badge", date: "2024-01-15" },
            { name: "Top Performer of the Month", date: "2024-02-05" },
          ],
        },
        screenTimeRecords: [
          { date: "2024-01-01", timeSpent: "3 hours" },
          { date: "2024-02-01", timeSpent: "2.5 hours" },
        ],
      };
      setUser(dummyUserData);
    };

    fetchUserData();
  }, []);

  const handleFilterReport = () => {
    const startDateStr = startDate.toISOString().split('T')[0];
    const endDateStr = endDate.toISOString().split('T')[0];

    if (!startDateStr || !endDateStr) {
      alert("Please select both start and end dates.");
      return;
    }

    // Filter milestones, rewards, and screen time records based on the selected date range
    const filteredMilestones = user.performance.milestones.filter(milestone =>
      new Date(milestone.date) >= new Date(startDateStr) && new Date(milestone.date) <= new Date(endDateStr)
    );
    const filteredRewards = user.performance.rewards.filter(reward =>
      new Date(reward.date) >= new Date(startDateStr) && new Date(reward.date) <= new Date(endDateStr)
    );
    const filteredScreenTime = user.screenTimeRecords.filter(record =>
      new Date(record.date) >= new Date(startDateStr) && new Date(record.date) <= new Date(endDateStr)
    );

    setFilteredReport({
      milestones: filteredMilestones,
      rewards: filteredRewards,
      screenTime: filteredScreenTime,
    });
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
      </View>

      <View style={styles.reportContainer}>
        <Text style={styles.reportTitle}>Performance in Community</Text>
        <Text style={styles.reportText}>{user.performance.community}</Text>

        <Text style={styles.reportTitle}>Total Points</Text>
        <Text style={styles.reportText}>{user.performance.points}</Text>

        <Text style={styles.reportTitle}>Milestone Reports</Text>
        {user.performance.milestones.map((milestone, index) => (
          <Text key={index} style={styles.reportText}>
            {milestone.name} (Completed on: {milestone.date})
          </Text>
        ))}

        <Text style={styles.reportTitle}>Rewards</Text>
        {user.performance.rewards.map((reward, index) => (
          <Text key={index} style={styles.reportText}>
            {reward.name} (Received on: {reward.date})
          </Text>
        ))}

        <Text style={styles.reportTitle}>Screen Time Records</Text>
        {user.screenTimeRecords.map((record, index) => (
          <Text key={index} style={styles.reportText}>
            {record.date}: {record.timeSpent}
          </Text>
        ))}
      </View>

      <View style={styles.datePickerContainer}>
        <Text style={styles.datePickerLabel}>Select Date Range for Report</Text>

        {/* Start Date Button */}
        <TouchableOpacity onPress={() => setOpenStartDate(true)} style={styles.dateButton}>
          <Text style={styles.dateButtonText}>Start Date: {startDate.toISOString().split('T')[0]}</Text>
        </TouchableOpacity>

        {/* Start Date Picker Modal */}
        {openStartDate && (
          <DatePicker
            modal
            open={openStartDate}
            date={startDate}
            mode="date"
            onConfirm={(date) => {
              setOpenStartDate(false);
              setStartDate(date);
            }}
            onCancel={() => setOpenStartDate(false)}
          />
        )}

        {/* End Date Button */}
        <TouchableOpacity onPress={() => setOpenEndDate(true)} style={styles.dateButton}>
          <Text style={styles.dateButtonText}></Text>
        </TouchableOpacity>

        {/* End Date Picker Modal */}
        {openEndDate && (
          <DatePicker
            modal
            open={openEndDate}
            date={endDate}
            mode="date"
            onConfirm={(date) => {
              setOpenEndDate(false);
              setEndDate(date);
            }}
            onCancel={() => setOpenEndDate(false)}
          />
        )}

        <TouchableOpacity style={styles.filterButton} onPress={handleFilterReport}>
          <Text style={styles.filterButtonText}>Filter Report</Text>
        </TouchableOpacity>
      </View>
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
  reportContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 20,
    marginBottom: 20,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },
  reportTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#00796B',
    marginBottom: 10,
  },
  reportText: {
    fontSize: 16,
    color: '#555',
    marginBottom: 5,
  },
  datePickerContainer: {
    marginTop: 20,
  },
  datePickerLabel: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#00796B',
    marginBottom: 10,
  },
  dateButton: {
    backgroundColor: '#00796B',
    padding: 10,
    borderRadius: 5,
    marginBottom: 10,
  },
  dateButtonText: {
    fontSize: 16,
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  filterButton: {
    backgroundColor: '#00796B',
    padding: 15,
    borderRadius: 5,
    alignItems: 'center',
  },
  filterButtonText: {
    fontSize: 16,
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  filteredReportContainer: {
    backgroundColor: '#FFFFFF',
    borderRadius: 10,
    padding: 20,
    marginTop: 20,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },
  logoutButton: {
    backgroundColor: '#FF5722',
    padding: 15,
    borderRadius: 5,
    alignItems: 'center',
    marginTop: 30,
  },
  logoutButtonText: {
    fontSize: 16,
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
});

export default UserReportScreen;
