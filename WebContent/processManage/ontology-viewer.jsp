<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本体查看</title>
<link rel="stylesheet" type="text/css" href="css/ontology-viewer.css">
<script type="text/javascript">
	mxBasePath = "lib/mxGraph";
</script>
<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
<script type="text/javascript" src="js/ontology-viewer.js"></script>
</head>
<body>
	<div id="viewer">
		<div class="head">
			<div class="title">查看本体 －</div>
			<div class="sub-title">未选择本体</div>
			<a href="ontology-help.html" class="icon help" title="查看帮助"> </a>
			<div class="list icon" title="参考本体列表"></div>
			<div class="refresh icon" title="刷新"></div>
			<!-- div class="zoom-actual icon" title="实际大小"></div>
			<div class="zoom-out icon" title="缩小"></div>
			<div class="zoom-in icon" title="放大"></div-->
		</div>
		<div class="body"></div>
		<div class="foot">
			<div  class="info"></div>
			<div class="slider">
				<div class="left">-</div>
				<div class="center"></div>
				<div class="right">+</div>
				<div class="base"></div>
				<div class="float"></div>
				<div class="value">100%</div>
			</div>
		</div>
	</div>
	<div id="dialog-cover"></div>
	<div id="ontology-list-dialog">
		<div class="head">
			<div class="title">参考本体列表</div>
			<div class="close" title="关闭"></div>
		</div>
		<div class="body"></div>
		<div class="foot">
			<div class="close button">关闭</div>
			<div class="refresh button">刷新</div>
		</div>
	</div>
</body>
</html>