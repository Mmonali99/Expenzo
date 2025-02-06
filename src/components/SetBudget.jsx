import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { createBudget, updateBudget, getBudgetById } from '../api/BudgetService';
import SetBudgetLayout from '../layout/SetBudgetLayout';

const SetBudget = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;
  const budgetId = state?.budgetId;
  const isEditing = !!budgetId;

  const [formState, setFormState] = useState({
    totalBudget: '',
    categories: [
      { name: 'Groceries', weeklyAmounts: [], weeklyPercent: [], monthlyPercent: '', monthlyAmount: '' },
      { name: 'Utilities', weeklyAmounts: [], weeklyPercent: [], monthlyPercent: '', monthlyAmount: '' },
      { name: 'Entertainment', weeklyAmounts: [], weeklyPercent: [], monthlyPercent: '', monthlyAmount: '' }
    ],
    newCategory: '',
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
    selectedWeek: '',
  });

  const [weekOptions, setWeekOptions] = useState([]);

  useEffect(() => {
    if (isEditing) {
      loadBudget(budgetId);
    }
  }, [isEditing, budgetId]);

  useEffect(() => {
    calculateWeeks();
  }, [formState.month, formState.year]);

  useEffect(() => {
    updateCalculatedValues();
  }, [formState.totalBudget, formState.categories, weekOptions]);

  const loadBudget = async (budgetId) => {
    try {
      const budget = await getBudgetById(budgetId);
      setFormState(prevState => ({
        ...prevState,
        totalBudget: budget.totalBudget,
        categories: budget.categories,
        month: budget.month || new Date().getMonth() + 1,
        year: budget.year || new Date().getFullYear(),
      }));
    } catch (error) {
      console.error('Error loading budget:', error);
    }
  };

  const calculateWeeks = () => {
    const { month, year } = formState;
    const weeks = [];
    
    const firstDayOfMonth = new Date(year, month - 1, 1);
    const lastDayOfMonth = new Date(year, month, 0);
  
    let currentStart = new Date(firstDayOfMonth);
    let currentEnd = new Date(currentStart);
    currentEnd.setDate(currentStart.getDate() + (6 - currentStart.getDay()));
  
    if (currentEnd > lastDayOfMonth) {
      currentEnd = lastDayOfMonth;
    }
  
    let weekNumber = 1;
  
    while (currentStart <= lastDayOfMonth) {
      const start = currentStart.getDate();
      const end = currentEnd.getDate();
      const daysInWeek = end - start + 1;
  
      weeks.push({
        weekNumber: weekNumber,
        startDay: currentStart.toLocaleDateString(),
        endDay: currentEnd.toLocaleDateString(),
        daysInWeek: daysInWeek
      });
  
      currentStart = new Date(currentEnd);
      currentStart.setDate(currentEnd.getDate() + 1);
      currentEnd = new Date(currentStart);
      currentEnd.setDate(currentStart.getDate() + 6);
  
      if (currentEnd > lastDayOfMonth) {
        currentEnd = lastDayOfMonth;
      }
  
      weekNumber++;
    }
  
    const options = weeks.map(week => ({
      label: `Week ${week.weekNumber}: ${week.startDay} - ${week.endDay}`,
      value: week.weekNumber,
      daysInWeek: week.daysInWeek
    }));
  
    setWeekOptions(options);
    if (options.length > 0 && !formState.selectedWeek) {
      setFormState(prevState => ({ ...prevState, selectedWeek: options[0].value }));
    }
  };

  const updateCalculatedValues = () => {
    setFormState(prevState => {
      const totalBudget = parseFloat(prevState.totalBudget) || 0;
      const daysInMonth = new Date(prevState.year, prevState.month, 0).getDate();

      const updatedCategories = prevState.categories.map(category => {
        const monthlyAmount = parseFloat(category.monthlyAmount) || 0;
        const monthlyPercent = totalBudget > 0 ? ((monthlyAmount / totalBudget) * 100).toFixed(2) : '0';

        const weeklyAmounts = weekOptions.map(week => {
          const weeklyAmount = (monthlyAmount / daysInMonth) * week.daysInWeek;
          return weeklyAmount.toFixed(2);
        });

        const weeklyPercent = weeklyAmounts.map(amount => {
          return totalBudget > 0 ? ((amount / totalBudget) * 100).toFixed(2) : '0';
        });

        return {
          ...category,
          monthlyAmount: monthlyAmount.toString(),
          monthlyPercent: monthlyPercent,
          weeklyAmounts: weeklyAmounts,
          weeklyPercent: weeklyPercent
        };
      });

      return { ...prevState, categories: updatedCategories };
    });
  };

  const handleInputChange = (index, field, value) => {
    const updatedCategories = [...formState.categories];
    updatedCategories[index][field] = value;

    setFormState(prevState => ({
      ...prevState,
      categories: updatedCategories
    }));
  };

  const handleAddCategory = () => {
    if (formState.newCategory.trim()) {
      setFormState(prevState => ({
        ...prevState,
        categories: [...prevState.categories, { name: prevState.newCategory, weeklyAmounts: [], weeklyPercent: [], monthlyPercent: '', monthlyAmount: '' }],
        newCategory: '',
      }));
    }
  };

  const handleFormChange = (event) => {
    const { name, value } = event.target;
    setFormState(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const budgetData = {
      totalBudget: formState.totalBudget,
      categories: formState.categories,
      month: formState.month,
      year: formState.year,
    };
    try {
      if (isEditing) {
        await updateBudget(budgetId, budgetData);
      } else {
        await createBudget(budgetData);
      }
      navigate('/dashboard');
    } catch (error) {
      console.error('Error saving budget:', error);
      alert('An error occurred while saving the budget. Please try again.');
    }
  };

  const totalAllocatedBudget = formState.categories.reduce(
    (acc, cat) => acc + parseFloat(cat.monthlyAmount || 0), 0
  );

  const overBudget = parseFloat(totalAllocatedBudget.toFixed(2)) > parseFloat(formState.totalBudget);

  return (
    <SetBudgetLayout
      totalBudget={formState.totalBudget}
      categories={formState.categories}
      newCategory={formState.newCategory}
      handleTotalBudgetChange={handleFormChange}
      handleInputChange={handleInputChange}
      handleAddCategory={handleAddCategory}
      setNewCategory={(value) => setFormState(prevState => ({ ...prevState, newCategory: value }))}
      overBudget={overBudget}
      totalMonthlyAmount={totalAllocatedBudget.toFixed(2)}
      handleSubmit={handleSubmit}
      month={formState.month}
      year={formState.year}
      handleMonthChange={handleFormChange}
      handleYearChange={handleFormChange}
      weekOptions={weekOptions}
      selectedWeek={formState.selectedWeek}
      setSelectedWeek={(value) => setFormState(prevState => ({ ...prevState, selectedWeek: value }))}
    />
  );
};

export default SetBudget;
