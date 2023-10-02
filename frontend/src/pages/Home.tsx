import React from 'react';
import { Box, Typography } from '@mui/joy';
import PageContainer from '../components/PageContainer/PageContainer';
//import { getTokenDecode } from '../utils/token';
//import { DecodedToken } from '../types';

export default function Home() {
  //const decode = getTokenDecode() as DecodedToken;
  //const userFirstname = decode.firstname;

  return (
    <PageContainer>
      <Box
        sx={{
          width:
            'clamp(100vw - var(--Cover-width), (var(--Collapsed-breakpoint) - 100vw) * 999, 100vw)',
          minHeight: 'calc(100vh - 9.25rem)',
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
          margin: '0 auto',
          padding: { xs: '1rem', md: '0' },
        }}
      >
        <Typography
          component="h1"
          sx={{ fontSize: '2rem', fontWeight: 'bold' }}
        >
          Hi, Luong
        </Typography>
        <Box
          sx={{
            display: 'grid',
            gridTemplateColumns: {
              xs: 'repeat(1, minmax(0, 1fr))',
              sm: 'repeat(2, 1fr)',
            },
            gridTemplateRows: '1fr',
            gap: 2,
          }}
        ></Box>
      </Box>
    </PageContainer>
  );
}
