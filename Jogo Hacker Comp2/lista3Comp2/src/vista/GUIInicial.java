package vista;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controle.*;

public class GUIInicial extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ControleGeral cg;
	private JPanel panelLugares;
	private JPanel panelEntidades;
	static JPanel panelEntidades2;
	private JPanel auxiliar;
	private JLabel descricao;
	private JButton salvar;
	static CardLayout C;

	public GUIInicial() throws HeadlessException {
		super("Jogo");
		super.setSize(700, 430);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setVisible(true);
		super.setLayout(new BorderLayout());

		cg= ControleGeral.getControle();

		C = new CardLayout();
		
		auxiliar = new JPanel(new BorderLayout());
		
		panelLugares= new JPanel(new FlowLayout());

		panelEntidades= new JPanel(new FlowLayout());

		panelEntidades2= new JPanel(C);
		
		auxiliar.add(panelLugares, BorderLayout.NORTH);
		auxiliar.add(panelEntidades2, BorderLayout.CENTER);

		descricao = new JLabel("", JLabel.CENTER);

		salvar= new JButton("Salvar");
		salvar.addMouseListener(new OuvinteBotaoSalvar());

		panelEntidades2.add(panelEntidades, "1");
	
		add(salvar, BorderLayout.SOUTH);
		add(auxiliar, BorderLayout.NORTH);
		add(descricao, BorderLayout.CENTER);
		
		
		atualizaEntidades();
		atualizaLugares();
	}
	
	
	private void atualizaEntidades(){
		panelEntidades.removeAll();
		for (String str : cg.NomeDasEntidadesLocais()) {
			JButton bt= new JButton(str);
			bt.addMouseListener(new OuvinteBotaoInteracao());
			panelEntidades.add(bt);
		}
		panelEntidades.updateUI();
		repaint();
	}
	private void atualizaLugares(){
		panelLugares.removeAll();
		panelLugares.add(new JLabel(cg.nomeDoLugar(),JLabel.CENTER));

		for (String str : cg.NomeDosLugaresAdjacentes()) {
			JButton bt= new JButton(str);
			bt.addMouseListener(new OuvinteBotaoLugar());
			panelLugares.add(bt);
		}
		panelLugares.updateUI();
		repaint();
	}


	class OuvinteBotaoLugar extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton bt = (JButton) arg0.getSource();
			cg.irLugar(bt.getText());
			atualizaLugares();
			atualizaEntidades();
			C.show(panelEntidades2, "1");
		
		}
	}
	class OuvinteBotaoSalvar extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				ControleGeral.salvar();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	class OuvinteBotaoInteracao extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton bt = (JButton) arg0.getSource();
			try {
				GUIConversa Con = new GUIConversa(cg.interagir(bt.getText()));
				panelEntidades2.add(Con, "2");
				C.show(panelEntidades2, "2");


			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton bt = (JButton) e.getSource();
			descricao.setText(cg.olharEntidade(bt.getText()));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			descricao.setText("");
		}

	}

	public static void main(String args[]) throws HeadlessException, FileNotFoundException, IOException{
		new GUIInicial();
	}
}