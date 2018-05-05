/**
 * If-Then-Else 控制结构
 * 
 * @class IfThenElseConstruct
 * @constructor IfThenElseConstruct
 * @param {String} id
 */
function IfThenElseConstruct(id) {
    this.id=id;
}
/**
 * 初始化
 * 
 * @param _json
 *                <JSON>
 */
IfThenElseConstruct.prototype.init = function(_json) {
    this.id = _json.metadata.itemId;
    this.name = "Condition_" + this.id;
    if (!!_json.children) {
	if (_json.children[0] != null) {
	    this.thenConstruct = Json2Construct(_json.children[0]);
	}
	if (_json.children[1] != null) {
	    this.elseConstruct = Json2Construct(_json.children[1]);
	}
    }
    this.id=_json.metadata.itemId;
    this.name="IfThenElse_"+this.id;
};

IfThenElseConstruct.prototype.getName=function (){
    return this.name;
};
/**
/**
 * @private <mxCell> 终止节点
 */
IfThenElseConstruct.prototype.endVertex = null;
/**
 * @private <mxCell> 起始节点
 */
IfThenElseConstruct.prototype.startVertex = null;

IfThenElseConstruct.prototype.expressionLanguage = "DRS";

IfThenElseConstruct.prototype.expressionBody = null;
/**
 * @private <ControlConstruct> 当ifCondition取true时转入的控制结构
 */
IfThenElseConstruct.prototype.thenConstruct = null;
/**
 * @private <ControlConstruct> 当ifCondition取false时转入的控制结构
 */
IfThenElseConstruct.prototype.elseConstruct = null;
/**
 * 将该控制结构显示在画布上
 * 
 * @param graph
 *                <mxGraph> 画布
 * @param x
 *                <Number> x坐标
 * @param y
 *                <number> y坐标
 */
IfThenElseConstruct.prototype.draw = function(graph, x, y) {
    var parent = graph.getDefaultParent();

    var v1, v2, v3, v4;

    var x1, x2, x3, x4;

    var y1, y2, y3, y4;

    var w1, w2, w3, w4;

    var h1, h2, h3, h4;

    var w, h;

    var style, style1, style2;

    w = this.getWidth();

    h = this.getHeight();

    w1 = this.getStartVertexWidth();

    h1 = this.getStartVertexHeight();

    w4 = this.getEndVertexWidth();

    h4 = this.getEndVertexHeight();

    style = 'fontFamily=Microsoft YaHei;' + 'fontSize=12;'
	    + 'fontColor=#0099FF;' + 'strokeWidth=2;' + 'strokeColor=#0099FF;'
	    + 'shape=rhombus;' + 'perimeter=rhombusPerimeter;' + 'editable=0;'
	    + 'movable=0;' + 'resizable=0;';

    style1 = style + 'fillColor=#FFFFFF;';

    style2 = style + 'fillColor=#0099FF;';

    // 画出起始节点

    x1 = x + (w - w1) / 2, y1 = y;
    this.startVertex = graph.insertVertex(parent, null, this.name, x1, y1, w1,
	    h1, style1);
    this.startVertex.type = "If-Then-Else";
    this.startVertex.itemId = this.id;
    if (this.thenConstruct != null) {
	// 画出第一个分支节点

	w2 = this.thenConstruct.getWidth();

	h2 = this.thenConstruct.getHeight();

	x2 = x;

	if (w1 == w) {
	    if (this.elseConstruct == null) {
		x2 += (w - w2) / 2;
	    } else {
		w3 = this.elseConstruct.getWidth();

		x2 += (w - w2 - w3 - 40) / 2;
	    }
	}

	y2 = y + h1 + (h - h1 - h2 - h4) / 2;

	this.thenConstruct.draw(graph, x2, y2);

	v1 = this.thenConstruct.getStartVertex();

	v2 = this.thenConstruct.getEndVertex();

	graph.insertEdge(parent, null, 'true', this.startVertex, v1);

	// 画出第二个分支节点

	if (this.elseConstruct != null) {
	    w3 = this.elseConstruct.getWidth();

	    h3 = this.elseConstruct.getHeight();

	    x3 = x2 + w2 + 40;

	    y3 = y + h1 + (h - h1 - h3 - h4) / 2;

	    this.elseConstruct.draw(graph, x3, y3);

	    v3 = this.elseConstruct.getStartVertex();

	    v4 = this.elseConstruct.getEndVertex();

	    graph.insertEdge(parent, null, 'false', this.startVertex, v3);
	}
    }

    // 画出终止节点

    x4 = x + (w - w4) / 2;

    y4 = y + h - h4;

    this.endVertex = graph.insertVertex(parent, null, null, x4, y4, w4, h4,
	    style2);

    if (this.thenConstruct == null) {
	graph.insertEdge(parent, null, null, this.startVertex, this.endVertex);
    } else {
	graph.insertEdge(parent, null, null, v2, this.endVertex);

	if (this.elseConstruct != null) {
	    graph.insertEdge(parent, null, null, v4, this.endVertex);
	}
    }
};
/**
 * @return <String> 该控制结构的名称
 */
IfThenElseConstruct.prototype.getConstructName = function() {
    return "If-Then-Else";
};
/**
 * @return <ControlContruct>
 */
IfThenElseConstruct.prototype.getElseConstruct = function() {
    return this.elseConstruct;
};
/**
 * @return <mxCell> 终止节点
 */
IfThenElseConstruct.prototype.getEndVertex = function() {
    return this.endVertex;
};
/**
 * @return <Number> 终止节点的高度
 */
IfThenElseConstruct.prototype.getEndVertexHeight = function() {
    return 40;
};
/**
 * @return <Number> 终止节点的宽度
 */
IfThenElseConstruct.prototype.getEndVertexWidth = function() {
    return 40;
};
/**
 * @return <Number> 高度
 */
IfThenElseConstruct.prototype.getHeight = function() {
    if (this.thenConstruct == null) {
	return this.getStartVertexHeight() + 40 + this.getEndVertexHeight();
    } else if (this.elseConstruct == null) {
	return this.getStartVertexHeight() + 40
		+ this.thenConstruct.getHeight() + 40
		+ this.getEndVertexHeight();
    } else {
	return this.getStartVertexHeight()
		+ 60
		+ Math.max(this.thenConstruct.getHeight(), this.elseConstruct
			.getHeight()) + 60 + this.getEndVertexHeight();
    }
};
/**
 * @return <mxCell> 起始节点
 */
IfThenElseConstruct.prototype.getStartVertex = function() {
    return this.startVertex;
};
/**
 * @return <Number> 起始节点的高度
 */
IfThenElseConstruct.prototype.getStartVertexHeight = function() {
    return 60;
};
/**
 * @return <Number> 起始节点的宽度
 */
IfThenElseConstruct.prototype.getStartVertexWidth = function() {
    var div = $('<div>').text(this.name);

    div.css({
	"display" : "inline",
	"font-size" : "12px",
	"font-family" : "Microsoft Yahei",
	"position" : "absolute",
	"visibility" : "hidden"
    });

    var w = div.appendTo($('body')).width() * 1.5;

    div.remove();

    return w > 60 ? w : 60;
};
/**
 * @return <ControlContruct>
 */
IfThenElseConstruct.prototype.getThenConstruct = function() {
    return this.thenConstruct;
};
/**
 * @return <Number> 宽度
 */
IfThenElseConstruct.prototype.getWidth = function() {
    var w1 = this.getStartVertexWidth();

    if (this.thenConstruct == null) {
	return w1;
    } else {
	var w2 = this.thenConstruct.getWidth();

	if (this.elseConstruct != null) {
	    w2 += this.elseConstruct.getWidth() + 40;
	}
	return w1 > w2 ? w1 : w2;
    }
};

IfThenElseConstruct.prototype.setName = function(name) {
    this.name = name;
};
IfThenElseConstruct.prototype.getName = function() {
    return this.name;
};
