import Header from "../../components/Header";
import style from "./AttendanceCode.module.css";
const AttendanceCode = () => {
  return (
    <div className={style.attendancecode_wraper}>
      <Header />
      <div className={style.num_container}>
        <div className={style.num_wrapper}></div>
        <div className={style.num_wrapper}></div>
        <div className={style.num_wrapper}></div>
        <div className={style.num_wrapper}></div>
      </div>
      <button className={style.createbutton}>생성</button>
    </div>
  );
};
export default AttendanceCode;
