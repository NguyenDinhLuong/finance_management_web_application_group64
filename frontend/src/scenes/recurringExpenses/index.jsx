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

const RecurringExpenses = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [recurringExpensesData, setRecurringExpensesData] = useState([]);
  const { currency, rate } = useCurrency();
  const navigate = useNavigate();
  const prevRateRef = useRef();
  const prevCurrencyRef = useRef();

  useEffect(() => {
    apiInstance
      .get(`/recurringExpenses/${localStorage.getItem('id')}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
        setRecurringExpensesData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the expense data', error);
      });
  }, []);

  useEffect(() => {
    if (rate !== prevRateRef.current || currency !== prevCurrencyRef.current) {
      const convertedData = recurringExpensesData.map(
        recurringExpensesData => ({
          ...recurringExpensesData,
          amount: recurringExpensesData.amount * rate,
          currency: currency,
        })
      );
      setRecurringExpensesData(convertedData);
    }

    // Update the refs with the current values
    prevRateRef.current = rate;
    prevCurrencyRef.current = currency;
  }, [currency, rate, recurringExpensesData]);

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
      field: 'frequency',
      headerName: 'Frequency',
      flex: 1,
    },
    {
      field: 'location',
      headerName: 'Location',
      flex: 1,
    },
    {
      field: 'startDate',
      headerName: 'Start Date',
      type: 'date',
      flex: 1,
      headerAlign: 'left',
      align: 'left',
    },
    {
      field: 'endDate',
      headerName: 'End Date',
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
          .delete(`/recurringExpenses/deleteRecurringExpense/${params.id}`)
          .then(response => {
            console.log(response.data);
            setRecurringExpensesData(prevData =>
              prevData.filter(user => user.id !== params.id)
            );
            toast.success('Delete recurring expense successfully!');
          })
          .catch(error => {
            toast.error('Delete recurring expense unsuccessfully!');
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
        localStorage.setItem('editRecurringExpenseId', params.id);
        navigate('/editRecurringExpense');
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
      <Header
        title="Recurring Expenses"
        subtitle="List of your recurring expenses"
      />
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
          rows={recurringExpensesData}
          columns={columns}
          components={{ Toolbar: GridToolbar }}
        />
      </Box>
    </Box>
  );
};

export default RecurringExpenses;
