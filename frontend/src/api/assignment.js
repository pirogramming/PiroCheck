import axios from "axios";

export const fetchAssignmentsByUser = async (userId) => {
  const res = await axios.get(`/api/assignment/${userId}`);
  return res.data;
};
