/**
 * @class Perform
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @construct Perform
 * @param {String}
 *            id
 */
function Perform(id) {
	this.id = id;
	this.inputBinding = new Object();
};
/**
 * @type {Boolean}
 */
Perform.prototype.extended = false;
/**
 * @type {Boolean}
 */
Perform.prototype.queryResult = false;
/**
 * @type {Binding[]}
 */
Perform.prototype.inputBinding = null;
/**
 * <mxCell>将该控制结构显示在画布上时对应的节点
 */
Perform.prototype.vertex = null;
/**
 * 当属性toParam不相同时添加，相同时替换掉原有的
 * 
 * @param {InputBinding}
 *            binding
 */
Perform.prototype.addInputBinding = function(binding) {
	this.inputBinding[binding + ""] = binding;
};
/**
 * 彻底删除该控制结构
 */
Perform.prototype.remove = function() {
	for ( var i in this.inputBinding) {
		this.inputBinding[i].remove();
	}
	OWLModel.remove(this + "");
};
/**
 * @param {InputBinding}
 *            inputBinding
 */
Perform.prototype.removeInputBinding = function(binding) {
	binding.remove();
	delete this.inputBinding[binding + ""];
};
/**
 * 将该控制结构画出来
 * 
 * @param graph
 *            <mxGraph> 绘图面板
 * @param x
 *            <Number> x坐标
 * @param y
 *            <Number> y坐标
 */
Perform.prototype.draw = function(graph, x, y) {
	var parent = graph.getDefaultParent();
	var color = "#0099FF;";
	this.queryResult ? color = "#333333;" : void (0);
	this.extended ? color = "#FF0000;" : void (0);
	var style = 'fontFamily=Microsoft YaHei;' + 'fontSize=12;' + 'fontColor='
			+ color + 'strokeColor=' + color + 'strokeWidth=2;'
			+ 'fillColor=#FFFFFF;' + 'editable=0;' + 'movable=0;'
			+ 'resizable=0;';
	this.vertex = graph.insertVertex(parent, null, this.id, x, y, this
			.getWidth(), this.getHeight(), style);
	this.vertex.construct = this;
};
/**
 * @return <mxCell> 该控制结构对应的终止节点
 */
Perform.prototype.getEndVertex = function() {
	return this.vertex;
};
/**
 * 获取Perform控制结构的高度
 */
Perform.prototype.getHeight = function() {
	return 30;
};
/**
 * @return <mxCell> 该控制结构对应的起始节点
 */
Perform.prototype.getStartVertex = function() {
	return this.vertex;
};
/**
 * @returns {String}
 */
Perform.prototype.getId = function() {
	return this.id;
};
/**
 * 获取Perform控制结构的宽度
 */
Perform.prototype.getWidth = function() {
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
	return Math.max(width + 20, 60);
};
/**
 * @returns {Map<Input>}
 */
Perform.prototype.getInput = function() {
	return this.process == null ? new Object() : this.process.getInput();
};
/**
 * @returns {Map<Output>}
 */
Perform.prototype.getOutput = function() {
	return this.process == null ? new Object() : this.process.getOutput();
};
/**
 * @returns {AtomicProcess}
 */
Perform.prototype.getProcess = function() {
	return this.process;
};
/**
 * @returns {Map<InputBinding>}
 */
Perform.prototype.getInputBinding = function() {
	return this.inputBinding;
};
/**
 * @return {Boolean}
 */
Perform.prototype.isExtended = function() {
	return this.extended;
};
/**
 * @returns {Boolean}
 */
Perform.prototype.isQueryResult = function() {
	return this.queryResult;
};
/**
 * 在进行基于变量的语义检索时，检查该结构是否匹配已有的语义标注
 * 
 * @param {String[]}
 *            inputs
 * @param {String[]}
 *            outputs
 * @returns {Boolean}
 */
Perform.prototype.matches = function(inputs, outputs) {
	for ( var i = 0; i < inputs.length; i++) {
		var flag = false;
		for ( var j in this.getInput()) {
			var input = this.getInput()[j];
			if (input.getType() == inputs[i]) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			return false;
		}
	}
	for ( var i = 0; i < outputs.length; i++) {
		var flag = false;
		for ( var j in this.getOutput()) {
			var output = this.getOutput()[j];
			if (output.getType() == outputs[i]) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			return false;
		}
	}
	return true;
};
/**
 * @param {Boolean}
 */
Perform.prototype.setExtended = function(extended) {
	this.extended = extended;
};
/**
 * 设置Perform控制结构要显示的文本
 * 
 * @param {String}
 *            id
 * @returns {Boolean}
 */
Perform.prototype.setId = function(id) {
	if (OWLModel.changeId(this.id, id)) {
		this.id = id;
		return true;
	} else {
		return false;
	}
};
/**
 * @param {Process}
 *            process
 */
Perform.prototype.setProcess = function(process) {
	this.process = process;
	for ( var i in this.inputBinding) {
		this.removeInputBinding(this.inputBinding[i]);
	}
};
/**
 * @param {Boolean}
 *            value
 */
Perform.prototype.setQueryResult = function(value) {
	this.queryResult = value;
};
/**
 * @returns {Object}
 */
Perform.prototype.toJson = function() {
	var obj = {};
	obj.ID = this.id;
	obj.process = this.process + "";
	obj.inputBinding = [];
	for ( var i in this.inputBinding) {
		obj.inputBinding.push(this.inputBinding[i] + "");
	}
	return obj;
};
/**
 * @override
 * @returns {String}
 */
Perform.prototype.toString = function() {
	return this.id;
};