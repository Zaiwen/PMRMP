package org.sklse.owlseditor.sparqldl;


//*Revised by Zaiwen FENG at 9 Nov 2015, for scalability evaluation of prototype*//

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ontology.OntologyDAO;
import ontology.OntologyDTO;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import databaseaccess.Access;
import de.derivo.sparqldlapi.Query;
import de.derivo.sparqldlapi.QueryEngine;
import de.derivo.sparqldlapi.QueryResult;
import finalvariable.BasicPathVariable;

/// 问题：无法解析 王晨 创建的本体

public class VariableQuery {

	private static Map<String, QueryEngine> engines = new HashMap<String, QueryEngine>();

	private static String sparqldl = null;

	public static String[] query(String path, String[] input, String[] output)
			throws Exception {
		sparqldl = "";
		ArrayList<ArrayList<String>> inputs = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> outputs = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < input.length; i++) {
			ArrayList<String> arr = new ArrayList<String>();
			arr.addAll(getEquivalentClass(input[i]));
			inputs.add(arr);
		}
		for (int i = 0; i < output.length; i++) {
			ArrayList<String> arr = new ArrayList<String>();
			arr.addAll(getEquivalentClass(output[i]));
			outputs.add(arr);
		}
		sparqldl = createSql(inputs, outputs);
		// 开始查询
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ont = manager.loadOntologyFromOntologyDocument(new File(
				path));
		StructuralReasonerFactory factory = new StructuralReasonerFactory();
		OWLReasoner reasoner = factory.createReasoner(ont);
		QueryEngine engine = QueryEngine.create(manager, reasoner, true);
		QueryResult result = engine.execute(Query.create(sparqldl));
		String[] arr1 = result.toString().split("\\?x =");
		String arr[] = new String[arr1.length - 1];
		for (int i = 1; i < arr1.length; i++) {
			arr[i - 1] = arr1[i].trim().split("#")[1];
		}
		return arr;
	}

	private static String createSql(ArrayList<ArrayList<String>> inputs,
			ArrayList<ArrayList<String>> outputs) {
		StringBuilder sql = new StringBuilder(
				"PREFIX process: <http://www.daml.org/services/owl-s/1.1/Process.owl#>\nSELECT ?x ");
		ArrayList<ArrayList<String>> io = new ArrayList<ArrayList<String>>();
		io.addAll(inputs);
		io.addAll(outputs);
		int count[] = new int[io.size()];
		count[io.size() - 1] = 1;
		for (int i = io.size() - 1; i > 0; i--) {
			count[i - 1] = count[i] * io.get(i).size();
		}
		for (int i = 0; i < count[0] * io.get(0).size(); i++) {
			String str = (i == 0 ? "" : "OR ") + "WHERE {\n";
			str += "PropertyValue(?x , process:process, ?y),\n";
			for (int j = 0; j < count.length - 1; j++) {
				if (j < inputs.size()) {
					str += "PropertyValue(?y , process:hasInput,?z"
							+ j
							+ "),\n"
							+ "PropertyValue(?z"
							+ j
							+ ", process:parameterType, \""
							+ io.get(j).get(
									i / count[j] % (inputs.get(j).size()))
							+ "\"),\n";
				} else {
					str += "PropertyValue(?y , process:hasOutput, ?z" + j
							+ "),\n" + "PropertyValue(?z" + j
							+ ", process:parameterType, \""
							+ io.get(j).get(i / count[j] % (io.get(j).size()))
							+ "\"),\n";
				}
			}
			int j = count.length - 1;
			if (j < inputs.size()) {
				str += "PropertyValue(?y , process:hasInput, ?z" + j + "),\n"
						+ "PropertyValue(?z" + j + ", process:parameterType,\""
						+ io.get(j).get(i % (inputs.get(j).size())) + "\")}\n";
			} else {
				str += "PropertyValue(?y , process:hasOutput, ?z" + j + "),\n"
						+ "PropertyValue(?z" + j
						+ ", process:parameterType, \""
						+ io.get(j).get(i % (io.get(j).size())) + "\")}\n ";
			}
			sql.append(str);
		}
		// Test
		// for (int i = 0; i < count[count.length - 1]; i++) {
		// for (int j = 0; j < count.length - 1; j++) {
		// if (j < inputs.size()) {
		// System.out.print(inputs.get(j).get(
		// i / count[count.length - j - 2]
		// % (inputs.get(j).size())));
		//
		// } else {
		// System.out.print(outputs.get(j - inputs.size()).get(
		// i
		// / count[count.length - j - 2]
		// % (outputs.get(j - inputs.size())
		// .size())));
		// }
		// }
		// int j = count.length - 1;
		// if (j < inputs.size()) {
		// System.out.print(inputs.get(j).get(i % (inputs.get(j).size())));
		// } else {
		// System.out.print(outputs.get(j - inputs.size()).get(
		// i % (outputs.get(j - inputs.size()).size())));
		// }
		// System.out.println();
		// }
		return sql.toString();
	}

	public static String getSparqldl() {
		return sparqldl;
	}

	// 找到包含该类的本体的物理地址
	private static String getFilePath(String cls) {
		Connection conn = Access.getConnection();
		OntologyDAO odao = new OntologyDAO(conn);
		List<OntologyDTO> dtos = odao.queryAll();
		OntologyDTO dto = null;
		Iterator<OntologyDTO> iterator = dtos.iterator();
		while (iterator.hasNext()) {
			dto = iterator.next();
			if (cls.indexOf(dto.getUri()) == 0) {
				String path = BasicPathVariable.ontologyLO;
				path += dto.getUser();
				path += "//" + dto.getFileLocation();
				return path;
			}
		}
		return null;
	}

	// 获取针对于某个本体的查询引擎（有缓存）
	private static QueryEngine getQueryEngine(String path) throws Exception {
		if (engines.containsKey(path)) {
			return engines.get(path);
		} else {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ont = manager
					.loadOntologyFromOntologyDocument(new File(path));
			StructuralReasonerFactory factory = new StructuralReasonerFactory();
			OWLReasoner reasoner = factory.createReasoner(ont);
			QueryEngine engine = QueryEngine.create(manager, reasoner, true);
			engines.put(path, engine);
			return engine;
		}
	}

	// 找到与某个类相同的类或其子类
	private static ArrayList<String> getEquivalentClass(String cls)
			throws Exception {
		ArrayList<String> arr = new ArrayList<String>();
		String path = getFilePath(cls);
		arr.add(cls);
		if (path == null) {
			return arr;
		}
		QueryEngine engine = getQueryEngine(path);
		String query = "SELECT ?x " + "WHERE{EquivalentClass(?x, <" + cls
				+ ">)}" + " OR WHERE{SubClassOf(?x, <" + cls + ">)}";
		QueryResult result = engine.execute(Query.create(query));
		String[] arr1 = result.toString().split("\\?x =");
		for (int i = 1; i < arr1.length; i++) {
			arr.add(arr1[i].trim());
		}
		// 删除Nothing
		ArrayList<String> arr2 = new ArrayList<String>();
		for (int i = 0; i < arr.size(); i++) {
			if ("http://www.w3.org/2002/07/owl#Nothing".equals(arr.get(i))) {
				continue;
			} else {
				arr2.add(arr.get(i));
			}
		}
		arr = arr2;
		// 删除相同的概念
		ArrayList<String> arr3 = new ArrayList<String>();
		for (int i = 0; i < arr.size(); i++) {
			String ss = arr.get(i);
			boolean flag = false;
			for (int j = 0; j < arr3.size(); j++) {
				if (arr3.get(j).equals(ss)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				arr3.add(ss);
			}
		}
		arr = arr3;
		return arr;
	}
	
	
	/**This function is used to compare a pair of interface variables in a semantic way, just for prototype evaluation*
	 * *if word1 is similar with or equivalent to word2, return true, or return false
	 * *'word2 is supposed to be 'eps_designator_value''***'word1' is the input or output of process node in APG*/
	 /* 'word2' is one of concepts of the ontology that 'ontURL' points to**/
	public static boolean semanticSimilarity4Words (String word1, String word2, OntModel ontModel){
		
		boolean b = false;
		
//		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		//**read the ontology from file system to memory**//
//		ontModel.read(ontURL);
		
		//**iterate all the concepts of the ontology, in order to get all the subclasses of 'word2'**//
		List<String> sub_concept_word2 = new ArrayList<String>();
		
		/*Get all of sub-classes of 'word2' in the ontology 'ontModel'*/
		listAllSubClasses(ontModel, word2, sub_concept_word2);
		
		if(word1.equals(word2)){
			
			b=true;
		}else{
			
			//**Loop the array list 'sub_concept_word2'**//
			Iterator<String> iterator = sub_concept_word2.iterator();
			while(iterator.hasNext()){
				
				if (word1.equals(iterator.next())){
					b=true;
				}else{
					
					continue;
				}
			}
			
		}
		
		return b;
	}
	

	
	//**Obtain all the sub-classes of the concept 'cp' in the ontology model named 'ontModel', and all the sub-classes is saved in an array list named 'sub_cp'**//
	private static void listAllSubClasses (OntModel ontModel, String cp, List<String> sub_cp){
		
		//**iterate all the concepts of the ontology, in order to find the concept 'cp'**//
		for(Iterator<OntClass> i = ontModel.listClasses(); i.hasNext();){
			
			//**get a OntClass of ontology**//
			OntClass c = i.next();
			
			if(!c.isAnon()){
				
				//**get a concept of ontology**//
				String concept_ont = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI());
				
				/*need to remove the first character of 'concept_ont'*/
				concept_ont = concept_ont.substring(1);
				
				//*address 'word2' in the ontology*//
				if(cp.equals(concept_ont)){
					
					//**List all of the subclasses of cp' in the ontology**//
					//**Loop to list all the sub-classes of the concept named 'cp'**//
					for(Iterator<OntClass> it = c.listSubClasses(); it.hasNext();){
						
						OntClass sb = it.next();
						
						/*get the string for OntClass 'sb'*/
						String sb_str = sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI());
						
						/*Need to remove the first character of 'sb_str'*/
						sb_str = sb_str.substring(1);
						
						/*append the label of subclass 'sb_str' into the array list*/						
						sub_cp.add(sb_str);
						
						/*if 'sb_str' has the sub-classes, recursion*/
						if (sb.listSubClasses().hasNext()){
							
							listAllSubClasses(ontModel, sb_str, sub_cp);
						}else{
							continue;
						}
						
					}
					
				}
				
			}
			
			
		}
		
		
	}

	public static void main(String args[]) throws Exception {
		// System.out.println(getEquivalentClass("http://www.owl-ontologies.com/Ontology1358945918.owl#Tree"));
		// ArrayList<ArrayList<String>> inputs = new
		// ArrayList<ArrayList<String>>();
		// ArrayList<ArrayList<String>> outputs = new
		// ArrayList<ArrayList<String>>();
		// for (int i = 0; i < 2; i++) {
		// ArrayList<String> str1 = new ArrayList<String>();
		// str1.add(i+ ""+1 );
		// str1.add(i+ ""+2 + "");
		// inputs.add(str1);
		// ArrayList<String> str2 = new ArrayList<String>();
		// str2.add(i+ ""+3 + "");
		// str2.add(i+ ""+4 + "");
		// outputs.add(str2);
		// }
		// System.out.println(inputs);
		// System.out.println(outputs);
		// String sql = createSql(inputs, outputs);
		// System.out.println(sql);
		String inputs[] = new String[2];
		String outputs[] = new String[1];
		inputs[0] = "http://www.owl-ontologies.com/Ontology1358945918.owl#Plant";
		inputs[1] = "http://www.owl-ontologies.com/Ontology1358945918.owl#Animal";
		outputs[0] = "http://www.owl-ontologies.com/Ontology1358945918.owl#Leaf";
		String path = "E:\\project\\summerproject\\BPEP\\WebContent\\Process\\chenliang\\demo03\\process.owl";
		String[] arr = query(path, inputs, outputs);
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}

	}
}
