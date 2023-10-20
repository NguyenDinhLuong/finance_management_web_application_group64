import { useTheme } from '@mui/material';
import { ResponsiveBar } from '@nivo/bar';
import { tokens } from '../theme';
import React, { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

const BarChart = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [investmentData, setInvestmentsData] = useState([]);

  useEffect(() => {
    apiInstance
      .get(`/investment/${localStorage.getItem('id')}`)
      .then(response => {
        if (Array.isArray(response.data)) {
          setInvestmentsData(response.data);
        } else {
          console.error('Expected array but received:', response.data);
          setInvestmentsData([]);
        }
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  return (
    <ResponsiveBar
      data={investmentData}
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
      }}
      keys={['amount']}
      indexBy="category"
      margin={{ top: 50, right: 130, bottom: 50, left: 60 }}
      padding={0.3}
      valueScale={{ type: 'linear' }}
      indexScale={{ type: 'band', round: true }}
      colors={{ scheme: 'nivo' }}
      borderColor={{
        from: 'color',
        modifiers: [['darker', '1.6']],
      }}
      axisTop={null}
      axisRight={null}
      axisBottom={{
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: 'Investment Category',
        legendPosition: 'middle',
        legendOffset: 32,
      }}
      axisLeft={{
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: 'Investment Amount',
        legendPosition: 'middle',
        legendOffset: -40,
      }}
      enableLabel={false}
      labelSkipWidth={12}
      labelSkipHeight={12}
      labelTextColor={{
        from: 'color',
        modifiers: [['darker', 1.6]],
      }}
      legends={[
        {
          dataFrom: 'keys',
          anchor: 'bottom-right',
          direction: 'column',
          justify: false,
          translateX: 120,
          translateY: 0,
          itemsSpacing: 2,
          itemWidth: 100,
          itemHeight: 20,
          itemDirection: 'left-to-right',
          itemOpacity: 0.85,
          symbolSize: 20,
          effects: [
            {
              on: 'hover',
              style: {
                itemOpacity: 1,
              },
            },
          ],
        },
      ]}
    />
  );
};

export default BarChart;
