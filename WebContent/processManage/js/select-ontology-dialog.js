var $dlg_ont_list=null;

var $dlg_ont_class=null;

var $ont_list=null;

var $ont_uri=null;

var $ont_class=null;

function ajax_ont_list(){
	var ajax={};
	// ajax.async=false;
	ajax.dataType="json";
	ajax.error=ajax_ont_list_err;
	ajax.url="get-ont-list.jsp";
	ajax.success=ajax_ont_list_scc;
	$.ajax(ajax);
}
  
function ajax_ont_list_scc(data){ 
	var _table=$("<table/>");
	var _tr=$("<tr/>").appendTo(_table);
	$(language=="zh_CN"?"<th>名称</th>":"<th>Name</th>").appendTo(_tr);
	$(language=="zh_CN"?"<th>领域</th>":"<th>Domain</th>").appendTo(_tr);
	$("<th>URI</th>").appendTo(_tr);
	for(var i=0;i<data.length;i+=1){
		var _name=data[i].name;
		var _domain=data[i].domain;
		var _uri=data[i].uri;
		var _td1=$("<td class='name'/>").text(_name);
		var _td2=$("<td class='domain'/>").text(_domain);
		var _td3=$("<td class='uri'/>").text(_uri);
		var _tr=$("<tr class='ontology'/>");
		_tr.append(_td1).append(_td2).append(_td3);
		_tr.click(dlg_ont_list_clk);
		_table.append(_tr);
	}
	$ont_list=data;
	$dlg_ont_list.empty().append(_table);
}

function ajax_ont_list_err(){
	$dlg_ont_list.empty().text(language=="zh_CN"?"信息获取失败，请联系管理员！":"Error");
}

function ajax_ont_class(){
	var ajax={};
	// ajax.async=false;
	ajax.data="uri="+escape($ont_uri);
	ajax.dataType="json";
	ajax.error=ajax_ont_class_err;
	ajax.type="POST";
	ajax.url="get-ont-class.jsp";
	ajax.success=ajax_ont_class_scc;
	$.ajax(ajax);
}


function ajax_ont_class_scc(data){
	util_arr_str_sort(data);
	var _ul=$("<ul/>");
	for(var i=0;i<data.length;i+=1) {
		var _chk=$("<input type='radio'/>");
		var _span=$("<span/>").text(data[i]);
		var _li=$("<li/>").append(_chk).append(_span);
		_chk.click(dlg_ont_class_clk);
		_ul.append(_li);
	}
	$dlg_ont_class.empty().append(_ul);
}

function ajax_ont_class_err(){
	$dlg_ont_class.text(language=="zh_CN"?"无法获取类信息！":"Error");
}

function dlg_ont_list_init(){
	var cfg={};
	cfg.autoOpen=false;
	cfg.close=dlg_ont_list_close;
	cfg.height=400;
	cfg.modal=true;
	cfg.open=dlg_ont_list_open;
	cfg.resizable=false;
	cfg.title=language=="zh_CN"?"选择本体":"Select ontology";
	cfg.width=800;
	$dlg_ont_list=$("#dlg-ont-list");
	$dlg_ont_list.dialog(cfg);
}

function dlg_ont_list_open(){
	if($ont_list==null){
		ajax_ont_list();
	}
}

function dlg_ont_list_close(){
	if($ont_list==null){
		$dlg_ont_list.text("正在获取可用的本体列表……");
	}
}

function dlg_ont_list_clk(){
	$ont_uri=$(this).find(".uri").text();
	$dlg_ont_list.dialog("close");
	$dlg_ont_class.dialog("open");
}

function dlg_ont_class_init(){
	var cfg={};
	var buts=[];
	buts[0]={};
	buts[1]={};
	buts[0].text=language=="zh_CN"?"确定":"ok";
	buts[1].text=language=="zh_CN"?"返回":"cancel";
	buts[0].click=dlg_ont_class_ok;
	buts[1].click=dlg_ont_class_cancel;
	cfg.autoOpen=false;
	cfg.buttons=buts;
	cfg.close=dlg_ont_class_close;
	cfg.height=400;
	cfg.modal=true;
	cfg.open=dlg_ont_class_open;
	cfg.resizable=false;
	cfg.title=language=="zh_CN"?"选择类":"Select Class";
	cfg.width=640;
	$dlg_ont_class=$("#dlg-ont-class");
	$dlg_ont_class.dialog(cfg);
}

function dlg_ont_class_open(){
	if($ont_uri==null){
		$dlg_ont_class.text(language=="zh_CN"?"没有选择本体！":"no ontology was selected");
		ajax_ont_class();
	}else{
		ajax_ont_class();
	}
}

function dlg_ont_class_close(){
	$dlg_ont_class.text(language=="zh_CN"?"正在获取本体中所有类信息……":"loading..");
	$ont_class=null;
}

function dlg_ont_class_clk(){
	if($(this).attr("checked")){
		$dlg_ont_class.find("input").removeAttr("checked");
		$(this).attr("checked","checked");
		$ont_class=$ont_uri+$(this).parent().text();
	}
}

function dlg_ont_class_ok(){
	if($ont_class==null)
	{
		alert(language=="zh_CN"?"请先选择一个类！":"please select a Class at first");
	}
	else if($temp_owls_param == null)
	{
		alert(language=="zh_CN"?"程序出错啦~\n$temp_owls_param的值为空！":"Error");
	}
	else
	{
		$temp_owls_param.setType($ont_class);
		$dlg_ont_class.dialog("close");
		$dlg_ato_proc.dialog("open");
	}
}

function dlg_ont_class_cancel(){
	
}
// 对字符串数组进行排序（冒泡排序）
function util_arr_str_sort(arr){
	for(var i=0;i<arr.length;i+=1){
		arr[i]=arr[i]+"";
	}
	for(var i=0;i<arr.length;i+=1){
		for(var j=0;j<arr.length-1;j+=1){
			if(arr[j].localeCompare(arr[j+1])>0){
				var tmp=arr[j];
				arr[j]=arr[j+1];
				arr[j+1]=tmp;
			}
		}
	}
}

$(function (){
	dlg_ont_list_init();
	dlg_ont_class_init();
});

$(function() {
    var $dialog = $("#select-ontology-dialog");
    var param = null;
    var dialogButtons = [ {
	text : language=="zh_CN"?"确定":"ok",
	click : function() {
	    var nodes = $dialog.jstree('get_selected');
	    if (nodes.length == 0) {
		alert(language=="zh_CN"?'请先进行选择！':"select at first");
	    } else { 
		var text = nodes.eq(0).children('a').text();
		if (param != null) {
		    text = $.trim(text);
		    param.setType(text);
		    $("#param-dialog>.type>.value").text(text);
		}
		$dialog.dialog("close");
	    }
	}
    }, {
	text :language=="zh_CN"? "取消":"cancel",
	click : function() {
	    $dialog.dialog("close");
	}
    } ];
    var dialogClose = function() {
	$dialog.children().remove();
    };
    var dialogOpen = function() {
	var model = new OntModel();
	var tree = new OntModelTreeView(model).getHtml();
	$(tree).appendTo($dialog);
	$dialog.jstree({ 
	    "themes" : {
		"theme" : "classic",
		"dots" : true,
		"icons" : false
	    },
	    "ui" : {
		"select_limit" : 1,
		"select_prev_on_delete" : true
	    },
	    "core" : {
		"animation" : 0,
	    },
	    "plugins" : [ "themes", "html_data", "crrm", "ui" ]
	});
	param = $dialog[0].param;
    };
    $dialog.dialog({
	autoOpen : false,
	buttons : dialogButtons,
	close : dialogClose,
	height : 360,
	modal : true,
	open : dialogOpen,
	resizable : false,
	width : 740
    });

});