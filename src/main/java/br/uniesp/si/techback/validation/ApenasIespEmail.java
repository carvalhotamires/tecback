package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indica que essa anotação será exibida na documentação JavaDoc
 */
@Documented
/**
 * Define que essa anotação será usada como uma anotação de validação (Bean Validation)
 * O validador que será usado é o IespEmailValidator
 */
@Constraint(validatedBy = IespEmailValidator.class)
/**
 * Especifica onde essa anotação pode ser aplicada (em atributos de classe e métodos)
 */
@Target({ElementType.FIELD, ElementType.METHOD})
/**
 * Define que essa anotação estará disponível em tempo de execução (necessário para o Bean Validation funcionar)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApenasIespEmail {

    /**
     * Mensagem padrão que será exibida se a validação falhar
     */
    String message() default "O e-mail não é válido pois não contém '@iesp.edu.br'";

    /**
     * Usado para agrupar validações, geralmente deixado vazio
     */
    Class<?>[] groups() default {};

    /**
     * Usado para agrupar validações, geralmente deixado vazio
     */
    Class<? extends Payload>[] payload() default {};
}
