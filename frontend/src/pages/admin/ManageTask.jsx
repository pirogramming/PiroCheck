import { useState } from "react";
import Header from "../../components/Header";
import style from "./ManageTask.module.css";
import TaskModal from "../../components/TaskModal";

const weekData = [
  { week: "1주차", title: "Comming soon~", tasks: [] },
  { week: "2주차", title: "Comming soon~", tasks: [] },
  { week: "3주차", title: "Comming soon~", tasks: [] },
  { week: "4주차", title: "Comming soon~", tasks: [] },
  { week: "5주차", title: "Comming soon~", tasks: [] },
];

const ManageTask = () => {
  const [selectedWeekIndex, setSelectedWeekIndex] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const handleEditClick = (index) => {
    setSelectedWeekIndex(index);
    setShowModal(true);
  };

  const closeModal = () => setShowModal(false);

  return (
    <div className={style.manage_task_container}>
      <div className={style.managetask_wrapper}>
        <Header />
        <div className={style.week_container}>
          {weekData.map((week, index) => (
            <div key={index} className={style.week_block}>
              <button className={style.week_button}>
                {week.week} {week.title && `  ${week.title}`}
              </button>
              <img
                src="/assets/img/edit.png"
                alt="edit"
                className={style.edit_icon}
                onClick={() => handleEditClick(index)}
              />
            </div>
          ))}
        </div>
        <img className={style.plus} src="assets/img/plus.svg"></img>
      </div>
      {showModal && (
        <TaskModal
          weekInfo={weekData[selectedWeekIndex]}
          onClose={closeModal}
        />
      )}
    </div>
  );
};
export default ManageTask;
