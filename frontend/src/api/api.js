import axios from "axios";

const api = axios.create({
  baseURL: "http://api.pirocheck.org:8080/api",

  // 수정 필요한지 재검 필요함
  // "http://api.pirocheck.org:8080/api"

  withCredentials: true,
});

// 401 오류 시 로그인 페이지로 리다이렉트
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;
