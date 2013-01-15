package controle;

import modelo.Personagem;

public class SemDinheiroSuficienteException extends Exception {
	private static final long serialVersionUID = 1L;
	private Personagem comprador;
	public SemDinheiroSuficienteException(Personagem comprador) {
		this.comprador = comprador;
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "Personagem "+ comprador.getNome()+ " nao possui dinheiro suficiente" ;
	}
	

}
