import React from "react";
import "./componentsCss/AssignmentInfoBlock.css"

const AssignmentInfoBlock = ({ weekLabel, day, tasks }) => {
  return (
    <div className="assignment-info-block">
      <p className="week-day">{weekLabel} ({day})</p>
      <ul className="task-list">
        {tasks.map((task, idx) => (
          <li key={idx}>{task.label}</li>
        ))}
      </ul>
    </div>
  );
};
export default AssignmentInfoBlock;