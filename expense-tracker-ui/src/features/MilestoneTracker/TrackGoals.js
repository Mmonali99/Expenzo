import React, { useState, useEffect } from "react";
import styles from "./TrackGoals.module.css";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";
import { getGoals, deleteGoal, updateGoal } from "../../api/api";

const TrackGoals = () => {
    const [goals, setGoals] = useState([]);
    const [error, setError] = useState("");
    const [editingGoal, setEditingGoal] = useState(null);

    useEffect(() => {
        fetchGoals();
    }, []);

    const fetchGoals = async () => {
        try {
            const response = await getGoals();
            setGoals(response.data.map((goal) => ({ ...goal, expanded: false })));
            setError("");
        } catch (error) {
            setError("Failed to fetch goals: " + (error.response?.data || error.message));
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this goal?")) {
            try {
                await deleteGoal(id);
                setGoals(goals.filter((goal) => goal.id !== id));
                setError("");
            } catch (error) {
                setError("Failed to delete goal: " + (error.response?.data || error.message));
            }
        }
    };

    const handleEdit = (goal) => {
        setEditingGoal({ ...goal });
    };

    const handleUpdateGoal = async () => {
        if (!editingGoal.name || !editingGoal.targetAmount || !editingGoal.deadline) {
            setError("Please fill in all required fields");
            return;
        }
        if (parseFloat(editingGoal.targetAmount) <= 0) {
            setError("Target amount must be greater than 0");
            return;
        }
        if (new Date(editingGoal.deadline) < new Date().setHours(0, 0, 0, 0)) {
            setError("Deadline cannot be in the past");
            return;
        }
    
        try {
            await updateGoal(editingGoal.id, {
                name: editingGoal.name,
                targetAmount: parseFloat(editingGoal.targetAmount),
                savedAmount: parseFloat(editingGoal.savedAmount),
                deadline: editingGoal.deadline,
                username: localStorage.getItem("username"),
            });
            setGoals(
                goals.map((goal) =>
                    goal.id === editingGoal.id ? { ...goal, ...editingGoal, expanded: false } : goal
                )
            );
            setEditingGoal(null);
            setError("");
            alert("Goal updated successfully!"); // Added success feedback
        } catch (error) {
            setError("Failed to update goal: " + (error.response?.data || error.message));
        }
    };

    
    const toggleExpand = (id) => {
        setGoals(goals.map((goal) => (goal.id === id ? { ...goal, expanded: !goal.expanded } : goal)));
    };

    const calculateProgress = (goal) => {
        return Math.min((goal.savedAmount / goal.targetAmount) * 100, 100).toFixed(0);
    };

    return (
        <div className="container">
            <h1 className={styles.title}>Track Your Goals</h1>
            {error && <p className={styles.error}>{error}</p>}
            <div className={styles.goalList}>
                {goals.map((goal) => (
                    <div key={goal.id} className={styles.goalCard}>
                        <div className={styles.goalHeader} onClick={() => toggleExpand(goal.id)}>
                            <h3 className={styles.goalTitle}>{goal.name}</h3>
                            <div className={styles.goalRight}>
                                <div className={styles.progressBarContainer}>
                                    <div
                                        className={styles.progressBar}
                                        style={{ width: `${calculateProgress(goal)}%` }}
                                    ></div>
                                </div>
                                <span className={styles.progressPercentage}>
                                    {calculateProgress(goal)}%
                                </span>
                                {goal.expanded ? (
                                    <FaChevronUp className={styles.dropdownIcon} />
                                ) : (
                                    <FaChevronDown className={styles.dropdownIcon} />
                                )}
                            </div>
                        </div>
                        {goal.expanded && !editingGoal && (
                            <div className={styles.expandedContent}>
                                <p><strong>Target:</strong> ₹{goal.targetAmount}</p>
                                <p><strong>Saved:</strong> ₹{goal.savedAmount}</p>
                                <p><strong>Deadline:</strong> {goal.deadline}</p>
                                <p className={styles.status}>
                                    {goal.savedAmount >= goal.targetAmount ? "Completed" : "In Progress"}
                                </p>
                                <button className={styles.editBtn} onClick={() => handleEdit(goal)}>
                                    Edit
                                </button>
                                <button className={styles.deleteBtn} onClick={() => handleDelete(goal.id)}>
                                    Delete
                                </button>
                            </div>
                        )}
                    </div>
                ))}
            </div>

            {editingGoal && (
                <div className={styles.modal}>
                    <h2>Edit Goal</h2>
                    <label>Name:</label>
                    <input
                        type="text"
                        value={editingGoal.name}
                        onChange={(e) => setEditingGoal({ ...editingGoal, name: e.target.value })}
                    />
                    <label>Target Amount:</label>
                    <input
                        type="number"
                        value={editingGoal.targetAmount}
                        onChange={(e) =>
                            setEditingGoal({ ...editingGoal, targetAmount: e.target.value })
                        }
                    />
                    <label>Saved Amount:</label>
                    <input
                        type="number"
                        value={editingGoal.savedAmount}
                        onChange={(e) =>
                            setEditingGoal({ ...editingGoal, savedAmount: e.target.value })
                        }
                    />
                    <label>Deadline:</label>
                    <input
                        type="date"
                        value={editingGoal.deadline}
                        onChange={(e) =>
                            setEditingGoal({ ...editingGoal, deadline: e.target.value })
                        }
                    />
                    <button onClick={handleUpdateGoal}>Save</button>
                    <button onClick={() => setEditingGoal(null)}>Cancel</button>
                </div>
            )}
        </div>
    );
};

export default TrackGoals;