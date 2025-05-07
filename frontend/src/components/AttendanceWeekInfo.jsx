import React from "react";
import "./componentsCss/AttendanceWeekInfo.css";

const AttendanceWeekInfo = ({ week }) => {
  return (
    <div className="eachWeekInfo">
      <p className="weekInfo">{week}주차</p>
    </div>
  );
};

export default AttendanceWeekInfo;
