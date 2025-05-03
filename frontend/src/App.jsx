import React from "react";
import InputBlock from "./components/InputBlock";
import InfoBlock from "./components/InfoBlock";
import PageBtn from "./components/PageBtn";

function App() {
  return (
    <>
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
