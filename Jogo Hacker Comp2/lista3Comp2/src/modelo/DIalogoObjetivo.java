package modelo;

import java.util.ArrayList;
import java.util.Collection;


public class DIalogoObjetivo implements Dialogo {

	private static final long serialVersionUID = 1L;

	@Override
	public String falaInicial() {
		return "Com uma guerra virtual se formando, voce decide dar sua contribuicao. \nSeu objetivo é acabar com 3 sites do governo brasileiro, mas antes voce precisa ganhar alguma experiencia.";
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("Livros")){
			return "Cada livro comprado contribui para algum de seus conhecimentos: Programacao, Redes ou Sistemas de \nInformacao. Esses conhecimentos influenciam em seus ataques.";
		}
		if (comando.equalsIgnoreCase("Ataques")){
			return "A efetividade de cada ataque é influenciada por diferentes niveis de conhecimentos \ne pela quantidade de seguidores lhe ajudando. Cada ataque pode lhe dar dinheiro ou seguidores.";
		}
		if (comando.equalsIgnoreCase("Seguidores")){
			return "Seguidore sao pessoas com a qual voce ganhou respeito e agora te ajudam em seus ataques.";
		}
		if (comando.equalsIgnoreCase("Virus")){
			return "Diferentes virus podem ser criados de acordo com seu nivel de programacao. \nEles podem ser dispersados nas redes sociais para se ganhar seguidores.";
		}
		return null;
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc = new ArrayList<String>();
		
		opc.add("Livros");
		opc.add("Ataques");
		opc.add("Seguidores");
		opc.add("Virus");
		opc.add("sair");
		return opc;
	}

}
