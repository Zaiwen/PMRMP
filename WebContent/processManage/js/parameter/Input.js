/**
 * @class Input
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor Input
 * @param {String}
 *                id
 */
function Input(id) {
    this.id = id;
}
/**
 * @type {String}
 */
Input.prototype.id = null;
/**
 * @returns {String}
 */
Input.prototype.getId = function() {
    return this.id;
};
/**
 * @returns {String}
 */
Input.prototype.getType = function() {
    return this.type;
};
/**
 * @param {String}
 *                id
 * @returns {Boolean}
 */
Input.prototype.setId = function(id) {
    if (OWLModel.changeId(this.id, id)) {
	this.id = id;
	return true;
    } else {
	return false;
    }
};
/**
 * @param {String}
 *                type
 */
Input.prototype.setType = function(type) {
    this.type = type;
};
/**
 * 
 */
Input.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @returns {object}
 */
Input.prototype.toJson = function() {
    return {
	ID : this.id,
	type : this.type
    };
};
/**
 * @override
 * @returns {String}
 */
Input.prototype.toString = function() {
    return this.id;
};