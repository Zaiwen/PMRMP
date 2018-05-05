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
<title>Information</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
</head>
<body>

	<div id="content" align="center">
		<%
		    try{
			if (session.getAttribute("ORGusername") == null) {
				out.print("<script>alert('请登录后再操作!');window.location.href='/BPEP/login/login3.jsp'</script>");
				return;
			} else {
				String security = request.getParameter("check");
				security = security.trim();
				if (!security.equalsIgnoreCase((String) session
						.getAttribute("randomCode"))) {
					out.print("<script>alert('验证码不正确!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
					return;
				} else {
					String newname = request.getParameter("username");
					String password = request.getParameter("userpassword");
					String password1 = request.getParameter("password1");
					newname = newname.trim();
					password = password.trim();
					password1 = password1.trim();
					if (newname == null || newname.equals("")
							|| password == null || password.equals("")
							|| password1 == null || password1.equals("")) {
						out.print("<script>alert('用户名或密码不能为空!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
						return;
					} else {
						String oldname = (String) session
								.getAttribute("ORGusername");
						ResultSet rs;
						String usertype = "Users";
						String sql = "select name,pass From ORGuser where name='"
								+ oldname + "'";
						DA.connDB(usertype);
						rs = DA.executeSelectSql(sql);
						if (rs.next()) {
							String passsql = rs.getString(2);
							if (!password.equals(passsql)) {
								DA.closeDB();
								out.print("<script>alert('原密码不正确!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
								return;
							} else {
								String useremail = request
										.getParameter("useremail");
								useremail = useremail.trim();
								String useraddress = request
										.getParameter("useraddress");
								useraddress = useraddress.trim();
								if (oldname.equals(newname)) {
									String sql2 = "update  ORGuser set pass="
											+ "'" + password1 + "',email='"
											+ useremail + "',address='"
											+ useraddress + "',reasons='"
											+ "ok" + "' where name='" + oldname
											+ "'";
									DA.executeUpdateSql(sql2);
									DA.closeDB();
									out.print("<script>alert('Organizations修改信息成功!(请记住新密码!)');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
									return;
								} else {
									String sql3 = "select name,pass From ORGuser where name='"
											+ newname + "'";
									DA.connDB(usertype);
									rs = DA.executeSelectSql(sql3);
									if (rs.next()) {
										DA.closeDB();
										out.print("<script>alert('新用户名已存在，请重新设置用户名!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
										return;
									} else {
										String sql4 = "update  ORGuser set name='"
												+ newname
												+ "',pass="
												+ "'"
												+ password1
												+ "',email='"
												+ useremail
												+ "',address='"
												+ useraddress
												+ "',reasons='"
												+ "ok"
												+ "' where name='"
												+ oldname + "'";
										DA.executeUpdateSql(sql4);
										DA.closeDB();
										session.setAttribute("ORGusername",
												newname);
										out.print("<script>alert('Organizations修改信息成功!(请记住新用户名与密码!)');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
										return;
									}
								}
							}
						}
					}
				}
			}
		    }catch(SQLException se){
		    	out.print("<script>alert('验证过程发生错误!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
		    }catch(Exception e){
		    	out.print("<script>alert('验证过程发生错误!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
		    }
		%>
	</div>

</body>
</html>