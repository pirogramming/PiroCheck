import { useState } from "react";
import styles from "../pages/admin/ManageTask.module.css";

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
          <button className={styles.save_button}>save</button>
        </div>
      </div>
    </div>
  );
};

export default TaskModal;
