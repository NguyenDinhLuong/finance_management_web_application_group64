import { Box, Button, TextField } from '@mui/material';
import { Formik } from 'formik';
import * as yup from 'yup';
import Header from '../../components/Header';
import React, { useState, useEffect } from 'react';
import apiInstance from '../../apis/Axios';
import { updateGoal } from '../../apis/Goal';

const GoalForm = () => {
  //const userId = localStorage.getItem('id');

  const [initialValues, setInitialValues] = useState({
    targetIncome: 0,
    maximumExpense: 0,
    maximumInvestment: 0,
  });

  const checkoutSchema = yup.object().shape({
    targetIncome: yup.number().required('required'),
    maximumExpense: yup.number().required('required'),
    maximumInvestment: yup.number().required('required'),
  });

  useEffect(() => {
    apiInstance
      .get(`/goals/${localStorage.getItem('id')}`)
      .then(response => {
        setInitialValues({
          targetIncome: response.data.targetIncome,
          maximumExpense: response.data.maximumExpense,
          maximumInvestment: response.data.maximumInvestment,
        });
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  const handleFormSubmit = values => {
    const updatedData = {
      targetIncome: values.targetIncome,
      maximumExpense: values.maximumExpense,
      maximumInvestment: values.maximumInvestment,
      currency: localStorage.getItem('currentCurrency'),
      id: 1,
    };

    const isSuccess = updateGoal({
      ...updatedData,
    });

    if (isSuccess) {
    }
  };

  return (
    <Box m="20px">
      <Header title="YOUR GOAL" subtitle="Update the goal" />

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
                label="Target Income"
                placeholder="$"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.targetIncome}
                name="targetIncome"
                error={!!touched.targetIncome && !!errors.targetIncome}
                helperText={touched.targetIncome && errors.targetIncome}
                sx={{ gridColumn: 'span 2' }}
                inputProps={{ step: '0.01' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="number"
                label="Maximum Expense"
                placeholder="$"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.maximumExpense}
                name="maximumExpense"
                error={!!touched.maximumExpense && !!errors.maximumExpense}
                helperText={touched.maximumExpense && errors.maximumExpense}
                sx={{ gridColumn: 'span 2' }}
                inputProps={{ step: '0.01' }}
              />
              <TextField
                fullWidth
                variant="filled"
                type="number"
                label="Maximum Investment"
                placeholder="$"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.maximumInvestment}
                name="maximumInvestment"
                error={
                  !!touched.maximumInvestment && !!errors.maximumInvestment
                }
                helperText={
                  touched.maximumInvestment && errors.maximumInvestment
                }
                sx={{ gridColumn: 'span 2' }}
                inputProps={{ step: '0.01' }}
              />
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Update Goal
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

export default GoalForm;
