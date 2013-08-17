package Main;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Form extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField nField = new JTextField(15);
	JTextField vField = new JTextField(5);
	public Form(){}

	public Form(Circle c1, Circle c2) 
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel rad0 = new JLabel("From " + c1.getName() + " till "+ c2.getName());
		add(rad0);

		JPanel rad1 = new JPanel();
		rad1.add(new JLabel("Name of route"));
		rad1.add(nField);
		add(rad1);

		JPanel rad2 = new JPanel();
		rad2.add(new JLabel("Time"));
		rad2.add(vField);
		add(rad2);



	}
	public void setName(String name)
	{
		nField.setText(name);
		nField.setEditable(false);
	}
	public String getName() 
	{
		return nField.getText();
	}

	public int getTime() 
	{
		return Integer.parseInt(vField.getText());
	}


}