/**
 * @class WSDLOperation
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor WSDLOperation
 * @param {WSDLService}
 *            service
 */
function WSDLOperation(wsdlLocation) {
	this.wsdlLocation = wsdlLocation;
	this.inputs = new Array();
	this.outputs = new Array();
}
/**
 * @type {String}
 */
WSDLOperation.prototype.name = null;
/**
 * @type {String}
 */
WSDLOperation.prototype.operationName = null;
/**
 * @type {String}
 */
WSDLOperation.prototype.portName = null;
/**
 * @type {WSDLParameter[]}
 */
WSDLOperation.prototype.inputs = null;
/**
 * @type {WSDLParameter[]}
 */
WSDLOperation.prototype.outputs = null;
/**
 * @type {String}
 */
WSDLOperation.prototype.inputMessageName = null;
/**
 * @type {String}
 */
WSDLOperation.prototype.outputMessageName = null;
/**
 * @type {String}
 */
WSDLOperation.prototype.wsdlLocation = null;
/**
 * @param {String}
 *            name
 * @param {String}
 *            type
 */
WSDLOperation.prototype.addInput = function(name, type) {
	var input = new WSDLParameter(this.wsdlLocation);
	input.setName(name);
	input.setType(QName.valueOf(type));
	this.inputs.push(input);
};
/**
 * @param {String}
 *            name
 * @param {String}
 *            type
 */
WSDLOperation.prototype.addOutput = function(name, type) {
	var output = new WSDLParameter(this.wsdlLocation);
	output.setName(name);
	output.setType(QName.valueOf(type));
	this.outputs.push(output);
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getName = function() {
	return this.name;
};
/**
 * @param {String}
 *            name
 * @returns {WSDLParameter}
 */
WSDLOperation.prototype.getParameter = function(name) {
	for ( var i = 0; i < this.inputs.length; i++) {
		if (this.inputs[i].getName() == name) {
			return this.inputs[i];
		}
	}
	for ( var i = 0; i < this.outputs.length; i++) {
		if (this.outputs[i].getName() == name) {
			return this.outputs[i];
		}
	}
	return null;
};
/**
 * @param {Number|String}
 *            i
 * @returns {WSDLParameter}
 */
WSDLOperation.prototype.getInput = function(i) {
	if (this.inputs == null) {
		return null;
	} else if (typeof i == 'number') {
		return this.inputs[i];
	} else {
		for ( var k = 0; k < this.inputs.length; k++) {
			var param = this.inputs[k];
			if (i == param.getName()) {
				return param;
			}
		}
		return null;
	}
};
/**
 * @returns {WSDLParameter[]}
 */
WSDLOperation.prototype.getInputs = function() {
	return this.inputs;
};
/**
 * @param {Number|String}
 *            i
 * @returns {WSDLParameter}
 */
WSDLOperation.prototype.getOutput = function(i) {
	if (this.outputs == null) {
		return null;
	} else if (typeof i == 'number') {
		return this.outputs[i];
	} else {
		for ( var k = 0; k < this.outputs.length; k++) {
			var param = this.outputs[k];
			if (i == param.getName()) {
				return param;
			}
		}
		return null;
	}
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getInputMessageName = function() {
	return this.inputMessageName;
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getOutputMessageName = function() {
	return this.outputMessageName;
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getPortName = function() {
	return this.portName;
};
/**
 * @returns {WSDLParameter[]}
 */
WSDLOperation.prototype.getOutputs = function() {
	return this.outputs;
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getWsdlLocation = function() {
	return this.wsdlLocation;
};
/**
 * @returns {String}
 */
WSDLOperation.prototype.getOperationName = function() {
	return this.operationName;
};
/**
 * @param {String}
 *            name
 */
WSDLOperation.prototype.setName = function(name) {
	this.name = name;
	this.defName = name;
};
/**
 * @param {String}
 *            name
 */
WSDLOperation.prototype.setInputMessageName = function(name) {
	this.inputMessageName = name;
};
/**
 * @param {String}
 *            name
 */
WSDLOperation.prototype.setOutputMessageName = function(name) {
	this.outputMessageName = name;
};
/**
 * @param {String}
 *            name
 */
WSDLOperation.prototype.setOperationName = function(name) {
	this.operationName = name;
};
/**
 * @param {String}
 *            name
 */
WSDLOperation.prototype.setPortName = function(name) {
	this.portName = name;
};
/**
 * @returns {Object}
 */
WSDLOperation.prototype.toJson = function() {
	var obj = {
		inputs : [],
		output : [],
	};
	for ( var i = 0; i < this.inputs.length; i++) {
		obj.inputs.push(this.inputs[i].toJson());
	}
	for ( var i = 0; i < this.outputs.length; i++) {
		obj.outputs.push(this.outputs[i].toJson());
	}

};
/**
 * @returns {String}
 */
WSDLOperation.prototype.toString = function() {
	return this.getService().getURI() + "#" + this.name;
};
/**
 * @static
 * @param {String}
 *            opName
 */
WSDLOperation.valueOf = function(opName) {
	if(opName != null){
		var arr = opName.split("#");
		var name = arr[arr.length - 1];
		/*
		var serv = WSDLService.createService(opName.substring(0, opName.length
				- name.length - 1));
		return serv.getOperation(name);
		*/
		var operation = new WSDLOperation(arr[0]);
		operation.setName(arr[1]);
		return operation;
	}
	return new WSDLOperation();
};