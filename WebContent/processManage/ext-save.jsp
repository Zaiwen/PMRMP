<%@page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.net.*,org.json.*,java.io.*,finalvariable.*,com.google.gson.*,org.sklse.owlseditor.xml.*,org.sklse.owlseditor.json.*"%>
<%
	String path = BasicPathVariable.pluginPath;
	String process = request.getParameter("Process");
	String provider = request.getParameter("Provider");
	String isv = session.getAttribute("ISVusername") + "\\";
	String name = request.getParameter("Name");
	path += isv;
	path += name + "\\";
	String json = request.getParameter("json");
	new File(path).mkdirs();
	File jsonFile = new File(path + "\\extension.json");
	if (jsonFile.exists()) {
		jsonFile.delete();
	}
	//写JSON文件
	BufferedOutputStream fout = new BufferedOutputStream(
			new FileOutputStream(jsonFile));
	byte bb[] = json.getBytes();
	fout.write(bb);
	fout.close();
	// 写插件
	String opath=BasicPathVariable.processPath+provider+"\\"+process+"\\process.owl";
	String jpath=BasicPathVariable.processPath+provider+"\\"+process+"\\process.json";
	File file1=new File(jpath);
	JModel model=new Gson().fromJson(new FileReader(file1), JModel.class);
	XPlugin plug=new Gson().fromJson(json.trim(), XPlugin.class);
	new XMLWriter(plug,path+"\\extension.xml");
	new newJSONWriter(opath,model,plug);
	JSONObject obj=new getJSON(model).get();
	out.print(obj);
%>