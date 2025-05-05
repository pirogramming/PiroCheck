import React from "react";

const Login = ({ onLogin }) => {
  return (
    <div>
      <h2>로그인 페이지</h2>
      <input type="text" placeholder="아이디" />
      <input type="password" placeholder="비밀번호" />
      <button onClick={onLogin}>로그인</button>
    </div>
  );
};

export default Login;
