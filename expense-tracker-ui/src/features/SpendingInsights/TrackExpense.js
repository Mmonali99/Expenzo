import React, { useState, useEffect } from "react";
import styles from "./TrackExpense.module.css";
import { FaFilter, FaAngleDown, FaAngleUp, FaTrash, FaEdit } from "react-icons/fa";
import { getExpenses, deleteExpense, updateExpense } from "../../api/api";

export default function TrackExpense() {
    const [expenses, setExpenses] = useState([]);
    const [showFilters, setShowFilters] = useState(false);
    const [expandedFilter, setExpandedFilter] = useState(null);
    const [expandedRows, setExpandedRows] = useState({});
    const [filterParams, setFilterParams] = useState({
        startDate: "",
        endDate: "",
        category: "",
        minAmount: "",
        maxAmount: "",
    });
    const [error, setError] = useState("");
    const [editingExpense, setEditingExpense] = useState(null);

    useEffect(() => {
        fetchExpenses();
    }, []);

    const fetchExpenses = (query = "") => {
        getExpenses(query)
            .then((response) => {
                const data = Array.isArray(response.data) ? response.data : [];
                setExpenses(data.map((expense) => ({ ...expense, items: expense.items || [] })));
                setError("");
            })
            .catch((error) => setError("Failed to fetch expenses: " + (error.response?.data || error.message)));
    };

    const toggleExpandRow = (id) => setExpandedRows((prev) => ({ ...prev, [id]: !prev[id] }));
    const toggleFilter = (filter) => setExpandedFilter(expandedFilter === filter ? null : filter);

    const handleFilterChange = (field, value) => {
        setFilterParams((prev) => ({ ...prev, [field]: value }));
    };

    const applyFilters = () => {
        const queryParams = new URLSearchParams();
        if (filterParams.startDate) queryParams.append("startDate", filterParams.startDate);
        if (filterParams.endDate) queryParams.append("endDate", filterParams.endDate);
        if (filterParams.category) queryParams.append("category", filterParams.category);
        if (filterParams.minAmount) queryParams.append("minAmount", filterParams.minAmount);
        if (filterParams.maxAmount) queryParams.append("maxAmount", filterParams.maxAmount);

        fetchExpenses(queryParams.toString() ? `/filter?${queryParams}` : "");
    };

    const handleEdit = (expense) => {
        setEditingExpense({ ...expense, items: [...expense.items] });
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this expense?")) {
            try {
                await deleteExpense(id);
                setExpenses(expenses.filter((expense) => expense.id !== id));
                setError("");
            } catch (error) {
                setError("Failed to delete expense: " + (error.response?.data || error.message));
            }
        }
    };

    const handleUpdateExpense = async () => {
        if (!editingExpense.amount || !editingExpense.date || !editingExpense.time) {
            setError("Please fill in all required fields (Amount, Date, Time)");
            return;
        }
        if (parseFloat(editingExpense.amount) <= 0) {
            setError("Amount must be greater than 0");
            return;
        }
        if (new Date(editingExpense.date) > new Date()) {
            setError("Expense date cannot be in the future");
            return;
        }
        if (editingExpense.items.some((item) => !item.name || !item.category || !item.amount || parseFloat(item.amount) <= 0)) {
            setError("All items must have a name, category, and positive amount");
            return;
        }
    
        try {
            await updateExpense(editingExpense.id, {
                amount: parseFloat(editingExpense.amount),
                purchaseLocation: editingExpense.purchaseLocation,
                date: editingExpense.date,
                time: editingExpense.time,
                description: editingExpense.description,
                items: editingExpense.items,
            });
            setExpenses(
                expenses.map((expense) => (expense.id === editingExpense.id ? editingExpense : expense))
            );
            setEditingExpense(null);
            setError("");
            alert("Expense updated successfully!"); // Added success feedback
        } catch (error) {
            setError("Failed to update expense: " + (error.response?.data || error.message));
        }
    };

    const handleItemChange = (index, field, value) => {
        const newItems = [...editingExpense.items];
        newItems[index][field] = value;
        setEditingExpense({ ...editingExpense, items: newItems });
    };

    return (
        <div className={styles.container}>
            <h1>Track Expenses</h1>
            {error && <p className={styles.error}>{error}</p>}
            <div className={styles.filterSection}>
                <button onClick={() => setShowFilters(!showFilters)}>
                    <FaFilter /> {showFilters ? <FaAngleUp /> : <FaAngleDown />}
                </button>
            </div>

            {showFilters && (
                <div className={styles.filterSection}>
                    <div>
                        <button onClick={() => toggleFilter("date")}>Date Range</button>
                        {expandedFilter === "date" && (
                            <div>
                                <label>From:</label>
                                <input
                                    type="date"
                                    value={filterParams.startDate}
                                    onChange={(e) => handleFilterChange("startDate", e.target.value)}
                                />
                                <label>To:</label>
                                <input
                                    type="date"
                                    value={filterParams.endDate}
                                    onChange={(e) => handleFilterChange("endDate", e.target.value)}
                                />
                            </div>
                        )}
                    </div>
                    <div>
                        <button onClick={() => toggleFilter("category")}>Category</button>
                        {expandedFilter === "category" && (
                            <select
                                value={filterParams.category}
                                onChange={(e) => handleFilterChange("category", e.target.value)}
                            >
                                <option value="">Select Category</option>
                                <option value="Food">Food</option>
                                <option value="Transport">Transport</option>
                                <option value="Entertainment">Entertainment</option>
                                <option value="Shopping">Shopping</option>
                                <option value="Others">Others</option>
                            </select>
                        )}
                    </div>
                    <div>
                        <button onClick={() => toggleFilter("amount")}>Amount Range</button>
                        {expandedFilter === "amount" && (
                            <div>
                                <label>Min:</label>
                                <input
                                    type="number"
                                    placeholder="Min Amount"
                                    value={filterParams.minAmount}
                                    onChange={(e) => handleFilterChange("minAmount", e.target.value)}
                                />
                                <label>Max:</label>
                                <input
                                    type="number"
                                    placeholder="Max Amount"
                                    value={filterParams.maxAmount}
                                    onChange={(e) => handleFilterChange("maxAmount", e.target.value)}
                                />
                            </div>
                        )}
                    </div>
                    <button onClick={applyFilters}>Apply Filters</button>
                </div>
            )}

            <div>
                <table className={styles.expenseList}>
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Amount</th>
                            <th>Location</th>
                            <th>More</th>
                        </tr>
                    </thead>
                    <tbody>
                        {expenses.map((expense) => (
                            <React.Fragment key={expense.id}>
                                <tr>
                                    <td>{expense.date || "N/A"}</td>
                                    <td>{expense.time || "N/A"}</td>
                                    <td>${expense.amount?.toFixed(2) || "0.00"}</td>
                                    <td>{expense.purchaseLocation || "N/A"}</td>
                                    <td>
                                        <button onClick={() => toggleExpandRow(expense.id)}>
                                            {expandedRows[expense.id] ? <FaAngleUp /> : <FaAngleDown />}
                                        </button>
                                    </td>
                                </tr>
                                {expandedRows[expense.id] && !editingExpense && (
                                    <tr>
                                        <td colSpan="5">
                                            <p>{expense.description || "No description"}</p>
                                            <table className={styles.innerTable}>
                                                <thead>
                                                    <tr>
                                                        <th>Item Name</th>
                                                        <th>Category</th>
                                                        <th>Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {expense.items.length > 0 ? (
                                                        expense.items.map((item, index) => (
                                                            <tr key={index}>
                                                                <td>{item.name || "N/A"}</td>
                                                                <td>{item.category || "N/A"}</td>
                                                                <td>${item.amount?.toFixed(2) || "0.00"}</td>
                                                            </tr>
                                                        ))
                                                    ) : (
                                                        <tr>
                                                            <td colSpan="3">No items available</td>
                                                        </tr>
                                                    )}
                                                </tbody>
                                            </table>
                                            <div className={styles.buttons}>
                                                <button
                                                    className={styles.editButton}
                                                    onClick={() => handleEdit(expense)}
                                                >
                                                    <FaEdit />
                                                </button>
                                                <button
                                                    className={styles.deleteButton}
                                                    onClick={() => handleDelete(expense.id)}
                                                >
                                                    <FaTrash />
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))}
                    </tbody>
                </table>
            </div>

            {editingExpense && (
                <div className={styles.modal}>
                    <h2>Edit Expense</h2>
                    <label>Amount:</label>
                    <input
                        type="number"
                        value={editingExpense.amount}
                        onChange={(e) => setEditingExpense({ ...editingExpense, amount: e.target.value })}
                    />
                    <label>Purchase Location:</label>
                    <input
                        type="text"
                        value={editingExpense.purchaseLocation}
                        onChange={(e) =>
                            setEditingExpense({ ...editingExpense, purchaseLocation: e.target.value })
                        }
                    />
                    <label>Date:</label>
                    <input
                        type="date"
                        value={editingExpense.date}
                        onChange={(e) => setEditingExpense({ ...editingExpense, date: e.target.value })}
                    />
                    <label>Time:</label>
                    <input
                        type="time"
                        value={editingExpense.time}
                        onChange={(e) => setEditingExpense({ ...editingExpense, time: e.target.value })}
                    />
                    <label>Description:</label>
                    <textarea
                        value={editingExpense.description}
                        onChange={(e) =>
                            setEditingExpense({ ...editingExpense, description: e.target.value })
                        }
                    />
                    <label>Items:</label>
                    {editingExpense.items.map((item, index) => (
                        <div key={index} className={styles.itemRow}>
                            <input
                                type="text"
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
                                value={item.amount}
                                onChange={(e) => handleItemChange(index, "amount", e.target.value)}
                            />
                        </div>
                    ))}
                    <button onClick={handleUpdateExpense}>Save</button>
                    <button onClick={() => setEditingExpense(null)}>Cancel</button>
                </div>
            )}
        </div>
    );
}