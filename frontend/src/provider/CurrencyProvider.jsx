import React, { createContext, useContext, useState } from 'react';

const CurrencyContext = createContext();

export const useCurrency = () => {
  return useContext(CurrencyContext);
};

export const CurrencyProvider = ({ children }) => {
  const [currency, setCurrency] = useState(
    localStorage.getItem('currentCurrency') || 'AUD'
  );
  const [rate, setRate] = useState(localStorage.getItem('rate') || 1);

  return (
    <CurrencyContext.Provider value={{ currency, setCurrency, rate, setRate }}>
      {children}
    </CurrencyContext.Provider>
  );
};
