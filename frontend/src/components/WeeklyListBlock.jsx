import React, { useState } from "react";
import "./componentsCss/WeeklyListBlock.css";
import DailyOpenBlock from "./DailyOpenBlock";

const WeeklyListBlock = ({ weeks }) => {
  const [openIndex, setOpenIndex] = useState(null);

  const toggleOpen = (index) => {
    setOpenIndex(index === openIndex ? null : index);
  };

  return (
    <div className="weekly-list">
      {weeks.map((week, index) => (
        <div key={index} className="weekly-item">
          <button
            className={`weekly-button ${openIndex === index ? "active" : ""}`}
            onClick={() => toggleOpen(index)}
          >
            <span>{week.label}</span>
            <span className={`arrow ${openIndex === index ? "rotate" : ""}`}>
              ▾
            </span>
          </button>
          {openIndex === index && (
            <div className="week-details">
              {week.details?.map((detail, i) => (
                <DailyOpenBlock
                key={i}
                day={detail.day}
                subject={detail.subject}
                tasks={detail.tasks}
              />
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default WeeklyListBlock;
