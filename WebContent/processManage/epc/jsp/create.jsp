<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*,java.util.*,org.sklse.epceditor.*"%>
<%
	String user=request.getParameter("user");
	String name=request.getParameter("name");
	out.print(EPC.create(user,name));
%>