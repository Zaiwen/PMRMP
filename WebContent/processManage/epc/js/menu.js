/*// 文件
$(function (){
	var flag=false;
	$("#menubar>.file").click(function (){
		$("#toolbar>.file").show();
	});*/
	//$(document).on("click","*",function (){
		// 只有menubar和 toolbar下有file类
		//if(!$(this).hasClass("file")&&!$(this).parents().hasClass("file")){
			//$("#toolbar>.file").hide();
		//}
		//return false;
	//});
/*});*/


/*//松开鼠标时获取被选中的cell
$(function (){
    $("#edit-window .body").mouseup(function (){
        $("#detail-window .body *").hide();
        setTimeout(function (){
            var cells=graph.getSelectionCells();
            if(cells.length==0){

            }else if(cells.length==1){
                var cell=cells[0];
                if(cell.isVertex()){
                    // 显示所有项目
                    $("#detail-window .body *").show();
                    // 获取Cell类型
                    var type = cell.getType();
                    $("#detail-window .body .section.main .item.type").text("类型: "+ type);
                    // Cell为三种连接符时，隐藏名称和字体信息
                    if(type == "and" || type == "or" || type == "xor"){
                        $("#detail-window .body .section.main .item.name").hide();
                        $("#detail-window .body .section.font").hide();
                    }
                    // Cell不为object时，隐藏本体信息
                    if(type != "object"){
                        $("#detail-window .body .section.others").hide();
                    }
                    // Cell不为连接符时获取名称
                    if(type != "and" || type != "or" || type != "xor"){
                        console.log(cell);
                        $("#detail-window .body .section.main .item.name .value").text(cell.getName());
                    }
                    // 对所有Cell获取Description信息
                    $("#detail-window .body .section.main .item.description .value").text(cell.getDescription());
                    // 对所有Cell获取位置信息
                    $("#detail-window .body .section.position .item.x .value").text(cell.getX());
                    $("#detail-window .body .section.position .item.y .value").text(cell.getY());
                    $("#detail-window .body .section.position .item.width .value").text(cell.getWidth());
                    $("#detail-window .body .section.position .item.height .value").text(cell.getHeight());
                    // 对非连接符的Cell获取字体信息
                    if(type != "and" || type != "or" || type != "xor"){
                        if(cell.isFontBold()){
                            $("#detail-window .body .section.font .item.bold").addClass("checked");
                        }else{
                            $("#detail-window .body .section.font .item.bold").removeClass("checked");
                        }
                        if(cell.isFontItalic()){
                            $("#detail-window .body .section.font .item.italic").addClass("checked");
                        }else{
                            $("#detail-window .body .section.font .item.italic").removeClass("checked");
                        }
                        if(cell.isFontUnderline()){
                            $("#detail-window .body .section.font .item.underline").addClass("checked");
                        }else{
                            $("#detail-window .body .section.font .item.underline").removeClass("checked");
                        }
                    }
                    // 对Object Cell获取语义信息
                    if(type == "object"){
                        $("#detail-window .body .section.others .item.semantic .value").text(cell.getSemanticType());
                    }
                    
                    //if()
                    $("#detail-window .body .section").show();
                    if(cell instanceof ObjectCell){
                        $("#detail-window .body .section.others").show();
                    }else{
                        $("#detail-window .body .section.others").hide();
                    }
                    $("#detail-window .body .section.main .item.type").text("类型: "+cell.gettype);
                    $("#detail-window .body .section.main .item.name .value").text(cell.value);
                    console.log(cell);
                }else{

                }
            }else{

            }
       },10);
    });



})*/
/*// 设置各种详细属性
$(function (){
    // Name
    $("#detail-window .section.main .item.name .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var str = prompt("请输入新的名字：",cell.getName());
        if(str != null && str != cell.getName()){
            $(this).text(str);
            cell.setName(str);
            graph.refresh();
        } 
    });
    // Description
    $("#detail-window .section.main .item.description .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var str = prompt("描述：",cell.getDescription());
        if(str != null && str != cell.getDescription()){
            $(this).text(str);
            cell.setDescription(str);
            graph.refresh();
        } 
    });
    // X
    $("#detail-window .section.position .item.x .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var num = parseInt(prompt("X：",cell.getX()));
        if(!isNaN(num) && num != cell.getX()){
            $(this).text(num);
            cell.setX(num);
            graph.refresh();
        } 
    });
    // Y
    $("#detail-window .section.position .item.y .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var num = parseInt(prompt("Y：",cell.getY()));
        if(!isNaN(num) && num != cell.getY()){
            $(this).text(num);
            cell.setY(num);
            graph.refresh();
        } 
    });
    // Width
    $("#detail-window .section.position .item.width .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var num = parseInt(prompt("宽：",cell.getWidth()));
        if(!isNaN(num) && num != cell.getWidth()){
            $(this).text(num);
            cell.setWidth(num);
            graph.refresh();
        } 
    });
    // Height
    $("#detail-window .section.position .item.height .value").click(function (){
        var cell = graph.getSelectionCells()[0];
        var num = parseInt(prompt("高：",cell.getHeight()));
        if(!isNaN(num) && num != cell.getHeight()){
            $(this).text(num);
            cell.setHeight(num);
            graph.refresh();
        } 
    });
    // Font Bold
    $("#detail-window .section.font .item.bold").click(function (){
        var cell = graph.getSelectionCells()[0];
        if($(this).hasClass("checked")){
            $(this).removeClass("checked");
            cell.setFontBold(false);
            graph.refresh();
        }else{
            $(this).addClass("checked");
            cell.setFontBold(true);
            graph.refresh();
        }
    });
    // Font Italic
    $("#detail-window .section.font .item.italic").click(function (){
        var cell = graph.getSelectionCells()[0];
        if($(this).hasClass("checked")){
            $(this).removeClass("checked");
            cell.setFontItalic(false);
            graph.refresh();
        }else{
            $(this).addClass("checked");
            cell.setFontItalic(true);
            graph.refresh();
        }
    });
    // Font Bold
    $("#detail-window .section.font .item.underline").click(function (){
        var cell = graph.getSelectionCells()[0];
        if($(this).hasClass("checked")){
            $(this).removeClass("checked");
            cell.setFontUnderline(false);
            graph.refresh();
        }else{
            $(this).addClass("checked");
            cell.setFontUnderline(true);
            graph.refresh();
        }
    });
    // Semantic
    $("#detail-window .section.others .item.semantic .value").click(function (){
    	
    });
});
*/
function get_epml(){
    var cells = graph.model.getChildren(graph.getDefaultParent());
    var _epc=$("<epc/>").attr("name",$("#edit-window .head .name").text());
    for(var i =0;i<cells.length;i++){
        if(cells[i].isVertex()){
            _epc.append(cells[i].toXml());
        }else if(cells[i].isEdge()){
            var _arc=$("<arc/>").attr("id",cells[i].id).appendTo(_epc);
            var _flow = $("<flow/>").appendTo(_arc);
            var _graphics=$("<graphics/>").appendTo(_arc);
            if(cells[i].source){
                _arc.append(_flow.attr("source",cells[i].source.id));
            }
            if(cells[i].target){
                _arc.append(_flow.attr("target",cells[i].target.id));
            }
            if(cells[i].geometry.points){
                for(var j=0;j<cells[i].geometry.points.length;j++){
                    $("<position/>").attr("x",cells[i].geometry.points[j].x).attr("y",cells[i].geometry.points[j].y).appendTo(_graphics);
                }
            }
        }   
    }
   var str="<epml>"+$("<epml/>").append($("<directory/>").append(_epc)).html()+"</epml>";
   return formatXml(str);
}