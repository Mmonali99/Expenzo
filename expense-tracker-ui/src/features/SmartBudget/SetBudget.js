import React, { useState, useEffect } from "react";
import styles from "./SetBudget.module.css";
import { addBudget, addBudgetCategory } from "../../api/api";

const SetBudget = () => {
    const [totalBudget, setTotalBudget] = useState("");
    const [monthYear, setMonthYear] = useState("");
    const [categories, setCategories] = useState([
        { name: "Food", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
        { name: "Rent", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
        { name: "Shopping", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
        { name: "Entertainment", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
    ]);
    const [newCategory, setNewCategory] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        const today = new Date();
        const formattedDate = today.toISOString().slice(0, 7);
        setMonthYear(formattedDate);
    }, []);

    const handleCategoryChange = (index, field, value) => {
        const updatedCategories = [...categories];
        let category = updatedCategories[index];
        category[field] = value;

        if (field === "weeklyPercentage") {
            category.weeklyAmount = ((totalBudget * value) / 100 / 4).toFixed(2);
            category.monthlyPercentage = (value * 4).toFixed(2);
            category.monthlyAmount = ((totalBudget * category.monthlyPercentage) / 100).toFixed(2);
        } else if (field === "weeklyAmount") {
            category.weeklyPercentage = ((value * 4 * 100) / totalBudget).toFixed(2);
            category.monthlyPercentage = (category.weeklyPercentage * 4).toFixed(2);
            category.monthlyAmount = ((totalBudget * category.monthlyPercentage) / 100).toFixed(2);
        } else if (field === "monthlyPercentage") {
            category.monthlyAmount = ((totalBudget * value) / 100).toFixed(2);
            category.weeklyPercentage = (value / 4).toFixed(2);
            category.weeklyAmount = ((totalBudget * category.weeklyPercentage) / 100 / 4).toFixed(2);
        } else if (field === "monthlyAmount") {
            category.monthlyPercentage = ((value * 100) / totalBudget).toFixed(2);
            category.weeklyPercentage = (category.monthlyPercentage / 4).toFixed(2);
            category.weeklyAmount = ((totalBudget * category.weeklyPercentage) / 100 / 4).toFixed(2);
        }

        setCategories(updatedCategories);
    };

    const addCategory = () => {
        if (newCategory.trim()) {
            setCategories([
                ...categories,
                { name: newCategory, weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
            ]);
            setNewCategory("");
        }
    };

    const handleSave = async () => {
        if (!totalBudget || parseFloat(totalBudget) <= 0) {
            setError("Total budget amount must be greater than 0.");
            return;
        }
        if (!monthYear) {
            setError("Please select a month and year.");
            return;
        }
        if (categories.some((cat) => !cat.name)) {
            setError("All categories must have a name.");
            return;
        }
        if (categories.some((cat) => cat.monthlyAmount && parseFloat(cat.monthlyAmount) < 0)) {
            setError("Category amounts cannot be negative.");
            return;
        }

        try {
            const budgetData = {
                totalBudgetAmount: parseFloat(totalBudget),
                month: parseInt(monthYear.split("-")[1]),
                year: parseInt(monthYear.split("-")[0]),
                budgetCategories: [],
            };
            const response = await addBudget(budgetData);
            const budgetId = response.data.id;

            for (const category of categories) {
                await addBudgetCategory(budgetId, category.name, parseFloat(category.monthlyAmount || 0));
            }

            alert("Budget Saved Successfully!");
            setTotalBudget("");
            setMonthYear(new Date().toISOString().slice(0, 7));
            setCategories([
                { name: "Food", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
                { name: "Rent", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
                { name: "Shopping", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
                { name: "Entertainment", weeklyPercentage: "", weeklyAmount: "", monthlyPercentage: "", monthlyAmount: "" },
            ]);
            setNewCategory("");
            setError("");
        } catch (error) {
            setError("Failed to save budget: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className="container">
            <h2 className={styles.title}>Smart Budget</h2>
            {error && <p className={styles.error}>{error}</p>}

            <div className={styles.budgetInputs}>
                <label>Total Budget Amount:</label>
                <input
                    type="number"
                    value={totalBudget}
                    onChange={(e) => setTotalBudget(e.target.value)}
                    required
                />

                <label>Month and Year:</label>
                <input
                    type="month"
                    value={monthYear}
                    onChange={(e) => setMonthYear(e.target.value)}
                    required
                />
            </div>

            <table className={styles.budgetTable}>
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Weekly %</th>
                        <th>Weekly Amount</th>
                        <th>Monthly %</th>
                        <th>Monthly Amount</th>
                    </tr>
                </thead>
                <tbody>
                    {categories.map((category, index) => (
                        <tr key={index}>
                            <td>{category.name}</td>
                            <td>
                                <input
                                    type="number"
                                    value={category.weeklyPercentage}
                                    onChange={(e) =>
                                        handleCategoryChange(index, "weeklyPercentage", e.target.value)
                                    }
                                />
                            </td>
                            <td>
                                <input
                                    type="number"
                                    value={category.weeklyAmount}
                                    onChange={(e) =>
                                        handleCategoryChange(index, "weeklyAmount", e.target.value)
                                    }
                                />
                            </td>
                            <td>
                                <input
                                    type="number"
                                    value={category.monthlyPercentage}
                                    onChange={(e) =>
                                        handleCategoryChange(index, "monthlyPercentage", e.target.value)
                                    }
                                />
                            </td>
                            <td>
                                <input
                                    type="number"
                                    value={category.monthlyAmount}
                                    onChange={(e) =>
                                        handleCategoryChange(index, "monthlyAmount", e.target.value)
                                    }
                                />
                            </td>
                        </tr>
                    ))}
                </tbody>
                <tfoot>
                    <tr>
                        <td><strong>Total</strong></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <strong>
                                ₹{categories.reduce((sum, cat) => sum + Number(cat.monthlyAmount || 0), 0)}
                            </strong>
                        </td>
                    </tr>
                </tfoot>
            </table>

            <div className={styles.addCategory}>
                <input
                    type="text"
                    value={newCategory}
                    onChange={(e) => setNewCategory(e.target.value)}
                    placeholder="New Category"
                />
                <button onClick={addCategory}>OK</button>
            </div>

            <button className={styles.saveBtn} onClick={handleSave}>
                Save Budget
            </button>
        </div>
    );
};

export default SetBudget;