import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addIncome = async ({
  amount,
  source,
  category,
  currency,
  date,
  status,
  location,
  user_id,
}) => {
  const response = await apiInstance
    .post('/incomes/addIncome', {
      amount,
      source,
      category,
      currency,
      date,
      status,
      location,
      user_id,
    })
    .then(response => {
      toast.success('Income created successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Income created unsuccessfully!');
      console.log(error);
      return false;
    });
  return response;
};

export const updateIncome = async ({
  amount,
  source,
  category,
  currency,
  date,
  status,
  location,
  id,
}) => {
  const response = await apiInstance
    .put(`/incomes/update/${id}`, {
      amount,
      source,
      category,
      currency,
      date,
      status,
      location,
    })
    .then(response => {
      toast.success('Income updated successfully!');
      return true;
    })
    .catch(error => {
      toast.error('Income updated unsuccessfully!');
      return false;
    });
  return response;
};
