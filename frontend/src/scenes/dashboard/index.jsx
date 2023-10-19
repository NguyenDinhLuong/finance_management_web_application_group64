import { Box, IconButton, Typography, useTheme } from '@mui/material';
import { tokens } from '../../theme';
import { mockTransactions } from '../../data/mockData';
import DownloadOutlinedIcon from '@mui/icons-material/DownloadOutlined';
import Header from '../../components/Header';
import LineChart from '../../components/LineChart';
import BarChart from '../../components/BarChart';
import StatBox from '../../components/StatBox';
import ProgressCircle from '../../components/ProgressCircle';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import CurrencyBitcoinIcon from '@mui/icons-material/CurrencyBitcoin';
import CalculateIcon from '@mui/icons-material/Calculate';
import apiInstance from '../../apis/Axios';
import React, { useState, useEffect, useRef } from 'react';
import { useCurrency } from '../../provider/CurrencyProvider';

const calculateTax = income => {
  if (income <= 18200) {
    return 0;
  } else if (income > 18200 && income <= 45000) {
    return 0.19 * (income - 18200);
  } else if (income > 45000 && income <= 120000) {
    return 5092 + 0.325 * (income - 45000);
  } else if (income > 120000 && income <= 180000) {
    return 29467 + 0.37 * (income - 120000);
  } else {
    // Assuming there's an upper limit, you can adjust this as per your needs.
    // Handle tax calculation for incomes over $180,000
    // For simplicity, let's assume a flat rate above this. Adjust as needed.
    return 29467 + 0.37 * 80000 + 0.45 * (income - 180000);
  }
};

const Dashboard = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [totalIncomeAmount, setTotalIncomeAmount] = useState(0);
  const [totalNormalExpenseAmount, setTotalNormalExpenseAmount] = useState(0);
  const [totalRecurringExpenseAmount, setTotalRecurringExpenseAmount] =
    useState(0);
  const [totalInvestmentAmount, setTotalInvestmentAmount] = useState(0);
  const [totalTaxAmount, setTotalTaxAmount] = useState(0);
  const { currency, rate } = useCurrency();
  const prevRateRef = useRef();
  const prevCurrencyRef = useRef();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const incomeResponse = await apiInstance.get(
          `/incomes/totalAmount/${localStorage.getItem('id')}`
        );
        setTotalIncomeAmount(incomeResponse.data);
        setTotalTaxAmount(calculateTax(incomeResponse.data));

        const expenseResponse = await apiInstance.get(
          `/expenses/totalAmount/${localStorage.getItem('id')}`
        );
        setTotalNormalExpenseAmount(expenseResponse.data);

        const investmentResponse = await apiInstance.get(
          `/investment/totalAmount/${localStorage.getItem('id')}`
        );
        setTotalInvestmentAmount(investmentResponse.data);

        const recurringExpenseResponse = await apiInstance.get(
          `/recurringExpenses/totalAmount/${localStorage.getItem('id')}`
        );
        setTotalRecurringExpenseAmount(recurringExpenseResponse.data);
      } catch (error) {
        console.error('There was an error fetching the data', error);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    const updatedTotalIncomeAmount = totalIncomeAmount * rate;
    const updatedTotalInvestmentAmount = totalInvestmentAmount * rate;
    const updatedTotalNormalExpenseAmount = totalNormalExpenseAmount * rate;
    const updatedTotalRecurringExpenseAmount =
      totalRecurringExpenseAmount * rate;
    const updatedTotalTaxAmount = totalTaxAmount * rate;
    // Check if rate or currency has changed from their previous values
    if (rate !== prevRateRef.current || currency !== prevCurrencyRef.current) {
      setTotalIncomeAmount(updatedTotalIncomeAmount);
      setTotalInvestmentAmount(updatedTotalInvestmentAmount);
      setTotalNormalExpenseAmount(updatedTotalNormalExpenseAmount);
      setTotalRecurringExpenseAmount(updatedTotalRecurringExpenseAmount);
      setTotalTaxAmount(updatedTotalTaxAmount);
    }
    // Update the refs with the current values
    prevRateRef.current = rate;
    prevCurrencyRef.current = currency;
  }, [
    currency,
    rate,
    totalIncomeAmount,
    totalInvestmentAmount,
    totalNormalExpenseAmount,
    totalRecurringExpenseAmount,
    totalTaxAmount,
  ]);

  return (
    <Box m="20px">
      {/* HEADER */}
      <Box display="flex" justifyContent="space-between" alignItems="center">
        <Header title="DASHBOARD" subtitle="Welcome to your dashboard" />
      </Box>

      {/* GRID & CHARTS */}
      <Box
        display="grid"
        gridTemplateColumns="repeat(12, 1fr)"
        gridAutoRows="140px"
        gap="20px"
      >
        {/* ROW 1 */}
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <StatBox
            title={totalIncomeAmount + ' ' + currency}
            subtitle="Total Income"
            progress="0.75"
            increase="+14%"
            icon={
              <AttachMoneyIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          />
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <StatBox
            title={
              totalNormalExpenseAmount +
              totalRecurringExpenseAmount +
              ' ' +
              currency
            }
            subtitle="Total Expense"
            progress="0.50"
            increase="+21%"
            icon={
              <ShoppingCartCheckoutIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          />
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <StatBox
            title={totalInvestmentAmount + ' ' + currency}
            subtitle="Total Investment"
            progress="0.30"
            increase="+5%"
            icon={
              <CurrencyBitcoinIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          />
        </Box>
        <Box
          gridColumn="span 3"
          backgroundColor={colors.primary[400]}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <StatBox
            title={totalTaxAmount + ' ' + currency}
            subtitle="Total Taxes"
            progress="0.80"
            increase="+43%"
            icon={
              <CalculateIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          />
        </Box>

        {/* ROW 2 */}
        <Box
          gridColumn="span 8"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
        >
          <Box
            mt="25px"
            p="0 30px"
            display="flex "
            justifyContent="space-between"
            alignItems="center"
          >
            <Box>
              <Typography
                variant="h5"
                fontWeight="600"
                color={colors.grey[100]}
              >
                Revenue Generated
              </Typography>
              <Typography
                variant="h3"
                fontWeight="bold"
                color={colors.greenAccent[500]}
              >
                $59,342.32
              </Typography>
            </Box>
            <Box>
              <IconButton>
                <DownloadOutlinedIcon
                  sx={{ fontSize: '26px', color: colors.greenAccent[500] }}
                />
              </IconButton>
            </Box>
          </Box>
          <Box height="250px" m="-20px 0 0 0">
            <LineChart isDashboard={true} />
          </Box>
        </Box>
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
          overflow="auto"
        >
          <Box
            display="flex"
            justifyContent="space-between"
            alignItems="center"
            borderBottom={`4px solid ${colors.primary[500]}`}
            colors={colors.grey[100]}
            p="15px"
          >
            <Typography color={colors.grey[100]} variant="h5" fontWeight="600">
              Recent Transactions
            </Typography>
          </Box>
          {mockTransactions.map((transaction, i) => (
            <Box
              key={`${transaction.txId}-${i}`}
              display="flex"
              justifyContent="space-between"
              alignItems="center"
              borderBottom={`4px solid ${colors.primary[500]}`}
              p="15px"
            >
              <Box>
                <Typography
                  color={colors.greenAccent[500]}
                  variant="h5"
                  fontWeight="600"
                >
                  {transaction.txId}
                </Typography>
                <Typography color={colors.grey[100]}>
                  {transaction.user}
                </Typography>
              </Box>
              <Box color={colors.grey[100]}>{transaction.date}</Box>
              <Box
                backgroundColor={colors.greenAccent[500]}
                p="5px 10px"
                borderRadius="4px"
              >
                ${transaction.cost}
              </Box>
            </Box>
          ))}
        </Box>

        {/* ROW 3 */}
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
          p="30px"
        >
          <Typography variant="h5" fontWeight="600">
            Campaign
          </Typography>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            mt="25px"
          >
            <ProgressCircle size="125" />
            <Typography
              variant="h5"
              color={colors.greenAccent[500]}
              sx={{ mt: '15px' }}
            >
              $48,352 revenue generated
            </Typography>
            <Typography>Includes extra misc expenditures and costs</Typography>
          </Box>
        </Box>
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
        >
          <Typography
            variant="h5"
            fontWeight="600"
            sx={{ padding: '30px 30px 0 30px' }}
          >
            Sales Quantity
          </Typography>
          <Box height="250px" mt="-20px">
            <BarChart isDashboard={true} />
          </Box>
        </Box>
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
        >
          <Typography
            variant="h5"
            fontWeight="600"
            sx={{ padding: '30px 30px 0 30px' }}
          >
            Sales Quantity
          </Typography>
          <Box height="250px" mt="-20px">
            <BarChart isDashboard={true} />
          </Box>
        </Box>
      </Box>
    </Box>
  );
};

export default Dashboard;
