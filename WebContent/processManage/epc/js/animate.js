
$(function (){
	$("#tool-wsdl,#tool-ontology").click(function (){
		animate_property_edit();
	});
	$("#tool-save").click(function (){
		animate_property_close();
	});
});

function animate_property_edit(){
	$("#shape-window").animate({
		left : "-64px"
	});
	$("#edit-window").animate({
		left : '0px',
		width : ($("body").width()/2 - 2) + "px"
	},"slow");
	$("#property-window").show().animate({
		width : ($("body").width()/2 - 2) + "px"
	},"slow");
}

function animate_property_close(){
	$("#shape-window").animate({
		left : "0px"
	});
	$("#edit-window").animate({
		left : '64px',
		width : ($("body").width()-64) + "px"
	},"slow",function (){
		$(this).css("width","auto");
	});
	$("#property-window").animate({
		width :"0px"
	},"slow",function (){
		$(this).hide();
	});
}