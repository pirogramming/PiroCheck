import React from "react";
import { useState } from "react";
import InputBlock from "./components/InputBlock";
import { useNavigate } from "react-router-dom";
import styles from "./Login.module.css";
import { loginUser } from "./api/user";

const Login = () => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [responseMessage, setResponseMessage] = useState("");

  const navigate = useNavigate();

  const handleChange = (index, value) => {
    if (index === 0) setName(value);
    else if (index === 1) setPassword(value);
  };

  const handleLogin = async () => {
    try {
      const res = await loginUser({ name, password });

      if (!res.ok) {
        const data = await res.json();

        if (res.status === 401) {
          setResponseMessage(
            data?.message || "이름 또는 비밀번호를 다시 확인해주세요."
          );
        } else {
          setResponseMessage(
            data?.message ||
              "알 수 없는 오류가 발생했습니다. 다시 시도해주세요."
          );
        }

        return;
      }

      const data = await res.json(); // { id, name, role }

      localStorage.setItem("user", JSON.stringify(data));

      if (data.role === "ADMIN") {
        navigate("/admin");
      } else if (data.role === "MEMBER") {
        navigate("/home");
      } else {
        setResponseMessage("알 수 없는 사용자 유형입니다.");
      }
    } catch (error) {
      console.error(error);
      setResponseMessage("서버 연결에 실패했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <div className={styles.login_container}>
      <div className={styles.login}>
        <h1 className={styles.pirocheck}>PIROCHECK</h1>

        <InputBlock
          inputs={[
            {
              type: "text",
              placeholder: "이름",
            },
            {
              type: "password",
              placeholder: "비밀번호",
            },
          ]}
          onChange={handleChange}
        />
        <div className={styles.errorWrapper}>
          <span className={styles.errormessage}>{responseMessage}</span>
        </div>
        <button
          onClick={handleLogin}
          className={styles.button}
          disabled={!name || !password}
        >
          로그인
        </button>
      </div>
    </div>
  );
};
export default Login;
