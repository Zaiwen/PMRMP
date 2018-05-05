/**
 * @author APD
 * @Class Condition
 * */
function Condition(predicate, argument1, argument2){
	this.predicate = predicate;
	this.argument1 = argument1;
	this.argument2 = argument2;
}
/**
 * @type String
 * */
Condition.prototype.predicate = null;
/**
 * @type String
 * */
Condition.prototype.predicateType = null;
/**
 * @type String
 * */
Condition.prototype.argument1 = null;
/**
 * @type String
 * */
Condition.prototype.argument1Type = null;
/**
 * @type String
 * */
Condition.prototype.argument2 = null;
/**
 * @type String
 * */
Condition.prototype.argument2Type = null;
/**
 * @returns {String}
 * */
Condition.prototype.getPredicate = function (){
	return this.predicate;
};
/**
 * @returns {String}
 * */
Condition.prototype.getPredicateType = function (){
	return this.predicateType;
};
/**
 * @returns {String}
 * */
Condition.prototype.getArgument1 = function (){
	return this.argument1;
};
/**
 * @returns {String}
 * */
Condition.prototype.getArgument1Type = function (){
	return this.argument1Type;
};
/**
 * @returns {String}
 * */
Condition.prototype.getArgument2 = function (){
	return this.argument2;
};
/**
 * @returns {String}
 * */
Condition.prototype.getArgument2Type = function (){
	return this.argument2Type;
};
/**
 * @param predicate {String}
 * */
Condition.prototype.setPredicate = function (predicate){
	this.predicate=predicate;
};
/**
 * @param predicateType {String}
 * */
Condition.prototype.setPredicateType = function (predicateType){
	this.predicateType=predicateType;
};
/**
 * @param argument1 {String}
 * */
Condition.prototype.setArgument1 = function (argument1){
	this.argument1 = argument1;
};
/**
 * @param argument1Type {String}
 * */
Condition.prototype.setArgument1Type = function (argument1Type){
	this.argument1Type = argument1Type;
};
/**
 * @param argument2 {String}
 * */
Condition.prototype.setArgument2 = function (argument2){
	this.argument2 = argument2;
};
/**
 * @param argument2Type {String}
 * */
Condition.prototype.setArgument2Type = function (argument2Type){
	this.argument2Type = argument2Type;
};
/**
 * @returns {String}
 * */
Condition.prototype.toString = function (){
	if(this.predicate == null){
		return "true";
	}else if(this.argument1 == null){
		return this.predicate + "()";
	}else if(this.argument2 == null){
		return this.predicate + "(" + this.argument1 +")";
	}else {
		return this.predicate + "(" + this.argument1 + "," +this.argument2 + ")";
	}
};
/**
 * @param str {String}
 * @returns {Condition}
 * */
Condition.prototype.valueOf = function (str){
	
};
/**
 * 
 * */
Condition.prototype.toJson = function (){
	var obj = {};
	obj.predicate = this.predicate;
	obj.predicateType = this.predicateType;
	obj.arguments = [];
	obj.arguments[0]={name : this.argument1, type : this.argument1Type};
	obj.arguments[1]={name : this.argument2, type : this.argument2Type};
	return obj;
};
/**
 * @param obj {JSON}
 * @returns {Condition} 
 */
Condition.fromJson = function (obj){
	var condition = new Condition();
	condition.predicate = obj.predicate;
	condition.predicateType = obj.predicateType;
	if(obj.arguments[0]){
		condition.argument1 = obj.arguments[0].name;
		condition.argument1Type = obj.arguments[0].type;
	}
	if(obj.arguments[1]){
		condition.argument2 = obj.arguments[1].name;
		condition.argument2Type = obj.arguments[1].type;
	}
	return condition;
}