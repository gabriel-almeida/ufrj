package base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class NaoDeterministico extends Automato {
	public Deterministico ConstrucaoDeSubconjuntos()
	{
		Deterministico novo=new Deterministico();
		Set<Simbolo> alfabeto=super.getAlfabeto();
		novo.setAlfabeto(alfabeto);
		int i=1;
		//essa fila indica os estados que devem ser criados, usado para eliminar estados mortos
		LinkedList<HashSet<Estado>> fila=new LinkedList<HashSet<Estado>>();
		//Esse HashMap associa um Estado do novo automato a um subconjunto de estados do automato atual
		HashMap<HashSet<Estado>, Estado> mapa= new HashMap<HashSet<Estado>,Estado>();
		
		if (super.getEstadoInicial()==null) {throw new AutomatoInvalidoException(this);}
		
		//adiciono na fila o estado inicial junto com suas transicoes epsilon
		fila.addLast(new HashSet<Estado>(super.getEstadoInicial().funcaoE()));
		
		//associo o que será o novo estado inicial ao conjunto da funcaoE do estado inicial do automato nao deterministico
		Estado q0=new Estado("q0");
		mapa.put(fila.peek(), q0);
		while (!fila.isEmpty())
		{
			//pego o subconjunto que deve ser analisado, usando um 'pop' na fila
			HashSet<Estado> conjuntoAtual= fila.pollFirst();
			Estado atual = mapa.get(conjuntoAtual);
			
			for (Simbolo s : alfabeto)
			{
				HashSet<Estado> conjTransicao=transicaoDoConjunto(conjuntoAtual, s);
				Estado temp;
				if (mapa.containsKey(conjTransicao)==false)
				{
					//se ainda nao tiver ainda usado esse subconjunto, crio um estado
					//no novo automato para representar-lo e adiciono esse estado na fila para ser analizado depois
					temp=new Estado("q"+i);
					i++;
					fila.addLast(conjTransicao);
					//associo no mapa esse novo estado ao subconjunto de estados do automato antigo
					mapa.put(conjTransicao, temp);
				}
				else
				{
					//se ja tiver usado esse subconjunto de estados, simplesmete descubro 
					//a qual estado no novo automato ele pertence usando o mapa
					temp=mapa.get(conjTransicao);
				}
				//adiciono uma nova transicao com o simbolo 's' do estado que está sendo analizado 'atual' para um outro estado
				//'temp' do novo automato, de acordo com o subconjunto de estado obtido
				atual.addTransicao(temp, s);
			}
			novo.addEstado(atual);
		}
		//descubro os estados finais
		Set<Estado> finais=super.getEstadoFinal();
		for (HashSet<Estado> subConjunto : mapa.keySet()) {
			for (Estado e : subConjunto) {
				if (finais.contains(e))
				{
					novo.addEstadoFinal(mapa.get(subConjunto));
					break;
				}
			}
		}
		novo.setEstadoInicial(q0);
		return novo;
	}
	public HashSet<Estado> transicaoDoConjunto(Set<Estado> conjunto, Simbolo s)
	{
		HashSet<Estado> resultado= new HashSet<Estado>();
		HashSet<Estado> auxiliar= new HashSet<Estado>();
		for (Estado e : conjunto)
		{
			if (e.getTransicoes(s)!=null)
			{
				auxiliar.addAll(e.getTransicoes(s));
				resultado.addAll(e.getTransicoes(s));
			}			
		}
		for (Estado e : auxiliar) {
			resultado.addAll(e.funcaoE());
		}
		return resultado;
	}
}
