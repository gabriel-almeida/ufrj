package modelo;

import java.io.Serializable;
import java.util.*;

public class Lugar implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nome;
	private Collection<Entidade> coisas;
	private Collection<Lugar> adjacencias;
	
	public Collection<Lugar> getAdjacencias() {
		return adjacencias;
	}
	public void addAdjacencia(Lugar l){
		if(l==this){
			return;
		}
		adjacencias.add(l);
		l.getAdjacencias().add(this);
	}
	public void addEntidade(Entidade e){
		coisas.add(e);
	}
	public Lugar(String nome, Collection<Entidade> coisas,
			Collection<Lugar> adjacencias) {
		this.nome = nome;
		this.coisas = coisas;
		this.adjacencias = adjacencias;
	}
	public Lugar(String string) {
		this(string, new ArrayList<Entidade>(), new HashSet<Lugar>());
	}
	
	public void setAdjacencias(Collection<Lugar> adjacencias) {
		this.adjacencias = adjacencias;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Collection<Entidade> getCoisas() {
		return coisas;
	}
	public void setCoisas(Collection<Entidade> coisas) {
		this.coisas = coisas;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lugar other = (Lugar) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
