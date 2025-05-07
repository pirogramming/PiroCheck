import React from "react";
import "./componentsCss/AttendanceWeekInfo.css";

const AttendanceWeekInfo = ({ week }) => {
  return (
    <div className="eachWeekInfo">
      <p className="weekInfo">{week}주차</p>
      <div className="coin_img_container">
        <img src="/assets/img/full_coin_green.png" />
      </div>
      <div className="coin_img_container">
        <img src="/assets/img/two_coin_yellow.png" />
      </div>
      <div className="coin_img_container">
        <img src="/assets/img/one_coin_yellow.png" />
      </div>
    </div>
  );
};

export default AttendanceWeekInfo;
