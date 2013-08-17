package Main;
public class Edge<T>{
	private T source;
	private T destination; 
	private String name;
	private int weight;


	public Edge(T source, T destination, String name, int weight){
		if(weight<0)
			throw new IllegalArgumentException("Negativ weight for edge");
		this.source=source;
		this.destination=destination;
		this.name=name;
		this.weight=weight;
	}

	public String getName(){
		return name;
	}
	public T getSource(){
		return source;
	}
	public T getDestination(){
		return destination;
	}
	public int getWeight(){
		return weight;
	}
	public void setWeight(int weight){
		this.weight=weight;
	}
	/*	public String toStringSpecial(){
				return name + " to " + destination + "("+weight+")";

			}*/
	public String toString(){
		return  "To " +destination+ " with " +name + " takes " + weight;
	}

	public String toStringSpecial() {
		// TODO Auto-generated method stub
		return null;
	}

}


