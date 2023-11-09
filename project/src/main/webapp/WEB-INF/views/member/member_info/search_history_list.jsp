<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:if test="${ empty contentSearch }">
	<script>
		alert("검색 기록이 없습니다.");
		history.back();
	</script>
</c:if>


<br><br><br><br><br>
<div align="center" style="width:100%;">
	<div align="center" style="width:30%; position:relative;">
		<c:if test="${ not empty contentSearch }">
			<c:set var="date" value="${ contentSearch.get(0).searchDate.substring(0, 10) }"/>
			<c:set var="cnt" value="0"/>
			<table>
				<c:forEach var="list" items="${ contentSearch }">
					<c:if test="${ cnt eq 0 }">
						<tr>
							<th style="width:500px; border-bottom:3px solid rgba(0, 0, 0, 0.1);">${ list.searchDate.substring(0, 10) }</th>
							<th style="border-bottom:3px solid rgba(0, 0, 0, 0.1);"><div id="selectDiv"><div style="cursor:pointer;" onclick="allChoose();">모두</div></div></th>
						</tr>
					</c:if>
					<c:if test="${ date eq list.searchDate.substring(0, 10) }">
						<tr>
							<td style="text-align:center; border-bottom:1px solid rgba(0, 0, 0, 0.1); border-left:1px solid rgba(0, 0, 0, 0.1);">
								${ list.content }
							</td>
							<td style="text-align:center; border-bottom:1px solid rgba(0, 0, 0, 0.1); border-right:1px solid rgba(0, 0, 0, 0.1);"><input class="checkboxClass" type="checkbox" value="${ list.id }" onclick="checkboxClick();"></td>
						</tr>
						<c:set var="cnt" value="1"/>
					</c:if>
					<c:if test="${ date ne list.searchDate.substring(0, 10) }">
						<tr>
							<th style="border-bottom:3px solid rgba(0, 0, 0, 0.1);">
								${ list.searchDate.substring(0, 10) }
							</th>
							<th style="border-bottom:3px solid rgba(0, 0, 0, 0.1);">
							</th>
						</tr>
						<tr>
							<td style="text-align:center; border-bottom:1px solid rgba(0, 0, 0, 0.1); border-left:1px solid rgba(0, 0, 0, 0.1);">
								${ list.content }
							</td>
							<td style="text-align:center; border-bottom:1px solid rgba(0, 0, 0, 0.1); border-right:1px solid rgba(0, 0, 0, 0.1);"><input class="checkboxClass" type="checkbox" value="${ list.id }" onclick="checkboxClick();"></td>
						</tr>
						<c:set var="date" value="${ list.searchDate.substring(0, 10) }"/>
					</c:if>
				</c:forEach>
			</table>
			
			<div style="position:fixed; top:400px; right:300px;">
				<div style="border:1px solid rgba(0, 0, 0, 0.1); width:300px; height:200px; display:flex; align-items:center; justify-content:center;">
					<div>
						<a onclick="deleteAllSearchHistory();" style="all:unset; cursor:pointer;">
							<div style="background-color:rgba(0, 0, 0, 0.5); color:white; width:200px; height: 40px; display:flex; align-items:center; justify-content:center; margin-bottom:20px;">
								검색기록 모두 삭제
							</div>
						</a>
						<a onclick="deleteSearchHistory();" style="all:unset; cursor:pointer;">
							<div style="background-color:rgba(0, 0, 0, 0.5); color:white; width:200px; height: 40px; display:flex; align-items:center; justify-content:center;">
								검색기록 선택 삭제
							</div>
						</a>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_history_list.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>