import React from "react";
import "./componentsCss/InfoBlock.css";

const InputBlock = ({ inputs, onChange }) => {
  return (
    <div className="inputBlock">
      {inputs.map((input, index) => (
        <input
          key={index}
          className="inputTag"
          type={input.type}
          placeholder={input.placeholder}
          onChange={(e) => onChange && onChange(index, e.target.value)}
        />
      ))}
    </div>
  );
};

export default InputBlock;
