<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.io.*,java.util.*,org.sklse.processRegister.db.dto.*,org.sklse.processRegister.db.services.impl.*,org.sklse.processRegister.processGraph.*,finalvariable.*,java.sql.*,databaseaccess.*,org.sklse.processRegister.expander.impl.*,nl.tue.tm.is.epc.*" %>
<!DOCTYPE html>
<html style="overflow:hidden">
<head>
<meta charset="UTF-8">
<title>插件应用</title>
<style type="text/css">
	*{
		font-family:"黑体1"
	}
	
	body{
		-webkit-user-select:none;
		overflow:hidden;
		margin:0;
	}
	
	#manage{
		bottom:0;
		left:0;
		overflow:hidden;
		position:absolute;
		right:0;
		top:0;
	}
	
	#manage>.head{
		background-color:RGB(21,127,204);
		height:40px;
		left:0;
		position:absolute;
		right:0;
		top:0;
	}
	
	#manage>.head>.title{
		color:#fff;
		font-size:16px;
		line-height:40px;
		text-align:center;
	}
	
	#manage>.body{
		background-color:#fff;
		bottom:24px;
		left:0;
		position:absolute;
		right:0;
		top:40px;
	}
	
	#manage>.body>.button-area{
		height:44px;
	}
	
	#manage>.body>.button-area>.button{
		background-color:rgba(21,127,204,0);
		border-radius:0px;
		color:#000;
		cursor:default;
		font-size:14px;
		float:left;
		height:28px;
		line-height:28px;
		margin:8px 12px;
		padding:0 12px;
	}
	
	#manage>.body>.button-area>.button:hover{
		background-color:#ccc;
		color:#fff;
		
	}
	
	#manage>.foot{
		background-color:#fff;
		border-top:1px solid #ccc;
		bottom:0;
		height:23px;
		left:0;
		position:absolute;
		right:0;
	}
	
	#list{
		border-top:1px solid #999;
		bottom:0px;
		clear:both;
		left:0;
		overflow-x:hidden;
		overflow-y:auto;
		position:absolute;
		right:0;
		top:44px;
	}
	
	table{
		border-collapse:collapse;
		width:100%;
	}

	tr{
		border:none;
		cursor:default;
		height:30px;
		line-height:30px;
	}
	
	tr.item:hover{
		background-color:rgb(226,226,226);
	}
	
	tr.item.selected{
		background-color:RGBA(21,127,204,0.8);
	}
	
	th,td{
		border:none;
		font-size:13px;
		padding:0px 24px;
		text-align:left;
	}
	
	th{
		font-weight:normal;
	}
	
	td{
		color:#333;
	}
	
	.selected *{
		color:#fff;
	}
	
	td.type{
		width:120px;
	}
	
	td.time{
		width:240px;
	}
	
	td a{
		color:rgb(9,74,178);
		text-decoration:none;
		text-overflow:ellipsis;
	}
	
	td a:hover{
		color:rgb(9,74,178);
	}
	
	td a:visited{
		color:rgb(9,74,178);
	}
	
	.selected a:hover{
		color:#fff;
	}
	
	.selected a:visited{
		color:#fff;
	}
	
	#menu{
		cursor:pointer;
		left:0;
		position:absolute;
		top:0;
	}
	
	#menu:hover{
		background-color:rgba(255,255,255,0.3);
	}
	
	.search{
		border:1px solid #999;
		height:28px;
		margin:6px 22px;
		position:absolute;
		right:0;
		width:240px;
	}
	
	.search input{
		background-color:transparent;
		border:none;
		font-size:14px;
		height:100%;
		line-height:28px;
		padding:0;
		position:absolute;
		text-indent:8px;
		width:100%;
		
	}
	
	.search input:focus{
		outline:none;
	}
	
	.search .text{
		color:#ccc;
		font-size:14px;
		line-height:28px;
		padding:0px 8px;
		position:absolute;
	}
	
</style>
<script type="text/javascript" src="../lib/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript">
	// 选择
	$(function (){
		$("tr.item").click(function (e){
			$("tr.item.selected").removeClass("selected");
			$(this).addClass("selected");
			e.stopPropagation();
		});
		$(document).click(function (){
			$("tr.item.selected").removeClass("selected");
		});
		$(".head").click(function (e){
			e.stopPropagation();
		});
		$(".button").click(function (e){
			e.stopPropagation();
		});
	});
	// 搜索（不区分大小写）
	$(function (){
		$(".search input")[0].oninput =function (){
			var val = $.trim($(this).val()).toLowerCase();
			if(val == ""){
				$(".search .text").show();
				$("tr.item").show()
			}else{
				$(".search .text").hide();
				$("tr.item").each(function (){
					var text = $(this).find(".name a").text().toLowerCase();
					if(text.indexOf(val) > -1){
						$(this).show();
					}else{
						$(this).hide();
					}
				});
			}	
		};
	});
	// 新建
	$(function (){
		$(".create").click(function (){
			
		})
	});
</script>
</head>
<body>
	<div id="manage">
		<div class="head">
			<canvas id="menu" width="40" height="40"></canvas>
			<div class="title">插件应用 - 流程组合（3/3）</div>
		</div>
		<div class="body">
			<%
				String process = request.getParameter("process");
				String extension = request.getParameter("extension");
				// 读取基础流程
				Access ac=new Access();
				ac.connDB("bpep");
				String sql="select content,graph from original_process where id=?";
				PreparedStatement pst=ac.getCon().prepareStatement(sql);
				pst.setString(1, process);
				ResultSet rs=pst.executeQuery();
				long id=0;
				String content = "";
				if(rs.next()){
					content=rs.getString(1);
					id=rs.getLong(2);
				}
				// 获取ID
				/* sql = "select id from process_model where pid=?";
				pst=ac.getCon().prepareStatement(sql);
				pst.setLong(1, id);
				rs=pst.executeQuery();
				if(rs.next()){
					id=rs.getLong(1);
				} */
				ac.closeDB();
				// 写入文件
				/* String path=BasicPathVariable.processPath+"/temp";
				File file=new File(path);
				if(!file.exists()){
					file.createNewFile();
				}
				StringReader sr = new StringReader(content);
				BufferedOutputStream fout = new BufferedOutputStream(
						new FileOutputStream(file));
				byte bb[] = content.getBytes();
				fout.write(bb);
				fout.close(); */
				// 插件应用
				EPC epc=new EPC();
				ProcessGraphIOServiceImpl pgio=new ProcessGraphIOServiceImpl();
				ProcessGraphExpanderImpl pgex = new ProcessGraphExpanderImpl();
				PluginInfoServiceImpl plugs=new PluginInfoServiceImpl();
				
				ProcessGraph pg=pgio.ProcessGraphLoad(id);
				
				PluginInfoDTO plug = plugs.getPluginInfoDTO(Long.parseLong(extension));
				pgex.expandProcessGraph(pg, pgex.getExtensibilityPoint(pg, plug), plug);
				System.out.println(pg.id());
				String epml=epc.Epc2Epml(pgio.ProcessGraphToEpc(pg));
				out.print("<pre>"+epml+"</pre>");
			%>
		</div>
	</div>
</body>
</html>