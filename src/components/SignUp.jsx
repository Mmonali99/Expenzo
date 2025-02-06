import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import SignUpLayout from '../layout/SignUpLayout';

const SignUp = () => {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({
    username: '',
    password: '',
    confirmPassword: '',
    phoneNumber: '',
  });
  const [error, setError] = useState('');

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (formState.password !== formState.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    try {
      await axiosInstance.post('/auth/signup', formState);
      navigate('/menu');
    } catch (error) {
      console.error('Error signing up:', error);
      alert('Signup failed. Please try again.');
    }
  };

  return (
    <SignUpLayout
      username={formState.username}
      password={formState.password}
      confirmPassword={formState.confirmPassword}
      phoneNumber={formState.phoneNumber}
      handleUsernameChange={handleChange}
      handlePasswordChange={handleChange}
      handleConfirmPasswordChange={handleChange}
      handlePhoneNumberChange={handleChange}
      handleSubmit={handleSubmit}
      error={error}
    />
  );
};

export default SignUp;
