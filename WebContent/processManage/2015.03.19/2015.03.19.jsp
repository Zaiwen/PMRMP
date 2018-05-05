<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="window-right">
	<div class="head">
		<div class="title">配置PreCondition</div>
	</div>
	<div class="body">
		<div id="automic-process">
			<!-- div class="title">管理原子流程</div>
			<div class="label">选择原子流程</div>
			<div class="select">
				<div class="selected"></div>
				<div class="down"></div>
				<ul>
					<li>AAAAAAAAAAAAAAAAAA</li>
					<li>AAAAAAAAAAAAAAAAAA</li>
					<li>AAAAAAAAAAAAAAAAAA</li>
					<li>AAAAAAAAAAAAAAAAAA</li>
				</ul>
			</div>
			<div>
				<div class="button">添加</div>
				<div class="button">删除</div>
				<div class="button">重命名</div>
			</div>
			<div style="clear:both"></div>
			<div class="title">配置前置条件</div>
			<div class="label">选择逻辑关系</div>
			<div class="radio">AND</div>
			<div class="radio">OR</div>
			<div class="radio">XOR</div-->
			<div class="button add">添加</div>
			<div class="button" onclick='javascript:$("#editor").removeClass("readonly")'>保存</div>
			<div style="clear:both;"></div>
			<table>
				<tr>
					<td>Predicate:</td>
					<td></td>
				</tr>
				<tr>
					<td>Argument1:</td>
					<td></td>
				</tr>
				<tr>
					<td>Argument2:</td>
					<td></td>
				</tr>
			</table>
			<br/>
			<br/>
			<div style="clear:both;"></div>
			
		</div>
	</div>
</div>
<div id="center-split"></div>
<div id="split-20" class="split"></div>
<div id="split-80" class="split"></div>
<div id="split-100" class="split"></div>
<div id="split-160" class="split"></div>
<div id="atomic-process-graph-window" class="window">
	<div class="head">
		<div class="title">所有信息</div>
	</div>
	<div class="body"></div>
</div>
<div id="atomic-process-detail-window" class="window">
	<div class="head">
		<div class="title">详细</div>
	</div>
	<div class="body"></div>
</div>