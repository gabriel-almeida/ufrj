package componente;

public class Teste {

	public static void main(String[] args) {		
		
		char []pal1={'0','0','0','0', '0','0','0','0', '0','0','0','0', '0','0','1','0',};
		char []pal2={'0','0','0','0', '0','0','0','0', '0','0','0','0', '0','0','0','1',};
		char []cmd1={'1','1','1', '1'};
		
		Registrador a= new Registrador(new LinhaDados(pal1), 0);
		Registrador b= new Registrador(new LinhaDados(pal2), 1);
		
		Mux m= new Mux(a.getSaida(), b.getSaida(), 2);
		Registrador s= new Registrador(m.getSaida(), 3);
		
		a.trocaEstado(cmd1);
		b.trocaEstado(cmd1);
		m.trocaEstado(cmd1);
		s.trocaEstado(cmd1);
		System.out.println(s.getSaida());
	}

}
