package modelo;


public class NPC extends Personagem{
	private static final long serialVersionUID = 1L;
	//Design Strategy usado nessa parte para repetir certos comportamentos com os npcs
	private Dialogo dialogo;
	
	public NPC(String nome, String descricao, Dialogo dialogo) {
		super(nome, descricao);
		this.dialogo= dialogo;
	}
	public NPC(String nome, String descricao){
		this(nome, descricao, new DialogoNulo());
	}
	public void setDialogo(Dialogo dialogo) {
		this.dialogo = dialogo;
	}
	public Dialogo getDialogo() {
		return dialogo;
	}	
	
}
