package org.sklse.owlseditor.sparqldl;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;


/*Create by Zaiwen FENG at 10 Nov 2015 to test semanticSimilarity4Words()*/
public class Test_SemanticWord {
	
	public static void main(String args[]){
		
		String ontURL = new String("file:///D:\\newpaper7\\evaluation & implementation(from 2013-03-26)\\Evaluation_From26Mar2013-15Mar2015\\electronicdoc.owl");
    	
		/*define the address of the imported ontology*/
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		//**read the ontology from file system to memory**//
		ontModel.read(ontURL);
		
		String word1 = new String("Sorting_List");
		
		String word2 = new String ("Electronic_List");
		
		boolean b = VariableQuery.semanticSimilarity4Words(word1, word2, ontModel);
		
		System.out.println("Are they similar? " + b);
		
	}

}
