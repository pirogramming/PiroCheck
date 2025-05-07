import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Intro.module.css";

const Intro = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/login");
    }, 2000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className={styles.intro_container}>
      <div className={styles.intro}>
        <h1 className={styles.pirocheck}>PIROCHECK</h1>
      </div>
    </div>
  );
};

export default Intro;
