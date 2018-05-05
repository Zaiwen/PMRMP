<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header" class="header">
	<div class="app-name">EPC Designer</div>
	<div id="titlebar" class="title">未命名流程</div>
	<!--div class="titlebar">
				
				
				<div class="right help">？</div>
				<div class="right logout">注销</div>
				<div class="right">|</div>
				<div class="right user">用户</div>
			</div-->
	<div id="menubar" class="menubar">
		<!--div class="icon back"></div-->
		<div class="icon redo"></div>
		<div class="icon undo"></div>
		<!-- div class="icon save">
			<div></div>
		</div-->
		<div class="icon user"></div>
		<div class="icon language"></div>
		
		<!--div class="name">
					<div class="n1">EPC Designer > </div> 
					<div class="n2">未命名流程</div>
				</div-->
		<div class="menu file">
			<div class='text'>文件</div>
			<div class="list">
				<div class="create">新建</div>
				<div class="save">保存</div>
				<div class="open">打开</div>
				<div class="manage">管理</div>
				<div class="import">导入</div>
				<div class="export">导出</div>
				<div onclick="javascript:auto_layout()">自动布局</div>
				<div onclick="javascript:auto_size()">自动大小</div>
				<div onclick="javascript:change_style()">更改样式</div>
			</div>
		</div>
		<div class="menu start  selected">开始</div>
		<div class="menu design">设计</div>
		<div class="menu share">共享</div>



	</div>
	<div id="toolbar" class="toolbar">
		<!--div class="item">新建</div>
				<div class="item">打开</div>
				<div class="item">重命名</div>
				<div class="item">另存副本</div>
				<div class="item">导入本地文件</div>
				<div class="item">导出为图片</div>
				<div class="item">导出为EPML</div>
				<div class="item">资源管理器</div-->
		<div class="section start">

			<div class="font-family">
				<div class="text">Microsoft YaHei</div>
				<div class="down">
					<div class="item">Arial</div>
					<div class="item">Arial Black</div>
					<div class="item">Arial Narrow</div>
					<div class="item">Verdana</div>
					<div class="item">Georgia</div>
					<div class="item">Times New Roman</div>
					<div class="item">Trebuchet MS</div>
					<div class="item">Courier New</div>
					<div class="item">Impact</div>
					<div class="item">Comic Sans MS</div>
					<div class="item">Tahoma</div>
					<div class="item">Courier</div>
					<div class="item">Lucida Sans Unicode</div>
					<div class="item">Lucida Console</div>
					<div class="item">Garamond</div>
					<div class="item">MS Sans Serif</div>
					<div class="item">MS Serif</div>
					<div class="item">Palatino Linotype</div>
					<div class="item">Symbol</div>
					<div class="item">Bookman Old Style</div>
					<div class="item more">自定义</div>
				</div>
			</div>
			<div class="list size">
				<input type="text" value="12" />
				<div class="down"></div>
			</div>
			<div class="icon bold checked"></div>
			<div class="icon italic"></div>
			<div class="icon underline"></div>
			<div class="split"></div>
			<!-- div class="icon color"></div>
			<div class="icon fill"></div>
			<div class="icon stroke"></div>
			<div class="split"></div>
			<div class="icon undo"></div>
			<div class="icon redo"></div-->
			
			<div class="split"></div>
			<div class="icon cut"></div>
			<div class="icon copy"></div>
			<div class="icon paste"></div>
			<div class="icon delete"></div>
			<div class="split"></div>
			<!--div class="icon zoom-in"></div>
			<div class="icon zoom-out"></div>
			<div class="icon zoom-actual"></div-->
		</div>
		<div class="section design" style="display:none">
			<div class="action page-size">页面尺寸</div>
			<div class="action fit">自动调整大小</div>
			<div class="action format">语法检查</div>
			<div class="action layout">调整布局</div>
		</div>
		<div class="section share" style="display:none">
			<div class="action link">生成共享链接</div>
			<div class="action cooperation">与他人协作</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function (){
	$("#editor>.header>.menubar>.menu.start").click(function (){
		$("#editor>.header>.menubar>.menu.selected").removeClass("selected");
		$(this).addClass("selected");
		$("#editor>.header>.toolbar>.section").hide();
		$("#editor>.header>.toolbar>.section.start").show();
	});
	$("#editor>.header>.menubar>.menu.design").click(function (){
		$("#editor>.header>.menubar>.menu.selected").removeClass("selected");
		$(this).addClass("selected");
		$("#editor>.header>.toolbar>.section").hide();
		$("#editor>.header>.toolbar>.section.design").show();
	});
	$("#editor>.header>.menubar>.menu.share").click(function (){
		$("#editor>.header>.menubar>.menu.selected").removeClass("selected");
		$(this).addClass("selected");
		$("#editor>.header>.toolbar>.section").hide();
		$("#editor>.header>.toolbar>.section.share").show();
	});

	function get_font_list(){

	}
});
</script>