﻿var $edt_readonly = true;

var $edit_json = {};

// 插件的名称
var $ext_name = null;
// 插件的领域
var $ext_domain = null;
// 对插件的介绍
var $ext_intro = null;
//3种查询方式variable condition activity
var $ext_query_ptn = null;
var $ext_point_type=null;
// 插件的输入（偶数下标为参数名，奇数下标为数据绑定）
var $ext_input = new Array();
// 插件的输出（偶数下标为参数名，奇数下标为数据绑定）
var $ext_output = new Array();
var $ext_query = null;
var $ext_ptn = null;
//插件的4种控制流
var $ext_control_ptn=null;
//condition查询时7种数据流
var $ext_data_ptn=null;

// 基于变量的查询中的输入的本体标注
var $ext_query_var_input = new Array();
// 基于变量的查询中的输出的本体标注
var $ext_query_var_output = new Array();
// 查询结果（Perform的ID构成的数组）
var $ext_query_rst = null;
// 进行查询是使用到的SPARQL-DL语句
var $ext_sparqldl = new Array();

//基于条件查询的precondition postcondition
var $ext_precondition=null;
var $ext_postcondition=null;
var $ext_processDTOname=null;
var $ext_preconditionDTO=null;
var $ext_postconditionDTO=null;
var $ext_condition_query_rst = null;
var $wsdl_list = null;
// 插件绑定的Web服务的地址
var $ext_wsdl_url = null;
// Web服务的具体操作
var $ext_wsdl_op = null;

var $ext_wsdl_port_type = null;

var $ext_wsdl_in_msg_name = null;

var $ext_wsdl_out_msg_name = null;

var $dlg_ext_des = null;

var $dlg_ext_query_ptn = null;

var $dlg_ext_query_var = null;
var $dlg_ext_condition_query = null;

var $dlg_ext_query_rst = null;
var $dlg_ext_condition_query_rst = null;

var $dlg_ext_query_rst_on = false;
var $dlg_ext_condition_query_rst_on = false;

var $dlg_ext_ptn = null;
var $dlg_ext_condition_ptn=null;

var $dlg_ext_wsdl_url = null;
var $dlg_ext_condition_wsdl_url = null;

var $dlg_ext_wsdl_url_on = false;
var $dlg_ext_condition_wsdl_url_on = false;

var $dlg_ext_wsdl_op = null;
var $dlg_ext_condition_wsdl_op = null;

var $dlg_ext_wsdl_op_on = false;
var $dlg_ext_condition_wsdl_op_on = false;

var $dlg_ext_bind_prfx = null;
var $dlg_ext_condition_bind_prfx = null;

var $dlg_ext_bind_prpo = null;
var $dlg_ext_condition_bind_prpo = null;

var $dlg_ext_bind_pofx = null;
var $dlg_ext_condition_bind_pofx = null;

var $dlg_ext_bind_popo = null;
var $dlg_ext_condition_bind_popo = null;

var $dlg_ext_bind_syn = null;
var $dlg_ext_condition_bind_syn = null;

var $dlg_ext_bind_sub = null;
var $dlg_ext_condition_bind_sub = null;

var $dlg_ext_ovw = null;
var $dlg_ext_condition_ovw = null;

// 0：没有请求、1：请求中……、2：请求成功、3：请求失败
var $ajax_sparqldl_stat = 0;
var $ajax_condition_stat = 0;

var $ajax_wsdl_op_stat = 0;

var $ajax_wsdl_op_data = null;
// 初始化
function ext_init(ext, readonly) {

}
// 添加基于变量的查询的输入条件
function ext_query_var_input_add(input) {
	var flag = false;
	for ( var i = 0; i < $ext_query_var_input.length; i += 1) {
		if ($ext_query_var_input[i] == input) {
			flag = true;
			break;
		}
	}
	if (!flag) {
		$ext_query_var_input.push(input);
		return true;
	} else {
		return false;
	}
}
// 移除基于变量的查询的输入条件
function ext_query_var_input_rmv(input) {
	var index = -1;
	for ( var i = 0; i < $ext_query_var_input.length; i += 1) {
		if ($ext_query_var_input[i] == input) {
			index = i;
			break;
		}
	}
	if (index != -1) {
		for ( var i = index; i < $ext_query_var_input.length - 1; i += 1) {
			$ext_query_var_input[i] = $ext_query_var_input[i + 1];
		}
		$ext_query_var_input.length -= 1;
		return true;
	} else {
		return false;
	}
}
// 添加基于变量的查询的输出条件
function ext_query_var_output_add(output) {
	var flag = false;
	for ( var i = 0; i < $ext_query_var_output.length; i += 1) {
		if ($ext_query_var_output[i] == output) {
			flag = true;
			break;
		}
	}
	if (!flag) {
		$ext_query_var_output.push(output);
		return true;
	} else {
		return false;
	}
}
// 移除基于变量的查询的输出条件
function ext_query_var_output_rmv(output) {
	var index = -1;
	for ( var i = 0; i < $ext_query_var_output.length; i += 1) {
		if ($ext_query_var_output[i] == output) {
			index = i;
			break;
		}
	}
	if (index != -1) {
		for ( var i = index; i < $ext_query_var_output.length - 1; i += 1) {
			$ext_query_var_output[i] = $ext_query_var_output[i + 1];
		}
		$ext_query_var_output.length -= 1;
		return true;
	} else {
		return false;
	}
}
// 将输入转化为Json
function ext_json_input() {
	var obj = [];
	for ( var i = 0; i < $ext_input.length; i += 2) {
		obj[i / 2] = {};
		obj[i / 2].Name = $ext_input[i];
		obj[i / 2].Binding = $ext_input[i + 1];
	}
	return obj;
}
// 将输出转化为Json
function ext_json_output() {
	var obj = [];
	for ( var i = 0; i < $ext_output.length; i += 2) {
		obj[i / 2] = {};
		obj[i / 2].Name = $ext_output[i];
		obj[i / 2].Binding = $ext_output[i + 1];
	}
	return obj;
}

// 向服务器发送请求以通过SPARQL-DL查询Perform
function ajax_sparqldl() {
	var cfg = {};
	cfg.cache = false;
	cfg.success = ajax_sparqldl_scc;
	cfg.error = ajax_sparqldl_err;
	cfg.type = "POST";
	cfg.dataType = "json";
	cfg.url = "sparqldl.jsp";
	cfg.data = ajax_sparqldl_data();
	$.ajax(cfg);
	$ajax_sparqldl_stat = 1;
	$ext_sparqldl = null;
	$ext_query_rst = null;
}
// 得到请求的参数
function ajax_sparqldl_data() {
	var obj = {};
	obj.Provider = $req_prv;
	obj.Process = $req_prc;
	obj.Input = JSON.stringify($ext_query_var_input);
	obj.Output = JSON.stringify($ext_query_var_output);
	return obj;
}
// 请求成功时
function ajax_sparqldl_scc(data) {
	if (data) {
		$ext_sparqldl = data["SPARQL-DL"];
		$ext_query_rst = data["Perform"];
		$ajax_sparqldl_stat = 2;
	} else {
		$ajax_sparqldl_stat = 3;
	}
	if ($dlg_ext_query_rst_on) {
		$dlg_ext_query_rst.dialog("close");
		$dlg_ext_query_rst.dialog("open");
	}
}
// 请求失败时
function ajax_sparqldl_err() {
	$ajax_sparqldl_stat = 3;
	if ($dlg_ext_query_rst_on) {
		$dlg_ext_query_rst.dialog("close");
		$dlg_ext_query_rst.dialog("open");
	}
}

//向服务器发送请求以通过Condition相似性查询ConditionID
function ajax_condition() {
	var cfg = {};
	cfg.cache = false;
	cfg.success = ajax_condition_scc;
	cfg.error = ajax_condition_err;
	cfg.type = "POST";
	cfg.dataType = "JSON";
	cfg.url = "condition-query.jsp";
	cfg.data = ajax_condition_data();
	$.ajax(cfg);
	$ajax_condition_stat = 1;
	$ext_condition_query_rst = null;
}
//得到请求的参数
function ajax_condition_data() {
	var obj = {};
	obj.Provider = $req_prv;
	obj.Process = $req_prc;
	obj.Precondition = $ext_precondition;
	obj.Postcondition = $ext_postcondition;
	return obj;
}
//请求成功时
function ajax_condition_scc(data) {
	if (data) {
		if(data.errors != undefined){
			$ajax_condition_stat = 3;
		}else{
			$ext_condition_query_rst = data["ConditionIDs"];
			$ajax_condition_stat = 2;
			if(data.preCondition != undefined){
				$ext_preconditionDTO=data.preCondition;
			}
			if(data.postCondition != undefined){
				$ext_postconditionDTO=data.postCondition;
			}
		}
	} else {
		$ajax_condition_stat = 3;
	}
	if ($dlg_ext_condition_query_rst_on) {
		$dlg_ext_condition_query_rst.dialog("close");
		$dlg_ext_condition_query_rst.dialog("open");
	}
}
//请求失败时
function ajax_condition_err() {
	$ajax_condition_stat = 3;
	if ($dlg_ext_condition_query_rst_on) {
		$dlg_ext_condition_query_rst.dialog("close");
		$dlg_ext_condition_query_rst.dialog("open");
	}
}
function ajax_wsdl_list() {
	var cfg = {};
	cfg.cache = false;
	cfg.success = ajax_wsdl_list_scc;
	cfg.error = ajax_wsdl_list_err;
	cfg.type = "POST";
	cfg.dataType = "text";
	cfg.url = "get-wsdl-list.jsp";
	$.ajax(cfg);
	$ajax_wsdl_list_stat = 1;
}

function ajax_wsdl_list_scc(data) {
	if ($dlg_ext_wsdl_url_on || $dlg_ext_condition_wsdl_url_on) {
		$wsdl_list = new Array();
		$(data).find("li").each(function() {
			$wsdl_list.push($(this).text());
		});
		$ajax_wsdl_list_stat = 2;
	}else {
		$ajax_wsdl_list_stat = 3;
	}
	if ($dlg_ext_wsdl_url_on) {
		$dlg_ext_wsdl_url.dialog("close");
		$dlg_ext_wsdl_url.dialog("open");
	}else if($dlg_ext_condition_wsdl_url_on){
		$dlg_ext_condition_wsdl_url.dialog("close");
		$dlg_ext_condition_wsdl_url.dialog("open");
	}
}

function ajax_wsdl_list_err() {
	$ajax_wsdl_list_stat = 3;
	if ($dlg_ext_wsdl_url_on) {
		$dlg_ext_wsdl_url.dialog("close");
		$dlg_ext_wsdl_url.dialog("open");
	}else if($dlg_ext_condition_wsdl_url_on){
		$dlg_ext_condition_wsdl_url.dialog("close");
		$dlg_ext_condition_wsdl_url.dialog("open");
	}
}

function ajax_wsdl_op() {
	var cfg = {};
	cfg.cache = true;
	cfg.success = ajax_wsdl_op_scc;
	cfg.error = ajax_wsdl_op_err;
	cfg.type = "POST";
	cfg.dataType = "json";
	cfg.url = "GetWsdlInfo.jsp?" + $ext_wsdl_url;
	$.ajax(cfg);
	$ajax_wsdl_op_data = null;
	$ajax_wsdl_op_stat = 1;
}

function ajax_wsdl_op_scc(data) {
	$ajax_wsdl_op_data = data;
	$ajax_wsdl_op_stat = 2;
	if ($dlg_ext_wsdl_op_on) {
		$dlg_ext_wsdl_op.dialog("close");
		$dlg_ext_wsdl_op.dialog("open");
	}else if($dlg_ext_condition_wsdl_op_on){
		$dlg_ext_condition_wsdl_op.dialog("close");
		$dlg_ext_condition_wsdl_op.dialog("open");
	}
}

function ajax_wsdl_op_err() {
	$ajax_wsdl_op_stat = 3;
	if ($dlg_ext_wsdl_op_on) {
		$dlg_ext_wsdl_op.dialog("close");
		$dlg_ext_wsdl_op.dialog("open");
	}else if($dlg_ext_condition_wsdl_op_on){
		$dlg_ext_condition_wsdl_op.dialog("close");
		$dlg_ext_condition_wsdl_op.dialog("open");
	}
}


// 【插件描述】对话框初始化
function dlg_ext_des_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[0].text = "下一步";
	buts[0].click = dlg_ext_des_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.close = dlg_ext_des_close;
	cfg.height = 400;
	cfg.modal = true;
	cfg.open = dlg_ext_des_open;
	cfg.resizable = false;
	cfg.width = 540;
	$dlg_ext_des = $("#extension-description-dialog");
	$dlg_ext_des.dialog(cfg);
}
// 【插件描述】对话框打开后
function dlg_ext_des_open() {
	$dlg_ext_des.find(".name input").val($ext_name);
	$dlg_ext_des.find(".domain input").val($ext_domain);
	$dlg_ext_des.find(".introduction textarea").val($ext_intro);
}
// 【插件描述】对话框关闭后
function dlg_ext_des_close() {
	$ext_intro = $dlg_ext_des.find(".introduction textarea").val();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
// 【插件描述】对话框的下一个对话框
function dlg_ext_des_next() {
	$dlg_ext_des.dialog("close");
	$dlg_ext_query_ptn.dialog("open");
}
// 【查询方式】对话框初始化
function dlg_ext_query_ptn_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_query_ptn_prev;
	buts[1].click = dlg_ext_query_ptn_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.height = 320;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.width = 480;
	cfg.close = dlg_ext_query_ptn_close;
	$dlg_ext_query_ptn = $("#extension-query-pattern-dialog");
	$dlg_ext_query_ptn.dialog(cfg);
	$dlg_ext_query_ptn.find("input").click(dlg_ext_query_ptn_rd_clk);
}

// 【查询方式】对话框中点击第1、2、3种查询方式时
function dlg_ext_query_ptn_rd_clk() {
	$ext_query_ptn=$(this).parent().text().trim();
}
function dlg_ext_query_ptn_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
// 【查询方式】对话框的上一个对话框
function dlg_ext_query_ptn_prev() {
	$dlg_ext_query_ptn.dialog("close");
	$dlg_ext_des.dialog("open");
}
// 【查询方式】对话框的下一个对话框
function dlg_ext_query_ptn_next() {
	if($ext_query_ptn != null){
		$dlg_ext_query_ptn.dialog("close");
		if($ext_query_ptn=="Variable-Oriented Query"){
			$ext_point_type="1001";
			$dlg_ext_query_var.dialog("open");
		}else if($ext_query_ptn=="Condition-Oriented Query"){
			$ext_point_type="1002";
			$dlg_ext_condition_query.dialog("open");
		}else if($ext_query_ptn=="Activity-Oriented Query"){
			$ext_point_type="1003";
		}
	}else{
		alert("请选择查询方式...");
	}
}
// 【变量查询】对话框初始化
function dlg_ext_query_var_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_query_var_prev;
	buts[1].click = dlg_ext_query_var_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_query_var_open;
	cfg.close = dlg_ext_query_var_close;
	$dlg_ext_query_var = $("#extension-query-variable-dialog");
	$dlg_ext_query_var.dialog(cfg);
}
//【条件查询】对话框初始化
function dlg_ext_condition_query_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_query_prev;
	buts[1].click = dlg_ext_condition_query_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_query_close;
	$dlg_ext_condition_query = $("#extension-query-condition-dialog");
	$dlg_ext_condition_query.dialog(cfg);
}
// 【变量查询】对话框打开后
function dlg_ext_query_var_open() {
	dlg_ext_query_var_open_1();
	dlg_ext_query_var_open_2();
	dlg_ext_query_var_open_3();
}
// 【变量查询】对话框打开后（列出所有输入的本体标注）
function dlg_ext_query_var_open_1() {
	var $ul = $dlg_ext_query_var.find(".input ul");
	var inputs = OWLModel.getAllInput();
	var types_i = [];
	for ( var i = 0; i < inputs.length; i += 1) {
		var flag = false;
		for ( var j = 0; j < types_i.length; j += 1) {
			if (types_i[j] == inputs[i].getType()) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			types_i.push(inputs[i].getType());
		}
	}
	for ( var i = 0; i < types_i.length; i += 1) {
		var type = types_i[i];
		var $li = $("<li/>");
		var $check = $("<input type='checkbox'/>").appendTo($li);
		var $text = $("<span/>").text(type).appendTo($li);
		$ul.append($li);
	}
}
// 【变量查询】对话框打开后（列出所有输出的本体标注）
function dlg_ext_query_var_open_2() {
	var $ul = $dlg_ext_query_var.find(".output ul");
	var outputs = OWLModel.getAllOutput();
	var types_o = [];
	for ( var i = 0; i < outputs.length; i += 1) {
		var flag = false;
		for ( var j = 0; j < types_o.length; j += 1) {
			if (types_o[j] == outputs[i].getType()) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			types_o.push(outputs[i].getType());
		}
	}
	for ( var i = 0; i < types_o.length; i += 1) {
		var type = types_o[i];
		var $li = $("<li/>");
		var $check = $("<input type='checkbox'/>").appendTo($li);
		var $text = $("<span/>").text(type).appendTo($li);
		$ul.append($li);
	}
}
// 【变量查询】对话框打开后（选中已选择的本体标注）
function dlg_ext_query_var_open_3() {
	var $chk_i = $dlg_ext_query_var.find(".input  input");
	var $chk_o = $dlg_ext_query_var.find(".output input");
	$chk_i.change(function(e) {
		if ($(this).attr("checked")) {
			ext_query_var_input_add($(this).parent().text());
		} else {
			ext_query_var_input_rmv($(this).parent().text());
		}
	}).each(function() {
		var txt = $(this).parent().text();
		for ( var i = 0; i < $ext_query_var_input.length; i += 1) {
			if (txt == $ext_query_var_input[i]) {
				$(this).attr("checked", "checked");
				break;
			}
		}
	});
	$chk_o.click(function() {
		if ($(this).attr("checked")) {
			ext_query_var_output_add($(this).parent().text());
		} else {
			ext_query_var_output_rmv($(this).parent().text());
		}
	}).each(function() {
		var txt = $(this).parent().text();
		for ( var i = 0; i < $ext_query_var_output.length; i += 1) {
			if (txt == $ext_query_var_output[i]) {
				$(this).attr("checked", "checked");
				break;
			}
		}
	});
}

//关闭variable condition activity查询对话框
function dlg_ext_query_var_close() {
	$dlg_ext_query_var.find("ul").empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_query_close(){
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}

//在variable condition activity点击pre
function dlg_ext_query_var_prev() {
	$dlg_ext_query_var.dialog("close");
	$dlg_ext_query_ptn.dialog("open");
}
function dlg_ext_condition_query_prev() {
	$dlg_ext_condition_query.dialog("close");
	$dlg_ext_query_ptn.dialog("open");
}

//在variable condition activity点击next
function dlg_ext_query_var_next() {
	if ($ajax_sparqldl_stat == 1) {
		alert("请等待上次查询完成...");
	} else if ($ext_query_var_input.length == 0
			&& $ext_query_var_output.length == 0) {
		alert("没有选择查询条件...");
	} else {
		ajax_sparqldl();
		$dlg_ext_query_var.dialog("close");
		$dlg_ext_query_rst.dialog("open");
	}
}
function dlg_ext_condition_query_next() {
	$ext_precondition = $("#preconditionName").val().trim();
	$ext_postcondition = $("#postconditionName").val().trim();
	$ext_processDTOname=$("#processName").val().trim();
	if ($ajax_condition_stat == 1) {
		alert("请等待上次查询完成...");
	} else if ($ext_precondition == null && $ext_postcondition == null) {
		alert("没有输入需要查询的Condition...");
	} else {
		ajax_condition();
		$dlg_ext_condition_query.dialog("close");
		$dlg_ext_condition_query_rst.dialog("open");
	}
}


//变量查询--查询input output结果
function dlg_ext_query_rst_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_query_rst_prev;
	buts[1].click = dlg_ext_query_rst_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_query_rst_open;
	cfg.close = dlg_ext_query_rst_close;
	$dlg_ext_query_rst = $("#extension-query-result-dialog");
	$dlg_ext_query_rst.dialog(cfg);
}
//条件查询--查询precondition postcondition结果
function dlg_ext_condition_query_rst_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_query_rst_prev;
	buts[1].click =dlg_ext_condition_query_rst_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_condition_query_rst_open;
	cfg.close = dlg_ext_condition_query_rst_close;
	$dlg_ext_condition_query_rst = $("#extension-condition-result-dialog");
	$dlg_ext_condition_query_rst.dialog(cfg);
}


function dlg_ext_query_rst_open() {
	if ($ajax_sparqldl_stat == 0) {
		$dlg_ext_query_rst.text("没有进行查询，禁止下一步...");
	} else if ($ajax_sparqldl_stat == 1) {
		$dlg_ext_query_rst.text("查询中，请等待查询完成...");
	} else if ($ajax_sparqldl_stat == 2) {
		var _p = $("<p>查询到的Perform的ID：</p>");
		var _ul = $("<ul/>");
		for ( var i = 0; i < $ext_query_rst.length; i += 1) {
			_ul.append($("<li/>").text($ext_query_rst[i]));
		}
		$dlg_ext_query_rst.append(_p);
		$dlg_ext_query_rst.append(_ul);
	} else if ($ajax_sparqldl_stat == 3) {
		$dlg_ext_query_rst.text("查询失败...");
	}
	$dlg_ext_query_rst_on = true;
}
function dlg_ext_condition_query_rst_open() {
	if ($ajax_condition_stat == 0) {
		$dlg_ext_condition_query_rst.text("没有进行查询，禁止下一步...");
	} else if ($ajax_condition_stat == 1) {
		$dlg_ext_condition_query_rst.text("查询中，请等待Condition相似性查询完成...");
	} else if ($ajax_condition_stat == 2) {
		var _p = $("<p>查询到的相似ConditionID：</p>");
		var _ul = $("<ul/>");
		for ( var i = 0; i < $ext_condition_query_rst.length; i += 1) {
			_ul.append($("<li/>").text($ext_condition_query_rst[i]));
		}
		$dlg_ext_condition_query_rst.append(_p);
		$dlg_ext_condition_query_rst.append(_ul);
	} else if ($ajax_condition_stat == 3) {
		$dlg_ext_condition_query_rst.text("Condition相似性查询失败...");
	}
	$dlg_ext_condition_query_rst_on = true;
}

function dlg_ext_query_rst_close() {
	$dlg_ext_query_rst_on = false;
	$dlg_ext_query_rst.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_query_rst_close() {
	$dlg_ext_condition_query_rst_on = false;
	$dlg_ext_condition_query_rst.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}

function dlg_ext_query_rst_prev() {
	if ($ajax_sparqldl_stat == 1) {
		alert("请等待SPARQL-DL查询完成...");
	} else {
		$dlg_ext_query_rst.dialog("close");
		$dlg_ext_query_var.dialog("open");
	}
}
function dlg_ext_condition_query_rst_prev() {
	if ($ajax_condition_stat == 1) {
		alert("请等待Condition相似性查询完成...");
	} else {
		$dlg_ext_condition_query_rst.dialog("close");
		$dlg_ext_condition_query.dialog("open");
	}
}

function dlg_ext_query_rst_next() {
	if ($ajax_sparqldl_stat == 0) {
		alert("没有进行查询，禁止下一步...");
	} else if ($ajax_sparqldl_stat == 1) {
		alert("查询中，请等待查询完成...");
	} else if ($ajax_sparqldl_stat == 2) {
		$dlg_ext_query_rst.dialog("close");
		$dlg_ext_ptn.dialog("open");
	}
}
function dlg_ext_condition_query_rst_next() {
	if ($ajax_condition_stat == 0) {
		alert("没有进行查询，禁止下一步...");
	} else if ($ajax_condition_stat == 1) {
		alert("查询中，请等待Condition相似性查询完成...");
	} else if ($ajax_condition_stat == 2) {
		$dlg_ext_condition_query_rst.dialog("close");
		$dlg_ext_condition_ptn.dialog("open");
	}
}


//变量查询--插入模式选择
function dlg_ext_ptn_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_ptn_prev;
	buts[1].click = dlg_ext_ptn_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_ptn_open;
	cfg.close = dlg_ext_ptn_close;
	$dlg_ext_ptn = $("#extension-pattern-dialog");
	$dlg_ext_ptn.dialog(cfg);
	$dlg_ext_ptn.find("input").click(dlg_ext_ptn_rd_clk);
}
function dlg_ext_ptn_rd_clk() {
	$ext_ptn = $(this).parent().attr("class");
}
//条件查询--插入模式选择
function dlg_ext_condition_ptn_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_ptn_prev;
	buts[1].click = dlg_ext_condition_ptn_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_condition_ptn_open;
	cfg.close = dlg_ext_condition_ptn_close;
	$dlg_ext_condition_ptn = $("#extension-condition-pattern-dialog");
	$dlg_ext_condition_ptn.dialog(cfg);
	$dlg_ext_condition_ptn.find("input").click(dlg_ext_condition_ptn_rd_clk);
}
function dlg_ext_condition_ptn_rd_clk() {
	$ext_control_ptn = $(this).parent().attr("class");
}


function dlg_ext_ptn_open() {
	$dlg_ext_ptn.find("input").removeAttr("checked");
	$dlg_ext_ptn.find("." + $ext_ptn + " input").attr("checked", "checked");
}
function dlg_ext_condition_ptn_open() {
	$dlg_ext_condition_ptn.find("input").removeAttr("checked");
	$dlg_ext_condition_ptn.find("." + $ext_control_ptn + " input").attr("checked", "checked");
}


function dlg_ext_ptn_close() {
	$dlg_ext_ptn.find("input").removeAttr("checked");
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_ptn_close() {
	$dlg_ext_condition_ptn.find("input").removeAttr("checked");
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}


function dlg_ext_ptn_prev() {
	$dlg_ext_ptn.dialog("close");
	$dlg_ext_query_rst.dialog("open");
}
function dlg_ext_condition_ptn_prev() {
	$dlg_ext_condition_ptn.dialog("close");
	$dlg_ext_condition_query_rst.dialog("open");
}


function dlg_ext_ptn_next() {
	if ($ext_ptn != null) {
		$dlg_ext_ptn.dialog("close");
		$dlg_ext_wsdl_url.dialog("open");
	} else {
		alert("请选择扩展模式...");
	}
}
function dlg_ext_condition_ptn_next() {
	if ($ext_control_ptn != null) {
		$dlg_ext_condition_ptn.dialog("close");
		$dlg_ext_condition_wsdl_url.dialog("open");
	} else {
		alert("请选择扩展模式...");
	}
}


//变量查询--选择web服务
function dlg_ext_wsdl_url_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_wsdl_url_prev;
	buts[1].click = dlg_ext_wsdl_url_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_wsdl_url_open;
	cfg.close = dlg_ext_wsdl_url_close;
	$dlg_ext_wsdl_url = $("#extension-wsdl-url-dialog");
	$dlg_ext_wsdl_url.dialog(cfg);
}
//条件查询--选择web服务
function dlg_ext_condition_wsdl_url_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_wsdl_url_prev;
	buts[1].click = dlg_ext_condition_wsdl_url_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_condition_wsdl_url_open;
	cfg.close = dlg_ext_condition_wsdl_url_close;
	$dlg_ext_condition_wsdl_url = $("#extension-condition-wsdl-url-dialog");
	$dlg_ext_condition_wsdl_url.dialog(cfg);
}
function dlg_ext_wsdl_url_open() {
	if ($wsdl_list == null) {
		$dlg_ext_wsdl_url.text("正在获取Web服务列表...");
		ajax_wsdl_list();
	} else {
		var _ul = $("<ul style='padding: 0 0 0 20px'/>");
		for ( var i = 0; i < $wsdl_list.length; i += 1) {
			var _li = $("<li/>").appendTo(_ul);
			var _rd = $("<input type='radio'/>");
			var _p = $("<span/>").text($wsdl_list[i]);
			if ($ext_wsdl_url == $wsdl_list[i]) {
				_rd.attr("checked", "checked");
			}
			_rd.click(dlg_ext_wsdl_url_rd_click);
			_li.append(_rd).append(_p);
		}
		$dlg_ext_wsdl_url.append(_ul);
	}
	$dlg_ext_wsdl_url_on = true;
}
function dlg_ext_wsdl_url_rd_click() {
	$dlg_ext_wsdl_url.find("input").removeAttr("checked");
	$(this).attr("checked", "checked");
	$ext_wsdl_url = $(this).parent().text();
}
function dlg_ext_condition_wsdl_url_open() {
	if ($wsdl_list == null) {
		$dlg_ext_condition_wsdl_url.text("正在获取Web服务列表...");
		ajax_wsdl_list();
	} else {
		var _ul = $("<ul style='padding: 0 0 0 20px'/>");
		for ( var i = 0; i < $wsdl_list.length; i += 1) {
			var _li = $("<li/>").appendTo(_ul);
			var _rd = $("<input type='radio'/>");
			var _p = $("<span/>").text($wsdl_list[i]);
			if ($ext_wsdl_url == $wsdl_list[i]) {
				_rd.attr("checked", "checked");
			}
			_rd.click(dlg_ext_condition_wsdl_url_rd_click);
			_li.append(_rd).append(_p);
		}
		$dlg_ext_condition_wsdl_url.append(_ul);
	}
	$dlg_ext_condition_wsdl_url_on = true;
}
function dlg_ext_condition_wsdl_url_rd_click() {
	$dlg_ext_condition_wsdl_url.find("input").removeAttr("checked");
	$(this).attr("checked", "checked");
	$ext_wsdl_url = $(this).parent().text();
}


function dlg_ext_wsdl_url_close() {
	$dlg_ext_wsdl_url_on = false;
	$dlg_ext_wsdl_url.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_wsdl_url_close() {
	$dlg_ext_condition_wsdl_url_on = false;
	$dlg_ext_condition_wsdl_url.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}

function dlg_ext_wsdl_url_prev() {
	$dlg_ext_wsdl_url.dialog("close");
	$dlg_ext_ptn.dialog("open");
}
function dlg_ext_condition_wsdl_url_prev() {
	$dlg_ext_condition_wsdl_url.dialog("close");
	$dlg_ext_condition_ptn.dialog("open");
}
function dlg_ext_wsdl_url_next() {
	if ($ext_wsdl_url == null) {
		alert("请选择一个Web服务...");
	} else {
		ajax_wsdl_op();
		$dlg_ext_wsdl_url.dialog("close");
		$dlg_ext_wsdl_op.dialog("open");
	}
}
function dlg_ext_condition_wsdl_url_next() {
	if ($ext_wsdl_url == null) {
		alert("请选择一个Web服务...");
	} else {
		ajax_wsdl_op();
		$dlg_ext_condition_wsdl_url.dialog("close");
		$dlg_ext_condition_wsdl_op.dialog("open");
	}
}


//变量查询--选择服务操作
function dlg_ext_wsdl_op_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_wsdl_op_prev;
	buts[1].click = dlg_ext_wsdl_op_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_wsdl_op_open;
	cfg.close = dlg_ext_wsdl_op_close;
	$dlg_ext_wsdl_op = $("#extension-wsdl-operation-dialog");
	$dlg_ext_wsdl_op.dialog(cfg);
}
//条件查询--选择服务操作
function dlg_ext_condition_wsdl_op_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_wsdl_op_prev;
	buts[1].click = dlg_ext_condition_wsdl_op_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_condition_wsdl_op_open;
	cfg.close = dlg_ext_condition_wsdl_op_close;
	$dlg_ext_condition_wsdl_op = $("#extension-condition-wsdl-operation-dialog");
	$dlg_ext_condition_wsdl_op.dialog(cfg);
}

function dlg_ext_wsdl_op_open() {
	if ($ajax_wsdl_op_stat == 0) {
		$dlg_ext_wsdl_op.text("遇到未知错误...");
	} else if ($ajax_wsdl_op_stat == 1) {
		$dlg_ext_wsdl_op.text("正在获取该服务的详细信息...");
	} else if ($ajax_wsdl_op_stat == 2) {
		var _ul = $("<ul style='padding: 0 0 0 20px'/>");
		for ( var i = 0; i < $ajax_wsdl_op_data.length; i += 1) {
			var _op = $ajax_wsdl_op_data[i];
			var _li = $("<li/>").appendTo(_ul);
			var _rd = $("<input type='radio'/>").appendTo(_li);
			var _span = $("<span/>").text(_op.name).appendTo(_li);
			var _input = $("<div class='input'></div>").appendTo(_li);
			var _output = $("<div class='output'></div>").appendTo(_li);
			_input.append($("<h3>输入：</h3>"));
			_output.append($("<h3>输出：</h3>"));
			_input = $("<table border='2'/>").appendTo(_input);
			_output = $("<table border='2'/>").appendTo(_output);
			for ( var j = 0; j < _op.input.length; j += 1) {
				var _tr = $("<tr/>").appendTo(_input);
				$("<td/>").text(_op.input[j].name).appendTo(_tr);
				$("<td/>").text(_op.input[j].type).appendTo(_tr);
			}
			for ( var j = 0; j < _op.output.length; j += 1) {
				var _tr = $("<tr/>").appendTo(_output);
				$("<td/>").text(_op.output[j].name).appendTo(_tr);
				$("<td/>").text(_op.output[j].type).appendTo(_tr);
			}
			if (_op.name == $ext_wsdl_op) {
				_rd.attr("checked", "checked");
			}
			_rd.click(dlg_ext_wsdl_op_rd_clk);
		}
		$dlg_ext_wsdl_op.append(_ul);
	} else if ($ajax_wsdl_op_stat == 3) {
		$dlg_ext_wsdl_op.text("无法获取该服务的详细信息，请返回重新选择服务...");
	}
	$dlg_ext_wsdl_op_on = true;
}

function dlg_ext_wsdl_op_rd_clk() {
	$dlg_ext_wsdl_op.find("input").removeAttr("checked");
	$(this).attr("checked", "checked");
	$ext_wsdl_op = $(this).parent().children("span").text();
	$ext_input = new Array();
	$ext_output = new Array();
	$(this).parent().find(".input tr").each(function() {
		var _text = $(this).find("td:eq(0)").text();
		$ext_input.push(_text);
		$ext_input.length += 1;
	});
	$(this).parent().find(".output tr").each(function() {
		var _text = $(this).find("td:eq(0)").text();
		$ext_output.push(_text);
		$ext_output.length += 1;
	});
}
function dlg_ext_condition_wsdl_op_open() {
	if ($ajax_wsdl_op_stat == 0) {
		$dlg_ext_condition_wsdl_op.text("遇到未知错误...");
	} else if ($ajax_wsdl_op_stat == 1) {
		$dlg_ext_condition_wsdl_op.text("正在获取该服务的详细信息...");
	} else if ($ajax_wsdl_op_stat == 2) {
		var _ul = $("<ul style='padding: 0 0 0 20px'/>");
		for ( var i = 0; i < $ajax_wsdl_op_data.length; i += 1) {
			var _op = $ajax_wsdl_op_data[i];
			var _li = $("<li/>").appendTo(_ul);
			var _rd = $("<input type='radio'/>").appendTo(_li);
			var _span = $("<span/>").text(_op.name).appendTo(_li);
			var _input = $("<div class='input'></div>").appendTo(_li);
			var _output = $("<div class='output'></div>").appendTo(_li);
			_input.append($("<h3>输入：</h3>"));
			_output.append($("<h3>输出：</h3>"));
			_input = $("<table border='2'/>").appendTo(_input);
			_output = $("<table border='2'/>").appendTo(_output);
			for ( var j = 0; j < _op.input.length; j += 1) {
				var _tr = $("<tr/>").appendTo(_input);
				$("<td/>").text(_op.input[j].name).appendTo(_tr);
				$("<td/>").text(_op.input[j].type).appendTo(_tr);
			}
			for ( var j = 0; j < _op.output.length; j += 1) {
				var _tr = $("<tr/>").appendTo(_output);
				$("<td/>").text(_op.output[j].name).appendTo(_tr);
				$("<td/>").text(_op.output[j].type).appendTo(_tr);
			}
			if (_op.name == $ext_wsdl_op) {
				_rd.attr("checked", "checked");
			}
			_rd.click(dlg_ext_condition_wsdl_op_rd_clk);
		}
		$dlg_ext_condition_wsdl_op.append(_ul);
	} else if ($ajax_wsdl_op_stat == 3) {
		$dlg_ext_condition_wsdl_op.text("无法获取该服务的详细信息，请返回重新选择服务...");
	}
	$dlg_ext_condition_wsdl_op_on = true;
}

function dlg_ext_condition_wsdl_op_rd_clk() {
	$dlg_ext_condition_wsdl_op.find("input").removeAttr("checked");
	$(this).attr("checked", "checked");
	$ext_wsdl_op = $(this).parent().children("span").text();
	$ext_input = new Array();
	$ext_output = new Array();
	$(this).parent().find(".input tr").each(function() {
		var _text = $(this).find("td:eq(0)").text();
		$ext_input.push(_text);
		$ext_input.length += 1;
	});
	$(this).parent().find(".output tr").each(function() {
		var _text = $(this).find("td:eq(0)").text();
		$ext_output.push(_text);
		$ext_output.length += 1;
	});
}
function dlg_ext_wsdl_op_close() {
	$dlg_ext_wsdl_op_on = false;
	$dlg_ext_wsdl_op.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_wsdl_op_close() {
	$dlg_ext_condition_wsdl_op_on = false;
	$dlg_ext_condition_wsdl_op.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_wsdl_op_prev() {
	$dlg_ext_wsdl_op.dialog("close");
	$dlg_ext_wsdl_url.dialog("open");
}
function dlg_ext_condition_wsdl_op_prev() {
	$dlg_ext_condition_wsdl_op.dialog("close");
	$dlg_ext_condition_wsdl_url.dialog("open");
}
function dlg_ext_wsdl_op_next() {
	if ($ext_wsdl_op != null) {
		$dlg_ext_wsdl_op.dialog("close");
		if($ext_query_ptn !="Variable-Oriented Query")
		{
			$dlg_ext_ovw.dialog("open");
		}
		else{
		
		if ($ext_ptn == "prefix") {
			$dlg_ext_bind_prfx.dialog("open");
		} else if ($ext_ptn == "preposition") {
			$dlg_ext_bind_prpo.dialog("open");
		} else if ($ext_ptn == "postfix") {
			$dlg_ext_bind_pofx.dialog("open");
		} else if ($ext_ptn == "postposition") {
			$dlg_ext_bind_popo.dialog("open");
		}}
	} else {
		alert("请选择一个操作项...");
	}
}
function dlg_ext_condition_wsdl_op_next() {
	if ($ext_wsdl_op != null) {
		$dlg_ext_condition_wsdl_op.dialog("close");
		if($ext_query_ptn !="Condition-Oriented Query")
		{
			$dlg_ext_condition_ovw.dialog("open");
		}
		else{
		
		if ($ext_control_ptn == "1001") {
			$dlg_ext_condition_bind_before.dialog("open");
		} else if ($ext_control_ptn == "1002") {
			$dlg_ext_condition_bind_after.dialog("open");
		} else if ($ext_control_ptn == "1003") {
			$dlg_ext_condition_bind_parallel.dialog("open");
		} else if ($ext_control_ptn == "1004") {
			$dlg_ext_condition_bind_replace.dialog("open");
		}}
	} else {
		alert("请选择一个操作项...");
	}
}

//变量查询--prefix数据绑定
function dlg_ext_bind_prfx_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_bind_prfx_prev;
	buts[1].click = dlg_ext_bind_prfx_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_bind_prfx_open;
	cfg.close = dlg_ext_bind_prfx_close;
	$dlg_ext_bind_prfx = $("#extension-binding-prefix-dialog");
	$dlg_ext_bind_prfx.dialog(cfg);
}
//条件查询--before数据绑定
function dlg_ext_condition_bind_before_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_bind_before_prev;
	buts[1].click = dlg_ext_condition_bind_before_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 480;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_bind_before_close;
	$dlg_ext_condition_bind_before = $("#extension-condition-binding-before-dialog");
	$dlg_ext_condition_bind_before.dialog(cfg);
	$dlg_ext_condition_bind_before.find("input").click(dlg_ext_query_data_ptn_before_rd_clk);
}
function dlg_ext_query_data_ptn_before_rd_clk(){
	$ext_data_ptn=$(this).parent().attr("class");
}
function dlg_ext_bind_prfx_open() {
	var _tab = $("<table border='2'/>");
	var _tr = $("<tr/>").appendTo(_tab);
	$("<th>插件的输入</th>").appendTo(_tr);
	$("<th>插件的输出</th>").appendTo(_tr);
	$("<th>扩展点的输入</th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	for ( var i = 0; i < $ext_query_var_input.length; i += 1) {
		var _tr = $("<tr/>").appendTo(_tab);
		var _edit = $("<td class='edit'>编辑</td>");
		var _rset = $("<td class='reset'>重置</td>");
		$("<td/>").appendTo(_tr);
		$("<td/>").appendTo(_tr);
		$("<td/>").text($ext_query_var_input[i]).appendTo(_tr);
		_edit.click(dlg_ext_bind_prfx_edit).appendTo(_tr);
		_rset.click(dlg_ext_bind_prfx_rset).appendTo(_tr);
	}
	$dlg_ext_bind_prfx.append(_tab);
}

function dlg_ext_bind_prfx_edit() {
	var _input = $(this).parent().children("td:eq(0)");
	var _output = $(this).parent().children("td:eq(1)");
	if ($(this).text() == "编辑") {
		var _in = _input.text();
		var _out = _output.text();
		_input = $("<select/>").appendTo(_input.empty());
		_input.append($("<option/>"));
		_output = $("<select/>").appendTo(_output.empty());
		_output.append($("<option/>"));
		for ( var i = 0; i < $ext_input.length; i += 2) {
			var _option = $("<option/>");
			var _text = $ext_input[i];
			_option.text(_text).appendTo(_input);
			if (_text == _in) {
				_option.attr("selected", "selected");
			}
		}
		for ( var i = 0; i < $ext_output.length; i += 2) {
			var _option = $("<option/>");
			var _text = $ext_output[i];
			_option.text(_text).appendTo(_output);
			if (_text == _out) {
				_option.attr("selected", "selected");
			}
		}
		$(this).text("完成");
	} else if ($(this).text() == "完成") {
		var _in = _input.find(":selected").text();
		var _out = _output.find(":selected").text();
		var _text = $(this).parent().children("td:eq(2)").text();
		_input.empty().text(_in);
		_output.empty().text(_out);
		$(this).text("编辑");
		for ( var i = 0; i < $ext_input.length; i += 2) {
			if ($ext_input[i] == _in) {
				$ext_input[i + 1] = _text;
			}
		}
		for ( var i = 0; i < $ext_output.length; i += 2) {
			if ($ext_output[i] == _out) {
				$ext_output[i + 1] = _text;
			}
		}
	}
}

function dlg_ext_bind_prfx_rset() {
	var _input = $(this).parent().children("td:eq(0)");
	var _output = $(this).parent().children("td:eq(1)");
	var _edit = $(this).parent().children("td:eq(3)");
	if (_edit.text() == "完成") {
		_edit.click();
	}
	_input.text("");
	_output.text("");
}


function dlg_ext_bind_prfx_close() {
	$dlg_ext_bind_prfx.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_bind_before_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_bind_prfx_prev() {
	$dlg_ext_bind_prfx.dialog("close");
	$dlg_ext_wsdl_op.dialog("open");
}
function dlg_ext_condition_bind_before_prev() {
	$dlg_ext_condition_bind_before.dialog("close");
	$dlg_ext_condition_wsdl_op.dialog("open");
}
function dlg_ext_bind_prfx_next() {
	$dlg_ext_bind_prfx.dialog("close");
	$dlg_ext_ovw.dialog("open");
}
function dlg_ext_condition_bind_before_next() {
	if($ext_data_ptn != null){
		$dlg_ext_condition_bind_before.dialog("close");
		$dlg_ext_condition_ovw.dialog("open");
	}else{
		alert("请选择数据绑定方式...");
	}
}

//变量查询--preposition数据绑定
function dlg_ext_bind_prpo_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_bind_prpo_prev;
	buts[1].click = dlg_ext_bind_prpo_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_bind_prpo_close;
	$dlg_ext_bind_prpo = $("#extension-binding-preposition-dialog");
	$dlg_ext_bind_prpo.dialog(cfg);
}
//条件查询--after数据绑定
function dlg_ext_condition_bind_after_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_bind_after_prev;
	buts[1].click = dlg_ext_condition_bind_after_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 480;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_bind_after_close;
	$dlg_ext_condition_bind_after = $("#extension-condition-binding-after-dialog");
	$dlg_ext_condition_bind_after.dialog(cfg);
	$dlg_ext_condition_bind_after.find("input").click(dlg_ext_query_data_ptn_after_rd_clk);
}
function dlg_ext_query_data_ptn_after_rd_clk(){
	$ext_data_ptn=$(this).parent().attr("class");
}
function dlg_ext_bind_prpo_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_bind_after_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_bind_prpo_prev() {
	$dlg_ext_bind_prpo.dialog("close");
	$dlg_ext_wsdl_op.dialog("open");
}
function dlg_ext_condition_bind_after_prev() {
	$dlg_ext_condition_bind_after.dialog("close");
	$dlg_ext_condition_wsdl_op.dialog("open");
}
function dlg_ext_bind_prpo_next() {
	$dlg_ext_bind_prpo.dialog("close");
	$dlg_ext_ovw.dialog("open");
}
function dlg_ext_condition_bind_after_next() {
	if($ext_data_ptn != null){
		$dlg_ext_condition_bind_after.dialog("close");
		$dlg_ext_condition_ovw.dialog("open");
	}else{
		alert("请选择数据绑定方式...");
	}
}

//变量查询--postfix数据绑定
function dlg_ext_bind_pofx_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_bind_pofx_prev;
	buts[1].click = dlg_ext_bind_pofx_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_bind_pofx_open;
	cfg.close = dlg_ext_bind_pofx_close;
	$dlg_ext_bind_pofx = $("#extension-binding-postfix-dialog");
	$dlg_ext_bind_pofx.dialog(cfg);
}
//条件查询--parallel数据绑定
function dlg_ext_condition_bind_parallel_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_bind_parallel_prev;
	buts[1].click = dlg_ext_condition_bind_parallel_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 480;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_bind_parallel_close;
	$dlg_ext_condition_bind_parallel = $("#extension-condition-binding-parallel-dialog");
	$dlg_ext_condition_bind_parallel.dialog(cfg);
	$dlg_ext_condition_bind_parallel.find("input").click(dlg_ext_query_data_ptn_parallel_rd_clk);
}
function dlg_ext_query_data_ptn_parallel_rd_clk(){
	$ext_data_ptn=$(this).parent().attr("class");
}
function dlg_ext_bind_pofx_open() {
	var _tab = $("<table border='2'/>");
	var _tr = $("<tr/>").appendTo(_tab);
	$("<th>插件的输入</th>").appendTo(_tr);
	$("<th>插件的输出</th>").appendTo(_tr);
	$("<th>扩展点的输出</th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	for ( var i = 0; i < $ext_query_var_output.length; i += 1) {
		var _tr = $("<tr/>").appendTo(_tab);
		var _edit = $("<td class='edit'>编辑</td>");
		var _rset = $("<td class='reset'>重置</td>");
		$("<td/>").appendTo(_tr);
		$("<td/>").appendTo(_tr);
		$("<td/>").text($ext_query_var_output[i]).appendTo(_tr);
		_edit.click(dlg_ext_bind_pofx_edit).appendTo(_tr);
		_rset.click(dlg_ext_bind_pofx_rset).appendTo(_tr);
	}
	$dlg_ext_bind_pofx.append(_tab);
}

function dlg_ext_bind_pofx_edit() {
	var _input = $(this).parent().children("td:eq(0)");
	var _output = $(this).parent().children("td:eq(1)");
	if ($(this).text() == "编辑") {
		var _in = _input.text();
		var _out = _output.text();
		_input = $("<select/>").appendTo(_input.empty());
		_input.append($("<option/>"));
		_output = $("<select/>").appendTo(_output.empty());
		_output.append($("<option/>"));
		for ( var i = 0; i < $ext_input.length; i += 2) {
			var _option = $("<option/>");
			var _text = $ext_input[i];
			_option.text(_text).appendTo(_input);
			if (_text == _in) {
				_option.attr("selected", "selected");
			}
		}
		for ( var i = 0; i < $ext_output.length; i += 2) {
			var _option = $("<option/>");
			var _text = $ext_output[i];
			_option.text(_text).appendTo(_output);
			if (_text == _out) {
				_option.attr("selected", "selected");
			}
		}
		$(this).text("完成");
	} else if ($(this).text() == "完成") {
		var _in = _input.find(":selected").text();
		var _out = _output.find(":selected").text();
		var _text = $(this).parent().children("td:eq(2)").text();
		_input.empty().text(_in);
		_output.empty().text(_out);
		$(this).text("编辑");
		for ( var i = 0; i < $ext_input.length; i += 2) {
			if ($ext_input[i] == _in) {
				$ext_input[i + 1] = _text;
			}
		}
		for ( var i = 0; i < $ext_output.length; i += 2) {
			if ($ext_output[i] == _out) {
				$ext_output[i + 1] = _text;
			}
		}
	}
}

function dlg_ext_bind_pofx_rset() {
	var _input = $(this).parent().children("td:eq(0)");
	var _output = $(this).parent().children("td:eq(1)");
	var _edit = $(this).parent().children("td:eq(3)");
	if (_edit.text() == "完成") {
		_edit.click();
	}
	_input.text("");
	_output.text("");
}

function dlg_ext_bind_pofx_close() {
	$dlg_ext_bind_pofx.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_bind_parallel_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_bind_pofx_prev() {
	$dlg_ext_bind_pofx.dialog("close");
	$dlg_ext_wsdl_op.dialog("open");
}
function dlg_ext_condition_bind_parallel_prev() {
	$dlg_ext_condition_bind_parallel.dialog("close");
	$dlg_ext_condition_wsdl_op.dialog("open");
}
function dlg_ext_bind_pofx_next() {
	$dlg_ext_bind_pofx.dialog("close");
	$dlg_ext_ovw.dialog("open");
}
function dlg_ext_condition_bind_parallel_next() {
	if($ext_data_ptn != null){
		$dlg_ext_condition_bind_parallel.dialog("close");
		$dlg_ext_condition_ovw.dialog("open");
	}else{
		alert("请选择数据绑定方式...");
	}
}
//变量查询--postposition数据绑定
function dlg_ext_bind_popo_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_bind_popo_prev;
	buts[1].click = dlg_ext_bind_popo_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.open = dlg_ext_bind_popo_open;
	cfg.close = dlg_ext_bind_popo_close;
	$dlg_ext_bind_popo = $("#extension-binding-postposition-dialog");
	$dlg_ext_bind_popo.dialog(cfg);
}
//条件查询--replace数据绑定
function dlg_ext_condition_bind_replace_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "下一步";
	buts[0].click = dlg_ext_condition_bind_replace_prev;
	buts[1].click = dlg_ext_condition_bind_replace_next;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 480;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_bind_replace_close;
	$dlg_ext_condition_bind_replace = $("#extension-condition-binding-replace-dialog");
	$dlg_ext_condition_bind_replace.dialog(cfg);
	$dlg_ext_condition_bind_replace.find("input").click(dlg_ext_query_data_ptn_replace_rd_clk);
}
function dlg_ext_query_data_ptn_replace_rd_clk(){
	$ext_data_ptn=$(this).parent().attr("class");
}
function dlg_ext_bind_popo_open() {
	var _tab = $("<table border='2'/>");
	var _tr = $("<tr/>").appendTo(_tab);
	$("<th>插件的输入</th>").appendTo(_tr);
	$("<th>扩展点的输出</th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	$("<th></th>").appendTo(_tr);
	for ( var i = 0; i < $ext_query_var_output.length; i += 1) {
		var _tr = $("<tr/>").appendTo(_tab);
		var _edit = $("<td class='edit'>编辑</td>");
		var _rset = $("<td class='reset'>重置</td>");
		$("<td/>").appendTo(_tr);
		$("<td/>").text($ext_query_var_output[i]).appendTo(_tr);
		_edit.click(dlg_ext_bind_popo_edit).appendTo(_tr);
		_rset.click(dlg_ext_bind_popo_rset).appendTo(_tr);
	}
	$dlg_ext_bind_popo.append(_tab);
}

function dlg_ext_bind_popo_edit() {
	var _output = $(this).parent().children("td:eq(0)");
	if ($(this).text() == "编辑") {
		var _out = _output.text();
		_output = $("<select/>").appendTo(_output.empty());
		_output.append($("<option/>"));
		for ( var i = 0; i < $ext_output.length; i += 2) {
			var _option = $("<option/>");
			var _text = $ext_output[i];
			_option.text(_text).appendTo(_output);
			if (_text == _out) {
				_option.attr("selected", "selected");
			}
		}
		$(this).text("完成");
	} else if ($(this).text() == "完成") {
		var _out = _output.find(":selected").text();
		var _text = $(this).parent().children("td:eq(1)").text();
		_output.empty().text(_out);
		$(this).text("编辑");
		for ( var i = 0; i < $ext_output.length; i += 2) {
			if ($ext_output[i] == _out) {
				$ext_output[i + 1] = _text;
			}
		}
	}
}

function dlg_ext_bind_popo_rset() {
	var _output = $(this).parent().children("td:eq(0)");
	var _edit = $(this).parent().children("td:eq(2)");
	if (_edit.text() == "完成") {
		_edit.click();
	}
	_output.text("");
}

function dlg_ext_bind_popo_close() {
	$dlg_ext_bind_popo.empty();
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_bind_replace_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_bind_popo_prev() {
	$dlg_ext_bind_popo.dialog("close");
	$dlg_ext_wsdl_op.dialog("open");
}
function dlg_ext_condition_bind_replace_prev() {
	$dlg_ext_condition_bind_replace.dialog("close");
	$dlg_ext_condition_wsdl_op.dialog("open");
}
function dlg_ext_bind_popo_next() {
	$dlg_ext_bind_popo.dialog("close");
	$dlg_ext_ovw.dialog("open");
}
function dlg_ext_condition_bind_replace_next() {
	if($ext_data_ptn != null){
		$dlg_ext_condition_bind_replace.dialog("close");
		$dlg_ext_condition_ovw.dialog("open");
	}else{
		alert("请选择数据绑定方式...");
	}
}

//变量查询--插件总览
function dlg_ext_ovw_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "完成";
	buts[0].click = dlg_ext_ovw_prev;
	buts[1].click = dlg_ext_ovw_ok;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_ovw_close;
	$dlg_ext_ovw = $("#extension-overview-dialog");
	$dlg_ext_ovw.dialog(cfg);
}
//条件查询--插件总览
function dlg_ext_condition_ovw_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[1] = {};
	buts[0].text = "上一步";
	buts[1].text = "完成";
	buts[0].click = dlg_ext_condition_ovw_prev;
	buts[1].click = dlg_ext_condition_ovw_ok;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	cfg.close = dlg_ext_condition_ovw_close;
	$dlg_ext_condition_ovw = $("#extension-condition-overview-dialog");
	$dlg_ext_condition_ovw.dialog(cfg);
}
function dlg_ext_ovw_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_condition_ovw_close() {
	$("#editor>.toolbar>.extension")[0].$ = $(this);
}
function dlg_ext_ovw_prev() {
	$dlg_ext_ovw.dialog("close");
	if ($ext_ptn == "prefix") {
		$dlg_ext_bind_prfx.dialog("open");
	} else if ($ext_ptn == "preposition") {
		$dlg_ext_bind_prpo.dialog("open");
	} else if ($ext_ptn == "postfix") {
		$dlg_ext_bind_pofx.dialog("open");
	} else if ($ext_ptn == "postposition") {
		$dlg_ext_bind_popo.dialog("open");
	}
}
function dlg_ext_condition_ovw_prev() {
	$dlg_ext_condition_ovw.dialog("close");
	if ($ext_control_ptn == "1001") {
		$dlg_ext_condition_bind_before.dialog("open");
	} else if ($ext_control_ptn == "1002") {
		$dlg_ext_condition_bind_after.dialog("open");
	} else if ($ext_control_ptn == "1003") {
		$dlg_ext_condition_bind_parallel.dialog("open");
	} else if ($ext_control_ptn == "1004") {
		$dlg_ext_condition_bind_replace.dialog("open");
	}
}
function dlg_ext_ovw_ok() {
	$dlg_ext_ovw.dialog("close");
	ajax_save();
	$("#editor>.toolbar>.extension")[0].$ = $("#extension-finish-dialog");
}
function dlg_ext_condition_ovw_ok() {
	$dlg_ext_condition_ovw.dialog("close");
	ajax_condition_save();
	$("#editor>.toolbar>.extension")[0].$ = $("#extension-condition-finish-dialog");
}

//variable保存
function ajax_save() {
	var cfg = {};
	cfg.cache = true;
	cfg.success = ajax_save_scc;
	cfg.error = ajax_save_err;
	cfg.type = "POST";
	cfg.data = ajax_save_data();
	cfg.dataType = "text";
	cfg.url = "ext-save.jsp";
	$.ajax(cfg);
	$(".statusbar").text("正在保存插件...");
}
function ajax_save_data() {
	var obj = {};
	obj.json = JSON.stringify(ext_json_all());
	obj.Provider = $req_prv;
	obj.Process = $req_prc;
	obj.Name = $ext_name;
	return obj;
}
function ajax_save_scc(data) {
	$(".statusbar").text("保存成功...");
	OWLModel.fromJson(JSON.parse(data));
	Redraw();
}
function ajax_save_err() {
	$(".statusbar").text("保存失败...");
}
//转化为JSON以便服务器处理
function ext_json_all() {
	var obj = {};
	obj.Name = $ext_name;
	obj.Domain = $ext_domain;
	obj.Introduction = $ext_intro;
	obj.Extensional_Point = {};
	obj.Extensional_Fragment = {};
	obj.Extensional_Point.SPARQL_DL = [ $ext_sparqldl ];
	obj.Extensional_Point.Variable_Query = {};
	obj.Extensional_Point.Variable_Query.Input = $ext_query_var_input;
	obj.Extensional_Point.Variable_Query.Output = $ext_query_var_output;
	obj.Extensional_Fragment.Extension_pattern = $ext_ptn;
	obj.Extensional_Fragment.Input = ext_json_input();
	obj.Extensional_Fragment.Output = ext_json_output();
	obj.Extensional_Fragment.Wsdl_Grounding = {};
	obj.Extensional_Fragment.Wsdl_Grounding.Wsdl_Location = $ext_wsdl_url;
	obj.Extensional_Fragment.Wsdl_Grounding.Operation = $ext_wsdl_op;
	obj.Extensional_Fragment.Wsdl_Grounding.PortType = $ext_wsdl_port_type;
	obj.Extensional_Fragment.Wsdl_Grounding.InputMessageName = $ext_wsdl_in_msg_name;
	obj.Extensional_Fragment.Wsdl_Grounding.OutputMessageName = $ext_wsdl_out_msg_name;
	return obj;
}
//condition保存
function ajax_condition_save() {
	var cfg = {};
	cfg.cache = true;
	cfg.success = ajax_save_scc;
	cfg.error = ajax_save_err;
	cfg.type = "POST";
	cfg.data = ajax_condition_save_data();
	cfg.dataType = "text";
	cfg.url = "ext-condition-save.jsp";
	$.ajax(cfg);
	$(".statusbar").text("正在保存插件...");
}
function ajax_condition_save_data() {
	var obj = {};
	obj.json = JSON.stringify(ext_condition_json_all());
	obj.Provider = $req_prv;
	obj.Process = $req_prc;
	obj.Name = $ext_name;
	obj.ProcessDTOname=$ext_processDTOname;
	return obj;
}
function ext_condition_json_all() {
	var obj = {};
	obj.name = $ext_name;
	obj.field = $ext_domain;
	obj.annotation = $ext_intro;
	obj.processType=1001;//owls
	obj.pluginParam="";
	obj.url="";
	obj.preCondition = $ext_preconditionDTO;
	obj.postCondition = $ext_postconditionDTO;
	obj.SearchExtensibilityPointType=$ext_point_type;
	obj.controllFlowExtensionPattern = $ext_control_ptn;
	obj.dataFlowExtensionParttern = $ext_data_ptn;
	obj.queryStr1=$ext_precondition;
	obj.queryStr2=$ext_postcondition;
	obj.url=$ext_wsdl_url;
	//obj.Extensional_Fragment.Wsdl_Grounding = {};
	//obj.Extensional_Fragment.Wsdl_Grounding.Wsdl_Location = $ext_wsdl_url;
	//obj.Extensional_Fragment.Wsdl_Grounding.Operation = $ext_wsdl_op;
	return obj;
}
// 点击【插件开发按钮时弹出插件开发对话框】
$(function() {
	$("#editor>.toolbar>.extension")[0].$ = $("#extension-description-dialog");
	$("#editor>.toolbar>.extension").click(function() {
		this.$.dialog("open");
	});
	$ext_name = $req_name;
	if($req_dom=="traffic"){
		$ext_domain=1001;
	}else if($req_dom=="logistics"){
		$ext_domain=1002;
	}else if($req_dom=="hotel"){
		$ext_domain=1003;
	}else if($req_dom=="dailylife"){
		$ext_domain=1004;
	}
	dlg_ext_des_init();
	//第一步
	dlg_ext_query_ptn_init();
	//选择变量查询
	dlg_ext_query_var_init();
	dlg_ext_query_rst_init();
	dlg_ext_ptn_init();
	dlg_ext_wsdl_url_init();
	dlg_ext_wsdl_op_init();
	dlg_ext_bind_prfx_init();
	dlg_ext_bind_prpo_init();
	dlg_ext_bind_pofx_init();
	dlg_ext_bind_popo_init();
	dlg_ext_ovw_init();
	//选择条件查询
	dlg_ext_condition_query_init();
	dlg_ext_condition_query_rst_init();
	dlg_ext_condition_ptn_init();
	dlg_ext_condition_wsdl_url_init();
	dlg_ext_condition_wsdl_op_init();
	dlg_ext_condition_bind_before_init();
	dlg_ext_condition_bind_after_init();
	dlg_ext_condition_bind_parallel_init();
	dlg_ext_condition_bind_replace_init();
	dlg_ext_condition_ovw_init();
	//选择事件查询
});
