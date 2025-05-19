import { Navigate } from "react-router-dom";

const RequireAdmin = ({ children }) => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (!user) return <Navigate to="/login" replace />;
  if (user.role !== "ADMIN") return <Navigate to="/home" replace />;

  return children;
};

export default RequireAdmin;
