import { useEffect, useState, useRef } from "react";
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

  const currentDateRef = useRef(null);

  const getSubImage = (count) => {
    // ✅ 절대 경로(/) 사용으로 이미지 엑박 해결
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
    // ✅ 절대 경로(/) 사용
    switch (status) {
      case "success":
        return "/assets/img/boom-fill-green.png";
      case "fail":
        return "/assets/img/boom-fill-red.png";
      default:
        return "/assets/img/tabler--boom.png";
    }
  };

  // 날짜 기반 주차 계산 (0시 기준 초기화로 오차 제거)
  const getWeekFromDate = (dateStr) => {
    const startDate = new Date("2025-12-23"); // 세션 시작일
    startDate.setHours(0, 0, 0, 0);
    
    const currentDate = new Date(dateStr);
    currentDate.setHours(0, 0, 0, 0);

    // 두 날짜 사이 일수 차이 계산
    const diffTime = currentDate.getTime() - startDate.getTime();
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    // 0~6일 사이: 1주차, 7~13일 사이: 2주차
    return Math.floor(diffDays / 7) + 1;
  };

  // ✅ 화/목/토 요일별 데이터 매핑 로직 적용
  const processWeeklyAttendance = (rawData) => {
    // 화(0), 목(1), 토(2) 인덱스 반환 함수
    const getDayIndex = (dateStr) => {

      //임시 테스트 확인용
      return 0;
      // const day = new Date(dateStr).getDay();
      // if (day === 2) return 0; // 화
      // if (day === 4) return 1; // 목
      // if (day === 6) return 2; // 토
      // return -1;
    };

    // 5주차 x 3세션 빈 데이터 생성
    const weeklyData = Array.from({ length: 5 }, (_, i) => ({
      week: i + 1,
      classes: [
        { count: 0, image: getSubImage(0) }, // 화
        { count: 0, image: getSubImage(0) }, // 목
        { count: 0, image: getSubImage(0) }  // 토
      ]
    }));

    if (!rawData || !Array.isArray(rawData)) return weeklyData;

    rawData.forEach(({ date, slots }) => {
      const week = getWeekFromDate(date);
      const dayIdx = getDayIndex(date);

      // 유효한 주차(1~5)이고, 화/목/토 세션인 경우만 처리
      if (week >= 1 && week <= 5 && dayIdx !== -1) {
        
        // ✅ DB의 "t"(문자열)와 true(불리언) 모두 출석으로 인정
        const presentCount = slots.filter(
          (slot) => slot.status === true || slot.status === "t"
        ).length;

        weeklyData[week - 1].classes[dayIdx] = {
          count: presentCount,
          image: getSubImage(presentCount),
        };
      }
    });

    return weeklyData;
  };

  // 주간 출석 정보 가져오기
  const fetchAttendance = async () => {
    try {
      // ✅ [배포용] 실제 로그인 유저 ID 사용
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;
      
      if (!userId) return;

      const res = await api.get(`/attendance/user`, {
        params: { userId },
        withCredentials: true, 
      });
      
      const rawData = res.data.data;
      const weekly = processWeeklyAttendance(rawData);
      setAttendanceData(weekly);
    } catch (error) {
      console.error("출석 정보 가져오기 실패:", error);
    }
  };

  // 오늘 세션(3회) 출석 정보 가져오기
  const fetchTodayAttendance = async () => {
    try {
      // ✅ [배포용] 실제 로그인 유저 ID 사용
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;

      if (!userId) return;

      const today = new Date().toLocaleDateString("sv-SE"); 
      const res = await api.get(`/attendance/user/date`, {
        params: { userId, date: today },
        withCredentials: true, 
      });

      const slots = (res.data.data || []).sort((a, b) => a.order - b.order);

      const statuses = slots.map((slot) => {
        // ✅ DB 데이터 타입 호환성(t/true) 체크
        if (slot.status === true || slot.status === "t") return "success";
        else return "fail";
      });

      // 아직 진행되지 않은 출석체크 처리
      while (statuses.length < 3) {
        statuses.push("not_started");
      }

      setTodayStatuses(statuses);
    } catch (error) {
      console.error("오늘 출석 정보 가져오기 실패:", error);
    }
  };

  useEffect(() => {
    if (!currentDateRef.current) {
      currentDateRef.current = new Date().toLocaleDateString("sv-SE"); 
    }

    fetchAttendance();
    fetchTodayAttendance();

    // 10초마다 상태 갱신 (미진행 건이 있을 때만)
    const interval = setInterval(() => {
      setTodayStatuses((prev) => {
        if (prev.includes("not_started")) {
          fetchTodayAttendance();
        }
        return prev;
      });
    }, 10000);

    // 1분마다 날짜 변경 체크
    const dateCheckInterval = setInterval(() => {
      const todayStr = new Date().toLocaleDateString("sv-SE"); 
      
      if (todayStr !== currentDateRef.current) {
        currentDateRef.current = todayStr; 
        fetchTodayAttendance(); 
      }
    }, 60000); 

    return () => {
      clearInterval(interval);
      clearInterval(dateCheckInterval);
    };
  }, []);

  const handleChange = (index, value) => {
    if (/^\d*$/.test(value)) {
      const userCodes = [...attendanceCode];
      userCodes[index] = value;
      setAttendanceCode(userCodes);
    }
  };
  
  const handleSubmit = async () => {
    try {
      // ✅ [배포용] 실제 로그인 유저 ID 사용
      const user = JSON.parse(localStorage.getItem("user"));
      const userId = user?.id;
      
      if (!userId) return;

      const res = await api.post(
        "/attendance/mark",
        {
          userId,
          code: attendanceCode[0],
        },
        {
          withCredentials: true, 
        }
      );

      if (res.data.success) {
        alert(res.data.data.message);
        fetchAttendance(); // 주간 데이터 갱신
        fetchTodayAttendance(); // 오늘 상단 데이터 갱신
      } else {
        alert(res.data.message || "출석 실패");
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
        {todayStatuses.map((status, idx) => {
          return (
            <div className={styles.boom_icon} key={idx}>
              <img src={getBoomImage(status)} alt={`attendance-${idx}`} />
            </div>
          );
        })}
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