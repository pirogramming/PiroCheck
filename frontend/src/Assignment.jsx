import React from "react";
import WeeklyListBlock from "./components/WeeklyListBlock";
import Header from "./components/Header";
import AssignmentInfoBlock from "./components/AssignmentInfoBlock";
import styles from "./Assignment.module.css";

const Assignment = () => {
  const weeks = [
    {
      label: "2주차 JS개론/웹개론",
      details: [
        {
          day: "화",
          subject: "Git HTML CSS",
          tasks: [
            { label: "제로초 인강", status: "done" },
            { label: "깃허브 클론 코딩", status: "progress" },
          ],
        },
        {
          day: "목",
          subject: "Git HTML CSS",
          tasks: [
            { label: "제로초 인강", status: "fail" },
            { label: "깃허브 클론 코딩", status: "progress" },
          ],
        },
      ],
    },
    {
      label: "3주차 JS개론/웹개론",
      details: [
        {
          day: "화",
          subject: "Git HTML CSS",
          tasks: [],
        },
      ],
    },
  ];

  //Assignment-Info-Block(형광 초록색카드 더미데이터)
  const cardData = {
    weekLabel: "3주차",
    day: "화",
    tasks: [{ label: "Django girls 과제" }, { label: "Django girls 과제" }],
  };

  return (
    <div className={styles.assignment_page}>
      <Header />
      <div className={styles.info}>
        <AssignmentInfoBlock {...cardData} />
      </div>

      <WeeklyListBlock weeks={weeks} />
    </div>
  );
};

export default Assignment;
