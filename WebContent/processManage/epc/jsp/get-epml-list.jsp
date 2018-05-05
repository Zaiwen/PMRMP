<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.sql.*,java.util.*,databaseaccess.*,org.json.*"%>
<%
	if(session.getAttribute("PPusername")!=null){
		JSONArray arr = new JSONArray();
		Access ac=new Access();
		String user = session.getAttribute("PPusername").toString();
		ac.connDB("bpep");
		String sql = "select a.id,a.name,a.create_time,a.version from original_process as a , (select name,max(version) as version from original_process group by name) as b where a.name=b.name and a.version=b.version and a.user=? and a.process_type=?;";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, user);
		st.setString(2,"epc");
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			JSONObject obj=new JSONObject();
			obj.put("id",rs.getLong(1));
			obj.put("name", rs.getString(2));
			obj.put("time", rs.getDate(3) + " " +rs.getTime(3));
			obj.put("version",rs.getString(4));
			arr.put(obj);
		}
		ac.closeDB();
		out.print(arr);
	}
	/* File file =new File(BasicPathVariable.processPath+ "epml//");
	File []files=file.listFiles();
	JSONArray arr=new JSONArray();
	for(int i=0;i<files.length;i++){
		arr.put(files[i].getName());
	}
	out.print(arr.toString()); */
%>