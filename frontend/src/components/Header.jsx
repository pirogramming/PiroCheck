import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./componentsCss/Header.css";
const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname;

  let title = "ATTENDANCE\nCHECK";
  if (path.includes("assignment")) title = "ASSIGNMENT\nCHECK";
  else if (path.includes("deposit")) title = "DEPOSIT";
  else if (path.includes("attendance")) title = "ATTENDANCE\nCHECK";
  else if (path.includes("managestudent")) title = "수강생 관리";
  else if (path.includes("managetask")) title = "과제 관리";
  else if (path.includes("attendancecode")) title = "출석코드 생성";

  const showRightDeposit =
    !path.includes("deposit") &&
    !path.includes("managestudent") &&
    !path.includes("managetask"); // 수강생 관리, 과제 관리 페이지 추가

  const showRightMagageStudent = path.includes("attendancecode");

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
      {showRightDeposit ? (
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
      {showRightMagageStudent ? (
        <button
          className="icon-button"
          onClick={() => navigate("/managestudent")}
          aria-label="수강생 관리 페이지 이동"
        >
          <img
            src="/assets/img/managestudent.svg"
            alt="MagageStudent"
            width={30}
            height={30}
          />
        </button>
      ) : null}
    </header>
  );
};

export default Header;
