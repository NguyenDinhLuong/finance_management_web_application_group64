import {
  Box,
  Button,
  TextField,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from '@mui/material';
import { Formik } from 'formik';
import * as yup from 'yup';
import Header from '../../components/Header';
import { addIncome } from '../../apis/Income';

const IncomeForm = () => {
  const userId = localStorage.getItem('id');

  const handleFormSubmit = values => {
    const updatedData = {
      amount: values.amount,
      source: values.source,
      category: values.category,
      date: values.date,
      status: values.status,
      location: values.location,
      user_id: userId,
    };

    const isSuccess = addIncome({
      ...updatedData,
    });

    if (isSuccess) {
    }
  };

  return (
    <Box m="20px">
      <Header title="CREATE INCOME" subtitle="Create a New Income" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
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
            >
              <TextField
                fullWidth
                variant="filled"
                type="number"
                label="Amount"
                placeholder="$"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.amount}
                name="amount"
                error={!!touched.amount && !!errors.amount}
                helperText={touched.amount && errors.amount}
                sx={{ gridColumn: 'span 2' }}
                inputProps={{ step: '0.01' }}
              />
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Category</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="category"
                >
                  <MenuItem value="Salary">Salary</MenuItem>
                  <MenuItem value="Bonuses">Bonuses</MenuItem>
                  <MenuItem value="Commissions">Commissions</MenuItem>
                  <MenuItem value="Capital Gains">Capital Gains</MenuItem>
                  <MenuItem value="Others">Others</MenuItem>
                </Select>
              </FormControl>
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Source"
                placeholder="Source"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.source}
                name="source"
                error={!!touched.source && !!errors.source}
                helperText={touched.source && errors.source}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Date"
                placeholder="yyyy-MM-dd"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.date}
                name="date"
                error={!!touched.date && !!errors.date}
                helperText={touched.date && errors.date}
                sx={{ gridColumn: 'span 2' }}
              />
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Status</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="status"
                >
                  <MenuItem value="Pending">Pending</MenuItem>
                  <MenuItem value="Received">Received</MenuItem>
                  <MenuItem value="Overdue">Overdue</MenuItem>
                </Select>
              </FormControl>
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Location"
                placeholder="Location"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.location}
                name="location"
                error={!!touched.location && !!errors.location}
                helperText={touched.location && errors.location}
                sx={{ gridColumn: 'span 2' }}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Income
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

const dateRegExp = '\\d{4}-\\d{2}-\\d{2}';

const checkoutSchema = yup.object().shape({
  amount: yup.string().required('required'),
  source: yup.string().required('required'),
  category: yup.string().required('required'),
  date: yup
    .string()
    .matches(dateRegExp, 'Date format is not valid')
    .required('required'),
  status: yup.string().required('required'),
  location: yup.string().required('required'),
});
const initialValues = {
  amount: '',
  source: '',
  category: '',
  date: '',
  status: '',
  location: '',
  user_id: '',
};

export default IncomeForm;
