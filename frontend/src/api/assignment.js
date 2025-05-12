import api from "./api";

export const fetchAssignmentsByUser = async (userId) => {
  const res = await api.get(`/assignment/grouped/${userId}`);
  return res.data;
};
