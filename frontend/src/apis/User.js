import { toast } from 'react-toastify';
import apiInstance from './Axios';
import { setToken } from '../utils/token';

export const signUp = async ({
  username,
  email,
  firstName,
  lastName,
  password,
  confirmPassword,
}) => {
  if (password !== confirmPassword) {
    toast.error('Password and confirm password must be the same');
    return false;
  }
  const response = await apiInstance
    .post('/auth/signup', { username, email, firstName, lastName, password })
    .then(response => {
      toast.success(
        'Successfully registered! will redirect you to the login page.'
      );
      return true;
    })
    .catch(error => {
      toast.error(error);
      return false;
    });
  return response;
};

export const login = async ({ username, password }) => {
  const response = await apiInstance
    .post('/auth/signin', { username, password })
    .then(response => {
      toast.success('Successfully login!');
      setToken(response.data.jwtToken, response.data.refreshToken);
      localStorage.setItem('email', response.data.email);
      localStorage.setItem('username', response.data.username);
      localStorage.setItem('firstName', response.data.firstName);
      localStorage.setItem('lastName', response.data.lastName);
      localStorage.setItem('id', response.data.id);
      return true;
    })
    .catch(error => {
      console.log('login error: ', error.response);
      toast.error('Something wrong with your account.');
      return false;
    });
  return response;
};

export const updateUserById = async ({
  username,
  email,
  firstName,
  lastName,
  id,
}) => {
  const response = await apiInstance
    .put(`/auth/updateUser/${id}`, { username, email, firstName, lastName })
    .then(response => {
      toast.success('User updated successfully!');
      return true;
    })
    .catch(error => {
      toast.error('User updated unsuccessfully!');
      return false;
    });
  return response;
};
