package controle;

public class LugarInvalidoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeLugar;
	public LugarInvalidoException(String nomeLugar) {
		this.nomeLugar = nomeLugar;
	}
	@Override
	public String toString() {
		return "Lugar invalido:" + nomeLugar;
	}
	
	
}
