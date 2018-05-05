package DataStructure;
import java.util.*;
public interface Graph {
	public int getSize();
	
	public List<Vertice> getVertices();
	public Vertice getVertex(int index);
	public int getIndex(Vertice v);
	public List<Integer> getNeighbors(int index);
	//public int getDegree(int v);  /* Return the degree for a specified vertex*/
	public int [][] getAdjacencyMatrix();
	public void printEdges();
	public BPG.Tree dfs(int v);  /*Obtain a depth-first search tree*/
	public BPG.Tree bfs(int v);  /*Obtain a breadth-first search tree*/
}
