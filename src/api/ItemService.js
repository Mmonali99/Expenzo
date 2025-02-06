import axiosInstance from './axiosInstance';

const API_URL = '/items';

export const getAllItems = async () => {
  try {
    const response = await axiosInstance.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error fetching all items', error);
    throw error;
  }
};

export const getItemById = async (id) => {
  try {
    const response = await axiosInstance.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching item by id', error);
    throw error;
  }
};

export const createItem = async (itemDTO) => {
  try {
    const response = await axiosInstance.post(API_URL, itemDTO);
    return response.data;
  } catch (error) {
    console.error('Error creating item', error);
    throw error;
  }
};

export const updateItem = async (id, itemDTO) => {
  try {
    const response = await axiosInstance.put(`${API_URL}/${id}`, itemDTO);
    return response.data;
  } catch (error) {
    console.error('Error updating item', error);
    throw error;
  }
};

export const deleteItem = async (id) => {
  try {
    await axiosInstance.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error('Error deleting item', error);
    throw error;
  }
};
