function wrongkeyWS(){
	if(wssearch.wsname.value.length<1){
		alert("请输入web服务关键字!");
		wssearch.wsname.focus();
		return false;
	}else if(!checkSpecial(wssearch.wsname.value)){
		alert("web服务名不能包含特殊字符!");
		wssearch.wsname.focus();
		return false;
	}
	else{
		return true;
	}
}

function wrongkeyOnto(){
	if(ontologysearch.ontologyname.value.length<1){
		alert("请输入本体关键字!");
		ontologysearch.ontologyname.focus();
		return false;
	}else if(!checkSpecial(ontologysearch.ontologyname.value)){
		alert("本体名称不能包含特殊字符!");
		ontologysearch.ontologyname.focus();
		return false;
	}
	else{
		return true;
	}
}