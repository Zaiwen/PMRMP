package DataStructure;
import java.util.*;
public class BPG implements Graph{
	public List<Vertice> vertices; // Store vertices
	protected List<List<Integer>> neighbors;  // Adjacency lists
	protected List<Edge> edges;
	public HashSet<Integer>[] Lla;    /*Lla[v] represents all the subsequent vertices of vertice v
											with the task type */
	public HashSet<Integer>[] Llb;    
	/*public boolean[] mapped;*/    /*mapped[i] represents whether the vertex i has been mapped
	 								into the optimal equivalence mapping*/
	/*Construct a graph from edges and vertices stored in arrays */
	protected BPG(int[][] edges, Vertice[] vertices){
		this.vertices = new ArrayList<Vertice>();
		for(int i = 0; i< vertices.length; i++)
			this.vertices.add(vertices[i]);
		createAdjacencyLists(edges, vertices.length);
	}
	
	/*Construct a graph for integer vertices 0, 1, 2 and edge list*/
	protected BPG(List<Edge> edges, int  numberOfVertices){
		vertices = new ArrayList<Vertice>();  //Create vertices
		this.edges = edges;
		for(int i = 0; i<numberOfVertices; i++)
			vertices.add(new Vertice(i));  // vertices is {0,1, ...}
			
		createAdjacencyLists(edges, numberOfVertices);
	}
	
	/*Construct a graph from edges and vertices stored in List*/
	/*And we usually use this constructor to deal with our business process problems*/
	public BPG(List<Edge> edges, List<Vertice> vertices){
		this.vertices = vertices;
		this.edges = edges;
		createAdjacencyLists(edges, vertices.size());
		Lla = new HashSet[vertices.size()];
		Llb = new HashSet[vertices.size()];
		//mapped = new boolean[vertices.size()];
		for(int i = 0; i<vertices.size(); i++){
			Lla[i] = new HashSet<Integer>();
			Llb[i] = new HashSet<Integer>();
			//mapped[i] = false;
		}
		Tree bfsTree = bfs(0);
		getLla(bfsTree);
		getLlb(bfsTree);
	}
	
	/*Construct a graph from integer vertices 0,1, and edge array*/
	protected BPG(int[][] edges, int numberOfVertices){
		vertices = new ArrayList<Vertice>();
		for(int i = 0; i < numberOfVertices; i++)
			vertices.add(new Vertice(i));  
		
		createAdjacencyLists(edges, numberOfVertices);
	}
	
	/*Create adjacency lists for each vertex*/
	private void createAdjacencyLists(int[][] edges, int numberOfVertices){
		neighbors = new ArrayList<List<Integer>>();
		for(int i = 0; i<numberOfVertices; i++)
			neighbors.add(new ArrayList<Integer>());
		
		for(int i = 0; i<edges.length; i++){
			int u = edges[i][0];
			int v = edges[i][1];
			
			neighbors.get(u).add(v);
		}
	}
	
	/*Create adjacency lists for each vertex*/
	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices){
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i<numberOfVertices; i++)
			neighbors.add(new ArrayList<Integer>());
		
		for(Edge edge: edges){
			neighbors.get(edge.u).add(edge.v);
		}
	}
	
	/*Return the number of vertices in the graph*/
	public int getSize(){
		return vertices.size();
	}
	
	/*Return the vertices in the graph*/
	public List<Vertice> getVertices(){
		return vertices;
	}
	
	public List<Edge> getEdges(){
		return edges;
	}
	
	public int getNumberOfCertainType(Type t){
		int number = 0;
		
		for(Vertice v: vertices){
			if(v.type == t)
				number++;
		}
		return number;
	}
	
	public List<Vertice> getVerticesOfType(Type t){
		List<Vertice> vl = new ArrayList<Vertice>();
		
		for(Vertice v: vertices){
			if(v.type == Type.Task)
				vl.add(v);
		}
		
		return vl;
	}
	/*Return the object for the specified vertex*/
	public int getIndex(Vertice v){
		return vertices.indexOf(v);
	}
	
	/*Return the neighbors of vertex with the specified index*/
	public List<Integer> getNeighbors(int index){
		return neighbors.get(index);
	}
	
	public Vertice getVertex(int index) {
		return vertices.get(index);
	}
	
	public void showVertex(int index){
		System.out.println("The vertex" + index +": ");
		System.out.println("τ = " + getVertex(index).type);
		System.out.println("λ = " + getVertex(index).label);
	}
	
	/*Get the look-ahead link of each vertice with the task type in the graph*/
	public void getLla(BPG.Tree bfsTree){
		//Tree bfsTree = this.bfs(0);
		LinkedList<Vertice> queue = new LinkedList<Vertice>();
		Vertice v;
		//int j = 0;
		boolean[] isVisited = new boolean[vertices.size()];
		
		//this.Lla = new ArrayList<Set<Integer>>();
		//Lla = new HashSet[vertices.size()];
		
		for(int i = 0; i < vertices.size(); i++)
		{
			if(vertices.get(i).type == Type.Task){
			for(int k = 0; k<vertices.size(); k++)    /*Initialize the isVisited[] for each
														round of searching for children*/
				isVisited[k] = false;
			//Lla[i] = new HashSet<Integer>();
			
				queue.offer(vertices.get(i));
				while(!queue.isEmpty()){
					v = queue.poll();
					for(Integer index: bfsTree.children[v.No]){
						if(!isVisited[index]){
							if(vertices.get(index).type == Type.Task)
								Lla[i].add(index);
							isVisited[index] = true;
							queue.offer(vertices.get(index));
						}
					}
				}
			}
		}
	}
	
	/*Get the look-back link of each vertice with task type in the graph*/
	public void getLlb(BPG.Tree bfsTree){
		
		LinkedList<Vertice> queue = new LinkedList<Vertice>();
		Vertice v;
		
		boolean[] isVisited = new boolean[vertices.size()];
		
		for(int i = 0; i < vertices.size(); i++)
		{
			if(vertices.get(i).type == Type.Task){
			for(int k = 0; k<vertices.size(); k++)    /*Initialize the isVisited[] for each
														round of searching for parents*/
				isVisited[k] = false;
			
				queue.offer(vertices.get(i));
				while(!queue.isEmpty()){
					v = queue.poll();
					for(Integer index: bfsTree.parents[v.No]){
						if(!isVisited[index]){
							if(vertices.get(index).type == Type.Task)
								Llb[i].add(index);
							isVisited[index] = true;
							queue.offer(vertices.get(index));
						}
					}
				}
			}
		}
	}
	
	/*Return the adjacency matrix*/
	public int[][] getAdjacencyMatrix(){
		int [][] adjacencyMatrix = new int[getSize()][getSize()];
		
		for(int i = 0; i<neighbors.size(); i++){
			for(int j = 0; j<neighbors.get(i).size(); j++){
				int v = neighbors.get(i).get(j);
				adjacencyMatrix[i][v] = 1;
			}
		}
		
		return adjacencyMatrix;
	}
	
	/*Print the adjacency matrix*/
	public void printAdjacencyMatrix(){
		int [][] adjacencyMatrix = getAdjacencyMatrix();
		for(int i = 0; i<adjacencyMatrix.length; i++){
			for(int j = 0; j<adjacencyMatrix[0].length; j++){
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/*Print the edges*/
	public void printEdges(){
		for(int u = 0; u<neighbors.size(); u++){
			System.out.print("Vertex " + u + "( " + vertices.get(u).label + " )" + ": " );
			for(int j = 0; j< neighbors.get(u).size(); j++){
				System.out.print("(" + u + ", " + neighbors.get(u).get(j) + ") ");
			}
			System.out.println();
		}
	}
	
	public List<Vertice> getOutputContext(int index){
		List<Vertice> nin = new ArrayList<Vertice>();
		
		Iterator<Integer> iterator = Lla[index].iterator();
		while(iterator.hasNext())
			nin.add(vertices.get(iterator.next()));
		
		return nin;
	}
	
	public List<Vertice> getOutputContext(Vertice v){
		List<Vertice> nin = new ArrayList<Vertice>();
		int index = getIndex(v);
		
		Iterator<Integer> iterator = Lla[index].iterator();
		while(iterator.hasNext())
			nin.add(vertices.get(iterator.next()));
		
		return nin;
	}
	
	public List<Vertice> getInputContext(int index){
		List<Vertice> nout = new ArrayList<Vertice>();
		
		Iterator<Integer> iterator = Llb[index].iterator();
		while(iterator.hasNext())
			nout.add(vertices.get(iterator.next()));
		
		return nout;
	}
	
	public List<Vertice> getInputContext(Vertice v){
		List<Vertice> nout = new ArrayList<Vertice>();
		int index = getIndex(v);
		
		Iterator<Integer> iterator = Llb[index].iterator();
		while(iterator.hasNext())
			nout.add(vertices.get(iterator.next()));
		
		return nout;
	}
	
	/*Obtain a DFS tree starting from vertex v*/
	public Tree dfs(int v){
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for(int i = 0; i<parent.length; i++)
			parent[i] = -1;  //Initialize parent[i] to -a
		
		boolean[] isVisited = new boolean[vertices.size()];
		
		dfs(v, parent, searchOrders, isVisited);
		
		return new Tree(v, parent, searchOrders);
	}
	
	/*Recursive method for DFS search*/
	private void dfs(int v, int[] parent, List<Integer> searchOrders, boolean[] isVisited){
		/*Store the visited vertex*/
		searchOrders.add(v);
		isVisited[v] = true;
		
		for(int i : neighbors.get(v)){
			if(!isVisited[i]){
				parent[i] = v;  /*The parent of vertex i is v*/
				dfs(i, parent, searchOrders, isVisited);
			}
		}
	}
	
	/*Starting bfs search from vertex v*/
	public Tree bfs(int v){
		List<Integer> searchOrders = new ArrayList<Integer>();
		int [] parent = new int[vertices.size()];
		HashSet<Integer> [] parents = new HashSet[this.vertices.size()];
		HashSet<Integer> [] children =new HashSet[this.vertices.size()];
		for(int i = 0; i<parent.length; i++)
			parent[i] = -1;
		
		for(int i = 0; i<this.vertices.size(); i++){
			parents[i] = new HashSet<Integer>();
			children[i] = new HashSet<Integer>();
		}
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v);
		isVisited[v] = true;
		
		while(!queue.isEmpty()){
			int u = queue.poll();   //Dequeue to u
			searchOrders.add(u);
			for(int w: neighbors.get(u)){
				/*if(vertices.get(u).type == Type.Task){    /*Only consider the tasks nodes 
				                                         when obtain the causal footprint*/
					children[u].add(w);
					parents[w].add(u);
				//}
				if(!isVisited[w]){
					queue.offer(w);  //Enqueue w
					parent[w] = u;
					isVisited[w] = true;
				}
			}
		}
		return new Tree(v, parent, parents, children, searchOrders);
	}
	
	public class Tree{
		private int root;
		private int[] parent;
		private HashSet<Integer>[] children;
		private HashSet<Integer>[] parents;
		private List<Integer> searchOrders;
		
		/*Construct a tree with root, parent, and searchOrder*/
		public Tree(int root, int[] parent, List<Integer> searchOrders){
			this.root = root;
			this.parent = parent;
			this.searchOrders = searchOrders;
		}
		
		/*Construct a tree with root and parent without a particular order*/
		public Tree(int root, int[] parent){
			this.root = root;
			this.parent = parent;
		}
		
		public Tree(int root, int[] parent, HashSet<Integer>[] parents, HashSet<Integer>[] children, List<Integer>searchOrders){
			this.root = root;
			this.parent = parent;
			this.parents = parents;
			this.children = children;
			this.searchOrders = searchOrders;
		}
		/*Return the root of the tree*/
		public int getRoot(){
			return root;
		}
		
		/*Return the parent of vertex v*/
		public int getParent(int v){
			return parent[v];
		}
		
		public HashSet<Integer> getParents(int v){
			return parents[v];
		}
		
		public HashSet<Integer> getChildren(int v){
			return children[v];
		}
		/*Return an array representing search orders*/
		public List<Integer> getSearchOrders(){
			return searchOrders;
		}
		
		/*Return number of vertices found*/
		public int getNumberOfVerticesFound(){
			return searchOrders.size();
		}
		
		/*Return the path of vertices from a vertex index to the root*/
		public List<Vertice> getPath(int index){
			ArrayList<Vertice> path = new ArrayList<Vertice>();
			
			do{
				path.add(vertices.get(index));
				index = parent[index];
			}while(index != -1);
			return path;
		}
		
		/*Print a path from the root to vertex v*/
		public void printPath(int index){
			List<Vertice> path = getPath(index);
			System.out.print("A path from "+ vertices.get(root) + " to " + vertices.get(index) + ": ");
			for(int i = path.size() - 1; i>=0; i--)
				System.out.print(path.get(i) + " ");
		}
		
		/*Print the whole tree*/
		public void printTree(){
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for(int i = 0; i<parent.length; i++){
				if(parent[i] != -1){
					System.out.print("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}
		
	}
}
