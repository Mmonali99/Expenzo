import axiosInstance from './axiosInstance';

const API_URL = '/budgets';

export const getAllBudgets = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching budgets', error);
    throw error;
  }
};

export const getBudgetById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching budget', error);
    throw error;
  }
};

export const createBudget = async (budgetDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, budgetDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating budget', error);
    throw error;
  }
};

export const updateBudget = async (id, budgetDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, budgetDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating budget', error);
    throw error;
  }
};

export const deleteBudget = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting budget', error);
    throw error;
  }
};
