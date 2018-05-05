/**
 * @class AtomicProcess
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor AtomicProcess
 * @param {String}
 *                id
 * @param {WSDLOperation}
 *                operation
 */
function AtomicProcess(id, operation) {
    this.id = id;
    this.operation = operation;
    this.input = new Object();
    this.output = new Object();
    this.inputMessageMap = new Object();
    this.outputMessageMap = new Object();
    this.precondition = new Array();
    this.postcondition = new Array();
}
/**
 * @type {String}
 */
AtomicProcess.prototype.id = null;
/**
 * @type {Map<Input>}
 */
AtomicProcess.prototype.input = null;
/**
 * @type {Map<Output>}
 */
AtomicProcess.prototype.output = null;
/**
 * @type [Condition]
 * */
AtomicProcess.prototype.precondition = null;
/**
 * @type [Condition]
 * */
AtomicProcess.prototype.postcondition = null;
/**
 * @type {Input}
 */
AtomicProcess.prototype.addInput = function(input) {
    this.input[input + ""] = input;
};
/**
 * @type {Output}
 */
AtomicProcess.prototype.addOutput = function(output) {
    this.output[output + ""] = output;
};
/**
 * @param condition {Condition}
 * @returns {Boolean}
 * */
AtomicProcess.prototype.addPrecondition = function (condition){
	/*for(var i = 0; i < this.precondition.length; i++){
		if(condition.toString() == this.precondition[i].toString()){
			return false;
		}
	}*/
	this.precondition[this.precondition.length] = condition;
	return true;
};
/**
 * @param condition {Condition}
 * @returns {Boolean}
 * */
AtomicProcess.prototype.addPostcondition = function (condition){
	/*for(var i = 0; i < this.postcondition.length; i++){
		if(condition.toString() == this.postcondition[i].toString()){
			return false;
		}
	}*/
	this.postcondition[this.postcondition.length] = condition;
	return true;
};
/**
 * @param {InputMessageMap}
 *                map
 */
AtomicProcess.prototype.addInputMessageMap = function(map) {
    this.inputMessageMap[map + ""] = map;
};
/**
 * /**
 * 
 * @param {OutputMessageMap}
 *                map
 */
AtomicProcess.prototype.addOutputMessageMap = function(map) {
    this.outputMessageMap[map + ""] = map;
};
/**
 * @returns [Condition]
 * */
AtomicProcess.prototype.getPrecondition = function (){
	return this.precondition;
};
/**
 * @returns [Condition]
 * */
AtomicProcess.prototype.getPostcondition = function (){
	return this.postcondition;
};
/**
 * @returns {WSDLOperation}
 */
AtomicProcess.prototype.getOperation = function() {
    return this.operation;
};
/**
 * @returns {String}
 */
AtomicProcess.prototype.getId = function() {
    return this.id;
};
/**
 * @param {String}
 *                id
 * @returns {Boolean}
 */
AtomicProcess.prototype.setId = function(id) {
    if (OWLModel.changeId(this.id, id)) {
	this.id = id;
	return true;
    } else {
	return false;
    }
};
/**
 * @returns {Map<Input>}
 */
AtomicProcess.prototype.getInput = function() {
    return this.input;
};
/**
 * @returns {Number}
 * */
AtomicProcess.prototype.getInputCount = function (){
	var count = 0;
	for(var i in this.input){
		count ++;
	}
	return count;
};
/**
 * @returns {Map<Output>}
 */
AtomicProcess.prototype.getOutput = function() {
    return this.output;
};
/**
 * @returns {Number}
 * */
AtomicProcess.prototype.getOutputCount = function (){
	var count = 0;
	for(var i in this.output){
		count ++;
	}
	return count;
};
/**
 * 
 */
AtomicProcess.prototype.remove = function() {
    for ( var i in this.input) {
	this.input[i].remove();
    }
    for ( var i in this.output) {
	this.output[i].remove();
    }
    for ( var i in this.inputMessageMap) {
	this.inputMessageMap[i].remove();
    }
    for ( var i in this.outputMessageMap) {
	this.outputMessageMap[i].remove();
    }
    OWLModel.remove(this + "");
};
/**
 * @param i {Number}
 * @returns {Boolean}
 * */
AtomicProcess.prototype.removePrecondition = function (i){
	if(i > -1 && i < this.precondition.length){
		for(var j = i; j < this.precondition.length - 1; j++){
			this.precondition[j] = this.precondition[j+1];
		}
		if(this.precondition.length > 0){
			this.precondition.length--;
		}
		return true;
	}else{
		return false;
	}
};
/**
 * @param i {Number}
 * @returns {Boolean}
 * */
AtomicProcess.prototype.removePostcondition = function (i){
	if(i > -1 && i < this.postcondition.length){
		for(var j = i; j < this.postcondition.length - 1; j++){
			this.postcondition[j] = this.postcondition[j+1];
		}
		if(this.postcondition.length > 0){
			this.postcondition.length--;
		}
		return true;
	}else{
		return false;
	}
};
/**
 * @param {Input}
 *                input
 */
AtomicProcess.prototype.removeInput = function(input) {
    delete this.input[input + ""];
};
/**
 * @param {Output}
 *                output
 */
AtomicProcess.prototype.removeOutput = function(output) {
    delete this.output[output + ""];
};
/**
 * @param {WSDLOperation}
 *                operation
 */
AtomicProcess.prototype.setOperation = function(operation) {
    this.operation = operation;
    for ( var i in this.input) {
	this.input[i].remove();
    }
    for ( var i in this.output) {
	this.output[i].remove();
    }
    for ( var i in this.inputMessageMap) {
	this.inputMessageMap[i].remove();
    }
    for ( var i in this.outputMessageMap) {
	this.outputMessageMap[i].remove();
    }
};
/**
 * @returns {Object}
 */
AtomicProcess.prototype.toJson = function() {
    var obj = new Object();
    obj.ID = this.id;
    obj.portType = this.operation.getPortName();
    obj.operation = this.operation.getOperationName();
    obj.inputMessageName = this.operation.getInputMessageName();
    obj.outputMessageName = this.operation.getOutputMessageName();
    obj.input = new Array();
    obj.output = new Array();
    obj.inputMessageMap = new Array();
    obj.outputMessageMap = new Array();
    for ( var i in this.input) {
	obj.input.push(i + "");
    }
    for ( var i in this.output) {
	obj.output.push(i + "");
    }
    for ( var i in this.inputMessageMap) {
	obj.inputMessageMap.push(i + "");
    }
    for ( var i in this.outputMessageMap) {
	obj.outputMessageMap.push(i + "");
    }
    obj.conditionList = {connector:1,conditions:[{atoms:[]}]}
    for(var i = 0; i < this.precondition.length; i++){
    	obj.conditionList.conditions[0].atoms.push(this.precondition[i].toJson())
    }
    obj.results = {connector:1, effects : [{atoms:[]}]};
    for(var i = 0; i < this.postcondition.length; i++){
    	obj.results.effects[0].atoms.push(this.postcondition[i].toJson())
    }
    return obj;
};
/**
 * @override
 * @returns {String}
 */
AtomicProcess.prototype.toString = function() {
    return this.id;
};