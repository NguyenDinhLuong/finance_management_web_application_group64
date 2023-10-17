import jwt_decode from 'jwt-decode';

export function setToken(accessToken, refreshToken) {
  localStorage.setItem('access_token', accessToken);
  localStorage.setItem('refresh_token', refreshToken);
}

export function getAccessToken() {
  const tokenString = localStorage.getItem('access_token');
  return tokenString;
}

export function getRefreshToken() {
  const tokenString = localStorage.getItem('refresh_token');
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
  const decoded = getTokenDecode();
  let isAuth = false;
  if (decoded) {
    isAuth = decoded.exp * 1000 > Date.now();
  }
  return isAuth;
}
