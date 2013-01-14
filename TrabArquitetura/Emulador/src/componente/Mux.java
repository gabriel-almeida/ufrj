package componente;

public class Mux implements Componente{
	public LinhaDados entrada0, entrada1, saida;
	private int indice;
	public Mux(LinhaDados entrada1, LinhaDados entrada2, int indice) {
		this.entrada0 = entrada1;
		this.entrada1 = entrada2;
		this.indice = indice;
		this.saida=new LinhaDados();
	}
	@Override
	public void trocaEstado(char[] estado) {
		if (estado[indice]=='0')
		{
			saida.valor=entrada0.valor.clone();
		}
		if (estado[indice]=='1')
		{
			saida.valor=entrada1.valor.clone();
		}
		
	}
	@Override
	public LinhaDados getSaida() {
		return saida;
	}

}
