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
<title>关于</title>
<link rel="stylesheet" type="text/css" href="style/about.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/hideandshow.js"></script>
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
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=about">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=about">中文</a></li>
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
				<li id="desc1"><s:i18n name="bpep"><s:text name="project_introduce"></s:text></s:i18n></li>
				<li id="desc2"><s:i18n name="bpep"><s:text name="user_role"></s:text></s:i18n></li>
				<li id="desc3"><s:i18n name="bpep"><s:text name="team_introduce"></s:text></s:i18n></li>
				
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

	<div id="content" >
		<div id="content1" class="content">
			<br /> 
			<div id="accordion" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="project_introduce"></s:text></s:i18n></h3>
				<div id="showdetails" class="accordion">
					<br /> <br />
                    <img src="images/0.jpg"></img>
				</div>
			</div>
		</div>

		<div id="content2" class="content">
			<br />
			<div id="accordion2" class="accordion">
				<h3>用户角色介绍</h3>
				<div id="showdetails2" class="accordion" >
				<br/>
					<p>Administrator：</p>
					<br/>
					<p>管理员拥有最高权限：<br/>1、用户管理。对注册的用户进行审核、通过或删除。<br/>2、流程插件管理。基于Process
							Provider提供的原子流程process和Independent Software Vender<br/>&nbsp;&nbsp;&nbsp;&nbsp;开发的可插入式插件PE、Organizations组合而成的完整流程进行管理，可查看详情或删除。
							<br/>3、进行服务WebServices和领域本体Ontology的注册，以及对这些资源的管理(查看、删除等)。<br/>4、处理用户反馈的意见，为用户提供人工帮助。<br/>5、维护系统。
						</p>
					<br/>
					<p>Process Provider：</p>
					<br/>
					<p>1、查看系统中已注册的web服务和领域本体Ontology。<br/>2、根据系统中已有的服务、本体，在流程编辑界面开发原子流程。<br/>3、对自己开发的原子流程进行管理(查看、修改、删除)。</p>
					<br/>
					<p>Independent Software Vender：</p>
					<br/>
						<p>1、查看系统中已注册的web服务和领域本体Ontology。<br/>2、通过选择流程和领域，进行可插入式插件开发。<br/>3、对自己开发的可插入式插件进行管理(查看、修改、删除)。</p>
					<br/>
					<p>Organizations：</p>
					<br/>
						<p>1、查看系统中已注册的web服务和领域本体Ontology。<br/>2、有权限查看所有Independent Software Vender开发的可插入式插件和Process
							Provider提供的<br/>&nbsp;&nbsp;&nbsp;&nbsp;原子流程。<br/>3、选择Independent Software Vender开发的可插入式插件和Process
							Provider提供的原子流程<br/>&nbsp;&nbsp;&nbsp;&nbsp;进行合并，生成符合自己需求的业务流程</p>
				</div>
			</div>
		</div>

		<div id="content3" class="content">
			<br /> <br /><br />
			        <!-- 
					<dl class="person">
						<dt>
							<img src="images/1.jpg" alt="1" />
						</dt>
						<dd>姓名：何克清</dd>
						<dd>职业：武汉大学教授、博士</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					-->
					<dl class="person">
						<dt>
							<img src="images/2.jpg" alt="2" />
						</dt>
						<dd>姓名：冯在文</dd>
						<dd>职业：武汉大学讲师、博士</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<!--
					<p class="line"></p>
					<dl class="person">
						<dt>
							<img src="images/3.jpg" alt="3" />
						</dt>
						<dd>姓名：黄颖</dd>
						<dd>职业：武汉大学博士</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<dl class="person">
						<dt>
							<img src="images/person.gif" alt="4" />
						</dt>
						<dd>姓名：黄贻望</dd>
						<dd>职业：武汉大学博士</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<p class="line"></p>
					-->
					<dl class="person">
						<dt>
							<img src="images/5.jpg" alt="5" />
						</dt>
						<dd>姓名：艾培东</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<p class="line"></p>
					<dl class="person">
						<dt>
							<img src="images/6.jpg" alt="6" />
						</dt>
						<dd>姓名：袁胜磊</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<dl class="person">
						<dt>
							<img src="images/7.jpg" alt="7" />
						</dt>
						<dd>姓名：陈亮</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<p class="line"></p>
					<dl class="person">
						<dt>
							<img src="images/8.jpg" alt="8" />
						</dt>
						<dd>姓名：张昭麒</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<dl class="person">
						<dt>
							<img src="images/9.jpg" alt="9" />
						</dt>
						<dd>姓名：柯书健</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<p class="line"></p>
					<dl class="person">
						<dt>
							<img src="images/10.jpg" alt="10" />
						</dt>
						<dd>姓名：王晨</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<dl class="person">
						<dt>
							<img src="images/11.jpg" alt="11" />
						</dt>
						<dd>姓名：刘川</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					<!-- 
					<dl class="person">
						<dt>
							<img src="images/12.jpg" alt="12" />
						</dt>
						<dd>姓名：申一帆</dd>
						<dd>职业：计算机学院2011级本科生</dd>
						<dd>介绍：</dd>
						<dd>电话：</dd>
						<dd>邮箱：</dd>
					</dl>
					-->
		</div>



	</div>

	<div id="footer">
		<ul>
			<li><a href="mailto:zwfeng@whu.edu.cn">联系我们</a> |</li>
			<li>友情链接 |</li>
			<li>留言 |</li>
		</ul>
		<strong>Copyright &amp;copy: 2013
			WHU-版权所有.&nbsp;&nbsp;地址：武汉市武昌区珞珈山 &nbsp;&nbsp;邮编：430072</strong>
	</div>

</body>
</html>