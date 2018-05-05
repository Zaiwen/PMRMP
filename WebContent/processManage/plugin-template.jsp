<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>插件开发</title>
<meta charset="utf-8" />
<style type="text/css">
body {
	bottom: 0;
	left: 0;
	margin: 0;
	position: absolute;
	right: 0;
	top: 0;
}

#viewer {
	border: none;
	height: 100%;
	left: 0;
	position: absolute;
	top: 0;
	width: 50%;
}

#ext {
	border-left: 8px solid rgb(21, 127, 112);
	bottom: 0;
	left: 50%;
	position: absolute;
	right: 0;
	top: 0;
}

#ext .head {
	background-color: rgb(21, 127, 20);
	height: 40px;
	left: 0;
	position: absolute;
	right: 0;
	top: 0;
}

#ext .body {
	bottom: 24px;
	left: 0;
	position: absolute;
	overflow-y:auto;
	right: 0;
	top: 40px;
}

#ext .foot {
	background-color: rgb(21, 127, 20);
	bottom: 0px;
	height: 24px;
	left: 0;
	position: absolute;
	right: 0;
}

</style>
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
<script type="text/javascript" src="lib/jquery/jquery-1.10.2.js"></script>		
</head>
<body onselectstart="return false" oncontextmenu="return false">
	<%
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String domain = request.getParameter("domain");
		String processid = request.getParameter("process");
		String processname = request.getParameter("processname");
		String provider = request.getParameter("provider");
		if (type.equalsIgnoreCase("epc")) {
	%>
	<iframe id="viewer" src="epc/index.jsp?process=<%=processid%>"></iframe>
	<%
		} else if (type.equalsIgnoreCase("owls")) {
	%>
	<iframe id="viewer" src="extension-index.jsp"></iframe>
	<%
		}
	%>
	<script>
		function getEpml(){
			return $("iframe")[0].contentWindow.EpcEditor.encodeEpml();
		}
		var $req_name="<%=name%>";
		var $req_dom="<%=domain%>";
		var $req_prv="<%=provider%>";
		var $req_prc="<%=processname%>";
		var $req_pid="<%=processid%>";
		//var $req_epml=getEpml();
	</script>
	<div id="ext">
		<div class="head"></div>
		<div class="body">
			<div id="extension-description-dialog"class="dialogs" title="插件描述 -- 插件开发(1/9)">
				<div class="name">
					<div class="label">名称：</div>
					<input type="text" class="value" value="Extension02"
						disabled="disabled" />
				</div>
				<div class="domain">
					<div class="label">领域：</div>
					<input type="text" class="value" value="交通" disabled="disabled" />
				</div>
				<div class="introduction">
					<div class="label">简介：</div>
					<textarea rows="14" cols="48"></textarea><br/>
					<button class="buttons"type="button"name="next">下一步</button>
				</div>
			</div>
			<div id="extension-query-pattern-dialog" class="dialogs"title="选择查询方式--插件开发(2/9)">
				<ul>
					<li><input type="radio" name="query-pattern-name" />
						Variable-Oriented Query</li>
					<li><input type="radio" name="query-pattern-name" />
						Activity-Oriented Query</li>
					<li><input type="radio" name="query-pattern-name" />
						Condition-Oriented Query</li>
				</ul>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<!-- 变量查询 -->
			<div id="extension-query-variable-dialog"class="dialogs"
				title="基于变量的查询 -- 插件开发(3/9)">
				<div class="input">
					<p>hasInput：</p>
					<ul></ul>
				</div>
				<div class="output">
					<p>hasOutput：</p>
					<ul></ul>
				</div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-query-result-dialog"class="dialogs"
				title="查询到的Perform -- 插件开发(4/9)">
				<div id="rst_message"></div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>

			<div id="extension-pattern-dialog" class="dialogs"title="选择插入模式 -- 插件开发（5/9)">
				<ul>
					<li class="prefix"><input type="radio"
						name="control-pattern-name" /> <span>前缀(Prefix)</span></li>
					<li class="postfix"><input type="radio"
						name="control-pattern-name" /> <span>后缀(Postfix)</span></li>
					<li class="preposition"><input type="radio"
						name="control-pattern-name" /> <span>前置(Preposition)</span></li>
					<li class="postposition"><input type="radio"
						name="control-pattern-name" /> <span>后置(Postposition)</span></li>
				</ul>
				<p>
					不懂这些是什么，点击 <a href="extension-help.html" target="_blank">这里</a>查看帮助
				</p>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-wsdl-url-dialog" class="dialogs"title="选择Web服务 -- 插件开发（6/9)">
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-wsdl-operation-dialog"class="dialogs" title="选择服务操作 -- 插件开发（7/9)">
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-binding-prefix-dialog"class="dialogs"
				title="Prefix-数据绑定 -- 插件开发（8/9)">
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
				</div>
			<div id="extension-binding-preposition-dialog"class="dialogs"
				title="Postfix-数据绑定 -- 插件开发（8/9)">
				前置模式不需要进行数据绑定，所有的输入直接由总的流程给出，所有的输入也都输出给总的流程。
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
				</div>
			<div id="extension-binding-postfix-dialog"class="dialogs"
				title="Preposition-数据绑定 -- 插件开发（8/9)">
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
				</div>
			<div id="extension-binding-postposition-dialog"class="dialogs"
				title="Postposition-数据绑定 -- 插件开发（8/9)">
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
				</div>
			<div id="extension-overview-dialog"class="dialogs" title="插件总览 -- 插件开发(9/9)">
				暂时无法预览整个插件。
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button></div>
			<div id="extension-finish-dialog"class="dialogs" title="插件总览">
			<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">完成</button>
			</div>

			<!-- 条件查询 -->
			<div id="extension-query-condition-dialog"class="dialogs"
				title="基于条件的查询 -- 插件开发(3/9)">
				<div>
					<p>输入需要插入的流程名：</p>
					<input type="text" id="processName"
						style="width: 400px; height: 30px; padding: 0 0 0 5px;" />
				</div>
				<div class="precondition">
					<p>hasPrecondition：</p>
					<input type="text" id="preconditionName"
						style="width: 400px; height: 30px; padding: 0 0 0 20px;" />
				</div>
				<div class="postcondition">
					<p>hasPostCondition：</p>
					<input type="text" id="postconditionName"
						style="width: 400px; height: 30px; padding: 0 0 0 20px;" />
				</div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-result-dialog"class="dialogs"
				title="查询到的原子流程 -- 插件开发(4/9)">
				<div id="condition_rst_message"></div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>

			<div id="extension-condition-pattern-dialog"class="dialogs"
				title="选择插入模式 -- 插件开发（5/9)">
				<ul style="padding: 0 0 0 20px">
					<li class="1001"><input type="radio"
						name="control-pattern-name" /> <span>Insert Before</span><br /> <img
						src="../images/before.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1002"><input type="radio"
						name="control-pattern-name" /> <span>Insert After</span><br /> <img
						src="../images/after.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1003"><input type="radio"
						name="control-pattern-name" /> <span>Parallel</span><br /> <img
						src="../images/parallel.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1004"><input type="radio"
						name="control-pattern-name" /> <span>Replace</span><br /> <img
						src="../images/replace.jpg" style="padding: 20px 0 0 20px" /></li>
				</ul>
				<p>
					不懂这些是什么，点击 <a href="extension-help.html" target="_blank">这里</a>查看帮助
				</p>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-choose-dialog"class="dialogs"
				title="选择是否进行web服务标识?">
				<ul style="padding: 0 0 0 20px">
					<li class="000"><input type="radio"
						name="choose-name" /> <span>否</span><br /></li>
					<li class="111"><input type="radio"
						name="choose-name" /> <span>是</span><br /></li>
				</ul>
				<p>
					Tips : 当基础流程是EPC时，不需要进行web服务标识.
				    </p>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-wsdl-url-dialog"class="dialogs"
				title="选择Web服务 -- 插件开发（6/9)">
				<div id="condition_wsdl_url_message"></div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-wsdl-operation-dialog"class="dialogs"
				title="选择服务操作 -- 插件开发（7/9)">
				<div id="condition_wsdl_oper_message"></div>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-binding-before-dialog"class="dialogs"
				title="Before-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight: bold;">Before</span>
				extension point:
				<ul style="padding: 0 0 0 20px">
					<li class="1001"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Bi-External</span><br /> <img
						src="../images/Bi-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1002"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Pre-External</span><br /> <img
						src="../images/Pre-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1003"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Post-External</span><br /> <img
						src="../images/Post-External.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1004"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Prefix</span><br /> <img
						src="../images/Prefix.jpg" style="padding: 20px 0 0 20px" /></li>
					<li class="1005"><input type="radio" disabled
						name="data-pattern-name" /> <span>Postfix</span><br /> <img
						src="../images/Postfix.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1006"><input type="radio" disabled
						name="data-pattern-name" /> <span>Subsititution</span><br /> <img
						src="../images/Subsititution.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1007"><input type="radio" disabled
						name="data-pattern-name" /> <span>Synchronization</span><br /> <img
						src="../images/Synchronization.jpg" style="padding: 20px 0 0 20px" />
					</li>
				</ul>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-binding-after-dialog"class="dialogs"
				title="After-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight: bold;">After</span>
				extension point:
				<ul style="padding: 0 0 0 20px">
					<li class="1001"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Bi-External</span><br /> <img
						src="../images/Bi-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1002"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Pre-External</span><br /> <img
						src="../images/Pre-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1003"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Post-External</span><br /> <img
						src="../images/Post-External.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1004"><input type="radio" disabled
						name="data-pattern-name" /> <span>Prefix</span><br /> <img
						src="../images/Prefix.jpg" style="padding: 20px 0 0 20px" /></li>
					<li class="1005"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Postfix</span><br /> <img
						src="../images/Postfix.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1006"><input type="radio" disabled
						name="data-pattern-name" /> <span>Subsititution</span><br /> <img
						src="../images/Subsititution.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1007"><input type="radio" disabled
						name="data-pattern-name" /> <span>Synchronization</span><br /> <img
						src="../images/Synchronization.jpg" style="padding: 20px 0 0 20px" />
					</li>
				</ul>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-binding-parallel-dialog"class="dialogs"
				title="Parallel-数据绑定 -- 插件开发（8/9)">
				Insert process fragment <span style="font-weight: bold;">Parallel</span>
				to extension point:
				<ul style="padding: 0 0 0 20px">
					<li class="1001"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Bi-External</span><br /> <img
						src="../images/Bi-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1002"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Pre-External</span><br /> <img
						src="../images/Pre-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1003"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Post-External</span><br /> <img
						src="../images/Post-External.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1004"><input type="radio" disabled
						name="data-pattern-name" /> <span>Prefix</span><br /> <img
						src="../images/Prefix.jpg" style="padding: 20px 0 0 20px" /></li>
					<li class="1005"><input type="radio" disabled
						name="data-pattern-name" /> <span>Postfix</span><br /> <img
						src="../images/Postfix.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1006"><input type="radio" disabled
						name="data-pattern-name" /> <span>Subsititution</span><br /> <img
						src="../images/Subsititution.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1007"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Synchronization</span><br /> <img
						src="../images/Synchronization.jpg" style="padding: 20px 0 0 20px" />
					</li>
				</ul>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-binding-replace-dialog"class="dialogs"
				title="Replace-数据绑定 -- 插件开发（8/9)">
				<span style="font-weight: bold;">Replace</span> process fragment:
				<ul style="padding: 0 0 0 20px">
					<li class="1001"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Bi-External</span><br /> <img
						src="../images/Bi-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1002"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Pre-External</span><br /> <img
						src="../images/Pre-External.jpg" style="padding: 20px 0 20px 20px" />
					</li>
					<li class="1003"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Post-External</span><br /> <img
						src="../images/Post-External.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1004"><input type="radio" disabled
						name="data-pattern-name" /> <span>Prefix</span><br /> <img
						src="../images/Prefix.jpg" style="padding: 20px 0 0 20px" /></li>
					<li class="1005"><input type="radio" disabled
						name="data-pattern-name" /> <span>Postfix</span><br /> <img
						src="../images/Postfix.jpg" style="padding: 20px 0 20px 20px" /></li>
					<li class="1006"><input type="radio" name="data-pattern-name" />
						<span style="font-weight: bold;">Subsititution</span><br /> <img
						src="../images/Subsititution.jpg"
						style="padding: 20px 0 20px 20px" /></li>
					<li class="1007"><input type="radio" disabled
						name="data-pattern-name" /> <span>Synchronization</span><br /> <img
						src="../images/Synchronization.jpg" style="padding: 20px 0 0 20px" />
					</li>
				</ul>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button>
			</div>
			<div id="extension-condition-overview-dialog"class="dialogs"
				title="插件总览 -- 插件开发(9/9)">暂时无法预览整个插件。<br/>
				<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">下一步</button></div>
			<div id="extension-condition-finish-dialog" class="dialogs"title="插件总览">
			已完成插件的开发，是否保存?<br/>
			<button class="buttons"type="button"name="before">上一步</button><button class="buttons"type="button"name="next">保存</button>
			</div>


			<!-- 事件查询 -->
		</div>
		<div class="foot"></div>
	</div>
	<script type="text/javascript" src="lib/json.js"></script>
		<script type="text/javascript" src="js/extension-editor.js"></script>
		<script type="text/javascript" src="js/plugin-template.js"></script>
		<script type="text/javascript" src="js/extension/Extension.js"></script>
		<script type="text/javascript" src="js/extension/ExtParameter.js"></script>
</body>
		
</html>
