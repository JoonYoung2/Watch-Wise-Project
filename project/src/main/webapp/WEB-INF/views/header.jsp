<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body id="header-body">
	<header>
		<div align="center" style="width:100%;">
			<div style="width:80%; display:flex; justify-content:center; align-items:center;">
				<div align="left" style="width:50%; display:flex; align-items:center;">
					<a href="/"><img style="width:200px;" src="/resources/img/WWlogo.png"></a>
					
					<!------------------------------ Search ------------------------------>
					<div style="position:relative; padding-left:20px;">
						<input id="query" style="all:unset; cursor:auto; background-color:rgba(0, 0, 0, 0.05); padding-left:10px; width:300px; height:38px; font-size:18px;" type="text" onclick="searchesClick();" onblur="searchesBlur();" onkeyup="searchesKeyup(event)" value="${ query }" autocomplete='off' placeholder="영화, 배우명 등을 검색해보세요.">
						<div id="searches" style="position:absolute; width:306px; border:solid 1px rgba(0, 0, 0, 0.1); z-index:1; background-color:white; display:none;">
							<div id="recentDiv">
								<c:if test="${ not empty recentSearches }">
									<div style="display:flex; justify-content:space-between; width:100%; display:;">
										<div style="padding:10px; color:red;">최근 검색어</div>
										<a style="all:unset; cursor:pointer;" onmousedown="searchAllMove('${recentSearchesSize}');">
											<div style="padding:10px; color:rgba(0, 0, 0, 0.5);">모두 삭제</div>
										</a>
									</div>
									<c:forEach var="list" items="${ recentSearches }">
										<a style="all:unset; cursor:pointer;" onmousedown="searchStartMouseDown('${list}')"><div class="relatedSearches" style="padding:5px 10px; background-color:white;">${ list }</div></a>
										<input type="hidden" class="relatedSearchesValue" value="${ list }">
									</c:forEach>
								</c:if>
							</div>
							<c:if test="${ not empty popularSearches }">
								<div style='padding:10px; color:red;'>인기 검색어</div>
								<c:forEach var="list" items="${ popularSearches }">
									<a style="all:unset; cursor:pointer;" onmousedown="searchStartMouseDown('${list}')"><div class="relatedSearches" style="padding:5px 10px; background-color:white;">${ list }</div></a>
									<input type="hidden" class="relatedSearchesValue" value="${ list }">
								</c:forEach>
							</c:if>
						</div>
						<div id="searches2" style="position:absolute; width:306px; border:solid 1px rgba(0, 0, 0, 0.1); z-index:1; background-color:white; display:none;">
							<div style='padding:10px; color:red;'>연관 검색어</div>
						</div>
					</div>
					<!------------------------------ Search ------------------------------>
					
					<!------------------------------ Live Search ------------------------------>
					<c:if test="${ not empty liveSearch }">
						<div style="position:relative; padding-left:20px;">
							<div onmouseover="liveSearchMouseOver();" onmouseout="liveSearchMouseOut();" style="display:flex; justify-content:space-between; width:306px; cursor:pointer; border:1px solid rgba(0, 0, 0, 0.1);">
								<div style='padding:10px; color:red;'>실시간 인기 검색어</div><div id="locationDiv" style="padding:10px;">▽</div>
								<div id="liveSearchList" style="position:absolute; width:306px; top:43px; border:solid 1px rgba(0, 0, 0, 0.1); z-index:1; background-color:white; display: none;">
								<c:forEach var="list" items="${ liveSearch }">
									<a onclick="liveSearchClick('${list.content}');" style="all:unset; cursor:pointer;">
										<div style="display:flex; justify-content:space-between">
											<div style="padding:5px 0px; background-color:white; display:flex;"><div style="width:50px; text-align:center;"><span style="margin-right:5px;">${list.num}</span></div> ${ list.content }</div>
											<c:if test="${ list.state eq '0' }">
												<div style="padding:5px 10px; background-color:white; width:30px;">-</div>
											</c:if>
											<c:if test="${ list.state ne 'new' }">
												<c:if test="${ Integer.parseInt(list.state) > 0 }">
													<div style="padding:5px 10px; background-color:white; width:30px;"><span style="color:red;">↑</span>${ list.state.substring(0, 1) }</div>
												</c:if>
												<c:if test="${ Integer.parseInt(list.state) < 0 }">
													<div style="padding:5px 10px; background-color:white; width:30px;"><span style="color:blue;">↓</span>${ list.state.substring(1, 2) }</div>
												</c:if>
											</c:if>
											<c:if test="${ list.state eq 'new' }">
												<div style="padding:5px 10px; background-color:white; width:30px;"><span style="color:red; font-size:bold;">${ list.state }</span></div>
											</c:if>
										</div>
									</a>
									<input type="hidden"  value="${ list.content }">
								</c:forEach>
							</div>
							</div>
							
						</div>
					</c:if>
					<!------------------------------ Live Search ------------------------------>
					
				</div>
				<div align="right" id="header" style="width:50%;">
				<c:if test="${empty sessionScope.userLoginType }">
					<a href="/signIn" style="text-decoration:none; color:inherit; font-size:13px;">로그인</a>&nbsp	&nbsp	
					<button style="background-color:transparent; border:1px solid rgb(200, 200, 300); padding:5px; border-radius:5px;">
						<a href="selectSignUpType" style="text-decoration:none; color:inherit; font-size:13px;">회원가입</a>
					</button>
				</c:if>
					<!-------------------------------------------------------------------------------------------------------------------------------------- -->
				<c:if test="${not empty sessionScope.userEmail }">
					<a style="cursor:pointer" href="/memberInfo">
						<img style="width:30px; cursor:pointer;" src="resources/img/bean_profile.png"/>
					</a>
				</c:if>
				</div>
				<hr>
			</div>
		</div>
	</header>
</body>
</html>