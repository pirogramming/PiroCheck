import api from "./api";

export const getStudentsByName = async (name) => {
  const res = await api.get(`/admin/managestudent`, {
    params: { name },
  });
  console.log("💬 getStudentsByName 응답:", res.data);
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

// 보증금 방어권 수정
export const updateStudentDefence = async (studentId, defenceValue) => {
  const res = await api.patch(`/admin/managestudent/${studentId}/defence`, {
    defence: defenceValue,
  });
  return res.data;
};
