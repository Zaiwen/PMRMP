mxGraph.prototype.undoManager = null;

mxGraph.prototype.installUndoManager = function ()
{		
	var graph=this;
	
	var listener = mxUtils.bind(this,function (sender,evt)
	{
		var edit = evt.getProperty('edit');
		graph.undoManager.undoableEditHappened(edit);
	});
	
	var undoHandler = function (sender,evt)
	{
		var changes=evt.getProperty('edit').changes;
		graph.setSelectionCells(graph.getSelectionCellsForChanges(changes));
	}; 
	
	this.undoManager = new mxUndoManager();
	
	this.getModel().addListener(mxEvent.UNDO,listener);
	this.getView().addListener(mxEvent.UNDO,listener);
	
	this.undoManager.addListener(mxEvent.UNDO,undoHandler);
	this.undoManager.addListener(mxEvent.REDO,undoHandler);	
};

mxGraph.prototype.getSelectionVertexes = function ()
{
	var selectionCells = this.getSelectionCells();
	
	var selectionVertexes = new Array();
	
	for(var i = 0;i < selectionCells.length;i++)
	{
		var cell = selectionCells[i];
		
		if(cell.vertex)
		{
			selectionVertexes.push(cell);
		}
	}
	return selectionVertexes;
};

mxGraph.prototype.getSelectionEdges = function ()
{
	var selectionCells = this.getSelectionCells();
	
	var selectionEdges = new Array();
	
	for(var i =0;i < selectionCells.length;i++)
	{
		var cell = selectionCells[i];
		
		if(cell.edge)
		{
			selectionEdges.push(cell);
		}
	}
	return selectionEdges;
};

mxUtils.getElementsByClassName = function (targetClass,ancestorNode)
{
	if(typeof ancestorNode == 'undefined')
	{
		ancestorNode = document;
	}
	
	if(!document.getElementsByClassName)
	{
		var descendantElements = null;
		
		var correspondingElements = new Array();
		
		var i,j;
	
		descendantElements = ancestorNode.getElementsByTagName('*');
		
		for(i = 0;i < descendantElements.length;i++)
		{
			var element = descendantElements[i];
			
			var classNames = element.className.split(' ');
			
			for(j = 0;j < classNames.length;j++)
			{
				if(classNames[j] == targetClass)
				{
					correspondingElements.push(element);
					break;
				}
			}
			
		}
		return correspondingElements;
		
	}
	else
	{
		return ancestorNode.getElementsByClassName(targetClass);
	}
};
var left_collapse = false;

//slider
$(function (){
	var zoom=100;
	$("#footer .slider .left").click(function (){
		if(zoom>20){
			zoom-=20;
			graph.zoomTo(zoom/100);
			$("#footer .slider .value").text(zoom+"%");
			$("#footer .slider .center").css("left",(zoom+20)/2+"px");
		}
	});
	$("#footer .slider .right").click(function (){
		if(zoom<220){
			zoom+=20;
			graph.zoomTo(zoom/100);
			$("#footer .slider .value").text(zoom+"%");
			$("#footer .slider .center").css("left",(zoom+20)/2+"px");
		}
	});
});

$(function (){
	/*$("#shape-window .head .operation").click(function (){
		if($(this).text()=="<<"){
			$("#outline-window").hide();
			$("#shape-window").removeClass("max").addClass("min");
			$("#shape-window").css({"width":"60px"});
			$("#edit-window").css({"left":"60px"});
			$(this).text(">>");
		}else{
			$("#shape-window").removeClass("min").addClass("max");
			$("#shape-window").css({"width":"240px"});
			$("#edit-window").css({"left":"240px"});
			$("#outline-window").show();
			$(this).text("<<");
		}
	});*/
	
	$(document).on("click","#shape-window.max .head .operation",function (){
		$("#outline-window").hide();
		$("#shape-window").removeClass("max").addClass("min");
		$("#shape-window").css({"width":"60px"});
		$("#edit-window").css({"left":"60px"});
		left_collapse = true;
	});
	$(document).on("click","#shape-window.min .head .operation",function (){
		$("#shape-window").removeClass("min").addClass("max");
		$("#shape-window").css({"width":"240px"});
		$("#edit-window").css({"left":"240px"});
		$("#outline-window").show();
		left_collapse = false;
	});
	new mxOutline(graph,$("#outline-window .body")[0]);
});

$(function (){
	$("#operate .create").click(function (){
		$("#operate").hide();
		$("#dialog-cover").hide();
	});
	$("#operate .open").click(function (){
		$("#operate").hide();
		$("#dialog-cover").hide();
		EpcOpenDialog.show();
	});
	$("#operate .import").click(function (){
		$("#operate").hide();
		$("#dialog-cover").hide();
		$("#editor .import").click();
	});
});

/**
 * @author 艾培东
 * @public
 * @class EpcUtils
 * */
function EpcUtils (){}
/**
 * 将字符串中的所有空白都转化为单个空格
 * @public
 * @static
 * @param text {String}
 * @returns {String}
 * */
EpcUtils.formatText = function (text){
	text = $.trim(text + "");
	var arr1 = text.split(/\s/);
	var arr2 = new Array();
	for(var i = 0; i < arr1.length; i++){
		if(arr1[i] != ""){
			arr2.push(arr1[i]);
		}
	}
	return arr2.join(" ");
}
/**
 * 判断输入的字符串是否是全英文的
 * @public
 * @static
 * @param text {String}
 * @returns {Boolean}
 * */
EpcUtils.textIsEnglish = function (text){
	var flag = true;
	text += "";
	for(var i = 0; i < text.length; i++){
		if(text.charCodeAt(i) > 127){
			flag = false;
			break;
		}
	}
	return flag;
};
/**
 * 表示所有Epc图元的抽象类，不可直接实例化
 * @public
 * @abstract
 * @class EpcCell
 * @author 艾培东
 * */
function EpcVertex(x, y, width, height, text, vertexId){
	//console.log(arguments)
	if(arguments.length == 6){
		x = parseInt(x);
		y = parseInt(y);
		width = parseInt(width);
		height = parseInt(height);
		this.vertexId = vertexId;
		text = $.trim(text);
		var style=null;
		if(this instanceof EpcEvent){
			style="strokeColor=#000;" +
				"fillColor=#ff6666;" +
				"fontColor=#000;" +
				"fontSize=14;" +
				"strokeWidth=2;" +
				"shape=event;";
		}else if(this instanceof EpcFunction){
			style="strokeColor=#000;" +
				"fillColor=#66ff66;" +
				"fontColor=#000;" +
				"fontSize=14;" +
				"strokeWidth=2;" +
				"shape=function;";
		}else if(this instanceof EpcAnd){
			style="strokeColor=#000;" +
			"fillColor=#ddd;" +
			"strokeWidth=2;" +
			"shape=and;";
		}else if(this instanceof EpcOr){
			style="strokeColor=#000;" +
			"fillColor=#ddd;" +
			"strokeWidth=2;" +
			"shape=or;";
		}else if(this instanceof EpcXor){
			style="strokeColor=#000;" +
			"fillColor=#ddd;" +
			"strokeWidth=2;" +
			"shape=xor;";
		}else if(this instanceof EpcRole){
			style="strokeColor=#000;" +
			"fillColor=#ffff66;" +
			"fontColor=#000;" +
			"fontSize=14;" +
			"strokeWidth=2;" +
			"shape=role;";
		}else if(this instanceof EpcObject){
			style="strokeColor=#000;" +
			"fillColor=#66ffff;" +
			"fontColor=#000;" +
			"fontSize=14;" +
			"strokeWidth=2;" +
			"shape=object;";
		}
		mxCell.call(this, text, new mxGeometry(x, y, width, height), style);
		this.setVertex(true);
	}
}
/**
 * @extends {mxCell}
 * */
EpcVertex.prototype = new mxCell();
EpcVertex.prototype.constructor = EpcVertex;
/**
 * @private
 * @type {Number}
 * */
EpcVertex.prototype.vertexId = 0;
/**
 * @returns {Number}
 * */
EpcVertex.prototype.getX = function (){
	return this.geometry.x;
}
/**
 * @returns {Number}
 * */
EpcVertex.prototype.getY = function (){
	return this.geometry.y;
}
/**
 * @returns {Number}
 * */
EpcVertex.prototype.getWidth = function (){
	return this.geometry.width;
}
/**
 * @returns {Number}
 * */
EpcVertex.prototype.getHeight = function (){
	return this.geometry.height;
}
/**
 * @public
 * @returns {Number}
 * */
EpcVertex.prototype.getVertexId = function (){
	return this.vertexId;
}
/**
 * @param x {Number}
 * */
EpcVertex.prototype.setX = function (x){
	this.geometry.x = parseInt(x);
}
/**
 * @param y {Number}
 * */
EpcVertex.prototype.setY = function (y){
	this.geometry.y = parseInt(y);
}
/**
 * @param width {Number}
 * */
EpcVertex.prototype.setX = function (width){
	this.geometry.width = parseInt(width);
}
/**
 * @param height {Number}
 * */
EpcVertex.prototype.setHeight = function (height){
	this.geometry.height = parseInt(height);
}
/**
 * @public
 * @param vertexId {Number}
 * */
EpcVertex.prototype.setVertexId = function (vertexId){
	this.vertexId = vertexId;
}
/**
 * @abstract
 * @param xmlDocument {XMLDocument}
 * @returns {DOMElement}
 * */
EpcVertex.prototype.toXml = function (xmlDocument){}
/**
 * @private
 * @static
 * @type {[EpcVertex]}
 * */
EpcVertex.items = new Object();
/**
 * @public
 * @static 
 * @param item {EpcVertex}
 * @param vertexId {Number} -optional
 * @returns {Number} 节点ID，无论是否作为参数传入
 * */
EpcVertex.addItem = function (item, vertexId){
	if(vertexId == null){
		var i = 0;
		while(true){
			if(EpcVertex.items[i] == null){
				vertexId = i;
				break;
			}else{
				i++;
			}
		}
	}else{
		if(EpcVertex.items[vertexId] != null){
			throw new Error("重复的ID：" + vertexId);
		}
	}
	EpcVertex.items[vertexId] = item;
	return vertexId;
}
/**
 * @public
 * @static
 * @param vertexId {Number}
 * @returns {EpcVertex}
 * */
EpcVertex.getItem = function(vertexId){
	return EpcVertex.items[vertexId];
}
/**
 * @returns 
 * */
EpcVertex.getItems = function (){
	return EpcVertex.items;
}
/**
 * @public
 * @static
 * @param item {EpcVertex}
 * @returns {Number}
 * */
EpcVertex.getVertexId = function (item){
	for(var i in EpcVertex.items){
		if(EpcVertex.items[i] == item){
			return i;
		}
	}
}
/**
 * @public
 * @static
 * @param vertexId {Number}
 * @returns {Boolean} 操作是否成功，或者说相应ID的节点是否存在
 * */
EpcVertex.removeItem = function (vertexId){
	if(EpcVertex.items[vertexId]){
		EpcVertex.items[vertexId] = null;
		return true;
	}else{
		return false;
	}
}
/**
 * @public
 * @static
 * */
EpcVertex.removeItems = function (){
	EpcVertex.items = new Object();
}
/**
 * @public
 * @class EpcEvent
 * @author 艾培东
 * @constructor EpcEvent
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param text {String}
 * @param vertexId {Number} -optional
 * */
function EpcEvent(x, y, width, height, text, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, text, vertexId);
}
/**
 * @extends {EpcCell}
 * */
EpcEvent.prototype = new EpcVertex();
EpcEvent.prototype.constructor = EpcEvent;
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcEvent.prototype.toXml = function (xmlDocument){
	var eventNode = xmlDocument.createElement("event");
	var nameNode = xmlDocument.createElement("name");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	eventNode.setAttribute("id", this.vertexId);
	nameNode.appendChild(xmlDocument.createTextNode(this.value));
	eventNode.appendChild(nameNode);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	eventNode.appendChild(graphicsNode);
	return eventNode;
};
/**
 * @public
 * @class EpcFunction
 * @author 艾培东
 * @constructor EpcFunction
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param text {String}
 * @param vertexId {Number} -optional
 * */
function EpcFunction(x, y, width, height, text, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, text, vertexId);
}
/**
 * @extends {EpcVertex}
 * */
EpcFunction.prototype = new EpcVertex();
EpcFunction.prototype.constructor = EpcFunction;
/**
 * @private
 * @type {Boolean}
 * */
EpcFunction.prototype.configurable = false;
/**
 * @public
 * @returns {Boolean}
 * */
EpcFunction.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcFunction.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcFunction.prototype.toXml = function (xmlDocument){
	var functionNode = xmlDocument.createElement("function");
	var nameNode = xmlDocument.createElement("name");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	functionNode.setAttribute("id", this.vertexId);
	nameNode.appendChild(xmlDocument.createTextNode(this.value));
	functionNode.appendChild(nameNode);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	functionNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableFunction");
		functionNode.appendChild(configNode);
	}
	return functionNode;
};
/**
 * @public
 * @class EpcRole
 * @author 艾培东
 * @constructor EpcRole
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param text {String}
 * @param vertexId {Number} -optional
 * */
function EpcRole(x, y, width, height, text, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, text, vertexId);
}
/**
 * @extends {mxCell}
 * */
EpcRole.prototype = new EpcVertex();
EpcRole.prototype.constructor = EpcRole;
/**
 * @private
 * @type {Boolean}
 * */
EpcRole.prototype.configurable = false;
/**
 * @public
 * @returns {Boolean}
 * */
EpcRole.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcRole.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcRole.prototype.toXml = function (xmlDocument){
	var roleNode = xmlDocument.createElement("role");
	var nameNode = xmlDocument.createElement("name");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	roleNode.setAttribute("id", this.vertexId);
	nameNode.appendChild(xmlDocument.createTextNode(this.value));
	roleNode.appendChild(nameNode);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	roleNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableRole");
		roleNode.appendChild(configNode);
	}
	return roleNode;
};
function ObjectWithSemantic(){
	
}

/**
 * @extends <mxCylinder>
 */
ObjectWithSemantic.prototype = new mxCylinder();
ObjectWithSemantic.prototype.constructor = ObjectWithSemantic;

ObjectWithSemantic.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	if (isForeground) {
		path.moveTo(w*0.90, h*0.25);
		path.lineTo(w*0.95, h*0.75);
		path.lineTo(w*0.85, h*0.75);
		path.close();
	} else {
		path.moveTo(0, 0);
		path.lineTo(0, h);
		path.lineTo(w, h);
		path.lineTo(w, 0);
		path.close();
	}
};

mxCellRenderer.prototype.defaultShapes['object-with-semantic'] = ObjectWithSemantic;

/**
 * @public
 * @class EpcObject
 * @author 艾培东
 * @constructor EpcObject
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param text {String}
 * @param vertexId {Number} -optional
 * */
function EpcObject(x, y, width, height, text, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, text, vertexId);
}
/**
 * @extends {mxCell}
 * */
EpcObject.prototype = new EpcVertex();
EpcObject.prototype.constructor = EpcObject;
/**
 * @private
 * @type {Boolean}
 * */
EpcObject.prototype.configurable = false;
/**
 * @private
 * @type {String}
 * */
EpcObject.prototype.semanticType = null;
/**
 * @public
 * @returns {String}
 * */
EpcObject.prototype.getSemanticType = function (){
	return this.semanticType;
}
/**
 * @public
 * @returns {Boolean}
 * */
EpcObject.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param semanticType {String}
 * */
EpcObject.prototype.setSemanticType = function (semanticType){
	this.semanticType = semanticType;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcObject.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcObject.prototype.toXml = function (xmlDocument){
	var objectNode = xmlDocument.createElement("object");
	var nameNode = xmlDocument.createElement("name");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	objectNode.setAttribute("id", this.vertexId);
	nameNode.appendChild(xmlDocument.createTextNode(this.value));
	objectNode.appendChild(nameNode);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	objectNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableObject");
		objectNode.appendChild(configNode);
	}
	if(this.semanticType){
		var attributeNode = xmlDocument.createElement("attribute");
		attributeNode.setAttribute("typeDef", "semanticType");
		attributeNode.setAttribute("value", this.semanticType);
		objectNode.appendChild(attributeNode);
	}
	return objectNode;
};

/**
 * @public
 * @class EpcAnd
 * @author 艾培东
 * @constructor EpcAnd
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param vertexId {Number} -optional
 * */
function EpcAnd(x, y, width, height, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, "", vertexId);
}
/**
 * @extends {mxCell}
 * */
EpcAnd.prototype = new EpcVertex();
EpcAnd.prototype.constructor = EpcAnd;
/**
 * @private
 * @type {Boolean}
 * */
EpcAnd.prototype.configurable = false;
/**
 * @public
 * @returns {Boolean}
 * */
EpcAnd.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcAnd.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcAnd.prototype.toXml = function (xmlDocument){
	var andNode = xmlDocument.createElement("and");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	andNode.setAttribute("id", this.vertexId);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	andNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableConnector");
		andNode.appendChild(configNode);
	}
	return andNode;
};
/**
 * @public
 * @class EpcOr
 * @author 艾培东
 * @constructor EpcAnd
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param vertexId {Number} -optional
 * */
function EpcOr(x, y, width, height, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, "", vertexId);
}
/**
 * @extends {mxCell}
 * */
EpcOr.prototype = new EpcVertex();
EpcOr.prototype.constructor = EpcOr;
/**
 * @private
 * @type {Boolean}
 * */
EpcOr.prototype.configurable = false;
/**
 * @public
 * @returns {Boolean}
 * */
EpcOr.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcOr.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcOr.prototype.toXml = function (xmlDocument){
	var orNode = xmlDocument.createElement("or");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	orNode.setAttribute("id", this.vertexId);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	orNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableConnector");
		orNode.appendChild(configNode);
	}
	return orNode;
};
/**
 * @public
 * @class EpcXor
 * @author 艾培东
 * @constructor EpcAnd
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number}
 * @param vertexId {Number} -optional
 * */
function EpcXor(x, y, width, height, vertexId){
	if(vertexId == null){
		vertexId = EpcVertex.addItem(this);
	}else{
		EpcVertex.addItem(this, vertexId);
	}
	EpcVertex.call(this, x, y, width, height, "", vertexId);
}
/**
 * @extends {mxCell}
 * */
EpcXor.prototype = new EpcVertex();
EpcXor.prototype.constructor = EpcOr;
/**
 * @private
 * @type {Boolean}
 * */
EpcXor.prototype.configurable = false;
/**
 * @public
 * @returns {Boolean}
 * */
EpcXor.prototype.isConfigurable = function (){
	return this.configurable;
}
/**
 * @public
 * @param configurable {Boolean}
 * */
EpcXor.prototype.setConfigurable = function(configurable){
	this.configurable = configurable;
}
/**
 * @override
 * @public
 * @param xmlDocument {XMLDocument}
 * @return {DOMElement}
 * */
EpcXor.prototype.toXml = function (xmlDocument){
	var xorNode = xmlDocument.createElement("xor");
	var graphicsNode = xmlDocument.createElement("graphics");
	var positionNode = xmlDocument.createElement("position");
	xorNode.setAttribute("id", this.vertexId);
	positionNode.setAttribute("x", this.getX());
	positionNode.setAttribute("y", this.getY());
	positionNode.setAttribute("width", this.getWidth());
	positionNode.setAttribute("height", this.getHeight());
	graphicsNode.appendChild(positionNode);
	xorNode.appendChild(graphicsNode);
	if(this.configurable){
		var configNode = xmlDocument.createElement("configurableConnector");
		xorNode.appendChild(configNode);
	}
	return xorNode;
};
/**
 * @class EpcGraph
 * @author 艾培东
 * */
var EpcGraph = function (){};
/**
 * @public
 * @static
 * @returns {Number}
 * */
EpcGraph.getWidth = function (){
	return $("#edit-window svg").width();
}
/**
 * @public
 * @static
 * @returns {Number}
 * */
EpcGraph.getHeight = function (){
	return $("#edit-window svg").height();
}
/**
 * @class　EpcCanvas
 * @author 艾培东
 * */
var EpcCanvas = function (){};
/**
 * @private
 * @type HTMLCanvasElement
 * */
EpcCanvas.canvas = null;
/**
 * @private
 * @type CanvasRenderingContext2D
 * */
EpcCanvas.context = null;
/**
 * @private
 * @static
 * */
EpcCanvas.defaultFont = "12px Consolas";
/**
 * @public
 * @static
 * @param _event {EpcEvent}
 * */
EpcCanvas.drawEvent = function (_event){
	var x = parseInt(_event.getX()*EpcCanvas.getZoom());
	var y = parseInt(_event.getY()*EpcCanvas.getZoom());
	var w = parseInt(_event.getWidth()*EpcCanvas.getZoom())+1;
	var h = parseInt(_event.getHeight()*EpcCanvas.getZoom())+1;
	var r = parseInt( w > h*2 ? h/4 : w/8);
	EpcCanvas.context.beginPath();
	EpcCanvas.context.moveTo(x+r+0.5,y+0.5);
	EpcCanvas.context.lineTo(x+w-r-0.5,y+0.5);
	EpcCanvas.context.lineTo(x+w-0.5,y+h/2);
	EpcCanvas.context.lineTo(x+w-r-0.5,y+h-0.5);
	EpcCanvas.context.lineTo(x+r+0.5,y+h-0.5);
	EpcCanvas.context.lineTo(x+0.5,y+h/2);
	EpcCanvas.context.closePath();
	EpcCanvas.context.fillStyle="rgba(255,0,0,0.5)";
	EpcCanvas.context.fill();
	EpcCanvas.context.lineWidth=1;
	EpcCanvas.context.strokeStyle="#333";
	EpcCanvas.context.stroke();
	//console.log(_event.value.split(/\r/));
	//console.log(x*y*w*h)
	//EpcGraph.drawText(x+w*0.5, y+h*0.5, _event.getName());
};
/**
 * @public
 * @static
 * @param _function {EpcFunction}
 * */
EpcCanvas.drawFunction = function (_function){
	var x = parseInt(_function.getX()*EpcCanvas.getZoom());
	var y = parseInt(_function.getY()*EpcCanvas.getZoom());
	var w = parseInt(_function.getWidth()*EpcCanvas.getZoom())+1;
	var h = parseInt(_function.getHeight()*EpcCanvas.getZoom())+1;
	var r = parseInt(w>h*2 ? h/4 : w/8);
	EpcCanvas.context.beginPath();
	EpcCanvas.context.moveTo(x+w-r-0.5,y+0.5);
	EpcCanvas.context.arcTo(x+w,y+0.5,x+w-0.5,y+r+0.5,r-0.5);
	EpcCanvas.context.lineTo(x+w-0.5,y+h-r-0.5);
	EpcCanvas.context.arcTo(x+w-0.5,y+h-0.5,x+w-r-0.5,y+h-0.5,r-0.5);
	EpcCanvas.context.lineTo(x+r+0.5,y+h-0.5);
	EpcCanvas.context.arcTo(x+0.5,y+h-0.5,x+0.5,y+h-r-0.5,r-0.5);
	EpcCanvas.context.lineTo(x+0.5,y+r+0.5);
	EpcCanvas.context.arcTo(x+0.5,y+0.5,x+r+0.5,y+0.5,r-0.5);
	EpcCanvas.context.closePath();
	EpcCanvas.context.fillStyle="rgba(0,255,0,0.5)";
	EpcCanvas.context.fill();
	EpcCanvas.context.strokeStyle="#333"; 
	EpcCanvas.context.stroke();
}
/**
 * @public
 * @static
 * @param _role {EpcRole}
 * */
EpcCanvas.drawRole = function (_role){
	var x = parseInt(_role.getX()*EpcCanvas.getZoom());
	var y = parseInt(_role.getY()*EpcCanvas.getZoom());
	var w = parseInt(_role.getWidth()*EpcCanvas.getZoom())+1;
	var h = parseInt(_role.getHeight()*EpcCanvas.getZoom())+1;
	var r = parseInt(w/10);
	EpcCanvas.context.fillStyle = "rgba(255,255,0,0.5)";
	EpcCanvas.context.fillRect(x+0.5,y+0.5,w-1,h-1);
	EpcCanvas.context.strokeStyle = "#333";
	EpcCanvas.context.strokeRect(x+0.5,y+0.5,w-1,h-1);
	EpcCanvas.context.fillStyle = "#333";
	EpcCanvas.context.fillRect(x+r,y,1,h);
};
/**
 * @public
 * @static
 * @param _object {EpcObject}
 * */
EpcCanvas.drawObject = function (_object){
	var x = parseInt(_object.getX()*EpcCanvas.getZoom());
	var y = parseInt(_object.getY()*EpcCanvas.getZoom());
	var w = parseInt(_object.getWidth()*EpcCanvas.getZoom())+1;
	var h = parseInt(_object.getHeight()*EpcCanvas.getZoom())+1;
	EpcCanvas.context.fillStyle = "rgba(0,255,255,0.5)";
	EpcCanvas.context.fillRect(x+0.5,y+0.5,w-1,h-1);
	EpcCanvas.context.strokeStyle = "#333";
	EpcCanvas.context.strokeRect(x+0.5,y+0.5,w-1,h-1);
};
/**
 * @public
 * @static
 * @param _and {EpcAnd}
 * */
EpcCanvas.drawAnd = function (_and){
	var x = parseInt(_and.getX()*EpcCanvas.getZoom());
	var y = parseInt(_and.getY()*EpcCanvas.getZoom());
	var w = parseInt(_and.getWidth()*EpcCanvas.getZoom())+1;
	EpcCanvas.context.beginPath();
	EpcCanvas.context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
	EpcCanvas.context.closePath();
	EpcCanvas.context.fillStyle = "rgba(227,227,227,0.5)";
	EpcCanvas.context.fill();
	EpcCanvas.context.lineWidth=1.5;
	EpcCanvas.context.strokeStyle = "#333";
	EpcCanvas.context.stroke();
	EpcCanvas.context.beginPath();
	EpcCanvas.context.moveTo(x+w*0.25,y+w*0.75);
	EpcCanvas.context.lineTo(x+w*0.5,y+w*0.25);
	EpcCanvas.context.lineTo(x+w*0.75,y+w*0.75);
	EpcCanvas.context.moveTo(0,0);
	EpcCanvas.context.closePath();
	EpcCanvas.context.stroke();
};
/**
 * @public
 * @static
 * @param _or {EpcOr}
 * */
EpcCanvas.drawOr = function (_or, _context){
	var x = parseInt(_or.getX()*EpcCanvas.getZoom());
	var y = parseInt(_or.getY()*EpcCanvas.getZoom());
	var w = parseInt(_or.getWidth()*EpcCanvas.getZoom())+1;
	EpcCanvas.context.beginPath();
	EpcCanvas.context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
	EpcCanvas.context.closePath();
	EpcCanvas.context.fillStyle = "rgba(227,227,227,0.5)";
	EpcCanvas.context.fill();
	EpcCanvas.context.lineWidth=1.5;
	EpcCanvas.context.strokeStyle = "#333";
	EpcCanvas.context.stroke();
	EpcCanvas.context.beginPath();
	EpcCanvas.context.moveTo(x+w*0.25,y+w*0.25);
	EpcCanvas.context.lineTo(x+w*0.5,y+w*0.75);
	EpcCanvas.context.lineTo(x+w*0.75,y+w*0.25);
	EpcCanvas.context.moveTo(0,0);
	EpcCanvas.context.closePath();
	EpcCanvas.context.stroke();
};
/**
 * @public
 * @static
 * @param _xor {EpcXor}
 * */
EpcCanvas.drawXor = function (_xor){
	var x = parseInt(_xor.getX()*EpcCanvas.getZoom());
	var y = parseInt(_xor.getY()*EpcCanvas.getZoom());
	var w = parseInt(_xor.getWidth()*EpcCanvas.getZoom())+1;
	EpcCanvas.context.beginPath();
	EpcCanvas.context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
	EpcCanvas.context.closePath();
	EpcCanvas.context.fillStyle = "rgba(227,227,227,0.5)";
	EpcCanvas.context.fill();
	EpcCanvas.context.lineWidth=1.5;
	EpcCanvas.context.strokeStyle = "#333";
	EpcCanvas.context.stroke();
	EpcCanvas.context.beginPath();
	EpcCanvas.context.moveTo(x+w*0.25,y+w*0.25);
	EpcCanvas.context.lineTo(x+w*0.75,y+w*0.75);
	EpcCanvas.context.moveTo(x+w*0.75,y+w*0.25);
	EpcCanvas.context.lineTo(x+w*0.25,y+w*0.75);
	EpcCanvas.context.moveTo(0,0);
	EpcCanvas.context.closePath();
	EpcCanvas.context.stroke();
};
/**
 * 对字符串进行格式化，可以得到排列成不同行时字符串的最小宽度，最多5行
 * @public
 * @static
 * @param text {String}
 * @returns [[{text:String,width:Number}]]
 * */
EpcCanvas.formatText = function (text){
	text = $.trim(text + "");
	// 首先判断文本是中英文
	if(EpcUtils.textIsEnglish(text)){
		// 提取出所有单词
		var arr,arr1 = text.split(/\s/), arr2, temp, min, flag;
		// 测出所有单词的长度以及空格的长度
		var spaceWidth = EpcCanvas.context.measureText(" ").width;
		for(var i = 0, j = 0; i < arr1.length; i++){
			if(arr1[i] != ""){
				arr2[j] = new Object();
				arr2[j].text = arr1[i];
				arr2[j].width = EpcCanvas.context.measureText(arr1[i]).width;
				j++;
			}
		}
		var n = arr2.length;
		// 单行
		if(arr2.length > 0){
			for(var i = 0; i < arr2.length; i++){
				arr[1] += arr2[i].width;
			}
			arr[1] += (arr2[i].length-1) * spaceWidth;
		}else{
			arr[1] = {text : "", width : 0};
		}
		// 获取特定单词串的长度（包含空格）
		function getWordsWidth(start, end){
			var width = 0;
			for(var i = start; i < end; i++){
				width += arr2[i].width;
			}
			width += spaceWidth * (end - start - 1);
			return width;
		}
		// 两行
		temp = new Array(), min = 0, flag = 0;
		if(arr2.length > 1){
			for(var i = 1; i < arr2.length; i++){
				var width1 = getWordsWidth(0, i);
				var width2 = getWordsWidth(i, arr2.length);
				temp.push({
					text1 : arr2.slice(0, i).join(" "), 
					text2 : arr2.slice(i, arr2.length).join(" "),
					width1 : width1, 
					width2 : width2
				});
			}
			for(var i = 0; i < temp.length; i++){
				if(min < Math.max(temp[i].width1, temp[i].width2)){
					min = Math.max(temp[i].width1, temp[i].width2);
					flag = i;
				}
			}
			arr[2] = [{text : temp[flag].text1, width : temp[flag].width1},
			          {text : temp[flag].text2, width : temp[flag].width2}];
		}else{
			arr[2] = [{text : "", width : 0}, {text : "", width : 0}];
		}
		// 三行
		temp = new Array(), min = 0, flag = 0;
		if(n > 2){
			for(var i = 1; i < n - 1; i++){
				for(var j = i + 1; j < n; j++){
					temp.push([
						{text : arr2.slice(0, i).join(" "), width : getWordsWidth(0, i)},
						{text : arr2.slice(i, j).join(" "), width : getWordsWidth(i ,j)},
						{text : arr2.slice(j, n).join(" "), width : getWordsWidth(j, n)}
					]);
				}
			}
			for(var i = 0; i < temp.length; i++){
				max = Max.max(temp[i][0].width, temp[i][1].width);
				max = Max.max(temp[i][2].width, max);
				if(min < max){
					min = max;
					flag = i;
				}
			}
			
			
			
		}else{
			
			
		}
		
	}else{
		
	}
};
/**
 * @public
 * @static
 * */
EpcCanvas.refresh = function(){
//	var items = EpcVertex.getItems();
//	var width = EpcGraph.getWidth();
//	var height = EpcGraph.getHeight()+1;
//	$(EpcCanvas.canvas).width(width).height(height);
//	$(EpcCanvas.canvas).attr("width",width).attr("height",height);
//	EpcCanvas.context.clearRect(0, 0, height, width);
//	for(var i in items){
//		if(items[i] instanceof EpcEvent){
//			EpcCanvas.drawEvent(items[i]);
//		}else if(items[i] instanceof EpcFunction){
//			EpcCanvas.drawFunction(items[i]);
//		}else if(items[i] instanceof EpcRole){
//			EpcCanvas.drawRole(items[i]);
//		}else if(items[i] instanceof EpcObject){
//			EpcCanvas.drawObject(items[i]);
//		}else if(items[i] instanceof EpcAnd){
//			EpcCanvas.drawAnd(items[i]);
//		}else if(items[i] instanceof EpcOr){
//			EpcCanvas.drawOr(items[i]);
//		}else if(items[i] instanceof EpcXor){
//			EpcCanvas.drawXor(items[i]);
//		}
//	}
}
/**
 * @public
 * @static
 * @returns {Number}
 * */
EpcCanvas.getZoom = function (){
	return graph.view.scale;
}
// 对EpcCanvas类的初始化
EpcCanvas.init = function(){
	EpcCanvas.canvas = $("#edit-window canvas")[0];
	EpcCanvas.context = EpcCanvas.canvas.getContext("2d");
	$(document).mouseup(function (){
		EpcCanvas.refresh();
	});
	//$("#edit-window svg>g>g:eq(1)").hide();
};
/**
 * @public
 * @class EpcEditor
 * @author 艾培东
 * */
var EpcEditor = function (){};
/**
 * @public
 * @static
 * @returns {String}
 * */
EpcEditor.encodeEpml = function (){
	var xmlDocument = mxUtils.createXmlDocument();
	var epmlNode = xmlDocument.createElement("epml");
	var epcNode = xmlDocument.createElement("epc");
	epmlNode.appendChild(epcNode);
	epcNode.setAttribute("epcId","0");
	for(var i in EpcVertex.getItems()){
		var item = EpcVertex.getItems()[i];
		var node = item.toXml(xmlDocument);
		epcNode.appendChild(node);
	}
	// 编码所有边
	var cells = graph.model.cells;
	for(var i in cells){
		if(cells[i].isEdge()){
			var arcNode = xmlDocument.createElement("arc");
			var flowNode = xmlDocument.createElement("flow");
			arcNode.setAttribute("id", cells[i].id);
			flowNode.setAttribute("source", EpcVertex.getVertexId(cells[i].source));
			flowNode.setAttribute("target", EpcVertex.getVertexId(cells[i].target));
			arcNode.appendChild(flowNode);
			epcNode.appendChild(arcNode);
		}
	}
	return mxUtils.getPrettyXml(epmlNode);
};
/**
 * @public
 * @static
 * @param xmlStr {String}
 * */
EpcEditor.decodeEpml = function (xmlStr){
	console.log(xmlStr)
	// 先删除已有
	graph.removeCells(graph.model.getChildren(graph.getDefaultParent()));
	EpcVertex.removeItems();
	// 解析字符串
	var epc = $(xmlStr).find("epc:eq(0)");
	if(epc.length == 0){
		return;
	}
	epc.find("event").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var name = $(this).find("name").text();
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||80;
		h = parseInt(h)||40;
		// 创建
		var vertex = new EpcEvent(x, y, w, h, name, id);
		graph.addCell(vertex);
	});
	epc.find("function").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var name = $(this).find("name").text();
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableFunction").length == 1;
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||80;
		h = parseInt(h)||40;
		// 创建
		var vertex = new EpcFunction(x, y, w, h, name, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		graph.addCell(vertex);
	});
	epc.find("role").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var name = $(this).find("name").text();
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableRole").length == 1;
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||80;
		h = parseInt(h)||30;
		// 创建
		var vertex = new EpcRole(x, y, w, h, name, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		graph.addCell(vertex);
	});
	epc.find("object").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var name = $(this).find("name").text();
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableObject").length == 1;
		var semanticType = $(this).find("attribute[typDef=semanticType]").attr("value");
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||80;
		h = parseInt(h)||30;
		// 创建
		var vertex = new EpcObject(x, y, w, h, name, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		if(semanticType){
			vertex.setSemanticType(semanticType);
		}
		graph.addCell(vertex);
	});
	epc.find("and").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableConnector").length == 1;
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||30;
		h = parseInt(h)||30;
		// 创建
		var vertex = new EpcAnd(x, y, w, h, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		graph.addCell(vertex);
	});
	epc.find("or").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableConnector").length == 1;
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||30;
		h = parseInt(h)||30;
		// 创建
		var vertex = new EpcOr(x, y, w, h, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		graph.addCell(vertex);
	});
	epc.find("xor").each(function (){
		// 提取属性
		var id = $(this).attr("id");
		var x = $(this).find("position").attr("x");
		var y = $(this).find("position").attr("y");
		var w = $(this).find("position").attr("width");
		var h = $(this).find("position").attr("height");
		var configurable = $(this).find("configurableConnector").length == 1;
		// 进一步完善属性
		//id= parseInt(id)||null;
		x = parseInt(x)||0; 
		y = parseInt(y)||0;
		w = parseInt(w)||30;
		h = parseInt(h)||30;
		// 创建
		var vertex = new EpcXor(x, y, w, h, id);
		if(configurable){
			vertex.setConfigurable(true);
		}
		graph.addCell(vertex);
	});
	epc.find("arc").each(function (){
		var source = $(this).find("flow,relation").attr("source");
		var target = $(this).find("flow,relation").attr("target");
		source = EpcVertex.getItem(source);
		target = EpcVertex.getItem(target);
		graph.insertEdge(graph.getDefaultParent(),null,null,source,target,"strokeColor=#000;");
	});
	auto_layout();
	EpcCanvas.refresh();
};
/**
 * 编辑器的初始化，也是整个应用的入口
 * @public
 * @static
 * @param mode {String} 编辑器的模式，可以为edit、readonly或null
 * @param process {String} 流程的id,对应
 * @param creator {String} 流程创建者的用户名
 * @param userType{String} 流程创建者所属的用户组，只能是ProcessProvider或ORG
 * */
EpcEditor.init = function (mode, process, extension){
	graph_init();
	EpcCanvas.init();
	// 未登录时
	if(mode == null){
	//	$("body").text("请先登录！");
	// 编辑模式时(creator与userType无效)
	}else if(mode == 'edit'){
		if(process == null){
			$("#operate.dialog").css({
				"left":$(document).width()/2-$("#operate.dialog").width()/2,
				"top":$(document).height()/2-$("#operate.dialog").height()/2
			}).show();
			$("#dialog-cover").show();
		}else{
			
			//$("#edit-window .name").text(process);
			EpcEditor.open(process);
		}
	// 只读模式	
	}else if(mode == 'readonly'){
		$("#editor").addClass("readonly");
		EpcEditor.open(process,extension);;
	}
	
};

EpcEditor.version = 0;

EpcEditor.initTitlebar = function (){
	
};
/**
 * 动态加载流程
 * @param process {String} -optional 流程名
 * @param creator {String} -optional 流程的创建者
 * @param userType{String} -optional 流程创建者所属的用户组
 * */
EpcEditor.open = function (process, extension){
	$.ajax({
		async:false,
		catche:false,
		dataType:"text",
		error:function (){
			alert("流程打开失败！");
		},
		success:function (data){
			data=JSON.parse(data);
			if(data.code==0){
				EpcEditor.decodeEpml(data.content);
				EpcEditor.version = data.version;
				EpcEditor.setName(data.name);
				EpcCanvas.refresh();
			}else if(data.code==1){
				EpcEditor.decodeEpml(data.extended);
				alert("扩展点的个数为："+data.extendNumber);
				EpcCanvas.refresh();
			}else{
				alert("打开流程时出错，错误代码："+data.code);
			}
			
		},
		type:"GET", 
		url:"jsp/open.jsp",
		data :{process : process,extension:extension||""},
		dataType:"text"
	});
};

EpcEditor.setName = function (str){
	$("#edit-window .name").text(str);
};
/**
 * C-iEPC流程的名称
 * @private
 * @type {String}
 * */
EpcEditor.process = null;

var EpcFooter = (function (){
	
	var _EpcFooter = function (){};
	_EpcFooter.prototype = {};
	/**
	 * 
	 * */
	_EpcFooter.hideAll = function (){
		$("#footer>.item").hide();
	};
	/**
	 * @param x {Number}
	 * */
	_EpcFooter.showX = function (x){
		$("#footer>.x").text("X:" + x).show();
	};
	/**
	 * @param y {Number}
	 * */
	_EpcFooter.showY = function (y){
		$("#footer>.y").text("Y:" + y).show();
	};
	/**
	 * @param width {Number}
	 * */
	_EpcFooter.showWidth = function (width){
		$("#footer>.width").text("宽度:" + width).show();
	};
	/**
	 * @param height {Number}
	 * */
	_EpcFooter.showHeight = function (height){
		$("#footer>.height").text("高度:" + height).show();
	};
	/**
	 * @public
	 * @static
	 * @param semanticType {String}
	 * */
	_EpcFooter.showSemanticType = function (semanticType){
		if(semanticType){
			$("#footer>.semanticType").text("语义:" + semanticType).show();
		}else{
			$("#footer>.semanticType").text("未定义语义").show();
		}
	};
	/**
	 * @public 
	 * @static
	 * @param configurable {Boolean}
	 * */
	_EpcFooter.showConfiguration = function (configurable){
		if(configurable){
			$("#footer>.configuration").text("可配置").show();
		}else{
			$("#footer>.configuration").text("不可配置").show();
		}
	};
	
	return _EpcFooter;
})();
/**
 * 创建流程的对话框
 * @author 艾培东
 * @public
 * @class EpcCreateDialog
 * @constructor EpcCreateDialog
 * @param name {String} 流程名，可以为空
 * */
function EpcCreateDialog(name){
	
}


$(function (){
	$(document).mouseup(function (){
		setTimeout(function (){
			var cells=graph.getSelectionCells();
			EpcFooter.hideAll();
            if(cells.length==0){

            }else if(cells.length==1){
                var cell=cells[0];
                if(cell.isVertex()){
                	EpcFooter.showX(cell.getX());
                	EpcFooter.showY(cell.getY());
                	EpcFooter.showWidth(cell.getWidth());
                	EpcFooter.showHeight(cell.getHeight());
                	if(cell instanceof EpcFunction){
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}else if(cell instanceof EpcObject){
                		EpcFooter.showSemanticType(cell.getSemanticType());
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}else if(cell instanceof EpcRole){
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}else if(cell instanceof EpcAnd){
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}else if(cell instanceof EpcOr){
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}else if(cell instanceof EpcXor){
                		EpcFooter.showConfiguration(cell.isConfigurable());
                	}
                }
            }
		},10);
	});
	
	$("#footer>.configuration").click(function (){
		var cell = graph.getSelectionCells()[0];
		cell.setConfigurable(!cell.isConfigurable());
		graph.refresh();
	});
});


// 标题栏
var $titlebar={};
// 依次存放title与graph
$titlebar.array=new Array();
//单个标题的宽度（等宽）
$titlebar.maxwidth = 150;
// 单个标题的最小宽度
$titlebar.minwidth = 80;
// 添加标签页
// @param {String} _name 要建立的标签页的名称
// @returns {mxGraph} 
$titlebar.add = function (_name){
	
};
// 刷新标题栏，调用此方法的情况如下：
// 1、新建流程
// 2、关闭流程
// 3、浏览器窗口缩放
// 4、伸缩左边窗口
$titlebar.refresh=function (){
	// 标题栏中可容纳标题部分的宽度
	var _w1 = $("#titlebar").width()-70;
	// 标题的数量
	var _n = $titlebar.array.length/2;
	// 单个标题的宽度（两个标题之间有单位为1的间隔）
	var _w2 = _w1/_n - 1;
	// 隐藏滚动按钮
	$("#titlebar>.slide-left").hide();
	$("#titlebar>.slide-right").hide();
	// 需要适当减小标题宽度的情况
	if(_w2 >= $titlebar.minwidth && _w2 < $titlebar.maxwidth){
		$("#titlebar>.title").width(parseInt(_w2));
	// 需要增加滚动按钮的情况	
	}else if(_w2 < $titlebar.minwidth){
		$("#titlebar>.title").width($titlebar.minwidth);
		$("#titlebar>.slide-left").show();
		$("#titlebar>.slide-right").show();
	}
	
	
};






/*
 * descendant[di'sendənt] node:后代节点
 * ancestor['ænsɛstɚ] node:祖先节点
 * corresponding[,kɔrə'spɑndɪŋ]:匹配
 */

/*
 * getElementsByTagName方法在IE 8与Firefox 19中会返回一个HTMLCollection对象，
 * 而在Chrome 26与Opera 12中则将返回NodeList对象
 */
 
/*
 * element.className在IE 8、Firefox 19、Chrome 26与Opera 12中都能使用
 */

$(function (){

	$("#toolbar .undo").click(function (){
		if(graph.undoManager.canUndo()){
			graph.undoManager.undo();
		}else{
			alert("还未进行任何操作！");
		}
	});
	$("#toolbar .redo").click(function (){
		if(graph.undoManager.canRedo()){
			graph.undoManager.redo();
		}else{
			alert("已经到最后一步了！");
		}
	});
	$("#toolbar .cut").click(function (){
		if(graph.getSelectionCells().length>0){
			mxClipboard.cut(graph);
		}else{
			alert("未选中图元！");
		}
	});
	$("#toolbar .copy").click(function (){
		if(graph.getSelectionCells().length>0){
			mxClipboard.copy(graph);
		}else{
			alert("未选中图元！");
		}
	});
	$("#toolbar .paste").click(function (){
		if(mxClipboard.isEmpty()){
			alert("剪切板为空！");
		}else{
			mxClipboard.paste(graph);
		}
	});
	$("#edit-window .head .operation.delete").click(function (){
		if(graph.getSelectionCells().length==0){
			alert("未选中图元！");
		}else if(confirm("确定删除？")){
			graph.removeCells();
		}
	});
	$("#edit-window .head .operation.zoom-in").click(function (){
		graph.zoomIn();
	});
	$("#edit-window .head .operation.zoom-out").click(function (){
		graph.zoomOut();
	});
	$("#edit-window .head .operation.zoom-actual").click(function (){
		graph.zoomActual();
	});
});

$(function (){
	$("#edit-window .head .name").click(function (){
		var str = prompt("请输入新的名字：",$(this).text());
		if(str != null){
			$(this).text(str);
		}
	});
});

