import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AdminStudentHeader from "../../components/AdminStudentHeader";
import AdminDailyAttendanceCard from "../../components/AdminDailyAttendanceCard";
import api from "../../api/api";
import styles from "./AdminStudentAttendance.module.css";
import AdminWeeklyAttendanceList from "../../components/AdminWeeklyAttendanceList";
import { getStudentBasicInfo, getStudentAttendance } from "../../api/adminattendance";

const AdminStudentAttendance = () => {
  const { studentId } = useParams();
  const [studentInfo, setStudentInfo] = useState(null);
  const [attendanceData, setAttendanceData] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const fetchData = async () => {
    try {
      const studentRes = await getStudentBasicInfo(studentId);
      setStudentInfo(studentRes);

      const attendanceRes = await getStudentAttendance(studentId);
      /*
          "attendanceId": 1,
          "userId": 1,
          "username": "홍길동",
          "date": "2023-10-20",
          "order": 1,
          "status": true
      */
      const processed = processWeeklyAttendance(attendanceRes);
      setAttendanceData(processed);
    } catch (err) {
      console.error("데이터 불러오기 실패:", err);
    }
  };
  useEffect(() => {
    const id = Number(studentId);
    
    if (!id || isNaN(id)) {
      console.warn("❗ 잘못된 studentId:", studentId);
      return;
    }
    
    fetchData();
  }, [studentId]);
  
/*
// 더미데이터 임시!!
  useEffect(() => {

    setStudentInfo({ name: "김피로" });

    const mockAttendance = Array.from({ length: 5 }, (_, weekIdx) => ({
      week: weekIdx + 1,
      classes: [
        { status: "SUCCESS", date: "2025-06-24" },
        { status: "INSUFFICIENT", date: "2025-06-26" },
        { status: "SUCCESS", date: "2025-06-28" },
      ],
    }));

    setAttendanceData(mockAttendance);
  }, []);
*/
  // 날짜 기반 주차-회차 구조로 변환
const processWeeklyAttendance = (rawData) => {
  const startDate = new Date("2025-06-24");
  const offsetDays = [0, 2, 4];
  const weekMap = new Map();

  const getWeekFromDate = (dateStr) => {
    const d = new Date(dateStr);
    const diffDays = Math.floor((d - startDate) / (1000 * 60 * 60 * 24));
    return Math.floor(diffDays / 7) + 1;
  };

  const dateMap = new Map();
  
  rawData.forEach(({ date, status }) => {
    const week = getWeekFromDate(date);
    const dayKey = `${week}-${date}`;
    if (!dateMap.has(dayKey)) dateMap.set(dayKey, []);
    dateMap.get(dayKey).push(status);
  });
  /*
  const getDateForClass = (week, classIdx) => {
    const base = new Date(startDate);
    base.setDate(base.getDate() + (week - 1) * 7 + offsetDays[classIdx]);
    return base.toISOString().split("T")[0]; // 'YYYY-MM-DD' 형식
  };
*/
  // 주차별 출석 정보 묶기

  dateMap.forEach((statusList, key) => {
    const [week, date] = key.split("-");
    const trueCount = statusList.filter(Boolean).length;

    let status = "EMPTY";
    switch (trueCount) {
      case 3:
        status = "SUCCESS";
        break;
      case 2:
        status = "INSUFFICIENT";
        break;
      case 1:
        status = "FAILURE";
        break;
      default:
        status = "EMPTY";
    }

    if (!weekMap.has(week)) weekMap.set(week, []);
    weekMap.get(week).push({ date, status });
  });
  return Array.from({ length: 5 }, (_, i) => {
    const week = i + 1;
    const days = (weekMap.get(String(week)) || []).sort((a, b) =>
      a.date.localeCompare(b.date)
    );
    return { week, days };
  });
};

  return (
    <div className={styles.attendance_page}>
      {/*헤더 */}
      <AdminStudentHeader
        studentName={studentInfo?.name || "이름 없음"}
        subtitle="출석"
        onBack={() => window.history.back()}
      />

      {/* 주차별 출석 */}
      <AdminWeeklyAttendanceList
        attendanceData={attendanceData}
        onSelectDate={(selected) => setSelectedDate(selected)}
        //onSelectDate={(date) => setSelectedDate(date)}
      />

      {/* 선택된 날짜의 상세 수정 카드 
      {selectedDate && (
        <AdminDailyAttendanceCard 
          date={selectedDate}
          studentId={studentId}
          onClose={() => setSelectedDate(null)}
        />
      )}*/}
      {selectedDate && (
        <AdminDailyAttendanceCard
          studentId={studentId}
          date={selectedDate.date} 
          //order={selectedDate.order}
          onClose={() => setSelectedDate(null)}
          onRefresh={fetchData}
        />
      )}
    </div>
  );
};

export default AdminStudentAttendance;
