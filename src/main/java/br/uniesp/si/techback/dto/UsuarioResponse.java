package br.uniesp.si.techback.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String name;
    private String email;
    
    public static UsuarioResponse fromEntity(br.uniesp.si.techback.model.Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setName(usuario.getName());
        response.setEmail(usuario.getEmail());
        return response;
    }
}
