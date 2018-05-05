$(function() {
    var compositeProcess = OWLModel.getCompositeProcess();
    var $dialog = $("#produce-dialog");
    var produce = null;
    // 当属性对话框打开时对应的操作
    function dialogOpen() {
	produce = $("#produce-dialog")[0].produce;
	$dialog.dialog("option", "title", language=="zh_CN"?"Produce属性--":"Produce Property--" + produce);
	$("#produce-dialog>.name>.value").text(produce + "");
	var $table = $("<table/>");
	var $tr = $("<tr/>").appendTo($table);
	var $from = $("<th>From</th>").appendTo($tr);
	var $to = $("<th>To</th>").appendTo($tr);
	for ( var i in compositeProcess.getOutput()) {
	    var output = compositeProcess.getOutput()[i];
	    var binding = null;
	    for ( var j in produce.getOutputBinding()) {
		if (produce.getOutputBinding()[j].getToParam() == output) {
		    binding = produce.getOutputBinding()[j];
		    break; 
		} 
	    }
	    var $edit = $("<td class='edit'>"+language=="zh_CN"?"选择":"Select"+"</edit>");
	    var $reset = $("<td class='reset'>"+language=="zh_CN"?"重置":"reset"+"</td>");
	    $reset.click(function (){
		var txt = $(this).parent().children().eq(1).text();
		var param = OWLModel.get(txt);
		var binding=OWLModel.getBindingForParameter(param);
		if(binding!=null){
		    produce.removeOutputBinding(binding);
		    $("#produce-dialog").dialog("close");
		    $("#produce-dialog").dialog("open");
		} 
	    });
	    $tr = $("<tr/>");
	    $from = $("<td class='from'/>");
	    if (binding != null) {
		var text = binding.getFromProcess() + ":" + binding.getTheVar();
		$from.text(text);
	    }
	    $to = $("<td class='to'/>");
	    $to.text(output + "");
	    $edit.click(function() {
		var text = $(this).parent().children().eq(1).text();
		var param = OWLModel.get(text);
		select_binding(param);
	    });
	    $tr.append($from).append($to).append($edit).append($reset)
		    .appendTo($table);
	}
	$("#produce-dialog>.binding>.container").children().remove();
	$("#produce-dialog>.binding>.container").append($table);
    }
    function select_binding(param) {
	$("#produce-dialog").dialog("close");
	var $ul = $("<ul/>");
	var $li = $("<li/>");
	var $label = $("<p class='label'/>");
	var $ul2 = $("<ul/>");
	$label.text("TheParentPerform");
	$li.append($label).append($ul2).appendTo($ul);
	for ( var i in OWLModel.getAllPerform()) {
	    var perform = OWLModel.getAllPerform()[i];
	    $li = $("<li/>");
	    $label = $("<p class='label'/>");
	    $ul2 = $("<ul/>");
	    $label.text(perform + "");
	    $li.append($label).append($ul2);
	    for ( var j in perform.getOutput()) {
		var output = perform.getOutput()[j];
		var $item = $("<li class='item'/>");
		$item.text(output + "").appendTo($ul2);
		$item.click(function() {
		    var text = $(this).parent().siblings(".label").text();
		    var binding = OWLModel.createOutputBinding();
		    binding.setFromProcess(OWLModel.get(text));
		    binding.setToParam(param);
		    binding.setTheVar(OWLModel.get($(this).text()));
		    produce.addOutputBinding(binding);
		    $("#select-binding-dialog").dialog("close");
		});
	    }
	    $li.appendTo($ul);
	}
	$("#produce-dialog").dialog("close");
	$("#select-binding-dialog").append($ul);
	$("#select-binding-dialog").dialog({
	    close : function() {
		$(this).children().remove();
		$("#produce-dialog").dialog("open");
	    },
	    height : 320,
	    modal : true,
	    resizable : false,
	    title : "OutputBinding",
	    width : 540
	});
    }
    // 当属性对话框关闭时执行的操作
    function close() {
	$("#produce-dialog .container").children().remove();
    }
    // 更改该控制结构的名称
    $("#produce-dialog>.name>.button").click(function() {
	var _old = $("#produce-dialog>.name>.value").text();
	var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Please input new name", _old);
	if (_new != null && _old != _new) {
	    if (OWLModel.isIdAvailable(_new)) {
		produce.setId(_new);
		Redraw();
		$("#produce-dialog>.name>.value").text(_new);
		$("#composite-process-tree-window>.body").find("a").each(function() {
		    if ($.trim($(this).text()) == _old) {
			var prefix = "<ins class=\"jstree-icon\">&nbsp;</ins>";
			$(this).html(prefix + _new);
		    }
		});
		$dialog.dialog({
		    "title" : language=="zh_CN"?"Produce属性--":"Produce Property" + _new
		});
	    } else {
		alert(language=="zh_CN"?"该名称不可用！":"it is unavaliable");
	    }
	}
    });
    // 使用jQuery UI初始化界面
    $("#produce-dialog .button").button();
    $("#produce-dialog").dialog({
	autoOpen : false,
	close : close,
	height : 360,
	modal : true,
	open : dialogOpen,
	resizable : false,
	width : 480,
    });
});