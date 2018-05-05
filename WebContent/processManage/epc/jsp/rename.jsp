<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.net.*,java.util.*,org.sklse.epceditor.*"%>
<%
	String user=request.getParameter("user");
	String oldName=request.getParameter("oldname");
	String newName=request.getParameter("newName");
	out.print(EPC.rename(user,oldName,newName));
%>