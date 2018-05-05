<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page	import="java.io.*,java.sql.*,org.json.*,finalvariable.*,databaseaccess.*"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%
	Connection conn = Access.getConnection();
	try {
		JSONArray arr = new JSONArray();
		String sql = "SELECT uri,ontology_name,user,domain FROM ontology";
		ResultSet res = conn.createStatement().executeQuery(sql);
		while (res.next()) {
			String uri = res.getString("uri");
			String name = res.getString("ontology_name");
			// 不知道为什么这一句会报错，暂时先不考虑
			//String user = res.getString("user");
			String domain = res.getString("domain");
			JSONObject obj = new JSONObject();
			obj.put("uri", uri);
			obj.put("name", name);
			//obj.put("user", user);
			obj.put("domain", domain);
			arr.put(obj);
		}
		out.print(arr);
	} catch (Exception e) {
		e.printStackTrace();
		response.setStatus(500);
	} finally {
		conn.close();
	}
%>