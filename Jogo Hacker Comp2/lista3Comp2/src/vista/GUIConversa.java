package vista;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

import controle.*;

public class GUIConversa extends JPanel {
	private static final long serialVersionUID = 1L;
	private ControleDialogo cd;

	public GUIConversa(ControleDialogo cd) throws HeadlessException {
		this.cd = cd;
		telaConversa();
	}
	
	private void telaConversa(){
		this.removeAll();
		this.setLayout(new GridLayout(0,1));
		JTextArea fala = new JTextArea(cd.falaInicial());
		
		fala.setEditable(false);
		
		this.add(fala);

		for (String str : cd.opcoes()) {
			JButton bt= new JButton(str);
			bt.addMouseListener(new OuvinteBotao());
			this.add(bt);
		}
		JTextArea resp = new JTextArea(cd.getUltimaFala());
		resp.setEditable(false);
		this.add(resp);
		this.updateUI();
	}
	class OuvinteBotao extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JButton bt = (JButton) arg0.getSource();
			if (cd.interagir(bt.getText()) == null){
		
				GUIInicial.C.show(GUIInicial.panelEntidades2, "1");
			}
			telaConversa();
		}
	}
}