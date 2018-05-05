function EPCCell(){
	
}
/**
 * @extends {mxCell}
 */
EPCCell.prototype = new mxCell();
EPCCell.prototype.constructor = EPCCell;
/**
 * @type {String}
 */
EPCCell.prototype.description = null;
/**
 * @returns {String}
 */
EPCCell.prototype.getName = function (){
	return this.value;
};
/**
 * @abstract
 * @returns {String}
 */
EPCCell.prototype.getType = function (){

};
/**
 * @returns {Number}
 */
EPCCell.prototype.getX = function (){
	return this.geometry.x;
};
/**
 *@returns {Number}
 */
EPCCell.prototype.getY = function (){
	return this.geometry.y;
};
/**
 *@returns {Number}
 */
EPCCell.prototype.getWidth = function (){
	return this.geometry.width;
};
/**
 *@returns {Number}
 */
EPCCell.prototype.getHeight = function (){
	return this.geometry.height;
};
/**
 *@returns {String}
 */
EPCCell.prototype.getDescription = function (){
	return this.description;
};
/**
 *@returns {Boolean}
 */
EPCCell.prototype.isFontBold = function (){
	// 利用正则找出fontStyle对应的样式再提取出BOLD部分，下同
	var arr = /fontStyle=(\d)/.exec(this.style);
	if(arr != null && arr.length == 2){
		return (arr[1]&1) > 0;
	}
	return false;
};
/**
 *@returns {Boolean}
 */
EPCCell.prototype.isFontItalic = function (){
	var arr = /fontStyle=(\d)/.exec(this.style);
	if(arr != null && arr.length == 2){
		return (arr[1]&2) > 0;
	}
	return false;
};
/**
 *@returns {Boolean}
 */
EPCCell.prototype.isFontUnderline = function (){
	var arr = /fontStyle=(\d)/.exec(this.style);
	if(arr != null && arr.length == 2){
		return (arr[1]&4) > 0;
	}
	return false;
};
/**
 * @param name {String}
 */
EPCCell.prototype.setName = function (name){
	this.value = name;
};
/**
 * @param x {Number}
 */
EPCCell.prototype.setX = function (x){
	if(isNaN(x)){
		this.geometry.x = 0;
	}else{
		this.geometry.x = x;
	}
};
/**
 * @param y {Number}
 */
EPCCell.prototype.setY = function (y){
	if(isNaN(y)){
		this.geometry.y = 0;
	}else{
		this.geometry.y = y;
	}
};
/**
 * @param width {Number}
 */
EPCCell.prototype.setWidth = function (width){
	if(isNaN(width)){
		this.geometry.width = 80;
	}else{
		this.geometry.width = width;
	}
};
/**
 * @param height {Number}
 */
EPCCell.prototype.setHeight = function (height){
	if(isNaN(height)){
		this.geometry.height = 40;
	}else{
		this.geometry.height = height;
	}
};
/**
 * @param description {Number}
 */
EPCCell.prototype.setDescription = function (description){
	this.description = description;
};
/**
 *@param bold {Boolean}
 */
EPCCell.prototype.setFontBold = function (bold){
	// 1、将style字符串拆分成单个的键值对
	// 2、如果找到对应的键值对，则进行逻辑运算设定其值
	// 3、否则新增对应的键值对
	// 4、将键值对重新拼接成字符串
	// 下同
	var arr = this.style.split(";");
	var flag = false;
	for(var i = 0; i < arr.length; i++){
		var arr1 = /fontStyle=(\d)/.exec(arr[i]);
		if(arr1 != null && arr1.length == 2){
			if(bold){
				arr1[1] |= 1;
			}else{
				arr1[1] &= 14;
			}
			arr[i] = "fontStyle=" + arr1[1];
			flag = true;
			break;
		}
	}
	if(!flag){
		arr.push("fontStyle=" + (bold ? 1 : 0));
	}
	this.setStyle(arr.join(";"));
};
/**
 *@param bold {Boolean}
 */
EPCCell.prototype.setFontItalic = function (italic){
	var arr = this.style.split(";");
	var flag = false;
	for(var i = 0; i < arr.length; i++){
		var arr1 = /fontStyle=(\d)/.exec(arr[i]);
		if(arr1 != null && arr1.length == 2){
			if(italic){
				arr1[1] |= 2;
			}else{
				arr1[1] &= 13;
			}
			arr[i] = "fontStyle=" + arr1[1];
			flag = true;
			break;
		}
	}
	if(!flag){
		arr.push("fontStyle=" + (italic ? 2 : 0));
	}
	this.setStyle(arr.join(";"));
};
/**
 *@param bold {Boolean}
 */
EPCCell.prototype.setFontUnderline = function (underline){
	var arr = this.style.split(";");
	var flag = false;
	for(var i = 0; i < arr.length; i++){
		var arr1 = /fontStyle=(\d)/.exec(arr[i]);
		if(arr1 != null && arr1.length == 2){
			if(underline){
				arr1[1] |= 4;
			}else{
				arr1[1] &= 11;
			}
			arr[i] = "fontStyle=" + arr1[1];
			flag = true;
			break;
		}
	}
	if(!flag){
		arr.push("fontStyle=" + (underline ? 4 : 0));
	}
	this.setStyle(arr.join(";"));
};
/**
 * @returns {jQuery}
 */
EPCCell.prototype.getXmlPosition = function (){
	var _position = $("<position/>");
	_position.attr("x",this.getX());
	_position.attr("y",this.getY());
	_position.attr("width",this.getWidth());
	_position.attr("height",this.getHeight());
	return _position;
};
/**
 * @returns {jQuery}
 */
EPCCell.prototype.getXmlFont = function (){
	var _font = $("<font/>");
	this.isFontBold()?_font.attr("weight","bold"):void(0);
	this.isFontItalic()?_font.attr("style","italic"):void(0);
	this.isFontUnderline()?_font.attr("decoration","underline"):void(0);
	return _font;
};

EPCCell.prototype.toHtml = function (){

};