<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New User</title>
</head>
<body>
<H2>회원가입</H2>
<HR>
<form method="post" action="user_control.jsp?action=new">
<table>
	<tr><td>이름</td><td><input type="text" name="name" size="10" required>
		</td></tr>
	<tr><td>아이디</td><td><input type="text" name="uid" size="10" required>
		</td></tr>
	<tr><td>이메일</td><td><input type="email" name="email" size="10">
		</td></tr>
	<tr><td>비밀번호</td><td><input type="password" name="passwd" size="10"
		required><input type="submit" value="회원등록"></td></tr>
</table>
</form>
</body>
</html>