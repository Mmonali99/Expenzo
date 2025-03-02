import React from "react";
import { NavLink } from "react-router-dom";
import styles from "./Dashboard.module.css";

const Dashboard = ({ children }) => {
    return (
        <div className={styles.container}>
            <header className={styles.header}>Expenzo</header>
            <div className={styles.layout}>
                <nav className={styles.sidebar}>
                    <ul>
                        <li>
                            <NavLink to="/dashboard/expense/add" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Expense Hub
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/dashboard/expense/track" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Spending Insights
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/dashboard/budget/track" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Budgets Monitor
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/dashboard/budget/set" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Smart Budget
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/dashboard/goals/add" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Goal Setter
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/dashboard/goals/track" className={({ isActive }) => (isActive ? styles.active : "")}>
                                Milestone Tracker
                            </NavLink>
                        </li>
                    </ul>
                </nav>
                <main className={styles.content}>{children}</main>
            </div>
            <footer className={styles.footer}>© 2025 Expenzo. All rights reserved.</footer>
        </div>
    );
};

export default Dashboard;