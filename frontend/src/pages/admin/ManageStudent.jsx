import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getStudentsByName } from "../../api/students";
import Header from "../../components/Header";
import InputBlock from "../../components/InputBlock";
import style from "./ManageStudent.module.css";

const ManageStudent = () => {
  const [studentName, setStudentName] = useState([""]);
  const [page, setPage] = useState(1);
  const [students, setStudents] = useState([]); // 서버 데이터 저장
  const navigate = useNavigate();

  const studentsPerPage = 6;

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const name = studentName[0] || "";
        const data = await getStudentsByName(name);
        setStudents(data);
      } catch (err) {
        console.error("수강생 불러오기 실패:", err);
      }
    };

    fetchStudents();
  }, [studentName]);

  const handleChange = (index, value) => {
    const newNames = [...studentName];
    newNames[index] = value;
    setStudentName(newNames);
    setPage(1); // 검색 시 페이지 초기화
  };

  const totalPages = Math.ceil(students.length / studentsPerPage);
  const paginatedStudents = students.slice(
    (page - 1) * studentsPerPage,
    page * studentsPerPage
  );

  return (
    <div className={style.managestudent_wrapper}>
      <Header />
      <div className={style.under_header}>
        <InputBlock
          inputs={[
            {
              type: "text",
              placeholder: "수강생을 검색하세요",
            },
          ]}
          values={studentName}
          onChange={handleChange}
        />
        <div className={style.student_list}>
          {paginatedStudents.map((student, index) => {
            console.log("student to show:", student); // 🔍 여기 추가
            return (
              <button
                key={student.id || index}
                className={style.student_button}
                onClick={() => navigate(`/managestudent/detail/${student.id}`)}
              >
                {student.name} <span>&gt;</span>
              </button>
            );
          })}
        </div>

        {students.length > studentsPerPage && (
          <div className={style.pagination}>
            <button
              onClick={() => setPage((p) => Math.max(p - 1, 1))}
              disabled={page === 1}
            >
              ◀ 이전
            </button>
            <span>
              {page} / {totalPages}
            </span>
            <button
              onClick={() => setPage((p) => Math.min(p + 1, totalPages))}
              disabled={page === totalPages}
            >
              다음 ▶
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
export default ManageStudent;
