package componente;

public class teste2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinhaDados principal= new LinhaDados();
		Registrador R0 = new Registrador(principal, 0);
		Registrador R1 = new Registrador(principal, 1);
		Registrador PC = new Registrador(principal, 2);
		Registrador R2 = new Registrador(principal, 3);
		Registrador R3 = new Registrador(principal, 4);
		
		Registrador IR = new Registrador(principal, 5);
	
		Registrador RDados = new Registrador(principal, 6);//Problemas aqui
		Registrador REn = new Registrador(principal, 7);
		
		Mux muxB = new Mux(R2.getSaida(), R3.getSaida(), 8);
		Mux4 muxA= new Mux4(RDados.getSaida(), R0.getSaida(), R1.getSaida(), PC.getSaida(), 9);
		
		
		Mux mux = new Mux(null, principal, 11);
		
		ULA ula= new ULA(muxA.getSaida(), muxB.getSaida(), 12);//18
		
		Memoria mem = new Memoria(RDados.getSaida(), REn.getSaida(), 18);
		mux.entrada0=mem.getSaida();
	
		mem.preSet(0, "0000000000000010");
		mem.preSet(1, "0000000000000010");
		
		//mem envia
		
		char cmd[]= "0000001000011111001".toCharArray();
		mem.trocaEstado(cmd);
		mux.trocaEstado(cmd);
		RDados.trocaEstado(cmd);
		muxA.trocaEstado(cmd);
		ula.trocaEstado(cmd);
		
		System.out.println(ula.getSaida().toString());
	}

}
