package base;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Estado{
	private String nome;
	private Map <Simbolo, Set<Estado>> funcaoTransicao;
	public Estado(String nome)
	{
		this.nome=nome;
		funcaoTransicao=new TreeMap<Simbolo, Set<Estado>>();
	}
	public void addTransicao(Estado e,Simbolo s)
	{
		if (!funcaoTransicao.containsKey(s))
		{
			funcaoTransicao.put(s, new HashSet<Estado>());
		}
		funcaoTransicao.get(s).add(e);
	}
	public Set<Estado> funcaoE()
	{
		HashSet<Estado> resultado= new HashSet<Estado>();
		if (funcaoTransicao.get(Simbolo.EPSILON)!=null){
			resultado.addAll(funcaoTransicao.get(Simbolo.EPSILON));
		}
		
		LinkedList<Estado> fila= new LinkedList<Estado>(resultado);
		while(!fila.isEmpty())
		{
			Set<Estado> conj=fila.poll().funcaoTransicao.get(Simbolo.EPSILON);
			if (conj==null){continue;}
			if (!resultado.containsAll(conj))
			{
				fila.addAll(conj);
				resultado.addAll(conj);
			}
		}
		resultado.add(this);
		return resultado;
	}
	@Override
	public String toString() {
		return nome;
	}
	public Set<Estado> getTransicoes(Simbolo s)
	{
		return funcaoTransicao.get(s);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (funcaoTransicao == null) {
			if (other.funcaoTransicao != null)
				return false;
		} else if (!funcaoTransicao.equals(other.funcaoTransicao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
