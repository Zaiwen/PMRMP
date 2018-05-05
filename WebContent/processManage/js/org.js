var $dlg_ext_apply = null;

var $ext_list = [];

function ajax_apply() {
	var cfg = {};
	cfg.cache = true;
	cfg.success = ajax_apply_scc;
	cfg.error = ajax_apply_err;
	cfg.type = "POST";
	cfg.data = ajax_apply_data();
	cfg.dataType = "text";
	cfg.url = "ext-apply.jsp?";
	$.ajax(cfg);
	$(".statusbar").text(language=="zh_CN"?"正在应用插件……":"loading");
}
 
function ajax_apply_data(){
	var obj={};
	obj.Provider=$req_prv;
	obj.Process=$req_prc;
	obj.Name=$req_name;
	obj.json=JSON.stringify($ext_list);
	return obj;
}

function ajax_apply_scc(data){
	$(".statusbar").text(language=="zh_CN"?"插件应用成功！":"success");
	OWLModel.fromJson(JSON.parse(data));
	Redraw();
	UpdateProcessView();
	UpdateTreeView();
}

function ajax_apply_err(){
	$(".statusbar").text(language=="zh_CN"?"插件应用失败！":"failed");
}


function dlg_ext_apply_init() {
	var buts = [];
	var cfg = {};
	buts[0] = {};
	buts[0].text = language=="zh_CN"?"应用":"apply";
	buts[0].click = dlg_ext_apply_clk;
	cfg.autoOpen = false;
	cfg.buttons = buts;
	cfg.width = 640;
	cfg.height = 400;
	cfg.modal = true;
	cfg.resizable = false;
	$dlg_ext_apply = $("#extension-apply-dialog");
	$dlg_ext_apply.dialog(cfg);
}

function dlg_ext_apply_clk() {
	$ext_list = [];
	$(this).find("tr:gt(0)").each(function() {
		if ($(this).find("td input").attr("checked")) {
			var _user = $(this).find(".user").text();
			var _name = $(this).find(".name").text();
			$ext_list.push(_user);
			$ext_list.push(_name);
		}
	});
	ajax_apply();
	$(this).dialog("close");
}

$(function() {
	$("#editor>.toolbar>.apply").click(function() {
		$dlg_ext_apply.dialog("open");
	});
	dlg_ext_apply_init();
});