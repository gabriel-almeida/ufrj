package modelo;

import java.util.ArrayList;
import java.util.Collection;

public class DialogoNulo implements Dialogo {
	private static final long serialVersionUID = 1L;
	private String falaInicial;
	public DialogoNulo(String falaInicial) {
		this.falaInicial=falaInicial;
	}
	public DialogoNulo() {
		this.falaInicial="Ola";
	}
	@Override
	public String falaInicial() {
		return falaInicial;
	}
	@Override
	public String interagir(String comando) {
		return null;
	}
	@Override
	public Collection<String> opcoes() {
		ArrayList<String> opc = new ArrayList<String>();
		opc.add("sair");
		return opc;
	}
}
