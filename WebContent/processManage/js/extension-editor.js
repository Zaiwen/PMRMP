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
	    if (cell.construct instanceof PerformConstruct) {
		$("#perform-dialog")[0].perform = cell.construct;
		$("#perform-dialog").dialog("open");
	    } else if (cell.construct instanceof IfThenElseConstruct) {
		$("#condition-dialog")[0].construct = cell.construct;
		$("#condition-dialog").dialog("open");
	    } else if (cell.construct instanceof ProduceConstruct) {
		$("#produce-dialog")[0].produce = cell.construct;
		$("#produce-dialog").dialog("open");
	    }
	});
    }
    var vstyle = 'shape=ellipse;' + 'strokeWidth=2;' + 'strokeColor=#0099FF;'
	    + 'fillColor=#0099FF;' + 'fontColor=#FF0000;' + 'fontSize=12;'
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
