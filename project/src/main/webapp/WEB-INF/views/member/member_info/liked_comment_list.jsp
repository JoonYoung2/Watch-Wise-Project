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
<hr style="border:1px solid #ccc;">

<div style="margin-left:20px;">
	<a href="/memberInfo">
		<img src="resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<h3 style="text-align:center">내가 좋아요한 코멘트들</h3>
</div>

<br>
	
<c:set var="cntForMovie" value="0"/>
<c:set var="cntForComment" value="0"/>
<c:forEach var="list" items="${likedCommentList}">
	<c:set var="movieInfo" value="${ list.movieInfoDto }"/>
		<div class="movie-card" style="	border : 1px solid #ccc;border-radius: 5px;margin:10px auto;padding: 10px;width:40%;">
			<div class="movie-info" style="cursor: pointer;display: flex;align-items: center;border-radius: 5px;margin-left:5px auto;padding: 10px;width:70%;" onclick="moveToMovieInfo('${movieInfo.movieId }');">
				<span style="margin-left: 10px;">
					<img style="width:60px; height:auto;" src="${movieInfo.posterUrl }">
				</span>
				<span style="margin-left: 10px;">
					<b style="font-size:17px;">${movieInfo.movieNm }</b><br>
					${movieInfo.movieNmEn}<br>
					${movieInfo.genreNm }
				</span>
			</div>
	
		
			<c:forEach var="comment" items="${list.movieReviewDto }">
   				<c:if test="${comment.reviewComment != 'nan' }">
				    <div class="comment-card" style="display: flex;background-color: #f0f0f0;border: 1px solid #ccc;border-radius: 5px;margin: 10px auto;padding: 10px;width: 95%;">
				        <div class="comment-content" style="margin:10px; width:85%; word-wrap:break-word;">
					        <span class="movie_score">
						        <c:if test="${comment.reviewScore != 0}">
						            <span><b> ★ ${comment.reviewScore}</b> / 5.0 </span><br>
						        </c:if>
						        <c:if test="${comment.reviewScore eq 0 }">
						           <span>평점 기록 없음</span><br>
						        </c:if>
       						</span>
       						<span style="word-wrap:break-word;">
					           	<span style="font-size: 0.8em; color: #888;">${comment.userEmail } </span><br>
					            <b>${comment.reviewComment}</b> <br>
					            <span style="font-size: 0.8em; color: #888;">${comment.reviewCommentDate} </span>
					        </span>
				    	</div>
        
       					<div align="center" style="width:15%;  display: flex; flex-direction: column; justify-content: center; align-items: center;">
					        <div>
						        <img style="width:20px; vertical-align:-5px;" src="resources/img/likeColor.png"> 
						        <span> x </span>
						        <span class="comment_like_count" style="vertical-align:-1px;">${comment.reviewCommentLikes}</span>
				        	</div>
					        <br><br>
					        <div class="comment">
					        	<img class="likeButton" src="resources/img/likeColor.png" style="width:30px; margin-left:15px;" onclick="decreaseLikeCount('${comment.userEmail}', '${movieInfo.movieId}', '${ cntForComment }');">     
					        </div>
       					</div>
        
   				 		<c:set var="cntForComment" value="${ cntForComment+1 }"/>
   					</div>   
   				</c:if>
   			</c:forEach>
		</div>
		<br>
	   	<c:set var="cntForMovie" value="${ cntForMovie+1 }"/>
	</c:forEach>
	
	


	<!-- 헤더기능들 -->
	<script src="/resources/js/common.js"></script>
	<script src="/resources/js/search_common.js"></script>
	<!-- like 버튼 -->
	<script src="/resources/js/member_info.js"></script>
</body>
</html>