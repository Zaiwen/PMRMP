<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<title>帮助</title>
<link rel="stylesheet" type="text/css" href="style/help.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/hideandshow.js"></script>
<script type="text/javascript" src="js/imagecode.js"></script>
<script type="text/javascript" src="js/sendEmail.js"></script>
<script type="text/javascript" src="js/specialstringcheck.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
		$("#accordion2").accordion();
		$("#accordion3").accordion();
	});
</script>
<style>
A {
	text-decoration: NONE;
}
</style>
</head>
<body>

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
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=help">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=help">中文</a></li>
		  </ul>
		 </li>
		 
    
		
	</ul>	
</div>
	<script language=JavaScript>
		$(function() {
			$("#desc>li").click(function() {
				$("#desc>li").css("background", "#FFFFF0");
				$(this).css("background", "#CAFF70");
			});
		});
	</script>
	<div id="sidebar">
		<div id="sidetop">
			<h1><s:i18n name="bpep"><s:text name="navigate"></s:text></s:i18n></h1>
			<ul id="desc">
				<li id="desc1"><s:i18n name="bpep"><s:text name="questions"></s:text></s:i18n></li>
				<li id="desc2"><s:i18n name="bpep"><s:text name="help_get"></s:text></s:i18n></li>
				<li id="desc3"><s:i18n name="bpep"><s:text name="feedback"></s:text></s:i18n></li>
				
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

	<div id="content">
		<%
			if (session.getAttribute("sendsuccess") != null) {
				session.setAttribute("sendsuccess", null);
				out.print("<script>alert('发送成功!');</script>");
			}
			if (session.getAttribute("senderror") != null) {
				session.setAttribute("senderror", null);
				out.print("<script>alert('发送失败!');</script>");
			}
		%>
		<div id="content1" class="content">
			<br />
			<div id="accordion" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="question1"></s:text></s:i18n></h3>
				<div id="showdetails" class="accordion">
				<p>注册流程：</p>
					<br />
					<p>在导航栏之用户模块中点击注册按钮--进入注册页面--选择用户角色--输入用户名、密码与验证码--填写注册理由--点击完成注册。
					<br/>(提交后系统会根据用户名判断用户是否已注册，然后提示您等待管理员的审核通过!)
					</p>
				</div>
				<h3><s:i18n name="bpep"><s:text name="question2"></s:text></s:i18n></h3>
				<div id="showdetails1" class="accordion"></div>
				<h3><s:i18n name="bpep"><s:text name="question3"></s:text></s:i18n></h3>
				<div id="showdetails2" class="accordion"></div>
				<h3>问题4:如何进行web服务和领域本体Ontology的注册?</h3>
				<div id="showdetails3" class="accordion"></div>
				<h3>问题5:Process Provider开发原子流程Process的基本操作是什么?</h3>
				<div id="showdetails4" class="accordion"></div>
				<h3>问题6:ISV怎么根据已有流程进行可插入式插件PE开发?</h3>
				<div id="showdetails5" class="accordion"></div>
				<h3>问题7:Organizations如何对流程Process和插件PE进行组合?</h3>
				<div id="showdetails6" class="accordion"></div>
				<h3>问题8:平台的运作流程是什么?</h3>
				<div id="showdetails7" class="accordion"></div>
				<h3>问题9:平台的4种角色分别具有哪些功能?</h3>
				<div id="showdetails8" class="accordion">
					<p>Administrator：</p>
					<br />
					<p>
						管理员拥有最高权限：<br />1、用户管理。对注册的用户进行审核、通过或删除。<br />2、流程插件管理。基于Process
						Provider提供的原子流程process和Independent Software Vender<br />&nbsp;&nbsp;&nbsp;&nbsp;开发的可插入式插件PE、Organizations组合而成的完整流程进行管理，可查看详情或删除。
						<br />3、进行服务WebServices和领域本体Ontology的注册，以及对这些资源的管理(查看、删除等)。<br />4、处理用户反馈的意见，为用户提供人工帮助。<br />5、维护系统。
					</p>
					<br />
					<p>Process Provider：</p>
					<br />
					<p>
						1、查看系统中已注册的web服务和领域本体Ontology。<br />2、根据系统中已有的服务、本体，在流程编辑界面开发原子流程。<br />3、对自己开发的原子流程进行管理(查看、修改、删除)。
					</p>
					<br />
					<p>Independent Software Vender：</p>
					<br />
					<p>
						1、查看系统中已注册的web服务和领域本体Ontology。<br />2、通过选择流程和领域，进行可插入式插件开发。<br />3、对自己开发的可插入式插件进行管理(查看、修改、删除)。
					</p>
					<br />
					<p>Organizations：</p>
					<br />
					<p>
						1、查看系统中已注册的web服务和领域本体Ontology。<br />2、有权限查看所有Independent Software
						Vender开发的可插入式插件和Process Provider提供的<br />&nbsp;&nbsp;&nbsp;&nbsp;原子流程。<br />3、选择Independent
						Software Vender开发的可插入式插件和Process Provider提供的原子流程<br />&nbsp;&nbsp;&nbsp;&nbsp;进行合并，生成符合自己需求的业务流程
					</p>
				</div>
			</div>
		</div>

		<div id="content2" class="content">
			<br />
			<div id="accordion2" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="help_get"></s:text></s:i18n></h3>
				<div id="showdetails9" class="accordion">
					<br />
					<form name="formHelp" method="post"
						action="/BPEP/sendEmailingcheck"
						onsubmit="return resultcheckHelp();" style="margin: 10px auto;">
						<table align="center">
							<tr>
								<td><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n></td>
								<td><input type="text" name="useraddress"
									class="text useraddress" maxlength="30" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="title"></s:text></s:i18n></td>
								<td><input type="text" name="sendsubject"
									class="text sendsubject" maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="content"></s:text></s:i18n></td>
								<td><textarea name="sendcontent"
									class="text sendcontent" ></textarea></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="verify_code"></s:text></s:i18n></td>
								<td><input type="text" name="check" id="check"
									class="text check" maxlength="4" /></td>
							</tr>
							<tr>
								<td><img src="imgcode" id="picture" /></td>
								<td><a
									style="display: block; line-height: 23px; margin-top: 10px; font-size: 13px; font: 宋体; text-align: center;"
									href="javascript:void(0);" onclick="change()"><s:i18n name="bpep"><s:text name="tips"></s:text></s:i18n></a></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="Submit" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="Cancel" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>

		<div id="content3" class="content">
			<br /> <br /> <br />
		</div>



	</div>

<jsp:include page = "/style/footer.jsp" /> 

</body>
</html>