import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Topbar from './global/Topbar';
import Sidebar from './global/Sidebar';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { ColorModeContext, useMode } from '../theme';
import { CurrencyProvider } from '../provider/CurrencyProvider';

function Home() {
  const [theme, colorMode] = useMode();
  const [isSidebar, setIsSidebar] = useState(true);

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <div className="app">
          <Sidebar isSidebar={isSidebar} />
          <main className="content">
            <CurrencyProvider>
              <Topbar setIsSidebar={setIsSidebar} />
              <Outlet />
            </CurrencyProvider>
          </main>
        </div>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}

export default Home;
