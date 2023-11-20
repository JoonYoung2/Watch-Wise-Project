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
	
	<div>
		<h3 style="text-align:center;">블랙리스트 대기 명단</h3>
	</div>
	<div>
<!-- 		${contentList } -->
	</div>

<div class="col-sm-12 col-md-7">
	<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
		<ul class="pagination">
			<!-- Previous 시작 -->
			<li class="paginate_button page-item previous <c:if test='${list.startPage<6 }'>disabled</c:if>" id="dataTable_previous"><a href="/admin/black_list_waiting?currentPage=${list.startPage-5 }" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">Previous</a></li>
			<!-- Previous 끝 -->
			<!-- Page번호 시작 -->
			<c:forEach var="pNo" begin="${list.startPage }" end="${list.endPage }" step="1">
				<li class="paginate_button page-item  <c:if test='${param.currentPage eq pNo }'>active</c:if>"><a href="/admin/black_list_waiting?currentPage=${pNo }" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">${pNo }</a></li>
			</c:forEach>
			<!-- Page번호 끝 -->
			<!-- Next 시작 -->
			<li class="paginate_button page-item next <c:if test='${list.endPage>=list.totalPages }'>disabled</c:if>" id="dataTable_next"><a href="/admin/black_list_waiting?currentPage=${list.startPage+5 }" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">Next</a></li>
			<!-- Next 끝 -->
		</ul>
	</div>
</div>
</body>
</html>