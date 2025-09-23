package br.uniesp.si.techback.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Favoritos> favoritos = new ArrayList<>();
    
    public enum UserRole {
        USER, ADMIN
    }
    
    // Métodos utilitários para gerenciar a relação bidirecional
    public void adicionarFavorito(Favoritos favorito) {
        favoritos.add(favorito);
        favorito.setUsuario(this);
    }
    
    public void removerFavorito(Favoritos favorito) {
        favoritos.remove(favorito);
        favorito.setUsuario(null);
    }
}
