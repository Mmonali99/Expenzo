import axios from "axios";

const API = axios.create({ baseURL: process.env.REACT_APP_API_URL || "http://localhost:8080" });

API.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

// Response interceptor to handle ApiResponse structure from backend
API.interceptors.response.use(
    (response) => {
        // If backend returns ApiResponse, extract data if success is true
        if (response.data && typeof response.data.success === "boolean") {
            if (response.data.success) {
                return { ...response, data: response.data.data };
            } else {
                return Promise.reject(new Error(response.data.message || "API request failed"));
            }
        }
        return response;
    },
    (error) => Promise.reject(error)
);

export const register = (user) => API.post("/auth/signup", user);
export const login = (user) => API.post("/auth/signin", user);
export const forgotPassword = (data) => API.post("/auth/forgot-password", data);
export const addExpense = (expense) => API.post("/api/expenses", expense);
export const getExpenses = (query = "") => API.get(`/api/expenses${query}`);
export const deleteExpense = (id) => API.delete(`/api/expenses/${id}`);
export const updateExpense = (id, expense) => API.put(`/api/expenses/${id}`, expense);
export const addBudget = (budget) => API.post("/api/budgets", budget);
export const getBudgets = () => API.get("/api/budgets");
export const getBudgetWithSpent = (id) => API.get(`/api/budgets/${id}/with-spent`);
export const deleteBudget = (id) => API.delete(`/api/budgets/${id}`);
export const updateBudget = (id, budget) => API.put(`/api/budgets/${id}`, budget);
export const addBudgetCategory = (budgetId, categoryName, allocatedAmount) =>
    API.post(`/api/budgets/${budgetId}/categories/${categoryName}`, null, { params: { allocatedAmount } });
export const addGoal = (goal) => API.post("/api/goals", goal);
export const getGoals = () => API.get("/api/goals");
export const deleteGoal = (id) => API.delete(`/api/goals/${id}`);
export const updateGoal = (id, goal) => API.put(`/api/goals/${id}`, goal);