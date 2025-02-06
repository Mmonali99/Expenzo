import axiosInstance from './axiosInstance';

const API_URL = '/users';

export const getAllUsers = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all users', error);
    throw error;
  }
};

export const getUserById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user by id', error);
    throw error;
  }
};

export const createUser = async (userDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, userDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating user', error);
    throw error;
  }
};

export const updateUser = async (id, userDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, userDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating user', error);
    throw error;
  }
};

export const deleteUser = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting user', error);
    throw error;
  }
};
