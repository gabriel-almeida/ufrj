package GUI;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import base.Automato;
import base.AutomatoInvalidoException;
import base.Deterministico;
import base.Estado;
import base.NaoDeterministico;
import base.Simbolo;

public class CLI {

	static Scanner sc= new Scanner(System.in);
	static Deterministico afd=new Deterministico();
	static NaoDeterministico afnd=new NaoDeterministico();

	public static void main(String[] args) {
		while (true)
		{
			linha();
			System.out.println("CONSTRUTOR DE AUTOMATOS");
			linha();
			System.out.println("0-Sair\n1-AFD\n2-AFND\n3-Limpar Ambos Automatos");
			try
			{
				int opc = sc.nextInt();
				if (opc==1)
				{
					menuAFD();
				}
				else if (opc==2)
				{
					menuAFND();
				}
				else if (opc==2)
				{
					System.out.println("Continuar?(S/N)");
					String x=sc.nextLine();
					if (x.equalsIgnoreCase("s"))
					{
						afd=new Deterministico();
						afnd=new NaoDeterministico();
					}
				}
				else if (opc==0)
				{
					return;
				}
				else
				{
					System.out.println("Invalido");
				}
			}
			catch (Exception ex)
			{
				System.out.println(ex);
				sc.nextLine();
			}
		}

	}
	static void linha()
	{
		System.out.println("======================================");
	}
	static void menuAFD()
	{
		while (true)
		{
			linha();
			System.out.println("AUTOMATO FINITO DETERMINISTICO");
			linha();
			status(afd);

			padrao();
			System.out.println("4-Computar Palavra\n5-Gerar Expressao Regular");
			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0){return;}
			else if (opc==1){alfabeto(afd);}
			else if (opc==2){estados(afd);}
			else if (opc==3){transicaoAFD();}
			else if (opc==4)
			{
				try
				{
					System.out.println("Digite a palavra a ser computada, dentro do alfabeto escolhido:");
					System.out.println(afd.computar(sc.nextLine()));
				}
				catch (AutomatoInvalidoException ex)
				{
					System.out.println("A palavra escolhida nao usa os simbolos do alfabeto ou o automato é invalido.");
				}

			}
			else if (opc==5)
			{
				System.out.println(afd.brozozowski());
			}
		}
	}
	static void status (Automato a)
	{
		System.out.println("Alfabeto Sigma="+ a.getAlfabeto().toString());
		System.out.println("Estados Q="+ a.getEstados().toString());
		System.out.println("Estado Inicial q0="+a.getEstadoInicial());
		System.out.println("Estados Finais Qf="+ a.getEstadoFinal().toString());
		linha();

	}
	static void alfabeto(Automato a)
	{
		while(true)
		{
			linha();
			System.out.println("Alfabeto");
			linha();
			System.out.println("Simbolos atualmente: "+a.getAlfabeto());
			System.out.println("0-Voltar\n1-Adicionar Simbolos\n2-Remover Simbolo\n3-Limpar Alfabeto\n4-Usar alfabeto binario");
			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0)
			{
				return;
			}
			else if (opc==1)
			{
				System.out.println("Digite o Simbolo (apenas letras e numeros serao aceitos): ");
				char c=sc.nextLine().charAt(0);
				if (  ( (c >= 'A') && (c <= 'Z') ) || ( (c >= 'a') && (c <= 'z') ) || ( (c >= '0') && (c <= '9') ) )
				{
					a.addSimbolo(c);
				}
			}
			else if (opc==2)
			{
				System.out.println("Digite simbolo a ser excluido");
				a.removerSimbolo(sc.nextLine().charAt(0));
			}
			else if (opc==3)
			{
				a.setAlfabeto(new HashSet<Simbolo>());
			}
			else if (opc==4)
			{
				a.getAlfabeto().clear();
				a.addSimbolo('0');
				a.addSimbolo('1');
			}
		}
	}
	static void estados(Automato a)
	{
		while(true)
		{
			linha();
			System.out.println("Estados");
			linha();
			System.out.println("Estados Atuais: "+ a.getEstados());
			System.out.println("Estado Inicial: "+ a.getEstadoInicial());
			System.out.println("Estados Finais: "+ a.getEstadoFinal());
			linha();
			System.out.println("\n0-Voltar\n1-Adicionar Estado\n2-Remover Estado\n3-Alterar Estado Inicial\n4-Adicionar Estado Final\n5-Remover Estado Final\n6-Gerar n estados\n7-Limpar Estados");
			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0){return;}
			else if (opc==1)
			{
				System.out.println("Digite um nome para o Estado:");
				a.addEstado(new Estado(sc.nextLine()));
			}
			else if(opc==2)
			{
				System.out.println("Nome do estado a ser excluido:");
				a.removerEstado(sc.nextLine());
			}
			else if(opc==3)
			{
				System.out.println("Nome do Estado Inicial (0 para cancelar):");
				String nome=sc.nextLine();
				if (nome.equals("0")){continue;}
				for (Estado e : a.getEstados()) 
				{
					if(e.toString().equals(nome))
					{
						a.setEstadoInicial(e);
						break;
					}
				}
			}
			else if(opc==4)
			{
				System.out.println("Digite o nome do Estado que será final:");
				String nome=sc.nextLine();
				a.addEstadoFinal(nome);
			}
			else if(opc==5)
			{
				System.out.println("Digite o nome do Estado Final a ser removido (ele nao será excluido):");
				String nome=sc.nextLine();
				for (Estado e : a.getEstadoFinal())
				{
					if (e.toString().equals(nome))
					{
						a.getEstadoFinal().remove(e);
						break;
					}
				}
			}
			else if(opc==6)
			{
				System.out.println("Serao usados estados com nomes no formato Qi, com i de 0 a n-1 (Q0 sera o estado inicial). Digite n (0 para cancelar):");
				int n=sc.nextInt();
				sc.nextLine();
				if(n==0){continue;}
				Estado inicial=new Estado("Q0");
				a.addEstado(inicial);
				a.setEstadoInicial(inicial);
				for (int i=1; i<n; i++)
				{
					a.addEstado(new Estado("Q"+i));
				}
			}
			else if(opc==7)
			{
				a.getEstados().clear();
				a.setEstadoInicial(null);
				a.getEstadoFinal().clear();
			}

		}
	}
	static void exemplo()
	{
		NaoDeterministico a= afnd;

		afnd.getAlfabeto().clear();

		Simbolo zero= new Simbolo("0");
		Simbolo um= new Simbolo("1");
		a.addSimbolo("0");
		a.addSimbolo("1");

		Estado q1= new Estado("q1");
		Estado q2= new Estado("q2");
		Estado q3= new Estado("q3");
		Estado q4= new Estado("q4");
		Estado q5= new Estado("q5");
		Estado q6= new Estado("q6");
		Estado q7= new Estado("q7");
		Estado q8= new Estado("q8");
		Estado q9= new Estado("q9");
		Estado q10= new Estado("q10");
		Estado q11= new Estado("q11");

		a.addEstado(q1);
		a.addEstado(q2);
		a.addEstado(q3);
		a.addEstado(q4);
		a.addEstado(q5);
		a.addEstado(q6);
		a.addEstado(q7);
		a.addEstado(q8);
		a.addEstado(q9);
		a.addEstado(q10);
		a.addEstado(q11);

		a.setEstadoInicial(q1);

		q1.addTransicao(q2, Simbolo.EPSILON);
		q1.addTransicao(q8, Simbolo.EPSILON);
		q2.addTransicao(q3, Simbolo.EPSILON);
		q2.addTransicao(q4, Simbolo.EPSILON);
		q3.addTransicao(q5, zero);
		q4.addTransicao(q6, um);
		q5.addTransicao(q7, Simbolo.EPSILON);
		q6.addTransicao(q7, Simbolo.EPSILON);
		q7.addTransicao(q2, Simbolo.EPSILON);
		q7.addTransicao(q8, Simbolo.EPSILON);
		q8.addTransicao(q9, zero);
		q9.addTransicao(q10, um);
		q10.addTransicao(q11, um);

		a.addEstadoFinal(q11);
	}
	static void tabelaAFD()
	{
		linha();
		System.out.println("Tabela da funcao Atual:");
		linha();
		System.out.print("Estado");
		for (Simbolo s:afd.getAlfabeto())
		{
			System.out.print("\t\t"+ s + "\t\t");
		}
		System.out.print("\n");
		for (Estado e: afd.getEstados())
		{
			System.out.print(e);
			for (Simbolo s: afd.getAlfabeto()) {
				System.out.print("\t\t"+e.getTransicoes(s)+"\t\t");
			}
			System.out.print("\n");
		}
	}
	static void tabelaAFND()
	{
		linha();
		System.out.println("Tabela da funcao Atual:");
		linha();
		System.out.print("Estado\t\t");
		for (Simbolo s:afnd.getAlfabeto())
		{
			System.out.print( s + "\t\t");
		}
		System.out.print("epsilon\n");
		for (Estado e: afnd.getEstados())
		{
			System.out.print(e+"\t\t");
			for (Simbolo s: afnd.getAlfabeto()) {
				System.out.print(e.getTransicoes(s)+"\t\t");
			}
			System.out.print(e.getTransicoes(Simbolo.EPSILON));
			System.out.print("\n");
		}
	}
	static void transicaoAFD()
	{
		while (true)
		{
			tabelaAFD();
			System.out.println("0-Voltar\n1-Alterar transicoes de um Estado\n2-Validar automato");
			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0)
			{
				return;
			}
			else if(opc==1)
			{
				System.out.println("Digite o nome do estado:");
				String nome=sc.nextLine();
				Estado e=afd.getEstado(nome);
				if (e==null)
				{
					System.out.println("Estado Invalido");
					continue;
				}
				System.out.println("Digite o simbolo do alfabeto :");
				Simbolo s=new Simbolo(sc.nextLine());
				System.out.println("Digite o estado seguinte para essa transicao:");
				Estado alvo=afd.getEstado(sc.nextLine());
				if (alvo==null)
				{
					System.out.println("Estado Invalido");
					continue;
				}
				if (e.getTransicoes(s)!=null)
				{
					e.getTransicoes(s).clear();
				}
				e.addTransicao(alvo, s);
			}
			if (opc==2)
			{
				boolean flag=true;
				for (Estado e:afd.getEstados())
				{
					for (Simbolo s: afd.getAlfabeto())
					{
						if (e.getTransicoes(s)==null)
						{
							flag=false;
							System.out.println("Automato faltando transicao do estado "+e.toString()+" com o simbolo "+ s.toString());					
						}
					}
				}
				if(flag==true){System.out.println("Automato Valido");}
			}
		}
	}
	static void menuAFND()
	{
		while (true){
			linha();
			System.out.println("AUTOMATO FINITO NAO-DETERMINISTICO");
			linha();
			status(afnd);

			padrao();
			System.out.println("4-Converter para AFD\n5-Exemplo AFND");

			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0){return;}
			else if (opc==1){alfabeto(afnd);}
			else if (opc==2){estados(afnd);}
			else if (opc==3){transicaoAFND();}
			else if (opc==4)
			{
				System.out.println("O Automato Deterministico gerado será substituido. Use o menu anterior para visualizar o novo AFD. Deseja continuar?(S/N)");
				String x=sc.nextLine();
				if(x.equalsIgnoreCase("S"))
				{
					afd=afnd.ConstrucaoDeSubconjuntos();
				}
			}
			else if (opc==5)
			{
				System.out.println("Esse automato aceita a expressão (1|0)*011. Carregar exemplo?(S/N)");
				String x=sc.nextLine();
				if (x.equalsIgnoreCase("s"))
				{
					exemplo();
					System.out.println("Converta para um AFD para visualizar melhor.");
				}
			}
		}
	}
	static void padrao()  
	{

		System.out.println("0-Voltar\n1-Alfabeto\n2-Estados\n3-Funcao Transicao");
	}
	static void transicaoAFND()
	{
		while (true)
		{
			tabelaAFND();
			System.out.println("0-Voltar\n1-Adicionar transicoes de um Estado\n2-Limpar transicoes de um simbolo");
			int opc=sc.nextInt();
			sc.nextLine();
			if (opc==0)
			{
				return;
			}
			else if(opc==1)
			{
				System.out.println("Digite o nome do estado:");
				String nome=sc.nextLine();
				Estado e=afnd.getEstado(nome);
				if (e==null)
				{
					System.out.println("Estado Invalido");
					continue;
				}
				System.out.println("Digite o simbolo do alfabeto ('ep' para epsilon):");
				String x=sc.nextLine();
				Simbolo s;
				if (x.equalsIgnoreCase("ep"))
				{
					s=Simbolo.EPSILON;
				}
				else
				{
					s=new Simbolo(x);
				}
				System.out.println("Digite o estado seguinte para essa transicao:");
				Estado alvo=afnd.getEstado(sc.nextLine());
				if (alvo==null)
				{
					System.out.println("Estado Invalido");
					continue;
				}
				e.addTransicao(alvo, s);
			}
			if (opc==2)
			{
				System.out.println("Digite o estado que tera uma de suas transicoes alterada:");
				Estado e=afnd.getEstado(sc.nextLine());
				if (e==null)
				{
					System.out.println("Estado Invalido");
					continue;
				}
				System.out.println("Digite o simbolo da transicao");
				String x= sc.nextLine();
				Set<Estado> conj=e.getTransicoes(new Simbolo(x));
				if (conj!=null)
				{
					System.out.println("A transicao " +x +" de "+ e +" sera apagada. Continuar(S/N)?");
					if (sc.nextLine().equalsIgnoreCase("s"))
					{
						conj.clear();
					}
				}
			}
		}
	}

}
