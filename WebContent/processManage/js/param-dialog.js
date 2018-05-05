$(function() {
	var param = null;
	function open() {
		param = $("#param-dialog")[0].param;
		$("#param-dialog>.name>.value").text(param + "");
		$("#param-dialog>.type>.value").text(param.getType() + "");
		$("#param-dialog").dialog("option", "title", language=="zh_CN"?"编辑参数--":"edit parameter--" + param);
	}

	$("#param-dialog>.name>.button").click(function()  {
		var _old = param + "";
		var _new = prompt(language=="zh_CN"?"请输入新的参数名：":"please input new name", _old);
		if (_new != null & _new != _old) {
			if (OWLModel.isIdAvailable(_new)) {
				param.setId(_new);
				$("#param-dialog>.name>.value").text(_new);
				$("#param-dialog").dialog("option", "title", language=="zh_CN"?"编辑参数--":"edit parameter--" + _new);
			} else {
				alert(language=="zh_CN"?"该名称不可用！":"cannot use it");
			} 
		}
	});

	$("#param-dialog>.type>.button").click(function() {
		$("#select-ontology-dialog")[0].param = param;
		$("#select-ontology-dialog").dialog("open");
	});

	$("#param-dialog .button").button();
	$("#param-dialog").dialog( {
		autoOpen : false,
		height : 220,
		modal : true,
		open : open,
		resizable : false,
		width : 800
	});
});