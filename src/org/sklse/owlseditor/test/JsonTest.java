package org.sklse.owlseditor.test;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTest {

	public static void main(String[] args) {
		try {
			JSONObject j = new JSONObject();
			j.put("Boolean",true);
			j.put("Double",2D);
			j.put("Integer", 1234);
			j.put("Long",2L);
			j.put("Object", new URI("http://www.baidu.com"));
			System.out.println("JSONObject Test:");
			System.out.println(j.toString());
			JSONArray a=new JSONArray();
			a.put("Hello World!");
			a.put(123);
			a.put(true);
			System.out.println("JSONArray Test:");
			System.out.println(a);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
