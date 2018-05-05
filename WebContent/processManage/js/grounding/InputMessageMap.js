/**
 * @class InputMessageMap
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor InputMessageMap
 * @param {String}
 *                id
 */
function InputMessageMap(id) {
    this.id = id;
}
/**
 * @type {WSDLParameter}
 */
InputMessageMap.prototype.groundingParameter = null;
/**
 * @type {ProcessInput}
 */
InputMessageMap.prototype.owlsParameter = null;
/**
 * @returns {String}
 */
InputMessageMap.prototype.getId = function() {
    return this.id;
};
/**
 * @returns {WSDLParameter}
 */
InputMessageMap.prototype.getGroundingParameter = function() {
    return this.groundingParameter;
};
/**
 * @returns {ProcessInput}
 */
InputMessageMap.prototype.getOwlsParameter = function() {
    return this.owlsParameter;
};
/**
 * 完全删除该实例
 */
InputMessageMap.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @param {WSDLParameter}
 *                param
 */
InputMessageMap.prototype.setGroundingParameter = function(param) {
    this.groundingParameter = param;
};
/**
 * @param {ProcessInput}
 *                param
 */
InputMessageMap.prototype.setOwlsParameter = function(param) {
    this.owlsParameter = param;
};
/**
 * @returns {Object}
 */
InputMessageMap.prototype.toJson = function() {
    return {
	ID : this.id,
	groundingParameter : this.groundingParameter.getWsdlLocation() + "#"
		+ this.groundingParameter.getName(),
	owlsParameter : this.owlsParameter + ""
    };
};
/**
 * @returns {String}
 */
InputMessageMap.prototype.toString = function() {
    return this.id;
};