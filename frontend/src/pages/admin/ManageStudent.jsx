import { useState } from "react";
import Header from "../../components/Header";
import InputBlock from "../../components/InputBlock";
import style from "./ManageStudent.module.css";

const ManageStudent = () => {
  const [studentName, setStudentName] = useState([""]);
  const [page, setPage] = useState(1);
  const studentsPerPage = 6;
  const studentList = [
    "김피로그",
    "박피로그",
    "이피로그",
    "최피로그",
    "김피로그",
    "박피로그",
    "이피로그",
    "최피로그",
    "김피로그",
    "박피로그",
    "이피로그",
    "최피로그",
    "김피로그",
    "박피로그",
    "이피로그",
    "최피로그",
    "경민",
    "경미니",
  ];
  const filteredStudents = studentList.filter((name) =>
    name.includes(studentName[0])
  );
  const totalPages = Math.ceil(filteredStudents.length / studentsPerPage);
  const paginatedStudents = filteredStudents.slice(
    (page - 1) * studentsPerPage,
    page * studentsPerPage
  );
  const handleChange = (index, value) => {
    const studentNames = [...studentName];
    studentNames[index] = value;
    setStudentName(studentNames);
    setPage(1); // 검색 시 페이지 초기화
  };

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
          {paginatedStudents.map((name, index) => (
            <button key={index} className={style.student_button}>
              {name} <span>&gt;</span>
            </button>
          ))}
        </div>

        {filteredStudents.length > studentsPerPage && (
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
