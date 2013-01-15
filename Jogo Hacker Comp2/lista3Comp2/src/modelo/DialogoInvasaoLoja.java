package modelo;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class DialogoInvasaoLoja implements Dialogo, Serializable {
	private static final long serialVersionUID = 1L;
	private Personagem jogador;
	public DialogoInvasaoLoja(Personagem jogador) {
		this.jogador = jogador;
	}

	@Override
	public String falaInicial() {
		return "Voce tem R$"+jogador.getDinheiro();
	}

	@Override
	public String interagir(String comando) {
		int dinheiroGanho=0;
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Random rd= new Random();
		if(!Acao.realizaAcao("Invade Loja", 15)){
			return "E mais seguro esperar uns 15 segundo antes de arriscar novamente.";
		}
		if (comando.equalsIgnoreCase("Pishing Scan")){
			dinheiroGanho = rd.nextInt(11)*jogador.nivelSI()*jogador.numeroSeguidores();
		}
		if (comando.equalsIgnoreCase("Cracker senha")){
			dinheiroGanho = rd.nextInt(11)*jogador.nivelRedes()*jogador.numeroSeguidores();
		}
		if (comando.equalsIgnoreCase("Exploit")){
			dinheiroGanho = rd.nextInt(11)*jogador.nivelProgramacao()*jogador.numeroSeguidores();
		}
		jogador.aumentarDinheiro(dinheiroGanho);
		return "Voce ganhou "+ dinheiroGanho;
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		opc.add("Pishing Scan");
		opc.add("Cracker senha");
		opc.add("Exploit");
		opc.add("sair");
		return opc;
	}

}