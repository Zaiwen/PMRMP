/**
 * 只在网页加载完成后调用，用于恢复已经保存的数据
 */
function UpdateTreeView() {
    var construct = OWLModel.getCompositeProcess().getComposeOf();
    var $container = $('#composite-process-tree-window>.body');
    var $ul = $("<ul/>");
    function update(construct, $node) {
	if (construct != null) {
	    var $li = $('<li><a href="#"/><ul/></li>');
	    $li.children('a').text(construct + '');
	    $li.appendTo($node);
	    if (construct.addComponent) {
		for ( var i = 0; i < construct.getComponents().length; i++) {
		    update(construct.getComponents()[i], $li.children("ul"));
		}
	    }
	}
    }
    update(construct, $ul);
    $container.children().remove();
    $container.append($ul);
    $container.jstree({
	"themes" : {
	    "theme" : "classic",
	    "dots" : true,
	    "icons" : false
	},
	"ui" : {
	    "select_limit" : 1,
	    "select_prev_on_delete" : true
	},
	"core" : {
	    "animation" : 0,
	},
	"plugins" : [ "themes", "html_data", "crrm", "ui", "json_data" ]
    });
    setTimeout(function (){
	$("#composite-process-tree-window>.body").jstree("select_node",$("#composite-process-tree-window>.body>ul>li"));
	$("#cover").remove();
    },1000);
}
/**
 * 每一次改变控制结构之后都要调用该方法同步视图
 */
function UpdateGraphView() {

}
/**
 * 在增删原子流程之后调用此方法显示出所有可用的原子流程
 */
function UpdateProcessView() {
    var $container = $("#atomic-process-list-window>.body");
    var processes = OWLModel.getAllAtomicProcess();
    $container.children().remove();
    for ( var i = 0; i < processes.length; i++) {
	var process = processes[i];
	var $item = $("<div class='item unselected'/>");
	$item.click(function() {
	    var $selected = $("#atomic-process-list-window>.body>.selected");
	    $selected.removeClass("selected").addClass("unselected");
	    $(this).removeClass("unselected").addClass("selected");
	});
	$item.dblclick(function() {
		console.log(123);
		AtomicProcessDialog(OWLModel.get($(this).text()));
	   //$dlg_ato_proc.dialog("open");
	});
	$item.text(process + "").appendTo($container);
    }
}