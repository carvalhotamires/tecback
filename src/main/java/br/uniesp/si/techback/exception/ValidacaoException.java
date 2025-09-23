package br.uniesp.si.techback.exception;

/**
 * Exceção lançada quando ocorre um erro de validação de negócio.
 */
public class ValidacaoException extends RuntimeException {
    
    /**
     * Cria uma nova instância de ValidacaoException com a mensagem especificada.
     * 
     * @param mensagem a mensagem de detalhe da exceção
     */
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma nova instância de ValidacaoException com a mensagem e a causa especificadas.
     * 
     * @param mensagem a mensagem de detalhe da exceção
     * @param causa a causa da exceção
     */
    public ValidacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
