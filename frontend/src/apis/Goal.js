import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addGoal = async ({
  targetIncome,
  maximumExpense,
  maximumInvestment,
  currency,
  user_id,
}) => {
  const response = await apiInstance
    .post('/goals/addGoal', {
      targetIncome,
      maximumExpense,
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
