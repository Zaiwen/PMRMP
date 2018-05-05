<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	body{
		margin:0;
	}

	.handler{
		border:1px dashed #00a8ff;
		cursor:move;
		opacity:1;
		position:absolute;
	}

	.nw-resize{
		background-color:#fff;
		border:1px solid #00a8ff;
		border-radius:50%;
		cursor:nw-resize;
		height:9px;
		left:-6px;
		position:absolute;
		top:-6px;
		width:9px;
	}
	
	.se-resize{
		background-color:#fff;
		border:1px solid #00a8ff;
		border-radius:50%;
		bottom:-6px;
		cursor:nw-resize;
		height:9px;
		position:absolute;
		right:-6px;
		width:9px;
	}
	
	#editor{
		bottom:0;
		left:0;
		overflow:hidden;
		position:absolute;
		right:0;
		top:0;
	}
	
	#editor>.header{
		background-color:rgb(21,127,204);
		font-size:14px;
		height:40px;
		line-height:40px;
		left:0px;
		position:absolute;
		right:0px;
		top:0px;
	}
	
	#editor>.header>.menu{
		height:40px;
		position:absolute;
		right:0;
		top:0;
		width:40px;
		z-index:1002;
	}
	
	#editor>.header>.menu:hover{
		background-color:rgba(0,0,0,0.2);
	}
	
	#editor>.header>.title{
		background-color:rgba(21,127,204,0.9);
		border:none;
		color:#fff;
		font-size:15px;
		height:40px;
		left:0;
		line-height:40px;
		position:absolute;
		right:0;
		text-align:center;
		top:0;
		width:100%;
		z-index:1001;
	}
	
	#editor>.toolbar{
		background-color:rgba(21,127,204,0);
		border-right:1px solid #ccc;
		bottom:24px;
		cursor:default;
		font-size:14px;
		left:0px;
		position:absolute;
		top:40px;
		width:64px;
	}
	
	#editor>.toolbar>canvas:hover{
		background-color:#ddd;
	}
	
	#editor>.graph{
		background-color:#fff;
		bottom:24px;
		left:64px;
		overflow:auto;
		position:absolute;
		right:0;
		top:40px;
	}
	
	#editor>.graph>canvas{
		background-color:#fff;
		box-shadow:0px 0px 2px #999;
		height:1123px;
		width:793px;
		z-index:1;
	}
	
	#editor>.graph>div{
		z-index:1000;
	}
	
	#editor>.footer{
		background-color:rgb(21,127,204);
		bottom:0px;
		font-size:14px;
		height:24px;
		line-height:24px;
		left:0px;
		position:absolute;
		right:0px;
	}
	
</style>
<script type="text/javascript" src="../lib/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/demo2.js"></script>
<script type="text/javascript">
	$(function (){
		var user 	= <%="'" + request.getParameter("user") + "'"%>;
		var name 	= <%="'" + request.getParameter("name") + "'"%>;
		var readonly= <%="'" + request.getParameter("readonly") + "'"%>;
		EpcEditor.init(user, name, readonly);
	});
</script>
</head>
<body onselectstart="return false">
	<%
		// 当前角色为Process Provider用户时
		if(session.getAttribute("PPusername")!=null){
		
		// 当前角色为其它用户时
		}else if(session.getAttribute("ISVusername")!=null
				|| session.getAttribute("ORGusername")!=null
				|| session.getAttribute("Adminusername")!=null){
			
		// 没有任何角色登录时	
		}else{
			
		}
	%>
	<div id="editor">
		<div class="header">
			<canvas class="menu" width="40" height="40"></canvas>
			<script type="text/javascript">
			$(function (){
				var context=$("canvas.menu")[0].getContext("2d");
				context.fillStyle = "#fff";
				context.fillRect(8, 12, 24, 2);
				context.fillRect(8, 19, 24, 2);
				context.fillRect(8, 26, 24, 2);
			})
			</script>
			<div class="title">未命名流程 - BPEP C-iEPC Editor</div>
		</div>
		<div class="toolbar">
			<canvas class="event"></canvas>
			<canvas class="function"></canvas>
			<canvas class="role"></canvas>
			<canvas class="object"></canvas>
			<canvas class="and"></canvas>
			<canvas class="or"></canvas>
			<canvas class="xor"></canvas>
			<canvas class="line"></canvas>
			<canvas class="delete"></canvas>
		</div>
		<div class="graph">
		</div>
		<div class="footer"></div>
	</div>
	
</body>
</html>