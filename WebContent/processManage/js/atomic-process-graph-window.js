/**
 * 初始化
 * */
$(function (){
	$atomic_process_graph=new mxGraph($("#atomic-process-graph-window>.body")[0]);
	$("#editor>.toolbar>.add-precondition").click(function (){
		$atomic_process_selected.addPrecondition(new Condition());
		atomic_process_graph_refresh();
	});
	$("#editor>.toolbar>.add-postcondition").click(function (){
		$atomic_process_selected.addPostcondition(new Condition());
		atomic_process_graph_refresh();
	});
	$("#atomic-process-graph-window>.body").mouseup(function (){
		setTimeout(function (){
			var cell = $atomic_process_graph.getSelectionCell();
			if(cell != null){
				atomic_process_detail_refresh(cell.type, cell.binding);
			}else{
				atomic_process_detail_refresh(null,{});
			}
		},50);
	});
});

var $atomic_process_graph = null;

function atomic_process_graph_refresh(){
	var process = $atomic_process_selected;
	var graph = $atomic_process_graph;
	var parent = graph.getDefaultParent();
	var style0 = "strokeColor=#66ff66;fillColor=#aaffaa;strokeWidth=1;fontSize=14;fontColor=#000;shape=ellipse;perimeter=ellipsePerimeter";
	var style1 = 'strokeColor=#66ffff;fillColor=#aaffff;strokeWidth=1;fontSize=12;fontColor=#000;shape=ellipse;perimeter=ellipsePerimeter';
	var style2 = 'strokeColor=#ff6666;fillColor=#ffaaaa;strokeWidth=1;fontSize=12;fontColor=#000;shape=ellipse;perimeter=ellipsePerimeter';
	var style3 = 'strokeColor=#000;strokeWidth=1;';
	// 删除画布上原有的所有图元
	graph.selectAll();
	graph.removeCells();
	// 对原本的数据进行处理以便更好地进行图的操作
	var input = new Array();
	var output = new Array();
	var precondition = new Array();
	var postcondition = new Array();
	for(var i in process.getInput()){
		var obj = new Object();
		obj.binding = process.getInput()[i];
		obj.height = 30;
		obj.text = i.toString();
		obj.type = "input";
		obj.width = get_text_width(i.toString(),{"font-size":"12px"}) + 20;
		input.push(obj);
	}
	for(var i in process.getOutput()){
		var obj = new Object();
		obj.binding = process.getOutput()[i];
		obj.height = 30;
		obj.text = i.toString();
		obj.type = "output";
		obj.width = get_text_width(i.toString(),{"font-size":"12px"}) + 20;
		output.push(obj);
	}
	for(var i = 0; i < process.getPrecondition().length; i++){
		var obj = new Object();
		obj.binding = process.getPrecondition()[i];
		obj.height = 30;
		obj.text = obj.binding.toString();
		obj.type = "precondition";
		obj.width = get_text_width(obj.text,{"font-size":"12px"}) + 30;
		precondition.push(obj);
	}
	for(var i = 0; i < process.getPostcondition().length; i++){
		var obj = new Object();
		obj.binding = process.getPostcondition()[i];
		obj.height = 30;
		obj.text = obj.binding.toString();
		obj.type = "postcondition";
		obj.width = get_text_width(obj.text,{"font-size":"12px"}) + 30;
		postcondition.push(obj);
	}
	// 对所有图元进行圆形布局，输入输出各占90°，条件各占120°
	// 为保证图元不重合，现在计算最小半径
	var r = 0, r1 = 0, r2 = 0, r3 = 0, r4 = 0;
	for(var i = 0; i < input.length; i++){
		r1 = r1 + input[i].width + 20;
	}
	for(var i = 0; i < output.length; i++){
		r2 = r2 + output[i].width + 20;
	}
	for(var i = 0; i < precondition.length; i++){
		r3 = r3 + precondition[i].width;
	}
	for(var i = 0; i < postcondition.length; i++){
		r4 = r4 + postcondition[i].width;
	}
	r = Math.max(Math.max(r1, r2), Math.max(r3, r4) / Math.sqrt(3));
	// 计算最大的输入宽度作为圆形的水平右偏移量
	// 输入全部放在左边
	var offset = 0;
	for(var i = 0; i < input.length; i++){
		offset = Math.max(offset, input[i].width);
	}
	offset += 10;
	// 画出圆心代表整个原子流程的图形
	var x = offset + r;
	var y = 40 + r;
	var width = get_text_width(process, {"font-size":"14px"}) * 2;
	var cell0 = graph.insertVertex(parent, null, process, x - width / 2, y - 20, width, 40, style0);
	// 画出所有Input
	for(var i = 0; i < input.length; i++){
		var angle = (30 - 60 / (input.length + 1) * (i + 1)) / 180 * Math.PI;
		var cx = x - r * Math.cos(angle) - input[i].width;
		var cy = y - r * Math.sin(angle) - input[i].height / 2;
		var cell1 = graph.insertVertex(parent, null, input[i].text, cx, cy, input[i].width, input[i].height, style1);
		cell1.type = input[i].type;
		cell1.binding = input[i].binding;
		graph.insertEdge(parent, null, null, cell1, cell0, style3);
	}
	// 画出所有Output
	for(var i = 0; i < output.length; i++){
		var angle = (30 - 60 / (output.length + 1) * (i + 1)) / 180 * Math.PI;
		var cx = x + r * Math.cos(angle);
		var cy = y - r * Math.sin(angle) - output[i].height / 2;
		var cell1 = graph.insertVertex(parent, null, output[i].text, cx, cy, output[i].width, output[i].height, style1);
		cell1.type = output[i].type;
		cell1.binding = output[i].binding;
		graph.insertEdge(parent, null, null, cell0, cell1, style3);
	}
	// 画出所有Precondition
	for(var i = 0; i < precondition.length; i++){
		var angle = (60 - 120 / (precondition.length + 1) * (i + 1)) / 180 * Math.PI;
		var cx = x + r * Math.sin(angle) - precondition[i].width / 2;
		var cy = y - r * Math.cos(angle) - precondition[i].height;
		var cell1 = graph.insertVertex(parent, null, precondition[i].text, cx, cy, precondition[i].width, precondition[i].height, style2);
		cell1.type = precondition[i].type;
		cell1.binding = precondition[i].binding;
		graph.insertEdge(parent, null, null, cell1, cell0, style3);
	}
	// 画出所有Postcondition
	for(var i = 0; i < postcondition.length; i++){
		var angle = (60 - 120 / (postcondition.length + 1) * (i + 1)) / 180 * Math.PI;
		var cx = x + r * Math.sin(angle) - postcondition[i].width / 2;
		var cy = y + r * Math.cos(angle) - postcondition[i].height;
		var cell1 = graph.insertVertex(parent, null, postcondition[i].text, cx, cy, postcondition[i].width, postcondition[i].height, style2);
		cell1.type = postcondition[i].type;
		cell1.binding = postcondition[i].binding;
		graph.insertEdge(parent, null, null, cell0, cell1, style3);
	}
	atomic_process_detail_refresh();
}
/**
 * test
 *
$(function (){
	$(".change-view").click();
	$atomic_process_selected=new AtomicProcess("TEST");
	$atomic_process_selected.input={
			"paafsfd":{
				"id":"paramter1avg",
				"type":"www.xxxxx.yyy/aaaaaaaaaaa"
			},
			"paramsdhg":{
				"id":"paramter2er",
				"type":"www.xxxxx.yyy/dsgsdzvfsd"
			},
			"paraafdsffds":{
				"id":"paramter3adgef",
				"type":"www.xxxxx.yyy/adaegdbds"
			},
			"pardsdg":{
				"id":"paramter4dhdfssfadf",
				"type":"www.xxxxx.yyy/dsgsvafaefa"
			},
	};
	$atomic_process_selected.output={
			"sfni":{
				"id":"xzgfsfa",
				"type":"sddfcsfsssvcxfv"
			},
			"bbfgbs":{
				"id":"xzgfsfa",
				"type":"sddfcsfsssvcxfv"
			},
			"zfvcx":{
				"id":"xzgfsfa",
				"type":"sddfcsfsssvcxfv"
			},
			"srdzfbcxb":{
				"id":"xzgfsfa",
				"type":"sddfcsfsssvcxfv"
			},
			
	};
	$atomic_process_selected.precondition = [
	  new Condition("aaaaa","sdgffsdfsds","dsgfdfss"),
	  new Condition("bbbb","gzd","dff"),
	  new Condition("cccc","dvfd","fffcxvx"),
	  new Condition("dddd","qeqer","dsfs"),
			];
	$atomic_process_selected.postcondition =  [
 	  new Condition("aaaaa","sdgffsdfsds","dsgfdfss"),
	  new Condition("bbbb","gzd","dff"),
	  new Condition("cccc","dvfd","fffcxvx"),
			];
	atomic_process_graph_refresh();
});/**/


function get_text_width(text,style){
	var _div=$("<div style='display:inline'/>").text(text+"").css(style).appendTo($("body"));
	var _width=_div.width();
	_div.remove();
	return _width;
}

/**
 * @class EventShape
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor EventShape
 */
function ConditionShape() {

}
/**
 * @extends <mxCylinder>
 */
ConditionShape.prototype = new mxCylinder();
ConditionShape.prototype.constructor = ConditionShape;
/**
 * @override
 */
ConditionShape.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	if (!isForeground) {
		path.moveTo(0, h / 2);
		path.lineTo(h / 2, 0);
		path.lineTo(w - h / 2, 0);
		path.lineTo(w, h / 2);
		path.lineTo(w - h/2, h);
		path.lineTo(h / 2, h);
		path.close();
	}
};

mxCellRenderer.prototype.defaultShapes['condition'] = ConditionShape;


