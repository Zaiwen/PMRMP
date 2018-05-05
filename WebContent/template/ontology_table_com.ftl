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
			命名空间
		</td>
		<td id="namespace">
			${namespace}
		</td>
	</tr>
	<tr>
		<td>
			句子标识符
		</td>
		<td id="identifier">
			${identifier}
		</td>
	</tr>
	<tr>
		<td>
			本体构件类型
		</td>
		<td id="component_type">
		${component_type}
		</td>
	</tr>
	<tr>
		<td>
			使用
		</td>
		<td>
				<select size="3">
				 <#list atomics as atomic>
					<option id="atomics">
						${atomic.com}
					</option>
				 </#list>
				</select>
		</td>
	</tr>
</table>