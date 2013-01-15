package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class DialogoRecursos implements Dialogo, Serializable {

	private static final long serialVersionUID = 1L;
	private Personagem jogador;
	public DialogoRecursos(Personagem jogador) {
		this.jogador = jogador;
	}

	@Override
	public String falaInicial() {
		return "Voce tem R$"+jogador.getDinheiro();
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		return FabricaItem.get(comando.split(" x")[0]).olhar();
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc = new ArrayList<String>();
		for (Item i:jogador.getInventario().keySet()){
			opc.add(i.getNome()+" x"+ jogador.getInventario().get(i));
		}
		opc.add("sair");
		return opc;
	}

}
