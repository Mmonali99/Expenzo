import axiosInstance from './axiosInstance';

const API_URL = '/expenses';

export const getAllExpenses = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all expenses', error);
    throw error;
  }
};

export const getExpenseById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching expense by id', error);
    throw error;
  }
};

export const createExpense = async (expenseDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, expenseDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating expense', error);
    throw error;
  }
};

export const updateExpense = async (id, expenseDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, expenseDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating expense', error);
    throw error;
  }
};

export const deleteExpense = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting expense', error);
    throw error;
  }
};
