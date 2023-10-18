import { Outlet, Navigate } from 'react-router-dom';
import { getIsAuth } from '../../utils/token';

const PrivateRouteAdmin = () => {
  const isAuth = getIsAuth();

  if (!isAuth) {
    return <Navigate to="/login" replace />;
  }
  if (isAuth && localStorage.getItem('role') === 'ROLE_ADMIN') {
    return <Outlet />;
  }
};

export default PrivateRouteAdmin;
