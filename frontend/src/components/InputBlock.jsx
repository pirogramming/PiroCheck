import React from "react";
import "./componentsCss/InputBlock.css";

const InputBlock = ({ inputs, onChange, values }) => {
  return (
    <div className="inputBlock">
      {inputs.map((input, index) => (
        <input
          key={index}
          className="inputTag"
          type={input.type}
          placeholder={input.placeholder}
          value={values?.[index] || ""}
          onChange={(e) => onChange && onChange(index, e.target.value)}
        />
      ))}
    </div>
  );
};

export default InputBlock;
