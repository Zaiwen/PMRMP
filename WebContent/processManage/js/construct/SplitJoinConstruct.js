/**
 * Split-Join 控制结构
 * 
 * @class SplitJoinConstruct
 * @constructor SplitJoinConstruct
 * @param {String}
 *                id
 */
function SplitJoinConstruct(id) {
    this.id = id;
    this.components = [];
}
/**
 * @returns {String} 
 */
SplitJoinConstruct.prototype.getId = function() {
    return this.id;
};
/**
 * @private <Array> 该控制结构包含的子结构
 */
SplitJoinConstruct.prototype.components = null;
/**
 * @private <mxCell> 该控制结构在画布上的终止节点
 */
SplitJoinConstruct.prototype.endVertex = null;
/**
 * @private <mxCell> 该控制结构在画布上的起始节点
 */
SplitJoinConstruct.prototype.startVertex = null;
/**
 * 添加控制结构
 * 
 * @param construct
 *                控制结构
 */
SplitJoinConstruct.prototype.addComponent = function(component) {
    this.components.push(component);
};
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
SplitJoinConstruct.prototype.draw = function(graph, x, y) {
    var parent = graph.getDefaultParent();
    var style = 'fontFamily=Microsoft YaHei;' + 'fontSize=12;'
	    + 'fontColor=#FFFFFF;' + 'strokeWidth=2;' + 'strokeColor=#0099FF;'
	    + 'fillColor=#0099FF;' + 'editable=0;' + 'movable=0;'
	    + 'resizable=0;' + 'shape=ellipse;' + 'perimeter=ellipsePerimeter;';
    var x1, x2, y1, y2, w1, w2, h1, h2;
    // 画出起始节点
    x1 = x + (this.getWidth() - this.getStartVertexWidth()) / 2;
    y1 = y;
    w1 = this.getStartVertexWidth();
    h1 = this.getStartVertexHeight();
    this.startVertex = graph.insertVertex(parent, null, 'Split-Join', x1, y1,
	    w1, h1, style);
    // 画出终止节点
    x2 = x1;
    y2 = y + this.getHeight() - this.getEndVertexHeight();
    w2 = this.getEndVertexWidth();
    h2 = this.getEndVertexHeight();
    this.endVertex = graph.insertVertex(parent, null, '/Split-Join', x2, y2,
	    w2, h2, style);
    // 画出子控制结构
    if (this.components.length == 0) {
	graph.insertEdge(parent, null, null, this.startVertex, this.endVertex);
    } else {
	var _x = x, _y;
	if (this.getWidth() == w1) {
	    _x += (this.getWidth() - this.getComponentsWidth()) / 2;
	}
	for ( var i = 0; i < this.components.length; i++) {
	    _y = y + (this.getHeight() - this.components[i].getHeight()) / 2;
	    this.components[i].draw(graph, _x, _y);
	    _x += (this.components[i].getWidth() + 20);
	    var v1 = this.components[i].getStartVertex();
	    var v2 = this.components[i].getEndVertex();
	    graph.insertEdge(parent, null, null, this.getStartVertex(), v1);
	    graph.insertEdge(parent, null, null, v2, this.getEndVertex());
	}
    }
};
/**
 * 所有子控制结构的高度
 */
SplitJoinConstruct.prototype.getComponentsHeight = function() {
    var max = 0;
    for ( var i = 0; i < this.components.length; i++) {
	var h = this.components[i].getHeight();
	max = max < h ? h : max;
    }
    return max;
};
/**
 * @return <Number> 所有子控制结构的宽度
 */
SplitJoinConstruct.prototype.getComponentsWidth = function() {
    if (this.components.length == 0) {
	return 0;
    } else {
	var width = (this.components.length - 1) * 20;
	for ( var i = 0; i < this.components.length; i++) {
	    width += this.components[i].getWidth();
	}
	return width;
    }
};
/**
 * 
 */
SplitJoinConstruct.prototype.getComponents=function (){
    return this.components;
};
/**
 * @return <mxCell> 终止节点
 */
SplitJoinConstruct.prototype.getEndVertex = function() {
    return this.endVertex;
};
/**
 * @return <Number> 终止节点的高度
 */
SplitJoinConstruct.prototype.getEndVertexHeight = function() {
    return 40;
};
/**
 * @return <Number> 终止节点的宽度
 */
SplitJoinConstruct.prototype.getEndVertexWidth = function() {
    return 80;
};
/**
 * @return <Number> 该控制结构的高度
 */
SplitJoinConstruct.prototype.getHeight = function() {
    if (this.components.length == 0) {
	return this.getStartVertexHeight() + 40 + this.getEndVertexHeight();
    } else {
	return this.getStartVertexHeight() + 60 + this.getComponentsHeight()
		+ 60 + this.getEndVertexHeight();
    }
};
/**
 * @return <mxCell> 起始节点
 */
SplitJoinConstruct.prototype.getStartVertex = function() {
    return this.startVertex;
};
/**
 * @return <Number> 起始节点的高度
 */
SplitJoinConstruct.prototype.getStartVertexHeight = function() {
    return 40;
};
/**
 * @return <Number> 起始节点的宽度
 */
SplitJoinConstruct.prototype.getStartVertexWidth = function() {
    return 80;
};
/**
 * @return <Number> 该控制结构的宽度
 */
SplitJoinConstruct.prototype.getWidth = function() {
    var width = Math.max(this.getStartVertexWidth(), this.getEndVertexWidth());
    if (this.components.length == 0) {
	return width;
    } else {
	return Math.max(this.getComponentsWidth(), width);
    }
};
/**
 * 彻底删除该控制结构
 */
SplitJoinConstruct.prototype.remove=function (){
    for(var i=0;i<this.components.length;i++){
	this.components[i].remove();
    }
    OWLModel.remove(this.getId());
};
/**
 * @param {ControlConstruct}
 */
SplitJoinConstruct.prototype.removeComponent=function (component){
    var index=-1;
    for(var i=0;i<this.components.length;i++){
	if(this.components[i]==component){
	    index=i;
	    break;
	}
    }
    if(index!=-1){
	this.components[index].remove();
	for(var i=index;i<this.components.length-1;i++){
	    this.components[i]=this.components[i+1];
	}
	this.components.length--;
	return true;
    }else{
	return false;
    }
};
/**
 * @returns {Object}
 */
SplitJoinConstruct.prototype.toJson = function() {
    var obj = {};
    obj.ID = this.id;
    obj.components = [];
    for ( var i = 0; i < this.components.length; i++) {
	obj.components.push(this.components[i]+"");
    }
    return obj;
};
/**
 * @returns {String}
 */
SplitJoinConstruct.prototype.toString=function (){
    return this.id;
};