package componente;


public class Memoria implements Componente {

	public static final int altura=1*1024*1024;// 1M palavras
	public static final int end= (int) (Math.log(altura)/Math.log(2));
	private LinhaDados[] banco;
	private int indice;
	private LinhaDados endereco;
	private LinhaDados dadosIn, dadosOut= new LinhaDados();
	public Memoria(LinhaDados dadosIn, LinhaDados endereco, int indice)
	{
		banco= new LinhaDados[altura];
		for (LinhaDados l : banco) {
			l= new LinhaDados();
		}
		this.indice=indice;
		this.dadosIn=dadosIn;
		this.endereco=endereco;
		this.dadosOut=new LinhaDados();
	}
	public void preSet(int end, String dado)
	{
		banco [end]= new LinhaDados(dado);
	}
	@Override
	public void trocaEstado(char[] estado) {
		
		int posicao = Integer.parseInt(endereco.toString(), 2);
		if (estado[indice]=='1')
		{
			//leitura
			dadosOut.valor = banco[posicao].valor.clone();
		}
		else
		{
			//escrita
			banco[posicao]= new LinhaDados(dadosIn.valor);
		}
	}

	@Override
	public LinhaDados getSaida() {
		
		return dadosOut;
	}

}
