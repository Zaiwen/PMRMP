var error = "";
function wrongFileRoute() {
	var fileRoute = ontologyRegister.selectfile.value;
	var filename = fileRoute.substring(fileRoute.indexOf(".") + 1);
	var ontologyname = ontologyRegister.ontologyfilename.value;
	if (ontologyname.length<1) {
		error = error + '本体名称不能为空!\n';
	}
	else if(!checkSpecial(ontologyname)){
		error = error + '本体名称不能包含非法字符!\n';
	}
	else if (fileRoute.length<1) {
		error = error + '上传文件路径不能为空!\n';
	}else if (filename != 'owl') {
		error = error + '上传文件格式不正确!\n';
	}
	if (error != "") {
		alert(error);
		error = ""; 
		return false;
	} else {
		return true;
	}
}