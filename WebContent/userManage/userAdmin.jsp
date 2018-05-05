<%@ page language="java"
	import="java.util.*,java.util.Date,java.sql.*,java.io.*,ontology.*,databaseaccess.*,finalvariable.*"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="s" uri="/struts-tags" %>
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
<title>管理员</title>
<link rel="stylesheet" type="text/css" href="style/admin.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script type="text/javascript" src="js/hideandshow.js"></script>
<script type="text/javascript" src="js/ontowrongfileroutecheck.js"></script>
<script type="text/javascript" src="js/wswrongfileroutecheck.js"></script>
<script type="text/javascript" src="js/specialstringcheck.js"></script>
<script type="text/javascript" src="js/wrongkey.js"></script>
<script type="text/javascript" src="js/deleteOntology.js"></script>
<script type="text/javascript" src="js/deleteWebservice.js"></script>
<script type="text/javascript" src="js/deleteProcess.js"></script>
<script type="text/javascript" src="js/deletePluginname.js"></script>
<script type="text/javascript" src="js/deleteExtensionname.js"></script>
<script type="text/javascript" src="js/deleteUsers.js"></script>
<script type="text/javascript" src="js/passUsers.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
		$("#accordion2").accordion();
		$("#accordion3").accordion();
		$("#accordion4").accordion();
		$("#accordion5").accordion();
	});
</script>
<style type="text/css">
td.ontodelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td a.ontodetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.wsdelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td a.wsdetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.userdelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.userallow {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td a.processdetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.processdelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}
td.plugindetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.plugindelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}
td.extensiondetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td.extensiondelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}
</style>
<style>
A {
	text-decoration: NONE;
}
</style>
</head>
<body>
	<%!String username;
	String email;
	String address;
	String details;

	String databasetype;
	String usertype = "bpep";
	Boolean admitpermission;
	String useremail;
	String useraddress;%>
	<%
		int PageSize =10;
		int StartRow[] = {0, 0, 0}; //开始显示记录的编号 
		int PageNo[] = {0, 0, 0};//需要显示的页数
		int CounterStart[] = {0, 0, 0};//每页页码的初始值
		int CounterEnd[] = {0, 0, 0};//显示页码的最大值
		int RecordCount[] = {0, 0, 0};//总记录数;
		int RecordCountAll = 0;
		int MaxPage[] = {0, 0, 0};//总页数
		int PrevStart[] = {0, 0, 0};//前一页
		int NextPage[] = {0, 0, 0};//下一页
		int LastRec[] = {0, 0, 0};
		int LastStartRecord[] = {0, 0, 0};//最后一页开始显示记录的编号

		if (request.getParameter("PageNo[0]") == null) { //如果为空，则表示第1页
			if (StartRow[0] == 0) {
		PageNo[0] = StartRow[0] + 1; //设定为1
			}
		} else {
			PageNo[0] = Integer.parseInt(request.getParameter("PageNo[0]")); //获得用户提交的页数
			StartRow[0] = (PageNo[0] - 1) * PageSize; //获得开始显示的记录编号
		}
		if (request.getParameter("PageNo[1]") == null) { //如果为空，则表示第1页
			if (StartRow[1] == 0) {
		PageNo[1] = StartRow[1] + 1; //设定为1
			}
		} else {
			PageNo[1] = Integer.parseInt(request.getParameter("PageNo[1]")); //获得用户提交的页数
			StartRow[1] = (PageNo[1] - 1) * PageSize; //获得开始显示的记录编号
		}
		if (request.getParameter("PageNo[2]") == null) { //如果为空，则表示第1页
			if (StartRow[2] == 0) {
		PageNo[2] = StartRow[2] + 1; //设定为1
			}
		} else {
			PageNo[2] = Integer.parseInt(request.getParameter("PageNo[2]")); //获得用户提交的页数
			StartRow[2] = (PageNo[2] - 1) * PageSize; //获得开始显示的记录编号
		}

		//因为显示页码的数量是动态变化的，假如总共有一百页，则不可能同时显示100个链接。而是根据当前的页数显示
		//一定数量的页面链接

		//设置显示页码的初始值!!
		if (PageNo[0] % PageSize == 0) {
			CounterStart[0] = PageNo[0] - (PageSize - 1);
		} else {
			CounterStart[0] = PageNo[0] - (PageNo[0] % PageSize) + 1;
		}
		CounterEnd[0] = CounterStart[0] + (PageSize - 1);
		if (PageNo[1] % PageSize == 0) {
			CounterStart[1] = PageNo[1] - (PageSize - 1);
		} else {
			CounterStart[1] = PageNo[1] - (PageNo[1] % PageSize) + 1;
		}
		CounterEnd[1] = CounterStart[1] + (PageSize - 1);
		if (PageNo[2] % PageSize == 0) {
			CounterStart[2] = PageNo[2] - (PageSize - 1);
		} else {
			CounterStart[2] = PageNo[2] - (PageNo[2] % PageSize) + 1;
		}
		CounterEnd[2] = CounterStart[2] + (PageSize - 1);
	%>
	<div id="header"></div>


<div id="top">
	<ul>
		<li class="noneline"><a href="index.jsp"><s:i18n name="bpep"><s:text name="index"></s:text></s:i18n></a></li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="login"></s:text></s:i18n>
		  <ul class="login">
		    <li><a href="login/login1.jsp">ISV login</a></li>
		    <li><a href="login/login2.jsp" >PP login</a></li>
		    <li><a href="login/login3.jsp" >ORG login</a></li>
		    <li><a href="login/adminlogin.jsp">Admin login</a></li>
		  </ul>
        </li>
        <li class="noneline"><s:i18n name="bpep"><s:text name="users"></s:text></s:i18n>
		  <ul class="user">
		    <li><a href="userManage/information.jsp" ><s:i18n name="bpep"><s:text name="info"></s:text></s:i18n></a></li>
			<li><a href="userManage/register.jsp" ><s:i18n name="bpep"><s:text name="register"></s:text></s:i18n></a></li>
			<li><a href="logout.jsp" ><s:i18n name="bpep"><s:text name="logout"></s:text></s:i18n></a></li>
		  </ul>
		</li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="tools"></s:text></s:i18n>
		   <ul class="tools">
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_edit"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="plugin_design"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_plugin"></s:text></s:i18n></a></li>
		   </ul>
		</li>
		<li class="noneline"><a href="help/help.jsp" ><s:i18n name="bpep"><s:text name="help"></s:text></s:i18n></a></li>
		<li class="noneline"><a href="help/about.jsp" ><s:i18n name="bpep"><s:text name="about"></s:text></s:i18n></a></li>
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=userAdmin">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=userAdmin">中文</a></li>
		  </ul>
		 </li>
		 
    
		
	</ul>	
</div>

	<div id="sidebar">
		<div id="sidetop">
			<h1><s:i18n name="bpep"><s:text name="admin_info"></s:text></s:i18n></h1>
			<ul id="conus">
				<li id="welcomeuser"><s:i18n name="bpep"><s:text name="welcome"></s:text></s:i18n>,<%=(String) session.getAttribute("Adminusername")%></li>
				<li id="conus1"><s:i18n name="bpep"><s:text name="basic_info"></s:text></s:i18n></li>
				<li id="conus2"><s:i18n name="bpep"><s:text name="user_manage"></s:text></s:i18n></li>
				<li id="conus3"><s:i18n name="bpep"><s:text name="process_plugin"></s:text></s:i18n></li>
				<li id="conus4"><s:i18n name="bpep"><s:text name="ontology_manage"></s:text></s:i18n></li>
				<li id="conus5"><s:i18n name="bpep"><s:text name="service_manage"></s:text></s:i18n></li>
				<li id="conus6"><s:i18n name="bpep"><s:text name="comment_view"></s:text></s:i18n></li>
				<li id="userlogout"><a href="logout.jsp"><s:i18n name="bpep"><s:text name="logout"></s:text></s:i18n></a></li>
				
			</ul>
			<h1>Links</h1>
			<ul id="links">
				<li><a href="http://www.whu.edu.cn/" target="_balnk">WHU</a></li>
				<li><a href="http://www.sklse.org/" target="_blank"><s:i18n name="bpep"><s:text name="link_labname"></s:text></s:i18n></a>
				</li>
				<li><a href="http://cs.whu.edu.cn/cs2011/" target="_blank">WHU
						CS</a></li>
				<li><a href="http://www.lib.whu.edu.cn/" target="_blank">WHU
						Library</a></li>
				<li><a href="http://iss.whu.edu.cn/" target="_blank">WHU
						ISS</a></li>

			</ul>

		</div>
	</div>

	<div id="content" align="center">
		<%
			if (session.getAttribute("Adminusername") == null) {
				out.print("<script>alert('您没有以Admin身份登录，不能进入该界面!');window.location.href='/BPEP/login/adminlogin.jsp'</script>");
			} else {
				if (session.getAttribute("passerror") != null) {
					session.setAttribute("passerror", null);
					out.print("<script>alert('通过失败!');</script>");
				}
				if (session.getAttribute("deleteerror") != null) {
					session.setAttribute("deleteerror", null);
					out.print("<script>alert('删除失败!');</script>");
				}
				try {
					ResultSet rs;
					String name = (String) session
							.getAttribute("Adminusername");
					String sql = "select * From Adminuser where name='" + name
							+ "'";
					DA.connDB(usertype);
					rs = DA.executeSelectSql(sql);
					if (rs.next()) {
						username = rs.getString(2);
						email = rs.getString(4);
						address = rs.getString(5);
						details = rs.getString(6);
					}
					DA.closeDB();
				} catch (SQLException se) {
					out.print("<script>alert('数据库连接失败0!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
					return;
				} catch (Exception e) {
					out.print("<script>alert('数据库连接失败0!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
					return;
				}
			}
		%>
		<div id="content1" align="center" class="content">
			<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input> <br /> <br />
			<div id="accordion4" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="admin"></s:text></s:i18n></h3>
				<div id="showdetails10" class="accordion">
					<br /> <br />
					<table align="center" cellpadding="2" border="3"
						width="300px">
						<tr>
							<th align="center" width="100%" colspan="2" height="30px"><s:i18n name="bpep"><s:text name="admin_info"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</td>
							<td align="center" width="50%" height="30px"><%=username%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n>：</td>
							<td align="center" width="50%" height="30px"><%=email%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n>:</td>
							<td align="center" width="50%" height="30px"><%=address%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="others"></s:text></s:i18n>:</td>
							<td align="center" width="50%" height="30px"><%=details%></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="content2" align="center" class="content">
			<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input><br /> <br /> 
			<%
				try {
					ResultSet rs;
					String sql = "select count(*) From ISVuser";
					DA.connDB(usertype);
					rs = DA.executeSelectSql(sql);
					rs.next();
					RecordCount[0] = rs.getInt(1);
					DA.closeDB();
					sql = "select count(*) From PPuser";
					DA.connDB(usertype);
					rs = DA.executeSelectSql(sql);
					rs.next();
					RecordCount[1] = rs.getInt(1);
					DA.closeDB();
					sql = "select count(*) From ORGuser";
					DA.connDB(usertype);
					rs = DA.executeSelectSql(sql);
					rs.next();
					RecordCount[2] = rs.getInt(1);
					DA.closeDB();

					RecordCountAll = RecordCount[0] + RecordCount[1]
							+ RecordCount[2];
					//获取总页数
					MaxPage[0] = RecordCount[0] % PageSize;
					if (RecordCount[0] % PageSize == 0) {
						MaxPage[0] = RecordCount[0] / PageSize;
					} else {
						MaxPage[0] = RecordCount[0] / PageSize + 1;
					}
					MaxPage[1] = RecordCount[1] % PageSize;
					if (RecordCount[1] % PageSize == 0) {
						MaxPage[1] = RecordCount[1] / PageSize;
					} else {
						MaxPage[1] = RecordCount[1] / PageSize + 1;
					}
					MaxPage[2] = RecordCount[2] % PageSize;
					if (RecordCount[2] % PageSize == 0) {
						MaxPage[2] = RecordCount[2] / PageSize;
					} else {
						MaxPage[2] = RecordCount[2] / PageSize + 1;
					}
			%>
			<div id="accordion3" class="accordion">
				<h3>ISV<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n></h3>
				<div id="showdetails7" class="accordion">
					<table align="center"  cellpadding="2" border="2"
						width="100%">
						<tr>
							<th align="center" width="100%" colspan="8" height="30px">ISV<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n>
								( <s:i18n name="bpep"><s:text name="register_user"></s:text></s:i18n>:<%=RecordCount[0]%> )
							</th>
						</tr>
						<tr>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify_or_not"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="register_reason"></s:text></s:i18n></td>
						</tr>

						<%
							databasetype = "ISVuser";
								sql = "select * From ISVuser limit " + (PageNo[0] - 1)
										* PageSize + "," + PageSize;
								DA.connDB(usertype);
								rs = DA.executeSelectSql(sql);
								while (rs.next()) {
									useremail = rs.getString(5);
									if (useremail == null || useremail.equals("")) {
										useremail = "null";
									}
									useraddress = rs.getString(6);
									if (useraddress == null || useraddress.equals("")) {
										useraddress = "null";
									}
									int permission = Integer.parseInt(rs.getString(4));
									if (permission == 0)
										admitpermission = false;
									else if (permission == 1)
										admitpermission = true;
						%>
						<tr>
							<td align="center" width="12.5%" height="30px" class="userdelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
							<%
								if (admitpermission == false) {
							%>
							<td align="center" width="12.5%" height="30px" class="userallow"><s:i18n name="bpep"><s:text name="pass"></s:text></s:i18n></td>
							<%
								} else if (admitpermission == true) {
							%>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="passed"></s:text></s:i18n></td>
							<%
								}
							%>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(2)%></td>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(7)%></td>
							<td align="center" width="12.5%" height="30px"><%=admitpermission%></td>
							<td align="center" width="12.5%" height="30px"><%=useremail%></td>
							<td align="center" width="12.5%" height="30px"><%=useraddress%></td>
							<td align="center" width="12.5%" height="30px"
								title=<%="'" + rs.getString(8) + "'"%>><s:i18n name="bpep"><s:text name="show_over"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
						%>
					</table>
					<br />
					<table width=400px border="0" class="pageShow">
						<tr>
							<td><div align="center">
									<%
										out.print("<font size=3>");
											//显示第一页或者前一页的链接
											//如果当前页不是第1页，则显示第一页和前一页的链接
											if (PageNo[0] != 1) {
												PrevStart[0] = PageNo[0] - 1;
												out.print("<a href='userManage/userAdmin.jsp?PageNo[0]=1'>第一页</a>**");
												out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
														+ PrevStart[0] + "'>前一页</a>");
											}
											out.print("[");

											//打印需要显示的页码
											for (int c = CounterStart[0]; c <= CounterEnd[0]; c++) {
												if (c < MaxPage[0]) {
													if (c == PageNo[0]) {
														if (c % PageSize == 0) {
															out.print(c);
														} else {
															out.print(c + " ,");
														}
													} else if (c % PageSize == 0) {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
																+ c + "'>" + c + "</a>");
													} else {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
																+ c + "'>" + c + "</a> ,");
													}
												} else {
													if (PageNo[0] == MaxPage[0]) {
														out.print(c);
														break;
													} else {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
																+ c + "'>" + c + "</a>");
														break;
													}
												}
											}

											out.print("]");
											;

											if (PageNo[0] < MaxPage[0]) { //如果当前页不是最后一页，则显示下一页链接
												NextPage[0] = PageNo[0] + 1;
												out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
														+ NextPage[0] + "'>下一页</a>");
											}

											//同时如果当前页不是最后一页，要显示最后一页的链接
											if (PageNo[0] < MaxPage[0]) {
												LastRec[0] = RecordCount[0] % PageSize;
												if (LastRec[0] == 0) {
													LastStartRecord[0] = RecordCount[0] - PageSize;
												} else {
													LastStartRecord[0] = RecordCount[0] - LastRec[0];
												}

												out.print("**");
												out.print("<a href='userManage/userAdmin.jsp?PageNo[0]="
														+ MaxPage[0] + "'>最后一页</a>");
											}
											out.print("</font>");
									%>
								</div></td>
						</tr>
					</table>
				</div>
				<h3>PP<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n></h3>
				<div id="showdetails8" class="accordion">
					<table align="center"  cellpadding="2" border="2"
						width="100%">
						<tr>
							<th align="center" width="100%" colspan="8" height="30px">PP<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n>
								( <s:i18n name="bpep"><s:text name="register_user"></s:text></s:i18n>:<%=RecordCount[1]%> )
							</th>
						</tr>
						<tr>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify_or_not"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="register_reason"></s:text></s:i18n></td>
						</tr>
						<%
							databasetype = "PPuser";
								sql = "select * From PPuser limit " + (PageNo[1] - 1)
										* PageSize + "," + PageSize;
								DA.connDB(usertype);
								rs = DA.executeSelectSql(sql);
								while (rs.next()) {
									useremail = rs.getString(5);
									if (useremail == null || useremail.equals("")) {
										useremail = "null";
									}
									useraddress = rs.getString(6);
									if (useraddress == null || useraddress.equals("")) {
										useraddress = "null";
									}
									int permission = Integer.parseInt(rs.getString(4));
									if (permission == 0)
										admitpermission = false;
									else if (permission == 1)
										admitpermission = true;
						%>
						<tr>
							<td align="center" width="12.5%" height="30px" class="userdelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
							<%
								if (admitpermission == false) {
							%>
							<td align="center" width="12.5%" height="30px" class="userallow"><s:i18n name="bpep"><s:text name="pass"></s:text></s:i18n></td>
							<%
								} else if (admitpermission == true) {
							%>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="passed"></s:text></s:i18n></td>
							<%
								}
							%>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(2)%></td>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(7)%></td>
							<td align="center" width="12.5%" height="30px"><%=admitpermission%></td>
							<td align="center" width="12.5%" height="30px"><%=useremail%></td>
							<td align="center" width="12.5%" height="30px"><%=useraddress%></td>
							<td align="center" width="12.5%" height="30px"
								title=<%="'" + rs.getString(8) + "'"%>><s:i18n name="bpep"><s:text name="show_over"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
						%>
					</table>
					<br />
					<table width="400px" border="0" class="pageShow">
						<tr>
							<td><div align="center">
									<%
										out.print("<font size=3>");
											//显示第一页或者前一页的链接
											//如果当前页不是第1页，则显示第一页和前一页的链接
											if (PageNo[1] != 1) {
												PrevStart[1] = PageNo[1] - 1;
												out.print("<a href='userManage/userAdmin.jsp?PageNo[1]=1'>第一页</a>**");
												out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
														+ PrevStart[1] + "'>前一页</a>");
											}
											out.print("[");

											//打印需要显示的页码
											for (int c = CounterStart[1]; c <= CounterEnd[1]; c++) {
												if (c < MaxPage[1]) {
													if (c == PageNo[1]) {
														if (c % PageSize == 0) {
															out.print(c);
														} else {
															out.print(c + " ,");
														}
													} else if (c % PageSize == 0) {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
																+ c + "'>" + c + "</a>");
													} else {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
																+ c + "'>" + c + "</a> ,");
													}
												} else {
													if (PageNo[1] == MaxPage[1]) {
														out.print(c);
														break;
													} else {
														out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
																+ c + "'>" + c + "</a>");
														break;
													}
												}
											}

											out.print("]");
											;

											if (PageNo[1] < MaxPage[1]) { //如果当前页不是最后一页，则显示下一页链接
												NextPage[1] = PageNo[1] + 1;
												out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
														+ NextPage[1] + "'>下一页</a>");
											}

											//同时如果当前页不是最后一页，要显示最后一页的链接
											if (PageNo[1] < MaxPage[1]) {
												LastRec[1] = RecordCount[1] % PageSize;
												if (LastRec[1] == 0) {
													LastStartRecord[1] = RecordCount[1] - PageSize;
												} else {
													LastStartRecord[1] = RecordCount[1] - LastRec[1];
												}

												out.print("**");
												out.print("<a href='userManage/userAdmin.jsp?PageNo[1]="
														+ MaxPage[1] + "'>最后一页</a>");
											}
											out.print("</font>");
									%>
								</div></td>
						</tr>
					</table>
				</div>
				<h3>ORG<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n></h3>
				<div id="showdetails9" class="accordion">
					<table align="center" cellpadding="2" border="2"
						width="100%">
						<tr>
							<th align="center" width="100%" colspan="8" height="30px">ORG<s:i18n name="bpep"><s:text name="user"></s:text></s:i18n>
								( <s:i18n name="bpep"><s:text name="register_user"></s:text></s:i18n>:<%=RecordCount[2]%> )
							</th>
						</tr>
						<tr>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="verify_or_not"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n></td>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="register_reason"></s:text></s:i18n></td>
						</tr>
						<%
							databasetype = "ORGuser";
								sql = "select * From ORGuser limit " + (PageNo[2] - 1)
										* PageSize + "," + (PageSize);
								DA.connDB(usertype);
								rs = DA.executeSelectSql(sql);
								while (rs.next()) {
									useremail = rs.getString(5);
									if (useremail == null || useremail.equals("")) {
										useremail = "null";
									}
									useraddress = rs.getString(6);
									if (useraddress == null || useraddress.equals("")) {
										useraddress = "null";
									}
									int permission = Integer.parseInt(rs.getString(4));
									if (permission == 0)
										admitpermission = false;
									else if (permission == 1)
										admitpermission = true;
						%>
						<tr>
							<td align="center" width="12.5%" height="30px" class="userdelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
							<%
								if (admitpermission == false) {
							%>
							<td align="center" width="12.5%" height="30px" class="userallow"><s:i18n name="bpep"><s:text name="pass"></s:text></s:i18n></td>
							<%
								} else if (admitpermission == true) {
							%>
							<td align="center" width="12.5%" height="30px"><s:i18n name="bpep"><s:text name="passed"></s:text></s:i18n></td>
							<%
								}
							%>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(2)%></td>
							<td align="center" width="12.5%" height="30px"><%=rs.getString(7)%></td>
							<td align="center" width="12.5%" height="30px"><%=admitpermission%></td>
							<td align="center" width="12.5%" height="30px"><%=useremail%></td>
							<td align="center" width="12.5%" height="30px"><%=useraddress%></td>
							<td align="center" width="12.5%" height="30px"
								title=<%="'" + rs.getString(8) + "'"%>><s:i18n name="bpep"><s:text name="show_over"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (SQLException se) {
								out.print("<script>alert('数据库连接失败1!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
								return;
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败1!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
								return;
							}
						%>
					</table>
					<br />
					<table width="400px" border="0" class="pageShow">
						<tr>
							<td><div align="center">
									<%
										out.print("<font size=3>");
										//显示第一页或者前一页的链接
										//如果当前页不是第1页，则显示第一页和前一页的链接
										if (PageNo[2] != 1) {
											PrevStart[2] = PageNo[2] - 1;
											out.print("<a href='userManage/userAdmin.jsp?PageNo[2]=1'>第一页</a>**");
											out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
													+ PrevStart[2] + "'>前一页</a>");
										}
										out.print("[");

										//打印需要显示的页码
										for (int c = CounterStart[2]; c <= CounterEnd[2]; c++) {
											if (c < MaxPage[2]) {
												if (c == PageNo[2]) {
													if (c % PageSize == 0) {
														out.print(c);
													} else {
														out.print(c + " ,");
													}
												} else if (c % PageSize == 0) {
													out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
															+ c + "'>" + c + "</a>");
												} else {
													out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
															+ c + "'>" + c + "</a> ,");
												}
											} else {
												if (PageNo[2] == MaxPage[2]) {
													out.print(c);
													break;
												} else {
													out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
															+ c + "'>" + c + "</a>");
													break;
												}
											}
										}

										out.print("]");
										;

										if (PageNo[2] < MaxPage[2]) { //如果当前页不是最后一页，则显示下一页链接
											NextPage[2] = PageNo[2] + 1;
											out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
													+ NextPage[2] + "'>下一页</a>");
										}

										//同时如果当前页不是最后一页，要显示最后一页的链接
										if (PageNo[2] < MaxPage[2]) {
											LastRec[2] = RecordCount[2] % PageSize;
											if (LastRec[2] == 0) {
												LastStartRecord[2] = RecordCount[2] - PageSize;
											} else {
												LastStartRecord[2] = RecordCount[2] - LastRec[2];
											}

											out.print("**");
											out.print("<a href='userManage/userAdmin.jsp?PageNo[2]="
													+ MaxPage[2] + "'>最后一页</a>");
										}
										out.print("</font>");
									%>
								</div></td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div id="content3" class="content">
			<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input><br /> <br /> 
			<div id="accordion5" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="process_view"></s:text></s:i18n></h3>
				<div id="showdetails11" class="accordion">
					<table cellpadding="2" border="2" width="100%"
						class="processbrowsers">
						<tr>
							<th align="center" width="100%" height="30px" colspan="5"><s:i18n name="bpep"><s:text name="process_manage"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="30%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="25%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="15%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="last_modify_time"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								String processfilePath = BasicPathVariable.processPath;
								File dir = new File(processfilePath);
								if (dir.isDirectory()) {
									File[] files1 = dir.listFiles();
									for (int i = 0; i < files1.length; i++) {
										if (files1[i].isDirectory()) {
											String fileuser = (String) files1[i].getName();
											File[] files2 = files1[i].listFiles();
											for (int j = 0; j < files2.length; j++) {
												String filename = (String) files2[j].getName();
												long changetime = (long) files2[j]
														.lastModified();
												Date pp = new Date(changetime);
												String thelasttime = (pp.getYear() + 1900)
														+ "-" + (pp.getMonth() + 1) + "-"
														+ (pp.getDay() + 6);
						%>
						<%
							DA.connDB("bpep");
												ResultSet res;
												String sqlpro = "select * from processinfo where name='"
														+ filename
														+ "' AND user='"
														+ fileuser
														+ "'";
												res = DA.executeSelectSql(sqlpro);
												String processdomain = "null";
												if (res.next()) {
													processdomain = (String) res.getString(3);
												}
						%>
						<tr>
							<td align="center" width="30%" height="30px"><a
								href="/BPEP/processManage/index.jsp?process=<%=filename%>"
								class="processdetails" target="_blank"><%=filename%></a></td>
							<td align="center" width="25%" height="30px"><%=fileuser%></td>
							<td align="center" width="15%" height="30px"><%=processdomain%></td>
							<td align="center" width="20%" height="30px"><%=thelasttime%></td>
							<td align="center" width="10%" height="30px"
								class="processdelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
										}
									}
								}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败2!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="plugin_view"></s:text></s:i18n></h3>
				<div id="showdetails12" class="accordion">
					<table cellpadding="2" border="2" width="100%"
						class="templatebrowsers">
						<tr>
							<th align="center" width="100%" height="30px" colspan="6"><s:i18n name="bpep"><s:text name="plugin_manage"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="12%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="base_process"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="process_creater"></s:text></s:i18n></td>
							<td align="center" width="12%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet plures;
								String sql_plu = "SELECT name,user,domain,processname,processuser FROM plugininfo";
								plures = DA.executeSelectSql(sql_plu);
								while (plures.next()) {
						%>
						<tr>
							<td align="center" width="20%" height="30px"class="plugindetails"><%=plures.getString("name")%></td>
							<td align="center" width="18%" height="30px"><%=plures.getString("user")%></td>
							<td align="center" width="12%" height="30px"><%=plures.getString("domain")%></td>
							<td align="center" width="20%" height="30px"><%=plures.getString("processname")%></td>
							<td align="center" width="18%" height="30px"><%=plures.getString("processuser")%></td>
							<td align="center" width="12%" height="30px"class="plugindelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败3!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="bussiness_view"></s:text></s:i18n></h3>
				<div id="showdetails13" class="accordion">
					<table cellpadding="2" border="2" width="100%"
						class="extensionbrowsers">
						<tr>
							<th align="center" width="100%" height="30px" colspan="5"><s:i18n name="bpep"><s:text name="bussiness_manage"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="25%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="25%" height="30px"><s:i18n name="bpep"><s:text name="base_process"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="process_creater"></s:text></s:i18n></td>
							<td align="center" width="14%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet extres;
								String sql_ext = "SELECT name,user,processname,processuser FROM extensioninfo";
								extres = DA.executeSelectSql(sql_ext);
								while (extres.next()) {
						%>
						<tr>
							<td align="center" width="25%" height="30px"class="extensiondetails"><%=extres.getString("name")%></td>
							<td align="center" width="18%" height="30px"><%=extres.getString("user")%></td>
							<td align="center" width="25%" height="30px"><%=extres.getString("processname")%></td>
							<td align="center" width="18%" height="30px"><%=extres.getString("processuser")%></td>
							<td align="center" width="14%" height="30px"class="extensiondelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败4!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="process_similarity"></s:text></s:i18n></h3>
				<div id="showdetails14" class="accordion">
						<table class="processsimilarity"cellpadding="2" border="2" width="100%">
						<tr>
							<th align="center" width="100%" height="30px" colspan="7"><s:i18n name="bpep"><s:text name="relationship_map"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="17%" height="30px">process1</td>
							<td align="center" width="17%" height="30px">provider1</td>
							<td align="center" width="17%" height="30px">process2</td>
							<td align="center" width="17%" height="30px">provider2</td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="node_match"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="structure_similar"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="behavior_similar"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet simres;
								String sql_sim = "SELECT * FROM similarityinfo";
								simres = DA.executeSelectSql(sql_sim);
								while (simres.next()) {
						%>
						<tr>
							<td align="center" width="17%" height="30px"><%=simres.getString("processname1")%></td>
							<td align="center" width="17%" height="30px"><%=simres.getString("processuser1")%></td>
							<td align="center" width="17%" height="30px"><%=simres.getString("processname2")%></td>
							<td align="center" width="17%" height="30px"><%=simres.getString("processuser2")%></td>
							<td align="center" width="10%" height="30px"><%=simres.getDouble("node")%></td>
							<td align="center" width="10%" height="30px"><%=simres.getDouble("structural")%></td>
							<td align="center" width="10%" height="30px"><%=simres.getDouble("behavioral")%></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败5!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				
			</div>
		</div>

		<div id="content4" class="content">
			<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input><br /> <br /> 
			<div id="accordion" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="theme_ontology_query"></s:text></s:i18n></h3>
				<div id="showdetails1" class="accordion">
					<br /> <br />
					<form name="ontologysearch" method="post" action=""
						onsubmit="return wrongkeyOnto();">
						<table class="ontologysearch">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="ontologyname"
									class="ontologyname" maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="ontologydomain" class="ontologyselect"
									id="ontologydomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;查&nbsp;询&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3><s:i18n name="bpep"><s:text name="theme_ontology_view"></s:text></s:i18n></h3>
				<div id="showdetails2" class="accordion">
					<table  cellpadding="2" border="2" width="100%"
						class="ontologybrowsers">
						<tr>
							<th align="center" width="100%" colspan="6" height="30px"><s:i18n name="bpep"><s:text name="ontology_manage"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="30%" height="30px">URI</td>
							<td align="center" width="15%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="15%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								Connection conn = Access.getConnection();
								OntologyDAO odao = new OntologyDAO(conn);
								List<OntologyDTO> dtos = odao.queryAll();
								OntologyDTO dto = null;
								Iterator<OntologyDTO> iterator = dtos.iterator();
								while (iterator.hasNext()) {
									dto = iterator.next();
						%>
						<tr>
							<td align="center" width="20%" height="30px"><%=dto.getOntologyName()%></td>
							<td align="center" width="30%" height="30px"><%=dto.getUri()%></td>
							<td align="center" width="15%" height="30px"><%=dto.getDomain()%></td>
							<td align="center" width="15%" height="30px"><%=dto.getUser()%></td>
							<td align="center" width="10%" height="30px"><a
								href="/BPEP/showOntology?id=<%=dto.getId()%>"
								class="ontodetails" target="_blank"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></a></td>
							<td align="center" width="10%" height="30px" class="ontodelete"
								onclick="return deleteOnto('<%=dto.getId()%>','<%=dto.getFileLocation()%>');"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
								odao.closeDBConnection();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败6!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="ontology_inject"></s:text></s:i18n></h3>
				<div id="showdetails3" class="accordion">
					<form name="ontologyRegister" method="post"
						action="/BPEP/regOntology" onsubmit="return wrongFileRoute();"
						enctype="multipart/form-data">
						<table class="ontologymanage">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="ontologyfilename"
									class="ontologyfilename" maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="file"></s:text></s:i18n>:</td>
								<td><input type="file" name="selectfile" class="selectfile" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="description"></s:text></s:i18n>:</td>
								<td><textarea name="description" rows="3" cols="23"
										class="textarea"></textarea></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="ontologydomain" class="ontologyselect"
									id="ontologydomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="daliylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;导&nbsp;入&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="content5" class="content">
			<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input><br /> <br /> 
			<div id="accordion2" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="service_query"></s:text></s:i18n></h3>
				<div id="showdetails4" class="accordion">
					<form name="wssearch" method="post" action=""
						onsubmit="return wrongkeyWS();">
						<table class="wsmanage">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsname" class="wsname"
									maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="input"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsinputinfo"
									class="wsinputinfo" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="output"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsoutputinfo"
									class="wsoutputinfo" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="wsdomain" class="wsselect" id="wsdomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="function"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsfunctioninfo"
									class="wsfunctioninfo" /></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;查&nbsp;询&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3><s:i18n name="bpep"><s:text name="service_view"></s:text></s:i18n></h3>
				<div id="showdetails5" class="accordion">
					<table align="center" cellpadding="2" border="2"
						width="100%" class="wsbrowsers">
						<tr>
							<th align="center" width="100%" colspan="6" height="30px"><s:i18n name="bpep"><s:text name="service_manage"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="service_id"></s:text></s:i18n></td>
							<td align="center" width="30%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet res;
								String sql_que = "SELECT id,name,domain,user FROM serviceinfo";
								res = DA.executeSelectSql(sql_que);
								while (res.next()) {
						%>
						<tr>
							<td align="center" width="10%" height="30px"><%=res.getString("id")%></td>
							<td align="center" width="30%" height="30px"><%=res.getString("name")%></td>
							<td align="center" width="20%" height="30px"><%=res.getString("domain")%></td>
							<td align="center" width="20%" height="30px"><%=res.getString("user")%></td>
							<td align="center" width="10%" height="30px"><a
								href="/BPEP/wsManage/wsDetail.jsp?id=<%=res.getString("id")%>"
								target="_blank" class="wsdetails"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></a></td>
							<td align="center" width="10%" height="30px" class="wsdelete"
								onclick="return deleteWS(<%=res.getString("id")%>);"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败7!');window.location.href('/BPEP/login/adminlogin.jsp')</script>");
							}
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="service_inject"></s:text></s:i18n></h3>
				<div id="showdetails6" class="accordion">
					<br />
					<form name="wsRegister" method="post" action="/BPEP/regWSDL"
						onsubmit="return checkWebService();">
						<table class="wsmanage">
							<tr>
								<td>WSDL URL:</td>
								<td><input type="text" name="wsfileurl"
									class="text wsfileurl" /></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="wsdomain" class="select" id="wsdomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;注&nbsp;册&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="content6" class="content">
		<input type="button" class="inputbutton"onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
		<br/></div>
	</div>

	<jsp:include page = "/style/footer.jsp"/>
</body>
</html>
