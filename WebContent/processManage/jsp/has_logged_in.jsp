<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("username") == null) {
		out.print(0);
	} else {
		out.print(1);
	}
%>