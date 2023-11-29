<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="/resources/css/header.css">
</head>
<body id="header-body">
	<br>
	<div align="center" class="header-page" >
		<div class="header-width" >
			<div align="left" class="header-left-div" >
				<a href="/"><img class="header-logo"  src="/resources/img/WWlogo.png"></a>
				<!------------------------------ Search ------------------------------>
				<div class="header-search-div" >
					<input id="query" class="search-input"  type="text" onclick="searchesClick();" onblur="searchesBlur();" onkeyup="searchesKeyup(event)" value="${ query }" autocomplete='off' placeholder="영화, 배우명 등을 검색해보세요.">
					<div id="searches" class="auto-searches" >
						<div id="recentDiv">
							<c:if test="${ not empty recentSearches }">
								<div class="recent-search-div" style="display:;">
									<div class="recent-search-font" >최근 검색어</div>
									<a class="recent-search-remove tag-usnet-pointer"  onmousedown="searchAllMove('${recentSearchesSize}');">
										<div class="recent-search-remove-div">모두 삭제</div>
									</a>
								</div>
								<c:forEach var="list" items="${ recentSearches }">
									<a class="recent-search-list tag-usnet-pointer"  onmousedown="searchStartMouseDown('${list}')">
										<div class="relatedSearches recent-search-list-content" >${ list }</div>
									</a>
									<input type="hidden" class="relatedSearchesValue" value="${ list }">
								</c:forEach>
							</c:if>
						</div>
						<c:if test="${ not empty popularSearches }">
							<div class="popular-search-font" >인기 검색어</div>
							<c:forEach var="list" items="${ popularSearches }">
								<a class="popular-search-list tag-usnet-pointer"  onmousedown="searchStartMouseDown('${list}')">
									<div class="relatedSearches popular-search-list-content" >
										${ list }
									</div>
								</a>
								<input type="hidden" class="relatedSearchesValue" value="${ list }">
							</c:forEach>
						</c:if>
					</div>
					<div id="searches2" class="related-search-div" >
						<div class="related-search-font">연관 검색어</div>
					</div>
				</div>
				<!------------------------------ Search ------------------------------>
				<!------------------------------ Live Search ------------------------------>
				<c:if test="${ not empty liveSearch }">
					<div class="live-search-page" >
						<div class="live-search-div" onmouseover="liveSearchMouseOver();" onmouseout="liveSearchMouseOut();" >
							<div class="live-search-font">실시간 인기 검색어</div>
							<div id="locationDiv" class="live-search-down" >▽</div>
							<div id="liveSearchList" class="live-search-content-div" >
								<c:forEach var="list" items="${ liveSearch }">
									<a onclick="liveSearchClick('${list.content}');" class="tag-usnet-pointer" >
										<div class="live-search-content-location" >
											<div class="live-search-left" >
												<div class="left-num-div" >
													<span class="left-num" >${list.num}</span>
												</div> 
												<div>
													${ list.content }
												</div>
											</div>
											<c:if test="${ list.state eq '0' }">
												<div class="live-search-right" >
													<span>-</span>
												</div>
											</c:if>
											<c:if test="${ list.state ne 'new' }">
												<c:if test="${ Integer.parseInt(list.state) > 0 }">
													<div class="live-search-right">
														<span class="live-search-up-font">↑</span>
														${ list.state.substring(0, 1) }
													</div>
												</c:if>
												<c:if test="${ Integer.parseInt(list.state) < 0 }">
													<div class="live-search-right" >
														<span class="live-search-down-font">↓</span>
														${ list.state.substring(1, 2) }
													</div>
												</c:if>
											</c:if>
											<c:if test="${ list.state eq 'new' }">
												<div class="live-search-right" >
													<span class="live-search-new-font">${ list.state }</span>
												</div>
											</c:if>
										</div>
									</a>
									<input type="hidden" value="${ list.content }">
								</c:forEach>
							</div>
						</div>
						
					</div>
				</c:if>				
				<!------------------------------ Live Search ------------------------------>
			</div>
			
			<div align="right" id="header" class="header-right-div">
				<c:choose>
					<c:when test="${empty sessionScope.userLoginType }"> <!-- 로그인하기 전 -->
						<a href="/signIn" class="login-btn">로그인</a>&nbsp	&nbsp	
						<button class="sign-up-btn">
							<a href="selectSignUpType" class="sign-up-font">회원가입</a>
						</button>
					</c:when>
					<c:otherwise> <!-- 로그인한 후 -->
						<a class="notify-bell" href="/showNotifyList">
							<c:choose>
								<c:when test="${sessionScope.newNoti eq 1 }"> <!-- 새로운 알림이 있는 경우 -->
									<img style="width:30px; margin-right:20px;" src="/resources/img/bellWithNotification.png">
								</c:when>
								<c:otherwise> <!-- 새로운 알림이 없는 경우 -->
									<img style="width:30px; margin-right:20px;" src="/resources/img/bell.png">
								</c:otherwise>
							</c:choose>						
						</a>
						
						<a class="profile-btn" href="/memberInfo">
							<div style="position:relative; margin-top: -4px;">
								<c:choose>
									<c:when test="${sessionScope.profileImg eq 'nan' }">
	                       				<div style="position:relative;width:40px; height:40px;overflow: hidden; border-radius: 50%;">                                			
	                       					<img style="width:40px;"src="resources/img/basicProfileImg.png"/>
	                       				</div>								
	                       			</c:when>
									<c:otherwise>
	                               		<div style="position:relative;width:40px; height:40px;overflow: hidden; border-radius: 50%;">
	                               			<img style="width: 40px; height: 40px;object-fit: cover; object-position: center center;transition: transform 0.3s ease-in-out;" src="/resources/profile_img/${sessionScope.userEmail }/${sessionScope.profileImg}"/>
	                               		</div>   									
									</c:otherwise>
								</c:choose>
							</div>
						</a>
					</c:otherwise>
				</c:choose>
					<!-------------------------------------------------------------------------------------------------------------------------------------- -->
			</div>
			<hr>
		</div>
	</div>
	<br>
	<script src="/resources/js/common.js"></script>
	<script src="/resources/js/search_common.js"></script>
</body>
</html>