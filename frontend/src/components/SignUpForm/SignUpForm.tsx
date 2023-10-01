import Button from '@mui/joy/Button';
import FormControl from '@mui/joy/FormControl';
import FormLabel from '@mui/joy/FormLabel';
import Input from '@mui/joy/Input';
import { useNavigate } from 'react-router-dom';
import { signUp } from '../../apis/User';

interface FormElements extends HTMLFormControlsCollection {
  username: HTMLInputElement;
  email: HTMLInputElement;
  firstName: HTMLInputElement;
  lastName: HTMLInputElement;
  confirmPassword: HTMLInputElement;
  password: HTMLInputElement;
}

interface SignUpFormElement extends HTMLFormElement {
  readonly elements: FormElements;
}

export default function SignUpForm() {
  const navigate = useNavigate();
  return (
    <form
      onSubmit={async (event: React.FormEvent<SignUpFormElement>) => {
        event.preventDefault();
        const formElements = event.currentTarget.elements;
        const isSuccess = await signUp({
          username: formElements.username.value,
          email: formElements.email.value,
          firstName: formElements.firstName.value,
          lastName: formElements.lastName.value,
          confirmPassword: formElements.confirmPassword.value,
          password: formElements.password.value,
        });
        if (isSuccess) {
          setTimeout(() => navigate('/login'), 4000);
        }
      }}
    >
      <FormControl required>
        <FormLabel>Username *</FormLabel>
        <Input type="text" name="username" />
      </FormControl>
      <FormControl required>
        <FormLabel>Email Address *</FormLabel>
        <Input type="email" name="email" />
      </FormControl>
      <FormControl required>
        <FormLabel>First Name *</FormLabel>
        <Input type="text" name="firstName" />
      </FormControl>
      <FormControl required>
        <FormLabel>Last Name *</FormLabel>
        <Input type="text" name="lastName" />
      </FormControl>
      <FormControl required>
        <FormLabel>Password *</FormLabel>
        <Input type="password" name="password" />
      </FormControl>
      <FormControl required>
        <FormLabel>Confirm Password *</FormLabel>
        <Input type="password" name="confirmPassword" />
      </FormControl>
      <Button type="submit" fullWidth>
        Sign up
      </Button>
    </form>
  );
}
