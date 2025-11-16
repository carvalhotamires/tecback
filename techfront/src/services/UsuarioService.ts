import api from './api';

interface Usuario {
    id?: number;
    nome: string;
    email: string;
    password: string;

    // adicione outros campos conforme necessário
}

export const UsuarioService = {
    async listarUsuarios() {
        const response = await api.get('/usuarios');
        return response.data;
    },
    async criarUsuario(usuario: Omit<Usuario, 'id'>) {
        const response = await api.post('/usuarios', usuario);
        return response.data;
    },

    async atualizarUsuario(id: number, usuario: Partial<Usuario>) {
        const response = await api.put(`/usuarios/${id}` , usuario);
        return response.data;
    },
    async deletarUsuario(id: number) {
        await api.delete(`/usuarios/${id}`);

    }
} ;