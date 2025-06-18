import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Header from "../../components/Header";
import style from "./DetailManageStudent.module.css";
import { getStudentDetail } from "../../api/students";
import { updateStudentDefence } from "../../api/students"; // 보증금 방어권 수정 api

/*const weekData = [
  { week: "1주차", title: "Git/HTML/CSS" },
  { week: "2주차", title: "JavaScript/웹 개론" },
  { week: "3주차", title: "Django CRUD/DB 개론" },
  { week: "4주차", title: "Django ORM/Ajax" },
  { week: "5주차", title: "배포/아이디어 기획" },
];*/

const DetailManageStudent = () => {
  const { studentId } = useParams();
  const numericId = Number(studentId);
  const [student, setStudent] = useState(null);
  const [defenceInput, setDefenceInput] = useState(""); // 보증금 방어권 input 값
  const [assignmentsByWeek, setAssignmentsByWeek] = useState([]); 
  const navigate = useNavigate();

  useEffect(() => {
    if (!studentId || isNaN(Number(studentId))) {
      console.warn("❗ 잘못된 studentId (DetailManageStudent):", studentId);
      return;
    }

    const id = Number(studentId);

    const fetchStudent = async () => {
      try {
        const data = await getStudentDetail(id);
        console.log("API 응답 데이터:", data);
        setStudent(data);
        setDefenceInput(data.defence); // 보증금 업데이트
      } catch (err) {
        console.error("학생 상세 정보 불러오기 실패:", err);
      }
    };

    fetchStudent();
  }, [studentId]);


  useEffect(() => {
    const fetchWeeks = async () => {
      try {
        const raw = await getAssignments();

        const weekMap = new Map();
        raw.forEach((task) => {
          const week = task.week;
          if (!weekMap.has(week)) {
            weekMap.set(week, []);
          }
          weekMap.get(week).push(task);
        });

        const formatted = Array.from(weekMap.entries())
          .sort(([a], [b]) => a - b)
          .map(([week, tasks]) => ({
            week,
            title: tasks[0]?.title || "제목 없음",
          }));

        setAssignmentsByWeek(formatted);
      } catch (err) {
        console.error("과제 주차 데이터 불러오기 실패:", err);
      }
    };

    fetchWeeks();
  }, []);


  const handleDefenceSave = async () => {
    try {
      await updateStudentDefence(student.id, defenceInput);
      setStudent((prev) => ({ ...prev, defence: defenceInput }));
      alert("보증금 방어권이 수정되었습니다.");
    } catch (err) {
      console.error("방어권 업데이트 실패:", err);
      alert("방어권 업데이트 실패");
    }
  };

  if (!student) return <div>loading...</div>;

  console.log("studentId from URL:", studentId);
  console.log("numericId:", numericId);

  return (
    <div className={style.managestudent_wrapper}>
      <Header />
      <div className={style.under_header}>
        <div className={style.student_card}>
          <h2 className={style.student_name}>{student.name}</h2>
          <div className={style.deposit_container}>
            잔여 보증금: <span>{student.deposit}원</span>
          </div>
          <div className={style.defence_container}>
            보증금 방어권
            <input
              type="text"
              value={defenceInput}
              onChange={(e) => setDefenceInput(e.target.value)}
              className={style.defence_input}
            />
            <button
              className={style.defence_save_btn}
              onClick={handleDefenceSave}
              disabled={defenceInput === student.defence}
            >
              save
            </button>
          </div>
        </div>
        {student && (
          <button
            className={style.attendance_btn}
            onClick={() => navigate(`/admin/attendance/${student.id}`)}
          >
            출석 관리 <span>&gt;</span>
          </button>
        )}
        {student && (
          <div className={style.assignment_list}>
            {weekData.map((week, index) => (
              <button
                key={index}
                className={style.assignment_button}
                onClick={() =>
                  navigate(`/admin/assignment/${student.id}/${week.week}`,
                            {state: { title: week.title }})
                }
              >
                {week.week} {week.title && `  ${week.title}`}
              </button>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
export default DetailManageStudent;
