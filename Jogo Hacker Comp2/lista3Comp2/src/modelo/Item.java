package modelo;

public class Item extends Entidade {
	private static final long serialVersionUID = 1L; 
	private int preco;
	
	public Item(String nome, String descricao, int preco) {
		super(nome, descricao);
		this.preco=preco; 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + preco;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (preco != other.preco)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Item [preco=" + preco + ", getNome()=" + getNome() + "]";
	}
	public int getPreco() {
		return preco;
	}
	
	public void setPreco(int preco) {
		this.preco = preco;
	}
}
