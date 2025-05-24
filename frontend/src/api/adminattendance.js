import api from "./api";



// 학생 기본 정보 조회
export const getStudentBasicInfo = async (studentId) => {
  try {
    const res = await api.get(`/admin/managestudent/${studentId}`);
    return res.data;
  } catch (error) {
    console.error("학생 기본 정보 불러오기 실패:", error);
    throw error;
  }
};


// 학생 출석 전체 데이터 조회 (특정 날짜와 차수 포함)
export const getStudentAttendance = async (studentId) => {
  try {
    const res = await api.get(`/attendance/${studentId}`);
    return res.data;
  } catch (error) {
    console.error("학생 출석 정보 불러오기 실패:", error);
    throw error;
  }
};

// 특정 출석 기록 조회
export const getAttendanceDetail = async (userId, attendanceId) => {
  try {
    const res = await api.get(`/admin/users/${userId}/attendance/${attendanceId}`);
    return res.data;
  } catch (error) {
    console.error("출석 상세 조회 실패:", error);
    throw error;
  }
};

// 출석 상태 변경
export const updateAttendanceStatus = async (userId, attendanceId, status) => {
  try {
    const res = await api.put(`/admin/users/${userId}/attendance/${attendanceId}/status`, { status });
    return res.data;
  } catch (error) {
    console.error("출석 상태 변경 실패:", error);
    throw error;
  }
};

// 출석 기록 삭제
export const deleteAttendanceRecord = async (userId, attendanceId) => {
  try {
    await api.delete(`/admin/users/${userId}/attendance/${attendanceId}`);
  } catch (error) {
    console.error("출석 기록 삭제 실패:", error);
    throw error;
  }
};
