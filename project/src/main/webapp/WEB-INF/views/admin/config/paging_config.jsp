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
	<%@ include file="../include/header.jsp" %>
	<div align="center" style="width:100%;">
		<div align="center" style="width:80%;">
			${ msg }		
			<table>
				<tr>
					<th>테이블명</th>
					<th>컬럼명</th>
					<th>정렬컬럼명</th>
					<th>행개수</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
				<c:if test="${ empty pagingConfig }">
					<tr>
						<th colspan="6">등록된 정보가 없습니다.</th>
					</tr>
				</c:if>
				<c:if test="${ not empty pagingConfig }">
					<c:forEach var="list" items="${ pagingConfig }">
						<tr>
							<td>${ list.tableNm }</td>
							<td>${ list.columns }</td>
							<td>${ list.orderByColumn }</td>
							<td>${ list.rowNum }</td>
							<td><button type="button" onclick="updateClick('${ list.id }','${ list.tableNm }','${ list.columns }','${ list.orderByColumn }','${ list.rowNum }');">수정</button></td>
							<td><a href="/admin/deletePagingConfig?id=${ list.id }">삭제</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<th colspan="6"><button type="button" onclick="registerClick();">등록</button></th>
				</tr>
			</table>
			
			<form style="display:none;" id="registerForm" action="/admin/insertPagingConfig" method="post">
				<input type="text" name="tableNm" style="width:300px;" placeholder="테이블명"><br>
				<input type="text" name="columns" style="width:300px;" placeholder="컬럼명"><br>
				<input type="text" name="orderByColumn" style="width:300px;" placeholder="컬럼정렬명"><br>
				<input type="text" name="rowNum" style="width:300px;" placeholder="행개수"><br>
				<button style="width:150px;">등록</button><button style="width:150px;" type="reset">취소</button>
			</form>
			<form style="display:none;" id="updateForm" action="/admin/updatePagingConfig" method="post">
				<input id="id" name="id" type="hidden">
				<input id="tableNm" type="text" name="tableNm" style="width:300px;" placeholder="테이블명"><br>
				<input id="columns" type="text" name="columns" style="width:300px;" placeholder="컬럼명"><br>
				<input id="orderByColumn" type="text" name="orderByColumn" style="width:300px;" placeholder="컬럼정렬명"><br>
				<input id="rowNum" type="text" name="rowNum" style="width:300px;" placeholder="행개수"><br>
				<button style="width:150px;">수정</button><button style="width:150px;" type="reset">취소</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/paging_confg.js"></script>
</body>
</html>