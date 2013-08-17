package graphs;
import java.util.ArrayList;
import java.util.List;

import Main.Edge;



public interface Graph<U>{

	abstract void add(U nw);
	abstract ArrayList<U> getNodes();
	abstract void connect(U source, U from, U to, String name, int weight);
	abstract List<Edge<U>>  getEdgesBetween(U s1, U s2);
	abstract List<String>  getEdges(U s1, U s2);
	abstract List<Edge<U>> getEdgesFrom(U from);
	abstract Edge<U> getEdge(Graph<U> g, U from, U to, String name);
	abstract void add(Graph<U> list);
	abstract ArrayList<Edge<U>> getAllEdges();
	abstract Edge<U> getRealEdge(U from, U to);
}