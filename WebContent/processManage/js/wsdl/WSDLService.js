/**
 * @class WSDLService
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor WSDLService
 * @param {String}
 *            wsdlURL
 */
function WSDLService(wsdlURL, callback, error) {
	if (wsdlURL != null) {
		this.uri = wsdlURL;
		this.callback = callback;
		this.error = error;
		this.operations = new Array();
		this.readOperations();
	}
}

WSDLService.prototype.operations = null;
/**
 * @type {Function(URL}
 */
WSDLService.prototype.error = null;
/**
 * @type {Function(WSDLService}
 */
WSDLService.prototype.callback = null;
/**
 * type {String}
 */
WSDLService.prototype.uri = null;
/**
 * @returns {String} 该服务的地址
 */
WSDLService.prototype.getURI = function() {
	return this.uri;
};
/**
 * @returns {WSDLOperation[]} 所有操作
 */
WSDLService.prototype.getOperations = function() {
	return this.operations;
};
/**
 * @param {String}
 *            name 操作的名字
 * @returns {WSDLOperation} 特定的操作
 */
WSDLService.prototype.getOperation = function(name) {
	if (this.operations == null) {
		return null;
	} else {
		for ( var i = 0; i < this.operations.length; i++) {
			var operation = this.operations[i];
			if (operation.getName() == name) {
				return operation;
			}
		}
		return null;
	}
};
/**
 * @param {String}
 *            name
 * @returns {WSDLParameter}
 */
WSDLService.prototype.getParameter = function(name) {
	for ( var i = 0; i < this.operations.length; i++) {
		var op = this.operations[i];
		var param = op.getParameter(name);
		if (param != null) {
			return param;
		}
	}
};
/**
 * @private 解析该服务
 */
WSDLService.prototype.readOperations = function() {
	var service = this;
	var success = function(str) {
		var opArr = JSON.parse(str);
		for ( var i = 0; i < opArr.length; i++) {
			var o = opArr[i];
			var op = new WSDLOperation(service.getURI());
			op.setName(o.name);
			op.setOperationName(o.operationName);
			op.setPortName(o.portName);
			op.setInputMessageName(o.inputMessage);
			op.setOutputMessageName(o.outputMessage);
			for ( var j = 0; j < o.input.length; j++) {
				op.addInput(o.input[j].name, o.input[j].type);
			}
			for ( var j = 0; j < o.output.length; j++) {
				op.addOutput(o.output[j].name, o.output[j].type);
			}
			service.operations.push(op);
		}
		if(typeof service.callback == "function"){
			service.callback(service);
		}
	};
	var error = function() {
		if(typeof this.error == "function"){
			service.error(service.uri);
		}
		throw new Error('Error when get WSDL:' + service.getURI());
	};
	$.ajax( {
		async : true,
		url : "GetWsdlInfo.jsp?" + service.getURI(),
		mimeType : 'text/plain',
		success : success,
		error : error
	});
};
/**
 * @private
 * @type {WSDLService[]}
 */
WSDLService.services = new Array();
/**
 * @static
 * @param {String}
 *            wsdlURL
 * @returns {WSDLService}
 */
WSDLService.createService = function(wsdlURL, success, error) {
	for ( var i = 0; i < WSDLService.services.length; i++) {
		var service = WSDLService.services[i];
		if (wsdlURL == service.getURI()) {
			return service;
		}
	}
	var service = new WSDLService(wsdlURL, success, error);
	WSDLService.services.push(service);
	return service;
};
/**
 * @return {String}
 */
WSDLService.toJson = function() {
	var obj = {};
	for ( var i = 0; i < WSDLService.services.length; i++) {
		var srv = WSDLService.services[i];

		var op = srv.getOperations();
		for ( var j = 0; j < op.length; j++) {

		}
	}
};