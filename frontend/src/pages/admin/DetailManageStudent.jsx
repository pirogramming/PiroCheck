import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Header from "../../components/Header";
import style from "./DetailManageStudent.module.css";
import { getStudentDetail } from "../../api/students";

const DetailManageStudent = () => {
  const { studentId } = useParams();
  const [student, setStudent] = useState(null);

  useEffect(() => {
    const fetchStudent = async () => {
      try {
        const data = await getStudentDetail(studentId);
        setStudent(data);
      } catch (err) {
        console.error("학생 상세 정보 불러오기 실패:", err);
      }
    };

    fetchStudent();
  }, [studentId]);

  if (!student) return <div>loading...</div>;

  return (
    <div className={style.managestudent_wrapper}>
      <Header />
      <div className={style.under_header}>
        <div className={style.student_card}>
          <h2>{student.name}</h2>
          <p>잔여 보증금: {student.deposit}원</p>
          <p>보증금 방어권: {student.defence}</p>
        </div>

        <div className={style.assignment_list}>
          {student.assignmentTitles.map((title, idx) => (
            <button key={idx} className={style.assignment_button}>
              {title}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
};
export default DetailManageStudent;
