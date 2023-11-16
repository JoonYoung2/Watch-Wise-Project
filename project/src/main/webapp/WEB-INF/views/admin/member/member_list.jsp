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
<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
	<% int cnt = 0; %>
	<%@ include file="../include/header.jsp" %>
	<br><br><br>
	<div align="center" class="list-page">
		<div align="center" class="list-page-div">
			<table>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 1 }">
						<div class="auto-table-header">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="********* 입력해주세요" onkeydown="search(event, '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
							<div class="header-rownum-div">Row Num : <input class="header-rownum-config" id="rowNum" type="number" value="${ autoPaging.rowNum }" onchange="rowNumUpdate('${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
						</div>
					</td>
				</tr>
				<tr>
					<c:forEach var="list" items="${ autoPaging.titleList }">
						<th>${ list }</th>
					</c:forEach>
					<th>삭제</th>
				</tr>
				<c:forEach var="list" items="${ contentList }">
					<input type="hidden" class="content-values<%= cnt %>" value="<!-- 수정수정 -->">
					<tr>
						<td class="table-width-50">${ list.num }</td>
						<td class="table-width-50">${ list.id }</td>
						<td class="table-width-300">${ list.userEmail }</td>
						<td class="table-width-150">${ list.userPw }</td>
						<td class="table-width-50">${ list.userName }</td>
						<td class="table-width-75">
							<c:if test="${ list.kakaoAgreement eq 0 }">
							X
							</c:if>
							<c:if test="${ list.kakaoAgreement eq 1 }">
							O
							</c:if>
						</td>
						<td class="table-width-75">
							<c:if test="${ list.naverAgreement eq 0 }">
							X
							</c:if>
							<c:if test="${ list.naverAgreement eq 1 }">
							O
							</c:if>
						</td>
						<td class="table-width-75">
							<c:if test="${ list.googleAgreement eq 0 }">
							X
							</c:if>
							<c:if test="${ list.googleAgreement eq 1 }">
							O
							</c:if>
						</td>
						<td class="table-width-50"><a href="/deleteMember?userEmail=${ list.userEmail }&pageNum=${autoPaging.pageNum}">삭제</a></td>
					</tr>
					<% cnt++; %>
				</c:forEach>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 1 }">
						<div align="center" class="auto-table-footer">
						
							<div style="width:100px;"></div>
							
							<c:if test="${ autoPaging.pageNum > 5 }">
								<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/member_info/id/1?query=${ query }"> 1 </a></span>...
							</c:if>
							<c:forEach var="i" begin="${ autoPaging.start }" end="${ autoPaging.end }" step="1">
								<c:if test="${ autoPaging.pageNum ne i }">
									<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/member_info/id/${ i }?query=${ query }"> ${ i } </a></span>
								</c:if>
								<c:if test="${ autoPaging.pageNum eq i }">
									<span class="footer-paging-span"> ${ i } </span>
								</c:if>
							</c:forEach>
							<c:if test="${ autoPaging.last ne 0 }">
								...<span class="footer-paging-span"><a class="footer-paging-number" href="/admin/member_info/id/${ autoPaging.last }?query=${ query }"> ${ autoPaging.last } </a></span>
							</c:if>
							
							<div style="width:100px;"></div>
							
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>