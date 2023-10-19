// export const mockDataIncomes = [
//   {
//     id: 'Salary',
//     value: 1000.5,
//     amount: 1000.5,
//     source: 'Canva',
//     category: 'Salary',
//     date: '2023-10-13',
//     status: 'received',
//     location: 'Sydney',
//     currency: 'AUD',
//     comment: 'Bonus included for exceptional performance.',
//   },
//   {
//     id: 'Bonuses',
//     value: 1002.5,
//     amount: 1002.5,
//     source: 'Canva',
//     category: 'Salary',
//     date: '2023-10-20',
//     status: 'received',
//     location: 'Sydney',
//     currency: 'AUD',
//     comment: 'Bonus included for exceptional performance.',
//   },
//   {
//     id: 'Commission',
//     value: 100000.5,
//     amount: 100000.5,
//     source: 'Bonus',
//     category: 'Invest',
//     date: '2023-10-08',
//     status: 'received',
//     location: 'Sydney',
//     currency: 'AUD',
//     comment: 'Bonus included for exceptional performance.',
//   },
// ];

import { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

const useIncomesData = () => {
  const [incomesData, setIncomesData] = useState([]);

  useEffect(() => {
    apiInstance
      .get(`/incomes/${localStorage.getItem('id')}`)
      .then(response => {
        const updatedData = response.data.map(item => ({
          ...item,
          id: item.category,
          value: item.amount,
        }));
        setIncomesData(updatedData);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  return incomesData;
};

export default useIncomesData;
