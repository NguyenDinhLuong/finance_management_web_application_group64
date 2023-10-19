import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addInvestment = async ({
  amount,
  category,
  currency,
  date,
  duration,
  risk,
  liquidity,
  user_id,
}) => {
  const response = await apiInstance
    .post('/investment/addInvestment', {
      amount,
      category,
      currency,
      date,
      duration,
      risk,
      liquidity,
      user_id,
    })
    .then(response => {
      toast.success('Investment created successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Investment created unsuccessfully!');
      return false;
    });
  return response;
};
