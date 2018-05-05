function atomic_process_detail_refresh(type, binding){
	$("#atomic-process-detail-window>.body>div").hide();
	if(type == "input"){
		$("#atomic-process-detail-window>.body>.parameter").show();
		$("#atomic-process-detail-window>.body>.parameter>h3").text("Input:");
		$("#atomic-process-detail-window>.body>.parameter>.value.name").text(binding.id);
		$("#atomic-process-detail-window>.body>.parameter>.value.type").text(binding.type);
	}else if(type == "output"){
		$("#atomic-process-detail-window>.body>.parameter").show();
		$("#atomic-process-detail-window>.body>.parameter>h3").text("Output:");
		$("#atomic-process-detail-window>.body>.parameter>.value.name").text(binding.id);
		$("#atomic-process-detail-window>.body>.parameter>.value.type").text(binding.type);
	}else if(type == "precondition"){
		$("#atomic-process-detail-window>.body>.condition").show();
		$("#atomic-process-detail-window>.body>.condition>h3").text("Precondition:");
		$("#atomic-process-detail-window>.body>.condition>.predicate>.value.name").text(binding.predicate);
		$("#atomic-process-detail-window>.body>.condition>.argument1>.value.name").text(binding.argument1);
		$("#atomic-process-detail-window>.body>.condition>.argument2>.value.name").text(binding.argument2);
	}else if(type == "postcondition"){
		$("#atomic-process-detail-window>.body>.condition").show();
		$("#atomic-process-detail-window>.body>.condition>h3").text("Effect:");
		$("#atomic-process-detail-window>.body>.condition>.predicate>.value.name").text(binding.predicate);
		$("#atomic-process-detail-window>.body>.condition>.argument1>.value.name").text(binding.argument1);
		$("#atomic-process-detail-window>.body>.condition>.argument2>.value.name").text(binding.argument2);
	}else{
		$("#atomic-process-detail-window>.body>.process").show();
	}
}