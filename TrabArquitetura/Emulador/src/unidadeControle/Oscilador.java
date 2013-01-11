package unidadeControle;

import java.util.Observable;

public class Oscilador extends Observable {
	int f;

	public Oscilador(int f) {
		super();
		this.f = f;
	}
	public void inicia()
	{
		while (true)
		{
			try {
				wait(1/f); //TODO ver isso
				notifyObservers();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
