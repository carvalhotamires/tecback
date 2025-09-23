package br.uniesp.si.techback.config;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "tamiresoak@gmail.com";
            
            // Verifica se já existe um usuário admin
            if (!usuarioRepository.existsByEmail(adminEmail)) {
                try {
                    Usuario admin = new Usuario();
                    admin.setName("Tamires");
                    admin.setEmail(adminEmail);
                    admin.setPassword(passwordEncoder.encode(""));
                    admin.setRole(Usuario.UserRole.ADMIN);
                    
                    usuarioRepository.save(admin);
                    log.info("Usuário admin criado com sucesso!");
                } catch (Exception e) {
                    log.error("Erro ao criar usuário admin: {}", e.getMessage());
                }
            }
        };
    }
}
