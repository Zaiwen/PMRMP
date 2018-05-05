$(function() {
	$("td.plugindelete").click(function(){
		var pluginname= $(this).parent().children("td").eq(0).text();
		var pluginuser=$(this).parent().children("td").eq(1).text();
		if (confirm("是否删除plugin?")) {
			var url = "/BPEP/deletePlugin?pluginname="+pluginname+"&pluginuser="+pluginuser;
			window.location.href = url;
		} else {
			return false;
		}
	});
});