<%@page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.sklse.processRegister.processGraph.*,org.sklse.processRegister.expander.*,org.sklse.processRegister.expander.impl.*,nl.tue.tm.is.epc.*,ontology.IdWorker,databaseaccess.Access,java.util.*,java.sql.*,java.net.*,org.json.*,java.io.*,finalvariable.*,com.google.gson.*,org.sklse.owlseditor.xml.*,org.sklse.owlseditor.json.*,org.sklse.processRegister.db.dao.*,org.sklse.processRegister.db.dto.*,org.sklse.processRegister.db.services.*,org.sklse.processRegister.db.services.impl.*"%>
<%
	try{
	String path = BasicPathVariable.pluginPath;
	String isv = (String)session.getAttribute("ISVusername");
	String name = request.getParameter("Name");
	String processDTOname=request.getParameter("ProcessDTOname");
	String epml=request.getParameter("epml");
	path += isv+"\\";
	path += name + "\\";
	String json = request.getParameter("json");
	new File(path).mkdirs();
	File jsonFile = new File(path + "\\extension.json");
	if (jsonFile.exists()) {
		jsonFile.delete();
	}
	File epmlFile = new File(path + "\\extension.epml");
	if (epmlFile.exists()) {
		epmlFile.delete();
	}
	//写JSON文件
	BufferedOutputStream fout = new BufferedOutputStream(
	new FileOutputStream(jsonFile));
	byte bb[] = json.getBytes();
	fout.write(bb);
	fout.close();
	BufferedOutputStream bout = new BufferedOutputStream(
	new FileOutputStream(epmlFile));
	byte ee[] = epml.getBytes();
	bout.write(ee);
	bout.close();
	// 写插件
	/*
	String opath=BasicPathVariable.processPath+provider+"\\"+process+"\\process.owl";
	String jpath=BasicPathVariable.processPath+provider+"\\"+process+"\\process.json";
	File file1=new File(jpath);
	JModel model=new Gson().fromJson(new FileReader(file1), JModel.class);
	XPlugin plug=new Gson().fromJson(json.trim(), XPlugin.class);
	new XMLWriter(plug,path+"\\extension.xml");
	new newJSONWriter(opath,model,plug);
	JSONObject obj=new getJSON(model).get();
	out.print(obj);
	*/
	//保存至数据库pluginprocess
	String sql="select id from isvuser where name=?";
	Access ac=new Access();
	ac.connDB("bpep");
	Connection conn=ac.getCon();
	PreparedStatement ps=conn.prepareStatement(sql);
	ps.setString(1, isv);
	ResultSet rs=ps.executeQuery();
	long author=0l;
	if(rs.next()){
		author=Long.parseLong(rs.getString(1));
	}
	ps.close();
	ac.closeDB();
	//把json中的信息解析成PluginInfoDTO对象
	PluginInfoDTO pluginDTO=new Gson().fromJson(json.trim(), PluginInfoDTO.class);
	pluginDTO.setAuthor(author);
	java.sql.Date date=new java.sql.Date(new java.util.Date().getTime());
	pluginDTO.setCreateTime(date);
	//ProcessDTO是插入的流程的对象
	ProcessDTO processDTO=new ProcessDTO();
	processDTO.setName(processDTOname);
	processDTO.setUri(pluginDTO.getUrl());
	if(pluginDTO.getQueryStr1()!=null){
		processDTO.setPreconditionid(Long.parseLong(pluginDTO.getQueryStr1()));
	}
	if(pluginDTO.getQueryStr2()!=null){
		processDTO.setPostconditionid(Long.parseLong(pluginDTO.getQueryStr2()));
	}
	pluginDTO.setProcessDTO(processDTO);
	PluginInfoService pis=new PluginInfoServiceImpl();
	//保存
	long pluginid=pis.savePluginInfo(pluginDTO, null);
	//将epml转化为epc对象
	EPML epmls=EPML.loadEPML(epmlFile);
	Map<String,EPC> epcs=new HashMap<String,EPC>();
	epcs=epmls.getEpcs();
	ProcessGraphIOService pgio=new ProcessGraphIOServiceImpl();
	ProcessGraphExpander pge=new ProcessGraphExpanderImpl();
	for(Map.Entry<String,EPC> entry : epcs.entrySet()){
		String key=entry.getKey();
		EPC epc=entry.getValue();
		//将EPC转化为ProcessGraph
		ProcessGraph pg=pgio.EpcToProcessGraph(epc);
		//寻找扩展点
		List<ProcessNode> processNodes=pge.getExtensibilityPoint(pg, pluginDTO);
		//保存
		pge.expandProcessGraph(pg, processNodes, pluginDTO);
	}
    JSONObject obj = new JSONObject();
    obj.put("extensionid", pluginid+"");
    out.print(obj);
}catch(Exception e){
	e.printStackTrace();
    JSONObject obj = new JSONObject();
    obj.put("errors", "出现异常...");
    out.print(obj);
}
%>