import { Box, IconButton } from '@mui/material';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { tokens } from '../../theme';
import Header from '../../components/Header';
import { useTheme } from '@mui/material';
import React, { useState, useEffect, useRef } from 'react';
import apiInstance from '../../apis/Axios';
import { useCurrency } from '../../provider/CurrencyProvider';
import DeleteIcon from '@mui/icons-material/Delete';
import { toast } from 'react-toastify';
import EditIcon from '@mui/icons-material/Edit';
import { useNavigate } from 'react-router-dom';

const Investments = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  const [investmentsData, setInvestmentsData] = useState([]);
  const { currency, rate } = useCurrency();
  const navigate = useNavigate();
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

  columns.push({
    field: 'deleteAction',
    headerName: 'Actions',
    flex: 1,
    sortable: false,
    filterable: false,
    disableClickEventBubbling: true,
    renderCell: params => {
      const handleDelete = () => {
        apiInstance
          .delete(`/investment/deleteInvestment/${params.id}`)
          .then(response => {
            console.log(response.data);
            setInvestmentsData(prevData =>
              prevData.filter(user => user.id !== params.id)
            );
            toast.success('Delete investment successfully!');
          })
          .catch(error => {
            toast.error('Delete investment unsuccessfully!');
          });
      };
      return (
        <Box display="flex" justifyContent="center">
          <IconButton onClick={handleDelete} color="error">
            <DeleteIcon />
          </IconButton>
        </Box>
      );
    },
  });
  columns.push({
    field: 'actions',
    headerName: 'Edit Actions',
    flex: 1,
    sortable: false,
    filterable: false,
    disableClickEventBubbling: true,
    renderCell: params => {
      const handleEdit = () => {
        localStorage.setItem('editInvestmentId', params.id);
        navigate('/editInvestment');
      };

      return (
        <Box display="flex" justifyContent="center" gap="10px">
          <IconButton onClick={handleEdit} color="primary">
            <EditIcon />
          </IconButton>
        </Box>
      );
    },
  });

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
