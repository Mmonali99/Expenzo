import React, { useState, useEffect } from "react";
import styles from "./TrackBudgets.module.css";
import { getBudgets, getBudgetWithSpent, deleteBudget, updateBudget } from "../../api/api";

const TrackBudget = () => {
    const [budgets, setBudgets] = useState([]);
    const [filter, setFilter] = useState("monthly");
    const [error, setError] = useState("");
    const [editingBudget, setEditingBudget] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        fetchBudgets();
    }, []);

    const fetchBudgets = async () => {
        setLoading(true);
        try {
            const response = await getBudgets();
            const budgetsWithSpent = await Promise.all(
                response.data.map(async (budget) => {
                    const spentResponse = await getBudgetWithSpent(budget.id);
                    return { ...budget, spent: spentResponse.data.spent || 0 };
                })
            );
            setBudgets(budgetsWithSpent);
            setError("");
        } catch (error) {
            setError("Failed to fetch budgets: " + (error.response?.data || error.message));
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (budget) => {
        setEditingBudget({ ...budget });
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this budget?")) {
            try {
                await deleteBudget(id);
                setBudgets(budgets.filter((budget) => budget.id !== id));
                setError("");
            } catch (error) {
                setError("Failed to delete budget: " + (error.response?.data || error.message));
            }
        }
    };

    const handleUpdateBudget = async () => {
        if (!editingBudget.totalBudgetAmount || !editingBudget.month || !editingBudget.year) {
            setError("Please fill in all required fields");
            return;
        }
        try {
            await updateBudget(editingBudget.id, {
                totalBudgetAmount: parseFloat(editingBudget.totalBudgetAmount),
                month: parseInt(editingBudget.month),
                year: parseInt(editingBudget.year),
                budgetCategories: editingBudget.budgetCategories,
            });
            setBudgets(budgets.map((budget) => (budget.id === editingBudget.id ? editingBudget : budget)));
            setEditingBudget(null);
            setError("");
        } catch (error) {
            setError("Failed to update budget: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className="container">
            <h1>Budget Monitor</h1>
            {error && <p className={styles.error}>{error}</p>}
            <div className={styles.filterOptions}>
                <button
                    onClick={() => setFilter("weekly")}
                    className={filter === "weekly" ? styles.active : ""}
                >
                    Weekly
                </button>
                <button
                    onClick={() => setFilter("monthly")}
                    className={filter === "monthly" ? styles.active : ""}
                >
                    Monthly
                </button>
            </div>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <table className={styles.budgetTable}>
                    <thead>
                        <tr>
                            <th>Category</th>
                            <th>Spent %</th>
                            <th>Spent Amount</th>
                            <th>Remaining %</th>
                            <th>Remaining Amount</th>
                            <th>Total Allocated</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {budgets.flatMap((budget) =>
                            budget.budgetCategories.map((bc, index) => {
                                const allocated = bc.allocatedAmount;
                                const spentPerCategory = budget.spent / budget.budgetCategories.length;
                                const spentPercentage = ((spentPerCategory / allocated) * 100).toFixed(2);
                                const remainingAmount = allocated - spentPerCategory;
                                const remainingPercentage = ((remainingAmount / allocated) * 100).toFixed(2);

                                return (
                                    <tr key={`${budget.id}-${index}`}>
                                        <td>{bc.category.name}</td>
                                        <td>{spentPercentage}%</td>
                                        <td>₹{spentPerCategory.toFixed(2)}</td>
                                        <td>{remainingPercentage}%</td>
                                        <td>₹{remainingAmount.toFixed(2)}</td>
                                        <td>₹{allocated.toFixed(2)}</td>
                                        <td>
                                            <button onClick={() => handleEdit(budget)}>✏️</button>
                                            <button onClick={() => handleDelete(budget.id)}>🗑️</button>
                                        </td>
                                    </tr>
                                );
                            })
                        )}
                    </tbody>
                </table>
            )}

            {editingBudget && (

                <div className={styles.modal}>
                    <h2>Edit Budget</h2>
                    <label>Total Budget Amount:</label>
                    <input
                        type="number"
                        value={editingBudget.totalBudgetAmount}
                        onChange={(e) => setEditingBudget({ ...editingBudget, totalBudgetAmount: e.target.value })}
                    />
                    <label>Month:</label>
                    <input
                        type="number"
                        value={editingBudget.month}
                        onChange={(e) => setEditingBudget({ ...editingBudget, month: e.target.value })}
                    />
                    <label>Year:</label>
                    <input
                        type="number"
                        value={editingBudget.year}
                        onChange={(e) => setEditingBudget({ ...editingBudget, year: e.target.value })} // Fixed typo
                    />
                    <button onClick={handleUpdateBudget}>Save</button>
                    <button onClick={() => setEditingBudget(null)}>Cancel</button>
            </div>
            )}
        </div>
    );
};

export default TrackBudget;