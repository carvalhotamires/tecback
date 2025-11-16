package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * Implementação do validador para a anotação @PalavrasBloqueadas.
 * Este validador verifica se o texto contém alguma palavra da lista de palavras bloqueadas.
 * A verificação é case-insensitive.
 *
 * Palavras bloqueadas atualmente:
 * - sexo, drogas, violência, pornô, xxx, nazista, racista
 *
 * Para adicionar mais palavras, basta incluí-las na lista PALAVRAS_BLOQUEADAS.
 */
public class PalavrasBloqueadasValidator implements ConstraintValidator<PalavrasBloqueadas, String> {

    // Lista de palavras que não são permitidas nos títulos
    private static final List<String> PALAVRAS_BLOQUEADAS = Arrays.asList(
            "sexo", "drogas", "violência", "pornô", "xxx", "nazista", "racista"
    );

    /**
     * Método principal de validação.
     *
     * @param valor O valor do campo que está sendo validado
     * @param context Contexto no qual a validação está sendo executada
     * @return true se o valor for válido (não contém palavras bloqueadas), false caso contrário
     */
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        // Se o valor for nulo ou vazio, consideramos válido
        // A validação de @NotBlank deve ser feita separadamente
        if (valor == null || valor.trim().isEmpty()) {
            return true;
        }

        // Converte o valor para minúsculas para fazer uma comparação case-insensitive
        String valorLowerCase = valor.toLowerCase();

        // Verifica se NENHUMA das palavras bloqueadas está contida no valor
        return PALAVRAS_BLOQUEADAS.stream()
                .noneMatch(palavra -> valorLowerCase.contains(palavra.toLowerCase()));
    }
}