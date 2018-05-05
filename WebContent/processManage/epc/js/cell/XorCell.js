/**
 * @class XorCell
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor XorCell
 * @param {Number}
 *            x
 * @param {Number}
 *            y
 * @param {Number}
 *            w
 * @param {Number}
 *            h
 */
function XorCell(x, y, w, h) {
	mxCell.call(this, "", new mxGeometry(x, y, w, h), XorCell.STYLE)
	this.setVertex(true);
	this.type="xor";
}
/**
 * @extends <mxCell>
 */
XorCell.prototype = new EPCCell();
XorCell.prototype.constructor = XorCell;
/**
 * @override
 * @returns {String}
 */
XorCell.prototype.getType = function() {
	return "xor";
};
/**
 * @override
 * @param width {Number}
 */
XorCell.prototype.setWidth = function (width){
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
XorCell.prototype.setHeight = function (height){
	if(isNaN(height)){
		this.geometry.height = 30;
	}else{
		this.geometry.height = height;
	}
};
/**
 * @returns {jQuery}
 */
XorCell.prototype.toXml = function (){
	var _xml = $("<xor/>").attr("id",this.id);
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()));
	return _xml;
};
XorCell.prototype.changeStyle=function (code){
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
XorCell.STYLE = "shape=xor;" + "perimeter=ellipsePerimeter;"
		+ "strokeColor=#000000;" + "fillColor=#DDDDDD;" + "editable=0;"
		+ "fontColor=#000000;" + "fontSize=13;" + "fontStyle=1;"
		+ "fontFamily=Microsoft YaHei;"
		+ "strokeWidth=2;";