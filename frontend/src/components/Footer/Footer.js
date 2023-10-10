import Box from '@mui/joy/Box';
import Typography from '@mui/joy/Typography';
export default function Footer() {
  return (
    <Box component="footer" sx={{ py: 3, zIndex: 999, position: 'relative' }}>
      <Typography level="body-xs" textAlign="center">
        © Financial Application {new Date().getFullYear()}
      </Typography>
    </Box>
  );
}
