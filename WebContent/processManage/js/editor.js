// 插入控制结构
// 第一个参数是父控制结构的ID
// 第二个参数是子控制结构的类型
// 如果可以插入则返回true，否则返回错误信息
function owls_insert(_parent,_child){
	_parent=OWLModel.get(_parent);
	if(_parent==null){
		return "程序出错，找不到对应的父控制结构！";
	}else if(_parent instanceof SequenceConstruct){
		if(_child=="Sequence"){
			return "Sequence内部加入Sequence是一种冗余的结构，不推荐使用！";
		}else{
			_parent.addComponent(OWLModel.create(_child+"Construct"));
			return true;
		}
	}else if(_parent instanceof SplitJoinConstruct){
		
	}else if(_parent instanceof AnyOrderConstruct){
		
	}else if(_parent instanceof PerformConstruct){
		
	}else if(_parent instanceof ProduceConstruct){
		
	}else{
		return "暂不可用！";
	}
}






// 返回被选中的流程
function get_selected_construct() {
    var $tree = $('#editor>.left>.body');
    var $node = $tree.jstree('get_selected');
    if ($node.length > 0) {
	var text = $.trim($node.children("a").text());
	return OWLModel.get(text);
    } else {
	return null;
    }
}
// 判断被选中的流程是否是Perform结构
function selected_is_perform() {
    var construct = get_selected_construct();
    if (construct == null) {
	return false;
    } else if (construct instanceof Perform) {
	return true;
    } else {
	return false;
    }
}
// 判断被选中的流程是否是Produce结构
function selected_is_produce() {
    var construct = get_selected_construct();
    if (construct == null) {
	return false;
    } else if (construct instanceof Produce) {
	return true;
    } else {
	return false;
    }
}
// 判断被选中的流程是否是Sequence结构
function selected_is_sequence() {
    var construct = get_selected_construct();
    if (construct == null) {
	return false;
    } else if (construct instanceof Sequence) {
	return true;
    } else {
	return false;
    }
}
// 判断被选中的流程是否是If-Then-Else结构
function selected_is_ifthenelse() {
    var construct = get_selected_construct();
    if (construct == null) {
	return false;
    } else if (construct instanceof IfThenElse) {
	return true;
    } else {
	return false;
    }
}
// 判断被选中的流程是否是SplitJoin结构
function selected_is_splitjoin() {
    var construct = get_selected_construct();
    if (construct == null) {
	return false;
    } else if (construct instanceof SplitJoin) {
	return true;
    } else {
	return false;
    }
}
// 在插入之前进行判断
// {String} construct 要插入的流程的名称
function check_before_insert(construct) 
{
    if (selected_is_perform()) 
	{
		alert("Perform结构内部不能有其它结构");
		return false;
    } 
	else if (selected_is_produce()) 
	{
		alert("Produce结构内部不能有其它结构");
		return false;
    } 
	else if (selected_is_sequence()) 
	{
		if (construct == "Sequence") {
			alert("Sequence结构内部加入Sequence是一种冗余的结构，不推荐使用！");
			return false;
		} else {
			return true;
		}
    } 
	else if (selected_is_ifthenelse()) 
	{
		var construct = get_selected_construct();
		if(construct.getElseConstruct() != null)
		{
			alert("If-Then-Else里面只能出现两个分支！");
			return false;
		}
		return true;
    } 
	else if (selected_is_splitjoin()) 
	{
		if (construct == "Split-Join") {
			alert("Split-Join结构内部加入Split-Join是一种冗余的结构，不推荐使用！");
			return false
		}
		else 
		{
			return true;
		}
	} 
	else 
	{
		return true;
    }
}

function insert_construct(construct, child)
{
	if(selected_is_sequence())
	{
		construct.addComponent(child);
	}
	else if(selected_is_splitjoin())
	{
		construct.addComponent(child);
	}
	else if(selected_is_ifthenelse())
	{
		if(construct.getThenConstruct() == null)
		{
			construct.setThenConstruct(child);
		}
		else if(construct.getElseConstruct() == null)
		{
			construct.setElseConstruct(child);
		}
		else
		{
			alert("程序出错啦~\nIf-Then-Else控制结构不可能出现三个及以上的分支的！");
		}
	}
}

// 流程建模
$(function() {
    $("#editor>.toolbar>.save").click(function() {
	$("#editor>.statusbar").text("正在保存ing……");
	$.ajax({
	    async : false,
	    url : "Save.jsp?",
	    mimeType : 'text/plain',
	    type : "POST",
	    data : "content=" + OWLModel.toJson(),
	    success : function() {
		$("#editor>.statusbar").text("保存成功！");
		alert("Success");
		$("#editor>.statusbar").text("");
	    },
	    error : function() {
		$("#editor>.statusbar").text("保存失败！");
		alert("Error!");
		$("#editor>.statusbar").text("");
	    }
	});
    });
    $("#editor>.toolbar>.property").click(function() {
	$("#property-dialog").dialog("open");
    });
});

// 添加原子流程
$(function() {
    $("#editor>.right>.head>.add").click(function() {
	$("#dlg-wsdl-info").dialog("open");
    });
});
// 移除原子流程
$(function() {
    $("#editor>.right>.head>.remove").click(function() {
	var $selected = $("#editor>.right>.body>.selected");
	var text = $selected.text();
	var pr = OWLModel.get(text);
	if (pr != null) {
	    pr.remove();
	    $selected.remove();
	} else {
	    alert("请先选中一个原子流程！");
	}

    });
});
$(function() {
    var $tree = $('#editor>.left>.body');
    $("#editor>.center>.head>.zoom-in").click(function() {
	$('#editor>.center>.body')[0].graph.zoomIn();
    });
    $("#editor>.center>.head>.zoom-out").click(function() {
	$('#editor>.center>.body')[0].graph.zoomOut();
    });
    $("#editor>.center>.head>.zoom-actual").click(function() {
	$('#editor>.center>.body')[0].graph.zoomActual();
    });
    Redraw();
    function isRoot() {
	return $tree.jstree('get_selected').length == 0;
    }
    // Click Perform
    $('#editor>.left>.head>.perform').click(function() {
	var perform = OWLModel.createPerform();
	var id = perform.getId();
	if (isRoot()) {
	    // $tree.jstree('create', -1, 'last', id, null, true);
	    // OWLModel.getCompositeProcess().setComposeOf(perform);
	    alert("Perform不能作为起始节点");
	} else if (check_before_insert("Perform")) {
	    $tree.jstree('create', null, 'last', id, null, true);
	    var $selected = $tree.jstree("get_selected");
	    var parent = $.trim($selected.children("a").text());
	    var construct = OWLModel.get(parent);
		insert_construct(construct, perform);
	}
	Redraw();
    });

    $('#editor>.left>.head>.sequence').click(function() {
	var sequence = OWLModel.createSequence();
	var id = sequence.getId();
	if (isRoot()) {
	    var $nd = $tree.jstree('create', -1, 'last', id, null, true);
	    $tree.jstree("select_node", $nd);
	    OWLModel.getCompositeProcess().setComposeOf(sequence);
	} else if (check_before_insert("Sequence")) {
	    $tree.jstree('create', null, 'last', id, null, true);
	    var $selected = $tree.jstree("get_selected");
	    var parent = $.trim($selected.children("a").text());
	    var construct = OWLModel.get(parent);
	    insert_construct(construct, sequence);
	}
	Redraw();
    });
    // Click Split+Join
    $('#editor>.left>.head>.split-join').click(function() {
	var splitJoin = OWLModel.createSplitJoin();
	var id = splitJoin.getId();
	if (isRoot()) {
	    var $nd = $tree.jstree('create', -1, 'last', id, null, true);
	    $tree.jstree("select_node", $nd);
	    OWLModel.getCompositeProcess().setComposeOf(splitJoin);
	} else if (check_before_insert("Split-Join")) {
	    $tree.jstree('create', null, 'last', id, null, true);
	    var $selected = $tree.jstree("get_selected");
	    var parent = $.trim($selected.children("a").text());
	    var construct = OWLModel.get(parent);
	    insert_construct(construct, splitJoin);
	}
	Redraw();
    });
    // Click Any-Order
    $('#editor>.left>.head>.any-order').click(function() {
		alert("暂不可用！");
    });
    // Click Choice
    $('#editor>.left>.head>.choice').click(function() {
		alert("暂不可用！");
    });
    // Click If-Then-Else
    $('#editor>.left>.head>.if-then-else').click(function() {
    	var _if = OWLModel.createIfThenElse();
    	var id = _if.getId();
    	if (isRoot()) {
    	    var $nd = $tree.jstree('create', -1, 'last', id, null, true);
    	    $tree.jstree("select_node", $nd);
    	    OWLModel.getCompositeProcess().setComposeOf(_if);
    	} else if (check_before_insert("If-Then-Else")) {
    	    $tree.jstree('create', null, 'last', id, null, true);
    	    var $selected = $tree.jstree("get_selected");
    	    var parent = $.trim($selected.children("a").text());
    	    var construct = OWLModel.get(parent);
    	    insert_construct(construct, _if);
    	}
    	Redraw();
    });

    $('#editor>.left>.head>.repeat-while').click(function() {
	// insert('If-Then-Else');
	alert("暂不可用");
    });

    $('#editor>.left>.head>.repeat-until').click(function() {
	// insert('If-Then-Else');
	alert("暂不可用");
    });

    // Click Produce
    $('#editor>.left>.head>.produce').click(function() {
	var produce = OWLModel.createProduce();
	var id = produce.getId();
	if (isRoot()) {
	    // $tree.jstree('create', -1, 'last', id, null, true);
	    // OWLModel.getCompositeProcess().setComposeOf(produce);
	    alert("Produce不能作为起始节点");
	} else if (check_before_insert("Produce")) {
	    $tree.jstree('create', null, 'last', id, null, true);
	    var $selected = $tree.jstree("get_selected");
	    var parent = $.trim($selected.children("a").text());
	    var construct = OWLModel.get(parent);
	    insert_construct(construct, produce);
	}
	Redraw();
    });
    // Click Delete
    $('#editor>.left>.head>.delete').click(function() {
	var $selected = $tree.jstree("get_selected");
	var text = $.trim($selected.children("a").text());
	var construct = OWLModel.get(text);
	if (OWLModel.getCompositeProcess().getComposeOf() == construct) {
	    construct.remove();
	    OWLModel.getCompositeProcess().setComposeOf(null);
	} else {
	    text = $selected.parent().parent().children("a").text();
	    text = $.trim(text);
	    var parent = OWLModel.get(text);
		if(parent instanceof Sequence || parent instanceof SplitJoin)
		{
			parent.removeComponent(construct);
		}
		else if(parent instanceof IfThenElse)
		{
			if(parent.getElseConstruct() != null)
			{
				parent.removeElseConstruct();
			}
			else if(parent.getThenConstruct() != null)
			{
				parent.removeThenConstruct();
			}
			else
			{
				alert("程序出错啦~\n要移除的节点不存在！");
			}
		}
	}
	$tree.jstree('remove');
	Redraw();
    });
});

function Redraw() {
    var $container = $('#editor>.center>.body');
    var graph = $container[0].graph;
    // 初始化画布
    if (graph == null) {
	graph = new mxGraph($container[0]);
	$container[0].graph = graph;
	var style = graph.getStylesheet().getDefaultEdgeStyle();
	style.editabled = 0;
	style.movable = 0;
	style.resizable = 0;
	style.strokeWidth = 2;
	style.strokeColor = '#0099FF';
	style.fontSize = 15;
	style.fontColor = "#FF0000";
	graph.panningHandler.useLeftButtonForPanning = true;
	graph.ignoreCell = true;
	graph.container.style.cursor = "move";
	graph.setPanning(true);
	graph.setConnectable(false);
	graph.setDisconnectOnMove(false);
	graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, event) {
	    var cell = graph.getSelectionCell();
	    if (cell == null || cell.construct == null) {
		return;
	    }
	    if (cell.construct instanceof Perform) {
		$("#perform-dialog")[0].perform = cell.construct;
		$("#perform-dialog").dialog("open");
	    } else if (cell.construct instanceof IfThenElse) {
	    	console.log(123);
		$("#condition-dialog")[0].construct = cell.construct;
		$("#condition-dialog").dialog("open");
	    } else if (cell.construct instanceof Produce) {
		$("#produce-dialog")[0].produce = cell.construct;
		$("#produce-dialog").dialog("open");
	    }
	});
    }
    var vstyle = 'shape=ellipse;' + 'strokeWidth=2;' + 'strokeColor=#0099FF;'
	    + 'fillColor=#0099FF;' + 'fontColor=#FFFFFF;' + 'fontSize=12;'
	    + 'fontFamily=Microsoft YaHei;' + 'movable=0;' + 'resizable=0;'
	    + 'editable=0;';
    var parent = graph.getDefaultParent();
    // 绘图
    if (OWLModel.getCompositeProcess().getComposeOf() == null) {
	graph.selectAll();
	graph.removeCells();
	var v1 = graph.insertVertex(parent, null, 'Start/In', 100, 40, 80, 40,
		vstyle);
	var v2 = graph.insertVertex(parent, null, 'Finish/Out', 100, 200, 80,
		40, vstyle);
	graph.insertEdge(parent, null, null, v1, v2);
    } else {
	graph.selectAll();
	graph.removeCells();
	var construct = OWLModel.getCompositeProcess().getComposeOf();
	construct.draw(graph, 60, 100);
	var v1 = graph.insertVertex(parent, null, 'Start/In', construct
		.getWidth() / 2 + 20, 20, 80, 40, vstyle);
	var v2 = graph.insertVertex(parent, null, 'Finish/Out', construct
		.getWidth() / 2 + 20, construct.getHeight() + 140, 80, 40,
		vstyle);
	if (construct.getStartVertex() != null) {
	    graph
		    .insertEdge(parent, null, null, v1, construct
			    .getStartVertex());
	    graph.insertEdge(parent, null, null, construct.getEndVertex(), v2);
	} else {
	    graph.insertEdge(parent, null, null, v1, v2);
	}
    }
}

function Init(str) {
    // 第一层的结构
    for ( var i = 0; i < str.anyOrder.length; i+=1) {
	OWLModel.create("AnyOrderConstruct", str.anyOrder[i].ID);
    }
    for ( var i = 0; i < str.choice.length; i+=1) {
	OWLModel.create("ChoiceConstruct", str.choice[i].ID);
    }
    for ( var i = 0; i < str.sequence.length; i+=1) {
	OWLModel.create("SequenceConstruct", str.sequence[i].ID);
    }
    for ( var i = 0; i < str.splitJoin.length; i+=1) {
	OWLModel.create("SplitJoinConstruct", str.splitJoin[i].ID);
    }
    for ( var i = 0; i < str.perform.length; i+=1) {
	OWLModel.create("PerformConstruct", str.perform[i].ID);
    }
    for ( var i = 0; i < str.produce.length; i+=1) {
	OWLModel.create("ProduceConstruct", str.produce[i].ID);
    }
    for ( var i = 0; i < str.atomicProcess.length; i+=1) {
	OWLModel.create("AtomicProcess", str.atomicProcess[i].ID);
    }
    for ( var i = 0; i < str.input.length; i+=1) {
	OWLModel.create("Input", str.input[i].ID);
    }
    for ( var i = 0; i < str.output.length; i+=1) {
	OWLModel.create("Output", str.output[i].ID);
    }
    for ( var i = 0; i < str.inputBinding.length; i+=1) {
	OWLModel.create("InputBinding", str.inputBinding[i].ID);
    }
    for ( var i = 0; i < str.outputBinding.length; i+=1) {
	OWLModel.create("OutputBinding", str.outputBinding[i].ID);
    }
    for ( var i = 0; i < str.inputMessageMap.length; i+=1) {
	OWLModel.create("InputMessageMap", str.inputMessageMap[i].ID);
    }
    for ( var i = 0; i < str.outputMessageMap.length; i+=1) {
	OWLModel.create("OutputMessageMap", str.outputMessageMap[i].ID);
    }
    OWLModel.getCompositeProcess().setId(str.compositeProcess.ID);
    // 第二层的结构
    for ( var i = 0; i < str.anyOrder.length; i+=1) {
	for ( var j = 0; j < str.anyOrder[i].components.length; j+=1) {
	    OWLModel.get(str.anyOrder[i].ID).addComponent(
		    OWLModel.get(str.anyOrder[i].components[j]));
	}
    }
    for ( var i = 0; i < str.choice.length; i+=1) {
	for ( var j = 0; j < str.choice[i].components.length; j+=1) {
	    OWLModel.get(str.choice[i].ID).addComponent(
		    OWLModel.get(str.choice[i].components[j]));
	}
    }
    for ( var i = 0; i < str.sequence.length; i+=1) {
	for ( var j = 0; j < str.sequence[i].components.length; j+=1) {
	    OWLModel.get(str.sequence[i].ID).addComponent(
		    OWLModel.get(str.sequence[i].components[j]));
	}
    }
    for ( var i = 0; i < str.splitJoin.length; i+=1) {
	for ( var j = 0; j < str.splitJoin[i].components.length; j+=1) {
	    OWLModel.get(str.splitJoin[i].ID).addComponent(
		    OWLModel.get(str.splitJoin[i].components[j]));
	}
    }
    for ( var i = 0; i < str.perform.length; i+=1) {
	OWLModel.get(str.perform[i].ID).setProcess(
		OWLModel.get(str.perform[i].process));
	for ( var j = 0; j < str.perform[i].inputBinding.length; j+=1) {
	    OWLModel.get(str.perform[i].ID).addInputBinding(
		    OWLModel.get(str.perform[i].inputBinding[j]));
	}
    }
    for ( var i = 0; i < str.produce.length; i+=1) {
	console.log();
	for ( var j = 0; j < str.produce[i].outputBinding.length; j+=1) {
	    OWLModel.get(str.produce[i].ID).addOutputBinding(
		    OWLModel.get(str.produce[i].outputBinding[j]));
	}
    }
    for ( var i = 0; i < str.atomicProcess.length; i+=1) {
	OWLModel.get(str.atomicProcess[i].ID).setOperation(
		WSDLOperation.valueOf(str.atomicProcess[i].operation));
	for ( var j = 0; j < str.atomicProcess[i].input.length; j+=1) {
	    OWLModel.get(str.atomicProcess[i].ID).addInput(
		    OWLModel.get(str.atomicProcess[i].input[j]));
	}
	for ( var j = 0; j < str.atomicProcess[i].output.length; j+=1) {
	    OWLModel.get(str.atomicProcess[i].ID).addOutput(
		    OWLModel.get(str.atomicProcess[i].output[j]));
	}
	for ( var j = 0; j < str.atomicProcess[i].inputMessageMap.length; j+=1) {
	    OWLModel.get(str.atomicProcess[i].ID).addInputMessageMap(
		    OWLModel.get(str.atomicProcess[i].inputMessageMap[j]));
	}
	for ( var j = 0; j < str.atomicProcess[i].inputMessageMap.length; j+=1) {
	    OWLModel.get(str.atomicProcess[i].ID).addOutputMessageMap(
		    OWLModel.get(str.atomicProcess[i].inputMessageMap[j]));
	}
    }
    for ( var i = 0; i < str.input.length; i+=1) {
	OWLModel.get(str.input[i].ID).setType(str.input[i].type);
    }
    for ( var i = 0; i < str.output.length; i+=1) {
	OWLModel.get(str.output[i].ID).setType(str.output[i].type);
    }
    for ( var i = 0; i < str.inputBinding.length; i+=1) {
	var binding = OWLModel.get(str.inputBinding[i].ID);
	binding.setFromProcess(OWLModel.get(str.inputBinding[i].fromProcess));
	binding.setTheVar(OWLModel.get(str.inputBinding[i].theVar));
	binding.setToParam(OWLModel.get(str.inputBinding[i].toParam));
    }
    for ( var i = 0; i < str.outputBinding.length; i+=1) {
	var binding = OWLModel.get(str.outputBinding[i].ID);
	binding.setFromProcess(OWLModel.get(str.outputBinding[i].fromProcess));
	binding.setTheVar(OWLModel.get(str.outputBinding[i].theVar));
	binding.setToParam(OWLModel.get(str.outputBinding[i].toParam));
    }
    for ( var i = 0; i < str.inputMessageMap.length; i+=1) {
	var map = str.inputMessageMap[i];
	var param = WSDLParameter.valueOf(map.groundingParameter);
	OWLModel.get(map.ID).setGroundingParameter(param);
	OWLModel.get(map.ID).setOwlsParameter(OWLModel.get(map.owlsParameter));
    }
    for ( var i = 0; i < str.outputMessageMap.length; i+=1) {
	var map = str.outputMessageMap[i];
	var param = WSDLParameter.valueOf(map.groundingParameter);
	OWLModel.get(map.ID).setGroundingParameter(param);
	OWLModel.get(map.ID).setOwlsParameter(OWLModel.get(map.owlsParameter));
    }
    // 组合流程
    OWLModel.getCompositeProcess().setComposeOf(
	    OWLModel.get(str.compositeProcess.composeOf));
    for ( var i = 0; i < str.compositeProcess.input.length; i+=1) {
	OWLModel.getCompositeProcess().addInput(
		OWLModel.get(str.compositeProcess.input[i]));
    }
    for ( var i = 0; i < str.compositeProcess.output.length; i+=1) {
	OWLModel.getCompositeProcess().addOutput(
		OWLModel.get(str.compositeProcess.output[i]));
    }
    UpdateProcessView();
    UpdateTreeView();
    Redraw();
}