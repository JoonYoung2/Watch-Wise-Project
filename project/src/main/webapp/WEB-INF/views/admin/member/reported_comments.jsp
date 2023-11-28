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

<c:if test="${not empty msg}">
	<script>
		alert('${msg}');
	</script>
</c:if>

<div>
	<a href="/admin/black_list_waiting?currentPage=${pageNum}">
		<img src="/resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<div style="margin: 0 auto; text-align:center;">
		<h3>${email }이 작성한 신고된 코멘트들</h3><h4> (신고 당시의 데이터)	</h4>
		<br>
	</div>
</div>

<div style=" z-index: 0;">
	<c:set var="cntForComment" value="0"/>
	<c:forEach var="list" items="${commentList}">
	<div class="movie-card">
		<div class="movie-info">
			<span style="margin-left: 10px;cursor: pointer;">
				<a href="/movieInfo?movieId=${list.movieId}" style="text-decoration:none; color:inherit"><b style="font-size:17px;">${list.movieNm }</b></a>
				
				<br>
			</span>
			<span style="margin-left:auto;">
				<a style="padding-left:8px; cursor:pointer; text-decoration:none; color:inherit" onclick="checkForDelete('${list.commentId }', '${email}', '${pageNum}');" onmouseover="this.style.fontWeight = 'bold'" onmouseout="this.style.fontWeight = 'normal'">정상 댓글 (신고 데이터에서 제외)</a>
			</span>
		</div>
			
		<div class="comment-card" style="display: flex;background-color: #f0f0f0;border: 1px solid #ccc;border-radius: 5px;margin: 10px auto;padding: 10px;width: 95%;">
			<div class="comment-content" style="margin:10px; width:85%; word-wrap:break-word;">
		        <span class="movie_score">
			        <c:if test="${list.reviewScore ne 0}">
			            <span><b> ★ ${list.reviewScore}</b> / 5.0 </span><br><br>
			        </c:if>
			        <c:if test="${list.reviewScore eq 0 }">
			           <span>평점 기록 없음</span><br><br>
			        </c:if>
		        </span>
				<span style="word-wrap:break-word;">
		            <b>${list.reportedComment}</b> <br>
		            <span class="date">${list.commentWrittenDate} </span>
	        	</span>
	        </div>
       		<div align="center" style="width:15%;  display: flex; flex-direction: column; justify-content: center; align-items: center;">
		        <div style="margin-top:35px;">
			        <img style="width:20px; vertical-align:-5px; cursor:pointer;" src="/resources/img/activatedAlert.png" onclick="openModalForReportersList('${list.blackListDto}', '${cntForComment}');"> 
			        <span> x </span>
			        <span class="comment_like_count" style="vertical-align:-1px;">${list.reportedAmount }</span>
	        	</div>
	        <br><br>
	        </div>
	    </div>   
	</div>

        <div id="modal" class="modal" style=" position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:600px; height:600px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
		    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:500px; height:550px; border-radius:5px;">
		    	<div class="top" style="display:flex; width: 100%; height:10px; top: 0%;"><!-- top  -->
					<span class="closeModalButton" onclick="closeModalForReportersList('${cntForComment}');" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
			    </div>
			    <div>
			    	<h4 style="text-align:center">신고자 리스트</h4>
			    	<div class="table-area" style="width:500px; height:480px;overflow: auto;">
				    	<table border="1" >
				    		<tr>
				    			<th>신고자</th><th>신고사유</th><th>신고날짜</th>
				    		</tr>
				    		<c:forEach var="listModal" items="${ list.blackListDto }">
					    		<tr>
					    			<td style="padding:5px;">${listModal.reporterEmail }</td>
					    			<td style="padding:5px;">${listModal.reasonForReport }</td>
					    			<td style="padding:5px;">${listModal.reportedDate }</td>
					    		</tr>
				    		</c:forEach>
				    	</table>
			    	</div>			   		
			   	</div>
		  	</div>
		</div>
		<c:set var="cntForComment" value="${ cntForComment+1 }"/>
	</c:forEach>
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
<script src="/resources/js/admin/black_list.js"></script>
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>