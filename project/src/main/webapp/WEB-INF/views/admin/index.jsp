<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/admin/auto-table.css">
<link rel="stylesheet" href="/resources/css/admin/index.css">

<title>Insert title here</title>
</head>
<body>
	<%@ include file="./include/header.jsp" %>
	<% int liveCnt = 0; %>
	<div align="center" class="index-page">
		<div class="page-width" style="display:flex;">
			<div align="left" class="live-search-page">
				<div onclick="liveSearchLeftBtn();" id="live-search-left-btn" class="live-search-left-btn">◁</div>
				<div class="live-search-div">
					<table>
						<tr>
							<th>순위</th>
							<th>검색 수</th>
							<th>검색어</th>
						</tr>
						<c:if test="${ empty liveSearch }">
							<tr>
								<td colspan="3" class="table-width-450">
								<br><br><br><br><br>
								실시간 인기검색어가 없습니다.
								</td>
							</tr>
						</c:if>
						<c:forEach var="list" items="${ liveSearch }">
							<tr class="live-search-content" style="display:none;">
								<td class="table-width-75 live-search-contents">${ list.num }</td>
								<td class="table-width-75 live-search-contents">${ list.cnt }</td>
								<td class="table-width-300 live-search-contents">${ list.content }</td>
							</tr>
							<% liveCnt++; %>
						</c:forEach>
					</table>
				</div>
				<% if(liveCnt > 10){ %>
					<div onclick="liveSearchRightBtn();" id="live-search-right-btn" class="live-search-right-btn">▷</div>
				<% } %>
			</div>

		</div>
	</div>

	<script src="/resources/js/admin/index.js"></script>
</body>
</html>