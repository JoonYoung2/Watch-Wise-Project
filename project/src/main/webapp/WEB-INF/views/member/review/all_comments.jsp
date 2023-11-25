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
<div style="margin-left:20px;">
	<a href="/movieInfo?movieId=${movieId }">
		<img src="resources/img/back.png" style="width:20px; margin-top:20px;">
	</a>
	<h3 style="text-align:center">코멘트</h3>
</div>
<br>



<div align="center" style="width:100%">
	<table style="text-align:center">
		<tr>
	<c:set var="cnt" value="0"/>
			<c:forEach var="dto" items="${comments}">
				<c:if test="${dto.reviewComment != 'nan' }">
					<c:if test="${cnt%4==0 }">
						</tr>
						<tr>
					</c:if>
					
                	<td height="330px" width="380px" style="text-align:center; word-wrap:break-word;">
                    	<div style="position:relative">
                        	<div style="text-align:center; margin:10px;background-color:#F2F2F2; width:350px; height: 300px; border-radius:5px; overflow: hidden;">
                            	<div style="margin:10px; width:305px; height:280px;">
                            	
                            		<div class="이름&평점" style="display:flex; height:10%; padding-top:10px;">
	                            		<div style="width:60%; text-align:left; margin-left:10px;">
	                            			${dto.userName}
	                            		</div>
                            		
                            			<div style="width:40%">
									        <c:if test="${dto.reviewScore != 0}">
									            <span><b> ★ ${dto.reviewScore}</b> / 5.0 </span><br>
									        </c:if>
									        <c:if test="${dto.reviewScore eq 0 }">
									           <span>평점 기록 없음</span><br>
									        </c:if>
                            			</div>
                            		</div>
                            	
                            		<div class="comment-content" style="display: -webkit-box; -webkit-box-orient: vertical; -webkit-line-clamp: 7; overflow: hidden;text-align:left; margin-left:10px; margin-right:10px; height:55%; border-top: 1px solid #E5E5E5; padding-top:10px;">
                            			${dto.reviewComment}
                            		</div>
                            		<div class="date" style="display:flex; height:13%;font-size: 0.8em; color:#888; margin-left:10px; margin-right:10px;">
                            	
	                            		<div style="text-align:left; width:50%;margin-top:8px;">
	                            			${dto.reviewCommentDate} 
	                            		</div>
                            		
			        					<div class="forAlertImg" style="text-align:right; margin-top:5px;width:50%; padding-bottom:5px;">
											<c:choose>
										        <c:when test="${dto.isReported eq 0 }">
									        		<img src="/resources/img/alert.png" onclick="openModalForReport('${dto.userEmail }', '${dto.reviewComment}', '${dto.id}', '${movieInfo.movieId }', '${cnt }');" style="cursor:pointer;  width:25px; ">
										        </c:when>
										        <c:otherwise>
									        		<img src="/resources/img/activatedAlert.png" onclick="cancelReportCheck('${dto.userEmail }', '${dto.reviewComment}', '${dto.id}', '${movieInfo.movieId }', '${cnt}');" style="cursor:pointer;  width:25px;">
										        </c:otherwise>
										    </c:choose>
								        </div>
					                 </div>
					                            	
					                 <div class="하트" style=" display:flex; margin-left:10px; height:10%; padding-top:8px;border-top: 1px solid #E5E5E5;">
					                            		
					                 	<div style="text-align:left; width:50%">
									    	<span>♥ x </span>
									        <span class="comment_like_count" style="vertical-align:-1px;">${dto.reviewCommentLikes }</span>
					                    </div>
					                            		
				                        <div class="comment" style="text-align:right; margin-right:10px;width:50%;">
					                    	<c:choose>
										        <c:when test="${dto.isLiked eq 0 }">
										        	<img class="likeButton" src="resources/img/like.png" style="cursor:pointer; width:25px;" onclick="increaseLikeCount('${dto.userEmail}', '${movieInfo.movieId}', '${ cnt }');">
										        </c:when>
										        <c:otherwise>
										           <img class="likeButton" src="resources/img/likeColor.png" style="cursor:pointer; width:25px;" onclick="decreaseLikeCount('${dto.userEmail}', '${movieInfo.movieId}', '${ cnt }');">
										        </c:otherwise>
											</c:choose>
				        				</div>
                            		</div>                     	
                           		</div>
                       		</div>
                		</div>
               		</td>
	<c:set var="cnt" value="${ cnt+1 }"/>
 				</c:if>
			</c:forEach>
		</tr>
	</table>
</div>
</body>
</html>