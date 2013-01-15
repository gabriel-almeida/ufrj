package base;

public class Simbolo implements Comparable<Simbolo>{
	private String nome;
	public static final Simbolo EPSILON=new Simbolo("epsilon");
	public Simbolo(String nome)
	{
		this.nome=nome;
	}
	public Simbolo(Character letra) {
		this.nome=letra.toString();
	}
	@Override
	public String toString() {
		return nome;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simbolo other = (Simbolo) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	@Override
	public int compareTo(Simbolo o) {
		return nome.compareTo(o.nome);
	}
	
}
