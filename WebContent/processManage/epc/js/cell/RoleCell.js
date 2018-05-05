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
function RoleCell(x, y, w, h, text) {
	mxCell.call(this, text, new mxGeometry(x, y, w, h), RoleCell.STYLE);
	this.setVertex(true);
	this.type="role";
}
/**
 * @extends <mxCell>
 */
RoleCell.prototype = new EPCCell();
RoleCell.prototype.constructor = RoleCell;
/**
 * @override
 * @returns {String}
 */
RoleCell.prototype.getType = function() {
	return "role";
};
RoleCell.prototype.changeStyle=function (code){
	var stylesheet=graph.getStylesheet();
	var styles=stylesheet.getCellStyle(this.getStyle());
	if(code==0){
		styles[mxConstants.STYLE_STROKECOLOR]="#0099ff";
		styles[mxConstants.STYLE_FILLCOLOR]="transparent";
		styles[mxConstants.STYLE_FONTCOLOR]="#0099ff";
	}else if(code==1){
		styles[mxConstants.STYLE_STROKECOLOR]="#000000";
		styles[mxConstants.STYLE_FILLCOLOR]="#ffff66";
		styles[mxConstants.STYLE_FONTCOLOR]="#000000";
	}
	var str="";
	for(var i in styles){
		str+=(i+"="+styles[i]+";");
	}
	this.setStyle(str);
};
/**
 * @returns {jQuery}
 */
RoleCell.prototype.toXml = function (){
	var _xml = $("<role/>").attr("id",this.id);
	_xml.append($("<name/>").text(this.value));
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()).append(this.getXmlFont()));
	return _xml;
};
RoleCell.STYLE = "shape=role;" +"strokeColor=#000000;"
		+ "fillColor=#FFFF66;" + "fontColor=#000000;"
		+ "fontSize=13;" + "strokeWidth=2;";