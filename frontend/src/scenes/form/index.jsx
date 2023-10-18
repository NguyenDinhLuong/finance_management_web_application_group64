import { Box, Button, TextField } from '@mui/material';
import { Formik } from 'formik';
import * as yup from 'yup';
import useMediaQuery from '@mui/material/useMediaQuery';
import Header from '../../components/Header';
import { updateUserById } from '../../apis/User';

const Profile = () => {
  const isNonMobile = useMediaQuery('(min-width:600px)');
  const userId = localStorage.getItem('id');

  const handleSave = values => {
    const updatedData = {
      username: values.username,
      email: values.email,
      firstName: values.firstName,
      lastName: values.lastName,
      id: userId,
    };
    const isSuccess = updateUserById({
      ...updatedData,
    });

    if (isSuccess) {
      localStorage.setItem('username', values.username);
      localStorage.setItem('email', values.email);
      localStorage.setItem('firstName', values.firstName);
      localStorage.setItem('lastName', values.lastName);
      // setUserName(values.username);
      // setEmail(values.email);
      // setFirstName(values.firstName);
      // setLastName(values.lastName);
    }
  };

  return (
    <Box m="20px">
      <Header title="USER PROFILE" subtitle="Update User Profile" />

      <Formik
        initialValues={{
          username: localStorage.getItem('username'),
          email: localStorage.getItem('email'),
          firstName: localStorage.getItem('firstName'),
          lastName: localStorage.getItem('lastName'),
        }}
        onSubmit={handleSave}
        validationSchema={checkoutSchema}
      >
        {({
          values,
          errors,
          touched,
          handleBlur,
          handleChange,
          handleSubmit,
        }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="30px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                '& > div': { gridColumn: isNonMobile ? undefined : 'span 4' },
              }}
            >
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Username"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.username}
                name="username"
                error={!!touched.username && !!errors.username}
                helperText={touched.username && errors.username}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Email"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.email}
                name="email"
                error={!!touched.email && !!errors.email}
                helperText={touched.email && errors.email}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="First Name"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.firstName}
                name="firstName"
                error={!!touched.firstName && !!errors.firstName}
                helperText={touched.firstName && errors.firstName}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Last Name"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.lastName}
                name="lastName"
                error={!!touched.lastName && !!errors.lastName}
                helperText={touched.lastName && errors.lastName}
                sx={{ gridColumn: 'span 2' }}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Update User Profile
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

const checkoutSchema = yup.object().shape({
  username: yup.string().required('required'),
  email: yup.string().email('invalid email').required('required'),
  firstName: yup.string().required('required'),
  lastName: yup.string().required('required'),
});

export default Profile;
