package nl.tue.tm.is.epc;

import org.utils.JsonUtil;

public class Connector extends Node {

	public static final String ANDLabel = "AND";
	public static final String ORLabel = "OR";
	public static final String XORLabel = "XOR";
	
	public Connector() {
	}
	public Connector(String id){
		this.id = id;
	}
	public Connector(String id, String label){
		this.id = id;
		this.name = label;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
