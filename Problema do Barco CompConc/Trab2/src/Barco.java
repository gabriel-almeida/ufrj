public class Barco{

	private int cTotal, javaTotal;
	private int c, java;
	private int situacao;
	private boolean embarcando;
	private int totalBarcos;
	private int totalEsperado;
	public Barco(int cTotal, int javaTotal) {
		this.cTotal=cTotal;
		this.javaTotal=javaTotal;
		c=java=situacao=0;
		embarcando=false;

		totalBarcos=(cTotal+javaTotal)/4;
		totalEsperado=(cTotal%4+javaTotal%4)%4;

		//verifica-se é possivel haver pelo menos uma viagem
		finaliza();
	}
	private void finaliza()
	{
		if (javaTotal+cTotal<4 || (javaTotal==3 && cTotal==1) || (cTotal==3 && javaTotal==1) )
		{
			//LOG
			if ((javaTotal==3 && cTotal==1) || (cTotal==3 && javaTotal==1))
			{
				System.out.println("Era esperado um total de 4.");
				//totalBarco==1 porque a conta nao leva em consideraçao isso ocorrer
				if (cTotal+javaTotal==4 && totalBarcos==1){System.out.println("Execução correta");}
				else{System.out.println("ERRO");}
			}
			else{
				System.out.println("Era esperado um total de "+totalEsperado +".");
				if (cTotal+javaTotal==totalEsperado && totalBarcos==0){System.out.println("Execução correta");}
				else{System.out.println("ERRO");}
			}
			//fim LOG
			
			System.out.println(cTotal + " "+ javaTotal);
			System.exit(0);
		}		
	}
	private boolean liberado()
	{
		if (embarcando || c+java>4 || ( c+java==4 && !( (c==3 && java==1) || (c==1 && java==3) ) ))
		{
			return true;
		}
		return false;
	}
	public synchronized void filaEspera(Programador p) throws InterruptedException
	{
		//me travo enquanto o barco atual está cheio
		while(liberado())
		{
			wait();
		}

		//anoto minha entrada
		if (p instanceof ProgramadorC){c++;}
		else {java++;}

		//LOG
		System.out.println(p.toString() + " entrou na fila.");

		//espero até que todas as condições sejam satisfeitas para ser liberado para executar board()
		while ( !barcoCheio(p)){
			wait();
		}

	}
	private synchronized boolean barcoCheio(Programador p) throws InterruptedException
	{		
		boolean vouTravar=false;

		//verifico se alguem deve esperar
		if ( (c==3 && java==2) || (c==1 && java==4)) {situacao=1;}
		if ((c==2 && java==3) || (c==4 && java==1)) {situacao=2;}

		//se posso normalizar a situaçao...
		if (situacao==2 && p instanceof ProgramadorJava )
		{
			situacao=0;
			java--;
			vouTravar=true;
		}
		if (situacao==1  && p instanceof ProgramadorC)
		{
			situacao=0;
			c--;
			vouTravar=true;
		}

		//verifico se esta tudo pronto agora
		if ( c+java>=4 && !(c==1 &&java==3)  && !(c==3 && java==1) && !embarcando )
		{
			//se está tudo consistente, vou liberar para começarem a embarcarem
			//caso contrário, realizo outro loop na filaEspera() para corrigir a situação
			if (situacao==0) {
				embarcando=true; 

				//LOG
				System.out.println(p.toString()+ " liberou a fila.");
			}
			notifyAll();

		}

		//me travo se for preciso
		if(vouTravar){

			//LOG
			System.out.println(p.toString() +" esta esperando a proxima.");

			while(liberado()){
				wait();}

			//volto para a fila
			if(p instanceof ProgramadorC){c++;}
			else {java++;}

			//LOG
			System.out.println(p.toString()+ " voltou a fila.");

			//executo esse método novamente para verificar se a situação atual é válida
			return barcoCheio(p);
		}
		return embarcando;
	}
	public synchronized boolean board(Programador p)
	{
		if (p instanceof ProgramadorC){c--;cTotal--;}
		else {java--;javaTotal--;}

		//LOG
		System.out.println(p.toString()+" entrou no barco. C="+c+ " Java="+ java);

		//se sou o ultimo, devo assumir os remos, retornando true
		if (c+java==0)
		{
			return true;
		}
		return false;
	}
	public synchronized void rowBoard(Programador p)
	{
		//libero para  próxima viagem desbloqueando tudo
		embarcando=false;
		notifyAll();

		//LOG
		totalBarcos--;
		System.out.println(p.toString()+" assumiu os remos. Ainda faltam "+ totalBarcos+ " barcos.");
		System.out.println("C restante="+cTotal+ " Java restante="+javaTotal);
		//fim LOG

		//verifica se é possivel continuar
		finaliza();
	}

}
