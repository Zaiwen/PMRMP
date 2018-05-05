package DataStructure;

public  class Edge{
	public int u;    /*边的射出*/
	public int v;    /*边的射入结点*/
	public Type type;
	public String label;
	
	public Edge(int u, int v){
		this.u = u;
		this.v = v;
		type = null;
		label = null;
	}
	
	public Edge(int u, int v, Type type, String label){
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}
}