import jwt_decode from 'jwt-decode';
import { DecodedToken } from '../types';

export function setToken(accessToken: string, refreshToken: string) {
  localStorage.setItem('access_token', accessToken);
  localStorage.setItem('refresh_token', refreshToken);
}

export function getAccessToken() {
  const tokenString = localStorage.getItem('access_token') as string;
  return tokenString;
}

export function getRefreshToken() {
  const tokenString = localStorage.getItem('refresh_token') as string;
  return tokenString;
}

export function cleanToken() {
  localStorage.removeItem('access_token');
  localStorage.removeItem('refresh_token');
}

export function getTokenDecode() {
  const token = getAccessToken();
  if (token) {
    return jwt_decode(token);
  }
  return null;
}

export function getIsAuth() {
  const decoded = getTokenDecode() as DecodedToken;
  let isAuth = false;
  if (decoded) {
    isAuth = decoded.exp * 1000 > Date.now();
  }
  return isAuth;
}
