import { useState } from "react";
import styles from "../pages/admin/ManageTask.module.css";
import api from "../api/api";

const TaskModal = ({ weekInfo, onClose }) => {
  const [topic, setTopic] = useState("");
  const [day, setDay] = useState("");
  const [taskList, setTaskList] = useState([""]);

  const handleTaskChange = (index, value) => {
    const newTasks = [...taskList];
    newTasks[index] = value;
    setTaskList(newTasks);
  };

  const handleAddTask = () => {
    setTaskList([...taskList, ""]);
  };

  const handleSave = async () => {
    console.log("save clicked");

    const weekNumber = parseInt(weekInfo.week.replace("주차", "")); // 주차 숫자 정보만 추출
    const filteredTasks = taskList.filter((t) => t.trim() !== ""); // 빈 값 제거

    const requests = filteredTasks.map((task, index) => {
      console.log("sending:", {
        subject: topic,
        assignmentName: task,
        week: weekNumber,
        day: day,
        orderNumber: index + 1,
      });

      return api.post("/admin/assignment/signup", {
        subject: topic,
        assignmentName: task,
        week: weekNumber,
        day: day,
        orderNumber: index + 1,
      });
    });

    try {
      const response = await Promise.all(requests);
      console.log("응답들: ", response);
      alert("과제가 저장되었습니다.");
      onClose();
    } catch (error) {
      console.error("저장 오류:", error);
      alert("과제 저장 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className={styles.modal_overlay}>
      <div className={styles.modal}>
        <div className={styles.modal_header}>
          <h3>{weekInfo.week}</h3>
          <button className={styles.close_button} onClick={onClose}>
            ×
          </button>
        </div>
        <div className={styles.modal_body}>
          <label>주제:</label>
          <input
            placeholder="주제를 입력하세요."
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
          />
          <label>요일:</label>
          <input
            placeholder="요일을 입력하세요."
            value={day}
            onChange={(e) => setDay(e.target.value)}
          />
          <label>과제:</label>
          {taskList.map((task, i) => (
            <input
              key={i}
              placeholder="과제를 입력하세요."
              value={task}
              onChange={(e) => handleTaskChange(i, e.target.value)}
            />
          ))}
          <button onClick={handleAddTask} className={styles.add_button}>
            +
          </button>
        </div>
        <div className={styles.modal_footer}>
          <button className={styles.save_button} onClick={handleSave}>
            save
          </button>
        </div>
      </div>
    </div>
  );
};

export default TaskModal;
