<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/admin/auto-table.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<title>Insert title here</title>
</head>
<body id="body">
	<% int cnt = 0; %>
	<%@ include file="../include/header.jsp" %>
	<br><br><br>
	<div align="center" class="list-page">
		<div align="center" class="list-page-div">
			
			<c:if test="${ not empty msg }">
				<span style="color:red;">${ msg }</span><br><br><br>
			</c:if>
			<!-- order by href 수정 및 추가 -->
			<div class="auto-table-menu-header">
				<a class="auto-table-menu" href="추가"><div class="auto-table-menu-div">추가</div></a>
			</div>
			<br><br><br>
			
			<table>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div class="auto-table-header">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="~~~~~~~~~~~~을 입력해주세요" onkeydown="search(event,'${ autoPaging.listNm }', '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
							<div class="header-rownum-div">Row Num : <input class="header-rownum-config" id="rowNum" type="number" value="${ autoPaging.rowNum }" onchange="rowNumUpdate('${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
						</div>
					</td>
				</tr>
				<tr>
					<c:forEach var="list" items="${ autoPaging.titleList }">
						<th>${ list }</th>
					</c:forEach>
					<th>수정</th>
					<th>삭제</th>
				</tr>
				<c:forEach var="list" items="${ contentList }">
					<!-- update를 위한 값 설정 -->
					<input type="hidden" class="content-values<%= cnt %>" value="<!-- 맞게 수정 -->">
					<tr>
						<!-- list 맞게 수정 -->
						<td class="table-width-50"><!-- 수정 위치 --></td>
						<td class="table-width-50"><button type="button" style="all:unset; cursor:pointer;" onclick="updateForm('<%=cnt%>')">수정</button></td>
						<td class="table-width-50">
							<!-- 파라미터 수정 -->
							<c:if test="${ not empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}?movieId=${list.movieId}" style="all:unset; cursor:pointer;">삭제</a>
							</c:if>
							<c:if test="${ empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan?movieId=${list.movieId}" style="all:unset; cursor:pointer;">삭제</a>
							</c:if>
							<!-- 파라미터 수정 -->
						</td>
					</tr>
					<% cnt++; %>
				</c:forEach>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div align="center" class="auto-table-footer">
							<div style="width:100px;"></div>
							<c:if test="${ autoPaging.pageNum > 5 && autoPaging.end > 10 }">
								<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/1?query=${ query }"> 1 </a></span>...
							</c:if>

							<c:forEach var="i" begin="${ autoPaging.start }" end="${ autoPaging.end }" step="1">
								<c:if test="${ autoPaging.pageNum ne i }">
									<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ i }?query=${ query }"> ${ i } </a></span>
								</c:if>
								<c:if test="${ autoPaging.pageNum eq i }">
									<span class="footer-paging-span"> ${ i } </span>
								</c:if>
							</c:forEach>
							
							
							<c:if test="${ autoPaging.last ne 0 }">
								...<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.last }?query=${ query }"> ${ autoPaging.last } </a></span>
							</c:if>
							<div class="footer-register-div">
								<a class="footer-register-btn" onclick="insertForm();">등록</a>
							</div>
						</div>
					</td>
				</tr>
			</table>
			
			<!-- 등록 or 업데이트 수정 -->
			<c:if test="${ not empty query }">
				<form id="insertForm" action="/insert/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}" method="post" style="display:none;">
			</c:if>
			<c:if test="${ empty query }">
				<form id="insertForm" action="/insert/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan" method="post" style="display:none;">
			</c:if>
				<input type="text" class="insert-input" name="" placeholder=""><br>
				<button class="insert-btn">등록</button><button class="insert-btn" type="button" onclick="insertClose();">닫기</button>
			</form>
			<c:if test="${ not empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}" method="post" style="display:none;">
			</c:if>
			<c:if test="${ empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan" method="post" style="display:none;">
			</c:if>
				<input type="text" class="update-input" name="" id="" placeholder=""><br>
				<button class="update-btn">수정</button><button class="update-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>