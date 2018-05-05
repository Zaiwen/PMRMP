<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page	import="java.io.*,java.sql.*,org.json.*,finalvariable.*,databaseaccess.*"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%
	String uri=request.getParameter("uri");
	Connection conn = Access.getConnection();
	try {
		JSONArray arr = new JSONArray();
		String sql = "SELECT key_atomic_id FROM ontology_component WHERE type='Class' AND namespace='"+uri+"'";
		ResultSet res = conn.createStatement().executeQuery(sql);
		while (res.next()) {
			String str = res.getString("key_atomic_id");
			arr.put(str);
		}
		out.print(arr);
	} catch (Exception e) {
		e.printStackTrace();
		response.setStatus(500);
	} finally {
		conn.close();
	}
%>