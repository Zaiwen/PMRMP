package SimilarityCalculation;
import java.util.*;
import DataStructure.*;
//import SimilarityCalculation.ElementsSimilarity;
public class Similarity {
	public static double NodeMatchingSimilarity(BPG graph1, BPG graph2){
		List<Vertice> n1 = graph1.getVerticesOfType(Type.Task);
		List<Vertice> n2 = graph2.getVerticesOfType(Type.Task);
		double similarity;
		
		EquivalenceMapping Mopt = ElementsSimilarity.getOptMapping(n1, n2);
		similarity = Mopt.getSumOfSimilarity() * 2/(n1.size() + n2.size());
		
		return similarity;
	}
	
	public static double StructuralSimilarity(BPG graph1, BPG graph2){
		List<Vertice> N1 = graph1.getVertices();
		List<Vertice> N2 = graph2.getVertices();
		
		EquivalenceMapping mapping = ElementsSimilarity.getOptMapping(N1, N2);
		List<Vertice> sn = new ArrayList<Vertice>();  /*TThe set of all inserted and deleted nodes.*/
		List<Vertice> sb = new ArrayList<Vertice>();  /*The set of vertices which should be substituted*/
		
		for(Vertice v: N1){
			if(!mapping.isMapped(v, 1))
				sn.add(v);
			else
				sb.add(v);
		}
		for(Vertice v: N2){
			if(!mapping.isMapped(v, 2))
				sn.add(v);
			else
				sb.add(v);
		}
		
		List<Edge> se = new ArrayList<Edge>();
		for(Edge e: graph1.getEdges()){
			if(!(mapping.isMapped(e.u, 1) && mapping.isMapped(e.v, 1)))
				se.add(e);
		}
		for(Edge e: graph2.getEdges()){
			if(!(mapping.isMapped(e.u, 2) && mapping.isMapped(e.v, 2)))
				se.add(e);
		}
		
		/*for(Vertice v: sn)
			System.out.print(v.label + "; ");
		System.out.println();
		for(Vertice v: sb)
			System.out.print(v.label + "; ");
		System.out.println();
		for(Edge e: se)
			System.out.print("(" + e.u + ", " + e.v +") ");
		System.out.println();
		System.out.println("The sumOfSimilarity is " + mapping.getSumOfSimilarity());*/
		double sev = se.size()/(graph1.getEdges().size() + graph2.getEdges().size());
		double snv = sn.size()/(N1.size() + N2.size());
		double sbv = 2 * (mapping.getMap().size() - mapping.getSumOfSimilarity())/(N1.size() + N2.size() - sn.size());
		
		double similarity = 1 - (sev + snv + sbv)/3;  /*the average function could use weights*/
		return similarity;
	}
	
	public static double BehavioralSimilarity(BPG graph1, BPG graph2){
		double elementsSimilarity;
		IndexVector g1 = new IndexVector();
		IndexVector g2 = new IndexVector();
		List<Vertice> N1 = graph1.getVertices();
		List<Vertice> N2 = graph2.getVertices();
		ElementsSimilarity calculator = new ElementsSimilarity();
		EquivalenceMapping mapping = ElementsSimilarity.getOptMapping(N1, N2);
		
		for(Pair pair: mapping.getMap()){
			elementsSimilarity = calculator.SyntacticSimilarity(N1.get(pair.n1), N2.get(pair.n2));
			g1.addComponent(elementsSimilarity);
			g2.addComponent(elementsSimilarity);
			g1.addComponent(elementsSimilarity/Math.pow(2, graph1.Llb[pair.n1].size()));
			g2.addComponent(elementsSimilarity/Math.pow(2, graph2.Llb[pair.n2].size()));
			g1.addComponent(elementsSimilarity/Math.pow(2, graph1.Lla[pair.n1].size()));
			g2.addComponent(elementsSimilarity/Math.pow(2, graph2.Lla[pair.n2].size()));
		}		
		
		double similarity = g1.Multiply(g2)/(g1.Module() * g2.Module());
		return similarity;
	}
}
