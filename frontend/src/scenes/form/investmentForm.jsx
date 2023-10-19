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
import { addInvestment } from '../../apis/Investment';

const InvestmentForm = () => {
  const userId = localStorage.getItem('id');

  const handleFormSubmit = values => {
    const updatedData = {
      amount: values.amount,
      category: values.category,
      currency: localStorage.getItem('currentCurrency'),
      date: values.date,
      duration: values.duration,
      risk: values.risk,
      liquidity: values.liquidity,
      user_id: userId,
    };

    const isSuccess = addInvestment({
      ...updatedData,
    });

    if (isSuccess) {
    }
  };

  return (
    <Box m="20px">
      <Header title="CREATE INVESTMENT" subtitle="Create a New Investment" />

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
                  <MenuItem value="Stocks">Stocks</MenuItem>
                  <MenuItem value="Bonds">Bonds</MenuItem>
                  <MenuItem value="Cryptocurrencies">Cryptocurrencies</MenuItem>
                  <MenuItem value="Forex">Forex</MenuItem>
                  <MenuItem value="Hedge Funds">Hedge Funds</MenuItem>
                  <MenuItem value="Others">Others</MenuItem>
                </Select>
              </FormControl>
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Date"
                placeholder="dd-MM-yyyy"
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
                <InputLabel id="dropdown-label">Duration</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="duration"
                >
                  <MenuItem value="< 1 year">less than 1 year</MenuItem>
                  <MenuItem value="1-5 years">1-5 years</MenuItem>
                  <MenuItem value="> 5 years">more than 5 years</MenuItem>
                </Select>
              </FormControl>
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Risk</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="risk"
                >
                  <MenuItem value="low">Low</MenuItem>
                  <MenuItem value="moderate">Moderate</MenuItem>
                  <MenuItem value="high">High</MenuItem>
                </Select>
              </FormControl>
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Liquidity</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="liquidity"
                >
                  <MenuItem value="highly liquid">Highly Liquid</MenuItem>
                  <MenuItem value="moderately liquid">
                    Moderately Liquid
                  </MenuItem>
                  <MenuItem value="Less Liquid">Less Liquid</MenuItem>
                  <MenuItem value="Illiquid">Illiquid</MenuItem>
                </Select>
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Investment
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
  category: yup.string().required('required'),
  date: yup
    .string()
    .matches(dateRegExp, 'Date format is not valid')
    .required('required'),
  duration: yup.string().required('required'),
  risk: yup.string().required('required'),
  liquidity: yup.string().required('required'),
});
const initialValues = {
  amount: '',
  category: '',
  date: '',
  duration: '',
  currency: '',
  risk: '',
  liquidity: '',
};

export default InvestmentForm;
