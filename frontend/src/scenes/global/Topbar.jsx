import { Box, IconButton, useTheme } from '@mui/material';
import { useContext } from 'react';
import { ColorModeContext, tokens } from '../../theme';
import InputBase from '@mui/material/InputBase';
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import NotificationsOutlinedIcon from '@mui/icons-material/NotificationsOutlined';
import SearchIcon from '@mui/icons-material/Search';
import { useNavigate } from 'react-router-dom';
import { cleanToken, getRefreshToken } from '../../utils/token';
import { toast } from 'react-toastify';
import apiInstance from '../../apis/Axios';
import LogoutIcon from '@mui/icons-material/Logout';
import CurrencyExchangeIcon from '@mui/icons-material/CurrencyExchange';
import React, { useState, useEffect, useRef } from 'react';
import Menu from '@mui/material/Menu';
import { MenuItem } from '@mui/material';
import { Check } from '@mui/icons-material';
import { useCurrency } from '../../provider/CurrencyProvider';
import Popover from '@mui/material/Popover';
import Typography from '@mui/material/Typography';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import Badge from '@mui/material/Badge';
import PriorityHighIcon from '@mui/icons-material/PriorityHigh';

const currencies = [
  { code: 'AUD', flag: '🇦🇺' },
  { code: 'USD', flag: '🇺🇸' },
  { code: 'EUR', flag: '🇪🇺' },
  { code: 'JPY', flag: '🇯🇵' },
  { code: 'GBP', flag: '🇬🇧' },
  { code: 'CAD', flag: '🇨🇦' },
  { code: 'CHF', flag: '🇨🇭' },
  { code: 'CNY', flag: '🇨🇳' },
  { code: 'SEK', flag: '🇸🇪' },
  { code: 'NZD', flag: '🇳🇿' },
];

const convertFrequencyToDays = frequency => {
  switch (frequency) {
    case 'DAILY':
      return 1;
    case 'WEEKLY':
      return 7;
    case 'MONTHLY':
      return 30; // Assuming an average month has 30 days
    case 'QUARTERLY':
      return 3 * 30; // 3 months
    case 'YEARLY':
      return 365; // Ignoring leap years for simplicity
    default:
      throw new Error(`Unknown frequency: ${frequency}`);
  }
};

const Topbar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const colorMode = useContext(ColorModeContext);
  const navigate = useNavigate();
  const refreshToken = getRefreshToken();
  const role = localStorage.getItem('role');
  const [notificationAnchorEl, setNotificationAnchorEl] = useState(null);
  const [anchorEl, setAnchorEl] = useState(null);
  const { currency, setCurrency, setRate } = useCurrency();
  const notificationIconRef = useRef(null);
  const notificationTimeoutRef = useRef(null);
  const [selectedCurrency, setSelectedCurrency] = useState(
    localStorage.getItem('currentCurrency')
  ); // Set default selected currency to AUD
  const [recurringExpensesData, setRecurringExpensesData] = useState([]);
  const [message, setMessage] = useState('');

  const inputCurrency = selectedCurrency;
  const handleClick = event => {
    setAnchorEl(event.currentTarget);
  };

  const handleNotificationClick = event => {
    setNotificationAnchorEl(event.currentTarget);
  };

  useEffect(() => {
    setMessage('');
    apiInstance
      .get(`/recurringExpenses/${localStorage.getItem('id')}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        setRecurringExpensesData(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the expense data', error);
      });

    let checkMessage = '';

    recurringExpensesData.forEach(expense => {
      const startDate = new Date(expense.startDate);
      const nextPaymentDate = new Date(startDate);
      const currentDate = new Date();

      if (currentDate > startDate) {
        while (nextPaymentDate <= currentDate) {
          nextPaymentDate.setDate(
            nextPaymentDate.getDate() +
              convertFrequencyToDays(expense.frequency)
          );
        }
        // Get the difference in days
        const differenceInDays = Math.round(
          (nextPaymentDate - currentDate) / (1000 * 60 * 60 * 24)
        );
        if (differenceInDays <= 3) {
          checkMessage =
            checkMessage +
            `You have a recurring expense for ${expense.category} with ${expense.amount} ${expense.currency} due in ${differenceInDays} days.\n`;
          setMessage(checkMessage);
        }
      }

      if (currentDate < startDate) {
        const differenceInDays = Math.round(
          (startDate - currentDate) / (1000 * 60 * 60 * 24)
        );

        if (differenceInDays <= 3) {
          checkMessage =
            checkMessage +
            `You have a recurring expense for ${expense.category} with ${expense.amount} ${expense.currency} due in ${differenceInDays} days.\n`;
          setMessage(checkMessage);
        }
      }
    });
  }, [recurringExpensesData]);

  const handleNotificationClose = () => {
    // Manually close the Popover and clear the timeout
    setNotificationAnchorEl(null);
    clearTimeout(notificationTimeoutRef.current);
  };

  const handleMenuItemClick = currencyCode => {
    setSelectedCurrency(currencyCode);
    localStorage.setItem('currentCurrency', currencyCode);
    setAnchorEl(null);
    console.log(currencyCode);
    apiInstance
      .get(`/currency/rate/${inputCurrency}/${currencyCode}`)
      .then(response => {
        localStorage.setItem('rate', response.data);
        setRate(response.data); // Use setRate from context
        setCurrency(currencyCode); // Use setCurrency from context
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/goals/updateCurrencyExchange/${inputCurrency}/${currencyCode}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/incomes/updateCurrencyExchange/${inputCurrency}/${currencyCode}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(
        `/investment/updateCurrencyExchange/${inputCurrency}/${currencyCode}`
      ) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/expenses/updateCurrencyExchange/${inputCurrency}/${currencyCode}`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(
        `/recurringExpenses/updateCurrencyExchange/${inputCurrency}/${currencyCode}`
      ) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  };

  const handleLogout = async () => {
    const response = await apiInstance
      .post('/auth/logout', { refreshToken })
      .then(response => {
        toast.success('Successfully logout!');
        cleanToken();
        localStorage.setItem('rate', 1);
        localStorage.setItem('currentCurrency', 'AUD');
        navigate('/login');
        return true;
      })
      .catch(error => {
        console.log('logout error: ', error.response);
        toast.error('Something wrong with your account.');
        return false;
      });
    console.log(currency);
    apiInstance
      .put(`/incomes/updateCurrencyExchange/${currency}/AUD`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/investment/updateCurrencyExchange/${currency}/AUD`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/expenses/updateCurrencyExchange/${currency}/AUD`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    apiInstance
      .put(`/recurringExpenses/updateCurrencyExchange/${currency}/AUD`) // this assumes the endpoint for fetching incomes is `/incomes`
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
    return response;
  };

  return (
    <Box display="flex" justifyContent="space-between" p={2}>
      {/* SEARCH BAR */}
      <Box
        display="flex"
        backgroundColor={colors.primary[400]}
        borderRadius="3px"
      >
        <InputBase sx={{ ml: 2, flex: 1 }} placeholder="Search" />
        <IconButton type="button" sx={{ p: 1 }}>
          <SearchIcon />
        </IconButton>
      </Box>

      {/* ICONS */}
      <Box display="flex">
        <IconButton onClick={colorMode.toggleColorMode}>
          {theme.palette.mode === 'dark' ? (
            <DarkModeOutlinedIcon />
          ) : (
            <LightModeOutlinedIcon />
          )}
        </IconButton>
        {role === 'ROLE_USER' && (
          <>
            <IconButton
              ref={notificationIconRef}
              onClick={handleNotificationClick}
            >
              {message !== '' ? (
                <Badge badgeContent={1} color="primary">
                  <NotificationsActiveIcon />
                </Badge>
              ) : (
                <NotificationsOutlinedIcon />
              )}
            </IconButton>
            <Popover
              open={Boolean(notificationAnchorEl)}
              anchorEl={notificationAnchorEl}
              onClose={handleNotificationClose}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'center',
              }}
              transformOrigin={{
                vertical: 'top',
                horizontal: 'center',
              }}
            >
              <Box p={2} display="flex" alignItems="center">
                {' '}
                {/* Add display="flex" and alignItems="center" */}
                <PriorityHighIcon sx={{ mr: 1 }} />{' '}
                {/* Add some margin to the right of the icon */}
                <Typography variant="body1">{message}</Typography>
              </Box>
            </Popover>
          </>
        )}
        {role === 'ROLE_USER' && (
          <>
            <IconButton onClick={handleClick}>
              <CurrencyExchangeIcon />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={() => setAnchorEl(null)}
            >
              {currencies.map(currency => (
                <MenuItem
                  key={currency.code}
                  onClick={() => handleMenuItemClick(currency.code)}
                >
                  {currency.flag} {currency.code}
                  {selectedCurrency === currency.code && (
                    <Check style={{ color: 'green', marginLeft: 'auto' }} />
                  )}
                </MenuItem>
              ))}
            </Menu>
          </>
        )}
        <IconButton onClick={handleLogout}>
          <LogoutIcon />
        </IconButton>
      </Box>
    </Box>
  );
};

export default Topbar;
