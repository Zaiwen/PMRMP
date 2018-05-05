<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,databaseaccess.*"%>
<%-- 将Session与请求的参数都转化为JS变量 --%>
<%
	String process  = request.getParameter("process");
	String extension = request.getParameter("extension");
	String mode	= "";
	// $mode
	if(session.getAttribute("PPusername") != null){
		mode = "'edit'";
	}else if(session.getAttribute("ISVusername") != null){
		mode = "'readonly'";
	}else if(session.getAttribute("ORGusername") != null){
		mode = "'readonly'";
	}else if(session.getAttribute("Adminusername") != null){
		mode = "'readonly'";
	}else{
		mode = "null";
	}
	// $process
	if(process == null || "".equals(process)){
		process = "null";
	}else{
		process = "'"+process+"'";
	}
	// extension
	if(extension == null || "".equals(extension)){
		extension = "null";
	}else{
		extension = "'"+extension+"'";
	}
%>
<!DOCTYPE HTML>
<html>
<head> 
<title>EPC Editor</title>
<meta charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="css/editor.css">
<!-- <link rel="stylesheet" type="text/css" href="css/header.css"> -->
<link rel="stylesheet" type="text/css" href="css/dialog.css">
<link rel="stylesheet" type="text/css" href="css/edit-window.css">
<link rel="stylesheet" type="text/css" href="css/ontology-dialog.css">
<link rel="stylesheet" type="text/css" href="css/import-dialog.css">
<link rel="stylesheet" type="text/css" href="css/export-dialog.css">
<link rel="stylesheet" type="text/css" href="css/create.css">
<script type="text/javascript" src="../lib/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
  	mxBasePath = "../lib/mxGraph/src";
  	$.get("AllEPCs.epml",function (data){
  		$SAP=$(data);
  	})
  	$(function (){
  		EpcEditor.init(<%=mode%>,<%=process%>,<%=extension%>);
  		window.timestamp = new Date().getTime();
  	})
</script>
<script type="text/javascript" src="../lib/mxGraph/src/js/mxClient.js"></script>
<script type="text/javascript" src="js/shape/EventShape.js"></script>
<script type="text/javascript" src="js/shape/FunctionShape.js"></script>
<script type="text/javascript" src="js/shape/ObjectShape.js"></script>
<script type="text/javascript" src="js/shape/RoleShape.js"></script>
<script type="text/javascript" src="js/shape/AndShape.js"></script>
<script type="text/javascript" src="js/shape/OrShape.js"></script>
<script type="text/javascript" src="js/shape/XorShape.js"></script>
<!-- <script type="text/javascript" src="js/cell/EPCCell.js"></script>
<script type="text/javascript" src="js/cell/EventCell.js"></script>
<script type="text/javascript" src="js/cell/FunctionCell.js"></script>
<script type="text/javascript" src="js/cell/RoleCell.js"></script>
<script type="text/javascript" src="js/cell/ObjectCell.js"></script>
<script type="text/javascript" src="js/cell/AndCell.js"></script>
<script type="text/javascript" src="js/cell/OrCell.js"></script>
<script type="text/javascript" src="js/cell/XorCell.js"></script> -->
<script type="text/javascript" src="js/edit-window.js"></script>
<!-- <script type="text/javascript" src="js/mxExtend.js"></script> -->
<!-- <script type="text/javascript" src="js/menu.js"></script> -->
<script type="text/javascript" src="js/draw.js"></script>
<!-- <script type="text/javascript" src="js/animate.js"></script> -->
<!-- <script type="text/javascript" src="js/shape-window.js"></script>  -->
<script type="text/javascript" src="js/ontology-dialog.js"></script>
<script type="text/javascript" src="js/import-dialog.js"></script>
<script type="text/javascript" src="js/export-dialog.js"></script>
<script type="text/javascript" src="js/EpcOpenDialog.js"></script>
<!-- <script type="text/javascript" src="js/header.js"></script> -->
<style type="text/css">
	body #dialog-cover{

	}
	
	input:focus{
		outline:none;
	}
	
	#operate{
		background-color:#FFF;
		border:none;
		border-radius:0;
		box-shadow:0px 0px 10px #000;
		display:none;
		height:200px;
		left:300px;
		padding:20px 0px;
		top:100px;
		width:320px;
	}
	
	#operate p{
		text-align:center;
	}
	
	#operate .item{
		background-color:rgb(21,127,204);
		color:#fff;
		cursor:pointer;
		font-size:14px;
		height:28px;
		line-height:28px;
		margin:20px auto;
		text-align:center;
		width:180px;
	
	}
	
	#operate .item:hover{
		background-color:rgba(21,127,204,0.8)
	}
	
	#operate .item:active{
		background-color:rgba(0,0,0,0.5);
	}
	
	#create{
		background-color:#FFF;
		border-radius:0;
		box-shadow:0px 0px 10px #000;
		height:200px;
		left:300px;
		top:100px;
		width:320px;
	}
	
	#create .body{
		padding:20px;
	}
	
	#create input{
		font-size:13px;
		height:24px;
		line-height:24px;
		margin:0;
		padding:0;
		text-indent:8px;
		width:100%;	
	}
</style>
</head>
<body onselectstart = "return false" oncontextmenu="return false">
	<div id="create" class="dialog">
		<div class="head">
			<div class="title">创建流程</div>
			<div class="close"></div>
		</div>
		<div class="body">
			<div class="title"></div>
			<input class="text"/>
			<div class="tips"></div>
			<div class="button">创建</div>
		</div>
	</div>
	<div id="operate" class="dialog">
		<p>请选择要进行的操作：</p>
		<div class="item create">创建新的流程</div>
		<div class="item open">打开已有流程</div>
		<div class="item import">导入本地流程</div>
	</div>
	<div id="editor">
		<div id="titlebar">Business Process Extension Platform  C-iEPC Editor</div>
		<div id="header">
		<%-- <jsp:include page="header.jsp"></jsp:include> --%>
			<div class="button new">新建</div>
			<div class="button save">保存</div>
			<div class="button open">打开</div>
			<div class="button import">导入本地文件</div>
			<div class="button export">导出为EPML</div>
			<div class="button layout" onclick="javascript:auto_layout()">自动布局</div>
			<div class="button size" onclick="javascript:auto_size()">自动尺寸</div>
			<!-- <div class="item" onclick="javascript:change_style()">切换样式</div> -->
			<div class="button" onclick="javascript:graph.undoManager.undo()">撤销</div>
			<div class="button" onclick="javascript:graph.undoManager.redo()">恢复</div>
			<div class="button" onclick="javascript:graph.removeCells()">删除</div>
			<!-- <div class="button" onclick="javascript:graph.zoomIn();EpcCanvas.refresh()">放大</div>
			<div class="button" onclick="javascript:graph.zoomOut();EpcCanvas.refresh()">缩小</div> -->
			<!-- <div class="link language English">English</div> -->
			<!-- <div class="link ">个人中心</div> -->
			<!--div class="item" id="tool-ontology">本体标注</div>
			<div class="item" id="tool-rename">重命名</div>
			<div class="item" id="tool-select-all">全选</div>
			<div class="item" id="tool-cut">剪切</div>
			<div class="item" id="tool-copy">复制</div>
			<div class="item" id="tool-paste">粘贴</div>
			<div class="item" id="tool-delete">删除</div>
			<div class="item" id="tool-zoom-in">放大</div>
			<div class="item" id="tool-zoom-out">缩小</div-->
		</div>
		<div id="container">
			<div id="shape-window" class="window max">
				<div class="head">
					<div class="title">图元</div>
					<div class="operation"></div>
				</div>
				<div class="body">
					<div id="shape-event" class="item">Event</div>
					<div id="shape-function" class="item">Function</div>
					<div id="shape-role" class="item">Role</div>
					<div id="shape-object" class="item">Object</div>
					<div id="shape-and" class="item">And</div>
					<div id="shape-or" class="item">Or</div>
					<div id="shape-xor" class="item">Xor</div>
					<div id="temp-img" ></div>
				</div>
				<!--canvas id="shape-event" class="item" height="32" width="48"></canvas>
				<canvas id="shape-function" class="item" height="32" width="48"></canvas>
				<canvas id="shape-unit" class="item" height="32" width="48"></canvas>
				<canvas id="shape-data" class="item" height="32" width="48"></canvas>
				<canvas id="shape-and" class="item" height="32" width="48"></canvas>
				<canvas id="shape-or" class="item" height="32" width="48"></canvas>
				<canvas id="shape-xor" class="item" height="32" width="48"></canvas>
				<canvas id="icon-menu" height="32" width="32"></canvas-->
			</div>
			<div id="outline-window" class="window">
				<div class="head">
					<div class="title">缩略图</div>
				</div>
				<div class="body"></div>
			</div>
			<div id="edit-window" class="window">
				<div class="head">
				<!-- 	<div class="undo"></div>
					<div class="redo"></div>
					<div class="slide-left">&lt;</div>
					<div class="title">
						<div class="text">流程1</div>
						<div class="close">×</div>
					</div>
					<div class="title selected">
						<div class="text">流程2</div>
						<div class="close">×</div>
					</div>
					<div class="title">
						<div class="text">流程3</div>
						<div class="close">×</div>
					</div>
					<div class="title">
						<div class="text">流程4</div>
						<div class="close">×</div>
					</div>
					<div class="split"></div>
					<div class="slide-right">&gt;</div>
					<div class="add">＋</div> -->
					<div class="name" title="点此重命名">No-Name</div>
<!-- 					<div class="operation zoom-actual" title="原始大小"></div>
					<div class="operation zoom-out" title="缩小"></div>
					<div class="operation zoom-in" title="放大"></div>
					<div class="operation null"></div>
					<div class="operation delete" title="删除"></div>
					<div class="operation paste" title="粘贴"></div>
					<div class="operation copy" title="复制"></div>
					<div class="operation cut" title="剪切"></div>
					<div class="operation null"></div>
					<div class="operation redo" title="恢复"></div>
					<div class="operation undo" title="撤销"></div> -->

				</div>
				<div class="body">
					<canvas></canvas>
				</div>
			</div>
			<div id="detail-window" class="window">
				<div class="head">
					<div class="title">详细</div>
				</div>
				<div class="body">
					<div class="section main">
						<div class="title">主要</div>
						<div class="content">
							<div class="item type">类型: Event</div>
							<div class="item name">
								<div class="label">名称:</div>
								<div class="value">Event0</div>
							</div>
							<div class="item description">
								<div class="label">说明:</div>
								<div class="value" title="点击进行编辑">无</div>
							</div>
						</div>
					</div>
					<div class="section position">
						<div class="title">位置</div>
						<div class="content">
							<div class="item x">
								<div class="label">X:</div>
								<div class="value">100</div>
							</div>
							<div class="item width">
								<div class="label">宽:</div>
								<div class="value">100</div>
							</div>
							<div class="item y">
								<div class="label">Y:</div>
								<div class="value">100</div>
							</div>
							<div class="item height">
								<div class="label">高:</div>
								<div class="value">100</div>
							</div>
						</div>
					</div>
						<div class="section font">
						<div class="title">字体</div>
						<div class="content">
							<!--div class="item type">
								<div class="label">类型:</div>
								<div class="value">Consolas</div>
							</div>
							<div class="item color">
								<div class="label">颜色:</div>
								<div class="value">#000000</div>
							</div-->
							<div class="item bold">加重</div>
							<div class="item italic">倾斜</div>
							<div class="item underline">下划线</div>
							<!--div class="item line-through">删除线</div-->
						</div>
					</div>
					<div class="section others">
						<div class="title">其它</div>
						<div class="content">
							<div class="item semantic">
								<div class="label">语义:</div>
								<div class="value">Ontology</div>
							</div>
						</div>
					</div>
					<!--div class="attribute x">
						<div class="name">X</div>
						<div class="value">100</div>
					</div>
					<div class="attribute y">
						<div class="name">Y</div>
						<div class="value">100</div>
					</div>
					<div class="attribute width">
						<div class="name">width</div>
						<div class="value">100</div>
					</div>
					<div class="attribute height">
						<div class="name">height</div>
						<div class="value">100</div>
					</div>
					<div class="attribute name">
						<div class="name">name</div>
						<div class="value">100</div>
						<div class="button">更改</div>
					</div>
					<div class="attribute description">
						<div class="name">description</div>
						<div class="value">100</div>
						<div class="button">更改</div>
					</div>
					<div class="attribute owl-class">
						<div class="name">OWL-Class：</div>
						<div class="value">null</div>
						<div class="button">编辑</div>
					</div-->
				</div>
			</div>
		</div>
		<div id="footer">
			<div class="item x"></div>
			<div class="item y"></div>
			<div class="item width"></div>
			<div class="item height"></div>
			<div class="item configuration"></div>
			<div class="item semanticType"></div>
			
			<div class="slider">
				<div class="left">-</div>
				<div class="center"></div>
				<div class="right">+</div>
				<div class="base"></div>
				<div class="float"></div>
				<div class="value">100%</div>
			</div>
			<canvas class="display" width="13" height="12" onclick="javascript:display_mode()" title="演示模式"></canvas>
		</div>
	</div>
	<div id="dialog-cover"></div>
	<div id="ontology-dialog" class="dialog">
		<div class="head">
			<div class="title">参考本体列表</div>
			<div class="close" title="关闭"></div>
		</div>
		<div class="body"></div>
		<div class="foot">
			<div class="close button">关闭</div>
			<div class="refresh button">下一步</div>
		</div>
	</div>
	<div id="import-dialog" class="dialog">
		<div class="head">
			<div class="title">导入本地文件</div>
			<div class="close" title="关闭"></div>
		</div>
		<div class="body">
			<div class="text">null</div>
			<div class="button select">
				<div class="label">选择文件</div>
				<input type="file" class="file">
			</div>
			<!-- div class="operation">
				<div class="button select-all">全选</div>
				<div class="button inverse">反选</div>
				<div class="label"></div>
			</div-->
			<ul class="content">
					<!--li class="item">dsgfgxdasfa</li>
					<li class="item">fdfhdshsgfsdfv</li>
					<li class="item checked">dfjghdsgdfh</li>
					<li class="item">gfhdgxdbccxv</li>
					<li class="item">vxcbvhgzsx</li>
					<li class="item checked">hsd</li>
					<li class="item checked">hsbfcggrtdf</li>
					<li class="item">cjghsdfhdfh</li>
					<li class="item">dfhtgdthngc</li>
					<li class="item">gjdtghdcvb</li>
					<li class="item checked">dgnfhncfd</li>
					<li class="item">gdjfgncv</li>
					<li class="item">vhjggbv</li>
					<li class="item">ddsgfdfvch</li>
					<li class="item">fsshfjd</li-->
				</ul>
		</div>
		<div class="foot">
			<div class="button cancel">取消</div>
			<div class="button ok">确定</div>
		</div>
	</div>
	<div id="export-dialog" class="dialog">
		<div class="head">
			<div class="title">导出</div>
			<div class="close"></div>
		</div>
		<div class="body">
			<textarea readonly="readonly"></textarea>
		</div>
	</div>
	<div id="open-dialog" class="dialog">
		<div class="head">
			<div class="title">打开已有流程</div>
			<div class="close"></div>
		</div>
		<div class="body">
			<div class="top">
				<div class="radio name selected">按名称排序</div>
				<div class="radio time">按时间排序</div>
				<div class="search">
					<div class='text'>搜索</div>
					<input type="text"/>
				</div>
			</div>
			<div class="content"></div>
		</div>
		<div class="foot">
			<div class="button close">关闭</div>
			<div class="button select">选择</div>
		</div>
	</div>
</body>
</html>
