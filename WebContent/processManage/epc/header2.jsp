<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="menubar">
	<div class="file">文件</div>
	<div class="item start selected">开始</div>
	<div class="item design">设计</div>
	<div class="item view">视图</div>
	<div class="item configuration">配置</div>
	<div class="item help">帮助</div>
	<div class="name">流程1</div>
	<div class="username">欢迎您！<span><%=session.getAttribute("PPusername") %></span></div>
</div>
<div id="toolbar">
	<div class="file">
		<div class="left">
			<div class="item new">新建</div>
			<div class="item open">打开</div>
			<div class="item save">保存</div>
			<div class="item import">导入本地文件</div>
			<div class="item export">导出为EPML</div>
			<div class="item print" onclick="javascript:window.print()">打印</div>
		</div>
		<div class="right"></div>
	</div>
	<div class="section start">
		<div class="undo-redo">
			<div class="undo" title="撤销"></div>
			<div class="redo" title="恢复"></div>
		</div>
		<div class="split"></div>
		<div class="paste">粘贴</div>
		<div class="cut-copy">
			<div class="cut">剪切</div>
			<div class="copy">复制</div>
		</div>
		<div class="split"></div>
		<div class="font">
			<div class="top">
				<div class="family">
					<div class="text">宋体</div>
					<div class="dropdown"></div>
				</div>
				<div class="size">
					<div class="text">12</div>
					<div class="dropdown"></div>
				</div>
			</div>
			<div class="bottom">
				<div class="bold"></div>
				<div class="italic"></div>
				<div class="underline"></div>
				<div class="split"></div>
				<div class="size-up">
					<div>A</div>
					<div class="sup">+</div>
				</div>
				<div class="size-down">
					<div>A</div>
					<div class="sup">-</div>
				</div>
				<div class="color">
					<div class="dropdown"></div>
					<div class="icon"></div>
					<div class="bottom"></div>
				</div>
			</div>
		</div>
		<div class="split"></div>
		<div class="cell">
			<div class="top">
				<div class="left"></div>
				<div class="center selected"></div>
				<div class="right"></div>
				<div class="split"></div>
				<div class="top"></div>
				<div class="middle selected"></div>
				<div class="bottom"></div>
				<div class="split"></div>
				<div class="select selected"></div>
				<div class="pan"></div>
			</div>
			<div class="bottom">
				<div class="line-width">
					<div class="dropdown"></div>
					<div class="icon"></div>
				</div>
				<div class="line-shape">
					<div class="dropdown"></div>
					<div class="icon"></div>
				</div>
				<div class="line-style">
					<div class="dropdown"></div>
					<div class="icon"></div>
				</div>
				<div class="line-color">
					<div class="dropdown"></div>
					<div class="icon"></div>
					<div class="bottom"></div>
				</div>
				<div class="fill-color">
					<div class="dropdown"></div>
					<div class="icon"></div>
					<div class="bottom"></div>
				</div>
			</div>
		</div>
		<div class="split"></div>
		<div class="find">
			<div class="find">查找</div>
			<div class="replace">替换</div>
		</div>
	</div>
	<div class="section design">纸张大小、纸张方向、智能调整</div>
	<div class="section view">10%、15%、25%、50%、75%、100%、125%、150%、200%、250%、等宽、适应屏幕</div>
	<div class="section configuration">配置</div>
	<div class="section help">使用说明、工具特点、参考文献、联系我</div>
</div>