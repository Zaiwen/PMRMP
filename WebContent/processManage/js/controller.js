/**
 * 根据用户所选择的视图的不同子窗口数量会有所变化 
 * 默认为三个 
 * 当点击不同的视图按钮时会在#editor元素上添加不同的类
 * 进而通过CSS改变视图
 */
var change_view = null;
$(function (){
	$("#statusbar .view-1").click(function (){
		$("#statusbar .view .selected").removeClass("selected");
		$("#editor").removeClass("view-2");
		$("#editor").removeClass("view-3");
		$("#editor").addClass("view-1");
		$(this).addClass("selected")
	});	
	$("#statusbar .view-2").click(function (){
		$("#statusbar .view .selected").removeClass("selected");
		$("#editor").removeClass("view-1");
		$("#editor").removeClass("view-3");
		$("#editor").addClass("view-2");
		$(this).addClass("selected")
	});
	$("#statusbar .view-3").click(function (){
		$("#statusbar .view .selected").removeClass("selected");
		$("#editor").removeClass("view-1");
		$("#editor").removeClass("view-2");
		$("#editor").addClass("view-3");
		$(this).addClass("selected")
	});
});

function imagePrint(){
	$("#editor").hide();
	var xx=$("#editor .center .body").children().clone();
	$("body").append(xx);
	window.print();
	xx.remove();
	$("#editor").show();
	
}

var Log = {};

var Constant = {};

Constant.URL_WSDL_LIST = "get-wsdl-list.jsp";

Constant.LANGUAGE_CHINESE = "zh-CN";

Constant.LANGUAGE_ENGLISH = "en-US";
	
var Config = {};

Config.language = "";

var Util = {};

Util.htmlEncode = function (text){
	return $("<div></div>").text(text + "").html();
};

Util.bind = function (scope, funct){
	return function(){
		return funct.apply(scope, arguments);
	};
}

/**
 * @param {Function(WSDLOperation)}
 * */
function WSDLListDialog(callback){
	var container = $("<div style='font-family:Consolas;font-size:12px;'>loading</div>");
	container.dialog({
		close : function (){
			container.dialog("destroy");
			container.remove();
		},
		height : "480",
		modal : true,
		title : "Web Service List",
		width : 720
	});
	
	$.getJSON(Constant.URL_WSDL_LIST, function (data){
		var content;
		if(data != null && data instanceof Array){
			//console.log(data)
			content = "<div><table>";
			content += "<tr>";
			//content += "<th class='id'></th>";
			content += "<th class='name'>" 	+ "Name" 	+ "</th>";
			content += "<th class='url'>" 		+ "URL" 	+ "</th>";
			content += "<th class='domain'>"	+ "Domain"	+ "</th>";
			content += "<th class='detail'></th>";
			content += "</tr>";
			for(var i = 0; i < data.length; i++){
				content += "<tr>";
				//content += "<td class='id'>" 		+ Util.htmlEncode(data[i].id) 		+ "</td>";
				content += "<td class='name'>" 	+ Util.htmlEncode(data[i].name) 	+ "</td>";
				content += "<td class='url'>" 	+ Util.htmlEncode(data[i].url) 		+ "</td>";
				content += "<td class='domain'>" 	+ Util.htmlEncode(data[i].domain) 	+ "</td>";
				content += "<td class='detail link'>"	+ "Detail"		+ "</td>";
				content += '</tr>';
			}
			content += "</table></div>";
		}else{
			content = "<div>Error</div>"
		}
		container.html(content);
		container.dialog({width:container.find("table").width() + 50});
		container.find("td.detail").click(function (){
			new WSDLDetailDialog($(this).prevAll(".url").text(), function (operation){
				container.dialog("close");
				callback(operation);
			});
		}).css({
			"cursor":"pointer",
			"color"	:"blue"
		});
	});
}
/**
 * @param url {String}
 * @param callback {Function(WSDLOperation)}
 * */
function WSDLDetailDialog(url, callback){
	var container = $("<div style='font-family:Consolas;font-size:12px;'>loading...</div>");
	container.dialog({
		close : function (){
			container.dialog("destroy");
			container.remove();
		},
		height : "480",
		modal : true,
		title : "Web Service Detail",
		width : 720
	});
	new WSDLService(url, function (service){
		var operations = service.getOperations();
		var content = "<table>";
		for(var i = 0; i < operations.length; i++){
			content += "<tr class='title'><th class='import' style='color:blue;cursor:pointer;text-decoration:underline;'>Import</th>";
			content += "<th colspan='2' style='font-size:14px;text-align:center'>" + Util.htmlEncode(operations[i].getName());
//			content += "<span class='unfold' style='color:#900;cursor:pointer;'>[+]</span>"
//			content += "<span class='import'style='color:#900;cursor:pointer;'>[→]</span></th></tr>"
			var inputs = operations[i].getInputs();
			var outputs = operations[i].getOutputs();
			
			for(var j = 0; j < inputs.length; j++){
				content += "<tr><td>input</td>"
				content += "<td><span style='color:#090;'>" + Util.htmlEncode(inputs[j].getName()) + "</span></td>"
				content += "<td><span style='color:#999'>{</span>";
				content += "<span style='color:#009'>" + Util.htmlEncode(inputs[j].getType().getNamespaceURI()) + "</span>";
				content += "<span style='color:gray'>}</span>";
				content += "<span style='color:#900'>" + Util.htmlEncode(inputs[j].getType().getLocalPart()) + "</span></td>";
				content += "</tr>";
			}
			for(var j = 0; j < outputs.length; j++){
				content += "<tr><td>output</td>"
				content += "<td><span style='color:#090;'>" + Util.htmlEncode(outputs[j].getName()) + "</span></td>"
				content += "<td><span style='color:#999'>{</span>";
				content += "<span style='color:#009'>" + Util.htmlEncode(outputs[j].getType().getNamespaceURI()) + "</span>";
				content += "<span style='color:gray'>}</span>";
				content += "<span style='color:#900'>" + Util.htmlEncode(outputs[j].getType().getLocalPart()) + "</span></td>";
				content += "</tr>";
			}
			
		}
		content += "</table>";
		container.html(content);
		container.dialog({width:container.find("table").width() + 50});
		container.find(".import").click(function (){
			callback(service.getOperation($(this).nextAll("th").text()));
			container.dialog("close");
		});
	}, function (url){
		container.text("cann't get " + url);
	});
	
	// 展开与隐藏
//	container.find("tr:not(.title)").hide();
//	container.find(".unfold").click(function (){
//		container.find("tr:not(.title)").hide();
//		var tr = $(this).parent().parent().nextAll("tr");
//		for(var i = 0; i < tr.length; i++){
//			if($(tr[i]).hasClass('title')){
//				break;
//			}
//			$(tr[i]).show();
//		}
//	});
	
	
	
}

$(function (){
	$("#editor .right .add").click(function (){
		new WSDLListDialog(function (operation){
			OWLModel.createProcess(operation);
			UpdateProcessView();
		});
	});
});