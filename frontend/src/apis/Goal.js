import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addGoal = async ({
  targetIncome,
  maximumExpense,
  maximumRecurringExpense,
  maximumInvestment,
  currency,
  user_id,
}) => {
  const response = await apiInstance
    .post('/goals/addGoal', {
      targetIncome,
      maximumExpense,
      maximumRecurringExpense,
      maximumInvestment,
      currency,
      user_id,
    })
    .then(response => {
      toast.success('Goal created successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Goal created unsuccessfully!');
      console.log(error);
      return false;
    });
  return response;
};

export const updateGoal = async ({
  targetIncome,
  maximumExpense,
  maximumRecurringExpense,
  maximumInvestment,
  currency,
  id,
}) => {
  const response = await apiInstance
    .put(`/goals/update/${id}`, {
      targetIncome,
      maximumExpense,
      maximumRecurringExpense,
      maximumInvestment,
      currency,
    })
    .then(response => {
      toast.success('Goal updated successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Goal updated unsuccessfully!');
      return false;
    });
  return response;
};
