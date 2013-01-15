package base;

import java.util.ArrayList;

public class Expressao {
	public String variavelMae;  //Variavel da expressao
	public String expressao;	// Expressao
	public ArrayList<String> termos; //Termos de cada variavel da expressao
	public ArrayList<String> variaveis; //Todas as variaveis da expressao
	

	Expressao(String expressao, boolean estFinal){
		
		// Como a entrada deve ser da forma V = T.V U T.V posso (sendo V variavel e T termos)
		// utilizar o split para obter cada variavel termo guardando-os em diferentes array 
		// sendo seu indice o indicador de relação(Termo i pertence a variavel i)
		
		String[] temp = expressao.split("=");  
		this.variavelMae = temp[0]; 
		
		termos = new ArrayList<String>();
		variaveis = new ArrayList<String>();
		
		if (estFinal){
			this.expressao = temp[1].concat("U.E");
		}else{
			this.expressao = temp[1];
		}
		String[] temp2 = this.expressao.split("U");

		for(int i=0;i<temp2.length;i++){
			String[] temp3 = temp2[i].split("\\.");
			termos.add(temp3[0]);
			variaveis.add(temp3[1]);	
		}
	}

	public void Simplificar(){
		
		//Simplifica expressões unindo termos de variaveis iguais em uma mesma expressão
		
			for(int j=0; j<variaveis.size()-1;j++){
				for(int i=1+j;i<variaveis.size();i++){
					if(variaveis.get(j).equalsIgnoreCase(variaveis.get(i))){
						if (termos.get(i).contains("(")||termos.get(j).contains("(")){ //Remove parenteses para realizar a uniao
							termos.set(i,termos.get(i).replace("(",""));
							termos.set(i,termos.get(i).replace(")",""));
							termos.set(j,termos.get(j).replace(")",""));
							termos.set(j,termos.get(j).replace("(",""));
							termos.set(j, "(" + termos.get(j).concat("U"+termos.get(i))+")");
						}else{
							termos.set(j, termos.get(j).concat("U"+termos.get(i)));
						}
						termos.remove(i);
						variaveis.remove(i);
						i--;
					}
				}
			}
	}
	
	public String Multiplicar(String multiplicador, String multiplicando){
		
		//Multiplica termos pelo outro, necessario na Substituição e ao utilizar lema de Arden 
		 
		boolean composto = false;
		for(int i=0; i<multiplicando.length();i++){
			if(multiplicando.charAt(i) == 'U'){    //Verifica se termo é composto para aplicar distributiva
				composto = true;
				multiplicando = multiplicando.substring(0, i+1).concat(multiplicador + multiplicando.substring(i+1, multiplicando.length()));
			}
		}
		if (composto){
			return "("+multiplicador.concat(multiplicando)+")"; //Se termo é composto envolver com parenteses 
		}else{
			return multiplicador.concat(multiplicando); //Se termo não é composto não envolver com parenteses 
		}
	}
	
	public void Arden(){
		
		// Aplica o Lema de Arden
		
		for(int i=0; i<variaveis.size();i++){
			if(variaveis.get(i).equalsIgnoreCase(variavelMae)){ 
				termos.set(i,termos.get(i).concat("*")); //Se é possivel aplicar o lema de arden o termo vira T* 
				variaveis.remove(i);                     //Removemos a variavel
				String temp = termos.get(i);
				termos.remove(i);
				i--;
				for(int j=0; j<variaveis.size();j++){   //Multiplicamos o termo T* pelos outros termos da expressão
					termos.set(j,Multiplicar(temp,termos.get(j)));
				}	
			}
		}
	}
	
	public boolean Substituir(Expressao exp,boolean substituiu){
		
		//Substitui uma expressao em outra caso haja a possibilidade
		
		for(int i=0; i<variaveis.size();i++){
			if(variaveis.get(i).equalsIgnoreCase(exp.variavelMae)){//Procura variaveis para substituir
			   substituiu = true;
			   variaveis.remove(i); //Remove a variaveis que serão substituidas
			   for(int j=0; j<exp.variaveis.size();j++){    //Multiplicamos o termo associado a variavel substituida a todos o termos 
				   termos.add(Multiplicar(termos.get(i),exp.termos.get(j))); //da expressão que a substituiu e adicionamos seus termos
				   variaveis.add(exp.variaveis.get(j)); // e variaveis			   
				}
				termos.remove(i);
				i--;
			}
		}
		return substituiu;
	}
	

	@Override
	public String toString(){
		
		//Une todas os termos e variaveis de volta na expressão para ser impressa apenas
		
		expressao = variavelMae + " = ";  
		for	(int j=0; j<variaveis.size();j++){
			expressao = expressao.concat(termos.get(j) + variaveis.get(j) + " U ");
			}
		expressao = expressao.replace("E", "");  //Some com a "variavel" vazio
		expressao = expressao.substring(0, expressao.length()-3); //Elimina o " U " sobressalhente
		return expressao;
	}

}

