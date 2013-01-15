package base;

import java.util.*;

public class Automato {
	
	private Set<Simbolo> alfabeto;
	private Set<Estado> estados;
	private Estado estadoInicial;
	private Set<Estado> estadosFinais;
	public Automato()
	{
		alfabeto= new HashSet<Simbolo>();
		estados= new HashSet<Estado>();
		estadosFinais= new HashSet<Estado>();		
	}
	public Set<Simbolo> getAlfabeto()
	{
		return alfabeto; 
	}
	public boolean isFinal(Estado e)
	{
		return estadosFinais.contains(e);
	}
	public void removerSimbolo(Character c) {
		removerSimbolo(c.toString());
	}
	public void removerSimbolo(String s)
	{
		for (Simbolo sim : alfabeto) {
			if (sim.toString().equals(s))
			{
				alfabeto.remove(sim);
				return;
			}
		}
	}
	public void addSimbolo(String s)
	{
		alfabeto.add(new Simbolo(s));		
	}
	public void addSimbolo(Character s)
	{
		addSimbolo(s.toString());
	}
	public void addEstado(Estado e)
	{
		estados.add(e);
	}
	public void removerEstado(String nome)
	{
		for (Estado es : estados) {
			if (es.toString().equals(nome))
			{
				estados.remove(es);
				return;
			}
		}
	}
	public void setEstadoFinal(Set<Estado> estadosFinais)
	{
		this.estadosFinais=estadosFinais;
	}
	public Estado getEstado(String nome)
	{
		for (Estado e: estados)
		{
			if(e.toString().equals(nome))
			{
				return e;
			}
		}
		return null;
	}
	public void addEstadoFinal(String nome)
	{
		for (Estado	e : estados)
		{
			if(e.toString().equals(nome))
			{
				estadosFinais.add(e);
				return;
			}
		}
	}
	public void addEstadoFinal(Estado e)
	{
		if (!estados.contains(e))
		{
			throw new AutomatoInvalidoException(this);
		}
		estadosFinais.add(e);
	}
	public Set<Estado> getEstadoFinal()
	{
		return estadosFinais;
	}
	public void setEstados(Set<Estado> estados)
	{
		this.estados=estados;
	}
	public void setEstadoInicial(Estado e)
	{
		if (!estados.contains(e))
		{
			throw new AutomatoInvalidoException(this);
		}
		estadoInicial=e;
	}
	public Estado getEstadoInicial()
	{
		return estadoInicial;
	}
	public void setAlfabeto(Set<Simbolo> alfabeto)
	{
		this.alfabeto=alfabeto;
	}
	@Override
	public String toString() {
		//TODO this
		return "Automato [alfabeto=" + alfabeto + ", estados=" + estados
				+ ", estadoInicial=" + estadoInicial + ", estadosFinais="
				+ estadosFinais + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((alfabeto == null) ? 0 : alfabeto.hashCode());
		result = prime * result
				+ ((estadoInicial == null) ? 0 : estadoInicial.hashCode());
		result = prime * result + ((estados == null) ? 0 : estados.hashCode());
		result = prime * result
				+ ((estadosFinais == null) ? 0 : estadosFinais.hashCode());
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
		Automato other = (Automato) obj;
		if (alfabeto == null) {
			if (other.alfabeto != null)
				return false;
		} else if (!alfabeto.equals(other.alfabeto))
			return false;
		if (estadoInicial == null) {
			if (other.estadoInicial != null)
				return false;
		} else if (!estadoInicial.equals(other.estadoInicial))
			return false;
		if (estados == null) {
			if (other.estados != null)
				return false;
		} else if (!estados.equals(other.estados))
			return false;
		if (estadosFinais == null) {
			if (other.estadosFinais != null)
				return false;
		} else if (!estadosFinais.equals(other.estadosFinais))
			return false;
		return true;
	}
	public Set<Estado> getEstados() {
		return estados;
	}
	
	
}
