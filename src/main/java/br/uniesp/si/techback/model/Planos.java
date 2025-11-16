package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "planos")
public class Planos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @Column(nullable = false)
    private Integer duracaoDias;
    
    @Column(nullable = false)
    private Integer limiteDispositivos;
    
    @Column(nullable = false)
    private Boolean temAnuncios = false;
    
    @Column(nullable = false, length = 50)
    private String resolucao = "Full HD (1080p)";
    
    @Column(name = "dispositivos_download")
    private Integer dispositivosDownload;
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    // Construtor padrão necessário para o JPA
    public Planos() {
    }
    
    // Construtor com parâmetros para facilitar a criação
    public Planos(String nome, String descricao, BigDecimal preco, 
                 Integer duracaoDias, Integer limiteDispositivos) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoDias = duracaoDias;
        this.limiteDispositivos = limiteDispositivos;
    }
}
