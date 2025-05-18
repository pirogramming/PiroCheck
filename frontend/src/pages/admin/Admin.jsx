import { useNavigate } from "react-router-dom";
import styles from "./Admin.module.css";

const Admin = () => {
  const navigate = useNavigate();
  return (
    <div className={styles.home_container}>
      <div className={styles.home}>
        <h1 className={styles.pirocheck}>PIROCHECK</h1>
        <button
          className={styles.button}
          onClick={() => navigate("/managestudent")}
        >
          <p>수강생 관리</p>
        </button>
        <button
          className={styles.button}
          onClick={() => navigate("/magagetask")}
        >
          <p>과제 관리</p>
        </button>
        <button
          className={styles.button}
          onClick={() => navigate("/attendancecode")}
        >
          <p>출석코드 생성</p>
        </button>
        <img src="/assets/img/logo.svg" alt="로고" />
      </div>
    </div>
  );
};

export default Admin;
