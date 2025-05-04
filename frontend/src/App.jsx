import React from "react";
import InputBlock from "./components/InputBlock";
import InfoBlock from "./components/InfoBlock";
import PageBtn from "./components/PageBtn";
import Header from "./components/Header";

function App() {
  return (
    <>
      <Header />
      <div>
        <InputBlock
          inputs={[
            { type: "text", placeholder: "이름" },
            { type: "password", placeholder: "비밀번호" },
            { type: "text", placeholder: "출석코드를 입력하세요" },
          ]}
        />
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

export default App;
