import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import TrackExpenseLayout from '../layout/TrackExpenseLayout';
import axiosInstance from '../api/axiosInstance';

const initialCategories = ['Groceries', 'Utilities', 'Transportation', 'Entertainment', 'Other'];

const TrackExpense = () => {
  const [expenses, setExpenses] = useState([
    {
      id: 1,
      date: '2023-05-01',
      time: '10:00',
      category: 'Groceries',
      amount: 50.0,
      description: 'Weekly grocery shopping',
      purchaseLocation: 'Supermarket',
      items: [
        { itemName: 'Milk', itemCategory: 'Groceries', itemAmount: 10 },
        { itemName: 'Bread', itemCategory: 'Groceries', itemAmount: 5 }
      ]
    },
    {
      id: 2,
      date: '2023-05-02',
      time: '12:30',
      category: 'Utilities',
      amount: 100.0,
      description: 'Electricity bill',
      purchaseLocation: 'Online',
      items: []
    }
  ]);
  const [filterType, setFilterType] = useState('');
  const [filterValue, setFilterValue] = useState({ from: '', to: '', min: '', max: '' });
  const [expandedExpenseId, setExpandedExpenseId] = useState(null);
  const [categories, setCategories] = useState(initialCategories);
  const navigate = useNavigate();

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      const response = await axiosInstance.get('/expenses/track');
      setExpenses(prevExpenses => [...prevExpenses, ...response.data]);
    } catch (error) {
      console.error('Error fetching expenses:', error);
    }
  };

  const handleSelect = (type) => {
    setFilterType(type);
    setFilterValue({ from: '', to: '', min: '', max: '' });
    setExpenses([]); // Reset expenses when filter type changes
  };

  const handleFilterValueChange = (e) => {
    const { name, value } = e.target;
    setFilterValue(prev => ({ ...prev, [name]: value }));
    if (filterType === 'dateRange' && name === 'to') {
      applyFilter(filterType, { ...filterValue, [name]: value });
    } else if (filterType === 'amountRange' && name === 'max') {
      applyFilter(filterType, { ...filterValue, [name]: value });
    }
  };

  const applyFilter = async (type, value) => {
    try {
      const response = await axiosInstance.get('/expenses/track', {
        params: {
          ...value,
        }
      });
      setExpenses(response.data);
    } catch (error) {
      console.error('Error applying filter:', error);
    }
  };

  const toggleExpand = (id) => {
    setExpandedExpenseId(expandedExpenseId === id ? null : id);
  };

  const handleEdit = (id) => {
    const expenseToEdit = expenses.find(expense => expense.id === id);
    const formattedTime = expenseToEdit.time.length === 5 ? expenseToEdit.time : expenseToEdit.time.substr(0, 5);
    navigate('/menu/add-expense', { state: { ...expenseToEdit, time: formattedTime } });
  };

  const handleDelete = async (id) => {
    try {
      await axiosInstance.delete(`/expenses/delete/${id}`);
      setExpenses(prevExpenses => prevExpenses.filter(expense => expense.id !== id));
    } catch (error) {
      console.error("Error deleting expense:", error);
    }
  };

  return (
    <TrackExpenseLayout
      expenses={expenses}
      filterType={filterType}
      filterValue={filterValue}
      categories={categories}
      expandedExpenseId={expandedExpenseId}
      handleSelect={handleSelect}
      handleFilterValueChange={handleFilterValueChange}
      toggleExpand={toggleExpand}
      handleEdit={handleEdit}
      handleDelete={handleDelete}
    />
  );
};

export default TrackExpense;
