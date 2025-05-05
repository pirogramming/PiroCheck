import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import homeLogo from "./assets/img/home.svg";

const Home = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/login");
    }, 2000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="home">
      <div className="home__container">
        <h1>PIROCHECK</h1>
        <img src={homeLogo} alt="PiroCheck Logo" className="home__logo" />
      </div>
    </div>
  );
};

export default Home;
