import { Navigate } from "react-router-dom";

const RequireAuth = ({ children }) => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default RequireAuth;
