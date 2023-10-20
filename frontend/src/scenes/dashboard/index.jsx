import { Box, Typography, useTheme } from '@mui/material';
import { tokens } from '../../theme';
import Header from '../../components/Header';
import LineChart from '../../components/LineChart';
import BarChart from '../../components/BarChart';
import StatBox from '../../components/StatBox';
import ProgressCircle from '../../components/ProgressCircle';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import CurrencyBitcoinIcon from '@mui/icons-material/CurrencyBitcoin';
import apiInstance from '../../apis/Axios';
import React, { useState, useEffect, useRef } from 'react';
import { useCurrency } from '../../provider/CurrencyProvider';
import PieChart from '../../components/PieChart';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';

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
  const [targetIncome, setTargetIncome] = useState(0);
  const [maximumExpense, setMaximumExpense] = useState(0);
  const [maximumInvestment, setMaximumInvestment] = useState(0);
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

        const goalResponse = await apiInstance.get(
          `/goals/${localStorage.getItem('id')}`
        );
        if (
          goalResponse.data.targetIncome != null &&
          goalResponse.data.maximumExpense != null &&
          goalResponse.data.maximumInvestment != null
        ) {
          setTargetIncome(goalResponse.data.targetIncome);
          setMaximumExpense(goalResponse.data.maximumExpense);
          setMaximumInvestment(goalResponse.data.maximumInvestment);
        }
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
    const updatedTargetIncome = targetIncome * rate;
    const updatedMaximumExpense = maximumExpense * rate;
    const updatedMaximumInvestment = maximumInvestment * rate;
    // Check if rate or currency has changed from their previous values
    if (rate !== prevRateRef.current || currency !== prevCurrencyRef.current) {
      setTotalIncomeAmount(updatedTotalIncomeAmount);
      setTotalInvestmentAmount(updatedTotalInvestmentAmount);
      setTotalNormalExpenseAmount(updatedTotalNormalExpenseAmount);
      setTotalRecurringExpenseAmount(updatedTotalRecurringExpenseAmount);
      setTotalTaxAmount(updatedTotalTaxAmount);
      setTargetIncome(updatedTargetIncome);
      setMaximumExpense(updatedMaximumExpense);
      setMaximumInvestment(updatedMaximumInvestment);
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
    targetIncome,
    maximumExpense,
    maximumInvestment,
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
        gap="10px"
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
            progress={totalIncomeAmount / targetIncome}
            increase={
              Math.round((totalIncomeAmount / targetIncome) * 100) + '%'
            }
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
            title={totalNormalExpenseAmount + ' ' + currency}
            subtitle="Total Normal Expense"
            progress={totalNormalExpenseAmount / maximumExpense}
            increase={
              Math.round((totalNormalExpenseAmount / maximumExpense) * 100) +
              '%'
            }
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
            title={totalRecurringExpenseAmount + ' ' + currency}
            subtitle="Total Recurring Expense"
            progress={totalRecurringExpenseAmount / maximumExpense}
            increase={
              Math.round((totalRecurringExpenseAmount / maximumExpense) * 100) +
              '%'
            }
            icon={
              <AccountBalanceIcon
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
            progress={totalInvestmentAmount / maximumInvestment}
            increase={
              Math.round((totalInvestmentAmount / maximumInvestment) * 100) +
              '%'
            }
            icon={
              <CurrencyBitcoinIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          />
          {/* <StatBox
            title={totalTaxAmount + ' ' + currency}
            subtitle="Total Taxes"
            progress="1"
            icon={
              <CalculateIcon
                sx={{ color: colors.greenAccent[600], fontSize: '26px' }}
              />
            }
          /> */}
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
                Expenses
              </Typography>
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
              Taxes
            </Typography>
          </Box>
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
              sx={{ mt: '15px', whiteSpace: 'pre-line' }}
            >
              {'Total Taxes: ' + totalTaxAmount + ' ' + currency}
            </Typography>
          </Box>
        </Box>

        {/* ROW 3 */}
        <Box
          gridColumn="span 4"
          gridRow="span 2"
          backgroundColor={colors.primary[400]}
          p="30px"
        >
          <Typography variant="h5" fontWeight="600">
            Goals
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
              sx={{ mt: '15px', whiteSpace: 'pre-line' }}
            >
              {'Target Income: ' +
                targetIncome +
                ' ' +
                currency +
                '\n' +
                'Maximum Expense: ' +
                maximumExpense +
                currency +
                '\n' +
                'Maximum Investment: ' +
                maximumInvestment +
                currency}
            </Typography>
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
            Investment
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
            Incomes
          </Typography>
          <Box height="250px" mt="-20px">
            <PieChart />
          </Box>
        </Box>
      </Box>
    </Box>
  );
};

export default Dashboard;
