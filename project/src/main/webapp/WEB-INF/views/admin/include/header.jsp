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

		<a href="/admin/pagingConfig">PagingConfig</a> | <a href="/admin/movie_info">Movies</a> | <a href="/admin/people_info_detail/people_id/1?query=">Actors</a> | <a href="/admin/member_info/id/1?query=">Members</a> | <a href="/admin/logout">로그아웃</a>
		<hr>
	</c:if>
</body>
</html>