$(function() {

    function dialogOpen() {
	var compositeProcess = OWLModel.getCompositeProcess();
	var input = compositeProcess.getInput();
	var output = compositeProcess.getOutput();
	$("#property-dialog>.name>.value").text(compositeProcess + "");
	$("#property-dialog>.input>.container").append(createTable(input));
	$("#property-dialog>.output>.container").append(createTable(output));
    } 
    // 创建输入或输出表格
    function createTable(arr) {
	var $table = $("<table/>");
	var $tr = $("<tr/>").appendTo($table);
	var $name = $("<th class='name'>Name</th>").appendTo($tr);
	var $type = $("<th class='type'>Type</th>").appendTo($tr);
	var $edit = $("<th class='edit'/>").appendTo($tr);
	var $delete = $("<th class='delete'/>").appendTo($tr);
	for ( var i in arr) {
	    var param = arr[i];
	    $tr = $("<tr/>"); 
	    $name = $("<td class='name'/>");
	    $type = $("<td class='type'/>");
	    $edit = $("<td class='edit'>"+(language=="zh_CN"?"编辑":"Edit")+"</td>");
	    $delete = $("<td class='delete'>"+(language=="zh_CN"?"删除":"Delete")+"</td>");
	    $edit.click(function()  {
		var text = $(this).parent().children(".name").text();
		var param = OWLModel.get(text);
		$("#property-dialog").dialog("close")
		$("#param-dialog").dialog({
		    close : function() {
			$("#property-dialog").dialog("open");
		    }
		}); 
		$("#param-dialog")[0].param = param;
		$("#param-dialog").dialog("open");
	    });
	    $delete.click(function() {
		var text = $(this).parent().children(".name").text();
		var param = OWLModel.get(text);
		OWLModel.getCompositeProcess().removeInput(param);
		OWLModel.getCompositeProcess().removeOutput(param);
		$("#property-dialog").dialog("close");
		$("#property-dialog").dialog("open");
	    });
	    $name.text(param + "");
	    $type.text(param.getType() + "");
	    $tr.append($name).append($type).append($edit).append($delete);
	    $table.append($tr);
	}
	return $table;
    }
    function close() {
	$("#property-dialog .container").children().remove();
    }
    // 添加process:Input
    $("#property-dialog>.input>.add").click(function() {
	var compositeProcess = OWLModel.getCompositeProcess();
	var processes = OWLModel.getAllAtomicProcess();
	var $ul = $("<ul/>");
	for ( var i = 0; i < processes.length; i++) {
	    var process = processes[i];
	    var $li = $("<li/>");
	    var $label = $("<p class='label'/>");
	    var $ul2 = $("<ul/>");
	    $label.text(process + "");
	    $li.append($label).append($ul2);
	    for ( var j in process.getInput()) {
		var input = process.getInput()[j];
		var $item = $("<li class='item'/>");
		$item.text(input + "").appendTo($ul2);
		$item.click(function() {
		    var text = $(this).parent().siblings(".label").text();
		    var input = OWLModel.get($(this).text());
		    var _input = OWLModel.createInput(input + "");
		    _input.setType(input.getType());
		    console.log(_input);
		    compositeProcess.addInput(_input);
		    $("#select-param-dialog").dialog("close");
		});
	    }
	    $li.appendTo($ul);
	}
	$("#property-dialog").dialog("close");
	$("#select-param-dialog").append($ul);
	$("#select-param-dialog").dialog({
	    close : function() {
		$(this).children().remove();
		$("#property-dialog").dialog("open");
	    },
	    height : 320,
	    modal : true,
	    resizable : false,
	    title : "选择process:Input",
	    width : 540
	});
    });

    $("#property-dialog>.name>.button").click(function() {
	var compositeProcess = OWLModel.getCompositeProcess();
	var _old = $("#property-dialog>.name>.value").text();
	var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Please input new name", _old);
	if (_new != null && _old != _new) {
	    if (OWLModel.isIdAvailable(_new)) {
		compositeProcess.setId(_new);
		$("#property-dialog>.name>.value").text(_new);
	    } else {
		alert(language=="zh_CN"?"该名称不可用！":"it is unavaliable");
	    }
	}
    });

    // 添加process:Output
    $("#property-dialog>.output>.add").click(function() {
	var compositeProcess = OWLModel.getCompositeProcess();
	var processes = OWLModel.getAllAtomicProcess();
	var $ul = $("<ul/>");
	for ( var i = 0; i < processes.length; i++) {
	    var process = processes[i];
	    var $li = $("<li/>");
	    var $label = $("<p class='label'/>");
	    var $ul2 = $("<ul/>");
	    $label.text(process + "");
	    $li.append($label).append($ul2);
	    for ( var j in process.getOutput()) {
		var output = process.getOutput()[j];
		var $item = $("<li class='item'/>");
		$item.text(output + "").appendTo($ul2);
		$item.click(function() {
		    var text = $(this).parent().siblings(".label").text();
		    var output = OWLModel.get($(this).text());
		    var _output = OWLModel.createOutput(output + "");
		    _output.setType(output.getType());
		    compositeProcess.addOutput(_output);
		    $("#select-param-dialog").dialog("close");
		});
	    }
	    $li.appendTo($ul);
	}
	$("#property-dialog").dialog("close");
	$("#select-param-dialog").append($ul);
	$("#select-param-dialog").dialog({
	    close : function() {
		$(this).children().remove();
		$("#property-dialog").dialog("open");
	    },
	    height : 320,
	    modal : true,
	    resizable : false,
	    title : language=="zh_CN"?"选择process:Output":"Select process output",
	    width : 540
	});
    });
    $("#property-dialog .button").button();
    $("#property-dialog").dialog( {
	autoOpen : false,
	close : close,
	height : 480,
	open : dialogOpen,
	modal : true,
	resizable : false,
	title : language=="zh_CN"?"属性":"Property",
	width : 640
    });
});