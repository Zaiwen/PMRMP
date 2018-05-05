function checkProcess(){
	if(newprocess.processname.value.length<1){
		alert("请输入process名称!");
		newprocess.processname.focus();
		return false;
	}
	else{
		return true;
	}
}