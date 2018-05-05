// 设定当窗口大小变化时，对话框一直保持居中
$(function() {
	var $dialog = $("#ontology-dialog");
	$(window).resize(function() {
		var _left = ($(document).width() - $dialog.width()) / 2;
		var _top = ($(document).height() - $dialog.height()) / 2;
		$dialog.css("left", _left + "px");
		$dialog.css("top", _top + "px");
	});
	$(window).resize();
});
//对话框的打开
$(function (){
	$("#footer>.semanticType").click(function (){
        $("#dialog-cover").show();
        $("#ontology-dialog").show();
        $("#ontology-dialog .body").text("正在加载本体列表，请稍候...");
        $.ajax( {
			url : "../get-ont-list.jsp",
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
					_td1.click(function (){
						var _uri = $(this).parent().find(".uri").text();
						$.ajax( {
							url : "../get-ont-info.jsp",
							data : {
								uri : _uri
							},
							dataType : "json",
							success : function(data) {
								var _table = $("<table/>");
								var _tr = $("<tr/>").appendTo(_table);
								$("<th>localName</th>").appendTo(_tr);
								$("<th>URI</th>").appendTo(_tr);
								$("<tr><td/><td/></tr>").appendTo(_table);
								for ( var i = 0; i < data.length; i += 1) {
									var _name = data[i].localName;
									var _uri = data[i].URI;
									var _td1 = $("<td class='name'/>").text(_name);
									var _td3 = $("<td class='uri'/>").text(_uri);
									var _tr = $("<tr class='ontology'/>");
									_tr.append(_td1).append(_td3);
									_td1.click(function (){
										var _uri=$(this).parent().find(".uri").text();
										var _cell=graph.getSelectionCells()[0];
										_cell.setSemanticType(_uri);
										graph.refresh();
										$("#ontology-dialog .head .close").click();
										$("#detail-window .section.others .item.semantic .value").text(_uri)
										$("#detail-window .section.others .item.semantic .value").attr("title",_uri)
									});
									_table.append(_tr);
								}
								$("#ontology-dialog .body").empty().append(_table);
							},
							error : function(data) {
								console.log("error");
							}
						});
					});
					_table.append(_tr);
				}
				$ont_list = data;
				$("#ontology-dialog .body").empty().append(_table);
			},
			error : function() {
				$("#ontology-dialog .body").text("无法获取本体列表，请联系管理员！");
			}
		});
    });
});

//对话框的关闭
$(function (){
	$("#ontology-dialog .head .close").click(function (){
		$("#dialog-cover").hide();
        $("#ontology-dialog").hide();
	});
	$("#ontology-dialog .foot .button.close").click(function (){
		$("#dialog-cover").hide();
        $("#ontology-dialog").hide();
	});
});