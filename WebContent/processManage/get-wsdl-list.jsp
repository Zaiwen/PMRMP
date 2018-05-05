<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.*"%>
<%@page import="javax.xml.transform.*"%>
<%@page import="javax.xml.transform.dom.*"%>
<%@page import="javax.xml.transform.stream.*"%>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="databaseaccess.Access"%>
<%@page import="org.sklse.owlseditor.util.*"%>
<%@page import="org.json.*"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	try {
		Access DA = new Access();
		DA.connDB("bpep");
		ResultSet res;
		String sql = "select * from serviceinfo";
		res = DA.executeSelectSql(sql);
		JSONArray arr = new JSONArray();
		while (res.next()) {
			JSONObject obj = new JSONObject();
			obj.put("id", res.getString(1));
			obj.put("name", res.getString(2));
			obj.put("url",res.getString(3));
			obj.put("domain",res.getString(4));
			arr.put(obj);
		}
		out.println(arr.toString());
		DA.closeDB();
	} catch (Exception e) {
		System.out.println(e);
	}
%>