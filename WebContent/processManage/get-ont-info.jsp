<%@ page import="java.util.*,java.sql.*,databaseaccess.*,ontology.*"%>
<%@page import="java.io.*,org.json.*"%>
<%@page import="com.hp.hpl.jena.ontology.OntClass"%>
<%@page import="com.hp.hpl.jena.ontology.OntModel"%>
<%@page import="com.hp.hpl.jena.rdf.model.ModelFactory"%>
<%@page import="com.hp.hpl.jena.util.iterator.ExtendedIterator"%>
<%@page import="finalvariable.*"%>
<%@ page contentType="text/plain;charset=utf-8" pageEncoding="utf-8"%>
<%
	String uri = request.getParameter("uri");
	Connection conn = Access.getConnection();
	OntologyDAO odao = new OntologyDAO(conn);
	try {
		OntologyDTO dto = odao.queryByUri(uri);
		String path = BasicPathVariable.ontologyLO;
		path = path + dto.getUser() + "//" + dto.getFileLocation();
		JSONArray arr = new JSONArray();
		OntModel model = ModelFactory.createOntologyModel();
		model.read(new FileInputStream(new File(path)), uri);
		ExtendedIterator iterator = model.listClasses();
		while (iterator.hasNext()) {
			JSONObject obj = new JSONObject();
			OntClass cls = (OntClass) iterator.next();
			String uri0 = cls.getURI();
			if (uri0 != null) {
				obj.put("URI",uri0);
				obj.put("namespace",uri);
				obj.put("localName", cls.getLocalName());
				if (cls.hasSuperClass()) {
					OntClass cls1 = cls.getSuperClass();
					String ns = cls.getNameSpace();
					String ns1 = cls1.getNameSpace();
					String uri1 = cls1.getURI();
					if (uri0.equals(uri1)) {
						obj.put("superClass", null);
					} else{
						obj.put("superClass", cls1.getURI());
					}
				} else {
					obj.put("superClass", null);
				}
				arr.put(obj);
			}
		}
		out.print(arr);
		System.out.println(arr);
	} catch (Exception e) {
		response.setStatus(500);
		e.printStackTrace();
	} finally {
		odao.closeDBConnection();
	}
%>