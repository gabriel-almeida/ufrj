package componente;

public class Mux implements Componente{
	public LinhaDados entrada0, entrada1, saida;
	private int indice;
	public Mux(LinhaDados entrada1, LinhaDados entrada2, int indice) {
		this.entrada0 = entrada1;
		this.entrada1 = entrada2;
		this.indice = indice;
	}
	@Override
	public void trocaEstado(char[] estado) {
		if (estado[indice]=='0')
		{
			saida=entrada0;
		}
		if (estado[indice]=='1')
		{
			saida=entrada1;
		}
		
	}
	@Override
	public LinhaDados getSaida() {
		return saida;
	}

}
