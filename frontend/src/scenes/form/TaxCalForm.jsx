import React, { useState } from 'react';
import {
    Box,
    Button,
    TextField,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
} from '@mui/material';

function TaxCalForm() {
    const [income, setIncome] = useState('');
    const [year, setYear] = useState('');
    const [residential, setResidential] = useState('');
    const [responseMessage, setResponseMessage] = useState('');

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        if (name === 'income') {
            setIncome(value);
        } else if (name === 'year') {
            setYear(value);
        } else if (name === 'residential') {
            setResidential(value);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = {
            income: parseFloat(income),
            year: year,
            residential: residential,
        };

        try {
            const response = await fetch('http://localhost:8080/api/tax/enterIncome', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            const responseData = await response.json();

            if (response.ok) {
                setResponseMessage(responseData.message);
            } else {
                setResponseMessage(`Error: ${responseData.error}`);
            }
        } catch (error) {
            console.error('Error:', error);
            setResponseMessage('An error occurred while processing your request.');
        }
    };

    return (
        <Box m="20px">
        <div>
            <form onSubmit={handleSubmit}>
                <Box
                    display="grid"
                    gap="30px"
                    gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                >
                    <TextField
                        fullWidth
                        variant="filled"
                        type="number"
                        label="Amount"
                        placeholder="$"
                        onChange={handleInputChange}
                        value={income}
                        name="income"
                        sx={{ gridColumn: 'span 2' }}
                        inputProps={{ step: '0.01' }}
                    />
                <TextField
                    fullWidth
                    variant="filled"
                    type="number"
                    label="Income Year"
                    onChange={handleInputChange}
                    value={year}
                    name="year"
                    sx={{ gridColumn: 'span 2' }}
                    inputProps={{ step: '1' }}
                />

                <FormControl
                    fullWidth
                    variant="filled"
                    sx={{ gridColumn: 'span 2' }}
                >
                    <InputLabel id="dropdown-label">Status</InputLabel>
                    <Select
                        labelId="dropdown-label"
                        value={residential}
                        onChange={handleInputChange}
                        name="residential"
                        required
                    >
                        <MenuItem value="">Select</MenuItem>
                        <MenuItem value="Resident">Resident</MenuItem>
                        <MenuItem value="Non-Resident">Non-Resident</MenuItem>
                    </Select>
                </FormControl>
                </Box>

                <Box display="flex" justifyContent="end" mt="20px">
                    <Button type="submit" color="secondary" variant="contained">
                        Calculate
                    </Button>
                </Box>
            </form>
            <div>

            </div>
            <Box
                display="grid"
                gap="30px">
                {responseMessage && <p>{responseMessage}</p>}
            </Box>
        </div>
        </Box>
    );
}

export default TaxCalForm;
