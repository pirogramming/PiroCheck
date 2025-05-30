import { useState, useEffect } from "react";
import Header from "../../components/Header";
import style from "./ManageTask.module.css";
import TaskModal from "../../components/TaskModal";
import { getAssignments } from "../../api/managetask";

const ManageTask = () => {
  const [assignmentsByWeek, setAssignmentsByWeek] = useState([]);
  const [selectedWeekIndex, setSelectedWeekIndex] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    const fetchAssignments = async () => {
      try {
        const rawAssignments = await getAssignments();

        const weekMap = new Map();
        rawAssignments.forEach((task) => {
          const week = task.week;
          if (!weekMap.has(week)) {
            weekMap.set(week, []);
          }
          weekMap.get(week).push(task);
        });

        const formatted = Array.from(weekMap.entries())
          .sort(([a], [b]) => a - b)
          .map(([week, tasks]) => ({
            week: `${week}주차`,
            title: tasks[0]?.title || "Comming soon~",
            tasks,
          }));

        setAssignmentsByWeek(formatted);
      } catch (err) {
        console.error("과제 데이터 불러오기 실패:", err);
      }
    };

    fetchAssignments();
  }, []);

  const handleEditClick = (index) => {
    setSelectedWeekIndex(index);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const handleAddWeek = () => {
    const nextWeekNumber = assignmentsByWeek.length + 1;
    setAssignmentsByWeek([
      ...assignmentsByWeek,
      {
        week: `${nextWeekNumber}주차`,
        title: "Comming soon~",
        tasks: [],
      },
    ]);
  };

  const handleAddTaskToWeek = (weekIndex, newTasks) => {
    const updated = [...assignmentsByWeek];
    updated[weekIndex].tasks = newTasks;
    updated[weekIndex].title = newTasks[0]?.title || "Comming soon~";
    setAssignmentsByWeek(updated);
  };

  return (
    <div className={style.manage_task_container}>
      <div className={style.managetask_wrapper}>
        <Header />
        <div className={style.week_container}>
          {assignmentsByWeek.map((week, index) => (
            <div key={index} className={style.week_block}>
              <button
                className={style.week_button}
                onClick={() => handleEditClick(index)}
              >
                {week.week} {week.title}
              </button>
            </div>
          ))}
        </div>
        <img
          className={style.plus}
          src="/assets/img/plus.svg"
          onClick={handleAddWeek}
          alt="Add"
        />
      </div>

      {showModal && (
        <TaskModal
          weekInfo={assignmentsByWeek[selectedWeekIndex]}
          onClose={closeModal}
          onSubmit={(newTasks) => {
            handleAddTaskToWeek(selectedWeekIndex, newTasks);
            closeModal();
          }}
        />
      )}
    </div>
  );
};

export default ManageTask;
