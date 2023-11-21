<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/admin/paging_config.css">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	<br><br>
	<div align="center" class="paging-config-page">
		<div align="center" class="paging-config-div">
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
						<tr class="table-tr">
							<td>${ list.tableNm }</td>
							<td>${ list.columns }</td>
							<td>${ list.orderByColumn }</td>
							<td>${ list.rowNum }</td>
							<td><button class="table-update-btn" type="button" onclick="updateClick('${ list.id }','${ list.tableNm }','${ list.columns }','${ list.orderByColumn }','${ list.rowNum }');">수정</button></td>
							<td><a class="table-delete-btn" href="/admin/deletePagingConfig?id=${ list.id }">삭제</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<th colspan="6"><button type="button" onclick="registerClick();">등록</button></th>
				</tr>
			</table>
			
			<form id="registerForm" class="register-form" style="display:none;" action="/admin/insertPagingConfig" method="post">
				<input type="text" name="tableNm" class="register-input" placeholder="테이블명"><br>
				<input type="text" name="columns" class="register-input" placeholder="컬럼명"><br>
				<input type="text" name="orderByColumn" class="register-input" placeholder="컬럼정렬명"><br>
				<input type="text" name="rowNum" class="register-input" placeholder="행개수"><br>
				<button class="register-btn">등록</button><button class="register-close-btn" type="button" onclick="registerClose();">닫기</button>
			</form>
			<form id="updateForm" class="update-form" style="display:none;" action="/admin/updatePagingConfig" method="post">
				<input id="id" name="id" type="hidden">
				<input id="tableNm" type="text" name="tableNm" class="update-input" placeholder="테이블명"><br>
				<input id="columns" type="text" name="columns" class="update-input" placeholder="컬럼명"><br>
				<input id="orderByColumn" type="text" name="orderByColumn" class="update-input" placeholder="컬럼정렬명"><br>
				<input id="rowNum" type="text" name="rowNum" class="update-input" placeholder="행개수"><br>
				<button class="update-btn">수정</button><button class="update-close-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/paging_confg.js"></script>
</body>
</html>