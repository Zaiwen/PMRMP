function resultcheck() {
	var changeemail = formUpdate.useremail.value;
	var wrongemail = changeemail.substring(changeemail.indexOf("@") + 1);
	if (formUpdate.check.value.length != 4) {
		alert("请输入4位验证码!");
		formUpdate.check.focus();
		return false;
	} else if (formUpdate.username.value.length < 1) {
		alert("请输入用户名!");
		formUpdate.username.focus();
		return false;
	} else if(!checkSpecial(formUpdate.username.value))
	{
	    alert("用户名不能包含特殊字符!");
		formUpdate.username.focus();
		return false;
	}else if (formUpdate.userpassword.value.length < 1) {
		alert("请输入原密码!");
		formUpdate.userpassword.focus();
		return false;
	}else if(!checkSpecial(formUpdate.userpassword.value))
	{
	    alert("原密码不能包含特殊字符!");
		formUpdate.userpassword.focus();
		return false;
	}else if (formUpdate.password1.value.length < 1) {
		alert("请输入新密码!");
		formUpdate.password1.focus();
		return false;
	}else if(!checkSpecial(formUpdate.password1.value))
	{
	    alert("新密码不能包含特殊字符!");
		formUpdate.password1.focus();
		return false;
	}else if (formUpdate.useremail.value.length < 1) {
		alert("请输入邮箱!");
		formUpdate.useremail.focus();
		return false;
	}else if(wrongemail.length<1)
	{
	    alert("邮箱格式不正确!");
		formUpdate.useremail.focus();
		return false;
	}else if (formUpdate.useraddress.value.length < 1) {
		alert("请输入地址!");
		formUpdate.useraddress.focus();
		return false;
	}else if(!checkSpecial(formUpdate.useraddress.value))
	{
	    alert("地址不能包含特殊字符!");
		formUpdate.useraddress.focus();
		return false;
	}else{
		return true;
	}
}