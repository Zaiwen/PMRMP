/**
 * @class ProduceConstruct
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor ProduceConstruct
 * @param {String}
 *                id
 */
function ProduceConstruct(id) {
    this.id = id;
    this.outputBinding = new Object();
}
/**
 * @type {Boolean}
 */
ProduceConstruct.prototype.extended = false;
/**
 * @type {Map<OutputBinding>}
 */
ProduceConstruct.prototype.outputBinding = null;
/**
 * 存在则替换，不存在则添加
 * 
 * @param {OutputBinding}
 *                binding
 */
ProduceConstruct.prototype.addOutputBinding = function(binding) {
    this.outputBinding[binding + ""] = binding;
};
/**
 * 彻底删除该控制结构
 */
ProduceConstruct.prototype.remove = function() {
    for ( var i in this.outputBinding) {
	this.outputBinding[i].remove();
    }
    OWLModel.remove(this + "");
};
/**
 * @param {OutputBinding}
 *                outputBinding
 */
ProduceConstruct.prototype.removeOutputBinding = function(binding) {
    binding.remove();
    delete this.outputBinding[binding + ""];
};
/**
 * @returns {Map<OutputBinding>}
 */
ProduceConstruct.prototype.getOutputBinding = function() {
    return this.outputBinding;
};
/**
 * <mxCell>将该控制结构显示在画布上时对应的节点
 */
ProduceConstruct.prototype.vertex = null;
/**
 * 将该控制结构显示在画布上
 * 
 * @param graph
 *                <mxGraph> 绘图面板
 * @param x
 *                <Number> x坐标
 * @param y
 *                <Number> y坐标
 */
ProduceConstruct.prototype.draw = function(graph, x, y) {
    var color = this.extended ? "#FF0000;" : "#0099FF;";
    var parent = graph.getDefaultParent();
    var style = 'fontFamily=Microsoft YaHei;' + 'fontSize=12;' + 'fontColor='
	    + color + 'strokeWidth=2;' + 'strokeColor=' + color
	    + 'fillColor=#FFFFFF;' + 'editable=0;' + 'movable=0;'
	    + 'resizable=0;' + 'shape=ellipse;' + 'perimeter=ellipsePerimeter;';
    this.vertex = graph.insertVertex(parent, null, this.id, x, y, this
	    .getWidth(), this.getHeight(), style);
    this.vertex.construct = this;
};
/**
 * @return <mxCell> 该控制结构对应的终止节点
 */
ProduceConstruct.prototype.getEndVertex = function() {
    return this.vertex;
};
/**
 * @return <Number> 该控制结构的高度
 */
ProduceConstruct.prototype.getHeight = function() {
    return 40;
};
/**
 * @override
 * @return <mxCell> 该控制结构对应的起始节点
 */
ProduceConstruct.prototype.getStartVertex = function() {
    return this.vertex;
};
/**
 * @return {String}
 */
ProduceConstruct.prototype.getId = function() {
    return this.id;
};
/**
 * @return <Number> 该控制结构的宽度
 */
ProduceConstruct.prototype.getWidth = function() {
    var div = $('<div>');
    div.css({
	"display" : "inline",
	"font-size" : "12px",
	"font-family" : "Microsoft Yahei",
	"position" : "absolute",
	"visibility" : "hidden"
    });
    var width = div.text(this.id).appendTo($('body')).width();
    div.remove();
    return Math.max(width + 40, 60);
};
/**
 * @return {Boolean}
 */
ProduceConstruct.prototype.isExtended = function() {
    return this.extended;
};
/**
 * @param {Boolean}
 *                extended
 */
ProduceConstruct.prototype.setExtended = function(extended) {
    this.extended = extended;
};
/**
 * @param {String}
 *                id
 */
ProduceConstruct.prototype.setId = function(id) {
    if (OWLModel.changeId(this.id, id)) {
	this.id = id;
	return true;
    } else {
	return false;
    }
};
/**
 * @returns {Object}
 */
ProduceConstruct.prototype.toJson = function() {
    var obj = {};
    obj.ID = this.id;
    obj.outputBinding = [];
    for ( var i in this.outputBinding) {
	obj.outputBinding.push(this.outputBinding[i] + "");
    }
    return obj;
};
/**
 * @returns {String}
 */
ProduceConstruct.prototype.toString = function() {
    return this.id;
};