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
function EventCell(x, y, w, h, text) {
	mxCell.call(this, text, new mxGeometry(x, y, w, h), EventCell.STYLE);
	this.setVertex(true);
	this.type="event";
}
/**
 * @extends <mxCell>
 */
EventCell.prototype = new EPCCell();
EventCell.prototype.constructor = EventCell;
/**
 * @override
 * @returns {String}
 */
EventCell.prototype.getType = function (){
	return "event";
};
/**
 * @returns {jQuery}
 */
EventCell.prototype.toXml = function (){
	var _xml = $("<event/>").attr("id",this.id);
	_xml.append($("<name/>").text(this.value));
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()).append(this.getXmlFont()));
	return _xml;
};
EventCell.prototype.changeStyle=function (code){
	var stylesheet=graph.getStylesheet();
	var styles=stylesheet.getCellStyle(this.getStyle());
	if(code==0){
		styles[mxConstants.STYLE_STROKECOLOR]="#0099ff";
		styles[mxConstants.STYLE_FILLCOLOR]="transparent";
		styles[mxConstants.STYLE_FONTCOLOR]="#0099ff";
	}else if(code==1){
		styles[mxConstants.STYLE_STROKECOLOR]="#000000";
		styles[mxConstants.STYLE_FILLCOLOR]="#ff0000";
		styles[mxConstants.STYLE_FONTCOLOR]="#000000";
	}
	var str="";
	for(var i in styles){
		str+=(i+"="+styles[i]+";");
	}
	this.setStyle(str);
};
EventCell.STYLE = "shape=event;" +"strokeColor=#000000;"
		+ "fillColor=#FF0000;" + "fontColor=#000000;"
		+ "fontSize=13;" + "strokeWidth=2;";