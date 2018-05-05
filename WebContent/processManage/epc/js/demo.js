var $user = "test";

function draw_icon_create(){
	var canvas = $(".icon.create")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(20,8);
	context.lineTo(20,32);
	context.moveTo(8,20);
	context.lineTo(32,20);
	context.moveTo(0,0);
	context.closePath();
	context.lineWidth = 4;
	context.strokeStyle = "#fff";
	context.stroke(); 
}

function draw_icon_upload(){
	var canvas = $(".icon.upload")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(20, 32);
	context.lineTo(20, 10);
	context.moveTo(10, 20);
	context.lineTo(20, 10);
	context.lineTo(30, 20);
	context.moveTo(20, 32);
	context.closePath();
	context.lineWidth = 4;
	context.strokeStyle = "#fff";
	context.stroke(); 
}

function draw_icon_redo(){
	var canvas = $(".icon.redo")[0];
	var context = canvas.getContext("2d");
	var radius = 1.5;
	var offset = 2;
	context.beginPath();
	context.arc(12*radius+offset,14*radius+offset,5*radius,Math.PI/2,Math.PI*3/2,false);
	context.lineTo(19*radius+offset,9*radius+offset);
	context.moveTo(15*radius+offset,5*radius+offset);
	context.lineTo(19*radius+offset,9*radius+offset);
	context.lineTo(15*radius+offset,13*radius+offset);
	context.moveTo(12*radius+offset,20*radius+offset);
	context.closePath();
	context.lineWidth=3;
	context.strokeStyle="#fff";
	context.stroke();
}

function draw_icon_refresh(){
	var canvas = $("canvas.refresh")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(12,14);
	context.lineTo(34,14);
	context.moveTo(6,26);
	context.lineTo(28,26);
	context.moveTo(0,0);
	context.closePath();
	context.lineWidth=4;
	context.strokeStyle="#fff";
	context.stroke();
	context.beginPath();
	context.moveTo(4,14);
	context.lineTo(12,8);
	context.lineTo(12,20);
	context.closePath();
	context.fillStyle="#fff";
	context.fill();
	context.beginPath();
	context.moveTo(36,26);
	context.lineTo(28,32);
	context.lineTo(28,20);
	context.closePath();
	context.fillStyle="#fff";
	context.fill();
}

function draw_icon_back(){
	var canvas = $(".icon.back")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(32, 20);
	context.lineTo(10, 20);
	context.moveTo(20, 10);
	context.lineTo(10, 20);
	context.lineTo(20, 30);
	context.moveTo(32, 20);
	context.closePath();
	context.lineWidth = 4;
	context.strokeStyle = "#fff";
	context.stroke(); 
}

function draw_icon_download (){
	var canvas = $(".icon.download")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(20, 8);
	context.lineTo(20, 30);
	context.moveTo(10, 20);
	context.lineTo(20, 30);
	context.lineTo(30, 20);
	context.moveTo(20, 8);
	context.closePath();
	context.lineWidth = 4;
	context.strokeStyle = "#fff";
	context.stroke(); 
}

function draw_icon_help (){
	$(".icon.help").each(function (){
		var context = this.getContext("2d");
		context.font = "32px Verdana";
		var width = context.measureText("?").width;
		context.fillStyle = "#fff";
		context.fillText("?",(40-width)/2,32);
	});
}

function draw_icon_undo(){
	var canvas = $(".icon.undo")[0];
	var context = canvas.getContext("2d");
	var radius = 1.5;
	var offset = 3;
	context.beginPath();
	context.arc(12*radius+offset,14*radius+offset,5*radius,Math.PI/2,Math.PI*3/2,true);
	context.lineTo(5*radius+offset,9*radius+offset);
	context.moveTo(9*radius+offset,5*radius+offset);
	context.lineTo(5*radius+offset,9*radius+offset);
	context.lineTo(9*radius+offset,13*radius+offset);
	context.moveTo(12*radius+offset,20*radius+offset);
	context.closePath();
	context.lineWidth=3;
	context.strokeStyle="#fff";
	context.stroke();
}


function draw_icon_view(){
	$(".icon.view").each(function (){
		var context = this.getContext("2d");
		context.beginPath();
		context.moveTo(4,20);
		context.arcTo(20,0,36,20,20);
		context.arcTo(20,40,4,20,20);
		context.closePath();
		context.lineWidth = 3;
		context.strokeStyle = "#fff";
		context.stroke();
		context.beginPath();
		context.arc(20,20,5,0,Math.PI*2);
		context.closePath();
		context.fillStyle = "#fff";
		context.fill();
	});
}

function draw_icon_more(){
	var canvas = $(".icon.more")[0];
	var context = canvas.getContext("2d");
	context.fillStyle = "#fff";
	context.fillRect(8,18,4,4);
	context.fillRect(18,18,4,4);
	context.fillRect(28,18,4,4);
}

function init_menu_create(){
	$(".window.create").click(function (e){
		e.stopPropagation();
	});
	$(".icon.create").click(function (e){
		$(".window").hide();
		$(".window.create input").val("").focusin().keyup();
		$(".window.create").show();
		e.stopPropagation();
	});
	$(document).click(function (){
		$(".window.create").hide();
	});
	$(".window.create input").keyup(function (){
		var val = $(this).val() + "";
		// 1、判断流程名是否为空，如果为空，不进行任何提示，但【创建】按钮不可用
		if(val == ""){
			$(".window.create .tip").hide();
			$(".window.create .button").addClass("disabled");	
			return;
		}
		// 2、判断流程名是否包含特殊字符，如果有，则提示，此时【创建】按钮不可用
		var arr = ['*', '?', '\\', '\/', '"', '\'', ':', '<', '>', '|'];
		var flag = false;
		for(var i = 0; i < arr.length; i++){
			if(val.indexOf(arr[i]) > -1){
				flag = true;
				break;
			}
		}
		if(flag){
			$(".window.create .button").addClass("disabled");
			$(".window.create .tip").text("流程名不能包含以下字符 " + arr.join(" ")).show();
			return;
		}
		// 3、判断流程名开始和结束是否为空格（包含制表、换行等所有空字符），如果有，则提示，此时【创建】按钮不可用
		if($.trim(val) != val){
			$(".window.create .button").addClass("disabled");
			$(".window.create .tip").text("流程名的开始和结束不能为空格 ").show();
			return;
		}
		// 4、判断流程名的长度，不得超过100个字符
		if(val.length > 100){
			$(".window.create .button").addClass("disabled");
			$(".window.create .tip").text("流程名长度限制为100个字符 ").show();
			return;
		}
		// 如果以上检查均通过则不显示提醒，【创建】按钮可用
		$(".window.create .tip").hide();
		$(".window.create .button").removeClass("disabled");
	});
	$(".window.create .button").click(function (){
		if(!$(this).hasClass("disabled")){
			var val = $(".window.create input").val();
			$.ajax("jsp/create.jsp",{
				data : {name:val,user:$user},
				dataType:"text",
				cache:false,
				success:function (data){
					data = $.trim(data);
					if(data == "true"){
						$("#manage").hide();
						$("#editor").show();
						$("#editor .header .name input").val(val);
					}
//					else if(data == "false"){
//						$(".window.create .tip").text("").show();
//						$(".window.create .button").addClass("disabled");
//					}else{
//						$(".window.create .tip").text("未知错误").show();
//						$(".window.create .button").addClass("disabled");
//					}
				},
				error:function (data){
					$(".window.create .tip").text("该名称已存在").show();
					$(".window.create .button").addClass("disabled");
				}
			})
		}
	});
}

function init_menu_download(){
	$(".list.download").click(function (e){
		e.stopPropagation();
	});
	$(".icon.download").click(function (e){
		$(".window").hide();
		$(".list.download").show();
		e.stopPropagation();
	});
	$(document).click(function (){
		$(".list.download").hide();
	});
}

function init_menu_view(){
	$(".list.view").click(function (e){
		e.stopPropagation();
	});
	$(".icon.view").click(function (e){
		$(".list").hide();
		$(".list.view").show();
		e.stopPropagation();
	});
	$(document).click(function (){
		$(".list.view").hide();
	});
}

function init_menu_more(){
	$(".list.more").click(function (e){
		e.stopPropagation();
	});
	$(".icon.more").click(function (e){
		$(".list").hide();
		$(".list.more").show();
		e.stopPropagation();
	});
	$(document).click(function (){
		$(".list.more").hide();
	});
}

function draw_icon_event(){
	var canvas = $("canvas.event")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(10,1);
	context.lineTo(70,1);
	context.lineTo(79,20);
	context.lineTo(70,39);
	context.lineTo(10,39);
	context.lineTo(1,20);
	context.closePath();
	context.fillStyle="rgb(255,147,147)";
	context.fill();
	context.lineWidth=2;
	context.strokeStyle="#000";
	context.stroke();
	context.fillStyle="#000";
	context.font="14px Consolas";
	context.fillText("Event",40-context.measureText("Event").width/2,24);
}

function draw_icon_function()
{
	var canvas = $("canvas.function")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(10,1);
	context.lineTo(70,1);
	context.arc(70,10,9,Math.PI*3/2,Math.PI*2,false);
	context.lineTo(79,10);
	context.lineTo(79,30);
	context.arc(70,30,9,0,Math.PI/2,false);
	context.lineTo(70,39);
	context.lineTo(10,39);
	context.arc(10,30,9,Math.PI/2,Math.PI,false);
	context.lineTo(1,30);
	context.lineTo(1,10);
	context.arc(10,10,9,Math.PI,Math.PI*3/2,false);
	context.lineTo(10,1);
	context.closePath();
	context.fillStyle = "#9f9";
	context.fill();
	context.lineWidth=2;
	context.stroke();
	context.fillStyle="#000";
	context.font="14px Consolas";
	context.fillText("Function",40-context.measureText("Function").width/2,24);
}

function draw_icon_role(){
	var canvas = $("canvas.role")[0];
	var context=canvas.getContext("2d");
	context.rect(1,1,78,28);
	context.fillStyle = "rgb(255,255,153)";
	context.fill();
	context.lineWidth = 2;
	context.strokeStyle = "#000";
	context.stroke();
	context.fillStyle="#000";
	context.fillRect(10,0,2,40);
	context.font="14px Consolas";
	context.fillText("Role",45-context.measureText("Role").width/2,19);
}

function draw_icon_object(){
	var canvas = $("canvas.object")[0];
	var context=canvas.getContext("2d");
	context.rect(1,1,78,28);
	context.fillStyle = "rgb(206,231,255)";
	context.fill();
	context.lineWidth = 2;
	context.strokeStyle = "#000";
	context.stroke();
	context.fillStyle="#000";
	context.font="14px Consolas";
	context.fillText("Object",40-context.measureText("Object").width/2,19);
}

function draw_icon_and(){
	var canvas =$("canvas.and")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.arc(15,15,14,0,Math.PI*2,true);
	context.closePath();
	context.fillStyle="rgb(230,230,230)";
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.strokeStyle="#000";
	context.stroke();
	context.beginPath();
	context.moveTo(8,22);
	context.lineTo(15,8);
	context.lineTo(22,22);
	context.moveTo(8,22);
	context.closePath();
	context.stroke();
}

function draw_icon_or(){
	var canvas =$("canvas.or")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.arc(15,15,14,0,Math.PI*2,true);
	context.closePath();
	context.fillStyle="rgb(230,230,230)";
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.strokeStyle="#000";
	context.stroke();
	context.beginPath();
	context.moveTo(8,8);
	context.lineTo(15,22);
	context.lineTo(22,8);
	context.moveTo(8,8);
	context.closePath();
	context.stroke();
}

function draw_icon_xor(){
	var canvas =$("canvas.xor")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.arc(15,15,14,0,Math.PI*2,true);
	context.closePath();
	context.fillStyle="rgb(230,230,230)";
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.strokeStyle="#000";
	context.stroke();
	context.beginPath();
	context.moveTo(8,8);
	context.lineTo(22,22);
	context.moveTo(8,22);
	context.lineTo(22,8);
	context.moveTo(8,8);
	context.closePath();
	context.stroke();
}

function draw_icon_requirement(){
	var canvas = $("canvas.requirement")[0];
	var context = canvas.getContext("2d");
	context.beginPath();
	context.moveTo(10,1);
	context.lineTo(119,1);
	context.lineTo(119,39);
	context.lineTo(10,39);
	context.lineTo(1,30);
	context.lineTo(1,10);
	context.closePath();
	context.fillStyle="rgb(230,230,230)";
	context.fill();
	context.lineWidth=2;
	context.strokeStyle="#000";
	context.stroke();
	context.beginPath();
	context.arc(10,20,5,0,Math.PI*2);
	context.closePath();
	context.stroke();
	context.fillStyle="#000";
	context.fillRect(18,0,2,40);
	context.font="14px Consolas";
	context.fillText("Requirement",70-context.measureText("Requirement").width/2,24);
}
/**
 * 判断流程名是否已经在数据库中存在，需要后台确认，不能直接判断前端数据
 * @param name
 * @param callback {Function(Boolean)}
 * */
function is_name_exist(name, callback){
	
}

$(function (){
	draw_icon_create();
	draw_icon_upload();
	draw_icon_help();
	draw_icon_view();
	draw_icon_refresh();
	draw_icon_back();
	draw_icon_download();
	draw_icon_undo();
	draw_icon_redo();
	draw_icon_more();
	
	init_menu_create();
	init_menu_download();
	init_menu_view();
	init_menu_more();
	
	draw_icon_event();
	draw_icon_function();
	draw_icon_role();
	draw_icon_object();
	draw_icon_and();
	draw_icon_or();
	draw_icon_xor();
	draw_icon_requirement();
})