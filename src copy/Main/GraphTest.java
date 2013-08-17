package Main;

/*
 * ***********************************************************************************
 *  Far unchecked varning pa class ListForm, (new JList)(addElement)bla dar jag har	*
 *  en class som hanterar Edges i JListan tillsammans med en DefaultListModel har   *
 *  star for hamtning av vald Edge, samtliga unchecked pa andra stallen ar lasta. 	* 
 *  Den klassen	gar inte att andra da den gar valdigt strangt kodade 				*
 *  saker, en unchecked	som oavsett vad jag castar till fortsatte komma tillbaka.	*
 *  vid addObject() etc och skapande av JList										*
 *  Vet inte om JAR processen fixar problemet men far aven <source><option> 		*
 *  pa Xlint: som jag inte lyckas lokaliser vad det kan vara.						*
 * 	Jag har aven en getter pa source hos Edge som jag anvande i borjan som		 	*
 *  referense vid testkorning.														*
 * 																					*																	
 * MehdiRahimi - mrk_187@hotmail.com					  							*																				  
 * 																					*			    																			     		 																					  
 * *********************************************************************************** */
import graphs.GraphMethods;
import graphs.ListGraph;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GraphTest extends JFrame 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar = new JMenuBar();

	private JMenu arMenu = new JMenu("Archive");
	private JMenu opMenu = new JMenu("Operations");

	private JMenuItem open = new JMenuItem("Open");
	private JMenuItem close = new JMenuItem("Close");

	private JMenuItem findPath = new JMenuItem("Find fastest path");
	private JMenuItem showRoute = new JMenuItem("Show connections");
	private JMenuItem newPlace = new JMenuItem("New node");
	private JMenuItem newRoute = new JMenuItem("New connection");
	private JMenuItem changeRoute = new JMenuItem("Change connection");

	private JButton findPathButton = new JButton("Find path");
	private JButton showRouteButton = new JButton("Show route");
	private JButton newPlaceButton = new JButton("New place");
	private JButton newRouteButton = new JButton("New route");
	private JButton changeRouteButton = new JButton("Change route");



	private Main.MapPanel mapP;
	private Circle cir = new Circle();
	private CircleLabel cl = new CircleLabel();
	private boolean opened = false;

	private OpenListener openListener = new OpenListener();
	private CloseListener closeListener = new CloseListener();
	private FindPathListener findPListener = new FindPathListener();
	private ShowRouteListener showRListener = new ShowRouteListener();
	private NewPlaceListener newPListener = new NewPlaceListener();
	private NewRouteListener newRListener = new NewRouteListener();
	private ChangeRouteListener changeRListener = new ChangeRouteListener();
	private MousListener mouseListener = new MousListener();
	private ListGraph<String> mapList = new ListGraph<String>();



	GraphTest() 
	{
		super("PathFinder");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(findPathButton);
		buttonPanel.add(showRouteButton);
		buttonPanel.add(newPlaceButton);
		buttonPanel.add(newRouteButton);
		buttonPanel.add(changeRouteButton);

		findPathButton.addActionListener(findPListener);
		showRouteButton.addActionListener(showRListener);
		newPlaceButton.addActionListener(newPListener);
		newRouteButton.addActionListener(newRListener);
		changeRouteButton.addActionListener(changeRListener);

		menuBar.add(arMenu);
		menuBar.add(opMenu);
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

		arMenu.add(open);
		arMenu.add(close);

		opMenu.add(newPlace);
		opMenu.add(newRoute);
		opMenu.add(showRoute);
		opMenu.add(findPath);
		opMenu.add(changeRoute);


		open.addActionListener(openListener);
		close.addActionListener(closeListener);

		findPath.addActionListener(findPListener);
		showRoute.addActionListener(showRListener);
		newPlace.addActionListener(newPListener);
		newRoute.addActionListener(newRListener);
		changeRoute.addActionListener(changeRListener);

		setJMenuBar(menuBar);
		add(buttonPanel, BorderLayout.NORTH);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(560, 60);
	}

	public static void main(String[] args) 
	{	
		
		GraphTest m = new GraphTest();
		m.pack();
		
		
	}

	Circle c1 = null, c2 = null;

	class OpenListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JFileChooser fc = new JFileChooser(
					"/Users/mehdirahimi/Documents/workspace/GPSInlupp2/");
			fc.setDialogTitle("Select map");
			fc.setAcceptAllFileFilterUsed(false);
			fc.setFileFilter(new Filter());
			int returnValue = fc.showOpenDialog(GraphTest.this);

			if (returnValue == JFileChooser.APPROVE_OPTION) 
			{
				if(opened){
					mapP.reset();
					mapList.clearAll();
					c1 = null; c2=null;
					newPlaceButton.setSelected(false);
				}
				
				File file = fc.getSelectedFile();
				mapP = new MapPanel(file.getAbsolutePath());
				add(mapP, BorderLayout.CENTER);
				setSize(600, 450);
				opened = true;
			}
		}
	}

	class CloseListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			System.out.println("working on close");
			System.exit(0);
		}
	}

	class FindPathListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{

			if(c1!=null && c2!=null)
			{

				ListForm textForm = new ListForm(c1, c2);
				ArrayList<String> str = new ArrayList<String>();
				ArrayList<Edge<String>> path = GraphMethods.getShortestPath(mapList, c1.getName(), c2.getName());

				str= new ArrayList<String>();
				int total = 0;
				try{

					for(int i=0; i<path.size(); i++)
					{
						str.add( path.get(i).getName() + " to " + path.get(i).getDestination() + "("+path.get(i).getWeight() +")");
						total += path.get(i).getWeight();
					}
				}catch (NullPointerException nop)
				{
					JOptionPane.showMessageDialog(null, "There is no route between these nodes");	
					return;
				}
				str.add("			");
				str.add("Total "+ total);
				textForm.addObject(str);


				Object[] options = {"OK"};
				int n = JOptionPane.showOptionDialog(GraphTest.this,textForm, "Show path", 
						JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
				if(n != JOptionPane.OK_OPTION)
					return;
			}else
				JOptionPane.showMessageDialog(null, "You have to choose from and to destination for this option");



		}
	}

	class ShowRouteListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(c1!=null && c2!=null)
			{
				if(mapList.getEdgesBetween(c1.getName(), c2.getName()).size()==0){
					JOptionPane.showMessageDialog(null, "There is no connection between these nodes");
					return;
				}
				ListForm list = new ListForm(c1, c2);
				list.addObject(mapList.getEdgesBetween(c1.getName(),c2.getName()));
				try
				{
					Object[] options = {"OK"};
					int n = JOptionPane.showOptionDialog(GraphTest.this,
							list,"Show one stop routes", JOptionPane.PLAIN_MESSAGE,
							JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);

					if(n != JOptionPane.OK_OPTION)
						return;

				}
				catch (NullPointerException eva)
				{
					JOptionPane.showMessageDialog(null, "Edge is not available or not added try (re)adding edge");
				}
				catch (NumberFormatException eve)
				{
					JOptionPane.showMessageDialog(GraphTest.this,
							"Oops! SomethingWhent wrong.", "Error message",
							JOptionPane.ERROR_MESSAGE);
				}

			}else
				JOptionPane.showMessageDialog(GraphTest.this, "You have to choose two nodes to show route");
		}
	}

	class NewPlaceListener extends MouseAdapter implements ActionListener 
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if (opened) 
			{
				if (!newPlaceButton.isSelected())
				{
					newPlaceButton.setSelected(true);
					newPlace.setSelected(true);
				}
				else
				{
					newPlaceButton.setSelected(false);
					newPlace.setSelected(false);
					mapP.setCursor(null);
				}
			}else
				JOptionPane.showMessageDialog(null, "You have to load in an image first");

			if (newPlaceButton.isSelected()) 
			{
				mapP.addMouseListener(this);
			} 
			else
			{
				mapP.removeMouseListener(this);
			}
		}
		public void mouseClicked(MouseEvent e) {
			for(;;){
				try {
					String nameLabel = null;
					nameLabel = JOptionPane.showInputDialog(GraphTest.this,
							"What is the name of the city", "Add city",
							JOptionPane.QUESTION_MESSAGE);


					if (nameLabel.length()!=0) 
					{
						cir = new Circle(nameLabel, e.getX() - 12, e.getY() - 12);
						cl = new CircleLabel(nameLabel, e.getX() - 31, e.getY() - 36);
						mapList.add(nameLabel);
						mapP.add(cir);
						mapP.add(cl);

						cir.addMouseListener(mouseListener);
						repaint();
						return;
					}else
						return;
				}
				catch (NullPointerException eve) 
				{
					return;
				} 
				catch (IllegalArgumentException eva) 
				{
					JOptionPane.showMessageDialog(GraphTest.this, "This name is taken");
					return;
				}
			}
		}
		public void mouseEntered(MouseEvent e) 
		{
			if(newPlaceButton.isSelected())
				mapP.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			else
				mapP.setCursor(null);	
		}
	}

	class ChangeRouteListener implements ActionListener 
	{
		Edge<?> edge;
		Form form;

		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent arg0) 
		{
			if(c1!=null && c2!=null){
				ListForm list = new ListForm(c1, c2);
				list.addObject(mapList.getEdgesBetween(c1.getName(),c2.getName()));

				for(;;){
					try{		
						if(mapList.getEdgesBetween(c1.getName(), c2.getName()).size()==0){
							JOptionPane.showMessageDialog(null, "There is no connection between these nodes");
							return;
						}
						else if(mapList.getEdgesBetween(c1.getName(), c2.getName()).size()==1){
							edge = mapList.getEdgesBetween(c1.getName(), c2.getName()).get(0);
							form = new Form(c1, c2);
							form.nField.setText(edge.getName());
							form.nField.setEditable(false);
							try{
								int svar = JOptionPane.showConfirmDialog(GraphTest.this, form, 
										"Change route", JOptionPane.OK_CANCEL_OPTION, 
										JOptionPane.QUESTION_MESSAGE);
								if(svar!=JOptionPane.OK_OPTION){
									return;
								}else if(svar == JOptionPane.OK_OPTION){
									int newTime = form.getTime();
									edge.setWeight(newTime);
									return;
								}else{
									return;
									}

							}catch (NumberFormatException eve){
								JOptionPane.showMessageDialog(GraphTest.this,
										"Oops! Try typing numbers.", "Error message",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						int n = JOptionPane.showConfirmDialog(GraphTest.this,
								list,"Show route",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if(n != JOptionPane.OK_OPTION)
							return;
						else if(n == JOptionPane.OK_OPTION && !list.list.isSelectionEmpty()) 
						{

							edge = (Edge)list.list.getSelectedValue();
							list.getObject(mapList.getEdgesBetween(c1.getName(),c2.getName()), edge );
							form = new Form(c1, c2);
							form.nField.setText(edge.getName());
							form.nField.setEditable(false);

							try{
								int svar = JOptionPane.showConfirmDialog(GraphTest.this, form, 
										"Change route", JOptionPane.OK_CANCEL_OPTION, 
										JOptionPane.QUESTION_MESSAGE);
								if(svar!=JOptionPane.OK_OPTION){
									list.addObject(mapList.getEdgesBetween(c1.getName(),c2.getName()));
								}else if(svar == JOptionPane.OK_OPTION){
									int newTime = form.getTime();
									edge.setWeight(newTime);
									list.addObject(mapList.getEdgesBetween(c1.getName(),c2.getName()));
								}else{
									list.addObject(mapList.getEdgesBetween(c1.getName(),c2.getName()));
								}

							}catch (NumberFormatException eve){
								JOptionPane.showMessageDialog(GraphTest.this,
										"Oops! Try typing numbers.", "Error message",
										JOptionPane.ERROR_MESSAGE);
							}

						}else
							return;
					}catch (Exception eva)
					{
						JOptionPane.showMessageDialog(null, "Unsuspected error has accured please try again");
					}
				}
			}else
				JOptionPane.showMessageDialog(null, "You have to choose from and to destination for this option");
		}
	}
	class NewRouteListener implements ActionListener 
	{
		Form form;
		Graphics g;

		public void actionPerformed(ActionEvent arg0) 
		{
			if (c1 != null && c2 != null) 
			{
				form = new Form(c1, c2);
				try {
					int svar = JOptionPane.showConfirmDialog(GraphTest.this,
							form, "New route", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (svar != JOptionPane.OK_OPTION)
						return;
					else {
						String n = form.getName();
						int w = form.getTime();
						mapList.connect(c1.getName(), c1.getCircle().getName(), 
								c2.getCircle().getName(), n, w);

					}
				} catch (NumberFormatException eve) {
					JOptionPane.showMessageDialog(GraphTest.this,
							"Oops! Try using numbers.", "Error message",
							JOptionPane.ERROR_MESSAGE);
				}
			}else
				JOptionPane.showMessageDialog(null, "You have to choose two nodes to add connection");
		}
	}

	public class MousListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			Circle b = (Circle) mev.getSource();
			if (!b.state() && c1 == null) {
				c1 = b;
				b.setChoosen(true);
			} else if (!b.state() && c2 == null && b != c1) {
				c2 = b;
				b.setChoosen(true);
			} else if ((Circle) mev.getSource() == c1) {
				b.setChoosen(false);
				c1 = null;
			} else if ((Circle) mev.getSource() == c2) {
				b.setChoosen(false);
				c2 = null;
			}else{
				JOptionPane.showMessageDialog(null, "You can choose two nodes at a time");
			}			
		}
	}
}//GraphTest

