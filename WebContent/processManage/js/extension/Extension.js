/**
 * @class Extension
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor Extension
 */
function Extension() {

}
/**
 * @private
 * @static
 * @type {String}
 */
Extension.name = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.domain = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.introduction = null;
/**
 * @private
 * @static
 * @type {String[]}
 */
Extension.queryInput = new Array();
/**
 * @private
 * @static
 * @type {String[]}
 */
Extension.queryOutput = new Array();
/**
 * @private
 * @static
 * @type {String[]}
 */
Extension.sparqldl = new Array();
/**
 * @private
 * @static
 * @type {String}
 *       prefix|preposition|postfix|postposition|substitution|synchronization
 */
Extension.pattern = null;
/**
 * @private
 * @static
 * @type {String[]}
 */
Extension.input = new Array();
/**
 * @private
 * @static
 * @type {String[]}
 */
Extension.output = new Array();
/**
 * @private
 * @static
 * @type {String}
 */
Extension.wsdlLocation = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.operation = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.portType = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.inputMessageName = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.outputMessageName = null;
/**
 * @private
 * @static
 * @type {String}
 */
Extension.complete = false;
/**
 * @param {String}
 *            name
 */
Extension.setName = function(name) {
	Extension.name = name;
};
/**
 * @returns {String}
 */
Extension.getName = function() {
	return Extension.name;
};
/**
 * @type {String} domain
 */
Extension.setDomain = function(domain) {
	Extension.domain = domain;
};
/**
 * @returns {String}
 */
Extension.getDomain = function() {
	return Extension.domain;
};
/**
 * @param {String}
 *            introduction
 */
Extension.setIntroduction = function(introduction) {
	Extension.introduction = introduction;
}
/**
 * @returns {String}
 */
Extension.getIntroduction = function() {
	return Extension.introduction;
};
/**
 * @param {String}
 *            input
 * @returns {Boolean}
 */
Extension.addQueryInput = function(input) {
	var flag = false;
	for ( var i = 0; i < Extension.queryInput.length; i += 1) {
		if (Extension.queryInput[i] == input) {
			flag = true;
			break;
		}
	}
	if (!flag) {
		Extension.queryInput.push(input);
		return true;
	} else {
		return false;
	}
};
/**
 * @param {String}
 *            input
 * @returns {Boolean}
 */
Extension.removeQueryInput = function(input) {
	var index = -1;
	for ( var i = 0; i < Extension.queryInput.length; i += 1) {
		if (Extension.queryInput[i] == input) {
			index = i;
			break;
		}
	}
	if (index != -1) {
		for ( var i = index; i < Extension.queryInput.length - 1; i += 1) {
			Extension.queryInput[i] = Extension.queryInput[i + 1];
		}
		Extension.queryInput.length -= 1;
		return true;
	} else {
		return false;
	}
};
/**
 * @param {String}
 *            output
 * @returns {Boolean}
 */
Extension.addQueryOutput = function(output) {
	var flag = false;
	for ( var i = 0; i < Extension.queryOutput.length; i += 1) {
		if (Extension.queryOutput[i] == output) {
			flag = true;
			break;
		}
	}
	if (!flag) {
		Extension.queryOutput.push(output);
		return true;
	} else {
		return false;
	}
};
/**
 * @param {String}
 *            output
 * @returns {Boolean}
 */
Extension.removeQueryOutput = function(output) {
	var index = -1;
	for ( var i = 0; i < Extension.queryOutput.length; i += 1) {
		if (Extension.queryOutput[i] == output) {
			index = i;
			break;
		}
	}
	if (index != -1) {
		for ( var i = index; i < Extension.queryOutput.length - 1; i += 1) {
			Extension.queryOutput[i] = Extension.queryOutput[i + 1];
		}
		Extension.queryOutput.length -= 1;
		return true;
	} else {
		return false;
	}
};
/**
 * @returns {String[]}
 */
Extension.getQueryInput = function() {
	return Extension.queryInput;
};
/**
 * @returns {String[]}
 */
Extension.getQueryOutput = function() {
	return Extension.queryOutput;
};
Extension.toJson = function() {

};

Extension.fromJson = function() {

};
