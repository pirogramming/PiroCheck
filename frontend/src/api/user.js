export const loginUser = async ({ name, password }) => {
  const res = await fetch("http://api.pirocheck.org:8080/api/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({ name, password }),
  });

  return res;
};

export default loginUser;
