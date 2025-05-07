import React, { useState } from "react";
import "./componentsCss/DailyOpenBlock.css";
import "./componentsCss/WeeklyListBlock.css";
import DailyOpenBlock from "./DailyOpenBlock";

const WeeklyOpenBlock = ({ label, days }) => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className={`weekly-wrapper ${isOpen ? "active" : ""}`}>
      <div className="weekly-header">
        <button className="weekly-button" onClick={toggleOpen}>
          <span className="weekly-label">{label}</span>
          <span className={`arrow ${isOpen ? "rotate" : ""}`}>▾</span>
        </button>
        {isOpen && (
          <button className="close-button" onClick={toggleOpen}>✕</button>
        )}
      </div>

      {isOpen && (
        <div className="daily-open-block">
          {days.map((dayData, index) => (
            <DailyOpenBlock
              key={index}
              day={dayData.day}
              subject={dayData.subject}
              tasks={dayData.tasks}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default WeeklyOpenBlock;