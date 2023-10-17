import React, { useState } from 'react';

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
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>
                        Income:
                        <input
                            type="number"
                            step="0.01"
                            name="income"
                            value={income}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Income Year:
                        <input
                            type="number"
                            name="Year"
                            value={year}
                            onChange={handleInputChange}
                            required
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Residential Status:
                        <select
                            name="residential"
                            value={residential}
                            onChange={handleInputChange}
                            required
                        >
                            <option value="">Select</option>
                            <option value="resident">Resident</option>
                            <option value="non-resident">Non-Resident</option>
                        </select>
                    </label>
                </div>
                <button type="submit">Submit</button>
            </form>
            <div>
                {responseMessage && <p>{responseMessage}</p>}
            </div>
        </div>
    );
}

export default TaxCalForm;
