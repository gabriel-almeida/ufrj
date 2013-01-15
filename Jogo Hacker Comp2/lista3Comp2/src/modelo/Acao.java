package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Acao implements Serializable{
	private static final long serialVersionUID = 1L;
	private static ArrayList<String> acoes= new ArrayList<String>();
	
	public static boolean realizaAcao(final String nomeAcao, final int tempo){
		if (acoes.contains(nomeAcao)){
			return false;
		}
		acoes.add(nomeAcao);
		new Thread(new Runnable (){		
			@Override
			public void run() {
				try {
					Thread.sleep(1000*tempo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				acoes.remove(nomeAcao);
			}
		}).start();
		return true;
	}
	
}
