package modelo;

import java.util.ArrayList;
import java.util.Collection;

public class DialogoCriarVirus implements Dialogo{
	private static final long serialVersionUID = 1L;
	private Personagem jogador;
	
	public DialogoCriarVirus(Personagem jogador) {
		this.jogador = jogador;
	}
	@Override
	public String falaInicial() {	
		return "Os niveis dos virus disponiveis estao de acordo com sua capacidade de Progamaçao";
	}

	@Override
	public String interagir(String comando) {
		if (comando.equalsIgnoreCase("sair")){
			return null;
		}
		Item i= FabricaItem.get(comando);
		if (i instanceof Virus){
			//checa se o jogador tem nivel para fazer isso
			Virus v= (Virus) i;
			if (v.getNivel()>jogador.nivelProgramacao()){
				return "Voce ainda nao consegue programar um virus tao complexo";
			}
			if (!Acao.realizaAcao("cria virus", 10)){
				return "Voce acabou de fazer um virus ...";
			}
			jogador.darItem(i, 1);
			return "Virus do tipo " + comando + " foi criado";
		}
		return "Invalido";
	}

	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc= new ArrayList<String>();
		if (jogador.nivelProgramacao()>=1){
			opc.add(FabricaItem.VIRUS1.getNome());
		}
		if (jogador.nivelProgramacao()>=2){
			opc.add(FabricaItem.VIRUS2.getNome());
		}
		if (jogador.nivelProgramacao()>=4){
			opc.add(FabricaItem.VIRUS3.getNome());
		}
		opc.add("sair");
		return opc;
	}
	
}
