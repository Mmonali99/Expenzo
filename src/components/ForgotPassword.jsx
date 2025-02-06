import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import ForgotPasswordLayout from '../layout/ForgotPasswordLayout';

const ForgotPassword = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();

  const handleUsernameChange = (e) => setUsername(e.target.value);
  const handlePasswordChange = (e) => setPassword(e.target.value);
  const handleConfirmPasswordChange = (e) => setConfirmPassword(e.target.value);

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    try {
      const response = await axiosInstance.post('/auth/reset-password', { username, password });
      console.log('Password reset successful:', response.data);
      navigate('/menu'); // Redirect to menu after successful password reset
    } catch (error) {
      console.error('Error resetting password:', error);
      // Handle password reset error (e.g., show error message)
    }
  };

  return (
    <ForgotPasswordLayout
      username={username}
      password={password}
      confirmPassword={confirmPassword}
      handleUsernameChange={handleUsernameChange}
      handlePasswordChange={handlePasswordChange}
      handleConfirmPasswordChange={handleConfirmPasswordChange}
      handleSubmit={handleSubmit}
    />
  );
};

export default ForgotPassword;
