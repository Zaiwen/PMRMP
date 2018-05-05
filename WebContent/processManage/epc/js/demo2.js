
function EpcElement(){
	
}

function EpcConnector(){
	
}

function EpcEvent(_x, _y, _width, _height, _name){
	
	var x = _x;
	
	var y = _y;
	
	var width = _width;
	
	var height = _height;
	
	var name = _name;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getHeight = function () {return height; };
	
	this.getWidth = function () {return width; };
	
	this.getName = function (){return name; };
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setHeight = function (_height) {height = _height; };
	
	this.setWidth = function (_width) {width = _width; };
	
	this.setName = function (_name) {name = _name;};
}

function EpcFunction(_x, _y, _width, _height, _name){
	
	var x = _x;
	
	var y = _y;
	
	var height = _height;
	
	var width = _width;
	
	var name = _name;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getHeight = function () {return height; };
	
	this.getWidth = function () {return width; };
	
	this.getName = function (){return name;};
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setHeight = function (_height) {height = _height; };
	
	this.setWidth = function (_width) {width = _width; };
	
	this.setName = function (_name){name = _name;};
}

function EpcRole(_x, _y, _width, _height, _name){
	
	var x = _x;
	
	var y = _y;
	
	var height = _height;
	
	var width = _width;
	
	var name = _name;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getHeight = function () {return height; };
	
	this.getWidth = function () {return width; };
	
	this.getName = function (){return name;}
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setHeight = function (_height) {height = _height; };
	
	this.setWidth = function (_width) {width = _width; };
	
	this.setName = function (_name){name=_name;};
}

function EpcObject(_x, _y, _width, _height, _name){
	
	var x = _x;
	
	var y = _y;
	
	var height = _height;
	
	var width = _width;
	
	var name = _name;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getHeight = function () {return height; };
	
	this.getWidth = function () {return width; };
	
	this.getName = function (){return name};
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setHeight = function (_height) {height = _height; };
	
	this.setName = function (_name) {name = _name;};
	
	this.setWidth = function (_width) {width = _width; };
}

function EpcAnd(_x, _y, _width){
	
	var x = _x;
	
	var y = _y;
	
	var width = _width;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getWidth = function () {return width; };
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setWidth = function (_width) {width = _width; };
}

function EpcOr(_x, _y, _width){
	
	var x = _x;
	
	var y = _y;
	
	var width = _width;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getWidth = function () {return width; };
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setWidth = function (_width) {width = _width; };
}

function EpcXor(_x, _y, _width){
	
	var x = _x;
	
	var y = _y;
	
	var width = _width;
	
	this.getX = function () { return x; };
	
	this.getY = function () { return y; };
	
	this.getWidth = function () {return width; };
	
	this.setX = function (_x) { x = _x; };
	
	this.setY = function (_y) { y = _y; };
	
	this.setWidth = function (_width) {width = _width; };
}
/**
 * @class EpcHandler
 * @param x {Number}
 * @param y {Number}
 * @param width {Number}
 * @param height {Number} -optional
 * */
function EpcHandler (_epcElement){
	
	var x = _epcElement.getX();
	
	var y = _epcElement.getY();
	
	var width = _epcElement.getWidth();
	
	var height = _epcElement.getHeight ? _epcElement.getHeight() : width;
	
	var visiable = true;
	// 规定是否为正方形，只有连接符是正方形的
	var square = !_epcElement.getHeight;
	
	var container = document.createElement("div");
	
	var nw = document.createElement("div");
	
	var se = document.createElement("div");
	
	$(container).addClass("handler").css("left",x+"px").css("top",y+"px").width(width-2).height(height-2);
	
	$(nw).addClass("nw-resize").appendTo($(container));
	
	$(se).addClass("se-resize").appendTo($(container));
	
	// 绑定左上角的Resizer
	
	var nw_mousedown = false;
	
	$(nw).mousedown(function (e){
		nw_mousedown = true;
		e.stopPropagation();
	});
	
	$(document).mousemove(function (e){
		if(nw_mousedown){
			var _width 	= width  + x - (e.clientX - EpcGraph.getOffsetX()) - 2;
			var _height = height + y - (e.clientY - EpcGraph.getOffsetY()) - 2;
			// 限制最小宽高为20px
			_width = Math.max(_width, 18);
			_height = Math.max(_height, 18);
			
			
			if(square){
				_width = _height = Math.min(_width, _height);
			}
			
			var _x = x + width - _width -2;
			var _y = y + height - _height - 2;
			
				$(container)
				.css("left",_x+"px")
				.css("top",_y+"px")
				.width(_width)
				.height(_height);
			//}
		}
	}).mouseup(function (){
		if(nw_mousedown){
			nw_mousedown = false;
			x = parseInt($(container).css("left"));
			y = parseInt($(container).css("top"));
			width = $(container).width()+2;
			height = $(container).height()+2;
			_epcElement.setX(x);
			_epcElement.setY(y);
			_epcElement.setWidth(width);
			_epcElement.setHeight ? _epcElement.setHeight(height) : null;
			EpcGraph.refresh();
		}
	});
	
	// 绑定右下角的Resizer
	
	var se_mousedown = false;
	
	$(se).mousedown(function (e){
		se_mousedown = true;
		e.stopPropagation();
	});
	
	$(document).mousemove(function (e){
		if(se_mousedown){
			var _width  = e.clientX - EpcGraph.getOffsetX() - x - 2;
			var _height = e.clientY - EpcGraph.getOffsetY() - y - 2;
			
			// 限制最小宽高为20px
			_width = Math.max(_width, 18);
			_height = Math.max(_height, 18);
			
			if(square){
				_width = _height = Math.min(_width, _height);
			}
			$(container).width(_width).height(_height);
		}
	}).mouseup(function (){
		if(se_mousedown){
			se_mousedown = false;
			width = $(container).width()+2;
			height = $(container).height()+2;
			_epcElement.setWidth(width);
			_epcElement.setHeight ? _epcElement.setHeight(height) : null;
			EpcGraph.refresh();
		}
	});
	
	// 整体移动
	
	var c_mousedown = false;
	// 
	var c_offset_x = 0;
	
	var c_offset_y = 0;
	
	$(container).mousedown(function (e){
		c_mousedown = true;
		c_offset_x = e.offsetX;
		c_offset_y = e.offsetY;
	});
	
	$(document).mousemove(function (e){
		if(c_mousedown){
			$(container).css({
				"left":(e.clientX - c_offset_x - EpcGraph.getOffsetX()) + "px",
				"top": (e.clientY - c_offset_y - EpcGraph.getOffsetY()) + "px"
			});
		}
	}).mouseup(function (){
		if(c_mousedown){
			c_mousedown = false;
			x = parseInt($(container).css("left"));
			y = parseInt($(container).css("top"));
			_epcElement.setX(x);
			_epcElement.setY(y);
			EpcGraph.refresh();
		}
	});
	
	this.getElement = function (){
		return container;
	}
}

/**
 * @class EpcGraph
 * */
var EpcGraph = (function (){
	/**
	 * @private
	 * @static
	 * */
	var shapes = new Array();
	/**
	 * @private
	 * @static
	 * @type {HTMLElement}
	 * */
	var div = document.createElement("div");
	/**
	 * @private
	 * @static
	 * */
	var container = null;
	/**
	 * @private
	 * @static
	 * */
	var canvas = document.createElement("canvas");
	/**
	 * @private
	 * @static
	 * @type {String} classic|light|pure
	 * */
	var style = "classic";
	/**
	 * @private
	 * @static
	 * */
	var context = null;
	/**
	 * 画布的高度
	 * @private
	 * @type {Number}
	 * */
	var height = 1123;
	/**
	 * 画布的宽度
	 * @private
	 * @type {Number}
	 * */
	var width = 793;
	/**
	 * @private
	 * @static
	 * */
	var zoom = 0.0;
	/// 只是形式上的而已
	var _EpcGraph= function (){};
	_EpcGraph.prototype=new Object();
	/**
	 * @public
	 * @static
	 * @param _event {EpcEvent}
	 * */
	_EpcGraph.addEvent = function (_event){
		if(!(_event instanceof EpcEvent)){
			throw new Error("Invalid argument for EpcGraph.addEvent(_event)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_event));
			shapes.push(_event)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _function {EpcFunction}
	 * */
	_EpcGraph.addFunction = function (_function){
		if(!(_function instanceof EpcFunction)){
			throw new Error("Invalid argument for EpcGraph.addFunction(_function)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_function));
			shapes.push(_function)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _role {EpcRole}
	 * */
	_EpcGraph.addRole = function(_role){
		if(!(_role instanceof EpcRole)){
			throw new Error("Invalid argument for EpcGraph.addRole(_role)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_role));
			shapes.push(_role)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _object {EpcObject}
	 * */
	_EpcGraph.addObject = function (_object){
		if(!(_object instanceof EpcObject)){
			throw new Error("Invalid argument for EpcGraph.addObject(_object)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_object));
			shapes.push(_object)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _and {EpcAnd}
	 * */
	_EpcGraph.addAnd = function(_and){
		if(!(_and instanceof EpcAnd)){
			throw new Error("Invalid argument for EpcGraph.addAnd(_and)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_and));
			shapes.push(_and)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _or {EpcOr}
	 * */
	_EpcGraph.addOr = function(_or){
		if(!(_or instanceof EpcOr)){
			throw new Error("Invalid argument for EpcGraph.addOr(_or)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_or));
			shapes.push(_or)
		}
	};
	/**
	 * @public
	 * @static
	 * @param _xor {EpcXor}
	 * */
	_EpcGraph.addXor = function(_xor){
		if(!(_xor instanceof EpcXor)){
			throw new Error("Invalid argument for EpcGraph.addXor(_xor)");
		}else{
			EpcGraph.drawHandler(new EpcHandler(_xor));
			shapes.push(_xor)
		}
	};
	/**
	 * @public
	 * @static 
	 * @param _event {EpcEvent}
	 * @param _context {CanvasRenderingContext2D} -optional 默认为context
	 * */
	_EpcGraph.drawEvent = function (_event, _context){
		var x = _event.getX();
		var y = _event.getY();
		var w = _event.getWidth();
		var h = _event.getHeight();
		var r = parseInt( w > h*2 ? h/4 : w/8);
		_context = _context || context ;
		_context.beginPath();
		_context.moveTo(x+r+0.5,y+0.5);
		_context.lineTo(x+w-r-0.5,y+0.5);
		_context.lineTo(x+w-0.5,y+h/2);
		_context.lineTo(x+w-r-0.5,y+h-0.5);
		_context.lineTo(x+r+0.5,y+h-0.5);
		_context.lineTo(x+0.5,y+h/2);
		_context.closePath();
		_context.fillStyle="rgba(255,0,0,0.5)";
		_context.fill();
		_context.lineWidth=1;
		_context.strokeStyle="#333";
		_context.stroke();
		EpcGraph.drawText(x+w*0.5, y+h*0.5, _event.getName());
	};
	
	_EpcGraph.drawFunction = function (_function, _context){
		var x = _function.getX();
		var y = _function.getY();
		var w = _function.getWidth();
		var h = _function.getHeight();
		var r = parseInt(w>h*2 ? h/4 : w/8);
		_context = _context || context;
		_context.beginPath();
		_context.moveTo(x+w-r-0.5,y+0.5);
		_context.arcTo(x+w,y+0.5,x+w-0.5,y+r+0.5,r-0.5);
		_context.lineTo(x+w-0.5,y+h-r-0.5);
		_context.arcTo(x+w-0.5,y+h-0.5,x+w-r-0.5,y+h-0.5,r-0.5);
		_context.lineTo(x+r+0.5,y+h-0.5);
		_context.arcTo(x+0.5,y+h-0.5,x+0.5,y+h-r-0.5,r-0.5);
		_context.lineTo(x+0.5,y+r+0.5);
		_context.arcTo(x+0.5,y+0.5,x+r+0.5,y+0.5,r-0.5);
		_context.closePath();
		_context.fillStyle="rgba(0,255,0,0.5)";
		_context.fill();
		_context.strokeStyle="#333"; 
		_context.stroke();
	}
	
	_EpcGraph.drawRole = function (_role, _context){
		var x = _role.getX();
		var y = _role.getY();
		var w = _role.getWidth();
		var h = _role.getHeight();
		var r = parseInt(w/10);
		_context = _context||context;
		_context.fillStyle = "rgba(255,255,0,0.5)";
		_context.fillRect(x+0.5,y+0.5,w-1,h-1);
		_context.strokeStyle = "#333";
		_context.strokeRect(x+0.5,y+0.5,w-1,h-1);
		_context.fillStyle = "#333";
		_context.fillRect(x+r,y,1,h);
	};
	
	_EpcGraph.drawObject = function (_object, _context){
		var x = _object.getX();
		var y = _object.getY();
		var w = _object.getWidth();
		var h = _object.getHeight();
		_context = _context||context;
		_context.fillStyle = "rgba(0,255,255,0.5)";
		_context.fillRect(x+0.5,y+0.5,w-1,h-1);
		_context.strokeStyle = "#333";
		_context.strokeRect(x+0.5,y+0.5,w-1,h-1);
	};
	
	_EpcGraph.drawAnd = function (_and, _context){
		var x = _and.getX();
		var y = _and.getY();
		var w = _and.getWidth();
		_context = _context||context;
		_context.beginPath();
		_context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
		_context.closePath();
		_context.fillStyle = "rgba(227,227,227,0.5)";
		_context.fill();
		_context.lineWidth=1.5;
		_context.strokeStyle = "#333";
		_context.stroke();
		_context.beginPath();
		_context.moveTo(x+w*0.25,y+w*0.75);
		_context.lineTo(x+w*0.5,y+w*0.25);
		_context.lineTo(x+w*0.75,y+w*0.75);
		_context.moveTo(0,0);
		_context.closePath();
		_context.stroke();
	};
	
	_EpcGraph.drawOr = function (_or, _context){
		var x = _or.getX();
		var y = _or.getY();
		var w = _or.getWidth();
		_context = _context||context;
		_context.beginPath();
		_context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
		_context.closePath();
		_context.fillStyle = "rgba(227,227,227,0.5)";
		_context.fill();
		_context.lineWidth=1.5;
		_context.strokeStyle = "#333";
		_context.stroke();
		_context.beginPath();
		_context.moveTo(x+w*0.25,y+w*0.25);
		_context.lineTo(x+w*0.5,y+w*0.75);
		_context.lineTo(x+w*0.75,y+w*0.25);
		_context.moveTo(0,0);
		_context.closePath();
		_context.stroke();
	};
	
	_EpcGraph.drawXor = function (_xor, _context){
		var x = _xor.getX();
		var y = _xor.getY();
		var w = _xor.getWidth();
		_context = _context||context;
		_context.beginPath();
		_context.arc(x+w*0.5,y+w*0.5,w*0.5-0.5,0,Math.PI*2);
		_context.closePath();
		_context.fillStyle = "rgba(227,227,227,0.5)";
		_context.fill();
		_context.lineWidth=1.5;
		_context.strokeStyle = "#333";
		_context.stroke();
		_context.beginPath();
		_context.moveTo(x+w*0.25,y+w*0.25);
		_context.lineTo(x+w*0.75,y+w*0.75);
		_context.moveTo(x+w*0.75,y+w*0.25);
		_context.lineTo(x+w*0.25,y+w*0.75);
		_context.moveTo(0,0);
		_context.closePath();
		_context.stroke();
	};
	
	_EpcGraph.drawHandler = function (_handler){
		if(!(_handler instanceof EpcHandler)){
			throw new Error("Invalid arguments for EpcGraph.drawHandler(_handler")
		}
		$(div).append($(_handler.getElement()));
	};
	/**
	 * @public
	 * @static
	 * @param x {Number}
	 * @param y {Number}
	 * @param text {String}
	 * */
	_EpcGraph.drawText = function (x, y, text){
		context.font = "12px Consolas";
		context.fillStyle = "#000000";
		context.textAlign = "center";
		context.textBaseline = "middle";
		context.fillText(text, x, y);
	};
	/**
	 * 获取画布左上角距离页面左上角在X轴方向上的位移量
	 * @returns {Number}
	 * */
	_EpcGraph.getOffsetX = function (){
		return parseInt($(container).css("left"))
			+ parseInt($(canvas).css("margin-left"))
			-$(container).scrollLeft();
	};
	/**
	 * 获取画布左上角距离页面左上角在Y轴方向上的位移量
	 * @returns {Number}
	 * */
	_EpcGraph.getOffsetY = function (){
		return parseInt($(container).css("top"))
			+ parseInt($(canvas).css("margin-top"))
			-$(container).scrollTop();
	};
	/**
	 * 初始化
	 * */
	_EpcGraph.init = function (){
		container = $("#editor>.graph")[0];
		$(div).width(width).height(height).css("position","absolute").appendTo($(container));
		$(canvas).css("position","absolute").attr("width",width).attr("height",height).appendTo($(container))[0];
		context = canvas.getContext("2d");
		// 保持画布居中
		$(window).resize(function (){
			var _x = (width + 80 > $(container).outerWidth()) ? 40 : parseInt(($(container).outerWidth() -width)/2);
			var _y = (height+ 80 > $(container).outerHeight())? 40 : parseInt(($(container).outerHeight()-height)/2);
			$(div).css("margin", _y + "px " + _x +"px");
			$(canvas).css("margin", _y + "px " + _x +"px");
		});
		
		
	};
	
	_EpcGraph.refresh = function (){
		var w = $(canvas).attr("width");
		var h = $(canvas).attr("height");
		context.clearRect(0, 0, w, h);
		
		for(var i = 0; i < shapes.length; i++){
			var _x = shapes[i].getX();
			var _y = shapes[i].getY();
			var _w = shapes[i].getWidth();
			var _h = shapes[i].getHeight ? shapes[i].getHeight() : _w;
			if(shapes[i] instanceof EpcEvent){
				EpcGraph.drawEvent(shapes[i]);
			}else if(shapes[i] instanceof EpcFunction){
				EpcGraph.drawFunction(shapes[i]);
			}else if(shapes[i] instanceof EpcRole){
				EpcGraph.drawRole(shapes[i]);
			}else if(shapes[i] instanceof EpcObject){
				EpcGraph.drawObject(shapes[i]);
			}else if(shapes[i] instanceof EpcAnd){
				EpcGraph.drawAnd(shapes[i]);
			}else if(shapes[i] instanceof EpcOr){
				EpcGraph.drawOr(shapes[i]);
			}else if(shapes[i] instanceof EpcXor){
				EpcGraph.drawXor(shapes[i]);
			}
		}
		
	};
	_EpcGraph.setZoom = function (_zoom){
		
	};
	
	return _EpcGraph;
})();


var EpcToolbar = (function (){
	/**
	 * @private
	 * @type {HTMLElement}
	 * */
	var toolbar = null;
	
	var shape = new Object();
	
	var _EpcToolbar = function (){};
	_EpcToolbar.prototype = new Object();
	/**
	 * 初始化
	 * @public
	 * */
	_EpcToolbar.init = function (){
		toolbar = $("#editor>.toolbar")[0];
		$(window).resize(function (){EpcToolbar.refresh();});
		var shape=null, src = "";
		var img = $("<img/>").css("position","absolute").css("z-index","1001").hide().appendTo($("body"));
		$("#editor>.toolbar>.event").mousedown(function (){
			shape = "event";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.function").mousedown(function (){
			shape = "function";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.role").mousedown(function (){
			shape = "role";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.object").mousedown(function (){
			shape = "object";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.and").mousedown(function (){
			shape = "and";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.or").mousedown(function (){
			shape = "or";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		$("#editor>.toolbar>.xor").mousedown(function (){
			shape = "xor";
			img.attr("src",this.toDataURL()).width($(this).width()).height($(this).height());
		});
		
		$(document).mousemove(function (e){
			if(shape!=null){
				img.css({
					"left":e.clientX+"px",
					"top":e.clientY+"px"
				}).show();
			}
			
			
		}).mouseup(function (e){
			var _x = parseInt(e.clientX-EpcGraph.getOffsetX());
			var _y = parseInt(e.clientY-EpcGraph.getOffsetY());
			if(shape=="event"){
				EpcGraph.addEvent(new EpcEvent(_x,_y,80,40,"Event"));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="function"){
				EpcGraph.addFunction(new EpcFunction(_x,_y,80,40,"Function"));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="role"){
				EpcGraph.addRole(new EpcRole(_x,_y,80,30,"Role"));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="object"){
				EpcGraph.addObject(new EpcObject(_x,_y,80,30,"Object"));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="and"){
				EpcGraph.addAnd(new EpcAnd(_x,_y,30));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="or"){
				EpcGraph.addOr(new EpcOr(_x,_y,30));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}else if(shape=="xor"){
				EpcGraph.addXor(new EpcXor(_x,_y,30));
				EpcGraph.refresh();
				shape=null;
				img.hide();
			}
		});
	};
	/**
	 * 重新绘制工具栏，当窗口大小调整时调用此方法
	 * */
	_EpcToolbar.refresh = function (){
		// 调整工具栏宽度
		var height = $(toolbar).height();
		var width = parseInt(height/($(toolbar).children().length+1)*2);
		$(toolbar).width(width);
		$("#editor>.graph").css("left", (width+1)+"px");
		// 设定各个图片属性
		var x=parseInt(width*0.2);
		var y=parseInt(width*0.1);
		var w=parseInt(width*0.6);
		var h=parseInt(width*0.3);
		$("#editor>.toolbar").children().css({
			"padding" : y+"px "+x+"px",
			"height":h+"px",
			"width" :w+"px"
		}).attr({"height":h,"width":w});
		$("#editor>.toolbar>canvas:first").css("margin-top",parseInt(width*0.2)+"px");
		
		var c,r=parseInt(w/10);
		// 重绘Event
		c = $("#editor>.toolbar>.event")[0].getContext("2d");
		EpcGraph.drawEvent(new EpcEvent(0, 0, w, h, ""), c);
		//
		// Function
		c = $("#editor>.toolbar>.function")[0].getContext("2d");
		EpcGraph.drawFunction(new EpcFunction(0, 0, w, h, ""), c);
		// Role
		c = $("#editor>.toolbar>.role")[0].getContext("2d");
		EpcGraph.drawRole(new EpcRole(0, parseInt(w*0.1), w ,parseInt(w*0.3)),c);
		// object
		c = $("#editor>.toolbar>.object")[0].getContext("2d");
		EpcGraph.drawObject(new EpcObject(0,parseInt(w*0.1),w,parseInt(w*0.3)),c);
		// and
		c = $("#editor>.toolbar>.and")[0].getContext("2d");
		EpcGraph.drawAnd(new EpcAnd(w*0.25,1,parseInt(w*0.5)-1),c);
		// or
		c = $("#editor>.toolbar>.or")[0].getContext("2d");
		EpcGraph.drawOr(new EpcOr(w*0.25,1,parseInt(w*0.5)-1),c);
		// and
		c = $("#editor>.toolbar>.xor")[0].getContext("2d");
		EpcGraph.drawXor(new EpcXor(w*0.25,1,parseInt(w*0.5)-1),c);
		// line
		c = $("#editor>.toolbar>.line")[0].getContext("2d");
		c.beginPath();
		c.moveTo(0.5,0.5);
		c.lineTo(w*0.5,0.5);
		c.lineTo(w*0.5,h-0.5);
		c.lineTo(w-0.5,h-0.5);
		c.moveTo(0,0);
		c.closePath();
		c.strokeStyle="#000";
		c.lineWidth = 1;
		c.stroke();
		//delete
		c = $("#editor>.toolbar>.delete")[0].getContext("2d");
		c.beginPath();
		c.moveTo(w*0.25,0);
		c.lineTo(w*0.75,h);
		c.moveTo(w*0.75,0);
		c.lineTo(w*0.25,h);
		c.moveTo(0,0);
		c.closePath();
		c.strokeStyle = "#c00";
		c.lineWidth = 2;
		c.stroke();
		
	};
	
	return _EpcToolbar;
})();

$(function (){
	EpcToolbar.init();
	EpcGraph.init();
})

//$(function (){
//	EpcToolbar.init();
//	EpcGraph.init();
//
//	var event = new EpcEvent();
//	event.setX(10),
//	event.setY(10),
//	event.setWidth(80);
//	event.setHeight(40);
//	event.setName("Event");
//	EpcGraph.addEvent(event);
//	var f = new EpcFunction();
//	f.setX(110),
//	f.setY(10),
//	f.setWidth(80);
//	f.setHeight(40);
//	EpcGraph.addFunction(f);
//	var r = new EpcRole();
//	r.setX(210),
//	r.setY(20),
//	r.setWidth(80);
//	r.setHeight(20);
//	EpcGraph.addRole(r);
//	var o = new EpcObject();
//	o.setX(310),
//	o.setY(20),
//	o.setWidth(80);
//	o.setHeight(20);
//	EpcGraph.addObject(o);
//	var a = new EpcAnd();
//	a.setX(410),
//	a.setY(20),
//	a.setWidth(30);
//	EpcGraph.addAnd(a);
//	var o = new EpcOr();
//	o.setX(460),
//	o.setY(20),
//	o.setWidth(30);
//	EpcGraph.addOr(o);
//	var x = new EpcXor();
//	x.setX(510),
//	x.setY(20),
//	x.setWidth(30);
//	EpcGraph.addXor(x);
//	EpcGraph.drawHandler(new EpcHandler(event));
// 	EpcGraph.drawHandler(new EpcHandler(f));
// 	EpcGraph.drawHandler(new EpcHandler(o));
//	EpcGraph.refresh();
//});
/**
 * @class EpcEditor
 * @author aipeidong
 * */
var EpcEditor = (function (){
	/**
	 * @private
	 * @type {HTMLElement}
	 * */
	var editor = null;
	/**
	 * @private
	 * @type {String}
	 * */
	var user = null;
	/**
	 * @private
	 * @type {String}
	 * */
	var name = null;
	/**
	 * @private
	 * @type {Boolean}
	 * */
	var readonly = false;
	// 形式上的类，为了便于Eclipse生成Outline
	var _EpcEditor = function (){};
	_EpcEditor.prototype = {};
	/**
	 * 初始化
	 * @public
	 * @param _user {String} 用户名
	 * @param _name {String} 流程名
	 * @param _readonly {Boolean} 是否只读
	 * */
	_EpcEditor.init = function (_user, _name, _readonly){
		user = _user;
		name = _name;
		readonly = !!_readonly;
		if(readonly){
			$("#editor").addClass("readonly");
		}
		$(window).resize();
	};
	/**
	 * 加载/打开某个EPC流程
	 * @public
	 * @param _user {String} 用户名
	 * @param _name {String} 流程名
	 * */
	_EpcEditor.loadProcess = function (_user, _name){
		
	};
	return _EpcEditor;
})();

var EpcUtils = {
	/**
	 * 进制转换——将毫米转换成像素点
	 * */
	mm2px : function (mm){
		var div = $("<div/>").css("height", mm+"mm").appendTo($("body"));
		var height = div.height();
		div.remove();
		return height;
	}	
};

// 获取屏幕尺寸
//$(function (){
//	var div=$("<div style='height:100in'/>").appendTo($("body"));
//	var height = div.height();
//	div.remove();
//	var radius=height/100;
//	var xxx=Math.sqrt(screen.width*screen.width + screen.height*screen.height);
//	alert(xxx/radius);
//});