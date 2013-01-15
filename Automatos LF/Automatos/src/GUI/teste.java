package GUI;

import base.Automato;
import base.Deterministico;
import base.Estado;
import base.NaoDeterministico;
import base.Simbolo;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NaoDeterministico a= new NaoDeterministico();
		
		Simbolo zero= new Simbolo("0");
		Simbolo um= new Simbolo("1");
		a.addSimbolo("0");
		a.addSimbolo("1");
		
		Estado q1= new Estado("q1");
		Estado q2= new Estado("q2");
		Estado q3= new Estado("q3");
		Estado q4= new Estado("q4");
		Estado q5= new Estado("q5");
		Estado q6= new Estado("q6");
		Estado q7= new Estado("q7");
		Estado q8= new Estado("q8");
		Estado q9= new Estado("q9");
		Estado q10= new Estado("q10");
		Estado q11= new Estado("q11");
		
		a.addEstado(q1);
		a.addEstado(q2);
		a.addEstado(q3);
		a.addEstado(q4);
		a.addEstado(q5);
		a.addEstado(q6);
		a.addEstado(q7);
		a.addEstado(q8);
		a.addEstado(q9);
		a.addEstado(q10);
		a.addEstado(q11);
		
		a.setEstadoInicial(q1);
		
		q1.addTransicao(q2, Simbolo.EPSILON);
		q1.addTransicao(q8, Simbolo.EPSILON);
		q2.addTransicao(q3, Simbolo.EPSILON);
		q2.addTransicao(q4, Simbolo.EPSILON);
		q3.addTransicao(q5, zero);
		q4.addTransicao(q6, um);
		q5.addTransicao(q7, Simbolo.EPSILON);
		q6.addTransicao(q7, Simbolo.EPSILON);
		q7.addTransicao(q2, Simbolo.EPSILON);
		q7.addTransicao(q8, Simbolo.EPSILON);
		q8.addTransicao(q9, zero);
		q9.addTransicao(q10, um);
		q10.addTransicao(q11, um);
		
		a.addEstadoFinal(q11);
		
		Deterministico b= a.ConstrucaoDeSubconjuntos();
		System.out.println(b.computar("00011"));
		
	}

}
