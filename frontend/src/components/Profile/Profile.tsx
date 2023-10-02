import { useState } from 'react';
import Box from '@mui/joy/Box';
import Button from '@mui/joy/Button';
import Divider from '@mui/joy/Divider';
import FormControl from '@mui/joy/FormControl';
import FormLabel from '@mui/joy/FormLabel';
import Input from '@mui/joy/Input';
import Typography from '@mui/joy/Typography';
import EditIcon from '@mui/icons-material/Edit';
import { updateUserById } from '../../apis/User';

interface FormElements extends HTMLFormControlsCollection {
  username: HTMLInputElement;
  email: HTMLInputElement;
  firstName: HTMLInputElement;
  lastName: HTMLInputElement;
}

interface ProfileFormElement extends HTMLFormElement {
  readonly elements: FormElements;
}

export default function Profile() {
  const [isEdit, setIsEdit] = useState(false);
  const [email, setEmail] = useState(localStorage.getItem('email'));
  const [username, setUserName] = useState(localStorage.getItem('username'));
  const [firstName, setFirstName] = useState(localStorage.getItem('firstName'));
  const [lastName, setLastName] = useState(localStorage.getItem('email'));
  const userId = localStorage.getItem('id') as string;

  const handleSave = async (event: React.FormEvent<ProfileFormElement>) => {
    event.preventDefault();
    const formElements = event.currentTarget.elements;
    const updatedData = {
      username: formElements.username.value,
      email: formElements.email.value,
      firstName: formElements.firstName.value,
      lastName: formElements.lastName.value,
      id: userId,
    };
    const isSuccess = await updateUserById({
      ...updatedData,
    });

    if (isSuccess) {
      setIsEdit(false);
      setEmail(formElements.email.value);
      setUserName(formElements.username.value);
      setFirstName(formElements.firstName.value);
      setLastName(formElements.lastName.value);
      localStorage.removeItem('email');
      localStorage.removeItem('username');
      localStorage.removeItem('firstName');
      localStorage.removeItem('lastName');
    }
  };

  return (
    <Box
      width={{ xs: '90%', md: '100%' }}
      sx={{
        flex: 1,
        maxWidth: 1200,
        mx: 'auto',
      }}
    >
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
          mb: 4,
        }}
      >
        <Typography level="h1" fontSize="xl2">
          My profile
        </Typography>
      </Box>
      <Box
        component="form"
        onSubmit={(event: React.FormEvent<ProfileFormElement>) =>
          handleSave(event)
        }
        sx={{
          display: 'grid',
          gridTemplateColumns: {
            xs: '100%',
            sm: 'minmax(120px, 30%) 1fr',
            lg: '280px 1fr minmax(120px, 208px)',
          },
          columnGap: { xs: 2, sm: 3, md: 4 },
          rowGap: { xs: 2, sm: 2.5 },
          '& > hr': {
            gridColumn: '1/-1',
          },
        }}
      >
        <FormControl sx={{ display: { sm: 'contents' } }}>
          {isEdit ? (
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Button
                variant="outlined"
                color="neutral"
                size="md"
                onClick={() => setIsEdit(false)}
              >
                Cancel
              </Button>
              <Button size="md" color="success" type="submit">
                Save
              </Button>
            </Box>
          ) : (
            <Button
              size="md"
              startDecorator={<EditIcon />}
              onClick={() => setIsEdit(true)}
            >
              Edit
            </Button>
          )}
        </FormControl>
        <Divider role="presentation" />
        <FormControl sx={{ display: { sm: 'contents' } }}>
          <FormLabel>Email</FormLabel>
          {isEdit ? (
            <Input
              placeholder="email"
              name="email"
              defaultValue={email as string}
            />
          ) : (
            <p className="px-3 py-2">{email}</p>
          )}
        </FormControl>
        <Divider role="presentation" />
        <FormControl sx={{ display: { sm: 'contents' } }}>
          <FormLabel>Username</FormLabel>
          {isEdit ? (
            <Input
              placeholder="username"
              name="username"
              defaultValue={username as string}
            />
          ) : (
            <p className="px-3 py-2">{username}</p>
          )}
        </FormControl>
        <Divider role="presentation" />
        <FormControl sx={{ display: { sm: 'contents' } }}>
          <FormLabel>First name</FormLabel>
          {isEdit ? (
            <Input
              placeholder="first name"
              name="firstName"
              defaultValue={firstName as string}
            />
          ) : (
            <p className="px-3 py-2">{firstName}</p>
          )}
        </FormControl>
        <Divider role="presentation" />
        <FormControl sx={{ display: { sm: 'contents' } }}>
          <FormLabel>Last name</FormLabel>
          {isEdit ? (
            <Input
              placeholder="last name"
              name="lastName"
              defaultValue={lastName as string}
            />
          ) : (
            <p className="px-3 py-2">{lastName}</p>
          )}
        </FormControl>
      </Box>
    </Box>
  );
}
