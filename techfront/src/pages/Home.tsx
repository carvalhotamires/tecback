import React from 'react';
import { Container, Typography, Button, Box, CssBaseline } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';

const Home: React.FC = () => {
  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
        p: 3,
        boxSizing: 'border-box'
      }}
    >
      <CssBaseline />
      <Box 
        sx={{
          maxWidth: '800px',
          mx: 'auto',
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}
      >
        <Typography variant="h2" component="h1" gutterBottom>
          Bem-vindo à Plataforma de Streaming 
        </Typography>
        
        <Typography variant="h5" color="text.secondary" paragraph sx={{ mb: 4, maxWidth: '600px' }}>
          Assista aos melhores filmes e séries quando e onde quiser.
        </Typography>
        
        <Box sx={{ display: 'flex', gap: 2, mt: 4, flexWrap: 'wrap', justifyContent: 'center' }}>
          <Button 
            component={RouterLink} 
            to="/planos" 
            variant="contained" 
            size="large"
            color="primary"
          >
            Ver Planos
          </Button>
          
          <Button 
            component={RouterLink} 
            to="/login" 
            variant="outlined" 
            size="large"
          >
            Cadastre-se
          </Button>
        </Box>

        <Box sx={{ mt: 8, width: '100%' }}>
          <Typography variant="h5" gutterBottom>
            Por que assinar?
          </Typography>
          <Box sx={{ 
            display: 'flex', 
            justifyContent: 'center', 
            gap: 4, 
            mt: 4, 
            flexWrap: 'wrap' 
          }}>
            {[
              { title: 'Conteúdo exclusivo', description: 'Acesso a séries e filmes que você não encontra em nenhum outro lugar.' },
              { title: 'Assista em qualquer lugar', description: 'No celular, tablet, computador ou TV.' },
              { title: 'Cancele quando quiser', description: 'Sem compromisso, cancele online quando quiser.' }
            ].map((feature, index) => (
              <Box key={index} sx={{ 
                maxWidth: 300, 
                p: 3, 
                border: '1px solid', 
                borderColor: 'divider', 
                borderRadius: 2 
              }}>
                <Typography variant="h6" gutterBottom>{feature.title}</Typography>
                <Typography variant="body2" color="text.secondary">
                  {feature.description}
                </Typography>
              </Box>
            ))}
          </Box>
        </Box>
      </Box>
    </Box>
  );
};

export default Home;
