function checkSpecial(strvalue) {
	var wrongstring = /^[^@\/\'\\\"#$%&\^\*]+$/;
	if (wrongstring.test(strvalue)) {
		return true;
	} else {
		return false;
	}
}