import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AdminStudentHeader from "../../components/AdminStudentHeader";
import DailyAttendanceCard from "../../components/AdminDailyAttendanceCard";
import api from "../../api/api";
import styles from "./AdminStudentAttendance.module.css";
import AdminWeeklyAttendanceList from "../../components/AdminWeeklyAttendanceList";
import { getStudentBasicInfo, getStudentAttendance } from "../../api/adminattendance";

const AdminStudentAttendance = () => {
  const { studentId } = useParams();
  const [studentInfo, setStudentInfo] = useState(null);
  const [attendanceData, setAttendanceData] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);

  useEffect(() => {
    const id = Number(studentId);
    
    if (!id || isNaN(id)) {
      console.warn("❗ 잘못된 studentId:", studentId);
      return;
    }
    
    const fetchData = async () => {
      try {
        const studentRes = await getStudentBasicInfo(studentId);
        setStudentInfo(studentRes);

        const attendanceRes = await getStudentAttendance(studentId);
        const processed = processWeeklyAttendance(attendanceRes.data);
        setAttendanceData(processed);
      } catch (err) {
        console.error("데이터 불러오기 실패:", err);
      }
    };

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
    const getWeekFromDate = (dateStr) => {
      const d = new Date(dateStr);
      const diffDays = Math.floor((d - startDate) / (1000 * 60 * 60 * 24));
      return Math.floor(diffDays / 7) + 1;
    }; 

    const weekSlotMap = new Map();
    const dateMap = new Map(); // 추가: 날짜 저장

    rawData.forEach(({ date, slots }) => {
      const week = getWeekFromDate(date);
      const statuses = slots.map((s) =>
        s.status ? "SUCCESS" : "FAILURE"
      );
      const existing = weekSlotMap.get(week) || [];
      const existingDates = dateMap.get(week) || [];

      weekSlotMap.set(week, [...existing, ...statuses]);
      dateMap.set(week, [...existingDates, date]);
    });

    return Array.from({ length: 5 }, (_, i) => {
      const week = i + 1;
      const all = weekSlotMap.get(week) || [];
      const dates = dateMap.get(week) || [];

      const classes = [0, 1, 2].map((classIdx) => {
        const slice = all.slice(classIdx * 3, classIdx * 3 + 3);
        const trueCount = slice.filter((s) => s === "SUCCESS").length;

        let status;
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

        return {
          status,
          date: dates[classIdx] || null,
        };
      });


      return { week, classes };
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
        onSelectDate={(date) => setSelectedDate(date)}
      />

      {/* 선택된 날짜의 상세 수정 카드 */}
      {selectedDate && (
        <DailyAttendanceCard
          date={selectedDate}
          studentId={studentId}
          onClose={() => setSelectedDate(null)}
        />
      )}
    </div>
  );
};

export default AdminStudentAttendance;
