import { useState } from 'react';
import { ProSidebar, Menu, MenuItem } from 'react-pro-sidebar';
import { Box, IconButton, Typography, useTheme } from '@mui/material';
import { Link } from 'react-router-dom';
import 'react-pro-sidebar/dist/css/styles.css';
import { tokens } from '../../theme';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import ReceiptOutlinedIcon from '@mui/icons-material/ReceiptOutlined';
import PersonOutlinedIcon from '@mui/icons-material/PersonOutlined';
import BarChartOutlinedIcon from '@mui/icons-material/BarChartOutlined';
import PieChartOutlineOutlinedIcon from '@mui/icons-material/PieChartOutlineOutlined';
import TimelineOutlinedIcon from '@mui/icons-material/TimelineOutlined';
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import PaymentIcon from '@mui/icons-material/Payment';
import PaidIcon from '@mui/icons-material/Paid';
import CurrencyBitcoinIcon from '@mui/icons-material/CurrencyBitcoin';
import CalculateIcon from '@mui/icons-material/Calculate';
import FormatListBulletedIcon from '@mui/icons-material/FormatListBulleted';
import FormatListNumberedIcon from '@mui/icons-material/FormatListNumbered';
import DriveFolderUploadIcon from '@mui/icons-material/DriveFolderUpload';

const Item = ({ title, to, icon, selected, setSelected }) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  return (
    <MenuItem
      active={selected === title}
      style={{
        color: colors.grey[100],
      }}
      onClick={() => setSelected(title)}
      icon={icon}
    >
      <Typography>{title}</Typography>
      <Link to={to} />
    </MenuItem>
  );
};

const Sidebar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [selected, setSelected] = useState('Dashboard');
  const role = localStorage.getItem('role');

  return (
    <Box
      sx={{
        '& .pro-sidebar-inner': {
          background: `${colors.primary[400]} !important`,
        },
        '& .pro-icon-wrapper': {
          backgroundColor: 'transparent !important',
        },
        '& .pro-inner-item': {
          padding: '5px 35px 5px 20px !important',
        },
        '& .pro-inner-item:hover': {
          color: '#868dfb !important',
        },
        '& .pro-menu-item.active': {
          color: '#6870fa !important',
        },
      }}
    >
      <ProSidebar collapsed={isCollapsed}>
        <Menu iconShape="square">
          {/* LOGO AND MENU ICON */}
          <MenuItem
            onClick={() => setIsCollapsed(!isCollapsed)}
            icon={isCollapsed ? <MenuOutlinedIcon /> : undefined}
            style={{
              margin: '10px 0 20px 0',
              color: colors.grey[100],
            }}
          >
            {!isCollapsed && (
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                ml="15px"
              >
                {role === 'ROLE_USER' && (
                  <Typography variant="h3" color={colors.grey[100]}>
                    USER
                  </Typography>
                )}
                {role === 'ROLE_ADMIN' && (
                  <Typography variant="h3" color={colors.grey[100]}>
                    ADMIN
                  </Typography>
                )}
                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                  <MenuOutlinedIcon />
                </IconButton>
              </Box>
            )}
          </MenuItem>
          {!isCollapsed && role === 'ROLE_USER' && (
            <Box mb="25px">
              <Box textAlign="center">
                <Typography
                  variant="h2"
                  color={colors.grey[100]}
                  fontWeight="bold"
                  sx={{ m: '10px 0 0 0' }}
                >
                  {localStorage.getItem('firstName')}
                </Typography>
              </Box>
            </Box>
          )}

          <Box paddingLeft={isCollapsed ? undefined : '10%'}>
            {role === 'ROLE_USER' && (
              <Item
                title="Dashboard"
                to="/"
                icon={<HomeOutlinedIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            )}
            {role === 'ROLE_USER' && (
              <Typography
                variant="h6"
                color={colors.grey[300]}
                sx={{ m: '15px 0 5px 20px' }}
              >
                Personal
              </Typography>
            )}
            {role === 'ROLE_USER' && (
              <>
                <Item
                  title="Profile"
                  to="/profile"
                  icon={<PersonOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Data
                </Typography>
              </>
            )}
            {role === 'ROLE_USER' && (
              <>
                <Item
                  title="Incomes"
                  to="/incomeForm"
                  icon={<PaymentIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Expenses"
                  to="/expenseForm"
                  icon={<PaidIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Recurring Expenses"
                  to="/recurringExpenseForm"
                  icon={<PaidIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Investment"
                  to="/investmentForm"
                  icon={<CurrencyBitcoinIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Tax Calculation"
                  to="/"
                  icon={<CalculateIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Incomes Information"
                  to="/incomes"
                  icon={<FormatListBulletedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Investments Information"
                  to="/investments"
                  icon={<FormatListNumberedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Expense Information"
                  to="/expenses"
                  icon={<ReceiptOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Recurring Expense Info"
                  to="/recurringExpenses"
                  icon={<ReceiptOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Upload receipts"
                  to="/uploadReceipts"
                  icon={<DriveFolderUploadIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Typography
                  variant="h6"
                  color={colors.grey[300]}
                  sx={{ m: '15px 0 5px 20px' }}
                >
                  Charts
                </Typography>
                <Item
                  title="Bar Chart"
                  to="/bar"
                  icon={<BarChartOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Pie Chart"
                  to="/pie"
                  icon={<PieChartOutlineOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
                <Item
                  title="Line Chart"
                  to="/line"
                  icon={<TimelineOutlinedIcon />}
                  selected={selected}
                  setSelected={setSelected}
                />
              </>
            )}
          </Box>
        </Menu>
      </ProSidebar>
    </Box>
  );
};

export default Sidebar;
