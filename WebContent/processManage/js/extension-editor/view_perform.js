/**
 * 弹出显示Perform属性的对话框
 * 
 * @function view_perform
 * @author 艾培东(aipeidong@vip.qq.com)
 * @param {PerformConstruct}
 */
function view_perform(perform) {
	var $dialog = $("#view-perform-dialog");
	if ($dialog.length == 0) {
		$dialog = $("<div id='view-perform-dialog'/>").appendTo($("body"));
	}
	$dialog.dialog({
		height:360,
		modal:true,
		width:480,
	});
}