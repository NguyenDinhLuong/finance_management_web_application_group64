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
import { editRecurringExpense } from '../../apis/Expense';
import { useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import apiInstance from '../../apis/Axios';

const EditRecurringExpenseForm = () => {
  //const userId = localStorage.getItem('id');

  const [initialValues, setInitialValues] = useState({
    amount: '',
    category: '',
    frequency: '',
    location: '',
    currency: '',
    startDate: '',
    endDate: '',
  });

  const navigate = useNavigate();

  const dateRegExp = '\\d{4}-\\d{2}-\\d{2}';

  const checkoutSchema = yup.object().shape({
    amount: yup.string().required('required'),
    category: yup.string().required('required'),
    frequency: yup.string().required('required'),
    location: yup.string().required('required'),
    startDate: yup
      .string()
      .matches(dateRegExp, 'Date format is not valid')
      .required('required'),
    endDate: yup
      .string()
      .matches(dateRegExp, 'Date format is not valid')
      .required('required'),
  });

  useEffect(() => {
    apiInstance
      .get(
        `/recurringExpenses/specificRecurringExpense/${localStorage.getItem(
          'editRecurringExpenseId'
        )}`
      )
      .then(response => {
        setInitialValues({
          amount: response.data.amount,
          category: response.data.category,
          frequency: response.data.frequency,
          location: response.data.location,
          currency: response.data.currency,
          startDate: response.data.startDate,
          endDate: response.data.endDate,
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
      startDate: values.startDate,
      location: values.location,
      frequency: values.frequency,
      endDate: values.endDate,
      id: localStorage.getItem('editRecurringExpenseId'),
    };

    const isSuccess = editRecurringExpense({
      ...updatedData,
    });

    if (isSuccess) {
      navigate('/recurringExpenses');
    }
  };

  return (
    <Box m="20px">
      <Header
        title="EDIT RECURRING EXPENSE"
        subtitle="Edit your recurring expense"
      />

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
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="Location"
                placeholder="location"
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
                label="Start Date"
                placeholder="yyyy-MM-dd"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.startDate}
                name="startDate"
                error={!!touched.startDate && !!errors.startDate}
                helperText={touched.startDate && errors.startDate}
                sx={{ gridColumn: 'span 2' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="text"
                label="End Date"
                placeholder="yyyy-MM-dd"
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
                Edit Recurring Expense
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

export default EditRecurringExpenseForm;
