import { useState } from "react";
import api from "../../api/api";
import Header from "../../components/Header";
import style from "./AttendanceCode.module.css";

const AttendanceCode = () => {
  const [code, setCode] = useState("");

  // 출석코드 생성
  const generateCode = async () => {
    try {
      const res = await api.post("/attendance/start");
      const newCode = res.data.data.code;
      setCode(newCode);
    } catch (error) {
      alert(
        "출석코드 생성 실패: " + (error.response?.data?.message || "서버 오류")
      );
    }
  };

  // 출석코드 만료
  const expireCode = async () => {
    try {
      const res = await api.put("/attendance/expire-latest");
      alert(res.data.message || "출석코드가 만료되었습니다");
      setCode("");
    } catch (error) {
      alert(
        "출석코드 만료 실패: " + (error.response?.data?.message || "서버 오류")
      );
    }
  };

  return (
    <div className={style.attendancecode_wrapper}>
      <Header />
      <div className={style.num_container}>
        {[...Array(4)].map((_, i) => (
          <div key={i} className={style.num_wrapper}>
            {code[i] || ""}
          </div>
        ))}
      </div>
      <div className={style.button_container}>
        <button onClick={generateCode} className={style.button}>
          생성
        </button>

        {code && (
          <button onClick={expireCode} className={style.button}>
            종료
          </button>
        )}
      </div>
    </div>
  );
};

export default AttendanceCode;
