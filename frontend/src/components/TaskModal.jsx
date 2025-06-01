import { useState, useEffect } from "react";
import styles from "../pages/admin/ManageTask.module.css";
import { postAssignment, putAssignment, deleteAssignment  } from "../api/managetask";

const TaskModal = ({ weekInfo, onClose, onSubmit }) => {
  const [topic, setTopic] = useState("");
  const [taskGroups, setTaskGroups] = useState([]);

  useEffect(() => {
    if (weekInfo?.tasks?.length > 0) {
      setTopic(weekInfo.tasks[0].subtitle || "");

      // day별로 그룹화
      const dayMap = new Map();
      weekInfo.tasks.forEach((task) => {
        if (!dayMap.has(task.day)) {
          dayMap.set(task.day, []);
        }
        dayMap.get(task.day).push(task);
      });

      const grouped = Array.from(dayMap.entries()).map(([day, tasks]) => ({
        day,
        assignments: tasks.map((t) => t.assignmentName).slice(0, 3),
        ids: tasks.map((t) => t.id),
      }));

      setTaskGroups(grouped);
    } else {
      // 초기값
      setTaskGroups([{ day: "", assignments: ["", "", ""], ids: [] }]);
    }
  }, [weekInfo]);

  const handleDayChange = (index, value) => {
    const updated = [...taskGroups];
    updated[index].day = value;
    setTaskGroups(updated);
  };

  const handleAssignmentChange = (groupIndex, assignmentIndex, value) => {
    const updated = [...taskGroups];
    updated[groupIndex].assignments[assignmentIndex] = value;
    setTaskGroups(updated);
  };

  const handleAddGroup = () => {
    setTaskGroups([
      ...taskGroups,
      { day: "", assignments: ["", "", ""], ids: [] },
    ]);
  };

  const handleSave = async () => {
    const weekNumber = parseInt(weekInfo.week.replace("주차", ""), 10);
    const newTasks = [];

    taskGroups.forEach((group, groupIndex) => {
      group.assignments.forEach((assignmentName, i) => {
        if (assignmentName.trim() !== "") {
          newTasks.push({
            title: topic,
            subtitle: topic,
            assignmentName,
            week: weekNumber,
            day: group.day,
            orderNumber: i + 1,
            id: group.ids?.[i] ?? null,
          });
        }
      });
    });

    const requests = newTasks.map((task) =>
      task.id ? putAssignment(task.id, task) : postAssignment(task)
    );

    try {
      await Promise.all(requests);
      alert("과제가 저장되었습니다.");
      onSubmit && onSubmit(newTasks);
      onClose();
    } catch (err) {
      console.error("저장 오류:", err);
      alert("과제 저장 중 오류가 발생했습니다.");
    }
  };

  //과제 삭제
  const handleDeleteAssignment = async (groupIdx, assignmentIdx) => {
  const group = taskGroups[groupIdx];
  const assignmentId = group.ids?.[assignmentIdx];

  if (!assignmentId) {
    // 로컬에만 존재하는 입력이면 그냥 삭제
    const updated = [...taskGroups];
    updated[groupIdx].assignments[assignmentIdx] = "";
    setTaskGroups(updated);
    return;
  }

  const confirm = window.confirm("정말 이 과제를 삭제하시겠습니까?");
  if (!confirm) return;

  try {
    await deleteAssignment(assignmentId);
    const updated = [...taskGroups];
    updated[groupIdx].assignments[assignmentIdx] = "";
    updated[groupIdx].ids[assignmentIdx] = null;
    
    // 그룹 전체가 빈 경우 제거
    const filtered = updated.filter(group => group.assignments.some(a => a.trim() !== ""));
    
    if (filtered.length === 0) {
      alert("모든 과제가 삭제되어 주차 정보도 제거됩니다.");
      onSubmit && onSubmit([]); // 부모에서 주차 제거
      onClose();
      return;
    }

    setTaskGroups(filtered);
  } catch (err) {
    console.error("삭제 오류:", err);
    alert("과제 삭제 중 오류가 발생했습니다.");
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

          {taskGroups.map((group, groupIdx) => (
            <div key={groupIdx} className={styles.day_group}>
              <label>요일:</label>
              <input
                placeholder="요일 입력"
                value={group.day}
                onChange={(e) => handleDayChange(groupIdx, e.target.value)}
              />
              <label>과제:</label>
              <div className={styles.assignment_inputs}>
                {group.assignments.map((assignment, i) => (
                    <div key={i} className={styles.assignment_row}>
                  <input
                    key={i}
                    placeholder={`과제 ${i + 1}`}
                    value={assignment}
                    onChange={(e) =>
                      handleAssignmentChange(groupIdx, i, e.target.value)
                    }
                  />
                  <button
                    className={styles.delete_button}
                    onClick={() => handleDeleteAssignment(groupIdx, i)}
                  >
                    🗑
                  </button>
                  </div>
                ))}
              </div>
            </div>
          ))}

          <button onClick={handleAddGroup} className={styles.add_button}>
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
