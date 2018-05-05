<%@page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page
	import="java.util.*,java.net.*,org.json.*,java.io.*,finalvariable.*,com.google.gson.*,org.sklse.owlseditor.xml.*,org.sklse.owlseditor.json.*"%>
<%
	String path = BasicPathVariable.pluginPath;
	String process = request.getParameter("Process");
	String provider = request.getParameter("Provider");
	String name = request.getParameter("Name");
	System.out.println(name);
	String json = request.getParameter("json");
	System.out.println(json);
	String plugins[] = new Gson().fromJson(json.trim(), String[].class);
	
	String opath = BasicPathVariable.processPath + provider + "\\"
			+ process + "\\process.owl";
	String jpath = BasicPathVariable.processPath + provider + "\\"
			+ process + "\\process.json";
	File file1 = new File(jpath);
	JModel model = new Gson().fromJson(new FileReader(file1),
			JModel.class);	
	for (int i = 0; i < plugins.length; i += 2) {
		String ppath = BasicPathVariable.pluginPath + plugins[i]
				+ "\\" + plugins[i + 1] + "\\extension.json";
		File file = new File(ppath);
		StringBuilder fileContent = new StringBuilder();
		BufferedReader rd = new BufferedReader(new FileReader(file));
		String str = null;
		while ((str = rd.readLine()) != null) {
			fileContent.append(str);
		}
		rd.close();
		XPlugin plug = new Gson().fromJson(fileContent.toString()
				.trim(), XPlugin.class);
		new newJSONWriter(opath, model, plug);
	}
	JSONObject obj = new getJSON(model).get();
	out.print(obj);
	// 写JSON文件
	String org=request.getParameter("ORGusername");
	opath = BasicPathVariable.extProcessPath + org + "\\"+ name;
	new File(opath).mkdirs();
	opath=opath + "\\process.owl";
	jpath = BasicPathVariable.extProcessPath + org + "\\"+ name;
	new File(jpath).mkdirs();
	jpath=jpath + "\\process.json";
	BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(new File(jpath)));
	byte bb[] = obj.toString().getBytes();
	fout.write(bb);
	fout.close();
	// 写OWL
	FileOutputStream fo=new FileOutputStream(new File(opath));
	new OWLSWriter(model).write(fo);
	fo.close();
	fo.flush();
%>