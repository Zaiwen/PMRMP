/**
 * @class ExtParameter
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor ExtParameter
 * @param {String}
 *            name
 */
function ExtParameter(name) {
	this.name = name;
}
/**
 * @type {String}
 */
ExtParameter.prototype.name = null;
/**
 * @type {String}
 */
ExtParameter.prototype.binding = null;
/**
 * @returns {String}
 */
ExtParameter.prototype.getName = function() {
	return this.name;
};
/**
 * @param {String}
 *            binding
 */
ExtParameter.prototype.setBidning = function(binding) {
	this.binding = binding;
};
/**
 * @returns {String}
 */
ExtParameter.prototype.getBinding = function() {
	return this.binding;
};