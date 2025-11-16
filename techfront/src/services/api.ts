import axios from 'axios';

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

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const planosService = {
  listarTodos: async (): Promise<Plano[]> => {
    const response = await api.get<Plano[]>('/planos');
    return response.data;
  },
  
  buscarPorId: async (id: number): Promise<Plano> => {
    const response = await api.get<Plano>(`/planos/${id}`);
    return response.data;
  },
  
  criar: async (plano: Omit<Plano, 'id' | 'ativo' | 'dataCriacao' | 'dataAtualizacao'>): Promise<Plano> => {
    const response = await api.post<Plano>('/planos', plano);
    return response.data;
  },
  
  atualizar: async (id: number, plano: Partial<Plano>): Promise<Plano> => {
    const response = await api.put<Plano>(`/planos/${id}`, plano);
    return response.data;
  },
  
  excluir: async (id: number): Promise<void> => {
    await api.delete(`/planos/${id}`);
  },
  
  atualizarStatus: async (id: number, ativo: boolean): Promise<void> => {
    await api.patch(`/planos/${id}/status?ativo=${ativo}`);
  }
};

export default api;
