 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
<head><title>Trang chủ</title></head>
<body>
<h2>Chào mừng!</h2>

<sec:authorize access="isAuthenticated()">
  Xin chào <sec:authentication property="name"/> |
  Quyền: <sec:authentication property="principal.authorities"/> |
  <a href="<c:url value='/logout'/>">Đăng xuất</a>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
  <a href="<c:url value='/login'/>">Đăng nhập</a>
</sec:authorize>

<hr/>
<ul>
  <li><a href="<c:url value='/products'/>">Danh sách sản phẩm</a></li>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
      <li><a href="<c:url value='/products/new'/>">Thêm sản phẩm</a></li>
  </sec:authorize>
</ul>
</body>
</html>
