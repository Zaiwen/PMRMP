/*
*//**
 * @requires jquery
 * @requires mxGraph
 * 
 * *//*
*//**
 * 可以在任意页面实例化该类，会清除页面上所有内容
 * 插件开发与应用界面可以将此编辑器单独放在一个iframe里面
 * @class EpcEditor
 * @constructor EpcEditor
 * @param readonly {Boolean} 设置为true时编辑器只能打开C-iEPC流程，不能进行任何编辑
 * *//*
function EpcEditor(readonly){
	*//**
	 * @private {String} 整个编辑器的主题颜色
	 * *//*
	var themeColor = "#000";
	*//**
	 * @private {jQuery} 整个编辑器最外层的DOM元素
	 * *//*
	var editor = $("<div/>").css({
		"background": themeColor,
		"bottom"	: "0",
		"left"		: "0",
		"top"		: "0",
		"right"		: "0",
		"position"	: "absolute"
	}).appendTo($("body"));
	*//**
	 * @private {jQuery} 编辑器的标题栏
	 * *//*
	var titlebar = $("<div/>").css({
		"color"		: "#fff",
		"height"	: "28px",
		"font-size"	: "12px",
		"left"		: "0px",
		"line-height": "28px",
		"position"	: "absolute",
		"right"		: "0px",
		"text-align": "center",
		"top"		: "0px"
	}).text("C-iEPC Editor").appendTo(editor);
	
	this.getTitlebar = function (){
		return titlebar;
	}
	
	this.isReadonly = function (){
		return readonly;
	}
	
	this.readEpml = function (){
		
	}
	*//**
	 * 只会刷新样式不会修改DOM元素
	 * *//*
	this.refresh = function (){
		
	};
	
}*/

function EpcMenubar(){

	this.addMenu = function (){
		
	};
}




var shape_selected = null;

var graph = null;

// 绘图区域初始化
function graph_init(){	
	mxConstants.DEFAULT_HOTSPOT=1;
	graph = new mxGraph($("#edit-window .body")[0]);
	graph.centerZoom = false;
	graph.setTooltips(false);
	graph.setMultigraph(false);
	graph.setAllowLoops(false);
	graph.getStylesheet().getDefaultEdgeStyle()[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
	graph.getStylesheet().getDefaultEdgeStyle()[mxConstants.STYLE_STROKECOLOR] = '#6666FF';
	graph.setConnectable(true);
	/*graph.addListener(mxEvent.DOUBLE_CLICK,function (sender,evt)
	{
		evt.consume();
	});*/
	graph.installUndoManager();
	//InstallContextMenu(graph);
	new mxRubberband(graph);
	

	//mxEvent.disableContextMenu(document.body);

	//设置样式
	var style = graph.getStylesheet().getDefaultEdgeStyle();
	style[mxConstants.STYLE_ROUNDED] = true;
	style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
	//style[mxConstants.STYLE_STROKECOLOR]="#000";
	//style[mxConstants.STYLE_STROKEWIDTH]=2;
};


//单击选中图元，然后单击绘图区生成流程图
$(function (){
	$("#shape-event").mousedown(function (){
		shape_selected = "event";
		$("#temp-img").css("background-image","url('img/shape-event-0.png')").show();
	});
	$("#shape-function").mousedown(function (){
		shape_selected = "function";
		$("#temp-img").css("background-image","url('img/shape-function-0.png')").show();
	});
	$("#shape-role").mousedown(function (){
		shape_selected = "role";
		$("#temp-img").css("background-image","url('img/shape-role-0.png')").show();
	});
	$("#shape-object").mousedown(function (){
		shape_selected = "object";
		$("#temp-img").css("background-image","url('img/shape-object-0.png')").show();
	});
	$("#shape-and").mousedown(function (){
		shape_selected = "and";
		$("#temp-img").css("background-image","url('img/shape-and-0.png')").show();
	});
	$("#shape-or").mousedown(function (){
		shape_selected = "or";
		$("#temp-img").css("background-image","url('img/shape-or-0.png')").show();
	});
	$("#shape-xor").mousedown(function (){
		shape_selected = "xor";
		$("#temp-img").css("background-image","url('img/shape-xor-0.png')").show();
	});

	$(document).mousemove(function (e){
		if(shape_selected!=null){
			$("#temp-img").css({
				left : (e.clientX-24)+"px",
				top : (e.clientY - 16)+"px"
			});
		}
	}).mouseup(function (e){
		$("#temp-img").css({
			"display":"none",
			"left":"-48px",
			"top":"-32px"
		});
		var _x=left_collapse?64:244;
		var _y=64;
		var xxx = !!shape_selected;
		xxx = xxx && e.clientX>_x;
		xxx = xxx && e.clientX<_x+$("#edit-window").width();
		xxx = xxx && e.clientY>_y;
		xxx = xxx && e.clientY<_y+$("#edit-window").height();
		if(xxx){
			var x = graph.getPointForEvent(e).x;//e.clientX - _x + $("#edit-window")[0].scrollLeft;
			var y = graph.getPointForEvent(e).y;//e.clientY - _y + $("#edit-window")[0].scrollTop;
			x=parseInt(x/10)*10;
			y=parseInt(y/10)*10;
			switch(shape_selected){
				case "event":{
					var cell = new EpcEvent(x-40,y-20,80,40,"Event");
					graph.addCell(cell);
					break;
				}
				case "function":{
					var cell = new EpcFunction(x-40,y-20,80,40,"Function");
					graph.addCell(cell);
					break;
				}
				case "role":{
					var cell = new EpcRole(x-30,y-10,60,20,"Role");
					graph.addCell(cell);
					break;
				}
				case "object":{
					var cell = new EpcObject(x-30,y-10,60,20,"Object");
					graph.addCell(cell);
					break;
				}
				case "and":{
					var cell = new EpcAnd(x-20,y-20,40,40);
					graph.addCell(cell);
					break;
				}
				case "or":{
					var cell = new EpcOr(x-20,y-20,40,40);
					graph.addCell(cell);
					break;
				}
				case "xor":{
					var cell = new EpcXor(x-20,y-20,40,40);
					graph.addCell(cell);
					break;
				}
			}
			EpcCanvas.refresh();
		}
		shape_selected = null;
	});
});

function _save(){};
$(function (){
	$(".save").click(function (){
		$.ajax({
			async:false,
			catche:false,
			data:{
				name:$("#edit-window .head .name").text(),
				content:EpcEditor.encodeEpml(),
				lastVersion:EpcEditor.version
			},
			error:function (){
				alert("保存失败！");
			},
			success:function (data){
				data=JSON.parse(data);
				if(data.code == 0){
					alert("保存成功！\n编辑共耗时:"+(new Date().getTime()-timestamp)/1000+"秒！");;
					EpcEditor.version=data.version;
					window.timestamp = new Date().getTime();
				}else{
					alert("保存失败，错误代码："+data.code);
				}
				
			},
			type:"POST",
			url:"jsp/save.jsp",
			dataType:"text"
		});
	});
})
// 打开流程
function _open(){}
/*$(function (){
	$(".open").click(function (){
		$("#open-dialog").show();
		$("#dialog-cover").show();
		$.ajax({
			async:false,
			catche:false,
			error:function (){
				$("#open-dialog .body").text("无法获取文件列表，请联系管理员！");
			},
			success:function (data){
				var arr=JSON.parse(data);
				$("#open-dialog .body .content").html("<table><tr><th class='name'>名称</th><th class='time'>修改时间</th><th class='version'>版本号</th></tr></table>");
				for(var i=0;i<arr.length;i++){
					var _tr=$("<tr class='item'/>");
					var _td1=$("<td class='name'/>").text(arr[i].name).appendTo(_tr);
					var _td2=$("<td class='time'/>").text(arr[i].time).appendTo(_tr);
					var _td3=$("<td class='version'/>").text(arr[i].version).appendTo(_tr);
					$("#open-dialog .body table").append(_tr);
					_tr.click(function (){
						$.ajax({
						async:false,
						catche:false,
						error:function (){
							alert("文件打开失败！");
						},
						success:function (data){
							console.log("before parse")
							console.log(data)
							EpcEditor.decodeEpml(data);
							EpcCanvas.refresh();
							console.log("after parse")
							$("#open-dialog").hide();
							$("#dialog-cover").hide();
						},
						type:"GET", 
						url:"jsp/get-epml-content.jsp",
						data :{name : $(this).find(".name").text()},
						dataType:"text"
						});
					});
				}
			},
			type:"POST",
			url:"jsp/get-epml-list.jsp",
			dataType:"text"
		});
	});
	// 窗口调整时对话框自动居中
	var $dialog = $("#open-dialog");
	$(window).resize(function() {
		var _left = ($(document).width() - $dialog.width()) / 2;
		var _top = ($(document).height() - $dialog.height()) / 2;
		$dialog.css("left", _left + "px");
		$dialog.css("top", _top + "px");
	});
	// 关闭
	$("#open-dialog .head .close").click(function (){
		$("#open-dialog").hide();
		$("#dialog-cover").hide();
	});
});
*/
function parse_epml(str){
	var cells={};
	//删除所有
	graph.removeCells(graph.model.getChildren(graph.getDefaultParent()));
	//EPC Name
	$("#edit-window .head .name").text($(str).find("epc").attr("name"));
	//Event
	$(str).find("event").each(function (){
		
		var _id=$(this).attr("id");
//		var _name=$(this).find("name").text();
//		var _description=$(this).find("description").text();
//		var _x=parseInt($(this).find("position").attr("x"));
//		var _y=parseInt($(this).find("position").attr("y"));
//		var _w=parseInt($(this).find("position").attr("width"));
//		var _h=parseInt($(this).find("position").attr("height"));
//		var _b=$(this).find("font").attr("weight")=="bold";
//		var _i=$(this).find("font").attr("style")=="italic";
//		var _u=$(this).find("font").attr("decoration")=="underline";
		cells[_id]=cell;
//		cell.setName(_name?_name:"");
//		cell.setDescription(_description?_description:"");
//		cell.setX(isNaN(_x)?0:_x);
//		cell.setY(isNaN(_y)?0:_y);
//		cell.setWidth(_w>0?_w:80);
//		cell.setHeight(_h>0?_h:40);
		var x=0,y=0,width=80,height=40,name="";
		try{name=$(this).find("name").text();}catch(e){}
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		//try{cell.setFontBold($(this).find("font").attr("weight")=="bold");}catch(e){}
		//try{cell.setFontItalic($(this).find("font").attr("style")=="italic");}catch(e){}
		//try{cell.setFontUnderline($(this).find("font").attr("decoration")=="underline");}catch(e){}
		var cell=new EpcEvent(x,y,width,height,name,_id);
		graph.addCell(cell);
	});
	$(str).find("function").each(function (){
		var _id=$(this).attr("id");
		cells[$(this).attr("id")]=cell;
		var x=0,y=0,width=80,height=40,name="";
		try{name=$(this).find("name").text();}catch(e){}
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		//try{cell.setFontBold($(this).find("font").attr("weight")=="bold");}catch(e){}
		//try{cell.setFontItalic($(this).find("font").attr("style")=="italic");}catch(e){}
		//try{cell.setFontUnderline($(this).find("font").attr("decoration")=="underline");}catch(e){}
		var cell=new EpcFunction(x,y,width,height,name,_id);
		graph.addCell(cell);
	});
	$(str).find("role").each(function (){
		var _id=$(this).attr("id"),x=0,y=0,width=80,height=20,name="";
		cells[$(this).attr("id")]=cell;
		try{name=$(this).find("name").text();}catch(e){}
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		//try{cell.setFontBold($(this).find("font").attr("weight")=="bold");}catch(e){}
		//try{cell.setFontItalic($(this).find("font").attr("style")=="italic");}catch(e){}
		//try{cell.setFontUnderline($(this).find("font").attr("decoration")=="underline");}catch(e){}
		var cell=new EpcRole(x,y,width,height,name,_id);
		graph.addCell(cell);
	});
	$(str).find("object").each(function (){
		
		var _id=$(this).attr("id"),x=0,y=0,width=80,height=20,name="";
		cells[$(this).attr("id")]=cell;
		try{name=$(this).find("name").text();}catch(e){}
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		//try{cell.setFontBold($(this).find("font").attr("weight")=="bold");}catch(e){}
		//try{cell.setFontItalic($(this).find("font").attr("style")=="italic");}catch(e){}
		//try{cell.setFontUnderline($(this).find("font").attr("decoration")=="underline");}catch(e){}
		var cell=new EpcObject(x,y,width,height,name,_id);
		try{cell.setSemanticType($(this).find("attribute[typeRef=semantic]").attr("value"));}catch(e){}
		
		graph.addCell(cell);
	});
	$(str).find("and").each(function (){
		
		var _id=$(this).attr("id");
		var x=0,y=0,width=40,height=40;
		cells[$(this).attr("id")]=cell;
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		var cell=new EpcAnd(x,y,width,height,_id);
		graph.addCell(cell);
	});
	$(str).find("or").each(function (){
		var _id=$(this).attr("id");
		var x=0,y=0,width=40,height=40;
		cells[$(this).attr("id")]=cell;
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		var cell=new EpcOr(x,y,width,height,_id);
		graph.addCell(cell);
	});
	$(str).find("xor").each(function (){
		
		var _id=$(this).attr("id");
		var x=0,y=0,width=40,height=40;
		cells[$(this).attr("id")]=cell;
		//try{cell.setDescription($(this).find("description").text());}catch(e){}
		try{x=parseInt($(this).find("position").attr("x"));}catch(e){}
		try{y=parseInt($(this).find("position").attr("y"));}catch(e){}
		try{width=parseInt($(this).find("position").attr("width"));}catch(e){}
		try{height=parseInt($(this).find("position").attr("height"));}catch(e){}
		var cell=new EpcXor(x,y,width,height,_id);
		graph.addCell(cell);
	});
	$(str).find("arc").each(function (){
		
		try{
			if($(this).find("flow").length>0){
				var source=EpcVertex.getItem($(this).find("flow").attr("source"));
				var target=EpcVertex.getItem($(this).find("flow").attr("target"));
				console.log(source)
				graph.insertEdge(graph.getDefaultParent(), parseInt($(this).attr("id")), "", source, target);
			}else if($(this).find("relation").length>0){
				var source=cells[$(this).find("relation").attr("source")];
				var target=cells[$(this).find("relation").attr("target")];
				graph.insertEdge(graph.getDefaultParent(), parseInt($(this).attr("id")), "", source, target,"sourcePerimeterSpacing＝4");
			}
			console.log(1);
			
		}catch(e){
			console.log(e);
		}
	});
}

function auto_size(){
	var cells=graph.model.cells;
	for(var i in cells){
		var t=null;
		if(cells[i]&&cells[i].getType){
			t=cells[i].getType();
		}
		if(t=="event"||t=="function"||t=="role"||t=="object"){
			var _p=$("<div/>").text(cells[i].getName());
			_p.css({
				margin:"0px",
				padding:"0px",
				"font-size":"13px",
				"line-height":"25px",
				"width":"auto"
			}).appendTo($("body"));
			var _w=_p.width();
			var _h=_p.height();
			while(true){
				if(_w/_h>2){
					_p.width(_w-10);
					_w=_p.width();
					_h=_p.height();
				}else{
					break;
				}
			}
			_p.remove();
			cells[i].setWidth(_w);
			cells[i].setHeight(_h);
		}
	}
	graph.refresh();
}


$(function (){
	//$("body").empty();
	//new EpcEditor();
})

function auto_layout(){
	var first = new mxFastOrganicLayout(graph);
	var second = new mxParallelEdgeLayout(graph);
	var layout = new mxCompositeLayout(graph, [first, second], first);
	layout.execute(graph.getDefaultParent());
}

var epc_style=0;
function change_style(){
	var cells=graph.model.cells;
	for(var i in cells){
		if(cells[i] instanceof EPCCell){
			cells[i].changeStyle(epc_style);
		}else if(cells[i].isEdge){
			
		}
	}
	epc_style=1-epc_style;
	graph.refresh();
}
// display icon
$(function (){
	var c = $("#footer .display")[0].getContext("2d");
	c.fillStyle = "#fff";
	c.fillRect(0,0,13,2);
	c.fillRect(1,2,1,6);
	c.fillRect(11,2,1,6);
	c.fillRect(3,3,7,1);
	c.fillRect(1,7,11,1);
	c.fillRect(6,7,1,4);
	c.fillRect(4,11,5,1);
	$(document).mousedown(function (e){
		if(e.which==3&&$("#edit-window .body").hasClass("fullscreen")){
			$("#edit-window .body").removeClass("fullscreen");
		}
	});
});

function display_mode(){
	$("#edit-window .body").addClass("fullscreen");
}