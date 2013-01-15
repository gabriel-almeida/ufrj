package controle;

public class EntidadeInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entidadeNome;
	public EntidadeInvalidaException(String entidadeNome) {
		this.entidadeNome = entidadeNome;
	}
	@Override
	public String toString() {
		return "Entidade Invalida: " + entidadeNome;
	}
	
}
