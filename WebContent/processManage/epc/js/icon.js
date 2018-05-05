/**
 * 利用HTML5 Canvas来生成各种图标
 * element为图标所在的canvas元素ID
 * selected表明该图标是否被选中
 */
var icon_color = "#3955A3";

function icon_event(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.rect(0,0,48,32);
	//context.fill();
	context.beginPath();
	context.moveTo(10,5);
	context.lineTo(37,5);
	context.lineTo(42,15);
	context.lineTo(37,26);
	context.lineTo(10,26);
	context.lineTo(5,15);
	context.closePath();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.fill();
}

function icon_function(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	context.beginPath();
	context.moveTo(10,5);
	context.lineTo(37,5);
	context.arc(37,10,5,Math.PI*3/2,Math.PI*2,false);
	context.lineTo(42,10);
	context.lineTo(42,21);
	context.arc(37,21,5,0,Math.PI/2,false);
	context.lineTo(37,26);
	context.lineTo(10,26);
	context.arc(10,21,5,Math.PI/2,Math.PI,false);
	context.lineTo(5,21);
	context.lineTo(5,10);
	context.arc(10,10,5,Math.PI,Math.PI*3/2,false);
	context.lineTo(10,5);
	context.closePath();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.fill();
}

function icon_unit(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	var x=24,y=16,a=20,b=12;
	var k = .5522848;
	var ox = a * k; // 水平控制点偏移量 
	var oy = b * k; // 垂直控制点偏移量 
	context.beginPath(); 
	//从椭圆的左端点开始顺时针绘制四条三次贝塞尔曲线 
	context.moveTo(x - a, y); 
	context.bezierCurveTo(x - a, y - oy, x - ox, y - b, x, y - b); 
	context.bezierCurveTo(x + ox, y - b, x + a, y - oy, x + a, y); 
	context.bezierCurveTo(x + a, y + oy, x + ox, y + b, x, y + b); 
	context.bezierCurveTo(x - ox, y + b, x - a, y + oy, x - a, y); 
	context.closePath(); 
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.fill();
}

function icon_data(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	context.beginPath();
	context.moveTo(5,5);
	context.lineTo(43,5);
	context.lineTo(43,27);
	context.lineTo(5,27);
	context.closePath();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.fill();
}

function icon_and(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.strokeStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.arc(24,16,12,0,Math.PI*2,true);
	context.closePath();
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.beginPath();
	context.moveTo(19,20);
	context.lineTo(24,10);
	context.closePath();
	context.stroke();
	context.beginPath();
	context.moveTo(24,10);
	context.lineTo(29,20);
	context.closePath();
	context.stroke();
}

function icon_or(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.strokeStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.arc(24,16,12,0,Math.PI*2,true);
	context.closePath();
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.beginPath();
	context.moveTo(19,12);
	context.lineTo(24,22);
	context.closePath();
	context.stroke();
	context.beginPath();
	context.moveTo(24,22);
	context.lineTo(29,12);
	context.closePath();
	context.stroke();
}

function icon_xor(element,selected)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.moveTo(0,0);
	context.lineTo(48,0);
	context.lineTo(48,32);
	context.lineTo(0,32);
	context.closePath();
	//context.fill();
	context.fillStyle = selected ? "#FFFFFF" : icon_color;
	context.strokeStyle = selected ? icon_color : "#FFFFFF";
	context.beginPath();
	context.arc(24,16,12,0,Math.PI*2,true);
	context.closePath();
	context.fill();
	context.lineWidth=2;
	context.lineJoin="round";
	context.beginPath();
	context.moveTo(19,21);
	context.lineTo(29,11);
	context.closePath();
	context.stroke();
	context.beginPath();
	context.moveTo(19,11);
	context.lineTo(29,21);
	context.closePath();
	context.stroke();
}

function icon_menu(element)
{
	var canvas = document.getElementById(element);
	var context = canvas.getContext("2d");
	context.fillStyle = "#FFFFFF";
	context.rect(0,0,8,8);
	context.rect(0,12,8,8);
	context.rect(0,24,8,8);
	context.rect(12,0,8,8);
	context.rect(12,12,8,8);
	context.rect(12,24,8,8);
	context.rect(24,0,8,8);
	context.rect(24,12,8,8);
	context.rect(24,24,8,8);
	context.fill();
}

$(function (){
	//$("#shape-window").css("background-color","#FF9900");
	//icon_color=$("#shape-window").css("background-color");
	icon_event("shape-event");
	icon_function("shape-function");
	icon_unit("shape-unit");
	icon_data("shape-data");
	icon_and("shape-and");
	icon_or("shape-or");
	icon_xor("shape-xor");
	//icon_menu("icon-menu");
})