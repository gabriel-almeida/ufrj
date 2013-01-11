package componente;

import javax.management.RuntimeErrorException;

public class LinhaDados {
	char[] valor;
	public static final int larguraPalavra=16;
	public static final LinhaDados um= new LinhaDados(zeros(larguraPalavra-1).concat("1"));
	public static final LinhaDados zero= new LinhaDados(zeros(larguraPalavra));
	private static String zeros(int t)
	{
		StringBuffer s= new StringBuffer(t);
		for (int i=0; i<t; i++)
		{
			s.append("0");
		}
		return s.toString();
	}
	public LinhaDados()
	{
		this(zeros(larguraPalavra));
	}
	/*public LinhaDados clone()
	{
		return new LinhaDados(valor);
	}*/
	public LinhaDados(String s)
	{
		this(s.toCharArray());
	}
	public LinhaDados(char valor[])
	{
		if (valor.length!=larguraPalavra)
		{
			throw new RuntimeErrorException(new Error("O tamanho da palavra de dados deve ser igual a "+larguraPalavra));
		}
		this.valor= valor.clone();
	}
	public String toString()
	{
		StringBuffer s= new StringBuffer(larguraPalavra);
		for (char c : valor) {
			s.append(c);
		}
		return s.toString();
	}
}
