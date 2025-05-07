import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./assets/root.css";
import { BrowserRouter } from "react-router-dom";
import "./assets/reset.css";
import { StrictMode } from "react";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <App />
  </StrictMode>
);
