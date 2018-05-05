function resultcheckHelp() {
	var useremail = formHelp.useraddress.value;
	var wrongaddress = useremail.substring(useremail.indexOf("@") + 1);
	if (formHelp.check.value.length != 4) {
		alert("请输入4位验证码!");
		formHelp.check.focus();
		return (false);
	} else if (formHelp.useraddress.value.length < 1) {
		alert("请输入您的邮箱,管理员将通过邮件为您解答!");
		formHelp.useraddress.focus();
		return (false);
	} else if (wrongaddress.length<1){
		alert("邮箱地址不正确!");
		formHelp.useraddress.focus();
		return false;
	} else if (formHelp.sendsubject.value.length < 1) {
		alert("请输入邮件标题!");
		formHelp.sendsubject.focus();
		return (false);
	} else if (!checkSpecial(formHelp.sendsubject.value)) {
		alert("邮件标题不能包含特殊字符!");
		formHelp.sendsubject.focus();
		return false;
	} else if (formHelp.sendcontent.value.length < 1) {
		alert("请输入邮件内容!");
		formHelp.sendcontent.focus();
		return false;
	} else if (!checkSpecial(formHelp.sendcontent.value)) {
		alert("邮件内容不能包含特殊字符!");
		formHelp.sendcontent.focus();
		return false;
	} else {
		return (true);
	}
}