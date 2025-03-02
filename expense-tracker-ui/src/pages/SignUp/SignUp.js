import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SignUp.module.css";
import { register, login } from "../../api/api";

const SignUp = ({ handleSignupSuccess }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            setError("Passwords do not match!");
            return;
        }
        try {
            // Check if user exists
            try {
                await login({ username, password });
                setError("User already exists. Please sign in.");
                if (handleSignupSuccess) handleSignupSuccess();
                return;
            } catch (loginError) {
                // User doesn’t exist, proceed with signup
                await register({ username, password });
                // Auto-sign in for first-time users
                const loginResponse = await login({ username, password });
                localStorage.setItem("token", loginResponse.data);
                localStorage.setItem("username", username);
                navigate("/dashboard");
            }
        } catch (error) {
            setError("Registration failed: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.heading}>Sign Up</h2>
            {error && <p className={styles.error}>{error}</p>}
            <form onSubmit={handleSubmit} className={styles.form}>
                <div className={styles.inputGroup}>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder="Enter Username"
                        required
                    />
                </div>
                <div className={styles.inputGroup}>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Enter Password"
                        required
                    />
                </div>
                <div className={styles.inputGroup}>
                    <label>Confirm Password:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        placeholder="Confirm Password"
                        required
                    />
                </div>
                <button type="submit" className={styles.button}>
                    Sign Up
                </button>
            </form>
        </div>
    );
};

export default SignUp;