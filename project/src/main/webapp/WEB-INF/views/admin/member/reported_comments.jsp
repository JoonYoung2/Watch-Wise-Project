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
<%@ include file="../include/header.jsp" %>
<hr style="border:1px solid #ccc;">

<div style="">
	<a href="/admin/black_list_waiting?currentPage=${pageNum}">
		<img src="/resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div style="margin: 0 auto; text-align:center;">
		<h3>${email }이 작성한 신고된 코멘트들	</h3>
		<br>
	</div>
</div>

<div style=" z-index: 0;">
	<c:set var="cntForComment" value="0"/>
	<c:forEach var="list" items="${commentList}">
	<div class="movie-card">
		<div class="movie-info">
			<span style="margin-left: 10px;cursor: pointer;" onclick="moveToMovieInfo('${list.movieId }');">
				<b style="font-size:17px;">${list.movieNm }</b><br>
			</span>
			<span style="margin-left:auto;">
				<img class="openModalButton" onclick="openModal('${list.movieNm }', '${list.movieId}', '${list.reportedComment}', '${cntForComment}');" src="/resources/img/thinPencil.png" style="cursor:pointer; width:20px;"/>
				<img src="/resources/img/bin.png" onclick="location.href='/deleteCommentFromMyCommentList?id=${list.commentId }&movieId=${list.movieId }'" style="padding-left:8px; cursor:pointer; width:20px;"/>
			</span>
		</div>
			
	    <div class="comment-card">
	        <span class="comment-content">
		        <span class="movie_score">
			        <c:if test="${list.reviewScore != 0}">
			            <span><b> ★ ${list.reviewScore}</b> / 5.0 </span><br>
			        </c:if>
			        <c:if test="${list.reviewScore eq 0 }">
			           <span>평점 기록 없음</span><br>
			        </c:if>
		        </span>

		            <b>${list.reportedComment}</b> <br>
		            <span class="date">${list.commentWrittenDate} </span>
	        </span>
	        <span style="margin-right:14px;">
		        <span>
			        <img style="width:20px; vertical-align:-5px;" src="/resources/img/activatedAlert.png"> 
			        <span> x </span>
			        <span class="comment_like_count" style="vertical-align:-1px;">${list.reportedAmount }</span>
	        	</span>
	        <br><br>
	        </span>
	    </div>   
	</div>
        <c:set var="cntForComment" value="${ cntForComment+1 }"/>
	</c:forEach>
</div>

<div id="modal" style=" position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitle" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModal();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		<textarea style="width:340px; height:250px;"id="movieComment"></textarea>
	   		<input type="hidden" id="modalMovieId" />
	   		
	   	</div>
	   	<div>
	   		<button style="margin-top:10px; margin-left:40%" onclick="updateMovieComment();">수정하기</button>
	   	</div>
  	</div>
</div>

<div id="bodyForShadow" class="bodyForShadow" style="content: '';
    position:fixed;  z-index:-2;  display:none;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 1;">
</div>



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
	width:95%;
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

<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>