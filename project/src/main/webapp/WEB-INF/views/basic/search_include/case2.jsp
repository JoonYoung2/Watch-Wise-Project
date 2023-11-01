<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	&quot${ query }&quot 검색결과  ${ searchList2.size() }개
				
				<!-- SearchCase2 검색결과 Movies START -->
				
				<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">&quot${ query }&quot 검색결과</span></div>
				<div style="display:flex; position:relative">
					
					<button id="searchMoviesLeftBtn" class="leftBtn" onclick="searchMoviesLeftBtn();">◀</button>
						
					<c:forEach var="list" items="${ searchList2 }">
					<% 
						if(cnt < 5){ 
					%>
					<a href="/movieInfo?movieId=${ list.movieId }" class="searchMovies" style="all:unset; cursor:pointer; display:;">
						<div style="padding-right:10px;">
							<c:if test="${ list.posterUrl ne 'nan' }">
								<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
							</c:if>
							<c:if test="${ list.posterUrl eq 'nan' }">
								<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>								</c:if>
								<div align="left">
									<c:if test="${ list.movieNm.length() > 15 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
									</c:if>
									<c:if test="${ list.movieNm.length() <= 15 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
									</c:if>
									${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
									<c:set var="gradeNum" value="${ list.gradeAvg } + ''"/>
									<c:if test="${ list.gradeCheck eq true }">
										<span style="color:orange;">평가함 ★ ${ gradeNum.substring(0,3) }</span>
									</c:if>
									<c:if test="${ list.gradeCheck ne true }">
										<span style="color:red;">평균 ★ ${ gradeNum.substring(0,3) }</span>
									</c:if>
								</div>
							</div>
						</a>
						<%
						}else{
						%>
						<a href="/movieInfo?movieId=${ list.movieId }" class="searchMovies" style="all:unset; cursor:pointer; display:none;">
							<div style="padding-right:10px;">
								<c:if test="${ list.posterUrl ne 'nan' }">
									<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
								</c:if>
								<c:if test="${ list.posterUrl eq 'nan' }">
									<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
								</c:if>
								<div align="left">
									<c:if test="${ list.movieNm.length() > 18 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
									</c:if>
									<c:if test="${ list.movieNm.length() <= 18 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
									</c:if>
									${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
									<c:if test="${ list.gradeCheck eq true }">
										<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
									</c:if>
									<c:if test="${ list.gradeCheck ne true }">
										<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
									</c:if>
								</div>
							</div>
						</a>
						<%
							}
						cnt++;
						%>
							
						</c:forEach>
						<% if(cnt > 5){ %>
							<button id="searchMoviesRightBtn" class="rightBtn" type="button" onclick="searchMoviesRightBtn('<%=cnt%>');">▶</button>
						<% } %>
					</div>
					
				<!-- SearchCase2 검색 결과 Movies END -->
				
				<% cnt = 0; %>
				<c:if test="${ memberCommend.size() > 0 }">
					<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">회원님만을 위한 추천영화</span></div>
					<div style="display:flex; position:relative">
					
						<button id="memberCommendLeftBtn" class="leftBtn" onclick="memberCommendLeftBtn();">◀</button>
						
						<!-- SearchCase2 영화 추천 Movies START -->
						
						<c:forEach var="list" items="${ memberCommend }">
						<% 
							if(cnt < 5){ 
						%>
						<a href="/movieInfo?movieId=${ list.movieId }" class="memberCommend" style="all:unset; cursor:pointer; display:;">
							<div style="padding-right:10px;">
								<c:if test="${ list.posterUrl ne 'nan' }">
									<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
								</c:if>
								<c:if test="${ list.posterUrl eq 'nan' }">
									<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
								</c:if>
								<div align="left">
									<c:if test="${ list.movieNm.length() > 15 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
									</c:if>
									<c:if test="${ list.movieNm.length() <= 15 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
									</c:if>
									${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
									<c:set var="gradeNum" value="${ list.gradeAvg } + ''"/>
									<c:if test="${ list.gradeCheck eq true }">
										<span style="color:orange;">평가함 ★ ${ gradeNum.substring(0,3) }</span>
									</c:if>
									<c:if test="${ list.gradeCheck ne true }">
										<span style="color:red;">평균 ★ ${ gradeNum.substring(0,3) }</span>
									</c:if>
								</div>
							</div>
						</a>
						<%
						}else{
						%>
						<a href="/movieInfo?movieId=${ list.movieId }" class="memberCommend" style="all:unset; cursor:pointer; display:none;">
							<div style="padding-right:10px;">
								<c:if test="${ list.posterUrl ne 'nan' }">
									<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
								</c:if>
								<c:if test="${ list.posterUrl eq 'nan' }">
									<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
								</c:if>
								<div align="left">
									<c:if test="${ list.movieNm.length() > 18 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
									</c:if>
									<c:if test="${ list.movieNm.length() <= 18 }">
										<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
									</c:if>
									${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
									<c:if test="${ list.gradeCheck eq true }">
										<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
									</c:if>
									<c:if test="${ list.gradeCheck ne true }">
										<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
									</c:if>
								</div>
							</div>
						</a>
						<%
							}
						cnt++;
						%>
							
						</c:forEach>
						<% if(cnt > 5){ %>
							<button id="memberCommendRightBtn" class="rightBtn" type="button" onclick="memberCommendRightBtn('<%=cnt%>');">▶</button>
						<% } %>
					</div>
					<!-- SearchCase2 영화 추천 Movies END -->
				</c:if>
				
				<% cnt = 0; %>
				
				<!-- SearchCase2 Movie Informations START -->
				<c:forEach var="movieInfo" items="${ searchList2 }">
					<a style="all:unset; cursor:pointer;" href="movieInfo?movieId=${ movieInfo.movieId }">
						<div style="display: flex;">
							<c:if test="${movieInfo.posterUrl ne 'nan' }">
								<div align="center" style="width: 50%;">
									<img style="width: 40%;" src="${ movieInfo.posterUrl }">
								</div>
							</c:if>
							<c:if test="${movieInfo.posterUrl eq 'nan' }">
								<div align="center" style="width: 50%;">
									<div style="width: 300px; height: 400px; border: 1px solid rgba(0, 0, 0, 0.1); display: flex; justify-content: center; align-items: center;">이미지가
										없습니다.
									</div>
								</div>
							</c:if>
	
							<div align="left" style="width: 50%;">
								<br>
								<br>
								<br> <span style="font-size: 40px; font-weight: bold;">${ movieInfo.movieNm }</span><br>
								<br>
								<c:if test="${ movieInfo.movieNmEn ne 'nan' }">
									<span style="font-size: 18px;">${ movieInfo.movieNmEn }</span>
									<br>
								</c:if>
								<c:if test="${ movieInfo.openDt ne 'nan' }">
									<span style="font-size: 18px;">${ movieInfo.openDt.substring(0, 4) }</span>
								</c:if>
								<c:if test="${ movieInfo.genreNm ne 'nan' }">
								・ <span style="font-size: 18px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
								</c:if>
								<c:if test="${ movieInfo.nations ne 'nan' }">
								・ <span style="font-size: 18px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
								</c:if>
								<br> <span style="font-size: 18px;">${ movieInfo.showTime }</span>
								<c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
								・ <span style="font-size: 18px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
								</c:if>
								<br>
								<c:if test="${ movieInfo.openDt ne 'nan' }">
									<span style="font-size: 18px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년
										${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span>
								</c:if>
							</div>
						</div>
					</a>
					<c:if test="${ movieInfo.movieActorList.size() > 0 }">
						<br><br><br>
						<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Actors</span></div>
						<div style="display:flex;">
							<c:forEach var="peopleInfo" items="${ movieInfo.movieActorList }">
								<c:if test="${ not empty peopleInfo.cast && peopleInfo.peopleId ne '0' }">
									<a href="peopleInfo?peopleId=${ peopleInfo.peopleId }" style="all: unset; cursor: pointer; display: flex; width: 25%;">
										<div>
											<img style="width: 80px; height: 80px;" src="/resources/img/bean_profile.png">
										</div>
										<div align="left" style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left: 20px;">
											<div style="padding-top: 10px;">
												<span style="font-size: 22px; font-weight: bold;">${ peopleInfo.peopleNm }</span>
											</div>
											<div style="padding-top: 5px;">
												<c:if test="${ peopleInfo.cast ne 'nan' }">
													<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.cast } </span>
												</c:if>
												<c:if test="${ peopleInfo.cast eq 'nan' }">
													<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연</span>
												</c:if>
											</div>
										</div>
									</a>
								</c:if>
								<c:if test="${ not empty peopleInfo.cast && peopleInfo.peopleId eq '0' }">
									<div style="display: flex; width: 25%;">
										<div>
											<img style="width: 80px; height: 80px;" src="/resources/img/bean_profile.png">
										</div>
										<div align="left" style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left: 20px;">
											<div style="padding-top: 10px;">
												<span style="font-size: 22px; font-weight: bold;">${ peopleInfo.peopleNm }</span>
											</div>
											<div style="padding-top: 5px;">
												<c:if test="${ peopleInfo.cast ne 'nan' }">
													<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.cast } </span>
												</c:if>
												<c:if test="${ peopleInfo.cast eq 'nan' }">
													<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연</span>
												</c:if>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</c:if>
					<br><br><br>
				</c:forEach>
				<!-- SearchCase2 Movie Informations END -->
</body>
</html>