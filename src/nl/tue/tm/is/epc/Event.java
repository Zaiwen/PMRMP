package nl.tue.tm.is.epc;

import org.utils.JsonUtil;

public class Event extends Node {

	public Event() {
	}
	public Event(String id){
		this.id = id;
	}
	public Event(String id, String label){
		this.id = id;
		this.name = label;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
