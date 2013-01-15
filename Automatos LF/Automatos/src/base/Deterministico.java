package base;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Deterministico extends Automato{
	public String computar(String palavra)
	{
		String res="";
		String str=palavra;
		Estado atual=super.getEstadoInicial();
		
		if (atual==null) 
		{
			throw new AutomatoInvalidoException(this);
		}
		
		while (true)
		{
			res+="(" + atual + ", " + str +") ";
			if (str.length()==0)
			{
				break;
			}
			Set<Estado> aux= atual.getTransicoes(new Simbolo(str.charAt(0)));
			if (aux==null || aux.size()!=1)
			{
				throw new AutomatoInvalidoException(this);
			}
			atual=aux.iterator().next();
			str=str.substring(1);
		}
		
		if (super.isFinal(atual))
		{
			res+="\nPalavra Aceita";
		}
		else
		{
			res+="\nPalavra Rejeitada";
		}
		return res;
	}
	public String brozozowski()
	{
		ArrayList<Expressao> Expressoes = new ArrayList<Expressao>();
		boolean estFinal = false;	
		int qnt = super.getEstados().size();
		//Pede quantidade de estados para saber o estado final e quando deve parar de ler
		int i=0;
		
		for (Estado e: super.getEstados()){	
			
			String expressao = e.toString()+"=";
			for (Simbolo s : super.getAlfabeto())
			{
				expressao+= s.toString()+"."+ e.getTransicoes(s).iterator().next().toString()+"U";
			}
			expressao = expressao.substring(0, expressao.length()-1);
			if (super.isFinal(e)){expressao+="U E";}
			System.out.println(expressao);
			Expressao exp = new Expressao(expressao, true); //Cria cada expressão e adiciona a lista
			Expressoes.add(exp);
			i++;
		}
		
		for (i=qnt-1; i>=0;i--){
			
			//Começando pelo estado final primero se simplifica depois aplica arden 
			//Depois faz a substituição de expressoes que ja foram simplificadas e onde arden foi aplicado
			
			boolean substituir; 
			Expressoes.get(i).Simplificar();
			//System.out.println(Expressoes.get(i));
			Expressoes.get(i).Arden();
			//System.out.println(Expressoes.get(i));
			if(i>0){
				do{
					substituir = false; //Tentar substituir por todas as expressões anteriores
					for (int j=i;j<qnt;j++){ //Caso ocorra alguma substituição volta a tentar todas as substituições
						substituir = Expressoes.get(i-1).Substituir(Expressoes.get(j),substituir); 
						Expressoes.get(i-1).Simplificar(); //Simplifica apos a substituição
						//System.out.println(Expressoes.get(i-1));		
					}
				}while(substituir);	
			}
		}
		

		//System.out.println("\n\n");
		String res="";
		for(i=0; i<qnt; i++){ //Imprime o resultado
			Expressoes.get(i).Simplificar();
			res=Expressoes.get(i).toString();
		}

		return res;
	}
}
