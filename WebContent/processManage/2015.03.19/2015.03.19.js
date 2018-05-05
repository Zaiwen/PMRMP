// 基于2015.02.09的稳定版的开发
//【相关文件】
// process-dialog.js
//【更新内容】
// 1、修复因zh_CN变量不存在而使脚本执行中断的问题
// 2、


$(function (){
	var _style={
		"background-color":"rgb(33,115,70)",
		"color":"#fff",
		"cursor":"pointer",
		"line-height":"28px",
		"text-align":"center",
		"width":"120px"
	};
	$("#dlg-ato-proc>.precondition").text("配置PreCondition").css(_style).click(function (){
		$("#dlg-ato-proc").dialog("close");
		$("#editor").addClass("readonly");
	});
	
	$("#automic-process .delete").click(function (){
		$("#editor").removeClass("readonly");
	});
	
	$.get("2015.03.19/2015.03.19.jsp",function (data){
		$("#editor").append($(data));
	});
});

/*

$(function (){
	$("#atomic-process-list-window>.body").append($("<div class='item selected'>TEST</div>"));
	OWLModel.ontology["TEST"]=new AtomicProcess("TEST",new WSDLOperation());
	
	$("#dlg-ato-proc").dialog("open");

});*/

window.zh_CN=null;


// 与之前版本的对接
$(function (){
	$("#composite-process-tree-window").attr("id","composite-process-tree-window");
	$("#composite-process-graph-window").attr("id","composite-process-graph-window");
	$("#atomic-process-list-window").attr("id","atomic-process-list-window");
});