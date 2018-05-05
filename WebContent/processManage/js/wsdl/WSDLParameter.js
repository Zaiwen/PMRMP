/**
 * @class WSDLParameter
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor WSDLParameter
 * @param {String}
 *            wsdlLocation
 */
function WSDLParameter(wsdlLocation) {
	this.wsdlLocation = wsdlLocation;
}
/**
 * @type {String}
 */
WSDLParameter.prototype.name = null;
/**
 * @type {QName}
 */
WSDLParameter.prototype.type = null;
/**
 * @returns {String}
 */
WSDLParameter.prototype.getName = function() {
	return this.name;
};
/**
 * @returns {QName}
 */
WSDLParameter.prototype.getType = function() {
	return this.type;
};
/**
 * @returns {String}
 */
WSDLParameter.prototype.getWsdlLocation = function() {
	return this.wsdlLocation;
};
/**
 * @param {String}
 *            name
 */
WSDLParameter.prototype.setName = function(name) {
	this.name = name;
};
/**
 * @param {QName}
 *            type
 */
WSDLParameter.prototype.setType = function(type) {
	this.type = type;
};
/**
 * @returns {Object}
 */
WSDLParameter.prototype.toJson = function() {
	return {
		name : this.name,
		type : this.type == null ? null : this.type.toJson()
	};
};
/**
 * @returns {String}
 */
WSDLParameter.prototype.toString = function() {
	return this.name;
};
/**
 * @static
 * @param {String}
 *            str
 * @returns {WSDLParameter}
 */
WSDLParameter.valueOf = function(str) {
	if(str != null){
		var arr = str.split("#");
		var name = arr[arr.length - 1];
		/**
		var serv = WSDLService.createService(str.substring(0, str.length
				- name.length - 1));
		return serv.getParameter(name);
		*/
		
		var param = new WSDLParameter(arr[0]);
		param.setName(arr[1]);
		return param;
	}else{
		return new WSDLParameter();
	}
};