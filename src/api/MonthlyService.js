import axiosInstance from './axiosInstance';

const API_URL = '/monthlies';

export const getAllMonthlies = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all monthlies', error);
    throw error;
  }
};

export const getMonthlyById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching monthly by id', error);
    throw error;
  }
};

export const createMonthly = async (monthlyDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, monthlyDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating monthly', error);
    throw error;
  }
};

export const updateMonthly = async (id, monthlyDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, monthlyDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating monthly', error);
    throw error;
  }
};

export const deleteMonthly = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting monthly', error);
    throw error;
  }
};
