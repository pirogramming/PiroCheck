import React, { useState } from "react";
import "./componentsCss/DailyOpenBlock.css";

const DailyOpenBlock = ({ day, subject, tasks }) => {
  const [open, setOpen] = useState(false);

  const toggleOpen = () => {
    setOpen(!open);
  };
  

  return (
    <div className="daily-open-block">
      <button
        className={`daily-button ${open ? "active" : ""}`}
        onClick={toggleOpen}
      >
        <div className="daily-open-header">
          <span className="day">{day}</span>
          <span className="subject">{subject}</span>
        </div>
        <span className={`arrow ${open ? "rotate" : ""}`}>▾</span>
      </button>

      {open && (
        <div className="task-list">
          {tasks.map((task, index) => (
            <div className={`task-item ${task.status}`} key={index}>
              <span>{task.label}</span>
              <span className="task-icon">
                {task.status === "done" && "◯"}
                {task.status === "progress" && "△"}
                {task.status === "fail" && "✕"}
              </span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default DailyOpenBlock;