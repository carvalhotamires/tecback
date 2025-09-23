package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Enderecos;
import br.uniesp.si.techback.repository.EnderecosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecosService {

    private final EnderecosRepository enderecosRepository;

    @Autowired
    public EnderecosService(EnderecosRepository enderecosRepository) {
        this.enderecosRepository = enderecosRepository;
    }

    public Enderecos salvarEndereco(Enderecos endereco) {
        return enderecosRepository.save(endereco);
    }

    public List<Enderecos> listarEnderecos() {
        return enderecosRepository.findAll();
    }

    public Optional<Enderecos> buscarEnderecoPorId(Long id) {
        return enderecosRepository.findById(id);
    }

    public void deletarEndereco(Long id) {
        enderecosRepository.deleteById(id);
    }
}
