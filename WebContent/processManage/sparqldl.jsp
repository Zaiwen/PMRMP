<%@ page
	import="java.util.*,org.json.*,java.sql.*,ontology.*,databaseaccess.*,finalvariable.*"
	pageEncoding="UTF-8"%>
<%@ page import="de.derivo.sparqldlapi.*,com.google.gson.*"%>
<%@ page import="org.sklse.owlseditor.sparqldl.*"%>
<%@ page import="org.semanticweb.owlapi.apibinding.*"%>
<%@ page import="org.semanticweb.owlapi.model.*"%>
<%@ page import="org.semanticweb.owlapi.reasoner.*"%>
<%@ page import="org.semanticweb.owlapi.reasoner.structural.*"%>
<%-- SPARQL-DL查询 --%>
<%-- 第一步、取得HTTP请求的参数 --%>
<%
	String provider = request.getParameter("Provider");
	String process = request.getParameter("Process");
	String input = request.getParameter("Input");
	String output = request.getParameter("Output");
	String[] inputs = new Gson().fromJson(input, String[].class);
	String[] outputs = new Gson().fromJson(output, String[].class);
	String path = BasicPathVariable.processPath;
	path = path + provider + "//";
	path = path + process + "//process.owl";
	JSONObject obj = new JSONObject();
	JSONArray arr = new JSONArray();
	String str[] = VariableQuery.query(path, inputs, outputs);
	for (int i = 0; i < str.length; i++) {
		arr.put(str[i]);
	}
	obj.put("Perform", arr);
	obj.put("SPARQL-DL", VariableQuery.getSparqldl());
	out.print(obj);
%>

