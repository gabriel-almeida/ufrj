package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import controle.NumeroNegativoException;
import controle.SemDinheiroSuficienteException;
import controle.SemItemSuficienteException;

public class DialogoVenda implements Dialogo, Serializable{
	private static final long serialVersionUID = 1L;
	private NPC npc;
	private Personagem jogador;
	
	public DialogoVenda(NPC npc, Personagem jogador) {
		this.npc = npc;
		this.jogador = jogador;
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		String str = comando.split("-")[0];
		
		try {
			Item i= FabricaItem.get(str);
			Personagem.venda(npc, jogador, i,1,i.getPreco()/2);
		} catch (NumberFormatException e) {
			return "Numero invalido";
		} catch (NumeroNegativoException e) {
			return e.toString();
		} catch (SemItemSuficienteException e) {
			return e.toString();
		} catch (SemDinheiroSuficienteException e) {
			return e.toString();
		}
		return "Transacao Efetuada";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		for (Item i: jogador.getInventario().keySet())
		{
			if (i!=FabricaItem.SEGUIDORES){
				opc.add(i.getNome()+ "-R$ "+i.getPreco()/2);
			}
		}
		opc.add("sair");
		return opc;
	}

	@Override
	public String falaInicial() {
		return "Voce tem R$"+jogador.getDinheiro();
	}

}
