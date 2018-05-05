/**
 * @class OutputMessageMap
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor OutputMessageMap
 * @param {String}
 *                id
 */
function OutputMessageMap(id) {
    this.id = id;
}
/**
 * @type {WSDLParameter}
 */
OutputMessageMap.prototype.groundingParameter = null;
/**
 * @type {Output}
 */
OutputMessageMap.prototype.owlsParameter = null;
/**
 * @returns {String}
 */
OutputMessageMap.prototype.getId = function() {
    return this.id;
};
/**
 * @returns {WSDLParameter}
 */
OutputMessageMap.prototype.getGroundingParameter = function() {
    return this.groundingParameter;
};
/**
 * @returns {Output}
 */
OutputMessageMap.prototype.getOwlsParameter = function() {
    return this.owlsParameter;
};
/**
 * 完全删除该实例
 */
OutputMessageMap.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @param {WSDLParameter}
 *                param
 */
OutputMessageMap.prototype.setGroundingParameter = function(param) {
    this.groundingParameter = param;
};
/**
 * @param {Output}
 *                param
 */
OutputMessageMap.prototype.setOwlsParameter = function(param) {
    this.owlsParameter = param;
};
/**
 * @returns {Object}
 */
OutputMessageMap.prototype.toJson = function() {
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
OutputMessageMap.prototype.toString = function() {
    return this.id;
};