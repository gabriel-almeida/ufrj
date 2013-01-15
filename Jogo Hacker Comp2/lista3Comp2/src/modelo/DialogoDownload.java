package modelo;

import java.util.ArrayList;
import java.util.Collection;

import controle.SemItemSuficienteException;

public class DialogoDownload implements Dialogo {
	private static final long serialVersionUID = 1L;
	private NPC npc;
	private Personagem jogador;
	public DialogoDownload(NPC npc, Personagem jogador) {
		this.npc= npc;
		this.jogador=jogador;
	}

	@Override
	public String falaInicial() {
		return "Escolha um item do servidor para baixar para seu desktop";
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Item i = FabricaItem.get(comando);
		try {
			Personagem.enviarItem(npc, jogador, i, 1);
		} catch (SemItemSuficienteException e) {
			return "Error 404";
		}
		return "Foi feito o download de " + comando;
		
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		for (Item  i : npc.getInventario().keySet()) {
			opc.add(i.getNome());
		}
		opc.add("sair");
		return opc;
	}

}
