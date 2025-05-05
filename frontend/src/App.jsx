import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Login";
import Home from "./Home";
import Assignment from "./Assignment";
import Deposit from "./Deposit";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/assignment" element={<Assignment />} />
        <Route path="/deposit" element={<Deposit />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
