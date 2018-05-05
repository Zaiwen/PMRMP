/**
 * @class OutputBinding
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor OutputBinding
 * @param {String}
 *                id
 */
function OutputBinding(id) {
    this.id = id;
}
/**
 * @type {String}
 */
OutputBinding.prototype.id = null;
/**
 * @type {PerformConstruct}
 */
OutputBinding.prototype.fromProcess = null;
/**
 * @type {Output}
 */
OutputBinding.prototype.toParam = null;
/**
 * @type {Output}
 */
OutputBinding.prototype.theVar = null;
/**
 * @returns {String}
 */
OutputBinding.prototype.getId = function() {
    return this.id;
};
/**
 * @type {PerformConstruct} perform
 */
OutputBinding.prototype.setFromProcess = function(perform) {
    this.fromProcess = perform;
};
/**
 * @returns {PerformConstruct}
 */
OutputBinding.prototype.getFromProcess = function() {
    return this.fromProcess;
};
/**
 * @type {Output} param
 */
OutputBinding.prototype.setToParam = function(param) {
    this.toParam = param;
};
/**
 * @returns {Output}
 */
OutputBinding.prototype.getToParam = function(param) {
    return this.toParam;
};
/**
 * @type {Output} _var
 */
OutputBinding.prototype.setTheVar = function(_var) {
    this.theVar = _var;
};
/**
 * @returns {Output}
 */
OutputBinding.prototype.getTheVar = function() {
    return this.theVar;
};
/**
 * 
 */
OutputBinding.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @returns {Object}
 */
OutputBinding.prototype.toJson = function() {
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
OutputBinding.prototype.toString = function() {
    return this.id;
};