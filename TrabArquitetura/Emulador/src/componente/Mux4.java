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
		saida= new LinhaDados();
	}
	@Override
	public void trocaEstado(char[] estado) {
		String cmd= String.copyValueOf(estado, indice, 2);
		if (cmd.equals("00"))
		{
			saida.valor=entrada0.valor.clone();
		}
		if (cmd.equals("01"))
		{
			saida.valor=entrada1.valor.clone();
		}
		if (cmd.equals("10"))
		{
			saida.valor=entrada2.valor.clone();
		}
		if (cmd.equals("11"))
		{
			saida.valor=entrada3.valor.clone();
		}
	}
	@Override
	public LinhaDados getSaida() {
		return saida;
	}

}
