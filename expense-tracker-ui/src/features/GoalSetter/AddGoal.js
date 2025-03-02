import React, { useState, useEffect } from "react";
import styles from "./AddGoal.module.css";
import { addGoal, getGoals, deleteGoal } from "../../api/api";

const AddGoal = () => {
    const [goalName, setGoalName] = useState("");
    const [description, setDescription] = useState("");
    const [goalType, setGoalType] = useState("");
    const [targetAmount, setTargetAmount] = useState("");
    const [deadline, setDeadline] = useState("");
    const [goals, setGoals] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchGoals();
    }, []);

    const fetchGoals = async () => {
        try {
            const response = await getGoals();
            setGoals(response.data.map((goal) => ({ ...goal, goalType: goal.goalType || "N/A" })));
            setError("");
        } catch (error) {
            setError("Failed to fetch goals: " + (error.response?.data || error.message));
        }
    };

    const handleAddGoal = async () => {
        if (!goalName || !targetAmount || !deadline) {
            setError("Please fill in all required fields (Name, Target Amount, Deadline)");
            return;
        }
        if (parseFloat(targetAmount) <= 0) {
            setError("Target amount must be greater than 0");
            return;
        }
        if (new Date(deadline) < new Date().setHours(0, 0, 0, 0)) {
            setError("Deadline cannot be in the past");
            return;
        }

        const newGoal = {
            name: goalName,
            targetAmount: parseFloat(targetAmount),
            savedAmount: 0,
            deadline,
            username: localStorage.getItem("username"),
        };

        try {
            const response = await addGoal(newGoal);
            setGoals([...goals, { ...response.data, goalType: goalType || "N/A" }]);
            setGoalName("");
            setDescription("");
            setGoalType("");
            setTargetAmount("");
            setDeadline("");
            setError("");
            alert("Goal added successfully!"); // Added success feedback
        } catch (error) {
            setError("Failed to add goal: " + (error.response?.data || error.message));
        }
    };

    const handleDeleteGoal = async (id) => {
        if (window.confirm("Are you sure you want to delete this goal?")) {
            try {
                await deleteGoal(id);
                setGoals(goals.filter((goal) => goal.id !== id));
                setError("");
                alert("Goal deleted successfully!"); // Added success feedback
            } catch (error) {
                setError("Failed to delete goal: " + (error.response?.data || error.message));
            }
        }
    };

    return (
        <div className="container">
            <h2>Set a Financial Goal</h2>
            {error && <p className={styles.error}>{error}</p>}

            <div className={styles.formGroup}>
                <label htmlFor="goalName">Title (Goal Name):</label>
                <input
                    type="text"
                    id="goalName"
                    placeholder="Enter Goal Name"
                    value={goalName}
                    onChange={(e) => setGoalName(e.target.value)}
                />

                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    placeholder="Brief details about the goal"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />

                <label htmlFor="goalType">Goal Type:</label>
                <select
                    id="goalType"
                    value={goalType}
                    onChange={(e) => setGoalType(e.target.value)}
                >
                    <option value="">Select Goal Type</option>
                    <option value="Savings">Savings</option>
                    <option value="Investment">Investment</option>
                    <option value="Emergency Fund">Emergency Fund</option>
                    <option value="Luxury Purchase">Luxury Purchase</option>
                </select>

                <label htmlFor="targetAmount">Amount (₹):</label>
                <input
                    type="number"
                    id="targetAmount"
                    placeholder="Enter Target Amount"
                    value={targetAmount}
                    onChange={(e) => setTargetAmount(e.target.value)}
                />

                <label htmlFor="deadline">Target Date:</label>
                <input
                    type="date"
                    id="deadline"
                    value={deadline}
                    onChange={(e) => setDeadline(e.target.value)}
                />

                <button type="button" onClick={handleAddGoal}>
                    Set Goal
                </button>
            </div>

            <div className={styles.goalList}>
                {goals.map((goal) => (
                    <div key={goal.id} className={styles.goalCard}>
                        <h3>{goal.name}</h3>
                        <p><strong>Description:</strong> {goal.description || "N/A"}</p>
                        <p><strong>Category:</strong> {goal.goalType}</p>
                        <p><strong>Target:</strong> ₹{goal.targetAmount}</p>
                        <p><strong>Deadline:</strong> {goal.deadline}</p>
                        <button onClick={() => handleDeleteGoal(goal.id)}>Delete</button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AddGoal;