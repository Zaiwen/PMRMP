package org.sklse.owlseditor.sparqldl;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;


//**Created by Zaiwen FENG at 9 Nov 2015**//

public class TestJena_hp_01 {
	
	public static void main (String args[] ){
		
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		ontModel.read("file:///D:\\newpaper7\\evaluation & implementation(from 2013-03-26)\\Evaluation_From26Mar2013-15Mar2015\\electronicdoc.owl");
		
		for(Iterator<OntClass> i = ontModel.listClasses(); i.hasNext();){
			
			OntClass c = i.next();
			
			if(!c.isAnon()){
				
				System.out.print("Class");
				
				System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
				
				
				System.out.println("---------------------------");
				
				for(Iterator<OntClass> it = c.listSuperClasses(); it.hasNext();){
					
					OntClass sp = it.next();
					String str = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI());
					String strSP = sp.getURI();
					try{
						
						str = str + ":" + strSP.substring(strSP.indexOf('#')+1);
						System.out.println(" Class" + str);
						
					}catch(Exception e){
						
						
					}
					
				}
				
				System.out.println("---------------------------");
				
				for(Iterator<OntClass> it = c.listSubClasses(); it.hasNext();){
					
					System.out.print(" Class");
					OntClass sb = it.next();
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) + 
							"'s subClass is " + sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()));
				}
				
				
						
			}
						
		}
				
	}
	
}
