$(function() {
	$("td.userallow").click(function() {
		var name= $(this).parent().children("td").eq(2).text();
		var databasetype=$(this).parent().children("td").eq(3).text();
		databasetype=databasetype+"user";
		if (confirm("是否允许用户通过认证?")) {
			var url = "/BPEP/passUseringcheck?name="+name+"&databasetype="+databasetype;
			window.location.href = url;
		} else {
			return false;
		}
	});
});