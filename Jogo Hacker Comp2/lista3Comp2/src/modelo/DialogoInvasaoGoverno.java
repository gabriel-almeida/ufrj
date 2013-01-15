package modelo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DialogoInvasaoGoverno implements Dialogo, Serializable {
	private static final long serialVersionUID = 1L;
	private NPC npc;
	private Personagem jogador;
	private int dificuldade;
	public DialogoInvasaoGoverno(NPC npc, Personagem jogador, int dificuldade) {
		this.npc = npc;
		this.jogador= jogador;
		this.dificuldade=dificuldade;
	}

	@Override
	public String falaInicial() {
		return npc.getNome();
	}

	@Override
	public String interagir(String comando) {
		boolean serverCaiu=false;
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Random rd= new Random();
		if (comando.equalsIgnoreCase("SQL Injection")){
			if (rd.nextInt(11)*jogador.nivelSI()*jogador.numeroSeguidores()>100*dificuldade){
				serverCaiu=true;
			}
		}
		if (comando.equalsIgnoreCase("Telnet")){
			if (rd.nextInt(11)*jogador.nivelRedes()*jogador.numeroSeguidores()>100*dificuldade){
				serverCaiu=true;
			}
		}
		if (comando.equalsIgnoreCase("Buffer Overflow")){
			if (rd.nextInt(11)*jogador.nivelProgramacao()*jogador.numeroSeguidores()>100*dificuldade){
				serverCaiu=true;
			}	
		}
		if (serverCaiu){
			npc.setDialogo(new DialogoNulo("Server offline"));
			return null;
		}
		return "Falho";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		opc.add("SQL Injection");
		opc.add("Telnet");
		opc.add("Buffer Overflow");
		opc.add("sair");
		return opc;
	}

}