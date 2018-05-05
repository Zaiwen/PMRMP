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
		return true;
	}
}