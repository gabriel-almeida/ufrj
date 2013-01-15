package modelo;

import java.util.*;

import controle.SemDinheiroSuficienteException;
import controle.NumeroNegativoException;
import controle.SemItemSuficienteException;

public class Personagem extends Entidade{
	private static final long serialVersionUID = 1L;
	private Map<Item,Integer> inventario;
	private int dinheiro;
	public Personagem(String nome, String descricao) {
		super(nome, descricao);
		inventario= new HashMap<Item,Integer>();
	}
	public Map<Item, Integer> getInventario() {
		return inventario;
	}
	private int nivelDeStatus(int categoria){
		int nivel=0;
		for (Item i:inventario.keySet()){
			if (i instanceof Livro){
				Livro l=(Livro) i;
				if (l.getCategoria()==categoria){
					nivel+=l.getNivel();	
				}
			}
		}
		return nivel;
	}
	public int nivelProgramacao(){
		return nivelDeStatus(Livro.PROGRAMACAO);
	}
	public int numeroSeguidores(){
		return inventario.get(FabricaItem.SEGUIDORES);
	}
	public int nivelRedes(){
		return nivelDeStatus(Livro.REDE);
	}
	public int nivelSI(){
		return nivelDeStatus(Livro.SI);
	}
	public void setInventario(Map<Item, Integer> inventario) {
		this.inventario = inventario;
	}
	public int getDinheiro() {
		return dinheiro;
	}
	public void diminuirDinheiro(int quantidade) throws SemDinheiroSuficienteException{
		if (this.dinheiro<quantidade) throw new SemDinheiroSuficienteException(this);
		this.dinheiro-=quantidade;
	}
	public void aumentarDinheiro(int quantidade){
		this.dinheiro+=quantidade;
	}
	public static void venda(Personagem comprador, Personagem vendedor,Item i, int quantidade, int precoUnidade) throws SemDinheiroSuficienteException, SemItemSuficienteException, NumeroNegativoException {
		//chamado por dialogos dos NPCs, remove item ou lanca exception
		if (comprador.getDinheiro()<i.getPreco()*quantidade) throw new SemDinheiroSuficienteException(comprador);
		//tenta enviar o item pro comprador, senao der, vai lançar exception
		enviarItem(vendedor, comprador, i, quantidade);
		//adiciona dinheiro pro vendedor e diminui do comprador
		comprador.diminuirDinheiro(quantidade*precoUnidade);
		vendedor.aumentarDinheiro(quantidade*precoUnidade);
	}
	public static void venda(Personagem comprador, Personagem vendedor,Item i, int quantidade) throws SemDinheiroSuficienteException, SemItemSuficienteException, NumeroNegativoException {
		venda(comprador,vendedor, i, quantidade,i.getPreco());
	}
	
	public void darItem(Item i, int q) throws NumeroNegativoException{
		//chamado por dialogos dos NPCs, da o item ou lanca exception
		if (q<0) throw new NumeroNegativoException(q);
		Map<Item,Integer> inventario =	this.getInventario();
		if (inventario.containsKey(i)){
			q += inventario.get(i);
			inventario.remove(i);
		}
		inventario.put(i, q);
	}
	public void tirarItem(Item i, int q) throws NumeroNegativoException, SemItemSuficienteException{
		//chamado por dialogos dos NPCs, remove item ou lanca exception
		if (q<0) throw new NumeroNegativoException(q);
		int quantidadeAtual=inventario.get(i);
		if (quantidadeAtual<q) throw new SemItemSuficienteException(this);
		
		quantidadeAtual-=q;
		inventario.remove(i);
		if (quantidadeAtual>0){
			inventario.put(i, quantidadeAtual);	
		}
	}
	public static void enviarItem(Personagem remetente, Personagem destinatario, Item i, int quantidade) throws SemItemSuficienteException, NumeroNegativoException{
		if (quantidade<0) throw new NumeroNegativoException(quantidade);
		//da o item pro destinatario e tira do remetente, lanca exception senao tem o item
		remetente.tirarItem(i,quantidade);
		destinatario.darItem(i, quantidade);
		
	}
}
