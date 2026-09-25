import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    const message = err.response?.data?.message || 'Erreur inconnue';
    const code = err.response?.data?.code || 'ERREUR';
    return Promise.reject({ code, message, status: err.response?.status });
  }
);

export default api;
