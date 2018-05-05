function newPluginname(){
	if(newplugin.templatename.value.length<1){
		alert("请输入plugin关键字!");
		newplugin.templatename.focus();
		return false;
	}else if(!checkSpecial(newplugin.templatename.value)){
		alert("plugin名称不能包含特殊字符!");
		newplugin.templatename.focus();
		return false;
	}
	else{
		var processname=$("#processSelect").val();
		var processuser=$("#processSelect2").val();
		if(processname=="" && processuser==""){
			alert("请选择基础流程及创建者!");
			return false;
		}
		return true;
	}
}