<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<style>
form {
	background: #EEEEEE;
	border: 1px solid #999999;
	padding: 20px 0px 40px 0px;
	width: 320px;
}

table {
	border: none;
}

td {
	font-size: 13px;
	height: 32px;
}

#username,#password {
	width: 160px;
}

#submit,#reset {
	padding: 2px 12px;
}
</style>
</head>
<body>
	<center>
		<form>
			<center>
				<h3>用户登录</h3>
				<table>
					<tr>
						<td align='right'>用户名：</td>
						<td align='left'><input id='username' type='text' /></td>
					</tr>
					<tr>
						<td align='right'>密码：</td>
						<td align='left'><input id='password' type='password' /></td>
					</tr>
					<tr>
						<td align='right'><input id='submit' type='submit' value='登录' /></td>
						<td align='left'><input id='reset' type='reset' value='重置' /></td>
					</tr>
				</table>
			</center>
		</form>
	</center>
</body>
</html>