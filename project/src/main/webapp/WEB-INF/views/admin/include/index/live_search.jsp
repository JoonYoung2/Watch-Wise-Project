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
	<% int liveCnt = 0; %>
	<div class="page-width">
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
						<input type="hidden" class="chart-live-search-content" value="${ list.content }">
						<input type="hidden" class="chart-live-search-cnt" value="${ list.cnt }">
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
		<div class="bar-chart-search">
			<canvas id="bar-chart-live-search"></canvas>
		</div>
	</div>
</body>
</html>