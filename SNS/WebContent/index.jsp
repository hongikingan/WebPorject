<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>HongikIngan SNS</title></head>
<body>
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<form name="loginform" method="post" action="user_control.jsp">
	<table align=center width=400 height=170>
		<tr>
			<td colspan=3 align=center bgcolor="#F29661"><font size="15" color="white">Hongik Ingan SNS</font></td>
		</tr>
		<tr height=50>
			<td>&nbsp;ID</td>
			<td align=center><input type=text name=id></td>
			<td rowspan=2 width=50 align=center><input type="hidden" name="action" value="login">
			<input type=submit name=action value="login"></td>
		</tr>
		<tr height=50>
			<td>&nbsp;Password</td>
			<td align=center><input type=password name=password></td>
		</tr>
		
	</table>
	</form>
</body>

</html>