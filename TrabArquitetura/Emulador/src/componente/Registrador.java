package componente;

public class Registrador implements Componente{
	public LinhaDados entrada, palavraSalva;
	private int indice;
	public Registrador(LinhaDados entrada, int indice) {
		this.entrada=entrada;
		palavraSalva=new LinhaDados();
		this.indice=indice;
	}
	public void trocaEstado(char estado[])
	{
		if (estado[indice]=='1')
		{
			palavraSalva.valor=entrada.valor.clone();
		}
	}
	public LinhaDados getSaida()
	{
		return palavraSalva;
	}
}
