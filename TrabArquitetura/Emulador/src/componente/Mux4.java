package componente;

public class Mux4 implements Componente {
	private LinhaDados entrada0, entrada1, entrada2, entrada3, saida;
	private int indice;
	public Mux4(LinhaDados entrada1, LinhaDados entrada2, LinhaDados entrada3, LinhaDados entrada4, int indice) {
		this.entrada0 = entrada1;
		this.entrada1 = entrada2;
		this.entrada2 = entrada3;
		this.entrada3 = entrada4;
		this.indice = indice;
	}
	@Override
	public void trocaEstado(char[] estado) {
		String cmd= String.copyValueOf(estado, indice, 2);
		if ("cmd".equals("00"))
		{
			saida=entrada0;
		}
		if ("cmd".equals("01"))
		{
			saida=entrada1;
		}
		if ("cmd".equals("10"))
		{
			saida=entrada2;
		}
		if ("cmd".equals("11"))
		{
			saida=entrada3;
		}
	}
	@Override
	public LinhaDados getSaida() {
		return saida;
	}

}
