import { toast } from 'react-toastify';
import apiInstance from './Axios';
import { setToken } from '../utils/token';

interface Login {
  username: string;
  password: string;
}

interface SignUp extends Login {
  email: String;
  firstName: string;
  lastName: string;
  confirmPassword: String;
}

export const signUp = async ({
  username,
  email,
  firstName,
  lastName,
  password,
  confirmPassword,
}: SignUp) => {
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

export const login = async ({ username, password }: Login) => {
  const response = await apiInstance
    .post('/auth/signin', { username, password })
    .then(response => {
      console.log('login: ', response.data);
      toast.success('Successfully login!');
      setToken(response.data.jwtToken);
      return true;
    })
    .catch(error => {
      console.log('login error: ', error.response);
      toast.error('Something wrong with your account.');
      return false;
    });
  return response;
};
