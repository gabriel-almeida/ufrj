
public abstract class Programador extends Thread {
	private Barco recurso;
	private int delay, id;
	
	@Override
	public String toString() {
		if (this instanceof ProgramadorJava)
		{return id+" (Java)";}
		return id+" (C)";
	}
	public Programador(Barco recurso, int delay, int id) {
		super();
		this.recurso = recurso;
		this.delay=delay;
		this.id=id;
	}
	@Override
	public void run() {
		try {
			sleep(delay);
			
			recurso.filaEspera(this);
			
			if (recurso.board(this))
			{
				recurso.rowBoard(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
}