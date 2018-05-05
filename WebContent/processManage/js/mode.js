// 根据mode的不同对该页面做不同程度的调整
$(function ()
{
	if($mode == "proc-edit")
	{
		$("#editor .left .item").show();
		$("#editor .toolbar .save").show();
		$("#editor .toolbar .property").text(language=="zh_CN"?"配置组合流程":"Config Composite Process");
		$("#editor .right .add").show();
		$("#editor .right .remove").show();
	}
	else if($mode == "proc-view")
	{
		$("#editor .left .title").show();
	}
	else if($mode == "ext-edit")
	{
		$("#editor .toolbar .extension").show();
		$("#editor .left .title").show();
	}
	else if($mode == "ext-view")
	{
		$("#editor .toolbar .extension").text("查看插件").show();
		$("#editor .left .title").show();
	}
	else if($mode == "ext-apply")
	{
		$("#editor .toolbar .apply").show();
		$("#editor .left .title").show();
	}
	else
	{
		console.log($mode);
		$("body").text("未知模式！");
	}

});