var $ont_graph = null;

// 预先加载需要用到的图片资源
$(function() {
	$.imgpreload("img/refresh-hover.png");
	$.imgpreload("img/refresh-active.png");
	$.imgpreload("img/list-hover.png");
	$.imgpreload("img/list-active.png");
	$.imgpreload("img/help-hover.png");
	$.imgpreload("img/help-active.png");
});
// 通过拖放滑动块进行缩放
$(function() {
	var mousedown = false;
	var pre_x = 0; 
	$(".slider .float").mousedown(function(e) {
		pre_x = e.clientX - parseInt($(this).css("left"));
		mousedown = true;
	});
	$(document).mousemove(function(e) {
		if (mousedown) {
			var _x = e.clientX - pre_x;
			if (_x >= 20 && _x <= 200) {
				$(".slider .float").css("left", _x + "px");
				$(".slider .value").text(parseInt(_x) + "%");
			} else if (_x < 20) {
				$(".slider .float").css("left", "20px");
				$(".slider .value").text("20%");
			} else {
				$(".slider .float").css("left", "200px");
				$(".slider .value").text("200%");
			}
		}
	}).mouseup(function() {
		mousedown = false;
	});
});
// 通过按钮调节滑动条进行缩放
$(function() {
	$(".slider .left").click(function() {
		var _left = parseInt($(".slider .float").css("left"));
		_left = parseInt((_left % 10 == 0 ? _left - 10 : _left) / 10) * 10;
		if (_left >= 20 && _left <= 200) {
			$(".slider .float").css("left", _left + "px");
			$(".slider .value").text(_left + "%");
		}
	});
	$(".slider .right").click(function() {
		var _left = parseInt($(".slider .float").css("left"));
		_left = parseInt(_left / 10) * 10 + 10;
		if (_left >= 20 && _left <= 200) {
			$(".slider .float").css("left", _left + "px");
			$(".slider .value").text(_left + "%");
		}
	});
	$(document).keydown(function(e) {
		if (e.ctrlKey) {
			if (e.keyCode == 187) {
				$(".slider .right").click();
				return false;
			} else if (e.keyCode == 189) {
				$(".slider .left").click();
				return false;
			}
		}
	});
});
// 设定当窗口大小变化时，对话框一直保持居中
$(function() {
	var $dialog = $("#ontology-list-dialog");
	$(window).resize(function() {
		var _left = ($(document).width() - $dialog.width()) / 2;
		var _top = ($(document).height() - $dialog.height()) / 2;
		$dialog.css("left", _left + "px");
		$dialog.css("top", _top + "px");
	});
	$(window).resize();
});
// 对话框的打开与关闭
$(function() {
	$("#viewer .head .list").click(function() {
		$("#ontology-list-dialog").show();
		$("#dialog-cover").show();
	});
	$("#ontology-list-dialog .close").click(function() {
		$("#ontology-list-dialog").hide();
		$("#dialog-cover").hide();
	});
})
// 本体列表的刷新
$(function() {
	var _body = $("#ontology-list-dialog>.body");
	$("#ontology-list-dialog .refresh").click(function() {
		$.ajax( {
			url : "get-ont-list.jsp",
			dataType : "json",
			cache : false,
			success : function(data) {
				var _table = $("<table/>");
				var _tr = $("<tr/>").appendTo(_table);
				$("<th>名称</th>").appendTo(_tr);
				$("<th>领域</th>").appendTo(_tr);
				$("<th>URI</th>").appendTo(_tr);
				$("<tr><td/><td/><td/></tr>").appendTo(_table);
				for ( var i = 0; i < data.length; i += 1) {
					var _name = data[i].name;
					var _domain = data[i].domain;
					var _uri = data[i].uri;
					var _td1 = $("<td class='name'/>").text(_name);
					var _td2 = $("<td class='domain'/>").text(_domain);
					var _td3 = $("<td class='uri'/>").text(_uri);
					var _tr = $("<tr class='ontology'/>");
					_tr.append(_td1).append(_td2).append(_td3);
					_td1.click(load_ont_class);
					_table.append(_tr);
				}
				$ont_list = data;
				_body.empty().append(_table);
			},
			error : function() {
				_body.text("刷新失败，请联系管理员！");
			}
		});
		$("#ontology-list-dialog>.body").text("正在刷新，请稍候……");
	}).click();
})

function load_ont_class() {
	var _uri = $(this).parent().find(".uri").text();
	$.ajax( {
		url : "get-ont-info.jsp",
		data : {
			uri : _uri
		},
		dataType : "json",
		success : function(data) {
			ont_show(data);
		},
		error : function(data) {
			console.log("error");
		}
	});
	$("#viewer>.foot>.info").text("正在加载 " + _uri);
	$("#viewer>.head>.sub-title").text(_uri);
	$("#ontology-list-dialog").hide();
	$("#dialog-cover").hide();
}

function ont_graph_init() {
	$ont_graph = new mxGraph($("#viewer .body")[0]);
}

$(ont_graph_init);

function ont_show(data) {
	OntModel.read(data);
	var _num=0;
	//$ont_graph.beginUpdate();
	for(var i=0;i<OntModel.rootClass.length;i+=1){
		var _h=OntModel.rootClass[0].getSize()*60;
		OntModel.rootClass[0].draw(0,_num+_h/2-10);
		_num=_num+_h;
	}
	//$ont_graph.endUpdate();
	
}

function OntClass() {
	this.subClass = new Array();
}

OntClass.prototype.localName = null;

OntClass.prototype.superClass = null;

OntClass.prototype.subClass = null;

OntClass.prototype.uri = null;

OntClass.prototype.namespace = null;

OntClass.prototype.setLocalName = function(name) {
	this.localName = name;
};

OntClass.prototype.getLocalName = function() {
	return this.localName;
};

OntClass.prototype.setSuperClass = function(superClass) {
	this.superClass = superClass;
}

OntClass.prototype.getSuperClass = function() {
	return this.superClass;
};

OntClass.prototype.addSubClass = function(subClass) {
	var flag = true;
	for ( var i = 0; i < this.subClass.length; i+=1) {
		if (this.subClass[i] == subClass) {
			flag = false;
			break;
		}
	}
	if (flag) {
		this.subClass.push(subClass);
	}
};

OntClass.prototype.getSubClass = function() {
	return this.subClass;
};

OntClass.prototype.setUri = function(uri) {
	this.uri = uri;
};

OntClass.prototype.getUri = function() {
	return this.uri;
};

OntClass.prototype.setNamespace = function(ns) {
	this.namespace = ns;
};

OntClass.prototype.getNamespace = function() {
	return this.namespace;
};
// 在绘图时确定尺寸
OntClass.prototype.getSize = function() {
	if (this.subClass.length == 0) {
		return 1;
	} else {
		var _num = 0;
		for ( var i = 0; i < this.subClass.length; i += 1) {
			_num = _num + this.subClass[i].getSize();
		}
		return _num;
	}
};

OntClass.prototype.draw=function (x,y){
	var _parent=$ont_graph.getDefaultParent();
	var _v1=$ont_graph.insertVertex(_parent,null, this.localName, x, y, 120, 40,"shape=ellipse;perimeter=ellipsePerimeter;strokeColor=#0099FF;fillColor=#FFFFFF;fontColor=#0099FF;strokeWidth=2;");
	for(var i=0;i<this.subClass.length;i+=1){
		var _v2=this.subClass[i].draw(x+200,y+i*60-30);
		$ont_graph.insertEdge(_parent, null, null, _v2, _v1,"strokeColor=#0099FF;strokeWidth=2;");
	}
	return _v1;
};

function OntModel() {

}

OntModel.data = new Array();

OntModel.ontClass = new Array();

OntModel.rootClass = new Array();

OntModel.read = function(data) {
	for ( var i = 0; i < data.length; i += 1) {
		OntModel.data.push(data[i]);
	}
	OntModel.ontClass = new Array();
	OntModel.rootClass = new Array();
	for ( var i = 0; i < OntModel.data.length; i += 1) {
		var ontClass = new OntClass();
		ontClass.setUri(OntModel.data[i].URI);
		ontClass.setLocalName(OntModel.data[i].localName);
		ontClass.setNamespace(OntModel.data[i].namespace);
		OntModel.ontClass.push(ontClass);
	}
	for ( var i = 0; i < OntModel.data.length; i += 1) {
		var superClass=OntModel.get(OntModel.data[i].superClass)
		if (superClass) {
			OntModel.get(OntModel.data[i].URI).setSuperClass(superClass);
			superClass.addSubClass(OntModel.get(OntModel.data[i].URI));
		} else {
			OntModel.addRootClass(OntModel.get(OntModel.data[i].URI));
		}
	}
};

OntModel.addRootClass = function(rootClass) {
	var flag = true;
	for ( var i = 0; i < OntModel.rootClass.length; i+=1) {
		if (OntModel.rootClass[i] == rootClass) {
			flag = false;
			break;
		}
	}
	if (flag) {
		OntModel.rootClass.push(rootClass);
	}
};

OntModel.get = function(uri) {
	for ( var i = 0; i < OntModel.ontClass.length; i += 1) {
		if (OntModel.ontClass[i].getUri() == uri) {
			return OntModel.ontClass[i];
		}
	}
}