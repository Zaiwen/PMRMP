package nl.tue.tm.is.epc;

import org.utils.JsonUtil;

public class Function extends Node {

	public Function() {
	}
	public Function(String id){
		this.id = id;
	}
	public Function(String id, String label){
		this.id = id;
		this.name = label;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
