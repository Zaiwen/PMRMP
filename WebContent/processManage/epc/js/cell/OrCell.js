/**
 * @class OrCell
 * @author 艾培东 (aipeidong@vip.qq.com)
 * @constructor OrCell
 * @param {Number}
 *            x
 * @param {Number}
 *            y
 * @param {Number}
 *            w
 * @param {Number}
 *            h
 */
function OrCell(x, y, w, h) {
	mxCell.call(this, "", new mxGeometry(x, y, w, h), OrCell.STYLE)
	this.setVertex(true);
	this.type="or";
}
/**
 * @extends <mxCell>
 */
OrCell.prototype = new EPCCell();
OrCell.prototype.constructor = OrCell;
/**
 * @override
 * @returns {String}
 */
OrCell.prototype.getType = function() {
	return "or";
};
/**
 * @override
 * @param width {Number}
 */
OrCell.prototype.setWidth = function (width){
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
OrCell.prototype.setHeight = function (height){
	if(isNaN(height)){
		this.geometry.height = 30;
	}else{
		this.geometry.height = height;
	}
};
/**
 * @returns {jQuery}
 */
OrCell.prototype.toXml = function (){
	var _xml = $("<or/>").attr("id",this.id);
	_xml.append($("<description/>").text(this.description));
	_xml.append($("<graphics/>").append(this.getXmlPosition()));
	return _xml;
};
OrCell.prototype.changeStyle=function (code){
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
OrCell.STYLE = "shape=or;" + "perimeter=ellipsePerimeter;"
		+ "strokeColor=#000000;" + "fillColor=#DDDDDD;" + "editable=0;"
		+ "fontColor=#000000;" + "fontSize=20;" + "fontFamily=Microsoft YaHei;"
		+ "strokeWidth=2;";