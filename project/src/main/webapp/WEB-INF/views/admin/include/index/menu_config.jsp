<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br><br>
	<div class="page-menu">
		<div class="menus" onclick="menuClick(0)">
			신고 접수 현황
		</div>
		<div class="menus" onclick="menuClick(1)">
			회원 동향
		</div>
		<div class="menus" onclick="menuClick(2)">
			실시간 인기 검색어
		</div>
		<div class="menus" onclick="menuClick(3)">
			인기 영화 순위
		</div>
		<div class="menus" onclick="menuClick(4)">
			인기 배우 순위
		</div>
	</div>
</body>
</html>