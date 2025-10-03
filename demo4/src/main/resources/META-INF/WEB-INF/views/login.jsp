 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head><title>Đăng nhập</title></head>
<body>
<h2>Login</h2>
<c:if test="${param.error != null}">Sai tài khoản hoặc mật khẩu</c:if>
<c:if test="${param.logout != null}">Bạn đã đăng xuất</c:if>

<form action="<c:url value='/login'/>" method="post">
    <label>Username: <input type="text" name="username"/></label><br/>
    <label>Password: <input type="password" name="password"/></label><br/>
    <button type="submit">Đăng nhập</button>
</form>
</body>
</html>
