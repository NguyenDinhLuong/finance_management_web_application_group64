import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './scenes/Login';
import SignUp from './scenes/SignUp';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import Home from './scenes/Home';
import Dashboard from './scenes/dashboard';
import Team from './scenes/team';
import Invoices from './scenes/invoices';
import Contacts from './scenes/contacts';
import Bar from './scenes/bar';
import Profile from './scenes/form';
import Line from './scenes/line';
import Pie from './scenes/pie';
import IncomeForm from './scenes/form/incomeForm';
import InvestmentForm from './scenes/form/investmentForm';
import ExpenseForm from './scenes/form/expenseForm';
import RecurringExpenseForm from './scenes/form/recurringExpenseForm';
import Incomes from './scenes/incomes';
import Investments from './scenes/investments';
import Expenses from './scenes/expenses';
import RecurringExpenses from './scenes/recurringExpenses';
import AdminDashboard from './scenes/adminDashboard';
import PrivateRouteAdmin from './components/PrivateRouteAdmin/PrivateRouteAdmin';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<PrivateRoute />}>
          <Route path="/" element={<Home />}>
            <Route path="/" element={<Dashboard />} />
            <Route path="/team" element={<Team />} />
            <Route path="/contacts" element={<Contacts />} />
            <Route path="/incomes" element={<Incomes />} />
            <Route path="/investments" element={<Investments />} />
            <Route path="/expenses" element={<Expenses />} />
            <Route path="/recurringExpenses" element={<RecurringExpenses />} />
            <Route path="/invoices" element={<Invoices />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/incomeForm" element={<IncomeForm />} />
            <Route path="/investmentForm" element={<InvestmentForm />} />
            <Route path="/expenseForm" element={<ExpenseForm />} />
            <Route
              path="/recurringExpenseForm"
              element={<RecurringExpenseForm />}
            />
            <Route path="/bar" element={<Bar />} />
            <Route path="/pie" element={<Pie />} />
            <Route path="/line" element={<Line />} />
          </Route>
        </Route>
        <Route path="/" element={<PrivateRouteAdmin />}>
          <Route path="/" element={<Home />}>
            <Route path="/admin" element={<AdminDashboard />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
