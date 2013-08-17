package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

	@SuppressWarnings("serial")
	public class CircleLabel extends JLabel {
		String n;
		Font font = new Font(Font.SERIF, Font.BOLD, 15); 
		
		public CircleLabel() {
		}

		public CircleLabel(String n, int x, int y) {
			this.n = n;
			setBounds(x, y, 100, 30);
			setForeground(Color.BLACK);
			setOpaque(false);
			
			

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				g.setFont(font);
				g.drawString(n, 24, 24);
				g.setColor(Color.GRAY);
			} catch (NullPointerException eve) {
			}
		}
	}