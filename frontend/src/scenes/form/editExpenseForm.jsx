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
import { editExpense } from '../../apis/Expense';
import { useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import apiInstance from '../../apis/Axios';

const EditExpenseForm = () => {
  //const userId = localStorage.getItem('id');

  const [initialValues, setInitialValues] = useState({
    amount: '',
    category: '',
    date: '',
    location: '',
    currency: '',
    status: '',
    paymentMethod: '',
  });
  const navigate = useNavigate();

  const dateRegExp = '\\d{4}-\\d{2}-\\d{2}';

  const checkoutSchema = yup.object().shape({
    amount: yup.string().required('required'),
    category: yup.string().required('required'),
    date: yup
      .string()
      .matches(dateRegExp, 'Date format is not valid')
      .required('required'),
    location: yup.string().required('required'),
    status: yup.string().required('required'),
    paymentMethod: yup.string().required('required'),
  });

  useEffect(() => {
    apiInstance
      .get(`/expenses/specificExpense/${localStorage.getItem('editExpenseId')}`)
      .then(response => {
        setInitialValues({
          amount: response.data.amount,
          category: response.data.category,
          date: response.data.date,
          location: response.data.location,
          status: response.data.status,
          paymentMethod: response.data.paymentMethod,
        });
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  const handleFormSubmit = values => {
    const updatedData = {
      amount: values.amount,
      category: values.category,
      currency: localStorage.getItem('currentCurrency'),
      date: values.date,
      location: values.location,
      status: values.status,
      paymentMethod: values.paymentMethod,
      id: localStorage.getItem('editExpenseId'),
    };

    const isSuccess = editExpense({
      ...updatedData,
    });

    if (isSuccess) {
      navigate('/expenses');
    }
  };

  return (
    <Box m="20px">
      <Header title="EDIT EXPENSE" subtitle="Edit your expense" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={checkoutSchema}
        enableReinitialize={true}
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
                  <MenuItem value="Utilities">Utilities</MenuItem>
                  <MenuItem value="Groceries">Groceries</MenuItem>
                  <MenuItem value="Dining">Dining</MenuItem>
                  <MenuItem value="Transportation">Transportation</MenuItem>
                  <MenuItem value="Entertainment">Entertainment</MenuItem>
                  <MenuItem value="Shopping">Shopping</MenuItem>
                  <MenuItem value="Others">Others</MenuItem>
                </Select>
              </FormControl>
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
                  <MenuItem value="Completed">Completed</MenuItem>
                </Select>
              </FormControl>
              <FormControl
                fullWidth
                variant="filled"
                sx={{ gridColumn: 'span 2' }}
              >
                <InputLabel id="dropdown-label">Payment Method</InputLabel>
                <Select
                  labelId="dropdown-label"
                  value={values.dropdown}
                  onChange={handleChange}
                  name="paymentMethod"
                >
                  <MenuItem value="Cash">Cash</MenuItem>
                  <MenuItem value="Debit Card">Debit Card</MenuItem>
                  <MenuItem value="Credit Card">Credit Card</MenuItem>
                  <MenuItem value="Bank Transfers">Bank Transfers</MenuItem>
                </Select>
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Edit Expense
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

export default EditExpenseForm;
