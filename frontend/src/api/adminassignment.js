// src/api/assignmentAdmin.js
import api from "./api";

// 학생 정보 불러오기
export const fetchStudentInfo = (studentId) =>
  api.get(`/admin/users/${studentId}`);

// 주차별 과제 데이터 불러오기
export const fetchStudentAssignments = (userId) =>
  api.get(`/api/assignment/${userId}`); // ← 수정됨

// 과제 상태 수정 (PUT)
export const updateAssignmentStatus = (userId, assignmentId, status) =>
  api.put(`/api/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    status,
  });

// 과제 상태 등록 (POST)
export const submitAssignmentStatus = (userId, assignmentId, status) =>
  api.post(`/api/admin/users/${userId}/assignments/${assignmentId}/submission`, {
    status,
  });
