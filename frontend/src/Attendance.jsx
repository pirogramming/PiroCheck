import { useState } from "react";
import Header from "./components/Header";
import InputBlock from "./components/InputBlock";
import AttendanceWeekInfo from "./components/AttendanceWeekInfo";
import styles from "./Attendance.module.css";

const Attendance = () => {
  const [attendanceCode, setAttendanceCode] = useState([""]);
  const handleChange = (index, value) => {
    // 숫자만 입력 허용
    if (/^\d*$/.test(value)) {
      const userCodes = [...attendanceCode];
      userCodes[index] = value;
      setAttendanceCode(userCodes);
    }
  };
  const handleSubmit = () => {
    console.log("제출된 출석 코드: ", attendanceCode[0]);
    // 서버 요청 등 추가 작업
  };

  return (
    <div className={styles.attendance_page}>
      <Header />
      <InputBlock
        inputs={[
          {
            type: "text",
            placeholder: "출석코드를 입력하세요.",
          },
        ]}
        values={attendanceCode}
        onChange={handleChange}
      />
      {attendanceCode[0].length === 4 && (
        <button className={styles.submitBtn} onClick={handleSubmit}>
          Submit
        </button>
      )}
      <div className={styles.attend_img_container}>
        <div className={styles.boom_icon}>
          <img src="assets/img/tabler--boom.png" />
        </div>
        <div className={styles.boom_icon}>
          <img src="assets/img/tabler--boom.png" />
        </div>
        <div className={styles.boom_icon}>
          <img src="assets/img/tabler--boom.png" />
        </div>
      </div>
      <div className={styles.attend_week_container}>
        <AttendanceWeekInfo week={1} />
        <AttendanceWeekInfo week={2} />
        <AttendanceWeekInfo week={3} />
        <AttendanceWeekInfo week={4} />
        <AttendanceWeekInfo week={5} />
      </div>
    </div>
  );
};

export default Attendance;
