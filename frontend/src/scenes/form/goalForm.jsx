import { Box, Button, TextField } from '@mui/material';
import { Formik } from 'formik';
import * as yup from 'yup';
import Header from '../../components/Header';
import { addGoal } from '../../apis/Goal';

const GoalForm = () => {
  const userId = localStorage.getItem('id');

  const handleFormSubmit = values => {
    const updatedData = {
      targetIncome: values.targetIncome,
      maximumExpense: values.maximumExpense,
      maximumInvestment: values.maximumInvestment,
      currency: localStorage.getItem('currentCurrency'),
      user_id: userId,
    };

    const isSuccess = addGoal({
      ...updatedData,
    });

    if (isSuccess) {
    }
  };

  return (
    <Box m="20px">
      <Header title="CREATE GOAL" subtitle="Create a goal" />

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
                Create New Goal
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

const checkoutSchema = yup.object().shape({
  targetIncome: yup.string().required('required'),
  maximumExpense: yup.string().required('required'),
  maximumInvestment: yup.string().required('required'),
});
const initialValues = {
  targetIncome: '',
  maximumExpense: '',
  maximumInvestment: '',
};

export default GoalForm;
