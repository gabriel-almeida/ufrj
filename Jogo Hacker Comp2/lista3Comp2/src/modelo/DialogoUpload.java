package modelo;

import java.util.ArrayList;
import java.util.Collection;
import controle.SemItemSuficienteException;

public class DialogoUpload implements Dialogo {
	private NPC npc;
	private Personagem jogador;
	public DialogoUpload(NPC npc, Personagem jogador) {
		this.npc = npc;
		this.jogador = jogador;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Item i = FabricaItem.get(comando);
		if (i == FabricaItem.SEGUIDORES){
			return "Voce nao pode enviar isso";
		}
		try {
			Personagem.enviarItem(jogador, npc, i, 1);
		} catch (SemItemSuficienteException e) {
			return "Voce nao tem isso";
		}
		return comando + " salvo no servidor.";
	}

	@Override
	public String falaInicial() {
		return "Escolha um de seus recursos para salvar no servidor web";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc = new ArrayList<String>();
		for (Item i : jogador.getInventario().keySet()) {
			if (i != FabricaItem.SEGUIDORES){
				opc.add(i.getNome());
			}
		}
		opc.add("sair");
		return opc;
	}

}
