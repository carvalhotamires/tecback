package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "enderecos")
public class Enderecos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false, length = 9)
    private String cep;

    // Construtor padrão necessário para o JPA
    public Enderecos() {
    }

    // Construtor com parâmetros para facilitar a criação de instâncias
    public Enderecos(String logradouro, String numero, String complemento, 
                    String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}
