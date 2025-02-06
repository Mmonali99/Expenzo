import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import SignInLayout from '../layout/SignInLayout';

const SignIn = () => {
  const navigate = useNavigate();
  const [formState, setFormState] = useState({ username: '', password: '' });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axiosInstance.post('/auth/login', formState);
      console.log('Login successful:', response.data);
      navigate('/menu');
    } catch (error) {
      console.error('Error logging in:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  return (
    <SignInLayout
      username={formState.username}
      password={formState.password}
      handleUsernameChange={handleChange}
      handlePasswordChange={handleChange}
      handleSubmit={handleSubmit}
    />
  );
};

export default SignIn;
