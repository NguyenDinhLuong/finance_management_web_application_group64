import axios from 'axios';

const apiInstance = axios.create({
  baseURL: 'http://localhost:8080/api', // My Spring Boot server URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiInstance;
