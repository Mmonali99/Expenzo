import React, { useState } from "react";
import SignIn from "../SignIn/SignIn";
import SignUp from "../SignUp/SignUp";
import ForgotPassword from "../ForgotPassword/ForgotPassword";
import styles from "./Home.module.css";

const Home = () => {
    const [view, setView] = useState("signIn");
    const [message, setMessage] = useState("");

    const handleSignupSuccess = () => {
        setView("signIn");
        setMessage("Please sign in now.");
    };

    return (
        <div className={styles.container}>
            <header className={styles.header}>Welcome to Expenzo</header>

            <div className={styles.authBox}>
                {message ? (
                    <p className={styles.message}>{message}</p>
                ) : (
                    <>
                        {view !== "forgotPassword" && (
                            <div className={styles.toggleContainer}>
                                <button
                                    className={`${styles.toggleButton} ${view === "signIn" ? styles.active : ""}`}
                                    onClick={() => setView("signIn")}
                                >
                                    Sign In
                                </button>
                                <button
                                    className={`${styles.toggleButton} ${view === "signUp" ? styles.active : ""}`}
                                    onClick={() => setView("signUp")}
                                >
                                    Sign Up
                                </button>
                            </div>
                        )}
                    </>
                )}

                <div className={styles.formContainer}>
                    {view === "signIn" && <SignIn />}
                    {view === "signUp" && <SignUp handleSignupSuccess={handleSignupSuccess} />}
                    {view === "forgotPassword" && <ForgotPassword />}
                </div>
            </div>

            <footer className={styles.footer}>© 2025 Expenzo. All rights reserved.</footer>
        </div>
    );
};

export default Home;