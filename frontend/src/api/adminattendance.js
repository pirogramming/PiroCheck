import api from "./api";

// api/attendanceApi.js

export const getStudentBasicInfo = async (studentId) => {
  try {
    const res = await api.get(`/admin/managestudent/${studentId}`);
    return res.data;
  } catch (error) {
    console.error("학생 기본 정보 불러오기 실패:", error);
    throw error;
  }
};

export const getStudentAttendance = async (studentId) => {
  try {
    const res = await api.get("/admin/attendance/user", {
      params: { userId: studentId },
      withCredentials: true,
    });
    return res.data;
  } catch (error) {
    console.error("학생 출석 정보 불러오기 실패:", error);
    throw error;
  }
};
