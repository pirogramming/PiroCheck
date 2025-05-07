import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./root.css";
import "./reset.css";
import { StrictMode } from "react";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <App />
  </StrictMode>
);
