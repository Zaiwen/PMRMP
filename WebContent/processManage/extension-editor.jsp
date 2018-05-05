<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<%
	// 检测用户是否已经登录
	//if (session.getAttribute("ISVusername") == null) {
	//	out.println("会话超时，请重新登录！");
	//	return;
	//}
	// 这个页面可以根据请求参数funct的不同有不同的功能 
	String funct = request.getParameter("funct");
	String process, provider;
	String domain=request.getParameter("domain");
	// 1、create：创建新的插件 
	if ("create".equals(funct)) {
		String name = request.getParameter("name");
		process = request.getParameter("process");
		provider = request.getParameter("provider");
		if (name == null) {
			out.println("请先填写要开发的插件的名称！");
			return;
		} else if (process == null) {
			out.println("未知的基础流程！");
			return;
		} else if (provider == null) {
			out.println("未知基础流程的提供者！");
			return;
		}
	}
	// 2、save：保存创建完成的插件
	else if ("save".equals(funct)) {
		//request.	
	}
	// 3、success：保存成功 
	else if ("success".equals(funct)) {

	}
	// 4、error：保存失败 
	else if ("error".equals(funct)) {
		out.println("由于未知原因，保存失败！");
		return;
	}
	// 没有任何参数的情况！
	else {
		out.print("没有给出有效参数！");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>插件开发</title>
		<link rel="stylesheet" type="text/css" href="css/editor.css">
		<link rel="stylesheet" type="text/css" href="css/dialog.css">
		<link rel="stylesheet" type="text/css" href="css/extension-dialog.css">
		<link rel="stylesheet" type="text/css" href="css/QNameView.css">
		<link rel="stylesheet" type="text/css"
			href="css/WSDLOperationView.css">
		<link rel="stylesheet" type="text/css"
			href="lib/jquery.ui/css/normalise.css">
		<link rel="stylesheet" type="text/css"
			href="lib/jquery.ui/css/jquery-ui.css">
		<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/modernizr-2.0.6.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery-ui.toggleSwitch.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.cookie.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.hotkeys.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.jstree.js"></script>
		<script type="text/javascript">
	mxBasePath = "lib/mxGraph";
	var $req_dom="<%=domain%>";
	$mode="ext-edit";
		</script>
		<script type="text/javascript" src="js/update.js"/></script>
		<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
		<script type="text/javascript" src="lib/json.js"></script>
		<script type="text/javascript" src="js/extension-editor.js"></script>
		<script type="text/javascript" src="js/extension.js"></script>
		<script type="text/javascript" src="js/mode.js"></script>
		<script type="text/javascript" src="js/extension/Extension.js"></script>
		<script type="text/javascript" src="js/extension/ExtParameter.js"></script>
		<script type="text/javascript" src="common-js.jsp"></script>
		<script type="text/javascript" src="js/owl/OWLModel.js"></script>
		<script type="text/javascript" src="js/construct/IfThenElse.js"></script>
	</head>
	<body>
		<div id='editor'>
			<div class='toolbar'>
				<!-- div class='button save'>保存</div>
			<!-- div class="button wsdl">查看所有Web服务</div>
			<div class="button ontology">查看所有本体</div-->
				<div class='button property'>
					组合流程属性
				</div>
				<div class="button extension">
					插件开发
				</div>
			</div>
			<div class='left'>
				<div class='head'>
					<div class="title">
						控制结构
					</div>
				</div>
				<div class='body'>
					<div class='tree'></div>
				</div>
			</div>
			<div class='center'>
				<div class='head'>
					<div class="title">
						流程图
					</div>
					<div class="zoom-actual" title="原始大小"></div>
					<div class="zoom-out" title="缩小"></div>
					<div class="zoom-in" title="放大"></div>
				</div>
				<div class='body'></div>
			</div>
			<div class='right'>
				<div class='head'>
					<div class="title">
						原子流程
					</div>
				</div>
				<div class='body'></div>
			</div>
			<div class="statusbar">
				<div class="extension">
					插件开发中，
					<span>点此</span>继续！
				</div>
			</div>
		</div>
		<jsp:include page="load-process.jsp"></jsp:include>
		<div id="extension-description-dialog" title="插件描述 -- 插件开发(1/9)">
			<div class="name">
				<div class="label">
					名称：
				</div>
				<input type="text" class="value" value="Extension02"
					disabled="disabled" />
			</div>
			<div class="domain">
				<div class="label">
					领域：
				</div>
				<input type="text" class="value" value="交通" disabled="disabled" />
			</div>
			<div class="introduction">
				<div class="label">
					简介：
				</div>
				<textarea rows="14" cols="48"></textarea>
			</div>
		</div>
		<div id="extension-query-pattern-dialog" title="选择查询方式--插件开发(2/9)">
			<ul>
				<li>
					<input type="radio"name="query-pattern-name" />
					Variable-Oriented Query
				</li>
				<li>
					<input type="radio" name="query-pattern-name" />
					Activity-Oriented Query
				</li>
				<li>
					<input type="radio"name="query-pattern-name" />
					Condition-Oriented Query
				</li>
			</ul>
		</div>
		<!-- 变量查询 -->
		<div id="extension-query-variable-dialog" title="基于变量的查询 -- 插件开发(3/9)">
			<div class="input">
				<p>
					hasInput：
				</p>
				<ul></ul>
			</div>
			<div class="output">
				<p>
					hasOutput：
				</p>
				<ul></ul>
			</div>
		</div>
		<div id="extension-query-result-dialog"
			title="查询到的Perform -- 插件开发(4/9)">
			<ol></ol>
		</div>

		<div id="extension-pattern-dialog" title="选择插入模式 -- 插件开发（5/9)">
			<ul>
				<li class="prefix">
					<input type="radio" name="control-pattern-name" />
					<span>前缀(Prefix)</span>
				</li>
				<li class="postfix">
					<input type="radio" name="control-pattern-name" />
					<span>后缀(Postfix)</span>
				</li>
				<li class="preposition">
					<input type="radio" name="control-pattern-name" />
					<span>前置(Preposition)</span>
				</li>
				<li class="postposition">
					<input type="radio" name="control-pattern-name" />
					<span>后置(Postposition)</span>
				</li>
			</ul>
			<p>
				不懂这些是什么，点击
				<a href="extension-help.html" target="_blank">这里</a>查看帮助
			</p>
		</div>
		<div id="extension-wsdl-url-dialog" title="选择Web服务 -- 插件开发（6/9)">

		</div>
		<div id="extension-wsdl-operation-dialog" title="选择服务操作 -- 插件开发（7/9)">

		</div>
		<div id="extension-binding-prefix-dialog" title="Prefix-数据绑定 -- 插件开发（8/9)">

		</div>
		<div id="extension-binding-preposition-dialog"title="Postfix-数据绑定 -- 插件开发（8/9)">
			前置模式不需要进行数据绑定，所有的输入直接由总的流程给出，所有的输入也都输出给总的流程。
		</div>
		<div id="extension-binding-postfix-dialog" title="Preposition-数据绑定 -- 插件开发（8/9)">

		</div>
		<div id="extension-binding-postposition-dialog"title="Postposition-数据绑定 -- 插件开发（8/9)">

		</div>
		<div id="extension-overview-dialog" title="插件总览 -- 插件开发(9/9)">
			暂时无法预览整个插件。
		</div>
		<div id="extension-finish-dialog" title="插件总览">
		
		</div>
		
		<!-- 条件查询 -->
		<div id="extension-query-condition-dialog" title="基于条件的查询 -- 插件开发(3/9)">
			<div>
				<p>
					输入需要插入的流程名：
				</p>
				<input type="text" id="processName"style="width:400px;height:30px;padding:0 0 0 5px;"/>
			</div>
			<div class="precondition">
				<p>
					hasPrecondition：
				</p>
				<input type="text" id="preconditionName"style="width:400px;height:30px;padding:0 0 0 5px;"/>
			</div>
			<div class="postcondition">
				<p>
					hasPostCondition：
				</p>
				<input type="text" id="postconditionName"style="width:400px;height:30px;padding:0 0 0 5px;"/>
			</div>
		</div>
		<div id="extension-condition-result-dialog"title="查询到的原子流程 -- 插件开发(4/9)">
			<ol></ol>
		</div>

		<div id="extension-condition-pattern-dialog" title="选择插入模式 -- 插件开发（5/9)">
			<ul style="padding: 0 0 0 20px">
				<li class="1001">
					<input type="radio" name="control-pattern-name" />
					<span>Insert Before</span><br/>
					<img src="../images/before.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1002">
					<input type="radio" name="control-pattern-name" />
					<span>Insert After</span><br/>
					<img src="../images/after.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1003">
					<input type="radio"name="control-pattern-name"  />
					<span>Parallel</span><br/>
					<img src="../images/parallel.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1004">
					<input type="radio" name="control-pattern-name" />
					<span>Replace</span><br/>
					<img src="../images/replace.jpg" style="padding:20px 0 0 20px"/>
				</li>
			</ul>
			<p>
				不懂这些是什么，点击
				<a href="extension-help.html" target="_blank">这里</a>查看帮助
			</p>
		</div>
		<div id="extension-condition-wsdl-url-dialog" title="选择Web服务 -- 插件开发（6/9)">

		</div>
		<div id="extension-condition-wsdl-operation-dialog" title="选择服务操作 -- 插件开发（7/9)">

		</div>
		<div id="extension-condition-binding-before-dialog" title="Before-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight:bold;">Before</span> extension point:
				<ul style="padding: 0 0 0 20px">
				<li class="1001">
					<input type="radio" name="data-pattern-name" />
					<span style="font-weight:bold;">Bi-External</span><br/>
					<img src="../images/Bi-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1002">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Pre-External</span><br/>
					<img src="../images/Pre-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1003">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Post-External</span><br/>
					<img src="../images/Post-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1004">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Prefix</span><br/>
					<img src="../images/Prefix.jpg" style="padding:20px 0 0 20px"/>
				</li>
				<li class="1005">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Postfix</span><br/>
					<img src="../images/Postfix.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1006">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Subsititution</span><br/>
					<img src="../images/Subsititution.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1007">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Synchronization</span><br/>
					<img src="../images/Synchronization.jpg" style="padding:20px 0 0 20px"/>
				</li>
			</ul>
		</div>
		<div id="extension-condition-binding-after-dialog"title="After-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight:bold;">After</span> extension point:
				<ul style="padding: 0 0 0 20px">
				<li class="1001">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Bi-External</span><br/>
					<img src="../images/Bi-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1002">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Pre-External</span><br/>
					<img src="../images/Pre-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1003">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Post-External</span><br/>
					<img src="../images/Post-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1004">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Prefix</span><br/>
					<img src="../images/Prefix.jpg" style="padding:20px 0 0 20px"/>
				</li>
				<li class="1005">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Postfix</span><br/>
					<img src="../images/Postfix.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1006">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Subsititution</span><br/>
					<img src="../images/Subsititution.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1007">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Synchronization</span><br/>
					<img src="../images/Synchronization.jpg" style="padding:20px 0 0 20px"/>
				</li>
			</ul>
		</div>
		<div id="extension-condition-binding-parallel-dialog" title="Parallel-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight:bold;">Parallel</span> to extension point:
				<ul style="padding: 0 0 0 20px">
				<li class="1001">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Bi-External</span><br/>
					<img src="../images/Bi-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1002">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Pre-External</span><br/>
					<img src="../images/Pre-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1003">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Post-External</span><br/>
					<img src="../images/Post-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1004">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Prefix</span><br/>
					<img src="../images/Prefix.jpg" style="padding:20px 0 0 20px"/>
				</li>
				<li class="1005">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Postfix</span><br/>
					<img src="../images/Postfix.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1006">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Subsititution</span><br/>
					<img src="../images/Subsititution.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1007">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Synchronization</span><br/>
					<img src="../images/Synchronization.jpg" style="padding:20px 0 0 20px"/>
				</li>
			</ul>
		</div>
		<div id="extension-condition-binding-replace-dialog"title="Replace-数据绑定 -- 插件开发（8/9)">
				<span style="font-weight:bold;">Replace</span> process fragment:
				<ul style="padding: 0 0 0 20px">
				<li class="1001">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Bi-External</span><br/>
					<img src="../images/Bi-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1002">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Pre-External</span><br/>
					<img src="../images/Pre-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1003">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Post-External</span><br/>
					<img src="../images/Post-External.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1004">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Prefix</span><br/>
					<img src="../images/Prefix.jpg" style="padding:20px 0 0 20px"/>
				</li>
				<li class="1005">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Postfix</span><br/>
					<img src="../images/Postfix.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1006">
					<input type="radio" name="data-pattern-name"/>
					<span style="font-weight:bold;">Subsititution</span><br/>
					<img src="../images/Subsititution.jpg" style="padding:20px 0 20px 20px"/>
				</li>
				<li class="1007">
					<input type="radio" disabled name="data-pattern-name"/>
					<span>Synchronization</span><br/>
					<img src="../images/Synchronization.jpg" style="padding:20px 0 0 20px"/>
				</li>
			</ul>
		</div>
		<div id="extension-condition-overview-dialog" title="插件总览 -- 插件开发(9/9)">
			暂时无法预览整个插件。
		</div>
		<div id="extension-condition-finish-dialog" title="插件总览">
		
		</div>
		
		
		<!-- 事件查询 -->
	</body>
</html>