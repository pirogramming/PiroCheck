import React from "react";
import "./componentsCss/AttendanceWeekInfo.css";

const AttendanceWeekInfo = ({ week, classes }) => {
  return (
    <div className="eachWeekInfo">
      <p className="weekInfo">{week}주차</p>
      <div className="coin_img_container">
        {classes.map((cls, idx) => {
          console.log(`week ${week}, idx ${idx}, image:`, cls.image);
          return <img key={idx} src={cls.image} alt={`${idx + 1}번째 수업`} />;
        })}
      </div>
    </div>
  );
};

export default AttendanceWeekInfo;
