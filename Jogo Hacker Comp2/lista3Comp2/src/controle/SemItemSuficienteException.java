package controle;
import modelo.Personagem;

public class SemItemSuficienteException extends Exception {
	private static final long serialVersionUID = 1L;
	private Personagem vendedor;

	public SemItemSuficienteException(Personagem vendedor) {
		this.vendedor = vendedor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "Personagem " +vendedor.getNome()+" nao possui o item solicitado";
	}
	
}
