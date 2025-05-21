import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Home from "./pages/generation/Home";
import Assignment from "./pages/generation/Assignment";
import Deposit from "./pages/generation/Deposit";
import Intro from "./Intro";
import Admin from "./pages/admin/Admin";
import DetailManageStudent from "./pages/admin/DetailManageStudent.jsx";
import ManageStudent from "./pages/admin/ManageStudent.jsx";
import ManageTask from "./pages/admin/ManageTask.jsx";
import AttendanceCode from "./pages/admin/AttendanceCode";
import Attendance from "./pages/generation/Attendance";
import AdminStudentAttendance from "./pages/admin/AdminStudentAttendance";
import AdminStudentAssignment from "./pages/admin/AdminStudentAssignment.jsx";
import RequireAuth from "./components/RequireAuth";
import RequireAdmin from "./components/RequireAdmin";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Intro />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/home"
          element={
            <RequireAuth>
              <Home />
            </RequireAuth>
          }
        />
        <Route
          path="/assignment"
          element={
            <RequireAuth>
              <Assignment />
            </RequireAuth>
          }
        />
        <Route
          path="/attendance"
          element={
            <RequireAuth>
              <Attendance />
            </RequireAuth>
          }
        />
        <Route
          path="/deposit"
          element={
            <RequireAuth>
              <Deposit />
            </RequireAuth>
          }
        />
        <Route
          path="/admin"
          element={
            <RequireAdmin>
              <Admin />
            </RequireAdmin>
          }
        />
        <Route
          path="/managestudent"
          element={
            <RequireAdmin>
              <ManageStudent />
            </RequireAdmin>
          }
        />
        <Route
          path="/managestudent/detail/:studentId"
          element={
            <RequireAdmin>
              <DetailManageStudent />
            </RequireAdmin>
          }
        />
        <Route
          path="/managetask"
          element={
            <RequireAdmin>
              <ManageTask />
            </RequireAdmin>
          }
        />
        <Route
          path="/attendancecode"
          element={
            <RequireAdmin>
              <AttendanceCode />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/attendance/:studentId"
          element={
            <RequireAdmin>
              <AdminStudentAttendance />
            </RequireAdmin>
          }
        />
        <Route
          path="/admin/assignment/:studentId"
          element={
            <RequireAdmin>
              <AdminStudentAssignment />
            </RequireAdmin>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
