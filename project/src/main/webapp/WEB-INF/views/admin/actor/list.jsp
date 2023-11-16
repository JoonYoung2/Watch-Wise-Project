<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../include/header.jsp" %>
	<div align="center" style="width:100%">
		<div align="center" style="width:80%">
			<table>
				<tr>
					<td colspan="${ titleList.size() + 2 }">
						<div style="display:flex; justify-content:space-between;">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="ID 또는 배우명을 입력해주세요" onkeydown="search(event,'${ autoPaging.listNm }', '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
							<div>Row Num : <input style="all:unset; width:100px; text-align:center; border:1px solid rgba(0, 0, 0, 0.1)" id="rowNum" type="number" value="${ pagingConfig.rowNum }" onchange="rowNumUpdate('${ pagingConfig.tableNm }')"></div>
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
					<tr>
						<td>${ list.num }</td>
						<td>${ list.peopleId }</td>
						<td>${ list.peopleNm }</td>
						<td>${ list.sex }</td>
						<td>${ list.likeNum }</td>
						<td>수정</td>
						<td>삭제</td>
					</tr>
				</c:forEach>
				<!-- 각 href수정 -->
				<tr>
					<td colspan="${ titleList.size() + 2 }">
						<div align="center" style="width:100%;">
							<c:if test="${ pagingConfig.pageNum > 5 }">
								<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/1"> 1 </a></span>...
							</c:if>
							<c:forEach var="i" begin="${ paging.start }" end="${ paging.end }" step="1">
								<c:if test="${ pagingConfig.pageNum ne i }">
									<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ i }"> ${ i } </a></span>
								</c:if>
								<c:if test="${ pagingConfig.pageNum eq i }">
									<span style="padding:0px 10px;"> ${ i } </span>
								</c:if>
							</c:forEach>
							<c:if test="${ paging.last ne 0 }">
								...<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ paging.last }"> ${ paging.last } </a></span>
							</c:if>
							<a style="all:unset; cursor:pointer;" onclick="insertForm();">등록</a>
						</div>
					</td>
				</tr>
				<!-- 각 href수정 -->
			</table>
			<form id="insertForm" action="" method="post" style="display:none;">
				<input type="text" name="" placeholder="" style="width:300px;"><br>
				<button style="width:154px;">등록</button><button style="width:154px;" type="reset">취소</button>
			</form>
			<form id="updateForm" action="" method="post" style="display:none;">
				<input type="text" name="" id="" placeholder="" style="width:300px;"><br>
				<button style="width:154px;">수정</button><button style="width:154px;" type="reset">취소</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>