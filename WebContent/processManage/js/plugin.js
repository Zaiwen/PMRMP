// 点击【插件开发按钮时弹出插件开发对话框】
$(function() {
	$("#editor>.toolbar>.plugin")[0].$=$("#extension-description-dialog");
	$("#editor>.toolbar>.plugin").click(function() {
		this.$.dialog("open");
	});
});



 

function plugin_dialog() {
	if (!window["plugin-init"]) {
		window["plugin-init"] = true;
		// 设定当窗口大小变化时，对话框一直保持居中
		(function() {
			function _resize() {
				var _w1 = $("#editor").width();
				var _w2 = $("#plugin-dialog").width();
				var _h1 = $("#editor").height();
				var _h2 = $("#plugin-dialog").height();
				var _left = (_w1 - _w2) / 2;
				var _top = (_h1 - _h2) / 2;
				$("#plugin-dialog").css("left", _left + "px");
				$("#plugin-dialog").css("top", _top + "px");
			}
			$(window).resize(_resize);
			_resize();
		})();
		// 显示插件开发对话框的触发事件
		(function() {
			$("#editor>.statusbar>.extension>span").click(function() {
				$("#plugin-dialog").show();
				$("#dialog-cover").show();
				$("#editor>.statusbar>.extension").hide();
			});
			$("#plugin-dialog .minimize").click(function() {
				$("#plugin-dialog").hide();
				$("#dialog-cover").hide();
				$("#editor>.statusbar>.extension").show();
			});
		})();

	}
	$("#dialog-cover").show();
	$("#plugin-dialog").show();
}

function plugin_description() {
	// 定于局部变量
	var $this = $("#plugin-dialog>.body>.content>.description");
	var $name = $this.find(".name>input");
	var $domain = $this.find(".domain>select");
	var $intro = $this.find(".introduction>textarea");
	var $navis = $("#plugin-dialog>.body>.navi>ol>li");
	var $navi = $("#plugin-dialog>.body>.navi>ol>.description");
	// 初始化本层次的数据
	$name.text(window["Pluggable-Extension"]["Name"]);
	$domain.children().each(function() {
		$(this).removeAttr("selected");
		if ($(this).text() == window["Pluggable-Extension"]["Domain"]) {
			$(this).attr("selected", "selected");
		}
	});
	$intro.text(window["Pluggable-Extension"]["Introduction"]);
	// 清除之后层次的数据
	window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Point"]["SPARQL-DL"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Extensional-Pattern"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Wsdl-Location"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Operation"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["PortType"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["InputMessageName"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["OutputMessageName"] = null;
	// 调节导航面板
	$navis.removeClass("current");
	$navi.addClass("current");
}

function plugin_query_pattern() {
	// 调节导航面板
	var $navis = $("#plugin-dialog>.body>.navi>ol>li");
	var $navi = $("#plugin-dialog>.body>.navi>ol>.query");
	$navis.removeClass("current");
	$navi.addClass("current");
	// 限定只能选择Variable-Query
	if (!window["plugin-query-pattern-init"]) {
		window["plugin-query-pattern-init"] = true;
		$("#extension-dialog .query-pattern input:gt(0)").click(function() {
			$(this).removeAttr("checked");
			alert(language=="zh_CN"?"暂不支持该查询方式":"dsiabled");
		});
	}
	// 清除之后层次的数据
	window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Point"]["SPARQL-DL"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Extensional-Pattern"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Wsdl-Location"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Operation"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["PortType"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["InputMessageName"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["OutputMessageName"] = null;
}

function plugin_query_variable() {
	var $this = $("#plugin-dialog>.body>.content>.query-variable");
	var $inputs = $this.find(".input ul");
	var $outputs = $this.find(".output ul");
	var inputs = window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Input"];
	var outputs = window["Pluggable-Extension"]["Extensional-Point"]["Variable-Query"]["Output"];
	// 初始化输入输出的本体标注信息（仅第一次）
	if (!window["plugin-query-variable-init"]) {
		window["plugin-query-variable-init"] = true;
		(function() {
			var _inputs = OWLModel.getAllInput();
			var _outputs = OWLModel.getAllOutput();
			for ( var i = 0; i < _inputs.length; i += 1) {
				var input = _inputs[i];
				var type = input.getType();
				var $check = $("<input type='checkbox'/>");
				var $li = $("<li/>").append($check);
				$li.append($("<span/>").text(type));
				$inputs.append($li);
				$check.change(function() {
					var text = $(this).parent().text();
					if ($(this).attr("checked") == "checked") {
						var flag = false;
						for ( var j = 0; j < inputs.length; j += 1) {
							if (inputs[i] == text) {
								flag = true;
								break;
							}
						}
						if (!flag) {
							inputs.push(text);
						}
					} else {
						var flag = -1;
						for ( var j = 0; j < inputs.length; j += 1) {
							if (inputs[i] == text) {
								flag = i;
								break;
							}
						}
						if (flag != -1) {
							for ( var j = flag; j < inputs.length - 1; j += 1) {
								inputs[j] = inputs[j + 1];
							}
							inputs.length -= 1;
						}
					}
				});
			}
			for ( var i = 0; i < outputs.length; i += 1) {
				var output = outputs[i];
				var type = output.getType();
				var $check = $("<input type='checkbox'/>");
				var $li = $("<li/>").append($check);
				$li.append($("<span/>").text(type));
				$outputs.append($li);
				$check.change(function() {
					var text = $(this).parent().text(), j;
					if ($(this).attr("checked") == "checked") {
						var flag = false;
						for (j = 0; j < outputs.length; j += 1) {
							if (outputs[i] == text) {
								flag = true;
								break;
							}
						}
						if (!flag) {
							outputs.push(text);
						}
					} else {
						var flag = -1;
						for (j = 0; j < outputs.length; j += 1) {
							if (outputs[i] == text) {
								flag = i;
								break;
							}
						}
						if (flag != -1) {
							for (j = flag; j < outputs.length - 1; j += 1) {
								outputs[j] = outputs[j + 1];
							}
							outputs.length -= 1;
						}
					}
				});
			}
		})();
	}
	// 初始化选中的本体标注
	$inputs.find("li").each(function() {
		for ( var i = 0; i < inputs.length; i += 1) {
			if (inputs[i] == $(this).text()) {
				$(this).find("input").attr("checked", "checked");
				return;
			}
		}
		$(this).find("input").removeAttr("checked");
	});
	$outputs.find("li").each(function() {
		for ( var i = 0; i < outputs.length; i += 1) {
			if (outputs[i] == $(this).text()) {
				$(this).find("input").attr("checked", "checked");
				return;
			}
		}
		$(this).find("input").removeAttr("checked");
	});
	// 清除之后层次的数据
	window["Pluggable-Extension"]["Extensional-Point"]["SPARQL-DL"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Extensional-Pattern"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Wsdl-Location"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Operation"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["PortType"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["InputMessageName"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["OutputMessageName"] = null;
}

function plugin_query_result() {

	window["Pluggable-Extension"]["Extensional-Fragment"]["Extensional-Pattern"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Input"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Output"] = [];
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Wsdl-Location"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["Operation"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["PortType"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["InputMessageName"] = null;
	window["Pluggable-Extension"]["Extensional-Fragment"]["Wsdl-Grounding"]["OutputMessageName"] = null;
}