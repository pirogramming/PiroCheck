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
    label: "2주차  JS개론/웹개론",
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
    label: "3주차  JS개론/웹개론",
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
    <div className="App" style={{ backgroundColor: "black", minHeight: "100vh", color: "white" }}>
      <WeeklyListBlock weeks={weeks} />


    </div>
  );
}
export default App;
