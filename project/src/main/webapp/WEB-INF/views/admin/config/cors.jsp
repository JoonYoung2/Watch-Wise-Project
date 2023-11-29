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
					<th style="width:50px;">ID</th>
					<th style="width:300px;">Allowed Origins</th>
					<th style="width:50px;">수정</th>
					<th style="width:50px;">삭제</th>
				</tr>
				<c:if test="${ empty config }">
					<tr>
						<th colspan="4">등록된 정보가 없습니다.</th>
					</tr>
				</c:if>
				<c:if test="${ not empty config }">
					<c:forEach var="list" items="${ config }">
						<tr class="table-tr">
							<td>${ list.id }</td>
							<td>${ list.allowedOrigins }</td>
							<td><button class="table-update-btn" type="button" onclick="updateClick('${ list.id }','${ list.allowedOrigins }');">수정</button></td>
							<td><a class="table-delete-btn" href="/admin/deleteCorsConfig?id=${ list.id }">삭제</a></td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<th colspan="4"><button type="button" onclick="registerClick();">등록</button></th>
				</tr>
			</table>
			
			<form id="registerForm" class="register-form" style="display:none;" action="/admin/insertCorsConfig" method="post">
				<input type="text" name="allowedOrigins" class="register-input" placeholder="origin"><br>
				<button class="register-btn">등록</button><button class="register-close-btn" type="button" onclick="registerClose();">닫기</button>
			</form>
			<form id="updateForm" class="update-form" style="display:none;" action="/admin/updateCorsConfig" method="post">
				<input id="id" name="id" type="hidden">
				<input id="allowed-origins" type="text" name="allowedOrigins" class="update-input" placeholder="origin"><br>
				<button class="update-btn">수정</button><button class="update-close-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/api_ip_confg.js"></script>
</body>
</html>