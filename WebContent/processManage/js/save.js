$(function() {
    // 是否正在保存文件
    var saving = false;
    // 每隔十分钟都自动保存一次（后台）
    setInterval(function() {
	saving = true;
	$("#editor .statusbar").text(language=="zh_CN"?"正在执行自动保存……":"saving..");
	$.ajax({ 
	    async : true,
	    url : "Save.jsp?",
	    mimeType : 'text/plain',
	    type : "POST",
	    data : "content=" + OWLModel.toJson(),
	    success : function() {
		$("#editor .statusbar").text(language=="zh_CN"?"保存成功！":"save success");
		// 10秒钟之后清除文本
		setTimeout(function() {
		    $("#editor .statusbar").text("");
		    saving = true;
		}, 10 * 1000);
	    },
	    error : function() {
		$("#editor .statusbar").text(language=="zh_CN"?"保存失败！":"save failed");
		// 10秒钟之后清除文本
		setTimeout(function() {
		    $("#editor .statusbar").text("");
		    saving = true;
		}, 10 * 1000);
	    }
	});
    }, 10 * 60 * 1000);

}); 