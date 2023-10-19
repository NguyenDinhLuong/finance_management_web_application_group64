// export const mockDataExpenses = [
//   {
//     id: 1,
//     amount: 1000.5,
//     category: 'Eating ',
//     date: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     status: 'pending',
//     paymentMethod: 'Card',
//   },
//   {
//     id: 2,
//     amount: 10000.5,
//     category: 'Food',
//     date: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     status: 'pending',
//     paymentMethod: 'Cash',
//   },
//   {
//     id: 3,
//     amount: 100000,
//     category: 'Salary',
//     date: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     status: 'pending',
//     paymentMethod: 'Card',
//   },
// ];

// export const mockDataRecurringExpenses = [
//   {
//     id: 1,
//     amount: 1000.5,
//     category: 'Eating ',
//     startDate: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     frequency: 'MONTHLY',
//     endDate: '2023-10-14',
//   },
//   {
//     id: 2,
//     amount: 1000.5,
//     category: 'Eating ',
//     startDate: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     frequency: 'MONTHLY',
//     endDate: '2023-10-14',
//   },
//   {
//     id: 3,
//     amount: 1000.5,
//     category: 'Eating ',
//     startDate: '2023-10-13',
//     location: 'Parramatta',
//     currency: 'AUD',
//     frequency: 'MONTHLY',
//     endDate: '2023-10-14',
//   },
// ];
import { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

const useExpensesData = () => {
  const [expenseData, setExpensesData] = useState([]);

  useEffect(() => {
    apiInstance
      .get(`/expenses/${localStorage.getItem('id')}`)
      .then(response => {
        setExpensesData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  return expenseData;
};

export default useExpensesData;
