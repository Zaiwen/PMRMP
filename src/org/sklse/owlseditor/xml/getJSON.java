package org.sklse.owlseditor.xml;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sklse.owlseditor.json.JModel;

public class getJSON {
	JSONArray array=new JSONArray();
	JModel model=null;
	JSONObject outData = new JSONObject();
	
	
	public  getJSON(JModel model){
		this.model=model;
		try {
			outData.put("sequence", this.addSequence());
			outData.put("anyOrder", this.addAnyOrder());
			outData.put("choice", this.addChoice());
			outData.put("splitJoin", this.addSplitJoin());
			outData.put("perform", this.addPerform());
			outData.put("produce", this.addProduce());
			outData.put("atomicProcess", this.addAtomicProcess());
			outData.put("compositeProcess", this.addCompositeProcess());
			outData.put("input", this.addInput());
			outData.put("output", this.addOutput());
			outData.put("inputBinding", this.addInputBinding());
			outData.put("outputBinding", this.addOutputBinding());
			outData.put("inputMessageMap", this.addInputMessageMap());
			outData.put("outputMessageMap", this.addOutputMessageMap());
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		System.out.print(outData.toString());
	}
	
	public JSONObject get(){
		return this.outData;
	}
	
	public JSONArray addSequence() throws JSONException{
		JSONArray sequence=new JSONArray();
		
		for(int i=0;i<model.getSequence().length;i++){
			JSONObject element=new JSONObject();
			element.put("ID",this.model.getSequence()[i].getID());
			JSONArray components=new JSONArray();
			for(int j=0;j<this.model.getSequence()[i].getComponents().length;j++){
				components.put(this.model.getSequence()[i].getComponents()[j]);
			}
			
			element.put("components", components);
			
			sequence.put(element);
		}
		
		return sequence;
	}
	public JSONArray addAnyOrder() throws JSONException{
		JSONArray anyOrder=new JSONArray();
		
		for(int i=0;i<model.getAnyOrder().length;i++){
			JSONObject element=new JSONObject();
			
			element.put("ID",this.model.getAnyOrder()[i].getID());
			JSONArray components=new JSONArray();
			for(int j=0;j<this.model.getAnyOrder()[i].getComponents().length;j++){
				components.put(this.model.getAnyOrder()[i].getComponents()[j]);
			}
			element.put("components", components);
			anyOrder.put(element);
		}
		
		return anyOrder;
	}
	public JSONArray addChoice() throws JSONException{
		JSONArray choice=new JSONArray();
		
		for(int i=0;i<model.getChoice().length;i++){
			JSONObject element=new JSONObject();
			
			element.put("ID",this.model.getChoice()[i].getID());
			JSONArray components=new JSONArray();
			for(int j=0;j<this.model.getChoice()[i].getComponents().length;j++){
				components.put(this.model.getChoice()[i].getComponents()[j]);
			}
			element.put("components", components);
			choice.put(element);
		}
		
		return choice;
	}
	public JSONArray addSplitJoin() throws JSONException{
		JSONArray splitJoin=new JSONArray();
		
		for(int i=0;i<model.getSplitJoin().length;i++){
			JSONObject element=new JSONObject();
			
			element.put("ID",this.model.getSplitJoin()[i].getID());
			JSONArray components=new JSONArray();
			for(int j=0;j<this.model.getSplitJoin()[i].getComponents().length;j++){
				components.put(this.model.getSplitJoin()[i].getComponents()[j]);
			}
			element.put("components",components);
			splitJoin.put(element);
		}
		
		return splitJoin;
	}
	public JSONArray addPerform() throws JSONException{
		JSONArray Perform=new JSONArray();
		
		for(int i=0;i<model.getPerform().length;i++){
			JSONObject element=new JSONObject();
			
			
			element.put("ID",this.model.getPerform()[i].getID());
			//element.put("qureyResult",this.model.getPerform()[i].isQureyResult());
			element.put("extended", this.model.getPerform()[i].isExtended());
			element.put("procress", this.model.getPerform()[i].getProcess());

			JSONArray inputBinding=new JSONArray();
			for(int j=0;j<this.model.getPerform()[i].getInputBinding().length;j++){
				inputBinding.put(this.model.getPerform()[i].getInputBinding()[j]);
			}
						element.put("inputBinding",inputBinding);
			Perform.put(element);
		}
		
		return Perform;
	}
	public JSONArray addProduce() throws JSONException{
		JSONArray produce=new JSONArray();
		
		for(int i=0;i<model.getProduce().length;i++){
			JSONObject element=new JSONObject();
			
		
			element.put("ID",this.model.getProduce()[i].getID());
			JSONArray outputBinding=new JSONArray();
			for(int j=0;j<this.model.getProduce()[i].getOutputBinding().length;j++){
				outputBinding.put(this.model.getProduce()[i].getOutputBinding()[j]);
			}
			
			element.put("outputBinding", outputBinding);
			produce.put(element);
		}
		
		return produce;
	}
	public JSONArray addAtomicProcess() throws JSONException{
		JSONArray atomicProcess=new JSONArray();
		
		for(int i=0;i<model.getAtomicProcess().length;i++){
			JSONObject element=new JSONObject();
			

			
			
			element.put("ID",this.model.getAtomicProcess()[i].getID());
			element.put("portType",this.model.getAtomicProcess()[i].getPortType());
			element.put("operation",this.model.getAtomicProcess()[i].getOperation());
			element.put("inputMessageName",this.model.getAtomicProcess()[i].getInputMessageName());
			element.put("outputMessageName",this.model.getAtomicProcess()[i].getInputMessageName());
			
			JSONArray input=new JSONArray();
			for(int j=0;j<this.model.getAtomicProcess()[i].getInput().length;j++){
				input.put(this.model.getAtomicProcess()[i].getInput()[j]);
			}
			JSONArray output=new JSONArray();
			for(int j=0;j<this.model.getAtomicProcess()[i].getOutput().length;j++){
				output.put(this.model.getAtomicProcess()[i].getOutput()[j]);
			}
			JSONArray inputMessageMap=new JSONArray();
			for(int j=0;j<this.model.getAtomicProcess()[i].getInputMessageMap().length;j++){
				inputMessageMap.put(this.model.getAtomicProcess()[i].getInputMessageMap()[j]);
			}
			JSONArray outputMessageMap=new JSONArray();
			for(int j=0;j<this.model.getAtomicProcess()[i].getOutputMessageMap().length;j++){
				outputMessageMap.put(this.model.getAtomicProcess()[i].getOutputMessageMap()[j]);
			}
			element.put("input", input);
			element.put("output",output);
			element.put("inputMessageMap", inputMessageMap);
			element.put("outputMessageMap", outputMessageMap);
			
			atomicProcess.put(element);
		}
		
		return atomicProcess;
	}
	
	public JSONObject addCompositeProcess() throws JSONException{
		
		JSONObject compositeProcess=new JSONObject();
			
	
			
			
			compositeProcess.put("ID",this.model.getCompositeProcess().getID());
			compositeProcess.put("composeOf",this.model.getCompositeProcess().getComposeOf());
			
			JSONArray input=new JSONArray();
			for(int j=0;j<this.model.getCompositeProcess().getInput().length;j++){
				input.put(this.model.getCompositeProcess().getInput()[j]);
			}
			JSONArray output=new JSONArray();
			for(int j=0;j<this.model.getCompositeProcess().getOutput().length;j++){
				output.put(this.model.getCompositeProcess().getOutput()[j]);
			}
			
			compositeProcess.put("input", input);
			compositeProcess.put("output", output);
			
		
		
		return compositeProcess;
	}
	
	public JSONArray addInput() throws JSONException{
		JSONArray input=new JSONArray();
		
		for(int i=0;i<model.getInput().length;i++){
			JSONObject element=new JSONObject();
			
			
			element.put("ID",this.model.getInput()[i].getID());
			element.put("type", this.model.getInput()[i].getType());
			
			input.put(element);
		}
		
		return input;
	}
	public JSONArray addOutput() throws JSONException{
		JSONArray output=new JSONArray();
		
		for(int i=0;i<model.getOutput().length;i++){
			JSONObject element=new JSONObject();
			
			element.put("ID",this.model.getOutput()[i].getID());
			element.put("type", this.model.getOutput()[i].getType());
			
			output.put(element);
		}
		
		return output;
	}
	public JSONArray addInputBinding() throws JSONException{
		JSONArray inputBinding=new JSONArray();
		
		for(int i=0;i<model.getInputBinding().length;i++){
			JSONObject element=new JSONObject();
			
			
			
			
			element.put("ID",this.model.getInputBinding()[i].getID());
			element.put("fromProcess",this.model.getInputBinding()[i].getFromProcess());
			element.put("theVar",this.model.getInputBinding()[i].getTheVar());
			element.put("toParam",this.model.getInputBinding()[i].getToParam());
			
			inputBinding.put(element);
		}
		
		return inputBinding;
	}
	
	public JSONArray addOutputBinding() throws JSONException{
		JSONArray outputBinding=new JSONArray();
		
		for(int i=0;i<model.getOutputBinding().length;i++){
			JSONObject element=new JSONObject();
			element.put("ID",this.model.getOutputBinding()[i].getID());
			
			element.put( "fromProcess",this.model.getOutputBinding()[i].getFromProcess());
			element.put("theVar",this.model.getOutputBinding()[i].getTheVar());
			element.put("toParam",this.model.getOutputBinding()[i].getToParam());
			
			outputBinding.put(element);
		}
		
		return outputBinding;
	}
	
	public JSONArray addInputMessageMap() throws JSONException{
		JSONArray inputMessageMap=new JSONArray();
		
		for(int i=0;i<model.getInputMessageMap().length;i++){
			JSONObject element=new JSONObject();
			
		
			
			element.put("ID",this.model.getInputMessageMap()[i].getID());
			element.put( "groundingParameter",this.model.getInputMessageMap()[i].getGroundingParameter());
			element.put("owlsParameter",this.model.getInputMessageMap()[i].getOwlsParameter());
			
			inputMessageMap.put(element);
		}
		
		return inputMessageMap;
	}
	
	public JSONArray addOutputMessageMap() throws JSONException{
		JSONArray outputMessageMap=new JSONArray();
		
		for(int i=0;i<model.getOutputMessageMap().length;i++){
			JSONObject element=new JSONObject();
		
	
			
			element.put("ID",this.model.getOutputMessageMap()[i].getID());
			element.put( "groundingParameter",this.model.getOutputMessageMap()[i].getGroundingParameter());
			element.put("owlsParameter",this.model.getOutputMessageMap()[i].getOwlsParameter());
			
			outputMessageMap.put(element);
		}
		
		return outputMessageMap;
	}
	

}
