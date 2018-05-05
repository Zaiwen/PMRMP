/**
 * @class Output
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor Output
 * @param {String}
 *                id
 */
function Output(id) {
    this.id = id;
}
/**
 * @type {Stirng}
 */
Output.prototype.id=null;
/**
 * @returns {String}
 */
Output.prototype.getId = function() {
    return this.id;
};
/**
 * @returns {String}
 */
Output.prototype.getType = function() {
    return this.type;
};
/**
 * @param {String}
 *                id
 * @returns {Boolean}
 */
Output.prototype.setId = function(id) {
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
Output.prototype.setType = function(type) {
    this.type = type;
};
/**
 * 
 */
Output.prototype.remove = function() {
    OWLModel.remove(this.id);
};
/**
 * @returns {object}
 */
Output.prototype.toJson = function() {
    return {
	ID : this.id,
	type : this.type
    };
};
/**
 * @override
 * @returns {String}
 */
Output.prototype.toString = function() {
    return this.id;
};