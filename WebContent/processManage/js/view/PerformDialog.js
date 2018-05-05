/**
 * @param {Number}
 *                itemId 一个控制结构的唯一标识
 */
function PerformDialog(itemId) {
    var perform = controlConstructs[itemId];
    var operation = null;
    var $container = $('<div/>').css("padding", "24px");
    var $name = $("<div><span>名称：</span><input type='text'/></div>");
    $name.children("input").val(perform.getText());
    var $wsdl = $("<div><span>Grounding：</span><input type='text'/><button>选择WSDL...</button></div>");
    $wsdl.children("input").val(perform.getOperation() + '');
    $wsdl.children("button").click(function() {
	new ImportWsdlDialog(function(op) {
	    operation = op;
	    $wsdl.children("input").val(operation + '');
	});
    });
    var $data=$("<div/>");
    $container.append($name).append($wsdl);
    var dialog = new Dialog($container[0], 'Perform', 640, 480);
    dialog.addButton("确定", function() {
	perform.setOperation(operation);
	dialog.close();
    });
    dialog.addButton("取消", function() {
	dialog.close();
    });
}