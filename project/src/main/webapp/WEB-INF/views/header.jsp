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
					<div style="position:relative;">
						<input id="query" style="width:300px; height:38px; font-size:20px;" type="text" onclick="searchesClick();" onblur="searchesBlur();" onkeyup="searchesKeyup(event)" value="${ query }" autocomplete='off'>
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