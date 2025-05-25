import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./componentsCss/Header.css";

const AdminStudentHeader = ({ studentName = "default", subtitle = "default", onBack }) => {
  const navigate = useNavigate();
  const { studentId } = useParams();

  return (
    <div className="header-container">
      <button className="icon-button" onClick={onBack}>
        <img
          src="/assets/img/arrowicon.svg"
          alt="Back"
          width={34}
          height={34}
        />
      </button>

      <h1 className="header-title">
        {studentName}
        {subtitle && ` ${subtitle}`}
      </h1>

      <button
        className="icon-button"
        onClick={() => navigate(`/managestudent`)}
      >
        👥
      </button>
    </div>
  );
};

export default AdminStudentHeader;
