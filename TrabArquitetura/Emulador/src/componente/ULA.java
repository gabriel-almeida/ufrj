package componente;

public class ULA implements Componente{

	private LinhaDados entrada1, entrada2, saida;
	public char[] flags={'0','0','0','0'}; //CARRY, NEGATIVO, OVERFLOW, ZERO 
	private int indice;
	public static final int PontosControle=6;
	public ULA(LinhaDados entrada1, LinhaDados entrada2, int indice) {
		this.entrada1 = entrada1;
		this.entrada2 = entrada2;
		this.indice = indice;
		this.saida= new LinhaDados();
	}
	//FUNCOES LOGICAS BASICAS POR BIT
	private char xor(char e1, char e2)
	{
		if (e1==e2){return '0';}
		return '1';
	}
	private char and(char e1, char e2)
	{
		if (e1=='1' && e2=='1'){return '1';}
		return '0';
	}
	private char or(char e1, char e2)
	{
		if (e1=='1' || e2=='1') {return '1';}
		return '0';
	}
	private char not(char c)
	{
		if (c=='0') return '1';
		return '0';
	}
	//FUNCOES USADAS PELA UNIDADE DE CONTROLE
	private LinhaDados deslocaEsquerda(LinhaDados l)
	{
		LinhaDados s= new LinhaDados();
		for (int i=LinhaDados.larguraPalavra-1; i>0; i--)
		{
			s.valor[i-1]=l.valor[i];
		}
		return s;
	}
	private LinhaDados deslocaDireita(LinhaDados l)
	{
		LinhaDados s= new LinhaDados();
		for (int i=0; i< LinhaDados.larguraPalavra-1; i++)
		{
			s.valor[i+1]=l.valor[i];
		}
		return s;
	}
	private LinhaDados andLogico(LinhaDados l1, LinhaDados l2)
	{
		LinhaDados s= new LinhaDados();
		
		for (int i=0; i<LinhaDados.larguraPalavra; i++)
		{
			s.valor[i]=and(l1.valor[i], l2.valor[i]);
		}
		return s;
	}
	private LinhaDados orLogico(LinhaDados l1, LinhaDados l2)
	{
		LinhaDados s= new LinhaDados();
		
		for (int i=0; i<LinhaDados.larguraPalavra; i++)
		{
			s.valor[i]=or(l1.valor[i], l2.valor[i]);
		}
		return s;

	}
	private LinhaDados notLogico(LinhaDados l)
	{
		LinhaDados s= new LinhaDados();
		for (int i=0; i<LinhaDados.larguraPalavra; i++)
		{
			s.valor[i]=not(l.valor[i]);
		}
		return s;
	}
	private LinhaDados subtrair(LinhaDados l1, LinhaDados l2)
	{
		return soma(l1, neg(l2));
	}
	private LinhaDados neg(LinhaDados l)
	{
		return soma(notLogico(l), LinhaDados.um);
	}
	private LinhaDados soma(LinhaDados l1, LinhaDados l2)
	{
		char carry='0';
		LinhaDados s= new LinhaDados();
		for (int i=LinhaDados.larguraPalavra-1; i>=0; i--)
		{
			char a=l1.valor[i];
			char b=l2.valor[i];
			//(a xor b ) xor carry
			s.valor[i]=xor(xor(a,b), carry);
			//(a and b) xor ((a xor b) and carry)
			carry=xor(and(a,b), and(xor(a,b),carry) ) ;
		}
		return s;
	}
	
	/*private LinhaDados atualizaFlags(){
		
		//saida negativa
		if (saida.valor[0]=='1'){
			flags[1]=1;
		}
		else {flags[1]=0;}
		
		//saida é zero
		flags[3]=1;
		for (int i=0;i<LinhaDados.larguraPalavra; i++)
		{
			if (saida.valor[i]=='1')
			{
				flags[3]=0;
				break;
			}
		}
		
	}*/
	@Override
	public void trocaEstado(char[] estado) {
		String cmd= String.copyValueOf(estado, indice, PontosControle);
		if (cmd.equals("000000"))
		{
			saida=soma(entrada1, entrada2);
		}
		if (cmd.equals("000001"))
		{
			saida=subtrair(entrada1, entrada2);
		}
		if (cmd.equals("000010"))
		{
			saida=andLogico(entrada1, entrada2);
		}
		if (cmd.equals("000011"))
		{
			saida=orLogico(entrada1, entrada2);
		}
		if (cmd.equals("000100"))
		{
			saida=notLogico(entrada1);
		}
		if (cmd.equals("000101"))
		{
			saida=notLogico(entrada2);
		}
		if (cmd.equals("000110"))
		{
			saida=LinhaDados.zero;
		}
		if (cmd.equals("000111"))
		{
			saida=neg(entrada1);
		}
		if (cmd.equals("001000"))
		{
			saida=neg(entrada2);
		}
		if (cmd.equals("001001"))
		{
			saida=deslocaEsquerda(entrada1);
		}
		if (cmd.equals("001010"))
		{
			saida=deslocaEsquerda(entrada2);
		}
		if (cmd.equals("001011"))
		{
			saida=deslocaDireita(entrada1);
		}
		if (cmd.equals("001100"))
		{
			saida=deslocaDireita(entrada2);
		}
		if (cmd.equals("001101"))
		{
			saida=entrada1;
		}
		if (cmd.equals("001110"))
		{
			saida=entrada2;
		}
		if (cmd.equals("001111"))
		{
			saida=soma(entrada1, LinhaDados.um);
		}
	}

	@Override
	public LinhaDados getSaida() {
		return saida;
	}

}

