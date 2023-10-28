<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%; display:flex;">
			<c:if test="${movieInfo.posterUrl[0] ne 'nan' }">
				<div align="center" style="width:50%;">
					<img style="width:40%;" src="${ movieInfo.posterUrl[0] }">
				</div>
			</c:if>
			<c:if test="${movieInfo.posterUrl[0] eq 'nan' }">
				<div align="center" style="width:50%;">
					<div style="width:300px; height:400px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
				</div>
			</c:if>
			
			<div align="left" style="width:50%;">
			<br><br><br>
				<span style="font-size:40px; font-weight:bold;">${ movieInfo.movieNm }</span><br><br>
				<c:if test="${ movieInfo.movieNmEn ne 'nan' }">
					<span style="font-size:18px;">${ movieInfo.movieNmEn }</span><br>
				</c:if>
				<c:if test="${ movieInfo.openDt ne 'nan' }">
					<span style="font-size:18px;">${ movieInfo.openDt.substring(0, 4) }</span>
				</c:if>
				<c:if test="${ movieInfo.genreNm ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
				</c:if>
				<c:if test="${ movieInfo.nations ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
				</c:if>
				<br>
				<span style="font-size:18px;">${ movieInfo.showTime }</span>
				<c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
					・ <span style="font-size:18px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
				</c:if>
				<br>
				<c:if test="${ movieInfo.openDt ne 'nan' }">
					<span style="font-size:18px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년 ${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span>
				</c:if>
			</div>
		</div>
	</div>
	
	<!--  -->
	<hr>
<div align="center" id="review">
<!-- 	<form action="/getReview" method="POST"> -->
		<label for="select-box">평점</label> <br>
		<select id="select-box" name="rating" onchange="rating(this.value,'${movieInfo.movieId }');">
	      	<option value="0.0">0.0</option>
	      	<option value="0.5">0.5</option>
	      	<option value="1.0">1.0</option>
	     	<option value="1.5">1.5</option>
	      	<option value="2.0">2.0</option>
	      	<option value="2.5">2.5</option>
	      	<option value="3.0">3.0</option>
	      	<option value="3.5">3.5</option>
	      	<option value="4.0">4.0</option>
	      	<option value="4.5">4.5</option>
	      	<option value="5.0">5.0</option>
		</select>
		<div id="msg"></div>
</div>
	
	<!--  -->
	<!-- Actors -->
	
	<c:if test="${ not empty peopleInfo }">
		<br><br><br>
		<div align="center" style="width:100%;">
			<div align="center" style="width:80%;">
				<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Actors</span></div>
					<c:if test="${ (peopleInfo.end+1) % 4 eq 0 }">
						<c:set var="pageNum" value="${ (peopleInfo.end+1) / 4 }" />
					</c:if>
					<c:if test="${ (peopleInfo.end+1) % 4 ne 0 }">
						<c:set var="pageNum" value="${ ((peopleInfo.end+1) / 4) + 1 }" />
					</c:if>
					<c:forEach var="i" begin="1" end="${ pageNum }" step="1">
						<div align="left" style="display:flex; padding-bottom:20px;">
						
							<c:if test="${ peopleInfo.peopleCast[0] ne 'nan' }">
							
								<c:if test="${ i * 4 - 1 <= peopleInfo.end }">
									<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ i * 4 - 1 }" step="1">
										<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] ne '0' }">
											<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
													</div>
												</div>
											</a>
										</c:if>
										<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] eq '0' }">
											<div style="display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
								
								<c:if test="${ i * 4 - 1 > peopleInfo.end }">
									<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ peopleInfo.end }" step="1">
										<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] ne '0' }">
											<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
													</div>
												</div>
											</a>
										</c:if>
										<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] eq '0' }">
											<div style="display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
								
							</c:if>
							
							<c:if test="${ peopleInfo.peopleCast[0] eq 'nan' }">
							
								<c:if test="${ i * 4 - 1 <= peopleInfo.end }">
									<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ i * 4 - 1 }" step="1">
										<c:if test="${ peopleInfo.peopleId[j] ne '0' }">
											<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연</span>
													</div>
												</div>
											</a>
										</c:if>
										<c:if test="${ peopleInfo.peopleId[j] eq '0' }">
											<div style="display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연</span>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
								
								<c:if test="${ i * 4 - 1 > peopleInfo.end }">
									<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ peopleInfo.end }" step="1">
										<c:if test="${ peopleInfo.peopleId[j] ne '0' }">
											<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연</span>
													</div>
												</div>
											</a>
										</c:if>
										<c:if test="${ peopleInfo.peopleId[j] eq '0' }">
											<div style="display:flex; width:25%;">
												<div>
													<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
												</div>
												<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
													<div style="padding-top:10px;">
														<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
													</div>
													<div style="padding-top:5px;">
														<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연</span>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
								
							</c:if>
							
						</div>
					</c:forEach>
			</div>
		</div>
	</c:if>
	
	<!--  -->
	<div class="comment-writing-card">
	<h4 style="text-align: center">나의 코멘트 작성하기</h4>
		<div align="center" class="comment-writing-content">
		<form action="/saveComment" method="post">
			<textarea rows="10" cols="100" name="reviewComment"></textarea><br>
			<input type="hidden" value="${movieInfo.movieId }" name="movieId">
			<input type="submit" value="저장">
		</form>
		</div>
	</div>

	 <div class="comment-card">
        <div class="comment-content">
            <h3>User Name</h3>
            <p>Here is the comment text. It can be as long as needed.</p>
            <span class="date">October 27, 2023</span>
        </div>
    </div>
    <!-- Add more comment cards here -->

 <style> 
	.comment-card {
    display: flex;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 10px;
    padding: 10px;
    width: 50%; /* 너비를 50%로 설정 */
    
}

     .comment-writing-card { 
     background-color: #f0f0f0; 
     border: 1px solid #ccc; 
     border-radius: 5px; 
     margin: 10px; 
     padding: 10px; 
     width: 50%; /* 너비를 50%로 설정 */
     
 } 


/* Comment content styles */
.comment-content {
    flex: 1;
}
.comment-writing-content {
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
	
	
	<!--  -->
	
	<c:if test="${ movieInfo.posterUrl[0] ne 'nan' }">
		<br><br><br>
		<% int cnt = 0; %>
		<div align="center" style="width:100%;">
			<div align="center" style="width:80%;">
				<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Posters</span></div>
				<div style="display:flex; position:relative">
				
					<button id="postersLeftBtn" class="leftBtn" type="button" onclick="postersLeftBtn();">◀</button>
					<c:forEach var="url" items="${ movieInfo.posterUrl }">
					<% if(cnt < 5){ %>
						<div class="posters" style="display:; padding-right:10px; position:relative;">
							<img style="width:300px; height:428.16px;" src="${ url }">
						</div>
					<% }else{ %>
						<div class="posters" style="display:none; padding-right:10px; position:relative;">
							<img style="width:300px; height:428.16px;" src="${ url }">
						</div>
					<% } cnt++; %>
					</c:forEach>
					<% if(cnt > 5){ %>
						<button id="postersRightBtn" class="rightBtn" type="button" onclick="postersRightBtn('<%=cnt%>');">▶</button>
					<% } %>
				</div>
			</div>
		</div>
	</c:if>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/movie_info.js"></script>
</body>
</html>