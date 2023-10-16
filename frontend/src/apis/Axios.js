import axios from 'axios';

const apiInstance = axios.create({
  baseURL: 'http://localhost:8080/api', // My Spring Boot server URL
  headers: {
    Authorization: `Bearer ${localStorage.getItem('access_token')}`,
    Accept: 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
    'Content-Type': 'application/json',
  },
});

export default apiInstance;
