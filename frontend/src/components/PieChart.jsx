import { ResponsivePie } from '@nivo/pie';
import { tokens } from '../theme';
import { useTheme } from '@mui/material';
import React, { useState, useEffect } from 'react';
import apiInstance from '../apis/Axios';

const PieChart = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [incomesData, setIncomesData] = useState([]);

  useEffect(() => {
    apiInstance
      .get(`/incomes/${localStorage.getItem('id')}`)
      .then(response => {
        if (Array.isArray(response.data)) {
          const updatedData = response.data.map(item => ({
            ...item,
            id: item.category,
            value: item.amount,
          }));
          setIncomesData(updatedData);
        } else {
          console.error('Expected array but received:', response.data);
          setIncomesData([]);
        }
      })
      .catch(error => {
        console.error('There was an error fetching the incomes data', error);
      });
  }, []);

  return (
    <ResponsivePie
      data={incomesData}
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
      margin={{ top: 40, right: 80, bottom: 80, left: 80 }}
      innerRadius={0.5}
      padAngle={0.7}
      cornerRadius={3}
      activeOuterRadiusOffset={8}
      borderColor={{
        from: 'color',
        modifiers: [['darker', 0.2]],
      }}
      arcLinkLabelsSkipAngle={10}
      arcLinkLabelsTextColor={colors.grey[100]}
      arcLinkLabelsThickness={2}
      arcLinkLabelsColor={{ from: 'color' }}
      enableArcLabels={false}
      arcLabelsRadiusOffset={0.4}
      arcLabelsSkipAngle={7}
      arcLabelsTextColor={{
        from: 'color',
        modifiers: [['darker', 2]],
      }}
      defs={[
        {
          id: 'dots',
          type: 'patternDots',
          background: 'inherit',
          color: 'rgba(255, 255, 255, 0.3)',
          size: 3,
          padding: 1,
          stagger: true,
        },
        {
          id: 'lines',
          type: 'patternLines',
          background: 'inherit',
          color: 'rgba(255, 255, 255, 0.3)',
          rotation: -45,
          lineWidth: 6,
          spacing: 10,
        },
      ]}
    />
  );
};

export default PieChart;
