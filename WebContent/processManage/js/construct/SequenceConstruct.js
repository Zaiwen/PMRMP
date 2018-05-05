/**
 * Sequence控制结构
 * 
 * @class SequenceConstruct
 * @constructor SequenceConstruct
 * @param {String}
 *                id
 */
function SequenceConstruct(id) {
    this.id = id;
    this.components=[];
}
/**
 * @returns {String}
 */
SequenceConstruct.prototype.getId = function() {
    return this.id;
};
/**
 * @private <Array> 该控制结构包含的子结构
 */
SequenceConstruct.prototype.components = null;
/**
 * 添加子控制结构
 * 
 * @param component
 *                <ControlConstruct> 子控制结构
 */
SequenceConstruct.prototype.addComponent = function(component) {
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
SequenceConstruct.prototype.draw = function(graph, x, y) {
    var parent = graph.getDefaultParent();
    if (this.components.length == 0) {
	style = 'fontFamily=Microsoft YaHei;' + 'fontSize=12;'
		+ 'fontColor=#0099FF;' + 'strokeWidth=2;'
		+ 'strokeColor=#0099FF;' + 'fillColor=#0099FF;'
		+ 'shape=ellipse;' + 'perimeter=ellipsePerimeter;'
		+ 'editable=0;' + 'movable=0;' + 'resizable=0;';

	this.vertex = graph.insertVertex(parent, null, null, x, y, 20, 20,
		style);
    } else {
	var _x, _y = y;
	for ( var i = 0; i < this.components.length; i++) {
	    _x = x + (this.getWidth() - this.components[i].getWidth()) / 2;
	    this.components[i].draw(graph, _x, _y);
	    _y += (40 + this.components[i].getHeight());
	}
	for ( var i = 0; i < this.components.length - 1; i++) {
	    var v1 = this.components[i].getEndVertex();
	    var v2 = this.components[i + 1].getStartVertex();
	    graph.insertEdge(parent, null, null, v1, v2);
	}
    }
};
/**
 * 
 */
SequenceConstruct.prototype.getComponents=function (){
    return this.components;
};
/**
 * @return <mxCell> 该控制结构在画布上对应的终止节点
 */
SequenceConstruct.prototype.getEndVertex = function() {
    if (this.components.length == 0) {
	return this.vertex;
    } else {
	return this.components[this.components.length - 1].getEndVertex();
    }
};
/**
 * @return <Number> 该控制结构的高度
 */
SequenceConstruct.prototype.getHeight = function() {
    if (this.components.length == 0) {
	return 20;
    } else {
	var height = (this.components.length - 1) * 40;
	for ( var i = 0; i < this.components.length; i++) {
	    height += this.components[i].getHeight();
	}
	return height;
    }
};
/**
 * @return <mxCell> 该控制结构在画布上对应的起始节点
 */
SequenceConstruct.prototype.getStartVertex = function() {
    if (this.components.length == 0) {
	return this.vertex;
    } else {
	return this.components[0].getStartVertex();
    }
};
/**
 * @return <Number> 该控制结构的宽度
 */
SequenceConstruct.prototype.getWidth = function() {
    var max = 20;
    for ( var i = 0; i < this.components.length; i++) {
	var w = this.components[i].getWidth();
	max = max < w ? w : max;
    }
    return max;
};
/**
 * @return <Boolean> 该控制结构是否不包含任何子结构
 */
SequenceConstruct.prototype.isEmpty = function() {
    return this.components.length == 0;
};
/**
 * 彻底删除该控制结构
 */
SequenceConstruct.prototype.remove=function (){
    for(var i=0;i<this.components.length;i++){
	this.components[i].remove();
    }
    OWLModel.remove(this.getId());
};
/**
 * @param {ControlConstruct}
 */
SequenceConstruct.prototype.removeComponent=function (component){
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
SequenceConstruct.prototype.toJson = function() {
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
SequenceConstruct.prototype.toString=function (){
    return this.id;
};