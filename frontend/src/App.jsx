import React from "react";
import InputBlock from "./components/InputBlock";
import InfoBlock from "./components/InfoBlock";
import PageBtn from "./components/PageBtn";
import Header from "./components/Header";
import WeeklyListBlock from "./components/WeeklyListBlock";

/*
function App() {
  return (
    <>
      <Header />
      <div>
       
        <InputBlock />
        <InfoBlock />
        <PageBtn
          buttons={[
            { label: "ASSIGNMENT CHECK", href: "/assignment" },
            { label: "ATTENDANCE CHECK", href: "/attendance" },
          ]}
        />
      </div>
    </>
  );
}
*/

function App() {
  
  const weeks = [
    {
      label: "1주차 Git/ HTML/CSS",
      details: ["제로초 인강", "깃허브 클론 코딩"],
    },
    {
      label: "2주차 JS개론/웹개론",
      details: ["React 기초", "JS 문법"],
    },
    {
      label: "3주차 JS개론/웹개론",
      details: ["Comming soon~"],
    },
    {
      label: "4주차 Comming soon~",
      details: [],
    },
    {
      label: "5주차 Comming soon~",
      details: [],
    },
  ];

  return (
    <div className="App" style={{ backgroundColor: "black", minHeight: "100vh", color: "white" }}>
      <WeeklyListBlock weeks={weeks} />
    </div>
  );
}
export default App;
