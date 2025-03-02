import React, { useState } from "react";
import styles from "./AddExpense.module.css";
import { addExpense } from "../../api/api";

const AddExpense = () => {
    const getCurrentTime = () => {
        const now = new Date();
        return now.toISOString().slice(11, 16);
    };

    const [amount, setAmount] = useState("");
    const [purchaseLocation, setPurchaseLocation] = useState("");
    const [items, setItems] = useState([{ name: "", category: "", amount: "" }]);
    const [date, setDate] = useState("");
    const [time, setTime] = useState(getCurrentTime());
    const [description, setDescription] = useState("");
    const [error, setError] = useState("");

    const handleAddItem = () => {
        setItems([...items, { name: "", category: "", amount: "" }]);
    };

    const handleItemChange = (index, field, value) => {
        const newItems = [...items];
        newItems[index][field] = value;
        setItems(newItems);
    };

    const handleDeleteItem = (index) => {
        if (items.length > 1) {
            setItems(items.filter((_, i) => i !== index));
        }
    };

    const validateForm = () => {
        if (!amount) return "Amount is required.";
        if (parseFloat(amount) <= 0) return "Amount must be greater than 0.";
        if (!date) return "Please select a date.";
        if (new Date(date) > new Date()) return "Expense date cannot be in the future.";
        if (!time) return "Please select a time.";
        if (items.some((item) => !item.name)) return "Item name cannot be empty.";
        if (items.some((item) => !item.category)) return "Please select a category.";
        if (items.some((item) => !item.amount || parseFloat(item.amount) <= 0))
            return "Item amount must be greater than 0.";
        if (description.length > 200) return "Description cannot exceed 200 characters.";
        return "";
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationError = validateForm();
        if (validationError) {
            setError(validationError);
            return;
        }
        try {
            const expenseData = {
                amount: parseFloat(amount),
                purchaseLocation,
                items: items.map((item) => ({
                    name: item.name,
                    category: item.category,
                    amount: parseFloat(item.amount),
                })),
                date,
                time,
                description,
            };
            await addExpense(expenseData);
            setAmount("");
            setPurchaseLocation("");
            setItems([{ name: "", category: "", amount: "" }]);
            setDate("");
            setTime(getCurrentTime());
            setDescription("");
            setError("");
            alert("Expense saved successfully!"); // Added success feedback
        } catch (error) {
            setError("Failed to save expense: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className={styles.addExpenseContainer}>
            <h2>Add Expense</h2>
            {error && <p className={styles.errorMessage}>{error}</p>}

            <form onSubmit={handleSubmit}>
                <label>Amount:</label>
                <input
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    placeholder="Enter amount"
                />

                <label>Purchase Location:</label>
                <input
                    type="text"
                    value={purchaseLocation}
                    onChange={(e) => setPurchaseLocation(e.target.value)}
                    placeholder="Enter location"
                />

                <label>Items:</label>
                {items.map((item, index) => (
                    <div key={index} className={styles.itemRow}>
                        <input
                            type="text"
                            placeholder="Item Name"
                            value={item.name}
                            onChange={(e) => handleItemChange(index, "name", e.target.value)}
                        />
                        <select
                            value={item.category}
                            onChange={(e) => handleItemChange(index, "category", e.target.value)}
                        >
                            <option value="">Select Category</option>
                            <option value="Food">Food</option>
                            <option value="Transport">Transport</option>
                            <option value="Entertainment">Entertainment</option>
                            <option value="Shopping">Shopping</option>
                            <option value="Others">Others</option>
                        </select>
                        <input
                            type="number"
                            placeholder="Item Amount"
                            value={item.amount}
                            onChange={(e) => handleItemChange(index, "amount", e.target.value)}
                        />
                        {items.length > 1 && (
                            <button type="button" onClick={() => handleDeleteItem(index)}>
                                ❌
                            </button>
                        )}
                    </div>
                ))}
                <button type="button" onClick={handleAddItem}>
                    + Add Item
                </button>

                <label>Date:</label>
                <input
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                />

                <label>Time:</label>
                <input
                    type="time"
                    value={time}
                    onChange={(e) => setTime(e.target.value)}
                />

                <label>Description:</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Enter description"
                />

                <button type="submit">Save Expense</button>
            </form>
        </div>
    );
};

export default AddExpense;