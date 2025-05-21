import api from "./api";

export const getStudentsByName = async (name) => {
  const res = await api.get(`/admin/managestudent`, {
    params: { name },
  });
  return res.data; // [{ id: ..., name: ... }]
};

export const getStudentDetail = async (studentId) => {
  try {
    const res = await api.get(`/admin/managestudent/${studentId}`);
    return res.data;
  } catch (error) {
    console.error("학생 상세 정보 불러오기 실패:", error);
    throw error;
  }
};
