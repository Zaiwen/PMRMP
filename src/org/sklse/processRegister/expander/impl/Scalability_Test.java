package org.sklse.processRegister.expander.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ontology.IdWorker;

import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.dto.PluginInfoDTO;
import org.sklse.processRegister.db.dto.APGProcessDTO;
import org.sklse.processRegister.db.enums.ConditionPredicateEnum;
import org.sklse.processRegister.db.enums.DataFlowExtensionPartternEnum;
import org.sklse.processRegister.db.enums.ProcessFieldEnum;
import org.sklse.processRegister.db.enums.ProcessTypeEnum;
import org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum;
import org.sklse.processRegister.expander.service.ProcessGraphExpanderService;
import org.sklse.processRegister.processGraph.AnnotatedProcessGraph;
import org.sklse.processRegister.processGraph.AnnotatedProcessNode;
import org.sklse.processRegister.processGraph.EventNode;
import org.sklse.processRegister.processGraph.GraphNode;
//import org.sklse.processRegister.processGraph.ProcessNode;
import org.sklse.processRegister.processGraph.SequenceDependencyNode;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/*Cretaed by Zaiwen Feng*/

public class Scalability_Test {

    static ProcessGraphExpanderImpl processGraphExpander = new ProcessGraphExpanderImpl();
    
//    /*Define a PMG graph*/
//    static ProcessGraph processGraph = getDebugProcessGraph();
    
    /*Define a plug-in, which is supposed to be used in a couple of APG processes*/
    static PluginInfoDTO pluginInfoDTO =getDebugpluginProcessDTO();
    
    static int insertPattern = 1002;
    
    /*define the working node number in a family*/
    static int totalnum = 500;
    
    static ProcessGraphExpanderService expanderService = ProcessGraphExpanderService.instance;
    
    public static void  main(String[] args) throws Exception {
    	
    	/*define family numbers in a process family that needs to co-evolve */
    	int number_of_process = 30;
    	
    	/*define the total time for make the whole process family co-evolve*/
    	long total_time_for_whole_family = 0L;
    	
    	/*define the total found EPs that are found in the process family*/
    	int total_PE_number = 0;
    	
		String ontURL = new String("file:///D:\\newpaper7\\evaluation & implementation(from 2013-03-26)\\Evaluation_From26Mar2013-15Mar2015\\electronicdoc_300_concepts.owl");
    	
		/*define the address of the imported ontology*/
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		//**read the ontology from file system to memory. Once is enough. **//
		ontModel.read(ontURL);
    	
    	for (int i = 1; i < number_of_process+1; i++){
    		
    		System.out.println("ID of the current process is: " + i);
    		
       		/*Get a APG*/
        	AnnotatedProcessGraph annotatedProcessGraph = getDebugProcessGraph();
        	       	
        	double timestart = System.currentTimeMillis();
        	
            System.out.println("The starting time is: " + timestart);
           
            StringBuffer sb = new StringBuffer();
            
            double total = 0;
        	
        	/*Get the extension point set*/
        	        	
        	List<AnnotatedProcessNode> annotatedprocessNodes = new ArrayList<AnnotatedProcessNode>();
        	
        	//Get the extension points by invoking this function below
        	annotatedprocessNodes = processGraphExpander.getExtensibilityPoint(annotatedProcessGraph, pluginInfoDTO, ontModel);
        	
        	double mid_time = System.currentTimeMillis();
        	
            System.out.println("Now the time is: " + mid_time);
            
            double takentime_addressingPE = mid_time - timestart;
            
            System.out.println("Addressing PE costs totally " + takentime_addressingPE + " (ms)");
        	
        	System.out.println("Before extension, the number of all the annotated process node(s) is: " + annotatedProcessGraph.getAllAnnotatedProcessNodes().size());
        	
        	System.out.println("By extension addressing, the number of all found extension point(s) is: " + annotatedprocessNodes.size());
            
            //PluginInfoDTO pluginInfoDTO = getDebugpluginProcessDTO();
            
            if (annotatedprocessNodes.size() > 0) {
            	
                /*Extend the annotated process graph structure*/
                processGraphExpander.expandAnnotatedProcessGraph(annotatedProcessGraph, annotatedprocessNodes, pluginInfoDTO);
            }
            
            System.out.println("After extension, the number of all the annotated process node(s) is: " + annotatedProcessGraph.getAllAnnotatedProcessNodes().size());
            
            double timeend = System.currentTimeMillis();
            
            System.out.println("The ending time is: " + timeend);
            
            double takentime = timeend - timestart;
            
            total = total + takentime;
            
            sb.append(" " + pluginInfoDTO.getControllFlowExtensionPattern() + " " + takentime + "\r\n");	

            System.out.println("It costs totally " + total + " (ms)");
            
//            saveTimeResult2File(sb.toString());
            
            total_time_for_whole_family = (long) (total_time_for_whole_family + total);
            
            total_PE_number = total_PE_number + annotatedprocessNodes.size();
            
    		System.out.println("--------------------------------------------");    		
    		
    	}
    	
    	System.out.println("The time for make " + number_of_process + " processes co-evolve with " + totalnum +  " node(s) is " + total_time_for_whole_family + "(ms)" );
    	
    	System.out.println("The average time is " + total_time_for_whole_family / number_of_process + "(ms) for a process");
    	
    	System.out.println("The average number of found EPs in the process family is: " + total_PE_number / number_of_process);
    	

    }

    public static void saveTimeResult2File(String content){

        try {
            File file = new File("d:/data.txt");

            // if file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*This function is to create a string randomly*/
    private static String createRandomString(){
    	
    	String random_string = new String("");
    	
    	/*Create an array of concept. The array includes 26 alphabets and 5 concepts in the domain-specific ontology 'e-documents'*/
    	List<String> label_list = new ArrayList<String>();
    	label_list.add("A");
    	label_list.add("B");
    	label_list.add("C");
    	label_list.add("D");
    	label_list.add("E");
    	
    	label_list.add("RFID");
    	
    	label_list.add("F");
    	label_list.add("G");
    	label_list.add("H");
    	label_list.add("I");
    	label_list.add("J");
    	
    	label_list.add("Packing_List");
    	
    	label_list.add("K");
    	label_list.add("L");
    	label_list.add("M");
    	label_list.add("N");
    	label_list.add("O");
    	
    	label_list.add("Real_Time_Route");
    	
    	label_list.add("P");
    	label_list.add("Q");
    	label_list.add("R");
    	label_list.add("S");
    	label_list.add("T");
    	
       	label_list.add("Sorting_List");
       	
    	label_list.add("U");
    	label_list.add("V");
    	label_list.add("W");
    	label_list.add("X");
    	label_list.add("Y");
    	label_list.add("Z");
    	
    	label_list.add("Delivery_List");
    	
    	/*Convert the array list to an string array*/
    	String[] label_array = new String[label_list.size()];
    	label_list.toArray(label_array);
    	
    	int START = 1;
    	int END = 31;
    	Random random = new Random();
    	
    	random_string = label_array[showRandomInteger(START,END,random) - 1];
    	
    	return random_string;
    }
    
    /*This function is to show a random integer*/
    private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
    	
    	if(aStart > aEnd){
    		
    		throw new IllegalArgumentException("Start cannot exceed End. ");
    	}
    	
    	long range = (long)aEnd - (long)aStart + 1;
    	
    	long fraction = (long)(range * aRandom.nextDouble());
    	
    	int randomNumber = (int)(fraction + aStart);
    	
    	return randomNumber;
    	
    }

    /*This function is to create an APG Graph automatically*/
    public static  AnnotatedProcessGraph getDebugProcessGraph() {
    	
        /*set the number of outputs interface*/
        int number_of_outputs = 8;
    	
        APGProcessDTO mainProcess = new APGProcessDTO();
        mainProcess.setId(1001);
        mainProcess.setName("1Be_25fz");
        mainProcess.setUri("http://www.w3.org/1999/XSL/Format");
        AnnotatedProcessGraph apg = new AnnotatedProcessGraph();
        apg.representing(mainProcess);
        apg.id(1000);


        EventNode eventNode1 = new EventNode();
        eventNode1.dto().setId(2001);
        eventNode1.dto().setName("Start");
        apg.addNode(eventNode1);

        EventNode eventNode2 = new EventNode();
        eventNode2.dto().setId(2002);
        eventNode2.dto().setName("End");
        apg.addNode(eventNode2);
        
        AnnotatedProcessNode annotatedprocessNode = new AnnotatedProcessNode();
        annotatedprocessNode.dto().setId(3001);
        
        /*Probably it should be populated with random text label here */
        annotatedprocessNode.dto().setName(createRandomString());
        
       
        annotatedprocessNode.dto().setParam("param");
        
        /*set the input of this annotated process node with a random string*/
        String input = createRandomString();
        List<String> input_list = new ArrayList<String>();
        input_list.add(input);
        annotatedprocessNode.dto().setInputList(input_list);
        

        //String output = new String("Sorting_List");
        List<String> output_list = new ArrayList<String>();
        
        for(int i = 0; i < number_of_outputs; i++){
        	
            /*set the output of this annotated process node with a random string*/
            String output = new String("");
            output = createRandomString();
            output_list.add(output);
        }
           
        annotatedprocessNode.dto().setOutputList(output_list);
        
        apg.addNode(annotatedprocessNode);
        
        apg.addEdge(eventNode1, annotatedprocessNode);
        apg.addEdge(annotatedprocessNode, eventNode2);
        
        /*Create annotated process node for scalability evaluation. The number of annotated process node is 'totalnum'*/
        int j = 0;
        
    	/*initialize the preceding annotated process node of newly added sequence node*/
    	long temp_preceding_apn_id = 3001L;
    	AnnotatedProcessNode temp_preceding_apn = new AnnotatedProcessNode();
    	temp_preceding_apn = annotatedprocessNode;
        
        do{
        	
        	/*remove the edge from the last annotated process node to the end node*/
        	apg.removeEdgeByTwoNode(temp_preceding_apn, eventNode2);
        	        	       	
           	/*create a new process node*/
        	AnnotatedProcessNode tempAnnotatedProcessNode = new AnnotatedProcessNode();
        	
            /*assign an 'dto_id' to the newly added annotated process node*/
        	tempAnnotatedProcessNode.dto().setId(IdWorker.getNextId());
            
            /*assign random text labels to the annotated process node*/
        	tempAnnotatedProcessNode.dto().setName(createRandomString());
        	
        	
        	String temp_apn_input = createRandomString();

        	List<String> temp_apn_inputs = new ArrayList<String>();
        	
        	temp_apn_inputs.add(temp_apn_input);
        	
        	tempAnnotatedProcessNode.dto().setInputList(temp_apn_inputs);
        	
        	List<String> temp_apn_outputs = new ArrayList<String>();
        	
            for(int i = 0; i < number_of_outputs; i++){
            	
                /*set the output of this annotated process node with a random string*/
                String temp_apn_output = new String("");
                temp_apn_output = createRandomString();
                temp_apn_outputs.add(temp_apn_output);
            }

        	tempAnnotatedProcessNode.dto().setOutputList(temp_apn_outputs);
        	
        	/*create a sequence node*/
        	SequenceDependencyNode tempSequence = expanderService.saveSequenceDependencyNode(temp_preceding_apn_id, tempAnnotatedProcessNode.getDTOId());
        	
        	/*add the 'tempAnnotatedProcessNode' and 'tempSequence' node to the annotated process graph 'apg'*/
        	apg.addNode(tempAnnotatedProcessNode);
        	apg.addNode(tempSequence);
        	
        	/*add the incoming edge and outgoing edge of 'tempSequence' into the graph*/
        	apg.addEdge(temp_preceding_apn, tempSequence);
        	apg.addEdge(tempSequence, tempAnnotatedProcessNode);
        	apg.addEdge(tempAnnotatedProcessNode, eventNode2); /*link the 'tempAnnotatedProcessNode' to the event node 'end'*/
        	
        	/*update the preceding annotated process node of newly added sequence node*/
        	temp_preceding_apn_id = tempAnnotatedProcessNode.getDTOId();
        	temp_preceding_apn = tempAnnotatedProcessNode;
        	 
        	
        	j++;
        	
        }while(j < totalnum);
               

        System.out.println("The number for all the nodes of the original process is: " + apg.getAllProcessElementNodes().size());
        return apg;
    }


    /*需修改，直接赋值ConditionDTO可能简单一些，而不需要通过赋值querystr，再在数据库里取ConditionDTO,因为测试用的process model并没存在数据库里，也不需要查询数据了
     * 7 Nov 2015 1200AM*/
    public static PluginInfoDTO getDebugpluginProcessDTO()  {
        PluginInfoDTO pluginDTO = new PluginInfoDTO();
//        Random rd = new Random();
//        int controlcode = rd.nextInt(4);
//        controlcode += 1001;
        pluginDTO.setControllFlowExtensionPattern(1002);
        pluginDTO.setId(IdWorker.getNextId());
        //pluginDTO.setDataFlowExtensionParttern(DataFlowExtensionPartternEnum.postfix.getValue());
        pluginDTO.setField(ProcessFieldEnum.hotel.getValue());
        pluginDTO.setPluginParam("");
        pluginDTO.setProcessId(IdWorker.getNextId());
        
        
        /*set 'eps_designator_type' as 'output_name', and the semantic relationship is set as 'sub-class-of' by default*/
        List<String> eps_designator_value = new ArrayList<String>();
        String output_name = new String("Electronic_Document");
        eps_designator_value.add(output_name);
        pluginDTO.setOutputList(eps_designator_value);
        
//        pluginDTO.setQueryStr1("apple"); //_condition表里面的id号码,需修改！！
//        pluginDTO.setQueryStr2("banana"); //_condition表里面的id号码
        
        pluginDTO.setProcessType(ProcessTypeEnum.epc.getValue());
        pluginDTO.setSearchExtensibilityPointType(SearchExtensibilityPointTypeEnum.interfaceQuery.getValue());
        pluginDTO.setCreateTime(new Date(new java.util.Date().getTime()));
        return pluginDTO;
    }


}
