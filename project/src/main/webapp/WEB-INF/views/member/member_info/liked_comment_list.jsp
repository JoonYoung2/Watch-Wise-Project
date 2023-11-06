<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/header.jsp" %>
	<h3 style="text-align:center">내가 좋아요한 코멘트들</h3>
	<c:set var="cntForMovie" value="0"/>
	<c:set var="cntForComment" value="0"/>
	<c:forEach var="list" items="${likedCommentList}">
	<c:set var="movieInfo" value="${ list.movieInfoDto }"/>
	<div class="movie-card">
		<div class="movie-info" style="cursor: pointer;" onclick="moveToMovieInfo('${movieInfo.movieId }');">
		<span>
		<img style="width:60px; height:auto;" src="${movieInfo.posterUrl }">
		</span>
		<span>
		<b style="font-size:17px;">${movieInfo.movieNm }</b><br>
		${movieInfo.movieNmEn}<br>
		${movieInfo.genreNm }
		</span>
		</div>
	
		
	<c:forEach var="comment" items="${list.movieReviewDto }">
   <c:if test="${comment.reviewComment != 'nan' }">
    <div class="comment-card">
        <span class="comment-content">
        <span class="movie_score">
        <c:if test="${comment.reviewScore != 0}">
            <span><b> ★ ${comment.reviewScore}</b> / 5.0 </span><br>
        </c:if>
        <c:if test="${comment.reviewScore eq 0 }">
           <span>평점 기록 없음</span><br>
        </c:if>
        </span>
           <span class="date">${comment.userEmail } </span><br>
            <b>${comment.reviewComment}</b> <br>
            <span class="date">${comment.reviewCommentDate} </span>
        </span>
        <span>
        <span class="comment_like_count">♥ x ${comment.reviewCommentLikes }</span>
        <br><br>
        <span class="comment">
           <button class="likeButton" onclick="decreaseLikeCount('${comment.userEmail}', '${movieInfo.movieId}', '${ cntForComment }');">♥</button>
        <br><br>
        
        </span>
        </span>
        <c:set var="cntForComment" value="${ cntForComment+1 }"/>
    </div>   
    </c:if>
   </c:forEach>
		<!-- 
		<c:forEach var="comment" items="${list.movieReviewDto }">
		<div class="comment-info">
		${comment.reviewComment }
		</div>
		</c:forEach>
		 -->
	</div>
	<br>
	   	<c:set var="cntForMovie" value="${ cntForMovie+1 }"/>
	</c:forEach>
<style> 
.movie-card {
	border : 1px solid #ccc;
	border-radius: 5px;
	margin:10px auto;
	padding: 10px;
	width:40%;
}

.movie-info {
    display: flex; /* 내부 요소들을 가로로 배치하기 위해 flex 사용 */
    align-items: center; /* 수직 가운데 정렬 */
	border-radius: 5px;
	margin-left:5px auto;
	padding: 10px;
	width:70%;
}

.movie-info span {
    margin-left: 10px; /* 이미지와 다른 정보들 간격 조절 */
}

.comment-card {
	display: flex;
	background-color: #f0f0f0;
	border: 1px solid #ccc;
	border-radius: 5px;
	margin: 10px auto;
	padding: 10px;
	width: 95%; /* 너비를 50%로 설정 */
}

.comment-content {
    flex: 1;
}

.comment-content h3 {
    margin: 0;
}

.comment-content p {
    margin: 5px 0;
}

.comment-content .date {
    font-size: 0.8em;
    color: #888;
}
</style>

	<!-- 헤더기능들 -->
	<script src="/resources/js/common.js"></script>
	<script src="/resources/js/search_common.js"></script>
	<!-- like 버튼 -->
	<script src="/resources/js/member_info.js"></script>
</body>
</html>