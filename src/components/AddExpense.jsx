import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import AddExpenseLayout from '../layout/AddExpenseLayout';

const AddExpense = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [formState, setFormState] = useState({
    date: '',
    time: '',
    amount: '',
    description: '',
    purchaseLocation: '',
    items: [{ itemName: '', itemCategory: 'Groceries', itemAmount: '' }],
  });

  useEffect(() => {
    const today = new Date();
    setFormState(prevState => ({
      ...prevState,
      date: today.toISOString().substr(0, 10),
      time: today.toTimeString().substr(0, 5),
    }));

    if (location.state) {
      const { date, time, amount, description, purchaseLocation, items } = location.state;
      setFormState({
        date,
        time,
        amount,
        description,
        purchaseLocation,
        items: items || [{ itemName: '', itemCategory: 'Groceries', itemAmount: '' }],
      });
    }
  }, [location.state]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };

  const handleItemChange = (index, key, value) => {
    const updatedItems = [...formState.items];
    updatedItems[index][key] = value;
    setFormState(prevState => ({ ...prevState, items: updatedItems }));
  };

  const handleAddRow = () => {
    setFormState(prevState => ({
      ...prevState,
      items: [...prevState.items, { itemName: '', itemCategory: 'Groceries', itemAmount: '' }],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formState.amount || !formState.purchaseLocation) {
      alert('Amount and Purchase Location are required fields.');
      return;
    }

    const dateTime = `${formState.date}T${formState.time}`;
    const expenseData = { dateTime, amount: formState.amount, description: formState.description, purchaseLocation: formState.purchaseLocation, items: formState.items };

    try {
      await axiosInstance.post('/expenses/add', expenseData);
      navigate('/menu/success');
    } catch (error) {
      console.error('Error saving expense:', error);
      alert('An error occurred while saving the expense. Please try again.');
    }
  };

  const handleReset = () => {
    const today = new Date();
    setFormState({
      date: today.toISOString().substr(0, 10),
      time: today.toTimeString().substr(0, 5),
      amount: '',
      description: '',
      purchaseLocation: '',
      items: [{ itemName: '', itemCategory: 'Groceries', itemAmount: '' }],
    });
  };

  return (
    <AddExpenseLayout
      date={formState.date}
      time={formState.time}
      amount={formState.amount}
      description={formState.description}
      purchaseLocation={formState.purchaseLocation}
      items={formState.items}
      handleDateChange={handleChange}
      handleTimeChange={handleChange}
      handleAmountChange={handleChange}
      handleDescriptionChange={handleChange}
      handlePurchaseLocationChange={handleChange}
      handleItemChange={handleItemChange}
      handleAddRow={handleAddRow}
      handleSubmit={handleSubmit}
      handleReset={handleReset}
    />
  );
};

export default AddExpense;
