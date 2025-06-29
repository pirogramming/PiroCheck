import React, { useEffect, useState } from "react";
import "./componentsCss/AdminDailyAttendanceCard.css";
import api from "../api/api";
import { getStudentAttendance,updateAttendanceStatus } from "../api/adminattendance";

const AdminDailyAttendanceCard = ({ date,  order,studentId, onClose, onRefresh }) => {
  const [slots, setSlots] = useState([]);
  const [modified, setModified] = useState([]);

  useEffect(() => {
    const fetchSlots = async () => {
      /*
           {
      //  개발용 더미 데이터
      const dummySlots = [
        { id: 1, status: true },
        { id: 2, status: false },
        { id: 3, status: true },
      ];
      setSlots(dummySlots);
      setModified(Array(dummySlots.length).fill(false));
      return;
    }
      */
      try {
        const rawData = await getStudentAttendance(studentId);
        /*   
        "attendanceId": 1,
        "userId": 1,
        "username": "홍길동",
        "date": "2023-10-20",
        "order": 1,
        "status": true
        */
        const rawSlots = rawData
          .filter((d) => d.date === date) // 해당 날짜의 출석만 필터
          //.sort((a, b) => a.order - b.order) // order 순으로 정렬
          .map((d) => ({
            date: d.date, 
            id: d.attendanceId,                     // 출석 ID
            //order: d.order,                         // 회차 표시용
            status: d.status ? "SUCCESS" : "FAILURE", // 드롭다운에 맞게 변환
          }));

        const filledSlots =
          rawSlots.length > 0
            ? rawSlots
            : [1, 2, 3].map((order) => ({
                date,
                id: null, // 새 출석이므로 아직 id 없음
                //order,
                status: "EMPTY",//기본값
              }));

        setSlots(filledSlots);
        setModified(Array(filledSlots.length).fill(false));

        //setSlots(rawSlots);
        //setModified(Array(rawSlots.length).fill(false));

      } catch (err) {
        console.error("슬롯 정보 불러오기 실패:", err);
      }
    };

    if (date) fetchSlots();
  }, [date, studentId]);
 
  const handleChange = (idx, newValue) => {
    const newSlots = [...slots];
    newSlots[idx].status = newValue;
    setSlots(newSlots);

    const newModified = [...modified];
    newModified[idx] = true;
    setModified(newModified);
  };

  const handleSave = async (idx) => {
    try {

      const slot = slots[idx]; 
      const attendanceId = slot.id;
      const status = slot.status === "SUCCESS"; 
      
      await updateAttendanceStatus(studentId, attendanceId, status);

      const newModified = [...modified];
      newModified[idx] = false;
      setModified(newModified);

      console.log("📝 저장 요청", {
        id: slot.id,
        order: slot.order,
        date: slot.date,
        status: slot.status,
      });

    } catch (err) {
      console.error("슬롯 저장 실패:", err);
      alert("저장 실패");

    }
  };

  const handleSubmit = async () => {
    try {
      for (let i = 0; i < slots.length; i++) {
        if (modified[i]) {
          await handleSave(i);
        }
      }
      alert("전체 저장 완료");
      if (onRefresh) onRefresh(); // submit 이후 새로고침
      onClose(); 
    } catch (err) {
      console.error("전체 저장 실패:", err);
    }
  };

  return (
    <div className="daily-card">
      <div className="card-header">
        <p>{date} 출석 수정</p>
        <button onClick={onClose}>❌</button>
      </div>
      <div className="card-body">
        {slots.map((slot, idx) => (
          <div key={slot.id || `${date}-${idx}`} className="slot-row">
            <span>{idx + 1}차 출석</span>
            <select value={slot.status} onChange={(e) => handleChange(idx, e.target.value)}>
              <option value="SUCCESS">성공</option>
              <option value="FAILURE">실패</option>
            </select>

            <button
              className="save-btn"
              onClick={() => handleSave(idx)}
              disabled={!modified[idx]}
            >
              save
            </button>
          </div>
        ))}
      </div>
      <button className="submit-btn" onClick={handleSubmit}>
        submit
      </button>
    </div>
  );
};

export default AdminDailyAttendanceCard;
