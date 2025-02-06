import axiosInstance from './axiosInstance';

const API_URL = '/goals';

export const getAllGoals = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all goals', error);
    throw error;
  }
};

export const getGoalById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching goal by id', error);
    throw error;
  }
};

export const createGoal = async (goalDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, goalDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating goal', error);
    throw error;
  }
};

export const updateGoal = async (id, goalDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, goalDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating goal', error);
    throw error;
  }
};

export const deleteGoal = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting goal', error);
    throw error;
  }
};

export const getGoalsByUserId = async (userId) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/user/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching goals by user id', error);
    throw error;
  }
};
