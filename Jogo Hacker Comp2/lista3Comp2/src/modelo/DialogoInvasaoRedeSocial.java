package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DialogoInvasaoRedeSocial implements Dialogo{

	private static final long serialVersionUID = 1L;
	private Personagem jogador;
	
	public DialogoInvasaoRedeSocial(Personagem jogador) {
		this.jogador = jogador;
	}

	@Override
	public String falaInicial() {
		return "Escolha um processo para descobrir a senha.";
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		
		int efeito=0;
		if (comando.equalsIgnoreCase("Engenharia Social")){
			efeito=jogador.nivelSI();
		}
		if (comando.equalsIgnoreCase("Sniffing")){
			efeito=jogador.nivelRedes();
		}
		if (!Acao.realizaAcao("Ataque Rede Social", 20)){
			return "Espere uns 20 segundos entre cada ataque.";
		}
		Random rd= new Random();
		efeito*=rd.nextInt(6);
		jogador.darItem(FabricaItem.SEGUIDORES, efeito);
		return "Voce conseguiu " + efeito + " seguidores apos divulgar alguns dados confidenciais";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		opc.add("Engenharia Social");
		opc.add("Sniffing");
		opc.add("sair");
		return opc;
	}

}
