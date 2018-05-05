function newExtensionname(){
	if(newextension.extensionname.value.length<1){
		alert("请输入plugin关键字!");
		newextension.extensionname.focus();
		return false;
	}else if(!checkSpecial(newextension.extensionname.value)){
		alert("plugin名称不能包含特殊字符!");
		newextension.extensionname.focus();
		return false;
	}
	else{
		return true;
	}
}