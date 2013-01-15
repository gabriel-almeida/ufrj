package modelo;

import java.io.Serializable;

public class Virus extends Item implements Serializable{
	private static final long serialVersionUID = 1L;
	private int nivel;
	public Virus(String nome, String descricao, int preco, int nivel) {
		super(nome, descricao, preco);
		this.nivel=nivel;
	}
	public int getNivel() {
		return nivel;
	}

}
