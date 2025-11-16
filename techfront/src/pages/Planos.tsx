import React, { useEffect, useState } from 'react';
import { Container, Typography, Grid, Card, CardContent, CardActions, Button, Box, Divider, Chip } from '@mui/material';
import { CheckCircle as CheckCircleIcon } from '@mui/icons-material';
import { planosService } from '../services/api';

interface Plano {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  temAnuncios: boolean;
  resolucao: string;
  limiteDispositivos: number;
  dispositivosDownload: number;
  observacoes: string;
  ativo: boolean;
}


const Planos: React.FC = () => {
  const [planos, setPlanos] = useState<Plano[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchPlanos = async () => {
      try {
        // Tenta buscar os planos da API
        const planosData = await planosService.listarTodos();
        setPlanos(planosData);
      } catch (err) {
        console.warn('Não foi possível conectar à API, usando dados de exemplo:', err);
        
        // Dados de exemplo para desenvolvimento
        const dadosExemplo = [
          {
            id: 1,
            nome: "PADRÃO COM ANÚNCIOS",
            descricao: "Plano básico com anúncios",
            preco: 20.90,
            temAnuncios: true,
            resolucao: "Full HD (1080p)",
            limiteDispositivos: 2,
            dispositivosDownload: 2,
            observacoes: "É o plano mais barato; contém anúncios durante os conteúdos.",
            ativo: true
          },
          {
            id: 2,
            nome: "PADRÃO (SEM ANÚNCIOS)",
            descricao: "Plano padrão sem anúncios",
            preco: 44.90,
            temAnuncios: false,
            resolucao: "Full HD (1080p)",
            limiteDispositivos: 2,
            dispositivosDownload: 2,
            observacoes: "Permite adicionar 1 assinante extra que mora fora da residência, com custo adicional.",
            ativo: true
          },
          {
            id: 3,
            nome: "PREMIUM",
            descricao: "Plano premium com os melhores recursos",
            preco: 59.90,
            temAnuncios: false,
            resolucao: "4K + HDR (Ultra HD)",
            limiteDispositivos: 4,
            dispositivosDownload: 6,
            observacoes: "Permite adicionar até 2 assinantes extras fora da casa, com custo extra.",
            ativo: true
          }
        ];
        
        setPlanos(dadosExemplo);
        setError('Modo offline: Dados de exemplo sendo exibidos.');
      } finally {
        setLoading(false);
      }
    };

    fetchPlanos();
  }, []);

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Typography>Carregando planos...</Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Typography color="error">{error}</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Escolha seu plano
      </Typography>
      <Typography variant="subtitle1" color="text.secondary" paragraph>
        Selecione o plano que melhor atende às suas necessidades.
      </Typography>
      
      <Grid container spacing={4} sx={{ mt: 2 }}>
        {planos.map((plano) => (
          <Grid item key={plano.id} xs={12} md={4}>
            <Card 
              elevation={3} 
              sx={{ 
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                border: '1px solid',
                borderColor: 'primary.main',
                '&:hover': {
                  boxShadow: 6,
                },
              }}
            >
              <CardContent sx={{ flexGrow: 1 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                  <Typography variant="h5" component="h2">
                    {plano.nome}
                  </Typography>
                  {plano.temAnuncios && (
                    <Chip 
                      label="Com anúncios" 
                      color="secondary" 
                      size="small" 
                    />
                  )}
                </Box>
                
                <Box sx={{ mb: 3 }}>
                  <Typography variant="h4" color="primary">
                    R$ {plano.preco.toFixed(2).replace('.', ',')}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    por mês
                  </Typography>
                </Box>

                <Divider sx={{ my: 2 }} />

                <Box sx={{ mb: 2 }}>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                    <CheckCircleIcon color="primary" fontSize="small" sx={{ mr: 1 }} />
                    <Typography>{plano.resolucao}</Typography>
                  </Box>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                    <CheckCircleIcon color="primary" fontSize="small" sx={{ mr: 1 }} />
                    <Typography>Até {plano.limiteDispositivos} telas simultâneas</Typography>
                  </Box>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                    <CheckCircleIcon color="primary" fontSize="small" sx={{ mr: 1 }} />
                    <Typography>{plano.dispositivosDownload} dispositivos para download</Typography>
                  </Box>
                  {plano.observacoes && (
                    <Box sx={{ mt: 2, p: 1, bgcolor: 'action.hover', borderRadius: 1 }}>
                      <Typography variant="body2">
                        {plano.observacoes}
                      </Typography>
                    </Box>
                  )}
                </Box>
              </CardContent>
              
              <CardActions sx={{ p: 2, pt: 0 }}>
                <Button 
                  fullWidth 
                  variant="contained" 
                  size="large"
                  sx={{ py: 1.5 }}
                >
                  Assinar Agora
                </Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default Planos;
