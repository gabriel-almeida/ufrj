package controle;

import java.io.FileInputStream;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import modelo.*;

public class ControleGeral implements Serializable{
	private static final long serialVersionUID = 1L;
	private Collection<Lugar> mapa;
	private Lugar lugarAtual;
	private Personagem jogador;
	private static ControleGeral controll = null;
	private final static String caminhoSave="JogoSalvo.save";


	private ControleGeral(){
		jogador=new Personagem("Jogador", "voce");
		jogador.aumentarDinheiro(10);
		jogador.darItem(FabricaItem.SEGUIDORES, 1);
		mapa= new ArrayList<Lugar>();
		Lugar bufferLocal;
		
		//Desktop
		bufferLocal= new Lugar("Desktop");
		bufferLocal.addEntidade(new NPC("Recursos","Exibe seus recursos atuais, como livros e virus disponiveis", new DialogoRecursos(jogador)));
		bufferLocal.addEntidade(new NPC("GCC", "Faça e compile seus proprios virus/programas aqui", new DialogoCriarVirus(jogador)));
		bufferLocal.addEntidade(new NPC("Objetivo","Breve sinopse e objetivo", new DIalogoObjetivo()));

		
		
		lugarAtual=bufferLocal;
		mapa.add(bufferLocal);

		//banco
		bufferLocal=new Lugar("Armazenamento nas nuvens");
		HashMap<Item, Integer> inventarioCompartilhado=new HashMap<Item, Integer>();
		NPC upload = new NPC("Upload de Arquivos","Envie seus arquivos importantes para a internet");
		upload.setDialogo(new DialogoUpload(upload, jogador));
		NPC download = new NPC("Download de Arquivos", "Baixe novamente o que esta salvo na internet");
		download.setDialogo(new DialogoDownload(download, jogador));
		upload.setInventario(inventarioCompartilhado);
		download.setInventario(inventarioCompartilhado);
		bufferLocal.addEntidade(upload);
		bufferLocal.addEntidade(download);
		mapa.add(bufferLocal);

		//Loja
		bufferLocal=new Lugar("Loja virtual");
		inventarioCompartilhado= new HashMap<Item, Integer>();
		//venda
		NPC vender= new NPC("Vender Recursos","Venda seus recursos extras aqui");
		vender.setDialogo(new DialogoVenda(vender,jogador));
		vender.aumentarDinheiro(999999);
		vender.setInventario(inventarioCompartilhado);
		
		NPC comprar= new NPC("Comprar Livros", "Compre seus livro de informatica aqui");
		comprar.setDialogo(new DialogoCompra(jogador, comprar));
		comprar.setInventario(inventarioCompartilhado);
		
		comprar.darItem(FabricaItem.LIVRO_PROGRAMACAO1, 1);
		comprar.darItem(FabricaItem.LIVRO_PROGRAMACAO2, 1);
		comprar.darItem(FabricaItem.LIVRO_PROGRAMACAO3, 1);
		comprar.darItem(FabricaItem.LIVRO_SI1, 1);
		comprar.darItem(FabricaItem.LIVRO_SI2, 1);
		comprar.darItem(FabricaItem.LIVRO_SI3, 1);
		comprar.darItem(FabricaItem.LIVRO_REDE1, 1);
		comprar.darItem(FabricaItem.LIVRO_REDE2, 1);
		comprar.darItem(FabricaItem.LIVRO_REDE3, 1);
		bufferLocal.addEntidade(comprar);
		bufferLocal.addEntidade(vender);
		//ataque loja
		NPC ataque=new NPC("Invadir loja", "Ganha dinheiro");
		ataque.setDialogo(new DialogoInvasaoLoja(jogador));
		bufferLocal.addEntidade(ataque);
		mapa.add(bufferLocal);

		//Redes sociais
		bufferLocal= new Lugar("Redes Sociais");
		bufferLocal.addEntidade(new NPC("Dispersar virus", "Use seus virus para fazer computadores trabalharem para voce", new DialogoLancarVirus(jogador)));
		bufferLocal.addEntidade(new NPC("Atacar contas","Descubra senhas de pessoas importantes e use seus dados pessoais para ganhar seguidores", new DialogoInvasaoRedeSocial(jogador)));

		mapa.add(bufferLocal);
		
		//Governo br
		bufferLocal= new Lugar("Governo.BR");
		NPC aux=new NPC("Governo Federal","Objetivo Final");
		aux.setDialogo(new DialogoInvasaoGoverno(aux, jogador,5));
		bufferLocal.addEntidade(aux);
		
		aux=new NPC("Ministerio da Fazenda","Site estrategico e razoavelmente vulneravel");
		aux.setDialogo(new DialogoInvasaoGoverno(aux, jogador,3));
		bufferLocal.addEntidade(aux);
		
		aux=new NPC("Prefeitura do Rio de Janeiro","O alvo mais vulneravel");
		aux.setDialogo(new DialogoInvasaoGoverno(aux, jogador,1));
		bufferLocal.addEntidade(aux);
		
		mapa.add(bufferLocal);

		//adiciona adjacencias
		for (Lugar lc : mapa) {
			lugarAtual.addAdjacencia(lc);// tudo é adjacente a praca, que eh o localAtual em que se comeca
		}
	}
	
	public static void salvar() throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoSave));
		oos.writeObject(controll);
		oos.close();
	}
	public static ControleGeral recuperar() throws IOException, ClassNotFoundException{
		ControleGeral ctrl  = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoSave));
		ctrl = (ControleGeral)ois.readObject();
		ois.close();
		return ctrl;
	}
	public static ControleGeral getControle(){
		try {
			//tenta carregar um jogo salvo
			controll=recuperar();
		} catch (Exception e){
			//deu alguma exception, cria-se um jogo novo
			controll=new ControleGeral();
		}
		//inicia a thread de auto save
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true){
						//1000 milisegundos*15 segundos
						Thread.sleep(1000*15);
						ControleGeral.salvar();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		return controll;
	}
	
	public Collection<String> NomeDosLugaresAdjacentes(){
		//retorna uma lista de lugares adjacentes para a Vista
		ArrayList<String> str= new ArrayList<String>();
		for (Lugar l : lugarAtual.getAdjacencias()) {
			str.add(l.getNome());
		}
		return str;	
	}
	
	public void irLugar(String nomeLocal) throws LugarInvalidoException{
		for (Lugar l : lugarAtual.getAdjacencias()) {
			if (l.getNome().equalsIgnoreCase(nomeLocal)){
				lugarAtual=l;
				return;
			}

		}
		throw new LugarInvalidoException(nomeLocal);
	}
	
	public Collection<String> NomeDasEntidadesLocais(){
		//Retorna para a Vista as coisas que existem no local
		ArrayList<String> str= new ArrayList<String>();
		for (Entidade ent:lugarAtual.getCoisas()){
			str.add(ent.getNome());
		}
		return str;
	}
	public String nomeDoLugar(){
		return lugarAtual.getNome();
	}
	protected static Entidade procuraEntidadeNumaLista(String nomeEntidade, Collection<? extends Entidade> col) throws EntidadeInvalidaException{
		//so pra me facilitar
		for (Entidade e:col){
			if (e.getNome().equalsIgnoreCase(nomeEntidade))
				return e;
		}
		throw new EntidadeInvalidaException(nomeEntidade);
	}
	
	public String olharEntidade(String nomeEntidade) throws EntidadeInvalidaException{
		//Usado pela Vista para receber o texto da descricao da Entidade
		return procuraEntidadeNumaLista(nomeEntidade,lugarAtual.getCoisas()).olhar();
	}
	
	
	public ControleDialogo interagir(String nomeEntidade) throws EntidadeInvalidaException{
		//retorna para a vista um outro controlador, usado para dialogos
		Entidade aux= procuraEntidadeNumaLista(nomeEntidade, lugarAtual.getCoisas());
		if (aux instanceof NPC){
			NPC npc= (NPC) aux;
			return new ControleDialogo(npc.getDialogo());
		}
		else{
			return new ControleDialogo(new DialogoNulo(olharEntidade(nomeEntidade)));
		}
	}
}