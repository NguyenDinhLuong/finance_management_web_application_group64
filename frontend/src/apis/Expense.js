import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addExpense = async ({
  amount,
  category,
  currency,
  date,
  location,
  status,
  paymentMethod,
  user_id,
}) => {
  const response = await apiInstance
    .post('/expenses/addExpense', {
      amount,
      category,
      currency,
      date,
      location,
      status,
      paymentMethod,
      user_id,
    })
    .then(response => {
      toast.success('Expense created successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Expense created unsuccessfully!');
      return false;
    });
  return response;
};

export const addRecurringExpense = async ({
  amount,
  category,
  currency,
  frequency,
  location,
  startDate,
  endDate,
  user_id,
}) => {
  if (startDate > endDate) {
    toast.error('End date must be greater than start date');
    return false;
  }
  const response = await apiInstance
    .post('/recurringExpenses/addRecurringExpense', {
      amount,
      category,
      currency,
      frequency,
      location,
      startDate,
      endDate,
      user_id,
    })
    .then(response => {
      toast.success('Recurring expense created successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Recurring expense created unsuccessfully!');
      return false;
    });
  return response;
};
