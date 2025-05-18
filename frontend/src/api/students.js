import api from "./api";

export const getStudentsByName = async (name) => {
  const res = await api.get(`/admin/managestudent`, {
    params: { name },
  });
  return res.data; // [{ id: ..., name: ... }]
};
