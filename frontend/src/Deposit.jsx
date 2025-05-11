import Header from "./components/Header";
import styles from "./Deposit.module.css";
import axios from "axios";
import { useEffect, useState } from "react";
import api from "./api/api";

const Deposit = () => {
  const [deposit, setDeposit] = useState(null);

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.id;

    if (!userId) return;

    api
      .get(`/deposit/${userId}`)
      .then((res) => setDeposit(res.data))
      .catch((err) => {
        alert("보증금 정보를 불러오지 못했습니다.");
      });
  }, []);

  if (!deposit) return <div>loagin...</div>;

  return (
    <div className={styles.deposit_container}>
      <Header />
      <div className={styles.deposit}>
        <span>잔여 보증금</span>
        <span>{deposit.amount}원</span>
      </div>
      <div className={styles.deposit_detail}>
        <span>과제 차감</span>
        <span>{deposit.descentAssignment}원</span>
      </div>
      <div className={styles.deposit_detail}>
        <span>출석 차감</span>
        <span>{deposit.descentAttendance}원</span>
      </div>
      <div className={styles.deposit_detail}>
        <span>보증금 방어권</span>
        <span>{deposit.ascentDefence}원</span>
      </div>
    </div>
  );
};
export default Deposit;
