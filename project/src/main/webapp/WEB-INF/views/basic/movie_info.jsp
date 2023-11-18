<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/movie_info.css">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body style=" height: 100%; margin: 0; ">
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:set var="gradeNum" value="${ gradeInfo.gradeAvg } + ''"/>
<input type="hidden" id="reviewScore" value="${ ifWroteComment.reviewScore }">
<input type="hidden" id="gradeCnt" value="${ gradeInfo.gradeCnt }">
	<br><br><br><br>
   <div align="center" style="width:100%;">
      <div align="center" style="width:80%; display:flex;">
         <div align="left" style="width:100%;">
            <div style="display:flex; justify-content:center;">
	            <div style="display:flex;">
		            <div>
			            <c:if test="${movieInfo.posterUrl[0] ne 'nan' }">
				            <div align="right">
				               <img style="width:350px;" src="${ movieInfo.posterUrl[0] }">
				            </div>
				         </c:if>
				         <c:if test="${movieInfo.posterUrl[0] eq 'nan' }">
				            <div align="right" style="margin-right:60px;">
				               <div style="width:350px; height:497.88px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
				            </div>
				         </c:if>
				         <div style="display:flex; align-items:center; width:100%;">
				         	<div style="display:flex; justify-content: center; align-items:center; width:100%;">
				         		<div>
					            	<span style="">
					            		<c:if test="${ ifWroteComment.reviewScore eq 0.0 || empty ifWroteComment.reviewScore }">
					            			<b> <span id="allGradeAvg" style="color:red; font-size:25px;">★ ${ gradeNum.substring(0, 3) }</span></b><span> / 5.0</span> <div align="center">평균 별점 <span id="gradeCount">(${ gradeInfo.gradeCnt }명)</span></div>
					            			<input type="hidden" id="gradeCheck" value="0">
					            		</c:if>
					            		<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
					            			<b> <span id="allGradeAvg" style="color:red; font-size:25px;">★ ${ gradeNum.substring(0, 3) }</span></b> / 5.0 <br> <div align="center">평균 별점 <span id="gradeCount">(${ gradeInfo.gradeCnt }명)</span></div>
					            			<input type="hidden" id="gradeCheck" value="1">
					            		</c:if> 
					            	</span>
				            	</div>
			            	</div>
            			</div>
			         </div>
			         <div style="display:flex; height:497.88px; align-items:center; margin-left:200px; padding-top:50px;">
			         	<div>
				         	<div style="display:flex;">
				         		<div>
				            		<span style="font-size:40px; font-weight:bold;">${ movieInfo.movieNm }</span>
				            	</div>
				            	<div id="likeDiv" style="display:flex; align-items:center;">
						            <c:if test="${ not empty sessionScope.userEmail }">
						               <c:if test="${ movieLikedCheck eq 0 }">
						                  <div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="likeAdd('${ movieInfo.movieId }');">
						                  		<div style="display:flex; align-items:center;">
						                     		<img style="width:40px; padding-left:7px;" src="/resources/img/like.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
						                     	</div>
						                  </div>
						               </c:if>
						               <c:if test="${ movieLikedCheck eq 1 }">
						                  <div style="display:flex; justify-content:center; align-items:center;" class="likeCancel" onclick="likeCancel('${ movieInfo.movieId }');">
						                  		<div style="display:flex; align-items:center;">
						                     		<img style="width:40px; padding-left:7px;" src="/resources/img/likeColor.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
						                     	</div>
						                  </div>
						               </c:if>
						            </c:if>
						            <c:if test="${ empty sessionScope.userEmail }">
						               <div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="unregisterLickClick();">
						               		<div style="display:flex; align-items:center;">
						                  		<img style="width:40px; padding-left:7px;" src="/resources/img/like.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
						                  	</div>
						               </div>
						            </c:if>
						         </div>
				            </div>
				            <div>
				            	<br><br>
					            <c:if test="${ movieInfo.movieNmEn ne 'nan' }">
					               <span style="font-size:20px;">${ movieInfo.movieNmEn }</span><br>
					            </c:if>
					            <c:if test="${ movieInfo.openDt ne 'nan' }">
					               <span style="font-size:20px;">${ movieInfo.openDt.substring(0, 4) }</span>
					            </c:if>
					            <c:if test="${ movieInfo.genreNm ne 'nan' }">
					               	・ <span style="font-size:20px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
					            </c:if>
					            <c:if test="${ movieInfo.nations ne 'nan' }">
					               	・ <span style="font-size:20px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
					            </c:if>
					            <br>
					            <span style="font-size:20px;">${ movieInfo.showTime }</span>
					            <c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
					               	・ <span style="font-size:20px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
					            </c:if>
					            <br>
					            <c:if test="${ movieInfo.openDt ne 'nan' }">
					               <span style="font-size:20px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년 ${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span><br><br><br>
					            </c:if>
				            </div>
				            <!-------------------------------- 평점 START ----------------------------------------------------------->
				            <div>
								<c:if test="${not empty sessionScope.userEmail }">
									<div align="center" id="review" style="">
										<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
											<select id="select-box" name="rating" style="all:unset; font-size:25px; text-align:center; align-items:center; width:150px; height: 50px; border:1px solid rgba(0, 0, 0, 0.1); color:orange; width:100%;" onchange="rating(this.value,'${movieInfo.movieId }');">
										        <option value="0.0" ${ifWroteComment.reviewScore == 0.0 ? 'selected' : ''}>취소하기</option>
										        <option value="0.5" ${ifWroteComment.reviewScore == 0.5 ? 'selected' : ''}>★ 0.5</option>
										        <option value="1.0" ${ifWroteComment.reviewScore == 1.0 ? 'selected' : ''}>★ 1.0</option>
										        <option value="1.5" ${ifWroteComment.reviewScore == 1.5 ? 'selected' : ''}>★ 1.5</option>
										        <option value="2.0" ${ifWroteComment.reviewScore == 2.0 ? 'selected' : ''}>★ 2.0</option>
										        <option value="2.5" ${ifWroteComment.reviewScore == 2.5 ? 'selected' : ''}>★ 2.5</option>
										        <option value="3.0" ${ifWroteComment.reviewScore == 3.0 ? 'selected' : ''}>★ 3.0</option>
										        <option value="3.5" ${ifWroteComment.reviewScore == 3.5 ? 'selected' : ''}>★ 3.5</option>
										        <option value="4.0" ${ifWroteComment.reviewScore == 4.0 ? 'selected' : ''}>★ 4.0</option>
										        <option value="4.5" ${ifWroteComment.reviewScore == 4.5 ? 'selected' : ''}>★ 4.5</option>
										        <option value="5.0" ${ifWroteComment.reviewScore == 5.0 ? 'selected' : ''}>★ 5.0</option>
											</select>
											<div align="center" id="msg" style="font-size:20px;"></div>
										</c:if>
										<c:if test="${ ifWroteComment.reviewScore eq 0.0 || empty ifWroteComment.reviewScore }">
											<select id="select-box" name="rating" style="all:unset; font-size:25px; text-align:center; align-items:center; width:150px; height: 50px; border:1px solid rgba(0, 0, 0, 0.1); color:red; width:100%;" onchange="rating(this.value,'${movieInfo.movieId }');">
										        <option value="0.0" ${ifWroteComment.reviewScore == 0.0 ? 'selected' : ''}>★ 0.0</option>
										        <option value="0.5" ${ifWroteComment.reviewScore == 0.5 ? 'selected' : ''}>★ 0.5</option>
										        <option value="1.0" ${ifWroteComment.reviewScore == 1.0 ? 'selected' : ''}>★ 1.0</option>
										        <option value="1.5" ${ifWroteComment.reviewScore == 1.5 ? 'selected' : ''}>★ 1.5</option>
										        <option value="2.0" ${ifWroteComment.reviewScore == 2.0 ? 'selected' : ''}>★ 2.0</option>
										        <option value="2.5" ${ifWroteComment.reviewScore == 2.5 ? 'selected' : ''}>★ 2.5</option>
										        <option value="3.0" ${ifWroteComment.reviewScore == 3.0 ? 'selected' : ''}>★ 3.0</option>
										        <option value="3.5" ${ifWroteComment.reviewScore == 3.5 ? 'selected' : ''}>★ 3.5</option>
										        <option value="4.0" ${ifWroteComment.reviewScore == 4.0 ? 'selected' : ''}>★ 4.0</option>
										        <option value="4.5" ${ifWroteComment.reviewScore == 4.5 ? 'selected' : ''}>★ 4.5</option>
										        <option value="5.0" ${ifWroteComment.reviewScore == 5.0 ? 'selected' : ''}>★ 5.0</option>
											</select>
											<div align="center" id="msg" style="font-size:20px;">평가해주세요</div>
										</c:if>
									</div>
								</c:if>
							</div>
				<!-------------------------------- 평점 END ----------------------------------------------------------->
						</div>
		            </div>
		     	</div>
	     	</div>
         </div>
      </div>
   </div>
   
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
                                       <c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
											<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
										        <img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
										    </div>
										</c:if>
										<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
											<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
										</c:if>
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
                                       <c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
											<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
										        <img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
										    </div>
										</c:if>
										<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
											<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
										</c:if>
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
                                       <c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
											<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
										        <img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
										    </div>
										</c:if>
										<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
											<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
										</c:if>
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
                                       <c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
											<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
										        <img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
										    </div>
										</c:if>
										<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
											<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
										</c:if>
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
   
   <!------------------------------------- 코멘트 START  -------------------------------------------------->
   

   
   	<c:if test="${not empty msg }">
      <script>
         alert('${msg}');
      </script>
   	</c:if>
   
       <hr>
   
   	<c:if test="${not empty sessionScope.userEmail }">
   	<c:choose>
    <c:when test="${ifWroteComment.reviewComment eq 'nan' || ifWroteComment.reviewComment eq null}">
   	<div style="text-align:center;" class="comment-writing-card">
   		<h4 style="text-align: center">나의 코멘트 작성하기</h4>
      	<div align="center" class="comment-writing-content">
      		<form action="/saveComment" method="post">
	         	<textarea rows="10" cols="100" name="reviewComment"></textarea><br>
	         	<input type="hidden" value="${movieInfo.movieId }" name="movieId">
	         	<input type="submit" value="저장">
      		</form>
      	</div>
   	</div>   
    </c:when>
    <c:otherwise>
    
      <h4>내가 작성한 코멘트</h4>
      
      <div class="comment-card">
        <span class="comment-content">
           <span class="date">${ifWroteComment.userEmail } </span><br>
            <b>${ifWroteComment.reviewComment}</b> <br>
            <span class="date">${ifWroteComment.reviewCommentDate} </span>
        </span>
        <span>
			<img src="resources/img/thinPencil.png" onclick="showModifyForm();" style="cursor:pointer; width:20px;"/>
			<img src="resources/img/bin.png" onclick="location.href='/deleteComment?id=${ifWroteComment.id }&movieId=${movieInfo.movieId }'" style="padding-left:8px; cursor:pointer; width:20px;"/>
        </span>
       </div>   
       
       <div id="modify-form" style="display:none;" class="comment-writing-card">
          <h4 style="text-align: center">나의 코멘트 수정하기</h4>
         <div align="center" class="comment-writing-content">
            <form action="/modifyComment" method="post">
               <textarea rows="10" cols="100" name="reviewComment"></textarea><br>
               <input type="hidden" value="${movieInfo.movieId }" name="movieId">
               <input type="submit" value="수정">
            </form>
         </div>
       </div>
    </c:otherwise>
   </c:choose>
   </c:if>
   <c:set var="cnt" value="0"/>
   <hr>
   <div style=" z-index: 0;"><!-- 추가한 부분! -->
   <h4 style="text-align:center;">이 영화에 대한 모든 코멘트</h4>
   <c:forEach var="dto" items="${comments}">
   <c:if test="${dto.reviewComment != 'nan' }">
    <div class="comment-card">
        <span class="comment-content">
        <span class="movie_score">
        <c:if test="${dto.reviewScore != 0}">
            <span><b> ★ ${dto.reviewScore}</b> / 5.0 </span><br>
        </c:if>
        <c:if test="${dto.reviewScore eq 0 }">
           <span>평점 기록 없음</span><br>
        </c:if>
        </span>
           <span class="date">${dto.userEmail } </span><br>
            <b>${dto.reviewComment}</b> <br>
            <span class="date">${dto.reviewCommentDate} </span>
        </span>
        <span>
        <span>
        	<img src="resources/img/likeColor.png" style="width:20px; vertical-align:-5px;">
        	<span> x </span>
        	<span class="comment_like_count" style="vertical-align:-1px;">${dto.reviewCommentLikes }</span>
        	<span class="forAlertImg" style=" margin-top:10px;">
		        <c:choose>
		        <c:when test="${dto.isReported eq 0 }">
	        		<img src="resources/img/alert.png" onclick="openModalForReport('${dto.userEmail }', '${dto.reviewComment}', '${movieInfo.movieId }', '${cnt }');" style="cursor:pointer; margin-left:10px; width:30px; vertical-align:-3px;">
		        </c:when>
		        <c:otherwise>
	        		<img src="resources/img/activatedAlert.png" onclick="cancelReportCheck('${dto.userEmail }', '${dto.reviewComment}', '${movieInfo.movieId }', '${cnt}');" style="cursor:pointer; margin-left:10px; width:30px; vertical-align:-3px;">
		        </c:otherwise>
		        </c:choose>
        	</span>
       	</span>
        <br><br>
        <span class="comment">
        <c:choose>
        <c:when test="${dto.isLiked eq 0 }">
        	<img class="likeButton" src="resources/img/like.png" style="width:25px;margin-left:50px;" onclick="increaseLikeCount('${dto.userEmail}', '${movieInfo.movieId}', '${ cnt }');">
        </c:when>
        <c:otherwise>
           <img class="likeButton" src="resources/img/likeColor.png" style="width:25px; margin-left:50px;" onclick="decreaseLikeCount('${dto.userEmail}', '${movieInfo.movieId}', '${ cnt }');">
        </c:otherwise>
        </c:choose>
        </span>
        </span>
        <c:set var="cnt" value="${ cnt+1 }"/>

    </div>   

    </c:if>
   </c:forEach>
   </div>
 
   <!--  modal  -->
 
 <div id="modal" style="position: fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
 		<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitle" style="font-size:25px; margin-left:8px; margin-top:5px; font-weight:bold;">신고사유</span>
			<span class="closeModalButton" onclick="closeModalForReport();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
		<div>&nbsp 
			<input type="hidden" id="storage-for-author" />
			<input type="hidden" id="storage-for-comment" />
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



 
 
 <style> 
   .comment-card {
    display: flex;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 10px;
    padding: 10px;
    width: 50%; /* 너비를 50%로 설정 */
    margin: 10px auto; /* 좌우 중앙 정렬을 위해 auto 사용 */
    
    
}

    .comment-writing-card {
      background-color: #f0f0f0;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin: 10px auto; /* 좌우 중앙 정렬을 위해 auto 사용 */
      padding: 10px;
      width: 50%;
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
   
   
   <!------------------------------------- 코멘트 END  -------------------------------------------------->
   
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
<script src="/resources/js/search_common.js"></script>
<script src="/resources/js/admin/black_list.js"></script>

</body>
</html>