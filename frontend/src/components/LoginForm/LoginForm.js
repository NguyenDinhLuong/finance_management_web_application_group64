import { useState } from 'react';
import Box from '@mui/joy/Box';
import Button from '@mui/joy/Button';
import FormControl from '@mui/joy/FormControl';
import FormLabel from '@mui/joy/FormLabel';
import Input from '@mui/joy/Input';
import Link from '@mui/joy/Link';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { login } from '../../apis/User';

export default function LoginForm() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  return (
    <form
      onSubmit={async event => {
        event.preventDefault();
        const formElements = event.currentTarget.elements;
        if (!isLoading) setIsLoading(true);
        const isSuccess = await login({
          username: formElements.username.value,
          password: formElements.password.value,
        });
        console.log(isSuccess);
        if (isSuccess) {
          localStorage.setItem('rate', 1);
          if (localStorage.getItem('currentCurrency') === null) {
            localStorage.setItem('currentCurrency', 'AUD');
          }
          navigate('/');
          setIsLoading(false);
        }
      }}
    >
      <FormControl required>
        <FormLabel>Username</FormLabel>
        <Input type="text" name="username" autoComplete="username" />
      </FormControl>
      <FormControl required>
        <FormLabel>Password</FormLabel>
        <Input
          type="password"
          name="password"
          autoComplete="current-password"
        />
      </FormControl>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
        }}
      >
        <Link
          component={RouterLink}
          fontSize="sm"
          to="/forgot-password"
          fontWeight="lg"
        >
          Forgot your password?
        </Link>
      </Box>
      <Button type="submit" fullWidth loading={isLoading}>
        Sign in
      </Button>
    </form>
  );
}
