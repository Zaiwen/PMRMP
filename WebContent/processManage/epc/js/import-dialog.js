$(function (){
	// 点击上方的【导入本地文件】按钮出现对话框
	var $dialog = $("#import-dialog");
	$(".import").click(function (){
		$dialog.show();
		$("#dialog-cover").show();
	});
	// 窗口调整时对话框自动居中
	$(window).resize(function() {
		var _left = ($(document).width() - $dialog.width()) / 2;
		var _top = ($(document).height() - $dialog.height()) / 2;
		$dialog.css("left", _left + "px");
		$dialog.css("top", _top + "px");
	});
	$(window).resize();
	// 复选框的选中与取消
//	$dialog.on("click",".content .item",function (){
//		if($(this).hasClass("checked")){
//			$(this).removeClass("checked");
//		}else{
//			$(this).addClass("checked");
//		}
//		show_num();
//	});
	// 全选与反选操作
//	$("#import-dialog .operation .select-all").click(function (){
//		$("#import-dialog .content .item:not(.checked)").addClass("checked");
//		show_num();
//	});
//	$("#import-dialog .operation .inverse").click(function (){
//		$("#import-dialog .content .item").each(function (){
//			if($(this).hasClass("checked")){
//				$(this).removeClass("checked");
//			}else{
//				$(this).addClass("checked");
//			}
//		});
//		show_num();
//	});
	// 选中操作
	$("#import-dialog .content").on("click",".item",function (){
		$("#import-dialog .content .selected").removeClass("selected");
		$(this).addClass("selected");
		//console.log($(this).find(".id").text());
	});
	// 打开文件
	var reader=new FileReader();
	$("#import-dialog .file").change(function (e){
		var file=e.target.files[0];
		if(file){
			$("#import-dialog .text").text(file.name);
			$("#import-dialog .operation .label").text("正在打开文件……");
			reader.readAsText(file);
			reader.onloadend=function (){
				$("#import-dialog .content").empty();
				$(reader.result).find("epc").each(function (){
					var _div=$("<div class='item'></div>");
					_div.append($("<div class='id'/>").text($(this).attr("epcId")));
					_div.append($("<div class='name'/>").text($(this).attr("name")));
					_div.appendTo($("#import-dialog .content"));
				});
				show_num();
			};
			reader.onerror=function(){
				alert("Error!");
			};
		}
	});
	// 对话框的关闭
	$("#import-dialog .head .close").click(dialog_close);
	$("#import-dialog .foot .cancel").click(dialog_close);
	// 导入
	$("#import-dialog .foot .ok").click(function (){
		if($("#import-dialog .content .selected").length==0){
			alert("没有选择任何流程！");
		}else{
			var _id=$("#import-dialog .content .selected .id").text();
			var _name=$("#import-dialog .content .selected .name").text();
			var _epc=$(reader.result).find("epc[epcId="+_id+"]").html();
			EpcEditor.decodeEpml("<epml><epc name='"+_name+"'>"+_epc+"</epc></epml>");
			epc_style=0;
			dialog_close();
		}
	});















	function show_num(){
		var n1=$("#import-dialog .content .item").length;
		var n2=$("#import-dialog .content .item.checked").length;
		var html="共有<span>"+n1+"</span>个流程，已选择<span>"+n2+"</span>个";
		$("#import-dialog .operation .label").html(html);
	}

	function dialog_close(){
		$("#import-dialog").hide();
		$("#dialog-cover").hide();
		$("#import-dialog .text").text("null");
		$("#import-dialog .file").val("");
		$("#import-dialog .operation .label").text(" ");
		$("#import-dialog .content").empty();
	}



});