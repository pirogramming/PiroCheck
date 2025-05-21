import api from "./api";
/*
export const fetchAssignmentsByUser = async (userId) => {
  const res = await api.get(`/assignment/grouped/${userId}`);
  return res.data;
};
*/
export const fetchAssignmentsByUser = async (userId) => {
  try {
    const res = await api.get(`/api/assignment/${userId}`);
    return res.data; // 백엔드가 반환하는 JSON 그대로
  } catch (err) {
    console.error("과제 데이터 불러오기 실패:", err);
    throw err;
  }
};


export const submitAssignmentStatus = async (userId, assignmentId, status) => {
  return api.post(`/api/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    assignmentId,
    userId,
    status,
  });
};

export const updateAssignmentStatus = async (userId, assignmentId, status) => {
  return api.put(`/api/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    status,
  });
};
