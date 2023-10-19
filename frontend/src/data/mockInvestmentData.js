import { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

const useInvestmentsData = () => {
  const [investmentData, setInvestmentsData] = useState([]);

  useEffect(() => {
    apiInstance
      .get(`/investment/${localStorage.getItem('id')}`)
      .then(response => {
        setInvestmentsData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  return investmentData;
};

export default useInvestmentsData;
