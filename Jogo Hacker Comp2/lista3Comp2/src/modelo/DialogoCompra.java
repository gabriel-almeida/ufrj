package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import controle.NumeroNegativoException;
import controle.SemDinheiroSuficienteException;
import controle.SemItemSuficienteException;

public class DialogoCompra implements Dialogo, Serializable{
	private static final long serialVersionUID = 1L;
	protected Personagem jogador;
	protected NPC npc;
	public DialogoCompra(Personagem jogador, NPC npc) {
		this.jogador = jogador;
		this.npc = npc;
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
		String str = comando.split("-")[0];
		
		try {
			Item i= FabricaItem.get(str);
			Personagem.venda(jogador,npc, i,1);
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
		for (Item i: npc.getInventario().keySet())
		{
			opc.add(i.getNome()+ "-R$ "+i.getPreco());
		}
		opc.add("sair");
		return opc;
	}



}
