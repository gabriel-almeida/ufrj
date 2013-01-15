package modelo;

import java.io.Serializable;
import java.util.HashSet;

public class FabricaItem implements Serializable{
	
	//Uma "fabrica estatica" de itens, acho que nem existe isso
	//mas permite que eu pegue as mesmas referencias de itens a partir dela
	private static final long serialVersionUID = 1L;
	
	public static final Item LIVRO_PROGRAMACAO1 = new Livro("Programacao para leigos","Livro basico que ensina sobre IFs e FOR", 10, Livro.PROGRAMACAO, 1);
	public static final Item LIVRO_PROGRAMACAO2 = new Livro("Apostila de C do Adriano","Desvende o misterio dos ponteiros nesse livro intermediario", 100, Livro.PROGRAMACAO, 2);
	public static final Item LIVRO_PROGRAMACAO3 = new Livro("Arte da Programacao de Computadores", "A verdadeira biblia da computacao", 1000, Livro.PROGRAMACAO, 4);
	public static final Item LIVRO_SI1 = new Livro("Curso de HTML da W3School", "Basico do basico sobre montagem de sites: a linguagem HTML", 10, Livro.SI, 1);
	public static final Item LIVRO_SI2 = new Livro("SQL Guia de Bolso", "Livro de referencia sobre consultas a banco de dados", 100, Livro.SI, 2);
	public static final Item LIVRO_SI3 = new Livro("SEO - Otimizacao de Sites", "Entenda como sites de busca funcionam e coloque seu site na frente", 1000, Livro.SI, 4);
	public static final Item LIVRO_REDE1 = new Livro("Artigo sobre TCP/IP da Wikipedia", "Visao geral sobre toda a base da internet: o protocolo TCP/IP", 10, Livro.REDE, 1);
	public static final Item LIVRO_REDE2 = new Livro("Use a Cabeca: Redes de Computadores", "Livro abranjante ", 100, Livro.REDE, 2);
	public static final Item LIVRO_REDE3 = new Livro("Seguranca da Informacao", "", 1000, Livro.REDE, 4);
	public static final Item VIRUS1 = new Virus("Virus de Macro","Virus simples feito em 2 linhas que formata o computador da vitima",0,1);
	public static final Item VIRUS2 = new Virus("Worm","Verdadeira praga da internet. Esses tipo de virus se dispersa muito facil",0,2);
	public static final Item VIRUS3 = new Virus("Trojan","Virus poderoso que se instala na maquina da vitima e transmite dados pessoais dela",0,4);
	public static final Item SEGUIDORES = new Item("Seguidores", "Pessoas que lhe ajudam de livre e espontanea vontade ou nao...",0);
	private static HashSet<Item> lista= new HashSet<Item>();
	static{
		lista.add(LIVRO_PROGRAMACAO1);
		lista.add(LIVRO_PROGRAMACAO2);
		lista.add(LIVRO_PROGRAMACAO3);
		lista.add(LIVRO_REDE1);
		lista.add(LIVRO_REDE2);
		lista.add(LIVRO_REDE3);
		lista.add(LIVRO_REDE3);
		lista.add(LIVRO_SI1);
		lista.add(LIVRO_SI2);
		lista.add(LIVRO_SI3);
		lista.add(VIRUS1);
		lista.add(VIRUS2);
		lista.add(VIRUS3);
		lista.add(SEGUIDORES);
	}
	public static Item get(String itemNome){
		for (Item i : lista) {
			if (i.getNome().equalsIgnoreCase(itemNome)){
				return i;
			}
		}
		return null;
	}
}
