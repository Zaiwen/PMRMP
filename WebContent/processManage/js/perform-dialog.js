$(function() {
    var compositeProcess = OWLModel.getCompositeProcess();
    var $dialog = $("#perform-dialog");
    var perform = null;
    // 对话框打开时
    function dialogOpen() {
	// 得到所选的Perform对象
	perform = $("#perform-dialog")[0].perform;
	var id = perform.getId();
	var process = perform.getProcess();
	// 初始化标题、名称以及流程名
	$dialog.dialog("option", "title", language=="zh_CN"?"Perform属性--":"Perform property" + id);
	$("#perform-dialog>.name>.value").text(id + "");
	$("#perform-dialog>.process>.value").text(process + "");
	// 初始化数据绑定 
	var $table = $("<table/>");
	var $tr = $("<tr/>").appendTo($table);
	var $from = $("<th>From</th>").appendTo($tr);
	var $to = $("<th>To</th>").appendTo($tr);
	var $edit = null;
	var $reset=null; 
	for ( var i in perform.getInput()) {
	    // 得到输入对象
	    var input = perform.getInput()[i];
	    // 得到绑定对象
	    var inputBinding = null;
	    for ( var j in perform.getInputBinding()) {
		var binding = perform.getInputBinding()[j];
		if (binding.getToParam() == input) {
		    inputBinding = binding;
		    break;
		}
	    } 
	    // From
	    $from = $("<td class='from'/>");
	    if (inputBinding != null) {
		var process = inputBinding.getFromProcess();
		var theVar = inputBinding.getTheVar();
		$from.text(process + ":" + theVar);
	    }
	    // To
	    $to = $("<td class='to'/>");
	    $to.text(input + "");
	    // 编辑已有的绑定
	    $edit = $("<td class='edit'>"+(language=="zh_CN"?"选择":"Select")+"</edit>"); 
	    $edit.click(function() {
		var txt = $(this).parent().children().eq(1).text();
		var param = OWLModel.get(txt);
		selectBinding(param);
	    });
	    // 清除数据绑定
	    $reset=$("<td class='reset'>"+(language=="zh_CN"?"重置":"reset")+"</td>");
	    $reset.click(function (){
		var txt = $(this).parent().children().eq(1).text();
		var param = OWLModel.get(txt);
		var binding=OWLModel.getBindingForParameter(param);
		if(binding!=null){
		    perform.removeInputBinding(binding);
		    $("#perform-dialog").dialog("close");
		    $("#perform-dialog").dialog("open");
		}
	    });
	    // 拼接成一行
	    $("<tr/>").append($from).append($to).append($edit).append($reset).appendTo($table);
	}
	// 构建表格
	$("#perform-dialog>.binding>.container").children().remove();
	$("#perform-dialog>.binding>.container").append($table);
    }
    // 选择参数进行绑定
    // param 要进行绑定的参数
    function selectBinding(param) {
	// 得到所有的Perform
	var performs = OWLModel.getAllPerform();
	// 构建列表以列出所有可用的参数
	var $ul = $("<ul/>");
	var $li = $("<li/>");
	var $label = $("<p class='label'/>");
	var $ul2 = $("<ul/>");
	// TheParentPerform
	$label.text("TheParentPerform");
	$li.append($label).append($ul2).appendTo($ul);
	for ( var i in compositeProcess.getInput()) {
	    var input = compositeProcess.getInput()[i];
	    var $item = $("<li class='item'/>");
	    $item.text(input + "").appendTo($ul2);
	    $item.click(function() {
		var binding = OWLModel.createInputBinding();
		binding.setToParam(param);
		binding.setFromProcess(OWLModel.getTheParentPerform());
		binding.setTheVar(OWLModel.get($(this).text()));
		perform.addInputBinding(binding);
		$("#select-binding-dialog").dialog("close");
	    });
	}
	for ( var i = 0; i < performs.length; i++) {
	    var pf = performs[i];
	    if (pf == perform) {
		continue;
	    }
	    $li = $("<li/>");
	    $label = $("<p class='label'/>");
	    $ul2 = $("<ul/>");
	    $label.text(pf + "");
	    $li.append($label).append($ul2);
	    for ( var j in pf.getOutput()) {
		var output = pf.getOutput()[j];
		var $item = $("<li class='item'/>");
		$item.text(output + "").appendTo($ul2);
		$item.click(function() {
		    var text = $(this).parent().siblings(".label").text();
		    var binding = OWLModel.createInputBinding();
		    binding.setFromProcess(OWLModel.get(text));
		    binding.setToParam(param);
		    binding.setTheVar(OWLModel.get($(this).text()));
		    perform.addInputBinding(binding);
		    $("#select-binding-dialog").dialog("close");
		});
	    }
	    $li.appendTo($ul);
	}
	$("#perform-dialog").dialog("close");
	$("#select-binding-dialog").append($ul);
	$("#select-binding-dialog").dialog({
	    close : function() {
		$(this).children().remove();
		$("#perform-dialog").dialog("open");
	    },
	    height : 320,
	    modal : true,
	    resizable : false,
	    title : "InputBinding",
	    width : 540
	});
    }

    $("#perform-dialog .button").button();
    $("#perform-dialog>.name>.button").click(function() {
	var _old = $("#perform-dialog>.name>.value").text();
	var _new = prompt(language=="zh_CN"?"请输入新的名字：":"Please input ne name", _old);
	if (_new != null && _old != _new) {
	    if (OWLModel.isIdAvailable(_new)) {
		perform.setId(_new);
		Redraw();
		$("#perform-dialog>.name>.value").text(_new);
		$("#composite-process-tree-window>.body").find("a").each(function() {
		    if ($.trim($(this).text()) == _old) {
			var prefix = "<ins class=\"jstree-icon\">&nbsp;</ins>";
			$(this).html(prefix + _new);
		    }
		});
		$dialog.dialog({
		    "title" : language=="zh_CN"?"Perform属性--":"Perform Property--" + _new
		});
	    } else {
		alert(language=="zh_CN"?"该名称不可用！":"cannot use it");
	    }
	}
    });
    $("#perform-dialog>.process>.button").click(function() {
	$("#perform-dialog").dialog("close");
	$("#select-process-dialog").dialog( {
	    close : function() {
		$("#perform-dialog").dialog("open");
	    },
	    height : 240,
	    modal : true,
	    resizable : false,
	    title : language=="zh_CN"?" 选择Process":" select process",
	    width : 360
	});
	$("#select-process-dialog").children().remove();
	for ( var i = 0; i < OWLModel.getAllAtomicProcess().length; i++) {
	    var process = OWLModel.getAllAtomicProcess()[i];
	    var item = $("<div/>").text(process + "");
	    item.click(function() {
		var text = $(this).text();
		perform.setProcess(OWLModel.get(text));
		$("#select-process-dialog").dialog("close");
		$("#perform-dialog>.process>.value").text(text + "");
	    });
	    $("#select-process-dialog").append(item);
	}
    });
    $dialog.dialog({
	autoOpen : false,
	height : 420,
	open : dialogOpen,
	modal : true,
	resizable : false,
	title :language=="zh_CN"? "Perform属性":"Perform Property",
	width : 480
    });
});