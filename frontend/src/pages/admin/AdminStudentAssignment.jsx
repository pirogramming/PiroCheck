import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AdminStudentHeader from "../../components/AdminStudentHeader";
import WeeklyOpenBlock from "../../components/WeeklyOpenBlock";
import AssignmentInfoBlock from "../../components/AssignmentInfoBlock";
import api from "../../api/api";
import styles from "./AdminStudentAssignment.module.css";
import {
  submitAssignmentStatus,
  updateAssignmentStatus,
  fetchStudentInfo,
  fetchStudentAssignments,
} from "../../api/adminassignment";

const AdminStudentAssignment = () => {
  const { studentId, week } = useParams();
  const [studentInfo, setStudentInfo] = useState(null);
  const [weeks, setWeeks] = useState([]);
  const [highlightCard, setHighlightCard] = useState(null);
  const [selectedWeekLabel, setSelectedWeekLabel] = useState(null);

  useEffect(() => {
    const id = Number(studentId);
    if (!id || isNaN(id)) {
      console.warn("❗ 잘못된 studentId:", studentId);
      return;
    }
    
    fetchStudentInfo(id).then((res) => {
      setStudentInfo(res);
    });



    fetchStudentAssignments(studentId).then((res) => {
      const formatted = res.data.map((weekItem) => ({
        week: weekItem.week,
        label: `${weekItem.week}주차 ${weekItem.title}`,
        days: weekItem.days.map((dayItem) => ({
          day: dayItem.day,
          tasks: dayItem.details.map((task) => ({
            id: task.id,
            label: task.assignmentName,
            status: task.status,
            modified: false,
          })),
        })),
      }));

        setWeeks(formatted);

        const matched = formatted.find((w) => Number(w.week) === Number(week));
        if (matched) {
          setSelectedWeekLabel(matched.label);
          /*
          if (matched.days.length > 0) {
            setHighlightCard({
              weekLabel: matched.label,
              day: matched.days[0].day,
              tasks: matched.days[0].tasks,
            });
          }*/
        }
      });
  }, [studentId, week]);

  const handleStatusChange = (taskId, newStatus) => {
    const updatedWeeks = weeks.map((weekItem) => ({
      ...weekItem,
      days: weekItem.days.map((dayItem) => ({
        ...dayItem,
        tasks: dayItem.tasks.map((task) =>
          task.id === taskId
            ? { ...task, status: newStatus, modified: true }
            : task
        ),
      })),
    }));

    setWeeks(updatedWeeks);
  };



  const handleSave = async (taskId, status) => {
    const userId = parseInt(studentId); // 문자열일 수 있으니 숫자로 변환
    
    try {
      // PUT 요청 시도 (기존 과제 수정)
      await updateAssignmentStatus(userId, taskId, status);
      alert("과제 상태가 수정되었습니다.");
    } catch (err) {
      console.warn("PUT 실패, POST 시도");
      try {
        // 없으면 POST 요청 (새 과제 등록)
        await submitAssignmentStatus(userId, taskId, status);
        alert("과제 상태가 등록되었습니다.");
      } catch (err) {
        alert("상태 저장 실패");
        console.error(err);
      }
    }
  };

  return (
    <div className={styles.container}>
      <AdminStudentHeader
        studentName={studentInfo?.name || "이름 없음"}
        subtitle={selectedWeekLabel ? `- ${selectedWeekLabel}` : ""}
        onBack={() => window.history.back()}
      />

      {highlightCard && (
        <div className={styles.info}>
          <AssignmentInfoBlock {...highlightCard} />
        </div>
      )}

      <div className={styles.weekList}>
        {weeks
        .filter((weekItem) => Number(weekItem.week) === Number(week))
        .map((weekItem, weekIdx) => (
          <div className={styles.weekBlock} key={weekIdx}>
            <p className={styles.weekTitle}>{weekItem.label}</p>
            {weekItem.days.map((dayItem, dayIdx) => (
              <div key={dayIdx} className={styles.dayCard}>
                <p className={styles.dayLabel}>
                  {dayItem.day} &nbsp; {dayItem.subtitle}
                </p>
                <div className={styles.taskList}>
                  {dayItem.tasks.map((task, taskIdx) => (
                    <div key={task.id} className={styles.taskRow}>
                      <span className={styles.taskLabel}>{task.label}</span>
                      <select
                        value={task.status}
                        onChange={(e) =>
                          handleStatusChange(
                            taskIdx,
                            e.target.value
                          )
                        }
                      >
                        <option value="SUCCESS">성공</option>
                        <option value="INSUFFICIENT">미흡</option>
                        <option value="FAILURE">실패</option>
                      </select>
                      <button
                        className={styles.saveButton}
                        disabled={!task.modified}
                        onClick={() => handleSave(task.id, task.status)}
                      >
                        save
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminStudentAssignment;
