var left_collapse = false;

$(function (){
	/*$("#shape-window .head .operation").click(function (){
		if($(this).text()=="<<"){
			$("#outline-window").hide();
			$("#shape-window").removeClass("max").addClass("min");
			$("#shape-window").css({"width":"60px"});
			$("#edit-window").css({"left":"60px"});
			$(this).text(">>");
		}else{
			$("#shape-window").removeClass("min").addClass("max");
			$("#shape-window").css({"width":"240px"});
			$("#edit-window").css({"left":"240px"});
			$("#outline-window").show();
			$(this).text("<<");
		}
	});*/
	
	$(document).on("click","#shape-window.max .head .operation",function (){
		$("#outline-window").hide();
		$("#shape-window").removeClass("max").addClass("min");
		$("#shape-window").css({"width":"60px"});
		$("#edit-window").css({"left":"60px"});
		left_collapse = true;
	});
	$(document).on("click","#shape-window.min .head .operation",function (){
		$("#shape-window").removeClass("min").addClass("max");
		$("#shape-window").css({"width":"240px"});
		$("#edit-window").css({"left":"240px"});
		$("#outline-window").show();
		left_collapse = false;
	});
	new mxOutline(graph,$("#outline-window .body")[0]);
});