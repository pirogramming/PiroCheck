import React from "react";

const PageBtn = ({ buttons }) => {
  return (
    <div>
      {buttons.map((btn, index) => (
        <button key={index} onClick={() => (window.location.href = btn.href)}>
          {btn.label}
        </button>
      ))}
    </div>
  );
};

export default PageBtn;
