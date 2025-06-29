import React from "react";
import "./componentsCss/AdminWeeklyAttendanceList.css";
//import "./componentsCss/AttendanceWeekInfo.css";

const statusImageMap = {
  SUCCESS: "/assets/img/full_coin_green.png",
  INSUFFICIENT: "/assets/img/two_coin_yellow.png",
  FAILURE: "/assets/img/one_coin_yellow.png",
  EMPTY: "/assets/img/three_out_red.png",
};
const AdminWeeklyAttendanceList = ({ attendanceData, onSelectDate }) => {
  return (
    <div className="weekly-container">
      {attendanceData.map(({ week, days}) => (
        <div key={week} className="eachWeekInfo" /*onClick={() => onSelectWeek(week)}*/>
          <p className="weekInfo">{week}주차</p>
          <div className="coin_img_container">
            {days.map((day, idx) => (
              <img
                key={idx}
                src={statusImageMap[day.status]}
                style={{ cursor: "pointer" }}
                onClick={() => {
                  console.log("🧪 클릭됨!", day.date);
                  if (day.date) onSelectDate({ date: day.date });
                }}
              />
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default AdminWeeklyAttendanceList;
