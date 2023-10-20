import { ResponsiveLine } from '@nivo/line';
import { useTheme } from '@mui/material';
import { tokens } from '../theme';
import React, { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

export const mockDataExpenses = [
  {
    id: 1,
    amount: 300,
    category: 'Utilities',
    date: '2023-10-15',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 2,
    amount: 400,
    category: 'Groceries',
    date: '2023-10-16',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Cash',
  },
  {
    id: 3,
    amount: 600,
    category: 'Dining',
    date: '2023-10-17',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 4,
    amount: 20,
    category: 'Transportation',
    date: '2023-10-17',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 5,
    amount: 200,
    category: 'Entertainment',
    date: '2023-10-19',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 6,
    amount: 300,
    category: 'Shopping',
    date: '2023-10-20',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 7,
    amount: 200,
    category: 'Shopping',
    date: '2023-10-21',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 8,
    amount: 300,
    category: 'Shopping',
    date: '2023-10-22',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 9,
    amount: 300,
    category: 'Shopping',
    date: '2023-10-23',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 10,
    amount: 500,
    category: 'Shopping',
    date: '2023-10-24',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
  {
    id: 11,
    amount: 250,
    category: 'Shopping',
    date: '2023-10-25',
    location: 'Parramatta',
    currency: 'AUD',
    status: 'pending',
    paymentMethod: 'Card',
  },
];

// Convert mockDataExpenses to line chart format
export const exampleExpensesData = {
  id: 'Expenses',
  data: mockDataExpenses.map(expense => ({
    x: expense.date,
    y: expense.amount,
  })),
};

// Combined data for the line chart
export const exampleLineChartData = [exampleExpensesData];

const LineChart = ({ isCustomLineColors = false, isDashboard = false }) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [mockExpenseData, setMockExpensesData] = useState([]);

  // Fetch expense data on component mount
  useEffect(() => {
    apiInstance
      .get(`/expenses/${localStorage.getItem('id')}`)
      .then(response => {
        if (Array.isArray(response.data)) {
          setMockExpensesData(response.data);
        } else {
          console.error('Expected array but received:', response.data);
          setMockExpensesData([]);
        }
      })
      .catch(error => {
        console.error('There was an error fetching the expenses data', error);
      });
  }, []);

  const lineChartData =
    mockExpenseData && mockExpenseData.length > 0
      ? [
          {
            id: 'Expenses',
            data: mockExpenseData.map(expense => ({
              x: expense.date,
              y: expense.amount,
            })),
          },
        ]
      : [];

  return (
    <ResponsiveLine
      data={lineChartData}
      theme={{
        axis: {
          domain: {
            line: {
              stroke: colors.grey[100],
            },
          },
          legend: {
            text: {
              fill: colors.grey[100],
            },
          },
          ticks: {
            line: {
              stroke: colors.grey[100],
              strokeWidth: 1,
            },
            text: {
              fill: colors.grey[100],
            },
          },
        },
        legends: {
          text: {
            fill: colors.grey[100],
          },
        },
        tooltip: {
          container: {
            color: colors.primary[500],
          },
        },
      }}
      colors={{ scheme: 'nivo' }}
      margin={{ top: 50, right: 170, bottom: 50, left: 60 }}
      xScale={{
        type: 'time',
        format: '%Y-%m-%d',
        precision: 'day',
      }}
      yScale={{
        type: 'linear',
        min: 0,
        max: 'auto',
        stacked: false,
        reverse: false,
      }}
      yFormat=" >-.2f"
      curve="catmullRom"
      axisTop={null}
      axisRight={null}
      axisBottom={{
        format: '%b %d',
        tickValues: 'every day',
        legend: 'date',
        legendOffset: -10,
        tickPadding: 10,
      }}
      axisLeft={{
        orient: 'left',
        tickValues: 5, // added
        tickSize: 3,
        tickPadding: 5,
        tickRotation: 0,
        legend: isDashboard ? undefined : 'Expense Amount', // added
        legendOffset: -40,
        legendPosition: 'middle',
      }}
      enableGridX={false}
      enableGridY={false}
      pointSize={8}
      pointColor={{ theme: 'background' }}
      pointBorderWidth={2}
      pointBorderColor={{ from: 'serieColor' }}
      pointLabelYOffset={-12}
      useMesh={true}
      legends={[
        {
          anchor: 'bottom-right',
          direction: 'column',
          justify: false,
          translateX: 160,
          translateY: 0,
          itemsSpacing: 0,
          itemDirection: 'left-to-right',
          itemWidth: 80,
          itemHeight: 20,
          itemOpacity: 0.75,
          symbolSize: 12,
          symbolShape: 'circle',
          symbolBorderColor: 'rgba(0, 0, 0, .5)',
          effects: [
            {
              on: 'hover',
              style: {
                itemBackground: 'rgba(0, 0, 0, .03)',
                itemOpacity: 1,
              },
            },
          ],
        },
      ]}
    />
  );
};

export default LineChart;
