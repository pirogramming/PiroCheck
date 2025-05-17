import React from "react";
import "./componentsCss/AdminWeeklyAttendanceList.css";
//import "./componentsCss/AttendanceWeekInfo.css";

const AdminWeeklyAttendanceList = ({ attendanceData, onSelectWeek }) => {
  return (
    <div className="weekly-container">
      {attendanceData.map(({ week, classes }) => (
        <div key={week} className="eachWeekInfo" onClick={() => onSelectWeek(week)}>
          <p className="weekInfo">{week}주차</p>
          <div className="coin_img_container">
            {classes.map((cls, idx) => (
              <img key={idx} src={cls.image} alt={`status-${idx}`} />
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default AdminWeeklyAttendanceList;
