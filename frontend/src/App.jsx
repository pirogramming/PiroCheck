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

export default App;
