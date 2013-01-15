package controle;

public class NumeroNegativoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numero;
	public NumeroNegativoException(int numero) {
		this.numero = numero;
	}
	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return "Numero negativo : "+ numero;
	}
	
}
