<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc;">

<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
<div style="margin-left:20px;">
	<a href="/memberInfo">
		<img src="/resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div align="center">
		<h3>내가 작성한 코멘트들</h3>
		<br>
	</div>
</div>

<div style=" z-index: 0;">
	<c:set var="cntForComment" value="0"/>
	<c:forEach var="list" items="${reviewList}">
   <c:if test="${list.reviewComment != 'nan' }">
	<div class="movie-card">
		<div class="movie-info">
			<span style="margin-left: 10px;cursor: pointer;" onclick="moveToMovieInfo('${list.movieId }');">
				<img style="width:60px; height:auto;" src="${list.posterUrl }">
			</span>
			<span style="margin-left: 10px;cursor: pointer;" onclick="moveToMovieInfo('${list.movieId }');">
				<b style="font-size:17px;">${list.movieNm }</b><br>
				${list.movieNmEn}<br>
				${list.genreNm }
			</span>
			<span style="margin-left:auto;">
				<c:choose>
	        		<c:when test="${sessionScope.isBlack eq 1 }">
	        		</c:when>
			        <c:otherwise>
						<img class="openModalButton" onclick="openModal('${list.movieNm }', '${list.movieId}', '${list.reviewComment}', '${cntForComment}');" src="resources/img/thinPencil.png" style="cursor:pointer; width:20px;"/>
						<img src="resources/img/bin.png" onclick="location.href='/deleteCommentFromMyCommentList?id=${list.id }&movieId=${list.movieId }'" style="padding-left:8px; cursor:pointer; width:20px;"/>
					</c:otherwise>
				</c:choose>
			</span>
		</div>
			
	    <div class="comment-card" style="display:flex;">
	        <div class="comment-content" style="padding-right:10px;word-wrap:break-word;width:85%;">
		        <div class="movie_score">
			        <c:if test="${list.reviewScore != 0}">
			            <span><b> ★ ${list.reviewScore}</b> / 5.0 </span><br>
			        </c:if>
			        <c:if test="${list.reviewScore eq 0 }">
			           <span>평점 기록 없음</span><br>
			        </c:if>
		        </div>
		           <span class="date">${list.userEmail } </span><br>
		            <span><b>${list.reviewComment}</b></span> <br>
		            <span class="date">${list.reviewCommentDate} </span>
	        </div>
	        <div style="width:15%; text-align:right; padding-right:10px;">
		        <div>
			        <img style="width:20px; vertical-align:-5px;" src="resources/img/likeColor.png"> 
			        <span> x </span>
			        <span class="comment_like_count" style="vertical-align:-1px;">${list.reviewCommentLikes }</span>
	        	</div>
	        <br><br>
		        <span class="comment">
		        	<c:choose>
		        		<c:when test="${sessionScope.isBlack eq 1 }">
		        		</c:when>
		        		<c:otherwise>
					        <c:choose>
						        <c:when test="${list.liked eq 1 }">
						        	<img class="likeButton" src="resources/img/likeColor.png" style="cursor:pointer; width:30px; margin-left:15px;" onclick="decreaseLikeCount('${list.userEmail}', '${list.movieId}', '${ cntForComment }');">
						        </c:when>
						        <c:otherwise>
						           <img class="likeButton" src="resources/img/like.png" style="cursor:pointer; width:30px;margin-left:15px;" onclick="increaseLikeCount('${list.userEmail}', '${list.movieId}', '${ cntForComment }');">
						        </c:otherwise>
					        </c:choose>
						</c:otherwise>
					</c:choose>
		        </span>
	        </div>
	    </div>   
	</div>
        <c:set var="cntForComment" value="${ cntForComment+1 }"/>
	    </c:if>
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