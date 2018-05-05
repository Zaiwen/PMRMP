<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.*"%>
<%@page import="javax.xml.transform.*"%>
<%@page import="javax.xml.transform.dom.*"%>
<%@page import="javax.xml.transform.stream.*"%>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="databaseaccess.Access"%>
<%@page import="org.sklse.owlseditor.util.*"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	try {
		Access DA = new Access();
		DA.connDB("bpep");
		ResultSet res;
		String sql = "select wsdllocation from serviceinfo";
		res = DA.executeSelectSql(sql);
		out.println("<ol>");
		while (res.next()) {
			out.print("<li class='item'>");
			out.print(Utils.getXml(res.getString(1)));
			out.println("</li>");
		}
		out.println("</ol>");
		DA.closeDB();
	} catch (Exception e) {
		System.out.println(e);
	}
%>