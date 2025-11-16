import React, { useState, useEffect } from 'react';
import { UsuarioService } from '../../services/UsuarioService';

interface Usuario {
    id: number;
    nome: string;
    email: string;
}

const ListaUsuarios: React.FC = () => {
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState('');

    useEffect(() => {
        const carregarUsuarios = async () => {
            try {
                const dados = await UsuarioService.listarUsuarios();
                setUsuarios(dados);
            } catch (erro: unknown) {
                const mensagemErro = erro instanceof Error ? erro.message : 'Erro ao carregar usuários';
                setErro(mensagemErro);
                console.error('Erro:', erro);
            } finally {
                setCarregando(false);
            }
        };

        carregarUsuarios();
    }, []);

    if (carregando) return <div>Carregando...</div>;
    if (erro) return <div style={{ color: 'red' }}>{erro}</div>;
    if (usuarios.length === 0) return <div>Nenhum usuário encontrado</div>;

    return (
        <div style={{ padding: '20px' }}>
            <h2>Lista de Usuários</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                <thead>
                    <tr style={{ backgroundColor: '#f5f5f5' }}>
                        <th style={{ padding: '10px', textAlign: 'left', borderBottom: '1px solid #ddd' }}>ID</th>
                        <th style={{ padding: '10px', textAlign: 'left', borderBottom: '1px solid #ddd' }}>Nome</th>
                        <th style={{ padding: '10px', textAlign: 'left', borderBottom: '1px solid #ddd' }}>Email</th>
                    </tr>
                </thead>
                <tbody>
                    {usuarios.map((usuario) => (
                        <tr key={`usuario-${usuario.id}`} style={{ borderBottom: '1px solid #eee' }}>
                            <td style={{ padding: '10px' }}>{usuario.id}</td>
                            <td style={{ padding: '10px' }}>{usuario.nome}</td>
                            <td style={{ padding: '10px' }}>{usuario.email}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ListaUsuarios;