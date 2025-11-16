package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IespEmailValidator implements ConstraintValidator<ApenasIespEmail, String> {
    
    private static final String IESP_EMAIL_SUFFIX = "@iesp.edu.br";
    
    @Override
    public void initialize(ApenasIespEmail constraintAnnotation) {
        // Inicialização do validador, se necessário
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Se o valor for nulo ou vazio, retorna falso
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Remove espaços em branco no início e no fim
        email = email.trim();
        
        // Verifica se o e-mail contém exatamente um @
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) {
            return false;
        }
        
        // Verifica se há texto antes do @
        if (atIndex == 0) {
            return false;
        }
        
        // Converte para minúsculas para fazer a comparação sem diferenciar maiúsculas/minúsculas
        String emailLowerCase = email.toLowerCase();
        
        // Verifica se o e-mail termina com @iesp.edu.br
        return emailLowerCase.endsWith(IESP_EMAIL_SUFFIX);
    }
}