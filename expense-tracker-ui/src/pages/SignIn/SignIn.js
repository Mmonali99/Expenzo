import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SignIn.module.css";
import { login } from "../../api/api";

const SignIn = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(""); // Added error state
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await login({ username, password });
            localStorage.setItem("token", response.data);
            localStorage.setItem("username", username);
            navigate("/dashboard");
        } catch (error) {
            setError("Login failed: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.heading}>Sign In</h2>
            {error && <p className={styles.error}>{error}</p>} {/* Styled error display */}
            <form onSubmit={handleSubmit} className={styles.form}>
                <div className={styles.inputGroup}>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.inputGroup}>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className={styles.button}>
                    Sign In
                </button>
            </form>
            <p className={styles.forgotPassword}>
                <button
                    onClick={() => navigate("/forgot-password")}
                    className={styles.linkButton}
                >
                    Forgot Password?
                </button>
            </p>
        </div>
    );
};

export default SignIn;