import { Box, IconButton, useTheme } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { tokens } from '../../theme';
import Header from '../../components/Header';
import DeleteIcon from '@mui/icons-material/Delete';
import React, { useState, useEffect } from 'react';
import apiInstance from '../../apis/Axios';
import { toast } from 'react-toastify';

const AdminDashboard = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [usersData, setUsersData] = useState([]);

  useEffect(() => {
    apiInstance
      .get('/auth/listOfUsers')
      .then(response => {
        console.log(response.data);
        setUsersData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);
  const columns = [
    { field: 'id', headerName: 'ID' },
    {
      field: 'username',
      headerName: 'Username',
      flex: 1,
      cellClassName: 'name-column--cell',
    },
    {
      field: 'firstName',
      headerName: 'First Name',
      headerAlign: 'left',
      align: 'left',
    },
    {
      field: 'lastName',
      headerName: 'Last Name',
      flex: 1,
    },
    {
      field: 'email',
      headerName: 'Email',
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
          .delete(`/auth/deleteUser/${params.id}`)
          .then(response => {
            console.log(response.data);
            setUsersData(prevData =>
              prevData.filter(user => user.id !== params.id)
            );
            toast.success('Delete user successfully!');
          })
          .catch(error => {
            toast.error('Delete user unsuccessfully!');
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

  return (
    <Box m="20px">
      <Box display="flex" justifyContent="space-between" alignItems="center">
        <Header title="ADMIN DASHBOARD" subtitle="Welcome to admin dashboard" />
      </Box>
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
        }}
      >
        <DataGrid rows={usersData} columns={columns} />
      </Box>
    </Box>
  );
};

export default AdminDashboard;
