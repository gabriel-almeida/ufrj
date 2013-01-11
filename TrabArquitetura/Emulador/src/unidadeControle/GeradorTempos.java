package unidadeControle;

import java.util.Observable;
import java.util.Observer;

public class GeradorTempos extends Observable implements Observer{
	int tempo=1, max=3;
	Oscilador osc;
	public GeradorTempos(Oscilador osc) {
		this.osc=osc;
		osc.addObserver(this);
	}
	@Override
	public void update(Observable o, Object arg) {
		tempo= (tempo +1) % (3+1);
		notifyObservers(tempo);
		
	}
	
}
