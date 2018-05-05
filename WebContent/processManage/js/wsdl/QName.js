/**
 * @class QName
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor QName
 * @param {String}
 *            localPart
 * @param {String}
 *            prefix
 * @param {String}
 *            [namespaceURI]
 */
function QName(localPart, prefix, namespaceURI) {
	if (localPart == null) {
		throw new Error('QName local part can not be empty');
	} else if (prefix == null) {
		throw new Error('QName prefix can not be empty');
	} else {
		this.localPart = $.trim(localPart);
		this.prefix = $.trim(prefix);
		this.namespaceURI = namespaceURI == null ? '' : $.trim(namespaceURI);
	}
}
/**
 * @type {String}
 */
QName.prototype.localPart = null;
/**
 * @type {String}
 */
QName.prototype.prefix = null;
/**
 * @type {String}
 */
QName.prototype.namespaceURI = null;
/**
 * @param {QName}
 *            qname
 * @returns {Boolean}
 */
QName.prototype.equals = function(qname) {
	if (qname == null) {
		return false;
	} else if (qname == this) {
		return true;
	} else if (!(qname instanceof QName)) {
		return false;
	} else if (this.namespaceURI != qname.namespaceURI) {
		return false;
	} else {
		return this.localPart == qname.localPart;
	}
};
/**
 * @returns {String}
 */
QName.prototype.getLocalPart = function() {
	return this.localPart;
};
/**
 * @returns {String}
 */
QName.prototype.getPrefix = function() {
	return this.prefix;
};
/**
 * @returns {String}
 */
QName.prototype.getNamespaceURI = function() {
	return this.namespaceURI;
};
/**
 * @returns {String}
 */
QName.prototype.getQualifiedName = function() {
	if (this.namespaceURI == '') {
		return this.localPart;
	} else {
		return this.prefix + ':' + this.localPart;
	}
};
/**
 * @returns {Object}
 */
QName.prototype.toJson = function() {
	return {
		namespaceURI : this.namespaceURI,
		localPart : this.localPart
	};
};
/**
 * @returns {String}
 */
QName.prototype.toString = function() {
	// if (this.namespaceURI == '') {
	// return this.localPart;
	// } else {
	// return '{' + this.namespaceURI + '}' + this.localPart;
	// }
	return this.namespaceURI + "#" + this.localPart;
};

/**
 * @static
 * @param {String}
 *            str
 * @returns {QName}
 */
QName.valueOf = function(str) {
	str = $.trim(str);
	if (str == null || str == '') {
		throw new Error('Invalid QName literal');
	} else if (str.charAt(0) == '{') {
		var i = str.indexOf('}');
		if (i == -1) {
			throw new Error('Invalid QName literal');
		} else if (i == str.length - 1) {
			throw new Error('Invalid QName literal');
		} else {
			return new QName(str.substr(i + 1), '', str.substring(1, i));
		}
	} else {
		return new QName(str, '');
	}
};
