import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./assets/root.css";
import { BrowserRouter } from "react-router-dom";
import "./assets/reset.css";

createRoot(document.getElementById("root")).render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
