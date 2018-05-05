$(function() {
	$("td.extensiondelete").click(function(){
		var extensionname= $(this).parent().children("td").eq(0).text();
		var extensionuser=$(this).parent().children("td").eq(1).text();
		if (confirm("是否删除extension?")) {
			var url = "/BPEP/deleteExtension?extensionname="+extensionname+"&extensionuser="+extensionuser;
			window.location.href = url;
		} else {
			return false;
		}
	});
}); 