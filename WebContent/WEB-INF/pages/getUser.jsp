<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ taglib prefix="elf" uri="/WEB-INF/tld/elfunc.tld"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table>
	<tr>
		<td>sessionid</td>
		<td>date</td>
		<td>是否退出</td>
		<td>是否登录</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${sessions}" var="item">
	<tr>
		<td>${item.id}</td>
		<td>${item.lastAccessTime}</td>
		<td>${elf:isForceLogout(item)?'是':'否'}</td>
		 <td>${elf:isLogin(item)?'在线':'离线'}</td> 
		<td><a href="delete/${item.id}">退出</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>