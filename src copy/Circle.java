
import java.awt.*;
import javax.swing.JComponent;


public class Circle extends JComponent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean choosen = false;
	private String name = null;
	//private GraphTest.MousListener mousListener = new MousListener();

	public Circle(){}
	
	public Circle(String name) 
	{
		this.name=name;
	}

	public Circle(String name, int x, int y) 
	{
		this.name = name;
		setBounds(x, y, 30, 30);
		setMaximumSize(new Dimension(30, 30));
		setPreferredSize(new Dimension(30, 30));
		setMinimumSize(new Dimension(30, 30));
		// setBorderPainted(true);
		// setContentAreaFilled(true);
		//addMouseListener(mouseListener);

	}

	public String getName() 
	{
		return name;
	}
	public Circle getCircle()
	{
		return this;
	}

	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if (choosen) {
			g.setColor(Color.RED);
			g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
		} else {
			g.setColor(Color.BLUE);
			g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
		}

	}

	public void setChoosen(Boolean what) 
	{
		choosen = what;
		repaint();
	}

	public boolean state() 
	{
		return choosen;
	}

}