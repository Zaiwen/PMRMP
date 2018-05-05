<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="utf-8"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册信息</title>
<script type="text/javascript" src="js/rightbutton.js"></script>
</head>
<body>
<div id="content" align="center">
		<%
		    try{
			String security = request.getParameter("check");
			security = security.trim();
			if (!security.equalsIgnoreCase((String) session
					.getAttribute("randomCode"))) {
				out.print("<script>alert('验证码不正确!');window.location.href='/BPEP/userManage/register.jsp'</script>");
				return;
			} else {
				String name = request.getParameter("username");
				name = name.trim();
				String password = request.getParameter("userpassword");
				password = password.trim();
				if (name == null || name.equals("") || password == null
						|| password.equals("")) {
					out.print("<script>alert('用户名或密码不能为空!');window.location.href='/BPEP/userManage/register.jsp'</script>");
					return;
				} else {
					String reasons = request.getParameter("reasons");
					reasons = reasons.trim();
					if (reasons == null || reasons.equals("")) {
						out.print("<script>alert('请输入注册理由,以方便管理员认证!');window.location.href='/BPEP/userManage/register.jsp'</script>");
						return;
					} else {
						ResultSet rs;
						String usertype = request.getParameter("usertype");
						String databasetype = "Users";
						String sql1 = "select * from " + usertype
								+ "user  where name=" + "'" + name + "'";
						DA.connDB(databasetype);
						rs = DA.executeSelectSql(sql1);
						if (rs.next()) {
							if (usertype.equals("ISV")) {
								out.println("<br/><br/><br/><h2>Independent Software Vender注册失败!<br/><br/>(用户名已存在!)</h2>");
								out.print("<script>alert('Independent Software Vender注册失败!(用户名已存在!)');window.location.href='/BPEP/userManage/register.jsp'</script>");
								return;
							} else if (usertype.equals("PP")) {
								out.println("<br/><br/><br/><h2>Process Provider注册失败!<br/><br/>(用户名已存在！)</h2>");
								out.print("<script>alert('Process Provider注册失败!(用户名已存在!)');window.location.href='/BPEP/userManage/register.jsp'</script>");
								return;
							} else if (usertype.equals("ORG")) {
								out.println("<br/><br/><br/><h2>Organizations注册失败!<br/><br/>(用户名已存在！)</h2>");
								out.print("<script>alert('Organizations注册失败!(用户名已存在!)');window.location.href='/BPEP/userManage/register.jsp'</script>");
								return;
							}
						} else {
							if (usertype.equals("ISV")) {
								String sql2 = "INSERT INTO ISVuser VALUES"
										+ "(null," + "'" + name + "','"
										+ password + "','" + "0" + "','" + ""
										+ "','" + "" + "','" + "ISV" + "','"
										+ reasons + "')";
								DA.executeUpdateSql(sql2);
								out.print("<script>window.location.href='/BPEP/userManage/registersuccess.jsp'</script>");
								return;
							} else if (usertype.equals("PP")) {
								String sql2 = "INSERT INTO PPuser VALUES"
										+ "(null," + "'" + name + "','"
										+ password + "','" + "0" + "','" + ""
										+ "','" + "" + "','" + "PP" + "','"
										+ reasons + "')";
								DA.executeUpdateSql(sql2);
								out.print("<script>window.location.href='/BPEP/userManage/registersuccess.jsp'</script>");
								return;
							} else if (usertype.equals("ORG")) {
								String sql2 = "INSERT INTO ORGuser VALUES"
										+ "(null," + "'" + name + "','"
										+ password + "','" + "0" + "','" + ""
										+ "','" + "" + "','" + "ORG" + "','"
										+ reasons + "')";
								DA.executeUpdateSql(sql2);
								out.print("<script>window.location.href='/BPEP/userManage/registersuccess.jsp'</script>");
								return;
							}
						}
						DA.closeDB();
					}
				}
			}
		    }catch(SQLException se){
		    	out.print("<script>alert('验证过程发生错误!');window.location.href='/BPEP/userManage/register.jsp'</script>");
		    }catch(Exception e){
		    	out.print("<script>alert('验证过程发生错误!');window.location.href='/BPEP/userManage/register.jsp'</script>");
		    }
		%>
		</div>
</body>
</html>
