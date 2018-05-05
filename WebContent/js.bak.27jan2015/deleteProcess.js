$(function() {
	$("td.processdelete").click(function(){
		var processname= $(this).parent().children("td").eq(0).text();
		var processuser=$(this).parent().children("td").eq(1).text();
		if (confirm("是否删除process?")) {
			var url = "/BPEP/deleteProcess?processname="+processname+"&processuser="+processuser;
			window.location.href = url;
		} else {
			return false;
		}
	});
});