// src/api/assignmentAdmin.js
import api from "./api";

// 학생 정보 불러오기
/*
export const fetchStudentInfo = (studentId) =>
  api.get(`/admin/users/${studentId}`);
*/
export const fetchStudentInfo = async (studentId) => {
  try {
    const res = await api.get(`/admin/managestudent/${studentId}`);
    return res.data;
  } catch (error) {
    console.error("학생 상세 정보 불러오기 실패:", error);
    throw error;
  }
};

// 주차별 과제 데이터 불러오기
export const fetchStudentAssignments = (userId) =>
  api.get(`/assignment/${userId}`); 

// 과제 상태 수정 (PUT)
export const updateAssignmentStatus = (userId, assignmentId, status) =>
  api.put(`/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    status,
  });

// 과제 상태 등록 (POST)
export const submitAssignmentStatus = (userId, assignmentId, status) =>
  api.post(`/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    status,
  });
