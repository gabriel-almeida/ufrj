package modelo;

import java.io.Serializable;

public class Livro extends Item implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final int PROGRAMACAO=1;
	public static final int REDE=2;
	public static final int SI=3;
	private int categoria;
	private int nivel;
	public Livro(String nome, String descricao, int preco, int categoria, int nivel) {
		super(nome, descricao, preco);
		this.categoria=categoria;
		this.nivel=nivel;
	}
	public int getCategoria(){
		return categoria;
	}
	public int getNivel(){
		return nivel;
	}

}
