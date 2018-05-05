/**
 * @class EventCell
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor EventCell
 * @param {Number}
 *            x
 * @param {Number}
 *            y
 * @param {Number}
 *            w
 * @param {Number}
 *            h
 * @param {Number}
 *            text
 */
function ObjectCell(x, y, w, h, text) {
	mxCell.call(this, text, new mxGeometry(x, y, w, h), ObjectCell.STYLE);
	this.setVertex(true);
	this.type="object";
}
/**
 * @extends <mxCell>
 */
ObjectCell.prototype = new EPCCell();
ObjectCell.prototype.constructor = ObjectCell;
/**
 * @type {String}
 */
ObjectCell.prototype.semanticType = null;
/**
 * @override
 * @returns {String}
 */
ObjectCell.prototype.getType = function() {
	return "object";
};
/**
 * @returns {String}
 */
ObjectCell.prototype.getSemanticType = function() {
	return this.semanticType;
};
/**
 * @param semanticType {String}
 */
ObjectCell.prototype.setSemanticType = function(semanticType) {
	return this.semanticType = semanticType;
};
/**
 * @override
 * @returns {String}
 */
ObjectCell.prototype.getType = function() {
	return "object";
}
/**
 * @returns {jQuery}
 */
ObjectCell.prototype.toXml = function (){
	var _xml = $("<object/>").attr("id",this.id);
	_xml.append($("<name/>").text(this.value));
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()).append(this.getXmlFont()));
	_xml.append($("<attribute/>").attr("typeRef","semanticType").attr("value",this.semanticType));
	return _xml;
};
ObjectCell.prototype.changeStyle=function (code){
	var stylesheet=graph.getStylesheet();
	var styles=stylesheet.getCellStyle(this.getStyle());
	if(code==0){
		styles[mxConstants.STYLE_STROKECOLOR]="#0099ff";
		styles[mxConstants.STYLE_FILLCOLOR]="transparent";
		styles[mxConstants.STYLE_FONTCOLOR]="#0099ff";
	}else if(code==1){
		styles[mxConstants.STYLE_STROKECOLOR]="#000000";
		styles[mxConstants.STYLE_FILLCOLOR]="#aaeeff";
		styles[mxConstants.STYLE_FONTCOLOR]="#000000";
	}
	var str="";
	for(var i in styles){
		str+=(i+"="+styles[i]+";");
	}
	this.setStyle(str);
};
ObjectCell.STYLE = "shape=rectangle;" +"strokeColor=#000000;"
		+ "fillColor=#AAEEFF;" + "fontColor=#000000;"
		+ "fontSize=13;" + "strokeWidth=2;";