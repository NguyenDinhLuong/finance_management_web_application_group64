import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './scenes/Login';
import SignUp from './scenes/SignUp';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import Home from './scenes/Home';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<PrivateRoute />}>
          <Route path="/" element={<Home />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
