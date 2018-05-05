<table class="onttable" align="center">

	<tr>
		<td>
			属性/引用
		</td>
		<td>
			值/实例
		</td>
	</tr>
	<tr>
		<td>
			管理项的管理
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			统一资源标识符
		</td>
		<td id="content">
			${content}
		</td>
	</tr>
	<tr>
		<td>
			本体名称
		</td>
		<td id="nm">
			${nm}
		</td>
	</tr>
	<tr>
		<td>
			模型类型
		</td>
		<td id="modelType">
			${modelType}
		</td>
	</tr>
	<tr>
		<td>
			本体类型
		</td>
		<td id="type">
			${type}
		</td>
	</tr>
	<tr>
		<td>
			由…组成
		</td>
		<td>
			<select size="5">
			 <#list components as component>
				<option id="components">
					${component.com}
				</option>
			 </#list>
			</select>
		</td>
	</tr>
</table>