package SimilarityCalculation;
import DataStructure.*;
import java.util.*;
import java.lang.String;

public class ElementsSimilarity {
	double similarity;
	
	public double SyntacticSimilarity(Vertice v1, Vertice v2){
		String l1 = v1.label;
		String l2 = v2.label;
		//double similarity;
		if(l1 == null || l2 == null)
			return 0;
		
		//System.out.println("l1.length() = " + l1.length());
		int[][] D = new int[l1.length()+1][l2.length()+1];  /*The distance matrix of two strings*/
		int i, j, d;
		
		for(i = 0; i <= l1.length(); i++)   /*Initialize the matrix*/
			D[i][0] = i;
		for(j = 0; j <= l2.length(); j++)
			D[0][j] = j;
		
		for(i = 1; i<= l1.length(); i++){
			for(j = 1; j<=l2.length(); j++){
				if(l1.charAt(i - 1) == l2.charAt(j - 1))
					d = 0;
				else
					d = 1;
				
				D[i][j] = min(D[i-1][j-1] + d, D[i][j-1] + 1, D[i-1][j] + 1);
			}
		}
		//System.out.println("The string-edit distance between \"" + l1 + "\" and \"" + l2 +"\" is " + D[l1.length()][l2.length()]);
		int Max;
		if(l1.length() > l2.length())
			Max = l1.length();
		else
			Max = l2.length();
		
		return (1- (double)D[l1.length()][l2.length()]/Max);
	}
	
	public double SemanticSimilarity(Vertice v1, Vertice v2){
		String l1 = v1.label;
		String l2 = v2.label;
		final double weight1 = 1.0;
		final double weight2 = 0.75;
		
		if(l1 == null || l2 == null)
			return 0;
		List<String> w1 = new ArrayList<String>();
		List<String> w2 = new ArrayList<String>();
		double similarity = 0;
		
		w1 = Seperate(l1);
		w2 = Seperate(l2);
		
		List<String> intersection = Intersection(w1, w2);
		List<String> s12 = Intersection(Synonyms(w1), w2);
		List<String> s21 = Intersection(Synonyms(w2), w1);
		
		similarity = (2*weight1*intersection.size() + weight2*(s12.size() + s21.size()))/(w1.size() + w2.size());
		return similarity;
	}
	
	public double ContextualSimilarity(BPG graph1, BPG graph2, int u, int v){
		double similarity;
		List<Vertice> N1in = graph1.getInputContext(u);
		List<Vertice> N2in = graph2.getInputContext(v);
		List<Vertice> N1out = graph1.getOutputContext(u);
		List<Vertice> N2out = graph2.getOutputContext(v);
		/*for(Vertice vertice: N1in)
			System.out.print(vertice.label + "; ");
		System.out.println();
		for(Vertice vertice: N2in)
			System.out.print(vertice.label + "; ");
		System.out.println();
		for(Vertice vertice: N1out)
			System.out.print(vertice.label + "; ");
		System.out.println();
		for(Vertice vertice: N2out)
			System.out.print(vertice.label + "; ");
		System.out.println();*/
		
		EquivalenceMapping Moptin = getOptMapping(N1in, N2in);
		/*for(Pair pair: Moptin.getMap())
			System.out.print("(" + pair.n1 + ", " + pair.n2 + ")  ");
		System.out.println();*/
		EquivalenceMapping Moptout = getOptMapping(N1out, N2out);
		/*for(Pair pair: Moptout.getMap())
			System.out.print("(" + pair.n1 + ", " + pair.n2 + ")  ");
		System.out.println();*/
		
		similarity = Moptin.getMap().size()/(2*Math.sqrt(N1in.size()))*Math.sqrt(N2in.size()) + Moptout.getMap().size()/(2*N1out.size())*N2out.size();
		return similarity;
	}
	
	static int min(int a, int b, int c)
	{
		int Min = a;
		if(b < Min)
			Min = b;
		if(c < Min)
			Min = c;
		
		return Min;
	}
	
	static int max(int a, int b){
		if(b > a)
			return b;
		else 
			return a;
	}
	
	public static ArrayList<String> Seperate(String label){
		ArrayList<String> w = new ArrayList<String>();
		String[] temp = label.split(" +");
		
		for(int i = 0; i < temp.length; i++)
			w.add(temp[i]);
		
		return w;
	}
	
	public static List<String> Synonyms(List<String> words){
		List<String> synonyms = new ArrayList<String>();
		
		return synonyms;
	}
	public static List<String> Intersection(List<String> w1, List<String> w2){
		List<String> intersection = new ArrayList<String>();
		quicksort(w1, 0, w1.size()-1);
		quicksort(w2, 0, w2.size()-1);
		
		int i=0, j=0;
		while(i<w1.size() && j < w2.size()){
			if(w1.get(i).compareTo(w2.get(j)) == 0){
				intersection.add(w1.get(i));
				i++;
				j++;
			}
			else if(w1.get(i).compareTo(w2.get(j)) < 0)
				i++;
			else
				j++;
		}
		
		return intersection;
	} 
	
	
	public static List<String> Difference(List<String> w1, List<String> w2){
		List<String> intersection = Intersection(w1, w2);
		List<String> difference = new ArrayList<String>();
		for(String word: w1){
			if(!belongTo(word, intersection))
				difference.add(word);
		}
		
		return difference;
	}
	
	public static boolean belongTo(String word, List<String> words){
		for(String w: words){
			if(word.equals(w))
				return true;
		}
		return false;
	}
	public static void quicksort(List<String> w, int low, int high){
		int m;
		if(low < high){
		m = Split(w, low, high);
		quicksort(w, low, m-1);
		quicksort(w, m+1, high);
		}
	}
	
	/*We define the threshold 0.5, which means only the similarity of two nodes is greater*/
	/*than 0.5 could the pair be added into the mapping*/
	public static EquivalenceMapping getOptMapping(List<Vertice> n1, List<Vertice> n2){
		EquivalenceMapping optm = new EquivalenceMapping();
		//boolean exists = true;
		int i, j;
		double maxsim;
		boolean flag = false;  /*used to judge whether there exists a (n,m)in openpairs that
		                         can be added to the mapping*/
		//int index = 0;
		ElementsSimilarity calculator = new ElementsSimilarity();
		boolean[][] hasBeenMapped = new boolean[2][max(n1.size(), n2.size())];
		double similarity = 0;
		Pair newPair = new Pair();
		
		for(i = 0; i < 2; i++)
			for(j = 0; j < hasBeenMapped[i].length; j++)
				hasBeenMapped[i][j] = false;
		
		while(true){
			//System.out.println(index);
			maxsim = 0.5;    /*The threshold*/
			newPair.n1 = -1;  newPair.n2 = -1;
			flag = false;
			for(i = 0; i < n1.size(); i++)
			{
				if(hasBeenMapped[0][i] == false){
				for(j = 0; j < n2.size(); j++){
					if((hasBeenMapped[1][j] == false) && (n1.get(i).type == n2.get(j).type)){
						if((similarity = calculator.SyntacticSimilarity(n1.get(i),n2.get(j))) >= maxsim){
							maxsim = similarity;
							newPair.n1 = i;
							newPair.n2 = j;
							flag = true;
						}
					}
				}
				}
			}
			if(flag == false)
				break;
			optm.addToSum(maxsim);
			optm.addPair(new Pair(n1.get(newPair.n1).No, n2.get(newPair.n2).No));
			hasBeenMapped[0][newPair.n1] = true;
			hasBeenMapped[1][newPair.n2] = true;
			//index++;
		}
		
		return optm;
	}
	
	public static int Split(List<String> w, int low, int high){
		int i, j, m;
		String sx, temp;
		sx = w.get(low);
		i = low;
		for(j = low + 1; j<=high; j++){
			if(w.get(j).compareTo(sx) < 0){
				i++;
				temp = w.get(i);
				w.set(i, w.get(j));
				w.set(j, temp);
			}
		}
		temp = w.get(low);
		w.set(low, w.get(i));
		w.set(i, temp);
		m = i;
		return m;
	}
}
