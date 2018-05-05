var $dlg_ato_proc = null;
function dlg_ato_proc_init() {
	var cfg = {};
	var _but=$("#dlg-ato-proc>.name>.button");
	cfg.autoOpen = false;
	cfg.close = dlg_ato_proc_close;
	cfg.height = 480;
	cfg.modal = true;
	cfg.open = dlg_ato_proc_open;
	cfg.resizable = false;
	cfg.title=language=="zh_CN"?"原子流程":"Automic Process";
	cfg.width = 720;
	$dlg_ato_proc = $("#dlg-ato-proc");
	$dlg_ato_proc.dialog(cfg);
	_but.button();
	_but.click(dlg_ato_proc_rename);
	if($mode!="proc-edit"){
		_but.hide();
		$dlg_ato_proc.find("rename,anno").remove();
	} 
}

function html_encode(str)
{
	if(!str) return "";
	return $("<div/>").text(str).html();
}
// 暂未写
function html_decode(html)
{
	
}

$(function (){
	$("#dlg-ato-proc .precondition .add").live("click",function (){
		var html = "";
		html += "<li><span class='delete'>删除</span>"
		html += "<table>";
		html += "<tr class='predicate'>";
		html += "<td class='name'>Predicate:</td>";
		html += "<td class='value'></td>";
		html += "<td class='type'></td>";
		html += "<td class='rename'>" + html_encode(string['rename']) + "</td>";
		html += "<td class='retype'>" + html_encode(string['retype']) + "</td>";
		html += "</tr>";
		html += "<tr class='argument1'>";
		html += "<td class='name'>Argument1:</td>";
		html += "<td class='value'></td>";
		html += "<td class='type'></td>";
		html += "<td class='rename'>" + html_encode(string['rename']) + "</td>";
		html += "<td class='retype'>" + html_encode(string['retype']) + "</td>";
		html += "</tr>";
		html += "<tr class='argument2'>";
		html += "<td class='name'>Argument2:</td>";
		html += "<td class='value'></td>";
		html += "<td class='type'></td>";
		html += "<td class='rename'>" + html_encode(string['rename']) + "</td>";
		html += "<td class='retype'>" + html_encode(string['retype']) + "</td>";
		html += "</tr>";
		html += "</table>";
		html += "</li>";
		//$("#dlg-ato-proc .precondition ol").append($(html));
		new ConditionDialog(new Condition(),function (){});
	});
});
/**
 * 生成对单个Condition进行编辑的对话框
 * @param condition {Condition}
 * @param callback {Function(condition)} 对单个Condition编辑完成之后的回调函数，参数为编辑好的Condition
 * */
function ConditionDialog(condition, callback){
	var div = $("<div>"
		+ "<table>"
			+ "<tr class='header'>"
				+ "<th class='name'></th>"
				+ "<th class='value'>名称</th>"
				+ "<th class='type'>类型</th>"
				+ "<th class='rename'></th>"
				+ "<th class='retype'></th>"
			+ "</tr>"
			+ "<tr class='predicate'>"
				+ "<td class='name'>Predicate:</td>"
				+ "<td class='value'>" + html_encode(condition.getPredicate()) + "</td>"
				+ "<td class='type'>" + html_encode(condition.getPredicateType()) + "</td>"
				+ "<td class='rename'>重命名</td>"
				+ "<td class='retype'>更改标注</td>"
			+ "</tr>"
			+ "<tr class='argument1'>"
				+ "<td class='name'>Argument1:</td>"
				+ "<td class='value'>" + html_encode(condition.getArgument1()) + "</td>"
				+ "<td class='type'>" + html_encode(condition.getArgument1Type()) + "</td>"
				+ "<td class='rename'>重命名</td>"
				+ "<td class='retype'>更改标注</td>"
			+ "</tr>"
			+ "<tr class='argument2'>"
				+ "<td class='name'>Argument2:</td>"
				+ "<td class='value'>" + html_encode(condition.getArgument2()) + "</td>"
				+ "<td class='type'>" + html_encode(condition.getArgument2Type()) +"</td>"
				+ "<td class='rename'>重命名</td>"
				+ "<td class='retype'>更改标注</td>"
			+ "</tr>"
		+ "</table>"
	+ "</div>");
	var config = {};
	config.buttons = [{}, {}];
	config.buttons[0].text = "确定";
	config.buttons[0].click = _ok;
	config.buttons[1].text = "取消";
	config.buttons[1].click = _close;
	config.close = _close;
	config.height = 300;
	config.modal = true;
	config.title = "Condition";
	config.width = 720;
	div.dialog(config);
	
	function _ok(){
		var condition = new Condition();
		condition.setPredicate(div.find(".predicate .value").text());
		condition.setPredicateType(div.find(".predicate .type").text());
		condition.setArgument1(div.find(".argument1 .value").text());
		condition.setArgument1Type(div.find(".argument1 .type").text());
		condition.setArgument2(div.find(".argument2 .value").text());
		condition.setArgument2Type(div.find(".argument2 .type").text());
		callback(condition);
		console.log(condition)
		_close();
	}
	
	function _close(){
		div.dialog("destroy");
		div.remove();
	}
	// 一些样式
	div.find(".rename,.retype").css({"color":"blue","cursor":"pointer"})
	// 重命名
	div.find("td.rename").click(function (){
		var str = prompt("输入新的名称：", $(this).prevAll(".value").text());
		$(this).prevAll(".value").text(str);
	});
	// 本体标注
	div.find("td.retype").click(function (){
		var obj = this;
		OntologyListDialog(function (ontology){
			$(obj).prevAll("td.type").text(ontology);
		});
	});
}



/**
 * 生成包含Ontology列表的对话框
 * @param callback {Function(ontology)} 选则好Ontology之后的回调函数，Ontology会作为回调函数的参数
 * */
function OntologyListDialog(callback){
	// 配置对话框
	var div = $("<div>loading</div>");
	var config = {};
	config.close = _dialog_close;
	config.height = 300;
	config.modal = true;
	config.title = "Ontology List";
	config.width = 720;
	div.dialog({
		"height"	: "300",
		"modal"		: true,
		"title"		: "Ontology List",
		"width"		: "720",
	});
	
	function _dialog_close(){
		div.dialog("destroy");
		div.remove();
	}
	// 异步请求本体列表
	var ajax_config = {};
	ajax_config.url = "get-ont-list.jsp";
	ajax_config.dataType = "json";
	ajax_config.cache = false;
	ajax_config.success = _ajax_success;
	$.ajax(ajax_config);
	
	function _ajax_success(data){
		var html = "<table><tr><th>名称</th><th>领域</th><th>URI</th><th/></tr>";
		for ( var i = 0; i < data.length; i += 1) {
			html += "<tr class='ontology'>";
			html += "<td class='name'>" +html_encode(data[i].name) + "</td>";
			html += "<td class='domain'>" +html_encode(data[i].domain) + "</td>";
			html += "<td class='uri'>" +html_encode(data[i].uri) + "</td>";
			html += "<td class='open' style='color:blue;cursor:pointer;'>打开</td>";
			html += "</tr>";
		}
		html += "</table>";
		div.html(html);
		$(div).find("td.open").click(_open);
	}

	function _open (){
		var uri = $(this).prevAll("td.uri").text();
		OntologyDetailDialog(uri, function (ontology){
			callback(ontology);
			_dialog_close();
		});
	}
}
/**
 * 生成包含Ontology树形关系图的对话框
 * @param uri {String} 目标Ontology的地址
 * @param callback {Function(ontology)} 选则好Ontology之后的回调函数，Ontology会作为回调函数的参数
 * */
function OntologyDetailDialog(uri,callback){
	// 配置对话框 
	var div = $("<div/>");
	
	var button = [{}, {}];
	button[0].text = "选择";
	button[0].click = _dialog_button_select;
	button[1].text = "取消";
	button[1].click = _dialog_button_cancel;
	
	var config = {};
	config.buttons = button;
	config.close = _dialog_close;
	config.height = 480;
	config.width = 720;
	config.modal = true;
	config.title = "Ontology Detail";
	div.dialog(config);
	
	function _dialog_button_select(){
		callback(div.find(".jstree-clicked").attr("title"));
		_dialog_close();
	}
	
	function _dialog_button_cancel(){
		_dialog_close();
	}
	
	function _dialog_close(){
		div.dialog("destroy");
		div.remove();
	}
	// 请求本体数据 
	var ajax_config = {};
	ajax_config.url = "get-ont-info.jsp";
	ajax_config.cache = false;
	ajax_config.data = {uri : uri};
	ajax_config.dataType = "json";
	ajax_config.success = _ajax_success;
	$.ajax(ajax_config);
	
	function _ajax_success(data){
		// string 为所有Ontology的父类
		var string = {};
		string.URI = "httP://www.w3.org/2001/XMLSchema#string";
		string.localName = "string";
		string.children = [];
		// 将原本的父节点表示树转为子节点表示，string为树的顶点
		for(var i = 0;i<data.length;i++){
			var flag = false;
			for(var j = 0; j < data.length; j++){
				if(data[i].superClass == data[j].URI){
					flag = true;
					if(!data[j].children){
						data[j].children = [data[i]];
					}else{
						data[j].children.push(data[i])
					}
				}
			}
			if(!flag){
				string.children.push(data[i]);
			}
		}
		// 生成HTML添加到对话框中
		$("<ul/>").append(_generate_html(string)).appendTo(div);
		// 以树的模型显示出来
		var jstree_config = {};
		jstree_config.core =  {animation : 0, load_open : true};
		jstree_config.plugins = ["themes", "html_data", "ui"];
		jstree_config.ui = {select_limit : 1, select_prev_on_delete : true};
		jstree_config.themes = {theme : "classic", dots : true, icons : false};
		div.jstree(jstree_config);
		// 展开所有节点
		setTimeout(function (){div.jstree("open_all")},100)
	}
	// 递归生成所有HTML节点
	function _generate_html(node){
		var li = $("<li/>");
		li.append($("<a href='#'/>").text(node.localName).attr("title",node.URI));
		if(!node.children){return li;}
		var ul = $("<ul/>");
		for(var i = 0; i < node.children.length; i++){
			ul.append(_generate_html(node.children[i]));
		}
		li.append(ul);
		return li;
	}
}




function AtomicProcessDialog(process) {
	// Precondition界面
	var div = "<div>";
	div += "<div class='precondition'>";
	div += "<h3>前置条件</h3>";
	//div += "<span>逻辑关系:</span><input type='radio'/>AND<input type='radio'/>OR<input type='radio'/>XOE<br/><br/>";
	div += "<span class='add' style='font-size:12px;color:blue;font-weight:normal;cursor:pointer'>添加</span>";
	div += "<table/>";
	div += "</div>"
	// Postcondition界面
	div += "<div>";
	div += "<div class='postcondition'>";
	div += "<h3>后置条件</h3>";
	div += "<span class='add' style='font-size:12px;color:blue;font-weight:normal;cursor:pointer'>添加</span>";
	div += "<table/>";
	div += "</div>"
	// 输入
	var input = process.getInput();
	div += "<div class='input'><h3>" + string["input"] +"</h3>";
	div += "<table>";
	div += "<tr>";
	div += "<th>" + html_encode(string["name"]) + "</th>";
	div += "<th>" + html_encode(string["type"]) + "</th>";
	//div += "<th/>";
	div += "<th/>";
	div += "</tr>"
	for(var i in input)
	{
		div += "<tr class='item'>";
		div += "<td class='name'>" + html_encode(input[i].toString()) + "</td>";
		div += "<td class='type'>" + html_encode(input[i].getType()) + "</td>";
		//div += "<td class='rename' style='color:blue;cursor:pointer;'>" + html_encode(string["rename"]) + "</td>";
		div += "<td class='retype' style='color:blue;cursor:pointer;'>" + html_encode(string["retype"]) + "</td>";
		div += "</tr>";
	}
	div += "</table></div>";
		
	// 输出
	var output = process.getOutput();
	div += "<div class='output'><h3>" + string["output"] +"</h3>";
	div += "<table>";
	div += "<tr>";
	div += "<th>" + html_encode(string["name"]) + "</th>";
	div += "<th>" + html_encode(string["type"]) + "</th>";
	//div += "<th/>";
	div += "<th/>";
	div += "</tr>"
	for(var i in output){
		div += "<tr class='item'>";
		div += "<td class='name'>" + html_encode(output[i].toString()) + "</td>";
		div += "<td class='type'>" + html_encode(output[i].getType()) + "</td>";
		//div += "<td class='rename' style='color:blue;cursor:pointer;'>" + html_encode(string["rename"]) + "</td>";
		div += "<td class='retype' style='color:blue;cursor:pointer;'>" + html_encode(string["retype"]) + "</td>";
		div += "</tr>";
	}
	div += "</table>";
	div += "</div></div>";
	
	// 生成对话框
	var config = {};
	config.close = _close;
	config.height = 480;
	config.modal = true;
	config.title = "原子流程";
	config.width = 720;
	div = $(div).dialog(config);
	
	function _close(){
		div.dialog("destroy");
		div.remove();
	}
	
	function _precondition(){
		var html = "";
		for(var i = 0; i < process.getPrecondition().length; i++){
			var condition = process.getPrecondition()[i];
			html += "<tr><td class='text'>" + condition.toString() +"</td>"
			html += "<td class='detail' style='color:blue;cursor:pointer'>详细</td>";
			html += "<td class='delete' style='color:blue;cursor:pointer'>删除</td></tr>";
		}
		div.find(".precondition table").html(html);
		// 详细
		div.find(".precondition .detail").click(function (){
			for(var i = 0; i < div.find(".precondition .detail").length; i++){
				if(this == div.find(".precondition .detail")[i]){
					break;
				}
			}
			ConditionDialog(process.getPrecondition()[i],function (condition){
				process.getPrecondition()[i] = condition;
				_precondition();
			});
		});
		// 删除
		div.find(".precondition .delete").click(function (){
			for(var i = 0; i < div.find(".precondition .delete").length; i++){
				if(this == div.find(".precondition .delete")[i]){
					break;
				}
			}
			process.removePrecondition(i);
			_precondition();
		});
	}
	
	function _postcondition(){
		var html = "";
		for(var i = 0; i < process.getPostcondition().length; i++){
			var condition = process.getPostcondition()[i];
			html += "<tr><td class='text'>" + condition.toString() +"</td>"
			html += "<td class='detail' style='color:blue;cursor:pointer'>详细</td>";
			html += "<td class='delete' style='color:blue;cursor:pointer'>删除</td></tr>";
		}
		div.find(".postcondition table").html(html);
		// 详细
		div.find(".postcondition .detail").click(function (){
			for(var i = 0; i < div.find(".postcondition .detail").length; i++){
				if(this == div.find(".postcondition .detail")[i]){
					break;
				}
			}
			ConditionDialog(process.getPostcondition()[i],function (condition){
				process.getPostcondition()[i] = condition;
				_postcondition();
			});
		});
		// 删除
		div.find(".postcondition .delete").click(function (){
			for(var i = 0; i < div.find(".postcondition .delete").length; i++){
				if(this == div.find(".postcondition .delete")[i]){
					break;
				}
			}
			process.removePostcondition(i);
			_postcondition();
		});
	}
	
	// 添加Precondition
	div.find(".precondition .add").click(function (){
		ConditionDialog(new Condition(), function (condition){
			process.addPrecondition(condition);
			_precondition();
		});
	});
	
	_precondition();
	
	// 添加Postcondition
	div.find(".postcondition .add").click(function (){
		ConditionDialog(new Condition(), function (condition){
			process.addPostcondition(condition);
			_postcondition();
		});
	});
	
	_postcondition();
	
	// input标注
	div.find(".input .retype").click(function (){
		var obj = this;
		OntologyListDialog(function (ontology){
			$(obj).prevAll(".type").text(ontology);
			OWLModel.get($(obj).prevAll(".name").text()).setType(ontology);
		});
	});
	
	// output标注
	div.find(".output .retype").click(function (){
		var obj = this;
		OntologyListDialog(function (ontology){
			$(obj).prevAll(".type").text(ontology);
			OWLModel.get($(obj).prevAll(".name").text()).setType(ontology);
		});
	});
	// 代码结构当初设计的有问题，一旦重命名，有很多引用就会失效，重写代价太大，暂不考虑
	// input重命名
//	div.find("input .rename").click(function (){
//		var oldName = $(this).prevAll(".name").text();
//		var newName = prompt("输入新的名称：", oldName);
//		if(newName != null && newName != "" && newName != oldName && OWLModel.isAvailable(newName)){
//			OWLModel.get(oldName).setId(newName);
//		}
//	});
	
	//$("#dlg-ato-proc .precondition .content").html(str);
	/*
	// 显示所有输入
	var _input=$dlg_ato_proc.find(".input table");
	for(var i in _proc.getInput()){
		var _param=_proc.getInput()[i];
		var _name=$("<td class='name'/>").text(_param+"");
		var _type=$("<td class='type'/>").text(_param.getType());
		var _rename=$("<td class='rename'/>").text(language=="zh_CN"?"重命名":"Rename");
		var _anno=$("<td class='anno'/>").text(language=="zh_CN"?"标注":"Mark");
		var _tr=$("<tr class='item'/>");
		_tr.append(_name).append(_type);
		_tr.append(_rename).append(_anno);
		_tr.appendTo(_input);
		_rename.click(dlg_ato_proc_rename_param);
		_anno.click(dlg_ato_proc_anno);
	}
	// 显示所有输出
	var _output=$dlg_ato_proc.find(".output table");
	for(var i in _proc.getOutput()){
		var _param=_proc.getOutput()[i];
		var _name=$("<td class='name'/>").text(_param+"");
		var _type=$("<td class='type'/>").text(_param.getType());
		var _rename=$("<td class='rename'/>").text(language=="zh_CN"?"重命名":"Rename");
		var _anno=$("<td class='anno'/>").text(language=="zh_CN"?"标注":"Mark");
		var _tr=$("<tr class='item'/>");
		_tr.append(_name).append(_type);
		_tr.append(_rename).append(_anno);
		_tr.appendTo(_output);
		_rename.click(dlg_ato_proc_rename_param);
		_anno.click(dlg_ato_proc_anno);
	}
	$dlg_ato_proc.html(html);*/
}
// 当该对话框关闭时
function dlg_ato_proc_close()
{
	$dlg_ato_proc.find("table").find("tr:gt(0)").remove();
}
// 对原子流程重命名
function dlg_ato_proc_rename() 
{
	var _old = $dlg_ato_proc.find(".name .value").text();
	var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Pleae input new name", _old);
	var _proc = OWLModel.get(_old);
	if (_new != null && _old != _new) 
	{
		if (OWLModel.isIdAvailable(_new)) 
		{
			_proc.setId(_new);
			$("#process-dialog>.name>.value").text(_new);
			UpdateProcessView();
		} 
		else 
		{
			alert(language=="zh_CN"?"该名称不可用！":"it it unavaliable");
		}
	}
	
}
// 对原子流程的某个参数重命名
function dlg_ato_proc_rename_param()
{
	var _old = $(this).parent().children(".name").text();
	var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Please input new name", _old);
	var _proc =OWLModel.get($dlg_ato_proc.find(".name .value").text());
	if (_new != null && _old != _new) 
	{
		if (OWLModel.isIdAvailable(_new)) 
		{
			OWLModel.changeId(_old, _new);
			// 判断进行重命名的是流程的输入还是输出
			if(_proc.getInput()[_old] != null)
			{
				_proc.getInput()[_new] = _proc.getInput()[_old];
				delete _proc.getInput()[_old];
			}
			else
			{
				_proc.getOutput()[_new] = _proc.getOutput()[_old];
				delete _proc.getOutput()[_old];
			}
			$(this).parent().children(".name").text(_new);
		} 
		else 
		{
			alert(language=="zh_CN"?"该名称不可用！":"it is unavaliable");
		}
	}
}

function dlg_ato_proc_anno() 
{
	var text = $(this).parent().children("td:eq(0)").text();
	$temp_owls_param = OWLModel.get(text);
	$dlg_ato_proc.dialog("close");
	$dlg_ont_list.dialog("open")
	
}

$(function (){
	//dlg_ato_proc_init();
});

$(function() {
	var $dialog = $("#process-dialog");
	var process = null;
	// 对话框打开
	function dialogOpen() {
		// 得到原子流程
		var text = $("#atomic-process-list-window>.body>.selected").text();
		process = OWLModel.get(text);
		// 显示对话框标题及流程名
		$dialog.dialog( {
			"title" : language=="zh_CN"?"Process属性--":"Process Property--" + text
		});
		$("#process-dialog>.name>.value").text(text);
		// 显示流程的输入输出
		$("#process-dialog .container").children().remove();
		$("#process-dialog>.hasInput>.container").append(
				createTable(process.getInput()));
		$("#process-dialog>.hasOutput>.container").append(
				createTable(process.getOutput()));
	}
	// 创建输入或输出表格
	function createTable(arr) {
		var $table = $("<table/>");
		var $tr = $("<tr/>").appendTo($table);
		var $name = $("<th class='name'>Name</th>").appendTo($tr);
		var $type = $("<th class='type'>Type</th>").appendTo($tr);
		var $edit = null;
		for ( var i in arr) {
			var param = arr[i];
			$tr = $("<tr/>");
			$name = $("<td class='name'/>");
			$type = $("<td class='type'/>");
			$edit = $("<td class='edit'>"+language=="zh_CN"?"编辑":"Edit"+"</td>");
			$edit.click(function() {
				var text = $(this).parent().children(".name").text();
				var param = OWLModel.get(text);
				$("#process-dialog").dialog("close");
				$("#param-dialog").dialog( {
					close : function() {
						$("#process-dialog").dialog("open");
					}
				});
				$("#param-dialog")[0].param = param;
				$("#param-dialog").dialog("open");
			});
			$name.text(param + "");
			$type.text(param.getType() + "");
			$tr.append($name).append($type).append($edit);
			$table.append($tr);
		}
		return $table;
	}
	// 改名字
	$("#process-dialog>.name>.button").button();
	$("#process-dialog>.name>.button").click(function() {
		var _old = $("#process-dialog>.name>.value").text();
		var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Please input new name", _old);
		if (_new != null && _old != _new) {
			if (OWLModel.isIdAvailable(_new)) {
				process.setId(_new);
				$("#process-dialog>.name>.value").text(_new);
				$("#atomic-process-list-window>.body>.selected").text(_new);
				$dialog.dialog( {
					"title" : language=="zh_CN"?"Process属性--":"Process Property" + _new
				});
			} else {
				alert(language=="zh_CN"?"该名称不可用！":"it is unavaliable");
			}
		}
	});
	$dialog.dialog( {
		autoOpen : false,
		height : 480,
		modal : true,
		open : dialogOpen,
		resizable : false,
		width : 640
	});
});

/**
 * test
 
$(function (){
	var process = new AtomicProcess("test");
	OWLModel.ontology["test"]=process;
	var input1 = new Input("abcd");
	input1.setType("www.test.com#abcdefg");
	process.addInput(input1);
	UpdateProcessView();
});*/

