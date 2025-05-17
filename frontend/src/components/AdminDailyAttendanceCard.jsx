import React, { useEffect, useState } from "react";
import "./componentsCss/AdminDailyAttendanceCard.css";
import api from "../api/api";

const AdminDailyAttendanceCard = ({ date, studentId, onClose }) => {
  const [slots, setSlots] = useState([]);
  const [modified, setModified] = useState([]);

  useEffect(() => {
    const fetchSlots = async () => {
      try {
        const res = await api.get("/attendance/user/date", {
          params: { userId: studentId, date },
          withCredentials: true,
        });

        const rawSlots = res.data.data?.[0]?.slots || [];
        setSlots(rawSlots);
        setModified(Array(rawSlots.length).fill(false));
      } catch (err) {
        console.error("슬롯 정보 불러오기 실패:", err);
      }
    };

    fetchSlots();
  }, [date, studentId]);

  const handleToggle = (idx) => {
    const newSlots = [...slots];
    newSlots[idx].status = !newSlots[idx].status;
    setSlots(newSlots);

    const newModified = [...modified];
    newModified[idx] = true;
    setModified(newModified);
  };

  const handleSave = async (idx) => {
    try {
      const slotId = slots[idx].id;
      await api.put(`/attendance/slot/${slotId}`, {
        status: slots[idx].status,
      }, { withCredentials: true });

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
            <input
              type="checkbox"
              checked={slot.status}
              onChange={() => handleToggle(idx)}
            />
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
