import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Home from "./Home";
import Assignment from "./Assignment";
import Deposit from "./Deposit";
import Intro from "./Intro";
import Admin from "./Admin";
import Attendance from "./Attendance";
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
      </Routes>
    </BrowserRouter>
  );
}

export default App;