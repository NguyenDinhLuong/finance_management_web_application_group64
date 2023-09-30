import Button from '@mui/joy/Button';
import FormControl from '@mui/joy/FormControl';
import FormLabel from '@mui/joy/FormLabel';
import Input from '@mui/joy/Input';
import { useNavigate } from 'react-router-dom';

interface FormElements extends HTMLFormControlsCollection {
  firstname: HTMLInputElement;
  lastname: HTMLInputElement;
  email: HTMLInputElement;
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
      }}
    >
      <FormControl required>
        <FormLabel>First Name *</FormLabel>
        <Input type="text" name="firstname" />
      </FormControl>
      <FormControl required>
        <FormLabel>Last Name *</FormLabel>
        <Input type="text" name="lastname" />
      </FormControl>
      <FormControl required>
        <FormLabel>Email Address *</FormLabel>
        <Input type="email" name="email" />
      </FormControl>
      <FormControl required>
        <FormLabel>Password *</FormLabel>
        <Input type="password" name="password" />
      </FormControl>
      <Button type="submit" fullWidth>
        Sign up
      </Button>
    </form>
  );
}
