/**
 * @class AndCell
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor AndCell
 * @param {Number}
 *            x
 * @param {Number}
 *            y
 * @param {Number}
 *            w
 * @param {Number}
 *            h
 */
function AndCell(x, y, w, h) {
	mxCell.call(this, "", new mxGeometry(x, y, w, h), AndCell.STYLE)
	this.setVertex(true);
	this.type="and";
}
/**
 * @extends <mxCell>
 */
AndCell.prototype = new EPCCell();
AndCell.prototype.constructor = AndCell;
/**
 * @override
 * @returns {String}
 */
AndCell.prototype.getType = function (){
	return "and";
};
/**
 * @override
 * @param width {Number}
 */
AndCell.prototype.setWidth = function (width){
	if(isNaN(width)){
		this.geometry.width = 30;
	}else{
		this.geometry.width = width;
	}
};
/**
 * @override
 * @param height {Number}
 */
AndCell.prototype.setHeight = function (height){
	if(isNaN(height)){
		this.geometry.height = 30;
	}else{
		this.geometry.height = height;
	}
};
/**
 * @returns {jQuery}
 */
AndCell.prototype.toXml = function (){
	var _xml = $("<and/>").attr("id",this.id);
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()));
	return _xml;
};

AndCell.prototype.changeStyle=function (code){
	var stylesheet=graph.getStylesheet();
	var styles=stylesheet.getCellStyle(this.getStyle());
	if(code==0){
		styles[mxConstants.STYLE_STROKECOLOR]="#0099ff";
		styles[mxConstants.STYLE_FILLCOLOR]="transparent";
	}else if(code==1){
		styles[mxConstants.STYLE_STROKECOLOR]="#000000";
		styles[mxConstants.STYLE_FILLCOLOR]="#dddddd";
	}
	var str="";
	for(var i in styles){
		str+=(i+"="+styles[i]+";");
	}
	console.log(str);
	this.setStyle(str);
};
/**
 *
 */
AndCell.STYLE = "shape=and;" + "perimeter=ellipsePerimeter;"
		+ "strokeColor=#000000;" + "fillColor=#DDDDDD;" + "editable=0;"
		+ "fontColor=#000000;" + "fontSize=20;"
		+ "strokeWidth=2;";