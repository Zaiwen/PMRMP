$(function() {
	$("td.userdelete").click(function() {
		var name= $(this).parent().children("td")[2].innerHTML;
		var databasetype=$(this).parent().children("td")[3].innerHTML;
		databasetype=databasetype+"user";
		if (confirm("是否删除此用户?")) {
			var url = "/BPEP/deleteUseringcheck?name="+name+"&databasetype="+databasetype;
			window.location.href = url;
		} else {
			return false;
		}
	});
}); 