import React from "react";
import "./componentsCss/InfoBlock.css";

const InputBlock = () => {
  return (
    <div>
      <input class="inputTag" type="text" placeholder="이름" />
      <input class="inputTag" type="password" placeholder="비밀번호" />
      <input
        class="inputTag"
        type="text"
        placeholder="출석코드를 입력하세요."
      />
    </div>
  );
};

export default InputBlock;
