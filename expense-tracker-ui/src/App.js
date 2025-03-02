import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./features/Dashboard/Dashboard.js";
import Home from "./pages/Home/Home.js";
import SignIn from "./pages/SignIn/SignIn.js";
import SignUp from "./pages/SignUp/SignUp.js";
import ForgotPassword from "./pages/ForgotPassword/ForgotPassword.js";
import AddExpense from "./features/ExpenseHub/AddExpense";
import TrackExpense from "./features/SpendingInsights/TrackExpense";
import TrackBudget from "./features/BudgetsMonitor/TrackBudget";
import AddGoal from "./features/GoalSetter/AddGoal";
import TrackGoals from "./features/MilestoneTracker/TrackGoals";
import SetBudget from "./features/SmartBudget/SetBudget";
import PrivateRoute from "./PrivateRoute"; // Add this file
import "./styles/styles.css";

function App() {
  return (
    <Router>
      <Routes>
        {/* Home Layout (Public Pages) */}
        <Route path="/" element={<Home />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />

        {/* Dashboard Layout (Protected Routes) */}
        <Route
          path="/dashboard/*"
          element={
            <PrivateRoute>
              <Dashboard>
                <Routes>
                  <Route path="expense/add" element={<AddExpense />} />
                  <Route path="expense/track" element={<TrackExpense />} />
                  <Route path="budget/track" element={<TrackBudget />} />
                  <Route path="budget/set" element={<SetBudget />} />
                  <Route path="goals/add" element={<AddGoal />} />
                  <Route path="goals/track" element={<TrackGoals />} />
                </Routes>
              </Dashboard>
            </PrivateRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;