/**
 * @class CompositeProcess
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor CompositeProcess
 * @param {String}
 *                id
 */
function CompositeProcess(id) {
    this.id = id;
    this.input = new Object();
    this.output = new Object();
}
/**
 * @type {String}
 */
CompositeProcess.prototype.id = null;
/**
 * @type {Input[]}
 */
CompositeProcess.prototype.input = null;
/**
 * @type {Output[]}
 */
CompositeProcess.prototype.output = null;
/**
 * 一种控制结构
 */
CompositeProcess.prototype.composeOf = null;
/**
 * @param {Input}
 *                input
 */
CompositeProcess.prototype.addInput = function(input) {
    this.input[input + ""] = input;
};
/**
 * @param {Output}
 *                output
 */
CompositeProcess.prototype.addOutput = function(output) {
    this.output[output + ""] = output;
};
/**
 * @returns {String}
 */
CompositeProcess.prototype.getId = function() {
    return this.id;
};
/**
 * @returns {Map<Input>}
 */
CompositeProcess.prototype.getInput = function() {
    return this.input;
};
/**
 * @returns {Map<Output>}
 */
CompositeProcess.prototype.getOutput = function() {
    return this.output;
};
/**
 * @returns 控制结构
 */
CompositeProcess.prototype.getComposeOf = function() {
    return this.composeOf;
};
/**
 * @param {Input|String}
 *                input
 * @returns {Boolean}
 */
CompositeProcess.prototype.removeInput = function(input) {
    delete this.input[input + ""];
    OWLModel.remove(input + "");
};
/**
 * @param {Output|String}
 *                output
 * @returns {Boolean}
 */
CompositeProcess.prototype.removeOutput = function(output) {
    delete this.output[output + ""];
    OWLModel.remove(output + "");
};
/**
 * @param construct
 *                任意一种控制结构
 */
CompositeProcess.prototype.setComposeOf = function(construct) {
    this.composeOf = construct;
};
/**
 * @param {String}
 *                id
 * @returns {Boolean}
 */
CompositeProcess.prototype.setId = function(id) {
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
CompositeProcess.prototype.toJson = function() {
    var obj = new Object();
    obj.ID = this.id;
    obj.composeOf = this.composeOf + "";
    obj.input = new Array();
    obj.output = new Array();
    for ( var i in this.input) {
	obj.input.push(i);
    }
    for ( var i in this.output) {
	obj.output.push(i);
    }
    return obj;
};
/**
 * @override
 * @returns {String}
 */
CompositeProcess.prototype.toString = function() {
    return this.id;
};