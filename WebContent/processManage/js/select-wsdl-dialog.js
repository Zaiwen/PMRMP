$(function() {
    var $dialog = $("#dlg-wsdl-list");
    var dialogClose = function() {
	$dialog.children().remove();
	$("#dlg-wsdl-info").dialog("open");
    };
    var dialogOpen = function() {
	var ajaxSuccess = function(data) {
		$("#dlg-wsdl-list").empty();
			for(var i = 0; i < data.length; i ++ )
			{
				var _p = $("<p/>").text(data[i]);
				_p.click(function ()
				{
					$temp_wsdl_url = $(this).text(); 
					$("#dlg-wsdl-list").dialog("close");
					$("#dlg-wsdl-info").dialog("open");
				});
				$("#dlg-wsdl-list").append(_p);
			}
	};
	var ajaxError = function() {
	    $dialog.text("");
	};
	$dialog.text(language=="zh_CN"?"正在获取WSDL文件列表……":"loading...");
	$.ajax({
	    async : false,
	    cache : false,
	    url : "editor.jsp",
		data: {action:"wsdl-list"},
	    dataType : 'json',
	    success : ajaxSuccess,
	    error : ajaxError
	}); 
    };

    $dialog.dialog({
	autoOpen : false,
	close : dialogClose,
	height : 360,
	modal : true,
	open : dialogOpen,
	title : language=="zh_CN"?"可用的Web服务":"Avaliable Web Services",
	width : 480,
    });

});