<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="jsp/extension/description.jsp"></jsp:include>
<jsp:include page="jsp/extension/query-pattern.jsp"></jsp:include>
<jsp:include page="jsp/extension/query-variable.jsp"></jsp:include>
<jsp:include page="jsp/extension/query-result.jsp"></jsp:include>

<div id="dialog-cover"></div>



<div id="plugin-dialog" class="dialog">
	<div class="head">
		<div class="title">
			插件开发向导
		</div>
		<div class="minimize icon" title="最小化"></div>
		<a class="help icon" title="帮助" href="extension-help.html"
			target="_blank"></a>
	</div>
	<div class="body">
		<div class="navi">
			<ol>
				<li class="description current">
					插件描述
				</li>
				<li class="query">
					语义查询
				</li>
				<li class="pattern">
					扩展模式
				</li>
				<li class="grounding">
					服务绑定
				</li>
				<li class="binding">
					数据绑定
				</li>
				<li class="finish">
					配置完成
				</li>
			</ol>
		</div>
		<div class="content">
			<div class="pattern">
				<p>
					选择扩展模式：
					<a href="extension-help.html#pattern" target="_blank">(这些是什么？)</a>
				</p>
				<ul>
					<li class="preposition">
						<input type="radio" checked="checked" value="1" />
						前置(preposition)
					</li>
					<li class="prefix">
						<input type="radio" value="2" />
						前缀(prefix)
					</li>
					<li class="substitution">
						<input type="radio" value="3" />
						替换(substitution)
					</li>
					<li class="postposition">
						<input type="radio" value="4" />
						后置(postposition)
					</li>
					<li class="append">
						<input type="radio" value="5" />
						附加(append)
					</li>
					<li class="synchronized">
						<input type="radio" value="6" />
						同步(synchronized)
					</li>
				</ul>
			</div>
			<div class="grounding-wsdl">
				<div class="title">
					选择要进行绑定的Web服务:
					<span>刷新</span>
				</div>
				<div class="content">
					<jsp:include page="get-wsdl-list.jsp"></jsp:include>
				</div>
			</div>
			<div class="grounding-operation">
				<div class="title">
					选择Web服务中的一个操作：
				</div>
				<div class="content">
					<ol></ol>
				</div>
			</div>
			<div class="binding">
				<div class="title">
					数据绑定
				</div>
				<div class="graph"></div>
			</div>
			<div class="complete">
				配置完成
			</div>
		</div>
	</div>
	<div class="foot">
		<div class="button next">
			下一步
		</div>
		<div class="button prev">
			上一步
		</div>
	</div>
</div>