<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.sql.*,ontology.*,databaseaccess.*"%>
<%@ page import="org.json.*"%>
<%
	try {
		Connection conn = Access.getConnection();
		OntologyDAO odao = new OntologyDAO(conn);
		List<OntologyDTO> dtos = odao.queryAll();
		OntologyDTO dto = null;
		Iterator<OntologyDTO> iterator = dtos.iterator();
		JSONArray arr = new JSONArray();
		while (iterator.hasNext()) {
			JSONObject obj=new JSONObject();
			dto = iterator.next();
			obj.put("uri",dto.getUri());
			obj.put("user",dto.getUser());
			obj.put("file",dto.getFileLocation());
			obj.put("description",dto.getDescription());
			obj.put("domain",dto.getDomain());
			obj.put("id",dto.getId());
			obj.put("name",dto.getOntologyName());
			arr.put(obj);
		}
		odao.closeDBConnection();
		conn.close();
		out.print(arr.toString());
	} catch (Exception e) {
		response.setStatus(500);
	}
%>