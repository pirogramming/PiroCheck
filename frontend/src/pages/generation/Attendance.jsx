import { useEffect, useState } from "react";
import Header from "../../components/Header";
import InputBlock from "../../components/InputBlock";
import AttendanceWeekInfo from "../../components/AttendanceWeekInfo";
import styles from "./Attendance.module.css";
import api from "../../api/api";

const Attendance = () => {
  const [attendanceCode, setAttendanceCode] = useState([""]);
  const [attendanceData, setAttendanceData] = useState([]);
  const [todayStatuses, setTodayStatuses] = useState([
    "not_started",
    "not_started",
    "not_started",
  ]);

  const getSubImage = (count) => {
    switch (count) {
      case 3:
        return "/assets/img/full_coin_green.png";
      case 2:
        return "/assets/img/two_coin_yellow.png";
      case 1:
        return "/assets/img/one_coin_yellow.png";
      default:
        return "/assets/img/three_out_red.png";
    }
  };

  // 세션별 상단 이미지 handling
  const getBoomImage = (status) => {
    switch (status) {
      case "success":
        return "/assets/img/boom-fill-green.png";
      case "fail":
        return "/assets/img/boom-fill-red.png";
      default:
        return "/assets/img/tabler--boom.png";
    }
  };

  // 날짜 기반 주차 계산
  const getWeekFromDate = (dateStr) => {
    const startDate = new Date("2025-06-24"); // 세션 시작일
    const currentDate = new Date(dateStr);

    // 두 날짜 사이 일수 차이 계산
    const diffTime = currentDate.getTime() - startDate.getTime();
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    // 0~6일 사이: 1주차, 7~13일 사이: 2주차
    return Math.floor(diffDays / 7) + 1;
  };

  const processWeeklyAttendance = (rawData) => {
    const weekSlotMap = new Map();
    // { weekNum: [boolean, boolean, ...] }

    rawData.forEach(({ date, slots }) => {
      const week = getWeekFromDate(date); // 날짜 기준 주차 계산
      const presentSlots = slots.map((slot) => slot.status); // T/F 목록 생성
      const existing = weekSlotMap.get(week) || [];
      weekSlotMap.set(week, [...existing, ...presentSlots]);
    });

    return Array.from({ length: 5 }, (_, i) => {
      const week = i + 1;
      const all9 = weekSlotMap.get(week) || []; // 총 9개의 출석 슬롯 (3번의 출석체크*주차당 3번의 세션)

      const classes = [0, 1, 2].map((classIdx) => {
        // 0,1,2 -> 세션당 3번의 출석체크
        const slice = all9.slice(classIdx * 3, classIdx * 3 + 3);
        const count = slice.filter(Boolean).length; // 출석 성공(True) 카운트
        return {
          image: getSubImage(count),
          count,
        };
      });

      return { week, classes }; // week: 1, classes: [ {image, count}, ... ]
    });
  };

  const fetchAttendance = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;
      if (!userId) return;

      // 유저 전체 출석 데이터 불러오기
      const res = await api.get(`/attendance/user`, {
        params: { userId },
        withCredentials: true, // 세션 기반 인증 요청처리
      });
      const rawData = res.data.data;
      const weekly = processWeeklyAttendance(rawData);
      setAttendanceData(weekly);
    } catch (error) {
      console.error("출석 정보 가져오기 실패:", error);
    }
  };

  // 세션별 출석체크(총 3번) 진행 정보 불러오기
  const fetchTodayAttendance = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;
      if (!userId) return;

      const today = new Date().toISOString().split("T")[0]; // YYYY-MM-DD
      const res = await api.get(`/attendance/user/date`, {
        params: { userId, date: today },
        withCredentials: true, // 세션 기반 인증 요청처리
      });

      const slots = res.data.data?.[0]?.slots || [];

      const statuses = slots.map((slot) => {
        if (slot.status === true) return "success";
        else return "fail";
      });

      // 출석체크 진행안된 것 처리
      while (statuses.length < 3) {
        statuses.push("not_started");
      }

      setTodayStatuses(statuses);
    } catch (error) {
      console.error("오늘 출석 정보 가져오기 실패:", error);
    }
  };

  useEffect(() => {
    fetchAttendance();
    fetchTodayAttendance();
  }, []);

  const handleChange = (index, value) => {
    // 숫자만 입력 허용
    if (/^\d*$/.test(value)) {
      const userCodes = [...attendanceCode];
      userCodes[index] = value;
      setAttendanceCode(userCodes);
    }
  };
  const handleSubmit = async () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;
      if (!userId) return;

      // 유저가 입력한 출석 코드 서버에 전달(서버에서 출석코드 체크)

      const res = await api.post(
        "/api/attendance/mark",

        {
          userId,
          code: attendanceCode[0],
        },
        {
          withCredentials: true, // 세션 기반 인증 요청처리
        }
      );

      if (res.data.success) {
        alert("출석이 성공적으로 처리되었습니다!");
        fetchAttendance(); // 서버 출석체크 전달 후 UI 반영
        fetchTodayAttendance(); // 세션별 상단 이미지 UI 반영
      } else {
        alert(res.data.message);
      }
    } catch (error) {
      console.error("출석 제출 실패:", error);
      alert("출석 제출 중 오류 발생!");
    }
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
        {todayStatuses.map((status, idx) => (
          <div className={styles.boom_icon} key={idx}>
            <img src={getBoomImage(status)} alt={`attendance-${idx}`} />
          </div>
        ))}
      </div>
      <div className={styles.attend_week_container}>
        {attendanceData.map(({ week, classes }) => (
          <AttendanceWeekInfo key={week} week={week} classes={classes} />
        ))}
      </div>
    </div>
  );
};

export default Attendance;
