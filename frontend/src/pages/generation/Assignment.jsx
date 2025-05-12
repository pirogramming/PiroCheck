import React, { useEffect, useState } from "react";
import WeeklyListBlock from "../components/WeeklyListBlock";
import Header from "../components/Header";
import AssignmentInfoBlock from "../components/AssignmentInfoBlock";
import styles from "./Assignment.module.css";
import { mapStatus } from "../utils/AssignmentStatus.js";
import { fetchAssignmentsByUser } from "../api/assignment.js";

const Assignment = () => {
  const [weeks, setWeeks] = useState([]);
  const [highlightCard, setHighlightCard] = useState(null);

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.id;

    if (!userId) return;

    fetchAssignmentsByUser(userId)
      .then((weekData) => {
        const formatted = weekData.map((weekItem) => ({
          label: `${weekItem.week}주차 ${weekItem.title}`,
          details: weekItem.days.map((dayItem) => ({
            day: dayItem.day,
            subject: weekItem.title,
            tasks: dayItem.details.map((task) => ({
              label: task.assignmentName,
              status: mapStatus(task.status),
            })),
          })),
        }));

        setWeeks(formatted);

        // 형광 카드용 하이라이트 카드 추출 (가장 최근 주차 + 첫 요일)=>운영진용 페이지 만든 후 수정필요
        // 운영진이 가장 최근 공개한 과제로.
        if (formatted.length > 0 && formatted[0].details.length > 0) {
          const first = formatted[0];
          const firstDay = first.details[0];

          setHighlightCard({
            weekLabel: first.label,
            day: firstDay.day,
            tasks: firstDay.tasks,
          });
        }
      })
      .catch(() => {
        alert("과제 정보를 불러오지 못했습니다.");
      });
  }, []);

  return (
    <div className={styles.assignment_page}>
      <Header />
      {highlightCard && (
        <div className={styles.info}>
          <AssignmentInfoBlock {...highlightCard} />
        </div>
      )}
      <WeeklyListBlock weeks={weeks} />
    </div>
  );
};

export default Assignment;
