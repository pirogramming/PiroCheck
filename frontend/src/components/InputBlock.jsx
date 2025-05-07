import React from "react";
import "./componentsCss/InputBlock.css";

const InputBlock = ({ inputs }) => {
  return (
    <div>
      {inputs.map((input, index) => (
        <input
          key={index}
          class="inputTag"
          type={input.type}
          placeholder={input.placeholder}
        />
      ))}
    </div>
  );
};

export default InputBlock;
