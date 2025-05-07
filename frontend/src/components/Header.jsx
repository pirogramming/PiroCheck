import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, Wallet } from "lucide-react";
import "./componentsCss/Header.css";
const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname;

  let title = "ATTENDANCE\nCHECK";
  if (path.includes("assignment")) title = "ASSIGNMENT\nCHECK";
  else if (path.includes("deposit")) title = "DEPOSIT";
  else if (path.includes("attendance")) title = "ATTENDANCE\nCHECK";

  const showRightButton = !path.includes("deposit");

  return (
    <header className="header-container">
      <button
        className="icon-button"
        onClick={() => navigate(-1)}
        aria-label="뒤로가기"
      >
        <img
          src="/assets/img/arrowicon.svg"
          alt="Back"
          width={34}
          height={34}
        />
      </button>
      <h1 className="header-title">{title}</h1>
      {showRightButton ? (
        <button
          className="icon-button"
          onClick={() => navigate("/deposit")}
          aria-label="보증금 페이지 이동"
        >
          <img
            src="/assets/img/moneyicon.svg"
            alt="Deposit"
            width={30}
            height={30}
          />
        </button>
      ) : (
        <div style={{ width: "30px" }} />
      )}
    </header>
  );
};

export default Header;
