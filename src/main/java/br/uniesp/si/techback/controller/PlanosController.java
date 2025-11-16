package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Planos;
import br.uniesp.si.techback.service.PlanosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador responsável por gerenciar os planos do sistema.
 * Garante que apenas um plano ativo esteja disponível por vez.
 */
@RestController
@RequestMapping("/api/planos")
public class PlanosController {
    
    private static final int DURACAO_PADRAO_DIAS = 30;
    
    // Planos disponíveis
    private static final String PLANO_PADRAO = "PADRÃO COM ANÚNCIOS";
    private static final String PLANO_SEM_ANUNCIOS = "PADRÃO (SEM ANÚNCIOS)";
    private static final String PLANO_PREMIUM = "PREMIUM";
    
    private final PlanosService planosService;

    @Autowired
    public PlanosController(PlanosService planosService) {
        this.planosService = planosService;
    }
    
    /**
     * Inicializa o plano padrão caso não exista nenhum plano cadastrado.
     */
    @PostConstruct
    public void inicializarPlanos() {
        if (planosService.listarTodos().isEmpty()) {
            // Cria os três planos iniciais
            planosService.salvar(criarPlano(PLANO_PADRAO, new BigDecimal("20.90"), 
                true, "Full HD (1080p)", 2, 2, 
                "É o plano mais barato; contém anúncios durante os conteúdos."));
                
            planosService.salvar(criarPlano(PLANO_SEM_ANUNCIOS, new BigDecimal("44.90"), 
                false, "Full HD (1080p)", 2, 2,
                "Permite adicionar 1 assinante extra que mora fora da residência, com custo adicional."));
                
            planosService.salvar(criarPlano(PLANO_PREMIUM, new BigDecimal("59.90"), 
                false, "4K + HDR (Ultra HD)", 4, 6,
                "Permite adicionar até 2 assinantes extras fora da casa, com custo extra."));
        }
    }
    
    /**
     * Cria um novo plano de assinatura.
     * 
     * @param plano Dados do plano a ser criado
     * @return O plano criado ou mensagem de erro
     */
    @PostMapping
    public ResponseEntity<?> criarPlano(@RequestBody Planos plano) {
        // Verifica se já existe um plano com o mesmo nome
        if (planosService.listarTodos().stream().anyMatch(p -> p.getNome().equalsIgnoreCase(plano.getNome()))) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Já existe um plano com este nome.");
        }
        
        // Define a data de criação e ativa o plano por padrão
        plano.setDataCriacao(java.time.LocalDateTime.now());
        plano.setAtivo(true);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(planosService.salvar(plano));
    }

    @GetMapping
    public ResponseEntity<List<Planos>> listarTodos() {
        return ResponseEntity.ok(planosService.listarTodos());
    }
    
    @GetMapping("/ativos")
    public ResponseEntity<List<Planos>> listarAtivos() {
        return ResponseEntity.ok(planosService.listarAtivos());
    }
    
    @GetMapping("/paginado")
    public ResponseEntity<Page<Planos>> listarPaginado(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(planosService.listarAtivosPaginados(pageable));
    }
    
    /**
     * Cria um objeto Planos com as configurações fornecidas.
     * 
     * @param nome Nome do plano
     * @param preco Preço mensal
     * @param temAnuncios Se o plano contém anúncios
     * @param resolucao Resolução de vídeo
     * @param limiteDispositivos Número de telas simultâneas
     * @param dispositivosDownload Número de dispositivos para download
     * @param observacoes Observações adicionais
     * @return Plano configurado
     */
    private Planos criarPlano(String nome, BigDecimal preco, boolean temAnuncios, 
                            String resolucao, int limiteDispositivos, 
                            int dispositivosDownload, String observacoes) {
        return Planos.builder()
            .nome(nome)
            .descricao(nome)
            .preco(preco)
            .temAnuncios(temAnuncios)
            .resolucao(resolucao)
            .duracaoDias(DURACAO_PADRAO_DIAS)
            .limiteDispositivos(limiteDispositivos)
            .dispositivosDownload(dispositivosDownload)
            .observacoes(observacoes)
            .ativo(true)
            .dataCriacao(java.time.LocalDateTime.now())
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planos> buscarPorId(@PathVariable Long id) {
        Planos plano = planosService.buscarPorId(id);
        return new ResponseEntity<>(plano, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planos> atualizarPlano(
            @PathVariable Long id, @RequestBody Planos planoAtualizado) {
        // Mantém os dados originais que não devem ser alterados
        Planos planoExistente = planosService.buscarPorId(id);
        if (planoExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Atualiza apenas os campos permitidos
        if (planoAtualizado.getNome() != null) {
            planoExistente.setNome(planoAtualizado.getNome());
        }
        if (planoAtualizado.getDescricao() != null) {
            planoExistente.setDescricao(planoAtualizado.getDescricao());
        }
        if (planoAtualizado.getPreco() != null) {
            planoExistente.setPreco(planoAtualizado.getPreco());
        }
        if (planoAtualizado.getTemAnuncios() != null) {
            planoExistente.setTemAnuncios(planoAtualizado.getTemAnuncios());
        }
        if (planoAtualizado.getResolucao() != null) {
            planoExistente.setResolucao(planoAtualizado.getResolucao());
        }
        if (planoAtualizado.getLimiteDispositivos() != null) {
            planoExistente.setLimiteDispositivos(planoAtualizado.getLimiteDispositivos());
        }
        if (planoAtualizado.getDispositivosDownload() != null) {
            planoExistente.setDispositivosDownload(planoAtualizado.getDispositivosDownload());
        }
        if (planoAtualizado.getObservacoes() != null) {
            planoExistente.setObservacoes(planoAtualizado.getObservacoes());
        }
        
        // Atualiza a data de atualização
        planoExistente.setDataAtualizacao(java.time.LocalDateTime.now());
        
        Planos planoAtualizadoSalvo = planosService.atualizar(id, planoExistente);
        return new ResponseEntity<>(planoAtualizadoSalvo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPlano(@PathVariable Long id) {
        planosService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id, @RequestParam boolean ativo) {
        planosService.ativarDesativar(id, ativo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Removidos endpoints relacionados a busca por preço, pois o sistema agora trabalha com um preço fixo

    @GetMapping("/buscar")
    public ResponseEntity<List<Planos>> buscarPorTermo(@RequestParam(required = false) String termo) {
        List<Planos> planos = planosService.buscarPorTermo(termo != null ? termo : "");
        return new ResponseEntity<>(planos, HttpStatus.OK);
    }

    @GetMapping("/contar-ativos")
    public ResponseEntity<Long> contarPlanosAtivos() {
        long total = planosService.contarPlanosAtivos();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}
