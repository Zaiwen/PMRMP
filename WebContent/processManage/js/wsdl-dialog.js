/**
 * @fileOverview
 * @author 艾培东(aipeidong@vip.qq.com)
 */

var $temp_wsdl_url = null;
$(function() {
	// {WSDLService}
	var service = null;
	// {String} Operation
	var selected = null;
	var $dialog = $("#dlg-wsdl-info");
	var add = function() {
		OWLModel.createProcess(service.getOperation(selected));
		$("#dlg-wsdl-info").dialog('close');
		$("#atomic-process-list-window>.body").children().remove();
		for (var i = 0; i < OWLModel.getAllAtomicProcess().length; i++) {
			var item = $("<p class='unselected'/>");
			item.text(OWLModel.getAllAtomicProcess()[i] + "");
			item.click(function() {
				$("#atomic-process-list-window>.body>.selected").removeClass(
						"selected").addClass("unselected");
				$(this).removeClass("unselected");
				$(this).addClass("selected");
			});
			item.dblclick(function() {
				$("#dlg-ato-proc").dialog("open");
			});
			$("#atomic-process-list-window>.body").append(item);
		}
		UpdateProcessView();
	};
	function dialogOpen() {
		var url = $temp_wsdl_url;
		$('#dlg-wsdl-info .url .value').text(url);
		if (url != null) {
			service = WSDLService.createService(url);
			var operations = service.getOperations();
			var $operation = $('#dlg-wsdl-info .operation');
			var $detail = $('#dlg-wsdl-info .detail');
			$operation.children().remove();
			$detail.children().remove();
			for (var i = 0; i < operations.length; i++) {
				var operation = operations[i];
				var name = operation.getName();
				var $p = $('<p class=\'item\'/>').text(name);
				$p.click(function() {
					var $items = $('#dlg-wsdl-info .operation .item');
					$items.css({
						'background-color' : 'transparent',
						'color' : '#000000'
					});
					$(this).css({
						'background-color' : '#0099FF',
						'color' : '#FFFFFF'
					});
					// 由于闭包的缘故，必须再次赋值
					operation = service.getOperation($(this).text());
					$detail.html(new WSDLOperationView(operation).getHtml());
					selected = $(this).text();
				});
				$operation.append($p);
			}
		}
	}

	// 当关闭对话框时执行的操作
	function close() {
		$('#dlg-wsdl-info .url .value').text("null");
		$('#dlg-wsdl-info .operation').children().remove();
		$('#dlg-wsdl-info .detail').children().remove();
		$dialog[0].url = null;
		service = null;
		selected = null;
	}
	$('#dlg-wsdl-info .url .button').click(function() {
		$dialog.dialog("close");
		$("#dlg-wsdl-list").dialog("open");

	});
	// 通过jQuery UI生成界面
	$('#dlg-wsdl-info .url .button').button();
	$("#dlg-wsdl-info").dialog(
			{
				autoOpen : false,
				width : 720,
				height : 420,
				modal : true,
				title : language == "zh_CN" ? '通过WSDL添加' : "Add through WSDL",
				close : close,
				open : dialogOpen,
				resizable : false,
				buttons : [
						{
							text : language == "zh_CN" ? '添加' : "Add",
							click : function() {
								if (service == null) {
									alert(language == "zh_CN" ? '请先进行解析'
											: "select read it at first");
								} else if (selected == null) {
									alert(language == "zh_CN" ? '请选择一个操作！'
											: "please select anaction");
								} else {
									add();
								}
							}
						}, {
							text : language == "zh_CN" ? '取消' : "cancel",
							click : function() {
								$(this).dialog('close');
							}
						} ]
			});
});