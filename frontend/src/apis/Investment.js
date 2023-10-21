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

export const updateInvestment = async ({
  amount,
  category,
  currency,
  date,
  duration,
  risk,
  liquidity,
  id,
}) => {
  const response = await apiInstance
    .put(`/investment/update/${id}`, {
      amount,
      category,
      currency,
      date,
      duration,
      risk,
      liquidity,
    })
    .then(response => {
      toast.success('Investment updated successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Investment updated unsuccessfully!');
      return false;
    });
  return response;
};
