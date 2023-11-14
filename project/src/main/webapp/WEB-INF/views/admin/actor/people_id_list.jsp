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
			<table>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div class="auto-table-header">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="ID 또는 배우명을 입력해주세요" onkeydown="search(event, '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
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
					<input type="hidden" class="content-values<%= cnt %>" value="<!-- 수정수정 -->">
					<tr>
						<td class="table-width-50">${ list.num }</td>
						<td class="table-width-100">${ list.peopleId }</td>
						<td class="table-width-250">${ list.peopleNm }</td>
						<td class="table-width-50">${ list.sex }</td>
						<td class="table-width-50">${ list.likeNum }</td>
						<td class="table-width-50"><button type="button" style="all:unset; cursor:pointer;" onclick="updateForm('<%=cnt%>')">수정</button></td>
						<td class="table-width-50">삭제</td>
					</tr>
					<% cnt++; %>
				</c:forEach>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div align="center" class="auto-table-footer">
							<div style="width:100px;"></div>
							<c:if test="${ autoPaging.pageNum > 5 }">
								<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/people_info_detail/people_id/1?query=${ query }"> 1 </a></span>...
							</c:if>

							<c:forEach var="i" begin="${ autoPaging.start }" end="${ autoPaging.end }" step="1">
								<c:if test="${ autoPaging.pageNum ne i }">
									<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/people_info_detail/people_id/${ i }?query=${ query }"> ${ i } </a></span>
								</c:if>
								<c:if test="${ autoPaging.pageNum eq i }">
									<span class="footer-paging-span"> ${ i } </span>
								</c:if>
							</c:forEach>
							
							
							<c:if test="${ autoPaging.last ne 0 }">
								...<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/people_info_detail/people_id/${ autoPaging.last }?query=${ query }"> ${ autoPaging.last } </a></span>
							</c:if>
							<div class="footer-register-div">
								<a class="footer-register-btn" onclick="insertForm();">등록</a>
							</div>
						</div>
					</td>
				</tr>
			</table>
			
			
			<form id="insertForm" action="<!-- 수정수정 -->" method="post" style="display:none;">
				<input type="text" class="insert-input" name="" placeholder=""><br>
				<button class="insert-btn">등록</button><button class="insert-btn" type="button" onclick="insertClose();">닫기</button>
			</form>
			<form id="updateForm" action="<!-- 수정수정 -->" method="post" style="display:none;">
				<input type="text" class="update-input" name="" id=""><br>
				<button class="update-btn">수정</button><button class="update-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>