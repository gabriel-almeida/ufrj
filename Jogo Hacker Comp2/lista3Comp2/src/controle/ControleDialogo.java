package controle;

import java.util.Collection;

import modelo.*;
public class ControleDialogo {
	private Dialogo dialogo;
	private String ultimaFala;
	
	ControleDialogo(Dialogo dialogo) {
		this.dialogo= dialogo;
		
	}
	
	
	public String interagir(String comando){
		ultimaFala=dialogo.interagir(comando);
		return ultimaFala;
	}
	
	public String getUltimaFala() {
		return ultimaFala;
	}
	
	public Collection<String> opcoes(){
		return dialogo.opcoes();
	}
	
	public String falaInicial(){
		return dialogo.falaInicial();
	}

}
