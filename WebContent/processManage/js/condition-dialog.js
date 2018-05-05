$(function() {
    var condition = null;
    function open() {
		condition = $("#condition-dialog")[0].construct;
		$("#condition-dialog>.name>.value").text(condition + "");
		$("#condition-dialog>.expression>textarea").val(condition.getCondition());
    }
    function close() {
		var text = $("#condition-dialog>.expression>textarea").val();
		condition.setCondition(text);
    }
    $("#condition-dialog").dialog({
		autoOpen : false,
		close :  close,
		height : 440,
		modal : true,
		open : open,
		resizable : false,
		title : "条件结构属性",
		width : 570,
    });
    $("#condition-dialog .name .operation").click(function (){
    	var _old = $("#condition-dialog>.name>.value").text();
    	var _new = prompt("请输入新的名字：", _old);
    	if (_new != null && _old != _new) {
    	    if (OWLModel.isIdAvailable(_new)) {
    		perform.setId(_new);
    		Redraw();
    	    }
    	}
    });
});