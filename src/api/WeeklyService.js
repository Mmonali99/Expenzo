import axiosInstance from './axiosInstance';

const API_URL = '/weeklies';

export const getAllWeeklies = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all weeklies', error);
    throw error;
  }
};

export const getWeeklyById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching weekly by id', error);
    throw error;
  }
};

export const createWeekly = async (weeklyDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, weeklyDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating weekly', error);
    throw error;
  }
};

export const updateWeekly = async (id, weeklyDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, weeklyDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating weekly', error);
    throw error;
  }
};

export const deleteWeekly = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting weekly', error);
    throw error;
  }
};
