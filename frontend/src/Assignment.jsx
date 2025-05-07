import React from "react";
import WeeklyListBlock from "./components/WeeklyListBlock";
import Header from "./components/Header";
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

  return (
    
    <div className="assignment-page" style={{ backgroundColor: "black", minHeight: "100vh", color: "white" }}>
      <Header/>
      <WeeklyListBlock weeks={weeks} />
    </div>
  );
};

export default Assignment;
