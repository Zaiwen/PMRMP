package org.sklse.owlseditor.wsdl;

import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindswap.wsdl.WSDLOperation;
import org.mindswap.wsdl.WSDLParameter;
import org.mindswap.wsdl.WSDLService;

public class WSDL2JSON {

	public static String transform(String url) throws Exception {
		JSONArray opArr = new JSONArray();
		WSDLService s = WSDLService.createService(url);
		List<WSDLOperation> ops = s.getOperations();
		for (int i = 0; i < ops.size(); i++) {
			JSONObject opObj = new JSONObject();
			WSDLOperation op = ops.get(i);
			String name = op.getName();
			String operationName = op.getOperationName();
			String portName = op.getPortName();
			String inputMessage = op.getInputMessageName();
			String outputMessage = op.getOutputMessageName();
			opObj.put("name", name);
			opObj.put("operationName", operationName);
			opObj.put("portName", portName);
			opObj.put("inputMessage", inputMessage);
			opObj.put("outputMessage", outputMessage);
			Vector inputs = op.getInputs();
			JSONArray inArr = new JSONArray();
			for (int j = 0; j < inputs.size(); j++) {
				WSDLParameter input = (WSDLParameter) inputs.get(j);
				String _name = input.getName().split("#")[1];
				String type = input.getType().toString();
				JSONObject inputObject = new JSONObject();
				inputObject.put("name", _name);
				inputObject.put("type", type);
				inArr.put(inputObject);
			}
			Vector outputs = op.getOutputs();
			JSONArray outArr = new JSONArray();
			for (int j = 0; j < outputs.size(); j++) {
				WSDLParameter output = (WSDLParameter) outputs.get(j);
				String _name = output.getName().split("#")[1];
				String type = output.getType().toString();
				JSONObject outputObject = new JSONObject();
				outputObject.put("name", _name);
				outputObject.put("type", type);
				outArr.put(outputObject);
			}
			opObj.put("input", inArr);
			opObj.put("output", outArr);
			opArr.put(opObj);
		}
		return opArr.toString();
	}

	public static void main(String[] args) throws Exception {
		String url = "https://www.callfire.com/service/CampaignService?wsdl";
		System.out.println(transform(url));
	}

}
