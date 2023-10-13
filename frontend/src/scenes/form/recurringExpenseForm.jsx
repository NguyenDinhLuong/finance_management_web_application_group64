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

const RecurringExpenseForm = () => {
  const handleFormSubmit = values => {
    console.log(values);
  };

  return (
    <Box m="20px">
      <Header
        title="CREATE RECURRING EXPENSE"
        subtitle="Create a New Recurring Expense"
      />

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
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Description"
                placeholder="Description"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.description}
                name="description"
                error={!!touched.description && !!errors.description}
                helperText={touched.description && errors.description}
                sx={{ gridColumn: 'span 2' }}
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
                  <MenuItem value="Rent">Rent</MenuItem>
                  <MenuItem value="Mortgage">Mortgage</MenuItem>
                  <MenuItem value="Loan">Loan</MenuItem>
                  <MenuItem value="Insurance">Insurance</MenuItem>
                  <MenuItem value="Tuition Fee">Tuition Fee</MenuItem>
                  <MenuItem value="Subscription Services">
                    Subscription Services
                  </MenuItem>
                  <MenuItem value="Others">Others</MenuItem>
                </Select>
              </FormControl>
              {/* <TextField
                    fullWidth
                    variant="filled"
                    type="text"
                    label="Type Of Investment"
                    placeholder="Type Of Investment"
                    onBlur={handleBlur}
                    onChange={handleChange}
                    value={values.typeOfInvestment}
                    name="typeOfIncome"
                    error={!!touched.typeOfInvestment && !!errors.typeOfInvestment}
                    helperText={touched.typeOfInvestment && errors.typeOfInvestment}
                    sx={{ gridColumn: 'span 2' }}
                  /> */}
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Start Date"
                placeholder="dd-MM-yyyy"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.startDate}
                name="startDate"
                error={!!touched.startDate && !!errors.startDate}
                helperText={touched.startDate && errors.startDate}
                sx={{ gridColumn: 'span 2' }}
              />
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Frequency</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="frequency"
                >
                  <MenuItem value="DAILY">DAILY</MenuItem>
                  <MenuItem value="WEEKLY">WEEKLY</MenuItem>
                  <MenuItem value="MONTHLY">MONTHLY</MenuItem>
                  <MenuItem value="QUARTERLY">QUARTERLY</MenuItem>
                  <MenuItem value="YEARLY">YEARLY</MenuItem>
                </Select>
              </FormControl>
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="End Date"
                placeholder="dd-MM-yyyy"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.endDate}
                name="endDate"
                error={!!touched.endDate && !!errors.endDate}
                helperText={touched.endDate && errors.endDate}
                sx={{ gridColumn: 'span 2' }}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Recurring Expense
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

const dateRegExp = '\\d{2}-\\d{2}-\\d{4}';

const checkoutSchema = yup.object().shape({
  amount: yup.string().required('required'),
  typeOfInvestment: yup.string().required('required'),
  date: yup
    .string()
    .matches(dateRegExp, 'Date format is not valid')
    .required('required'),
});
const initialValues = {
  amount: '',
  typeOfInvestment: '',
  date: '',
};

export default RecurringExpenseForm;
