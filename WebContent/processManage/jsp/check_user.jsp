<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--@ page import="edu.whu.apd.database.*"--%>
<%--@ page import="edu.whu.apd.bean.*"--%>
<%-- 此方法用于登录时检查用户名与密码 --%>
<%-- 输入：					 	--%>
<%-- 	username:用户名 			--%>
<%--	password:密码 			--%>
<%-- 输出：						--%>
<%--	0:用户名不存在 			--%>
<%--	1:用户名与密码不匹配 		--%>
<%--	2:用户未通过审核 			--%>
<%--	3:连接数据库时出现故障		--%>
<%--	4:登录成功				--%>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	final String DB_DRIVER = "org.sqlite.JDBC";
	final String DB_URL = "jdbc:sqlite:pp.db";
	java.sql.Connection connection = null;
	java.sql.Statement statement = null;
	java.sql.ResultSet result = null;
	String sql = "SELECT * FROM ppuser";
	if (username == null || "".equals(username)) {
		out.print(0);
		return;
	}

	try {
		Class.forName(DB_DRIVER);
		connection = java.sql.DriverManager.getConnection(DB_URL,
				new Properties());
		if (connection.isClosed()) {
			out.print(3);
			return;
		}

		statement = connection.createStatement();
		result = statement.executeQuery(sql);
		while (result.next()) {
			String name = result.getString("name");
			String pass = result.getString("pass");
			int permission = result.getInt("perm");
			if (name != null && name.equals(username)) {
				if (pass != null && pass.equals(password)) {
					if (permission == 1) {
						session.setAttribute("username",username);
						out.print(4);
						return;
					}
					out.print(2);
					return;
				}
				out.print(1);
				return;
			}
		}
		out.print(0);
		return;
	} catch (Exception e) {
		e.printStackTrace();
		out.print(3);
	}
	// 	if (!UserDB.isUserExist(username)) {
	// 		out.print(0);
	// 	} else if (!UserDB.isUserExist(username, password)) {
	// 		out.print(1);
	// 	} else if (UserDB.getUserPermission(username, password) == UserBean.USER_UNAUDITED) {
	// 		out.print(2);
	// 	} else {
	// 		session.setAttribute("username", username);
	// 		out.print(3);
	// 	}
%>