import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import SetGoalLayout from '../layout/SetGoalLayout';

const SetGoal = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const [goalTypes, setGoalTypes] = useState([
    'Saving',
    'Debt Repayment',
    'Investment Targets',
    'Emergency Funds',
    'Other'
  ]);

  const [formState, setFormState] = useState({
    selectedGoalType: 'Saving',
    newGoal: '',
    title: '',
    description: '',
    amount: '',
    targetDate: '',
  });

  useEffect(() => {
    if (location.state) {
      const { title, description, goalType, amount, targetDate } = location.state;
      setFormState(prevState => ({
        ...prevState,
        title,
        description,
        selectedGoalType: goalType,
        amount,
        targetDate,
      }));
    }
  }, [location.state]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };

  const handleAddGoal = () => {
    if (formState.newGoal.trim() !== '') {
      setGoalTypes([...goalTypes, formState.newGoal]);
      setFormState(prevState => ({
        ...prevState,
        selectedGoalType: prevState.newGoal,
        newGoal: '',
      }));
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const goalData = {
      title: formState.title,
      description: formState.description,
      goalType: formState.selectedGoalType,
      amount: formState.amount,
      targetDate: formState.targetDate,
    };

    try {
      await axiosInstance.post('/goals/set', goalData);
      navigate('/menu/success');
    } catch (error) {
      console.error('Error saving goal:', error);
      alert('An error occurred while saving the goal. Please try again.');
    }
  };

  return (
    <SetGoalLayout
      goalTypes={goalTypes}
      selectedGoalType={formState.selectedGoalType}
      newGoal={formState.newGoal}
      title={formState.title}
      description={formState.description}
      amount={formState.amount}
      targetDate={formState.targetDate}
      handleGoalTypeChange={(e) => handleChange(e)}
      handleNewGoalChange={(e) => handleChange(e)}
      handleAddGoal={handleAddGoal}
      handleTitleChange={(e) => handleChange(e)}
      handleDescriptionChange={(e) => handleChange(e)}
      handleAmountChange={(e) => handleChange(e)}
      handleTargetDateChange={(e) => handleChange(e)}
      handleSubmit={handleSubmit}
    />
  );
};

export default SetGoal;
