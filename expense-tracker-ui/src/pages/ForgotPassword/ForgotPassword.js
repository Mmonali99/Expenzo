import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./ForgotPassword.module.css";
import { forgotPassword } from "../../api/api";

const ForgotPassword = () => {
    const [username, setUsername] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState(""); // Added error state
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            setError("Passwords do not match!");
            return;
        }
        try {
            await forgotPassword({ username, newPassword, confirmPassword });
            alert("Password reset successfully!"); // Added success feedback
            navigate("/signin");
        } catch (error) {
            setError("Reset failed: " + (error.response?.data || error.message));
        }
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.heading}>Forgot Password</h2>
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
                    <label>New Password:</label>
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.inputGroup}>
                    <label>Confirm Password:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className={styles.button}>
                    Reset Password
                </button>
            </form>
        </div>
    );
};

export default ForgotPassword;