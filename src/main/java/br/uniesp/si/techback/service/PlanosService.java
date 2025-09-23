package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.exception.ValidacaoException;
import br.uniesp.si.techback.model.Planos;
import br.uniesp.si.techback.repository.PlanosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PlanosService {

    private final PlanosRepository planosRepository;

    @Autowired
    public PlanosService(PlanosRepository planosRepository) {
        this.planosRepository = planosRepository;
    }

    @Transactional(readOnly = true)
    public List<Planos> listarTodos() {
        return planosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Planos> listarAtivos() {
        return planosRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Page<Planos> listarAtivosPaginados(Pageable pageable) {
        return planosRepository.findByAtivoTrueOrderByPreco(pageable);
    }

    @Transactional(readOnly = true)
    public Planos buscarPorId(Long id) {
        return planosRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Plano não encontrado com o ID: " + id));
    }

    @Transactional
    public Planos salvar(Planos plano) {
        validarPlano(plano);
        if (plano.getId() != null && planosRepository.existsByNomeIgnoreCaseAndIdNot(plano.getNome(), plano.getId())) {
            throw new ValidacaoException("Já existe um plano com este nome.");
        } else if (plano.getId() == null && planosRepository.existsByNomeIgnoreCase(plano.getNome())) {
            throw new ValidacaoException("Já existe um plano com este nome.");
        }
        return planosRepository.save(plano);
    }

    @Transactional
    public Planos atualizar(Long id, Planos planoAtualizado) {
        return planosRepository.findById(id).map(plano -> {
            plano.setNome(planoAtualizado.getNome());
            plano.setDescricao(planoAtualizado.getDescricao());
            plano.setPreco(planoAtualizado.getPreco());
            plano.setDuracaoDias(planoAtualizado.getDuracaoDias());
            plano.setLimiteDispositivos(planoAtualizado.getLimiteDispositivos());
            plano.setAtivo(planoAtualizado.getAtivo());

            return planosRepository.save(plano);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com o ID: " + id));
    }

    @Transactional
    public void excluir(Long id) {
        if (!planosRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Plano não encontrado com o ID: " + id);
        }
        planosRepository.deleteById(id);
    }


    @Transactional
    public void ativarDesativar(Long id, boolean ativo) {
        Planos plano = buscarPorId(id);
        plano.setAtivo(ativo);
        planosRepository.save(plano);
    }

    @Transactional(readOnly = true)
    public List<Planos> buscarPorFaixaPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        if (precoMinimo == null || precoMaximo == null || precoMinimo.compareTo(precoMaximo) > 0) {
            throw new ValidacaoException("Intervalo de preço inválido");
        }
        return planosRepository.findByPrecoBetween(precoMinimo, precoMaximo);
    }

    @Transactional(readOnly = true)
    public Optional<Planos> encontrarPlanoMaisCaro() {
        return planosRepository.encontrarPlanoMaisCaro();
    }

    @Transactional(readOnly = true)
    public Optional<Planos> encontrarPlanoMaisBarato() {
        return planosRepository.encontrarPlanoMaisBarato();
    }

    @Transactional(readOnly = true)
    public List<Planos> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        return planosRepository.buscarPorNomeOuDescricao(termo);
    }

    @Transactional(readOnly = true)
    public long contarPlanosAtivos() {
        return planosRepository.countByAtivoTrue();
    }

    private void validarPlano(Planos plano) {
        if (plano.getNome() == null || plano.getNome().trim().isEmpty()) {
            throw new ValidacaoException("O nome do plano é obrigatório");
        }
        if (plano.getPreco() == null || plano.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoException("O preço do plano deve ser maior que zero");
        }
        if (plano.getDuracaoDias() == null || plano.getDuracaoDias() <= 0) {
            throw new ValidacaoException("A duração do plano deve ser maior que zero");
        }
        if (plano.getLimiteDispositivos() == null || plano.getLimiteDispositivos() <= 0) {
            throw new ValidacaoException("O limite de dispositivos deve ser maior que zero");
        }
    }
}
