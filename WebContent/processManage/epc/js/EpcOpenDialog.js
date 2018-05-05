/**
 * @author 艾培东 (1124180802@qq.com)
 * @public
 * @class EpcOpenDialog
 * */
var EpcOpenDialog = {};
/**
 * 指向整个对话框的jQuery对象
 * @private
 * @static
 * @type {jQuery}
 * */
EpcOpenDialog.$ = null;
/**
 * 请求得到的所有数据
 * @private
 * @static
 * @type {Array} 
 * */
EpcOpenDialog.data = [];
/**
 * 会显示出来的数据
 * @private
 * @static
 * @type {Array}
 * */
EpcOpenDialog.visibleData = [];
/**
 * 对话框是否初始过了（为了确保只在第一次打开的时候初始化）
 * @private
 * @static
 * @type {Boolean}
 * */
EpcOpenDialog.inited = false;
/**
 * 选中的流程的ID
 * @private
 * @static
 * @type {Number}
 * */
EpcOpenDialog.selected = -1;
/**
 * 排序的方式
 * @private
 * @static
 * @type {Number}
 * */
EpcOpenDialog.sortType = 0;
/**
 * 按名称正向排序
 * @private
 * @static
 * @final
 * @type {Number}
 * */
EpcOpenDialog.NAME_SORT = 0;
/**
 * 按时间逆向排序
 * @private
 * @static
 * @final
 * @type {Number}
 * */
EpcOpenDialog.TIME_SORT = 1;
/**
 * 关闭对话框
 * @private
 * @static
 * */
EpcOpenDialog.close = function (){
	EpcOpenDialog.$.hide();
	$("#dialog-cover").hide();
};
/**
 * 初始化对话框
 * @private
 * @static
 * */
EpcOpenDialog.init = function (){
	var dialog = EpcOpenDialog.$ = $("#open-dialog");
	// 按名称排序正排序
	dialog.find(".radio.name").click(function (){
		dialog.find(".radio.selected").removeClass("selected");
		$(this).addClass("selected");
		EpcOpenDialog.sortType = EpcOpenDialog.NAME_SORT;
		EpcOpenDialog.sortByName();
		EpcOpenDialog.updateView();
	});
	// 按时间逆排序
	dialog.find(".radio.time").click(function (){
		dialog.find(".radio.selected").removeClass("selected");
		$(this).addClass("selected");
		EpcOpenDialog.sortType = EpcOpenDialog.TIME_SORT;
		EpcOpenDialog.sortByTime();
		EpcOpenDialog.updateView();
	});
	// 搜索（不区分大小写）
	dialog.find(".search input")[0].oninput =function (){
		var val = $.trim($(this).val()).toLowerCase();
		if(val == ""){
			dialog.find(".search .text").show();
		}else{
			dialog.find(".search .text").hide();
		}
		EpcOpenDialog.search(val);
		EpcOpenDialog.sort();
		EpcOpenDialog.updateView();
	};
	// 选择
	dialog.find(".button.select").click(function (){
		if(EpcOpenDialog.selected == -1){
			alert("请先选择一个流程！");
		}else{
			EpcEditor.open(EpcOpenDialog.selected);
			EpcOpenDialog.close();
		}
	});
	// 关闭
	dialog.find(".button.close").click(function (){
		EpcOpenDialog.close();
	});
	// 对话框自动居中显示
	$(window).resize(function() {
		var _left = ($(document).width() - dialog.width()) / 2;
		var _top = ($(document).height() - dialog.height()) / 2;
		dialog.css("left", _left + "px");
		dialog.css("top", _top + "px");
	}).resize();
	EpcOpenDialog.inited = true;
};
/**
 * tr节点的事件绑定
 * @private
 * @static
 * */
EpcOpenDialog.onItemClick = function (){
	var content = EpcOpenDialog.$.find(".body .content");
	content.find("tr.selected").removeClass("selected");
	$(this).addClass("selected");
	EpcOpenDialog.selected = $(this).find(".id").text();
};
/**
 * 更新视图
 * @private
 * @static
 * @param error {Boolean} 当出错时会出现此参数
 * */
EpcOpenDialog.updateView = function (error){
	var data = EpcOpenDialog.visibleData;
	var content = EpcOpenDialog.$.find(".body .content");
	if(error){
		content.text("错误！");
	}else if(data == null || data.length == 0){
		content.text("没有任何满足条件的和数据！");
	}else{
		var table = $("<table/>");
		table.append("<tr>" +
				"<th class='id'></th>" +
				"<th class='name'>名称</th>" +
				"<th class='time'>修改时间</th>" +
				"<th class='version'>版本号</th>" +
				"</tr>");
		for(var i = 0; i < data.length; i++){
			var tr=$("<tr class='item'/>").click(EpcOpenDialog.onItemClick);
			$("<td class='id'/>").text(data[i].id).appendTo(tr);
			$("<td class='name'/>").text(data[i].name).appendTo(tr);
			$("<td class='time'/>").text(data[i].time).appendTo(tr);
			$("<td class='version'/>").text(data[i].version).appendTo(tr);
			table.append(tr);
		}
		content.empty().append(table);
	}
};
/**
 * 对visibleData中的数据进行排序
 * @private
 * @static
 * */
EpcOpenDialog.sort = function (){
	if(EpcOpenDialog.sortType == EpcOpenDialog.NAME_SORT){
		EpcOpenDialog.sortByName();
	}else if(EpcOpenDialog.sortType == EpcOpenDialog.TIME_SORT){
		EpcOpenDialog.sortByTime();
	}
};
/**
 * 对visibleData中的数据按name属性进行正向排序
 * @private
 * @static
 * */
EpcOpenDialog.sortByName = function (){
	var data = EpcOpenDialog.visibleData;
	for(var i = 0; i < data.length-1; i++){
		for(var j = 0; j < data.length-i-1; j++){
			if(data[j].name > data[j+1].name){
				var temp = data[j+1];
				data[j+1] = data[j];
				data[j] = temp;
			}
		}
	}
	EpcOpenDialog.selected = -1;
};
/**
 * 对visibleData中的数据按time属性进行逆向排序
 * @private
 * @static
 * */
EpcOpenDialog.sortByTime = function (){
	var data = EpcOpenDialog.visibleData;
	for(var i = 0; i < data.length-1; i++){
		for(var j = 0; j < data.length-i-1; j++){
			if(data[j].time < data[j+1].time){
				var temp = data[j+1];
				data[j+1] = data[j];
				data[j] = temp;
			}
		}
	}
	EpcOpenDialog.selected = -1;
};
/**
 * 搜索包含指定字符串的数据
 * @private
 * @static
 * @param str {String}
 * */
EpcOpenDialog.search = function (str){
	var data = EpcOpenDialog.data;
	var visible = EpcOpenDialog.visibleData =[];
	for(var i = 0; i < data.length; i++){
		if(data[i].name.indexOf(str) > -1){
			visible.push(data[i]);
		}
	}
	EpcOpenDialog.selected = -1;
};
/**
 * 显示该对话框
 * @public
 * @static
 * */
EpcOpenDialog.show = function (){
	if(!EpcOpenDialog.inited){
		EpcOpenDialog.init();
	}
	EpcOpenDialog.$.show();
	$("#dialog-cover").show();
	EpcOpenDialog.selected = -1;
	$.ajax({
		async:false,
		catche:false,
		error:function (){
			EpcOpenDialog.updateView(true);
		},
		success:function (data){
			EpcOpenDialog.data=JSON.parse(data);
			EpcOpenDialog.visibleData=JSON.parse(data);
			EpcOpenDialog.sort();
			EpcOpenDialog.updateView();
		},
		type:"POST",
		url:"jsp/get-epml-list.jsp",
		dataType:"text"
	});
};

$(function (){
	$("#header .button.open").click(function (){
		EpcOpenDialog.show();
	});
});