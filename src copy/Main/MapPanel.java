package Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MapPanel extends JPanel {
	private Image bg;
	
	public MapPanel() {
	}

	public MapPanel(String i) {
		bg = Toolkit.getDefaultToolkit().getImage(i);
		setLayout(null);
		
		

	}
	
	public void reset(){
		bg = null;
		setVisible(false);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth() - 10, getHeight() - 10, this);
	}
}