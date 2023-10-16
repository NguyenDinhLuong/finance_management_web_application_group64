import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';

function barChart() {
    const [data, setData] = useState({});

    useEffect(() => {
        axios.get('/api/getBarChart')
            .then(response => {
                setData(response.data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []);

    const chartData = {
        labels: Object.keys(data),
        datasets: [
            {
                label: 'Month',
                backgroundColor: 'rgba(75,192,192,0.2)',
                borderColor: 'rgba(75,192,192,1)',
                borderWidth: 1,
                data: Object.values(data),
            },
        ],
    };

    return (
        <div>
            <h2>Expenses in the Past Three Months</h2>
            <Bar data={chartData} />
        </div>
    );
}

export default barChart;