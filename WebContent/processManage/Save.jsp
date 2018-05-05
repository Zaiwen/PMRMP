<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*,com.google.gson.*,finalvariable.*"%>
<%@page import="org.sklse.owlseditor.json.*"%>
<%
	if (session.getAttribute("PPusername") == null) {
		out.println(false);
		return;
	}
%> 
<%
	
	String processName = session.getAttribute("processName").toString();
	
	System.out.println("json to be created");
	String filePath = BasicPathVariable.processPath
			+ session.getAttribute("PPusername") + "//" + processName;
	String str = request.getParameter("content");
	new File(filePath).mkdirs();
	File jsonFile = new File(filePath + "\\process.json");
	if (jsonFile.exists()) {
		jsonFile.delete();
	}
	str = URLDecoder.decode(str);
	System.out.println(str);
	//写JSON文件
	StringReader sr = new StringReader(str);
	BufferedOutputStream fout = new BufferedOutputStream(
			new FileOutputStream(jsonFile));
	byte bb[] = str.getBytes();
	fout.write(bb);
	
	fout.close();
	// 写owl文件
	/* Gson gson = new Gson();
	File owlFile = new File(filePath + "\\process.owl");
	if (owlFile.exists()) {
		owlFile.delete();
	}
	JModel model = gson.fromJson(str, JModel.class);
	BufferedOutputStream o = new BufferedOutputStream(
			new FileOutputStream(owlFile));
	new OWLSWriter(model).write(o);
	o.close();
	o.flush();   */ 
%>