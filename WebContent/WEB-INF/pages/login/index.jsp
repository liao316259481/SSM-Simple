<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
    <h1>Hello World!333333333333</h1>${info}
    <form action="${pageContext.request.contextPath}/logindo" method="post">
    	<input name="username"/>
    	<input name="password"/>
    	<button>1</button>
    </form>
</body>
</html>