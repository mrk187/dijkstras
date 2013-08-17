package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Main.Edge;




public abstract class GraphMethods<T> implements Graph<T>{



	//Indata ar startnoden och slutnoden
	public static <T> ArrayList<Edge<T>> getShortestPath(Graph<T> g, T from, T to){
		Set<T> visited = new HashSet<T>();
		Map<T, Integer> distance = new HashMap<T, Integer>();
		Map<T, Boolean> settled = new HashMap<T, Boolean>();
		Map<T, T> predec = new HashMap<T, T>();
		List<T> nodes = new ArrayList<T>();
		ArrayList<Edge<T>> edges = new ArrayList<Edge<T>>();
		//Kontrollera att det finns en stig mellan startnoden och slutnoden.	
		pathExists(g, from, to, visited);

		if(!visited.contains(to))
			return null;
		//Skapa en "tabell" - for varje nod: kortaste tiden hittills (Integer),
		//om tiden är bestämd (Boolean) och vilken nod man kommer fron

		for(T node : visited){
			distance.put(node, Integer.MAX_VALUE);
			settled.put(node, false);
			predec.put(node, null);
		}
		distance.remove(from);
		distance.put(from, 0);
		settled.remove(from);
		settled.put(from, true);


		execute(g, from, to, distance, settled, predec, visited);
		getPath(g, nodes, predec, from, to);

		return getEdges(g, nodes, edges, predec, to);
	}





	private static <T> void depthSearch(Graph<T> g, T from, Set<T> visited){
		visited.add(from);
		for(Edge<T> e : g.getEdgesFrom(from)){
			T to = e.getDestination();
			if(!visited.contains(to))
				depthSearch(g, to, visited);
		}
	}
	public static <T> boolean pathExists(Graph<T> g, T from, T to, Set<T> visited){
		//Set<T> visited = new HashSet<T>();
		depthSearch(g, from, visited);
		return visited.contains(to);
	}



	private static <T> void execute(
			Graph<T> g, T from, T to, 
			Map<T, Integer> distance, 
			Map<T, Boolean> settled, 
			Map<T,T> predec , Set<T> visited){
		while(!settled.get(to)){


			for(Edge<T> me : g.getEdgesFrom(from)){
				int param = distance.get(from);
				if(param==Integer.MAX_VALUE)
					param=0;
				param+=me.getWeight();

				if(param < distance.get(me.getDestination())){
					distance.remove(me.getDestination());
					distance.put(me.getDestination(), param);
					predec.remove(me.getDestination());
					predec.put(me.getDestination(), from);
				}
			}
			T minimumNode = null;
			int minimum = Integer.MAX_VALUE;
			for(Map.Entry<T, Boolean> me : settled.entrySet()){
				if(!me.getValue()){
					T node = me.getKey();
					if(distance.get(node)<minimum){
						minimum = distance.get(node);
						minimumNode = node;
					}
				}
			}
			settled.put(minimumNode, true);
			T temp = minimumNode;
			execute(g, temp, to, distance, settled, predec, visited);
		}
	}
	private static <T> void getPath(Graph<T> g,
			List<T> nodes,
			Map<T,T> predec, 
			T from, T to){
		T node;
		while(!to.equals(from)){
			if(predec.containsKey(to)){
				nodes.remove(to);
				node = predec.get(to);
				nodes.add(to);
				nodes.add(node);
				to = node;
			}
		}
	}
	private static <T> ArrayList<Edge<T>> getEdges(Graph<T> g, 
			List<T> nodes, List<Edge<T>> edges, Map<T, T> predec, T target){
		LinkedList<T> pathNodes = new LinkedList<T>();

		T step = target;
		if(predec.get(step)==null){
			return null;
		}
		pathNodes.add(step);
		while(predec.get(step)!=null){
			step = predec.get(step);
			pathNodes.add(step);
		}

		for(int i=0; i<nodes.size()-1; i++)
		{

			edges.add(g.getRealEdge(nodes.get(i+1), nodes.get(i) ));

		}
		Collections.reverse(edges);
		return (ArrayList<Edge<T>>) edges;
	}
}

