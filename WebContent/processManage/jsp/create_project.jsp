<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%-- 该方法用于创建一个工程 --%>
<%-- 输入：项目名 --%>
<%-- 输出： --%>
<%-- 	0:项目名不合法 --%>
<%-- 	1:会话超时 --%>
<%--	2:无法连接数据库 --%>
<%--	3:项目已存在 --%>
<%--	4:创建成功 --%>
<%
	String name = request.getParameter("name");
	if (name == null || "".equals(name)) {
		out.print(0);
		return;
	}

	if (session.getAttribute("username") == null) {
		out.print(1);
		return;
	}

	try {
		final String DB_DRIVER = "org.sqlite.JDBC";
		final String DB_URL = "jdbc:sqlite:pp.db";
		java.sql.Connection connection = null;
		java.sql.Statement statement = null;
		java.sql.ResultSet result = null;
		String sql1 = "SELECT * FROM project";
		Class.forName(DB_DRIVER);
		connection = java.sql.DriverManager.getConnection(DB_URL,
				new Properties());
		if (connection.isClosed()) {
			out.print(3);
			return;
		}

		statement = connection.createStatement();
		result = statement.executeQuery(sql1);

	} catch (Exception e) {
		e.printStackTrace();
		out.print(1);
		return;
	}
%>