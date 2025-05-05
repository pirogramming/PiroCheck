import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Home.module.css";
import logo from "./assets/img/logo.svg";

const Home = () => {
  const navigate = useNavigate();
  return (
    <div className={styles.home_container}>
      <div className={styles.home}>
        <h1 className={styles.pirocheck}>PIROCHECK</h1>
        <button
          className={styles.button}
          onClick={() => navigate("/assignment")}
        >
          <p>ASSIGNMENT</p>
          <p>CHECK</p>
        </button>
        <button
          className={styles.button}
          onClick={() => navigate("/attendance")}
        >
          <p>ATTENDANCE</p>
          <p>CHECK</p>
        </button>
        <img src={logo} alt="로고" />
      </div>
    </div>
  );
};

export default Home;
