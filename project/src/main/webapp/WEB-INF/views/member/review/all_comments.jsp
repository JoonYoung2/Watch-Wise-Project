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
                            	
                            		<div class="이름&평점" style="margin-left:10px;display:flex; height:10%; align-items: center;">
	                            		
                                		<div style="position:relative;">
                                		<c:choose>
                                			<c:when test="${dto.profileImg eq 'nan' }">
                                				<div style="position:relative;width:30px; height:30px;overflow: hidden; border-radius: 50%;">                                			
                                					<img id="preview" style="width:30px;"src="resources/img/basicProfileImg.png"/>
                                				</div>
                                			</c:when>
                                			<c:otherwise>
		                                		<div style="position:relative;width:30px; height:30px;overflow: hidden; border-radius: 50%;">
		                                			<img id="preview" style="width: 30px; height: 30px;object-fit: cover; object-position: center center;transition: transform 0.3s ease-in-out;" src="/resources/profile_img/${dto.userEmail }/${dto.profileImg }"/>
		                                		</div>                                				
                                			</c:otherwise>
                                		</c:choose>
                                		</div>
	                            		
	                            		
	                            		
	                            		<div style="width:50%; text-align:left; margin-left:10px;">
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
									        		<img src="/resources/img/alert.png" onclick="openModalForReport('${dto.userEmail }', '${dto.reviewComment}', '${dto.id}', '${movieId}', '${cnt }');" style="cursor:pointer;  width:25px; ">
										        </c:when>
										        <c:otherwise>
									        		<img src="/resources/img/activatedAlert.png" onclick="cancelReportCheck('${dto.userEmail }', '${dto.reviewComment}', '${dto.id}', '${movieId}', '${cnt}');" style="cursor:pointer;  width:25px;">
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
										        	<img class="likeButton" src="resources/img/like.png" style="cursor:pointer; width:25px;" onclick="increaseLikeCount('${dto.userEmail}', '${movieId}', '${ cnt }');">
										        </c:when>
										        <c:otherwise>
										           <img class="likeButton" src="resources/img/likeColor.png" style="cursor:pointer; width:25px;" onclick="decreaseLikeCount('${dto.userEmail}', '${movieId}', '${ cnt }');">
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

<!-- 댓글 신고용 modal -->
 <div id="modal" style="position: fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
 		<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitle" style="font-size:25px; margin-left:8px; margin-top:5px; font-weight:bold;">신고사유</span>
			<span class="closeModalButton" onclick="closeModalForReport();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
		<div>&nbsp 
			<input type="hidden" id="storage-for-author" />
			<input type="hidden" id="storage-for-comment" />
			<input type="hidden" id="storage-for-comment-id" />
			<input type="hidden" id="storage-for-movieId" />
			<input type="hidden" id="storage-for-cnt"/>
		</div>
		<div>
		    <div id="bad-words" onclick="reportForBadWord();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-top:1px solid rgb(0,0,0); border-bottom: 1px solid rgb(0,0,0);">
		    	욕설 또는 비속어
		    </div>
		    <div id="spam" onclick="reportForSpam();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	도배 및 스팸
		    </div>
		    <div id="false-fact" onclick="reportForFalseFact();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	허위 정보
		    </div>
		    <div id="racism" onclick="reportForRacism();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	차별 / 혐오 발언
		    </div>
		    <div id="spoiler" onclick="reportForSpoiler();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	스포일러
		    </div>
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

<script src="/resources/js/movie_info.js"></script>
<script src="/resources/js/admin/black_list.js"></script>
</body>
</html>