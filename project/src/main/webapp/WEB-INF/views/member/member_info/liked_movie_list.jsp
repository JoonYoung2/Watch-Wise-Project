<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/member_info.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<style>
.liked-movie-list-frame .liked-movie-info {
    display: flex; /* 내부 요소들을 가로로 배치하기 위해 flex 사용 */
    align-items: center; /* 수직 가운데 정렬 */
	border : 1px solid #ccc;
	border-radius: 5px;
	margin:10px auto;
	padding: 10px;
	width:30%;
}
.liked-movie-list-frame .liked-movie-Nm {
    width: 350px; /* 너비 설정 */
    margin-left: 20px; /* 왼쪽으로 민적부분을 최대로 확장하여 오른쪽 정렬 */
    text-align: left; /* 텍스트를 오른쪽 정렬 */
}
</style>

<div class="liked-movie-list-frame">
	<div class="liked-movie-title">
		<h3 style="text-align: center">내가 좋아요한 영화 목록</h3>
	</div>
	<div class="liked-movie-list">
	<c:set var="cnt" value="0"/>
		<c:forEach var="list" items="${likedMovieList }">
		<div class="liked-movie-info" style="display: flex; justify-content: space-between;">
			<span class="liked-movie-poster" style="cursor: pointer;" onclick="moveToMovieInfo('${list.movieId }');">
				<img src="${list.posterUrl }" style="width:70px; height:auto;">
			</span>
			<span class="liked-movie-Nm"  style="cursor: pointer;" onclick="moveToMovieInfo('${list.movieId }');">
			<b style="font-size:20px;">${list.movieNm }</b><br>
			${list.movieNmEn }<br>
			${list.genreNm }
			</span>
			<span class="liked-movie-list-heart" style="margin-right: 20px; cursor: pointer;">
		    	<img style="width:40px; " src="/resources/img/likeColor.png" onclick="likeCancel('${list.movieId}', '${cnt }')">
			</span>
		</div>
		<c:set var="cnt" value="${cnt+1 }"/>
		</c:forEach>
	</div>
</div>
</body>
</html>