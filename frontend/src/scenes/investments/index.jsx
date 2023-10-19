import { Box } from '@mui/material';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { tokens } from '../../theme';
import Header from '../../components/Header';
import { useTheme } from '@mui/material';
import React, { useState, useEffect, useRef } from 'react';
import apiInstance from '../../apis/Axios';
import { useCurrency } from '../../provider/CurrencyProvider';

const Investments = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const [investmentsData, setInvestmentsData] = useState([]);
  const { currency, rate } = useCurrency();
  const prevRateRef = useRef();
  const prevCurrencyRef = useRef();

  useEffect(() => {
    apiInstance
      .get(`/investment/${localStorage.getItem('id')}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
        setInvestmentsData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  useEffect(() => {
    if (rate !== prevRateRef.current || currency !== prevCurrencyRef.current) {
      const convertedData = investmentsData.map(investmentsData => ({
        ...investmentsData,
        amount: investmentsData.amount * rate,
        currency: currency,
      }));
      setInvestmentsData(convertedData);
    }

    // Update the refs with the current values
    prevRateRef.current = rate;
    prevCurrencyRef.current = currency;
  }, [currency, rate, investmentsData]);

  const columns = [
    { field: 'id', headerName: 'ID', flex: 0.5 },
    {
      field: 'amount',
      headerName: 'Amount',
    },
    {
      field: 'currency',
      headerName: 'Currency',
      flex: 1,
    },
    {
      field: 'category',
      headerName: 'Category',
      flex: 1,
      cellClassName: 'name-column--cell',
    },
    {
      field: 'date',
      headerName: 'Date',
      type: 'date',
      flex: 1,
      headerAlign: 'left',
      align: 'left',
    },
    {
      field: 'duration',
      headerName: 'Duration',
      flex: 1,
    },
    {
      field: 'risk',
      headerName: 'Risk',
      flex: 1,
    },
    {
      field: 'liquidity',
      headerName: 'Liquidity',
      flex: 1,
    },
  ];

  return (
    <Box m="20px">
      <Header title="Investments" subtitle="List of your investments" />
      <Box
        m="40px 0 0 0"
        height="75vh"
        sx={{
          '& .MuiDataGrid-root': {
            border: 'none',
          },
          '& .MuiDataGrid-cell': {
            borderBottom: 'none',
          },
          '& .name-column--cell': {
            color: colors.greenAccent[300],
          },
          '& .MuiDataGrid-columnHeaders': {
            backgroundColor: colors.blueAccent[700],
            borderBottom: 'none',
          },
          '& .MuiDataGrid-virtualScroller': {
            backgroundColor: colors.primary[400],
          },
          '& .MuiDataGrid-footerContainer': {
            borderTop: 'none',
            backgroundColor: colors.blueAccent[700],
          },
          '& .MuiCheckbox-root': {
            color: `${colors.greenAccent[200]} !important`,
          },
          '& .MuiDataGrid-toolbarContainer .MuiButton-text': {
            color: `${colors.grey[100]} !important`,
          },
        }}
      >
        <DataGrid
          rows={investmentsData}
          columns={columns}
          components={{ Toolbar: GridToolbar }}
        />
      </Box>
    </Box>
  );
};

export default Investments;
