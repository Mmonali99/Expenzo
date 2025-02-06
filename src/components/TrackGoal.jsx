import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import TrackGoalLayout from '../layout/TrackGoalLayout';

const TrackGoal = () => {
  const [goals, setGoals] = useState([]);
  const [activeKey, setActiveKey] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Initially fetch goals when component mounts
    fetchGoals();
  }, []);

  // Function to fetch goals from the API
  const fetchGoals = async () => {
    try {
      // Simulate API call with dummy data (replace with actual API call in production)
      const dummyData = [
        { id: 1, title: 'Exercise Daily', description: 'Run for 30 minutes', target: 'Fitness' },
        { id: 2, title: 'Read Books', description: 'Read 2 books per month', target: 'Personal Development' },
        { id: 3, title: 'Save Money', description: 'Save $500 monthly', target: 'Financial' }
      ];
      
      // Set dummy data to state
      setGoals(dummyData);
    } catch (error) {
      console.error('Error fetching goals:', error);
    }
  };

  // Function to toggle details of a goal
  const toggleDetails = (goalId) => {
    setActiveKey(activeKey === goalId ? null : goalId);
  };

  // Function to handle editing a goal
  const handleEdit = (goal) => {
    navigate('/menu/set-goal', { state: goal });
  };

  // Function to handle deleting a goal
  const handleDelete = async (id) => {
    try {
      // Simulate deletion (replace with actual delete API call in production)
      // Optimistically update state to reflect deletion
      await axiosInstance.delete(`/goals/delete/${id}`);
      setGoals(prevGoals => prevGoals.filter(goal => goal.id !== id));
    } catch (error) {
      console.error('Error deleting goal:', error);
    }
  };

  return (
    <TrackGoalLayout
      goals={goals}
      activeKey={activeKey}
      toggleDetails={toggleDetails}
      handleEdit={handleEdit}
      handleDelete={handleDelete}
    />
  );
};

export default TrackGoal;
