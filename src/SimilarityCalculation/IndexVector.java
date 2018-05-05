package SimilarityCalculation;
import java.util.*;
public class IndexVector {
	private List<Double> components;
	
	public IndexVector(){
		this.components = new ArrayList<Double>();
	}
	
	public IndexVector(List<Double> components){
		this.components = components;
	}
	
	public IndexVector(double[] components)
	{
		this.components = new ArrayList<Double>();
		for(int i = 0; i < components.length; i++)
			this.components.add(components[i]);
	}
	
	public List<Double> getComponents(){
		return components;
	}
	
	public double getComponent(int index){
		return components.get(index);
	}
	
	public void addComponent(double c){
		components.add(c);
	}
	
	public void setComponent(int index, double c){
		components.set(index, c);
	}
	
	public double Multiply(IndexVector g2){
		double result = 0;
		
		for(int i = 0; i < components.size(); i++)
			result += getComponent(i) * g2.getComponent(i);
		
		return result;
	}
	
	public double Module(){
		double result = 0;
		double midresult = 0;
		
		for(int i = 0; i< components.size(); i++)
			midresult += Math.pow(getComponent(i), 2);
		
		result = Math.sqrt(midresult);
		return result;
	}
}
