import { useState } from "react";
import Header from "../../components/Header";
import InputBlock from "../../components/InputBlock";
import style from "./ManageStudent.module.css";

const ManageStudent = () => {
  const [studentName, setStudentName] = useState([""]);

  const handleChange = (index, value) => {
    const studentNames = [...studentName];
    studentNames[index] = value;
    setStudentName(studentNames);
  };

  return (
    <div className={style.managestudent_wrapper}>
      <Header />
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
    </div>
  );
};
export default ManageStudent;
