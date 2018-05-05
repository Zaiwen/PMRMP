<%@page import="org.sklse.owlseditor.wsdl.*"%>
<%@ page language="java" contentType="text/plain; charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	try {
		String url = request.getQueryString();
		out.print(WSDL2JSON.transform(url));
	} catch (Exception e) {
		response.setStatus(500);
	}
%>