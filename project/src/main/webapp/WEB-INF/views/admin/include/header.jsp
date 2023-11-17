<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/admin/header.css">
<title>Insert title here</title>
</head>
<body>
	<br>
	<c:if test="${ not empty sessionScope.admin }">
		<div align="center" class="header-page">
			<div align="center" class="header-div">
				<div class="header-logo-div">
					<a href="/admin"><img class="header-logo-img" src="/resources/img/WWlogo.png"></a>
				</div>
				<div class="header-view-div">
					<a href="/admin/pagingConfig">PagingConfig</a><a href="/admin/movie_list/movie_info/movie_id/1?query=">Movies</a><a href="/admin/actor_list/people_info_detail/people_id/1?query=">Actors</a><a href="/admin/member_list/member_info/id/1?query=">Members</a>
				</div>
				<div class="header-logout-div" >
					<a href="/admin/logout" class="header-logout">로그아웃</a>
				</div>
			</div>
		</div>
	</c:if>
	<br>
</body>
</html>