


function plugin_binding_preposition(){
	
}

function plugin_binding_prefix(){
	
}

function plugin_binding_postposition(){
	
}

function plugin_binding_postfix(){
	
}

function plugin_binding_substitution(){
	
}

function plugin_binding_synchronization(){
	
}



// 预先加载需要用到的图片资源
$(function() {
	$.imgpreload("img/help-hover.png");
	$.imgpreload("img/help-active.png");
	$.imgpreload("img/minimize-hover.png");
	$.imgpreload("img/minimize-active.png");
});
// 显示插件开发向导
$(function() {
	$("#extension-dialog").show();
	$("#dialog-cover").show();
});
// 提示信息双色显示
$(function() {
	var $span = $("#editor>.statusbar>.extension>span");
	var flag = false;
	setInterval(function() {
		flag = !flag;
		if (flag) {
			$span.css("color", "#9900FF");
		} else {
			$span.css("color", "#0000FF");
		}
	}, 200);
});
// 显示插件开发对话框的触发事件
$(function() {
	$("#editor>.statusbar>.extension>span").click(function() {
		$("#extension-dialog").show();
		$("#dialog-cover").show();
		$("#editor>.statusbar>.extension").hide();
	});
	$("#extension-dialog .minimize").click(function() {
		$("#extension-dialog").hide();
		$("#dialog-cover").hide();
		$("#editor>.statusbar>.extension").show();
	});
});
// 设定当窗口大小变化时，对话框一直保持居中
$(function() {
	function _resize() {
		var _left = ($("#editor").width() - $("#extension-dialog").width()) / 2;
		var _top = ($("#editor").height() - $("#extension-dialog").height()) / 2;
		$("#extension-dialog").css("left", _left + "px");
		$("#extension-dialog").css("top", _top + "px");
	}
	$(window).resize(_resize);
	$(window).resize();
});
// 模板的切换
$(function() {
	var state = "description";
	$("#extension-dialog .button.prev").click(function() {
		if(state=="binding"){
			$("#extension-dialog .content .binding").hide();
			$("#extension-dialog .content .grounding-operation").show();
			state="grounding-operation";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .grounding").addClass("current");
		}else if (state == "grounding-operation") {
			$("#extension-dialog>.body>.content>.grounding-operation").hide();
			$("#extension-dialog>.body>.content>.grounding-wsdl").show();
			state = "grounding-wsdl";
		} else if (state == "grounding-wsdl") {
			$("#extension-dialog>.body>.content>.grounding-wsdl").hide();
			$("#extension-dialog>.body>.content>.pattern").show();
			state = "pattern";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .pattern").addClass("current");
		} else if (state == "pattern") {
			$("#extension-dialog>.body>.content>.pattern").hide();
			$("#extension-dialog>.body>.content>.query-result").show();
			state = "query-result";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .query").addClass("current");
		} else if (state == "query-result") {
			$("#extension-dialog>.body>.content>.query-result").hide();
			$("#extension-dialog>.body>.content>.query-variable").show();
			state = "query-variable";
		} else if (state == "query-variable") {
			$("#extension-dialog>.body>.content>.query-variable").hide();
			$("#extension-dialog>.body>.content>.query-pattern").show();
			state = "query-pattern";
		} else if (state == "query-pattern") {
			$("#extension-dialog>.body>.content>.query-pattern").hide();
			$("#extension-dialog>.body>.content>.description").show();
			state = "description";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .description").addClass("current");
		}
	});
	$("#extension-dialog .button.next").click(function() {
		if (state == "description") {
			$("#extension-dialog>.body>.content>.description").hide();
			$("#extension-dialog>.body>.content>.query-pattern").show();
			state = "query-pattern";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .query").addClass("current");
		} else if (state == "query-pattern") {
			$("#extension-dialog>.body>.content>.query-pattern").hide();
			$("#extension-dialog>.body>.content>.query-variable").show();
			state = "query-variable";
		} else if (state == "query-variable") {
			if (execute_query_variable()) {
				$("#extension-dialog>.body>.content>.query-variable").hide();
				$("#extension-dialog>.body>.content>.query-result").show();
				state = "query-result";
			}
		} else if (state == "query-result") {
			$("#extension-dialog>.body>.content>.query-result").hide();
			$("#extension-dialog>.body>.content>.pattern").show();
			state = "pattern";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .pattern").addClass("current");
		} else if (state == "pattern") {
			$("#extension-dialog>.body>.content>.pattern").hide();
			$("#extension-dialog>.body>.content>.grounding-wsdl").show();
			state = "grounding-wsdl";
			$("#extension-dialog .navi .current").removeClass("current");
			$("#extension-dialog .navi .grounding").addClass("current");
		}else if(state=="grounding-wsdl"){
			if(execute_grounding_wsdl()){
				$("#extension-dialog>.body>.content>.grounding-wsdl").hide();
				$("#extension-dialog>.body>.content>.grounding-operation").show();
				state = "grounding-operation";
			}
		}else if(state=="grounding-operation"){
			if(execute_grounding_operation()){
				$("#extension-dialog>.body>.content>.grounding-operation").hide();
				$("#extension-dialog .content .binding").show();
				state="binding";
				$("#extension-dialog .navi .current").removeClass("current");
				$("#extension-dialog .navi .binding").addClass("current");
			}
		}
	});

});

// 选择查询方式
$(function() {
	$("#extension-dialog .query-pattern input:gt(0)").click(function() {
		$(this).removeAttr("checked");
		alert("暂不支持该查询方式"); 
	});
});
// 提取出输入输出的语义标注让用户选择
$(function() {
	var inputs = OWLModel.getAllInput();
	var outputs = OWLModel.getAllOutput();
	var $input = $("#extension-dialog .query-variable .input ul");
	var $output = $("#extension-dialog .query-variable .output ul");
	for ( var i = 0; i < inputs.length; i++) {
		var input = inputs[i];
		var type = input.getType();
		var $check = $("<input type='checkbox'/>");
		var $li = $("<li/>").append($check).append($("<span/>").text(type));
		$input.append($li);
	}
	for ( var i = 0; i < outputs.length; i++) {
		var output = outputs[i];
		var type = output.getType();
		var $check = $("<input type='checkbox'/>");
		var $li = $("<li/>").append($check).append($("<span/>").text(type));
		$output.append($li);
	}
});
// 选择扩展模式
$(function() {
	$("#extension-dialog .pattern input").click(function() {
		$("#extension-dialog .pattern input:checked").removeAttr("checked");
		$(this).attr("checked", "checked");
	});
});
// 选择Web服务
$(function() {
	$("#extension-dialog .grounding-wsdl input").click(function() {
				$("#extension-dialog .grounding-wsdl input:checked").removeAttr("checked");
				$(this).attr("checked", "checked");
			});
	$("#extension-dialog .grounding-wsdl span").click(function() {
				$("#extension-dialog .grounding-wsdl .content").load("get-wsdl-list.jsp",function (){
					$("#extension-dialog .grounding-wsdl input").click(function() {
						$("#extension-dialog .grounding-wsdl input:checked").removeAttr("checked");
						$(this).attr("checked", "checked");
					});
				});
				
	});
});

var extension={
	name:null,
	domain:null,
	description:null,
	pattern:null,
	query:{
		input:[],
		output:[]
	},
	input:[],
	output:[],
	wsdl:null,
	operation:null
};

function execute_query_variable() {
	var $input = $("#extension-dialog .query-pattern input");
	var $ol = $("#extension-dialog .query-result ol").empty();
	var performs = OWLModel.getAllPerform();
	if ($input.eq(0).attr("checked") == "checked") {
		var type_i = [], type_o = [];
		var checked_i = $("#extension-dialog .query-variable .input :checked");
		var checked_o = $("#extension-dialog .query-variable .output :checked");
		var is_empty = true;
		checked_i.each(function() {
			var txt=$(this).parent().children("span").text();
			type_i.push(txt);
			extension.query.input.push({name:null,type:txt});
		});
		checked_o.each(function() {
			var txt=$(this).parent().children("span").text();
			type_o.push(txt);
			extension.query.output.push({name:null,type:txt});
		});
		// 检查是否选择了查询条件
		if (type_o.length == 0 && type_i.length == 0) {
			alert("请先选择查询条件");
			return false;
		}
		// 查询Perform
		for ( var i = 0; i < performs.length; i++) {
			if (performs[i].matches(type_i, type_o)) {
				performs[i].setQueryResult(true);
				$ol.append                                                                                                      ($("<li/>").text(performs[i] + ""));
				is_empty = false;
			} else {
				performs[i].setQueryResult(false);
			}
		}
		// 检查查询结果是否为空
		if (is_empty) {
			alert("没有对应的Perform！");
			return false;
		}
		Redraw();
	} else if ($input.eq(1).attr("checked") == "checked") {

	} else if ($input.eq(2).attr("checked") == "checked") {

	}
	return true;
}
function execute_grounding_wsdl(){
	var $checked=$("#extension-dialog .grounding-wsdl input:checked");
	if($checked.length==0){
		alert("请先选择Web服务！");
		return false;
	}
	var $ol=$("#extension-dialog .grounding-operation ol").empty();
	var uri=$checked.parent().text();
	var service=new WSDLService(uri);
	var ops=service.getOperations();
	extension.wsdl=uri;
	for(var i=0;i<ops.length;i++){
		var view=new WSDLOperationView(ops[i]);
		var $li=$("<li/>");
		var $input=$("<input type='radio'/>");
		$input.click(function (){
			$ol.find(":checked").removeAttr("checked");
			$(this).attr("checked","checked");
		});
		$li.append($input);
		$li.append($("<span/>").text(ops[i].getName()).css({
			"font-size":"13px",
			"font-weight":"bold"
		}));
		$li.append($(view.getHtml()));
		$ol.append($li);
	}
	return true;
}
function execute_grounding_operation(){
	var $checked=$("#extension-dialog .grounding-wsdl input:checked");
	var text=$checked.parent().text();
	var service=WSDLService.createService(text);
	$checked=$("#extension-dialog .grounding-operation input:checked");
	if($checked.length!=0){
		text=$checked.parent().children("span").text();
		extension.operation=text;
		var operation=service.getOperation(text);
		var pattern=$("#extension-dialog .pattern :checked").val();
		// 前置
		if(pattern==1){
			var $p=$("<p/>");
			$p.text("前置模式的输入来自于组合流程，输出作为组合流程的输入，因此不需要进行数据绑定！");
			$("#extension-dialog .content .binding").empty().append($p);
			return true;
		// 前缀
		}else if(pattern==2){
			return init_binding_prefix();
		// 替换
		}else if(pattern==3){
			return init_binding_substitution();
		}else{
			return false;
		}
	}else{
		return false;
	}
}
$(function (){
	var $div=$("#extension-dialog .binding .graph");
	var graph=$div[0].graph=new mxGraph($div[0]);
	
});
//$(function (){
//	$("#extension-dialog .content .description").hide();
//	$("#extension-dialog .content .binding").show();
//	init_binding_prefix();
//});
/**
 * 初始化数据绑定之前缀
 */
function init_binding_prefix(){
	var $div=$("#extension-dialog .binding .graph");
	var graph=$div[0].graph;
	var parent=graph.getDefaultParent();
	var style = "movable=0;"+
				"editable=0;"+
				"resizable=0;"+
				"strokeColor=#000000;"+
				"strokeWidth=2;"+
				"fillColor=#FFFFFF;"+
				"fontColor=#000000;"+
				"connectable=1;";
	var service=WSDLService.createService(extension.wsdl);
	var operation=service.getOperation(extension.operation);
	graph.selectAll();
	graph.removeCells();
	graph.setConnectable(true);
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_STROKECOLOR] = 'black';
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_FONTCOLOR] = 'black';
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_STROKEWIDTH] = 2;
	// 插件输入
	graph.insertVertex(parent,null,"插件输入",10,2,100,22,style);
	for(var i=0;i<operation.getInputs().length;i+=1){
		graph.insertVertex(parent,null,operation.getInputs()[i].getName(),10,24+22*i,100,22,style);
	}
	// 插件输出
	graph.insertVertex(parent,null,"插件输出",210,2,100,22,style);
	for(var i=0;i<operation.getOutputs().length;i+=1){
		graph.insertVertex(parent,null,operation.getOutputs()[i].getName(),210,24+22*i,100,22,style);
	}
	// 扩展点输入
	graph.insertVertex(parent,null,"扩展点输入",410,2,400,22,style);
	for(var i=0;i<extension.query.input.length;i+=1){
		graph.insertVertex(parent,null,extension.query.input[i].type,410,24+22*i,400,22,style);
	} 
	return true;
}
/**
 * 初始化数据绑定之替换
 */
function init_binding_substitution(){
	var $div=$("#extension-dialog .binding .graph");
	var graph=$div[0].graph;
	var parent=graph.getDefaultParent();
	var style = "movable=0;"+
				"editable=0;"+
				"resizable=0;"+
				"strokeColor=#000000;"+
				"strokeWidth=2;"+
				"fillColor=#FFFFFF;"+
				"fontColor=#000000;"+
				"connectable=1;";
	var service=WSDLService.createService(extension.wsdl);
	var operation=service.getOperation(extension.operation);
	graph.selectAll();
	graph.removeCells();
	graph.setConnectable(true);
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_EDGE] = mxEdgeStyle.EntityRelation;
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_STROKECOLOR] = 'black';
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_FONTCOLOR] = 'black';
	graph.stylesheet.getDefaultEdgeStyle()[mxConstants.STYLE_STROKEWIDTH] = 2;
	// 插件输入
	graph.insertVertex(parent,null,"插件输入",10,2,100,22,style);
	for(var i=0;i<operation.getInputs().length;i+=1){
		graph.insertVertex(parent,null,operation.getInputs()[i].getName(),10,24+22*i,100,22,style);
	}
	// 扩展点输入
	graph.insertVertex(parent,null,"扩展点输入",210,2,400,22,style);
	for(var i=0;i<extension.query.input.length;i+=1){
		graph.insertVertex(parent,null,extension.query.input[i].type,210,24+22*i,400,22,style);
	}
	var height=Math.max(operation.getInputs().length,extension.query.input.length);
	// 插件输出
	graph.insertVertex(parent,null,"插件输出",10,2+(length+4)*22,100,22,style);
	for(var i=0;i<operation.getOutputs().length;i+=1){
		graph.insertVertex(parent,null,operation.getOutputs()[i].getName(),10,24+22*(length+i+4),100,22,style);
	}
	// 扩展点输出
	graph.insertVertex(parent,null,"扩展点输出",210,2+(length+4)*22,400,22,style);
	for(var i=0;i<extension.query.output.length;i+=1){
		graph.insertVertex(parent,null,extension.query.output[i].type,210,24+22*(length+i+4),400,22,style);
	}
	console.log(length);
	return true;
}



function execute_binding(){
	var pattern=$("#extension-dialog .pattern :checked").val();
	if(pattern==1){
		return execute_binding_precondition();
	}else if(pattern==2){
		return execute_binding_prefix();
	}
}

function execute_binding_precondition(){
	return true;
}

function execute_binding_prefix(){
	
}

function rollback_query_variable() {

}
