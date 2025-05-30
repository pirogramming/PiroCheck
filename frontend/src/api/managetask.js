import api from "./api";

// 과제 등록
export const postAssignment = async (data) => {
  return api.post("/admin/assignment/signup", data);
};

// 과제 목록 조회
export const getAssignments = async () => {
  const res = await api.get("/admin/assignment/search");
  return res.data;
};

// 과제 수정
export const putAssignment = async (assignmentId, data) => {
  return api.put(`/admin/assignment/${assignmentId}`, data);
};
