<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${ not empty sessionScope.admin }">
		<hr>
		<a href="/admin/pagingConfig">PagingConfig</a> | <a href="/admin/movie_list/open_dt/1">Movies</a> | <a href="/admin/actor_list/people_id/1">Actors</a> | <a href="#">Members</a> | <a href="/admin/logout">로그아웃</a>
		<hr>
	</c:if>
</body>
</html>