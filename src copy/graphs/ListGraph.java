package graphs;

import java.util.*;
import java.util.Map.Entry;

import Main.Edge;



public class ListGraph<T> implements Graph<T>{
	CustomComparator customComparator = new CustomComparator();
	public ListGraph(){}

	private Map<T,List<Edge<T>>> nodes = new HashMap<T, List<Edge<T>>>();

	public boolean isEquals(T lhs, int i){
		return nodes.get(i).equals(lhs);
	}

	public void add(T ny){
		if(nodes.containsKey(ny))
			throw new IllegalArgumentException("Stad finns vid add");

		nodes.put(ny, new ArrayList<Edge<T>>());
	}		
	//Copy of existing collections of elements City precaution
	/*
		public Set<T> getNodes(){
			return new HashSet<T>(nodes.keySet());		
		}
	 */	
	public ArrayList<T> getNodes(){
		Iterator<Entry<T, List<Edge<T>>>> it = nodes.entrySet().iterator();
		ArrayList<T> allNodes = new ArrayList<T>();
		while(it.hasNext()){
			Entry<T, List<Edge<T>>> pairs = it.next();
			allNodes.add(pairs.getKey());
		}
		return allNodes;
	}

	public void connect(T source, T from, T to, String name, int v){
		List<Edge<T>> fList = nodes.get(from);
		List<Edge<T>> tList = nodes.get(to);
		Edge<T> e1 = new Edge<T>(source, to, name, v);
		fList.add(e1);
		Edge<T> e2 = new Edge<T>(source, from, name, v);
		tList.add(e2);
	}



	public List<Edge<T>> getEdgesBetween(T from, T to){
		ArrayList<Edge<T>> list = new ArrayList<Edge<T>>();
		for(Edge<T> e : nodes.get(from)){
			if(e.getDestination().equals(to))
				list.add(e);
		}
		return list;
	}
	public List<String> getEdges(T from, T to){
		ArrayList<String> list = new ArrayList<String>();
		for(Edge<T> e : nodes.get(from)){
			if(e.getDestination().equals(to))
				list.add(e.toStringSpecial());
		}
		return list;
	}
	public Edge<T> getRealEdge(T from, T to){
		ArrayList<Edge<T>> list = new ArrayList<Edge<T>>();
		for(Edge<T> e : nodes.get(from)){
			if(e.getDestination().equals(to)){
				list.add(e);

			}
		}
		Collections.sort(list, customComparator);
		return list.get(0);

	}

	public Edge<T> getEdge(Graph<T> g, T from, T to, String name){
		for(Edge<T> e : nodes.get(from)){
			if(e.getDestination().equals(to) && e.getName().equals(name))
				return e;
		}
		return null;		
	}
	public void setConnectionWeight(T from, T to, String name, int weight){
		for(Edge<T> e : nodes.get(from)){
			if(e.getDestination().equals(to) && e.getName().equals(name)){
				e.setWeight(weight);
			}
		}
	}
	public List<Edge<T>> getEdgesFrom(T from){
		return new ArrayList<Edge<T>>(nodes.get(from));
	}
	public ArrayList<Edge<T>> getAllEdges(){
		Iterator<Entry<T, List<Edge<T>>>> it = nodes.entrySet().iterator();
		ArrayList<Edge<T>> all = new ArrayList<Edge<T>>();
		while(it.hasNext()){
			Entry<T, List<Edge<T>>> pairs = it.next();
			List<Edge<T>> edges = pairs.getValue();
			for(Edge<T> edge : edges){
				all.add(edge);
			}
		}
		return all; 
	}

	public void clearAll(){
		nodes.clear();
	}
	
	public String toString(){
		String str= "";
		for( Map.Entry<T, List<Edge<T>>> me : nodes.entrySet())
			str += "\n" + me.getKey() + ": "+ me.getValue();
		return str;
	}
	@Override
	public void add(Graph<T> list) {
		// TODO Auto-generated method stub

	}
	public class CustomComparator implements Comparator<Edge<T>>{

		@Override
		public int compare(Edge<T> e1, Edge<T> e2) {
			// TODO Auto-generated method stub
			return e1.getWeight() - (e2.getWeight());
		}
	}
	
}



