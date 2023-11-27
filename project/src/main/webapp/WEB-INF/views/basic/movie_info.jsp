<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/movie_info.css">
<link rel="stylesheet" href="/resources/css/movie_common_btn.css">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:set var="gradeNum" value="${ gradeInfo.gradeAvg } + ''"/>
<input type="hidden" id="chartMovie" value="${ chartMovie }">
<input type="hidden" id="reviewScore" value="${ ifWroteComment.reviewScore }">
<input type="hidden" id="gradeCnt" value="${ gradeInfo.gradeCnt }">
	<br><br><br><br>
	
   	<c:if test="${not empty msg }">
      <script>
         alert('${msg}');
      </script>
   	</c:if>
   	<div align="center" style="width:100%;">
   		<div style="width:80%;">
   			<hr style="border:1px solid rgba(0, 0, 0, 0.1)">
   		</div>
   	</div>
   	<div align="center" style="width:100%;">
      	<div align="center" style="width:80%; display:flex;">
      		<div style="width:5%;"></div>
   			<div align="center" style="width:30%; text-align:center;">
		   		<div style="text-align:center; display:flex; justify-content: center; align-items:center;">
		    		<div style="width:30%; font-size:13px; margin-right:5px;">
		   			<c:choose>
		   				<c:when test="${isWished eq 1 }"><!-- 위시리스트에 추가된 영화라면 -->
		<!-- MemberInfoController에 있음. -->	<img src="/resources/img/saved.png" onclick="location.href='/deleteMovieFromWishList?movieId=${ movieInfo.movieId }'" style="cursor:pointer; width:25px; margin-bottom:10px;"><br>
		    			저장됨
		   				</c:when>
		   				<c:otherwise>
		<!-- MemberInfoController에 있음. -->	<img src="/resources/img/plus.png" onclick="location.href='/addMovieIntoWishList?movieId=${ movieInfo.movieId }'" style="cursor:pointer; width:25px; margin-bottom:10px;"><br>
		    			보고싶어요
		    			</c:otherwise>
		    		</c:choose>
		    		</div>
		    		
		    		<c:choose>
						<c:when test="${sessionScope.isBlack eq 1 }"><!-- 블랙리스트일 경우 -->
					    	<div style="width:30%; font-size:13px; margin-right:5px;">
								<img src="/resources/img/thinPencil.png" onclick="openBlackModal()" style="cursor:pointer;width:25px; margin-bottom:10px;"><br>
								코멘트
							</div>
						</c:when>
							
						<c:otherwise><!-- 블랙리스트가 아닐 경우 -->
				    		<c:choose>
					    		<c:when test="${ifWroteComment.reviewComment eq 'nan' || ifWroteComment.reviewComment eq null}"><!-- 코멘트 처음 작성하는 경우 -->
					    		<div style="width:30%; font-size:13px; margin-right:5px;">
					    			<img src="/resources/img/thinPencil.png" onclick="openCommentWritingModal('${ movieInfo.movieNm }', '${ movieInfo.movieId }')" style="cursor:pointer;width:25px; margin-bottom:10px;"><br>
					    			코멘트
					    		</div>
					    		</c:when>
					    		<c:otherwise> <!-- 코멘트 이미 작성된 경우 -->
					    		<div style="width:30%; font-size:13px; margin-right:5px;">
					    			<img src="/resources/img/thinPencil.png" onclick="openModifyModal('${ movieInfo.movieNm }' , '${movieInfo.movieId }', '${ifWroteComment.reviewComment}');" style=" cursor:pointer;width:25px; margin-bottom:10px;"><br>
					    			코멘트
					    		</div>
					    		</c:otherwise>
					    	</c:choose>
				    	</c:otherwise>
			    	</c:choose>
		    		
		<!-------------------------------------------------------- 평점 START ----------------------------------------------------------->
		    		<div style="width:30%; font-size:13px; padding-right:20px;">
			    		<div>
							<c:if test="${not empty sessionScope.userEmail }">
								<div align="center" id="review" style="">
									<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
										<select id="select-box" name="rating" style="all:unset; font-size:20px; text-align:center; align-items:center; width:150px; height: 25px; color:orange; width:100%; padding-bottom:10px; font-weight:bold;" onchange="rating(this.value,'${movieInfo.movieId }');">
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
										<div align="center" id="msg" style="font-size:13px; margin-top:5px;"></div>
									</c:if>
									<c:if test="${ ifWroteComment.reviewScore eq 0.0 || empty ifWroteComment.reviewScore }">
										<select id="select-box" name="rating" style="all:unset; font-size:20px; text-align:center; align-items:center; width:150px; height: 25px; color:red; width:100%; padding-bottom:10px; font-weight:bold;" onchange="rating(this.value,'${movieInfo.movieId }');">
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
										<div align="center" id="msg" style="font-size:13px;">평가해주세요</div>
									</c:if>
								</div>
							</c:if>
						</div>							    			
		    		</div>
		   		</div>
		   	</div>
		   	<div style="width:9%;">
		   	</div>
      		<div style="width:56%">
	      		<div style="display:flex;">
	         		<div style=" height:60px;">
	            		<div style="font-size:40px; font-weight:bold; margin-bottom:15px;">${ movieInfo.movieNm }</div>
	            	</div>
	            	<div id="likeDiv" style="display:flex; margin-left:30px; align-items:center;">
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
      		</div>
   		</div>
   	</div>
   	<div align="center" style="width:100%;">
   		<div style="width:80%;">
   			<hr style="border:1px solid rgba(0, 0, 0, 0.1)">
   		</div>
   	</div>
   <div align="center" style="width:100%;">
      <div align="center" style="width:80%; display:flex;">
         <div align="left" style="width:100%;">
            <div style="display:flex; justify-content:center;">
	            <div style="display:flex;">
		            <div style="margin-top:10px;">
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
				         			<div align="center">
						            	<span>
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
					            	<br>
					            	
					            	<!-- 영화 평점 그래프 -->
					            	<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
										<div id="graph-table-div" style="display:none;">
											<table>
												<tr>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">0.5</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">1</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">1.5</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">2</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">2.5</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">3</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">3.5</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">4</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">4.5</div></div></div></td>
													<td><div class="graph-bar-div"><div class="graph-bar-cnt"><div class="graph-bar-content" style="display:none;">5</div></div></div></td>
												</tr>
											</table>
										</div>
									</c:if>
									<!-- 영화 평점 그래프 -->
									
				            	</div>
			            	</div>
            			</div>
			         </div>
			         <div style="display:flex; margin-left:200px; padding-top:0px;">
			         	<div>
				         	
				            <div style="display:flex; margin-left:10px;">
				            	<br><br>
				            	<div style="width:60%; text-align:left; margin-top:10px;">
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
				            </div>

							<br>


				            <div style="border-top:1px solid #ccc">
				            
<!----------------------------------------------------- 코멘트 START ----------------------------------------------------------->
				            <c:if test="${empty sessionScope.userEmail }">
				            	<div style="width:700px;">
				            		
				            	</div>
				            </c:if>
				            
							   	<c:if test="${not empty sessionScope.userEmail }">
								   	<c:choose>
									    <c:when test="${ifWroteComment.reviewComment eq 'nan' || ifWroteComment.reviewComment eq null}">
										    <div  style="font-size:15px; margin:10px; margin-top:20px;">
										      내가 작성한 코멘트
											</div>
											<div style="margin:auto; display:flex; width:700px; background-color: #F8F8F8; border: 2px solid #ccc; border-radius: 5px; padding: 10px;">
											
												<div style="margin:10px; width:75%; word-wrap:break-word;">
													아직 코멘트를 작성하지 않았어요. 코멘트를 작성해주세요.
												</div>
												
												<div align="center" style="width:25%; display: flex;  justify-content: center; align-items: center;">
													<c:choose>
														<c:when test="${sessionScope.isBlack eq 1 }"><!-- 블랙리스트일 경우 -->
															<button onclick="openBlackModal()">코멘트 작성하기</button>
														</c:when>
														
														<c:otherwise><!-- 블랙리스트가 아닐 경우 -->
															<button onclick="openCommentWritingModal('${ movieInfo.movieNm }', '${ movieInfo.movieId }')">코멘트 작성하기</button>
														</c:otherwise>
													</c:choose>
												</div>
												
											</div> 
									    </c:when>
									    
									    
									    <c:otherwise>
										   
											<div style="margin:auto; display:flex; width:700px; background-color: #F8F8F8; border-radius: 5px; padding: 10px;">
											
												<div style="margin:10px; width:85%; word-wrap:break-word;">
<%-- 													<span style="font-size:0.8em; color:#888; display:block;">${ifWroteComment.userEmail }</span><br> --%>
										            <span style="color:#424242; display:block;"><b>${ifWroteComment.reviewComment}</b></span>
										            <span style="font-size: 0.8em; color: #888; display:block;">${ifWroteComment.reviewCommentDate}</span>
												</div>
												
												<div align="center" style="width:15%; display: flex;  justify-content: center; align-items: center;">
													<c:choose>
														<c:when test="${sessionScope.isBlack eq 1 }"><!-- 블랙리스트일 경우 -->
															<img src="resources/img/thinPencil.png" onclick="openBlackModal()" style="cursor:pointer; width:20px; margin-right:15px;"/>
															|
															<img src="resources/img/bin.png" onclick="openBlackModal()" style="padding-left:8px; cursor:pointer; width:20px; margin-left:10px;"/>
														</c:when>
														<c:otherwise>
															<img src="resources/img/thinPencil.png" onclick="openModifyModal('${ movieInfo.movieNm }' , '${movieInfo.movieId }', '${ifWroteComment.reviewComment}');" style="cursor:pointer; width:20px; margin-right:15px;"/>
															|
															<img src="resources/img/bin.png" onclick="location.href='/deleteComment?id=${ifWroteComment.id }&movieId=${movieInfo.movieId }'" style="padding-left:8px; cursor:pointer; width:20px; margin-left:10px;"/>
														</c:otherwise>
													</c:choose>
												</div>
												
											</div>   
		
									   </c:otherwise>
								   </c:choose>
							   </c:if>
											            
				            
				            
				            </div>
						</div>
						
		            </div>

		     	</div>
		     	
	     	</div>
	     	
         </div>
      </div>
   </div>
   <!------------------------------------- 배우 START  -------------------------------------------------->
   
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
   <!------------------------------------- 배우 END  -------------------------------------------------->
   
      <!------------------------------------- 단체 코멘트 START  -------------------------------------------------->
<c:if test="${fn:length(comments) ne 0 }">
   <br><br>

		<div style="z-index: 0;"><!-- 추가한 부분! -->
			<div align="center" style="width:100%;">
				<div align="center" style="width:80%;">
					<div style="display:flex;">
						<div align="left" style="width:100%; padding-bottom:10px;">
							<span style="font-size:30px; font-weight:bold;">Comments</span>
							<span style="vertical-align:2px; font-size:15px; margin-left:5px; margin-top:10px; color: rgb(160, 160, 160);">
            					${fn:length(comments)}
        					</span>
						</div>
						<c:if test="${fn:length(comments) >= 9}">
						<div align="right" style="width:80%; margin-top:15px; margin-right:10px;">
		        			<a href="/viewAllCommentsForThisMovie?movieId=${movieInfo.movieId }" style="color:rgb(255, 47, 110); text-decoration: none;">
	     						더보기
	   						</a>
						</div>
						</c:if>
					</div>
				</div>
			
			<div align="center" style="width:100%">
				<table style="text-align:center">
					<tr>
					<c:forEach begin="1" end="4">
						<td width="380px" height="0" style="text-align:center;">
							<div style="position:relative;height:0">
								<div style="text-align:center; margin:10px; width:350px; height:0 font-size:0;">
									&nbsp
								</div>
							</div>
						</td>
					</c:forEach>
					</tr>	
	   				<tr>
					<c:set var="cnt" value="0"/>
	   				<c:forEach var="dto" items="${comments}">
	   				<c:if test="${dto.reviewComment != 'nan' }">
	   					<c:if test="${cnt < 8 }">
		   					<c:if test="${cnt%4==0 }">
		   						</tr>
		   						<tr>
		   					</c:if>
	   					
                    <td height="330px" width="380px" style="text-align:center; word-wrap:break-word;">
                        <div style="position:relative">
                            <div style="text-align:center; margin:10px;background-color:#F2F2F2; width:350px; height: 300px; border-radius:5px; overflow: hidden;">
                                <div style="margin:10px; width:304px; height:280px;">
                                	
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
                                	<div class="date" style="display:flex; height:13%;font-size: 0.8em; color:#888; margin-left:10px; margin-right:5px;">
                                	
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
                                		
                                		<div class="comment" style="text-align:right; margin-right:5px;width:50%;">
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
	    			</c:if>
	   			</c:forEach>
	   					</tr>
	   			</table>
   			</div>
   		</div>
 </div>
 </c:if>
<!-----------------------  modal  -------------------------------------------------------------------------------------->
<!-- 댓글 작성용 modal -->
<div id="modalForWritingComment" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitleForWriting" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModalForWriting();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		<textarea style="width:340px; height:250px;"id="movieCommentForWriting"></textarea>
	   		<input type="hidden" id="modalMovieIdForWritingComment" />
	   		
	   	</div>
	   	<div>
	   		<button style="margin-top:10px; margin-left:40%" onclick="saveMovieComment();">작성하기</button>
	   	</div>
  	</div>
</div>
<!-- 댓글 수정용 modal -->
<div id="modalForModifyComment" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitleForModify" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModalForModify();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		<textarea style="width:340px; height:250px;"id="movieCommentForModify"></textarea>
	   		<input type="hidden" id="modalMovieIdForModify" />
	   		
	   	</div>
	   	<div>
	   		<button style="margin-top:10px; margin-left:40%" onclick="updateMovieComment();">수정하기</button>
	   	</div>
  	</div>
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
 
 <div id="modalForBlack" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:200px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:150px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span class="closeModalButton" onclick="closeModalForBlack();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		${sessionScope.userEmail} 님은 <br>적절하지 않은 댓글 활동으로 인하여<br> 댓글 기능을 더 이상 이용할 수 없습니다.
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
<!-----------------------  modal  End-------------------------------------------------------------------------------------->

   <!------------------------------------- 코멘트 END  -------------------------------------------------->

   
   
   
   <!------------------------------------- 포스터 START  -------------------------------------------------->
      
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