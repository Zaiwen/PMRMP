$(function (){
	$("#menubar>.item").click(function (){
		$("#toolbar>.section").hide();
		$("#menubar>.item.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	$("#menubar>.item.start").click(function (){
		$("#toolbar>.section.start").show();
	});
	$("#menubar>.item.design").click(function (){
		$("#toolbar>.section.design").show();
	});
	$("#menubar>.item.view").click(function (){
		$("#toolbar>.section.view").show();
	});
	$("#menubar>.item.configuration").click(function (){
		$("#toolbar>.section.configuration").show();
	});
	$("#menubar>.item.help").click(function (){
		$("#toolbar>.section.help").show();
	});
});