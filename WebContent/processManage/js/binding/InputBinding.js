/**
 * @class InputBinding
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor InputBinding
 * @param {String}
 *                id
 */
function InputBinding(id) {
    this.id = id;
}
/**
 * @type {String}
 */
InputBinding.prototype.id = null;
/**
 * @type {PerformConstruct}
 */
InputBinding.prototype.fromProcess = null;
/**
 * @type {Input}
 */
InputBinding.prototype.toParam = null;
/**
 * @type {Input|Output} 可以是TheParentPerform的Input，也可以是一般Perform的Output
 */
InputBinding.prototype.theVar = null;
/**
 * @returns {String}
 */
InputBinding.prototype.getId = function() {
    return this.id;
};
/**
 * @type {PerformConstruct} perform
 */
InputBinding.prototype.setFromProcess = function(perform) {
    this.fromProcess = perform;
};
/**
 * @returns {PerformConstruct}
 */
InputBinding.prototype.getFromProcess = function() {
    return this.fromProcess;
};
/**
 * @type {Input} param
 */
InputBinding.prototype.setToParam = function(param) {
    this.toParam = param;
};
/**
 * @returns {Input}
 */
InputBinding.prototype.getToParam = function(param) {
    return this.toParam;
};
/**
 * @type {Input|Output} _var
 */
InputBinding.prototype.setTheVar = function(_var) {
    this.theVar = _var;
};
/**
 * @returns {Input}
 */
InputBinding.prototype.getTheVar = function() {
    return this.theVar;
};
/**
 * 删除该实例
 */
InputBinding.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @returns {Object}
 */
InputBinding.prototype.toJson = function() {
    return {
	ID : this.id,
	fromProcess : this.fromProcess + "",
	theVar : this.theVar + "",
	toParam : this.toParam + ""
    };
};
/**
 * @override
 * @returns {String}
 */
InputBinding.prototype.toString = function() {
    return this.id;
};