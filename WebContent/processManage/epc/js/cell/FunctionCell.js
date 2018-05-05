/**
 * @class FunctionCell
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor FunctionCell
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
function FunctionCell(x, y, w, h, text) {
	mxCell.call(this, text, new mxGeometry(x, y, w, h), FunctionCell.STYLE);
	this.setVertex(true);
	this.type="function";
}
/**
 * @extends <mxCell>
 */
FunctionCell.prototype = new EPCCell();
FunctionCell.prototype.constructor = FunctionCell;
/**
 * @override
 * @returns {String}
 */
FunctionCell.prototype.getType = function() {
	return "function";
}
/**
 * @returns {jQuery}
 */
FunctionCell.prototype.toXml = function (){
	var _xml = $("<function/>");
	_xml.attr("id",this.id);
	_xml.append($("<name/>").text(this.value));
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()).append(this.getXmlFont()));
	return _xml;
};
FunctionCell.prototype.changeStyle=function (code){
	var stylesheet=graph.getStylesheet();
	var styles=stylesheet.getCellStyle(this.getStyle());
	if(code==0){
		styles[mxConstants.STYLE_STROKECOLOR]="#0099ff";
		styles[mxConstants.STYLE_FILLCOLOR]="transparent";
		styles[mxConstants.STYLE_FONTCOLOR]="#0099ff";
	}else if(code==1){
		styles[mxConstants.STYLE_STROKECOLOR]="#000000";
		styles[mxConstants.STYLE_FILLCOLOR]="#00ff00";
		styles[mxConstants.STYLE_FONTCOLOR]="#000000";
	}
	var str="";
	for(var i in styles){
		str+=(i+"="+styles[i]+";");
	}
	this.setStyle(str);
};
FunctionCell.STYLE = "shape=function;" +"strokeColor=#000000;"
		+ "fillColor=#00FF00;" + "editable=1;" + "fontColor=#000000;"
		+ "fontSize=13;" + "strokeWidth=2;";