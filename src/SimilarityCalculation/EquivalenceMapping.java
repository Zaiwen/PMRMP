package SimilarityCalculation;
import java.util.*;
import DataStructure.*;

public class EquivalenceMapping {
	private List<Pair> map = new ArrayList<Pair>();  
	private double sumOfSimilarity = 0;
	
	public EquivalenceMapping(){}
	
	public EquivalenceMapping(List<Pair> map)
	{
		this.map = map;
	}
	
	public EquivalenceMapping(Pair[] mapArray){
		for(Pair pair: mapArray){
			this.map.add(pair);
		}
	}
	
	public EquivalenceMapping(int[][] mapMatrix){
		for(int i = 0; i<mapMatrix.length; i++)
			for(int j = 0; j < mapMatrix[i].length; j++)
			{
				if(mapMatrix[i][j] > 0)
					this.map.add(new Pair(i, j));
			}
	}
	
	/*used for judge whether a vertice is mapped in the mapping*/
	public boolean isMapped(Vertice v, int graphNo){
		boolean flag = false;
		for(Pair pair: map){
			if(graphNo == 1){
			if(v.No == pair.n1){
				flag =true;
				break;
			}
			}
			else{
			if(v.No == pair.n2){
				flag = true;
				break;
			}
		}
		}
		return flag;
	}
	
	public boolean isMapped(int index, int graphNo){
		boolean flag = false;
		for(Pair pair: map){
			if(graphNo == 1){
			if(index == pair.n1){
				flag =true;
				break;
			}
			}
			else{
			if(index == pair.n2){
				flag = true;
				break;
			}
		}
		}
		return flag;
	}
	
	public List<Pair> getMap(){
		return map;
	}
	
	public double getSumOfSimilarity(){
		return sumOfSimilarity;
	}
	
	public void setSumOfSimilarity(double sum){
		sumOfSimilarity = sum;
	}
	
	public void addToSum(double s){
		sumOfSimilarity += s;
	}
	public void addPair(Pair pair){
		map.add(pair);
	}
	
	public void printMapping(){
		for(Pair pair: map){
			System.out.println("Vertex" + pair.n1 + " in the first graph is mapped with vertex" + pair.n2 + " in the second graph.");
		}
	}
	/*used for judge whether two vertices linked by an edge are all mappend with each other in the mapping*/
	/*public boolean isConnected(Edge e){
		boolean flag = false;
		
		
	}*/
}
