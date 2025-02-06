import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllBudgets, deleteBudget } from '../api/BudgetService';
import TrackBudgetLayout from '../layout/TrackBudgetLayout';

const TrackBudget = () => {
  const [budgetData, setBudgetData] = useState([]);
  const [period, setPeriod] = useState('Weekly'); // Set Weekly as the default period
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1);
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
  const [selectedWeek, setSelectedWeek] = useState('');
  const [weekOptions, setWeekOptions] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    fetchBudgetData();
    if (period === 'Weekly') {
      calculateWeeks(selectedMonth, selectedYear);
    }
  }, [period, selectedMonth, selectedYear]);

  const fetchBudgetData = async () => {
    try {
      const data = await getAllBudgets(period, selectedMonth, selectedYear);
      setBudgetData(data);
    } catch (error) {
      console.error('Error fetching budget data:', error);
    }
  };

  const calculateWeeks = (month, year) => {
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
      const startFormatted = currentStart.toLocaleDateString('en-GB'); // Format the start date as dd/mm/yyyy
      const endFormatted = currentEnd.toLocaleDateString('en-GB'); // Format the end date as dd/mm/yyyy

      weeks.push({
        label: `Week ${weekNumber}: ${startFormatted} - ${endFormatted}`,
        value: weekNumber,
      });

      // Move to the next week
      currentStart = new Date(currentEnd);
      currentStart.setDate(currentEnd.getDate() + 1);
      currentEnd = new Date(currentStart);
      currentEnd.setDate(currentStart.getDate() + 6);

      if (currentEnd > lastDayOfMonth) {
        currentEnd = lastDayOfMonth;
      }

      weekNumber++;
    }

    setWeekOptions(weeks);
    setSelectedWeek(weeks.length > 0 ? weeks[0].value : '');
  };

  const handleEdit = (index) => {
    const budgetToEdit = budgetData[index];
    navigate('/menu/set-budget', { state: { categories: budgetData, totalBudget: budgetToEdit.totalAmount } });
  };

  const handleDelete = async (index) => {
    const budgetToDelete = budgetData[index];
    try {
      await deleteBudget(budgetToDelete.id);
      setBudgetData((prevData) => prevData.filter((_, i) => i !== index));
    } catch (error) {
      console.error('Error deleting budget:', error);
    }
  };

  return (
    <TrackBudgetLayout
      budgetData={budgetData}
      handleEdit={handleEdit}
      handleDelete={handleDelete}
      period={period}
      setPeriod={setPeriod}
      selectedMonth={selectedMonth}
      setSelectedMonth={setSelectedMonth}
      selectedYear={selectedYear}
      setSelectedYear={setSelectedYear}
      weekOptions={weekOptions}
      selectedWeek={selectedWeek}
      setSelectedWeek={setSelectedWeek}
    />
  );
};

export default TrackBudget;
