var error = "";
function checkWebService() {
	var fileRoute = wsRegister.wsfileurl.value;
	var filename = fileRoute.substring(fileRoute.indexOf("?") + 1);
	var filename2=fileRoute.substring(fileRoute.indexOf(".")+1);
	if (fileRoute.length<1) {
		error = error + '上传文件路径不能为空!\n';
	}else if (!(filename == 'wsdl'||filename=='WSDL'||filename2=='wsdl'||filename2=='WSDL')) {
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