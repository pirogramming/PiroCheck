import React from 'react';
import { useNavigate, useLocation  } from 'react-router-dom';
import { ArrowLeft, Wallet } from 'lucide-react';
import './componentsCss/Header.css';

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const path = location.pathname;

  let title = "ATTENDANCE CHECK";
  if (path.includes('assignment')) title = "ASSIGNMENT CHECK";
  else if (path.includes('deposit')) title = "DEPOSIT";

  const showRightButton = !path.includes('deposit');

  return (
    <header className="header-container">
      <button className="icon-button" onClick={() => navigate(-1)} aria-label="뒤로가기">
        <ArrowLeft size={30} />
      </button>
      <h1 className="header-title">{title}</h1>
      {showRightButton ? (
        <button className="icon-button" onClick={() => navigate('/deposit')} aria-label="입금 페이지 이동">
          <Wallet size={30} />
        </button>
      ) : (
        <div style={{ width: '30px' }} /> // 오른쪽 공백 유지
      )}
    </header>
  );
};

export default Header;