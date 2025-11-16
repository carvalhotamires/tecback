import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#e50914', // Vermelho da Netflix
      contrastText: '#fff',
    },
    background: {
      default: '#141414',
      paper: '#222',
    },
    text: {
      primary: '#fff',
      secondary: '#e5e5e5',
    },
  },
  typography: {
    fontFamily: [
      'Helvetica Neue',
      'Arial',
      'sans-serif',
    ].join(','),
    h1: {
      fontWeight: 700,
      fontSize: '3rem',
    },
    h2: {
      fontWeight: 600,
      fontSize: '2.5rem',
    },
    h3: {
      fontWeight: 500,
      fontSize: '2rem',
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 4,
          textTransform: 'none',
          fontWeight: 600,
          padding: '8px 24px',
        },
        contained: {
          '&:hover': {
            opacity: 0.9,
          },
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          transition: 'transform 0.3s ease-in-out',
          '&:hover': {
            transform: 'scale(1.02)',
          },
        },
      },
    },
  },
});

export default theme;
