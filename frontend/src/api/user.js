export const loginUser = async ({ name, password }) => {
  const res = await fetch("https://api.pirocheck.org/api/login", {
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
