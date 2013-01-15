package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import controle.SemItemSuficienteException;

public class DialogoLancarVirus implements Dialogo  {

	private static final long serialVersionUID = 1L;
	private Personagem jogador;
	public DialogoLancarVirus(Personagem jogador) {
		this.jogador = jogador;
	}

	@Override
	public String falaInicial() {
		return "Escolha um de seus virus para dispersar nas Redes Sociais";
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Item i= FabricaItem.get(comando);
		Virus v= (Virus) i;
		
		Random rd= new Random();
		int efeito=v.getNivel() * rd.nextInt(6);
		
		try {
			jogador.tirarItem(v, 1);
		} 
		catch (SemItemSuficienteException e) {
			return e.toString();
		}
		jogador.darItem(FabricaItem.SEGUIDORES, efeito);
		return "Seu virus " + comando+ " infectou " + efeito + " computadores";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc = new ArrayList<String>();
		for (Item i : jogador.getInventario().keySet()) {
			if (i instanceof Virus){
				opc.add(i.getNome());
			}
		}
		opc.add("sair");
		return opc;
	}

}
