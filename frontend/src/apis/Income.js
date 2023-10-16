import { toast } from 'react-toastify';
import apiInstance from './Axios';

export const addIncome = async ({
  amount,
  source,
  category,
  date,
  status,
  location,
  currency,
  user_id,
}) => {
  const response = await apiInstance
    .post('/incomes/addIncome', {
      amount,
      source,
      category,
      date,
      status,
      location,
      currency,
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
