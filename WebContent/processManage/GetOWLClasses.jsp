<%@ page import="java.util.*,java.sql.*,databaseaccess.*,ontology.*"%>
<%@page import="java.io.*"%>
<%@page import="com.hp.hpl.jena.ontology.OntClass"%>
<%@page import="com.hp.hpl.jena.ontology.OntModel"%>
<%@page import="com.hp.hpl.jena.rdf.model.ModelFactory"%>
<%@page import="com.hp.hpl.jena.util.iterator.ExtendedIterator"%>
<%@page import="finalvariable.*"%>
<%@ page contentType="text/plain;charset=utf-8" pageEncoding="utf-8"%>
<%
	Connection conn = Access.getConnection();
	OntologyDAO odao = new OntologyDAO(conn);
	List<OntologyDTO> dtos = odao.queryAll();
	Iterator<OntologyDTO> iterator = dtos.iterator();
	OntModel m = ModelFactory.createOntologyModel();
	while (iterator.hasNext()) {
		OntologyDTO dto = iterator.next();
		String basePath = BasicPathVariable.ontologyLO + dto.getUser();
		File dir = new File(basePath);
		dir.mkdirs();
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			m.read(new FileReader(file), "htpp://www.example.com");
		}
	}
	ExtendedIterator itr = m.listNamedClasses();
	out.println("<list>");
	while (itr.hasNext()) {
		OntClass cls = (OntClass) itr.next();
		String uri = cls.getURI();
		out.print("<item ");
		if (cls.hasSuperClass()) {
			String parent = cls.getSuperClass().getURI();
			out.print("parent=\"" + parent + "\"");
		}
		out.print(">\n" + uri + "\n</item>\n");
	}
	odao.closeDBConnection();
	out.println("</list>");
%>