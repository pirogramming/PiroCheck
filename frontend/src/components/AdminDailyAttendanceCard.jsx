import React, { useEffect, useState } from "react";
import "./componentsCss/AdminDailyAttendanceCard.css";
import api from "../api/api";
import { getStudentAttendance,updateAttendanceStatus } from "../api/adminattendance";

const AdminDailyAttendanceCard = ({ date, studentId, onClose }) => {
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
        const dayData = rawData.data.find((d) => d.date === date);
        const rawSlots = dayData?.slots || [];

        setSlots(rawSlots);
        setModified(Array(rawSlots.length).fill(false));
      } catch (err) {
        console.error("슬롯 정보 불러오기 실패:", err);
      }
    };

    fetchSlots();
  }, [date, studentId]);

  const handleChange = (idx, newValue) => {
    const newSlots = [...slots];
    newSlots[idx].status = newValue === "SUCCESS";
    setSlots(newSlots);

    const newModified = [...modified];
    newModified[idx] = true;
    setModified(newModified);
  };

  const handleSave = async (idx) => {
    try {
      const slot = slots[idx];
      await updateAttendanceStatus(studentId, slot.id, slot.status);

      const newModified = [...modified];
      newModified[idx] = false;
      setModified(newModified);
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
          <div key={slot.id} className="slot-row">
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
