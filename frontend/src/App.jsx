import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Home from "./pages/generation/Home";
import Assignment from "./pages/generation/Assignment";
import Deposit from "./pages/generation/Deposit";
import Intro from "./Intro";
import Admin from "./pages/admin/Admin";
import ManageStudent from "./pages/admin/ManageStudent.jsx";
import ManageTask from "./pages/admin/ManageTask.jsx";
import AttendanceCode from "./pages/admin/AttendanceCode";
import Attendance from "./pages/generation/Attendance";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Intro />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/assignment" element={<Assignment />} />
        <Route path="/attendance" element={<Attendance />} />
        <Route path="/deposit" element={<Deposit />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/managestudent" element={<ManageStudent />} />
        <Route path="/magagetask" element={<ManageTask />} />
        <Route path="/attendancecode" element={<AttendanceCode />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
