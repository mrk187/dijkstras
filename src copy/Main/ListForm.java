package Main;
import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;



public class ListForm extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3158367686606020024L;
	public DefaultListModel listModel = (DefaultListModel)new DefaultListModel();
	public JList list = new JList(listModel);

	public ListForm(Circle c1, Circle c2){
		listModel = new DefaultListModel();
		list = new JList(listModel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel center = new JPanel();
		add(new JLabel("From "+c1.getName() + " to "+ c2.getName()));
		add(center, BorderLayout.CENTER);
		center.add(new JScrollPane(list));
		list.setVisibleRowCount(6);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	public <T> void addObject(List<Edge<T>> list1){
		listModel.clear();
		for(int i=0; i<list1.size(); i++){
			listModel.addElement((Edge<T>)list1.get(i));
			listModel.toString();
		}	
	}

	public <T> void addObject(ArrayList<String> list1){
		listModel.clear();
		for(int i=0; i<list1.size(); i++){
			listModel.addElement(list1.get(i));
			listModel.toString();
		}	
	}
	public <T> Edge<T> getObject(List<Edge<T>> edges, @SuppressWarnings("rawtypes") Edge edge2) {
		listModel.clear();
		Edge<T> edge=null;
		for(Edge<T> e : edges)
			if(e.equals(edge2)){
				edge=e;
			}
		return edge;
	}
}


