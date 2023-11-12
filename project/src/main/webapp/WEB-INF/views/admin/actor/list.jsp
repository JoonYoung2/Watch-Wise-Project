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
							<div><input type="text" placeholder="ID 또는 배우명을 입력해주세요"></div>
							<div>Row Num : <input style="all:unset; width:100px; text-align:center; border:1px solid rgba(0, 0, 0, 0.1)" id="rowNum" type="number" value="${ pagingConfig.rowNum }" onchange="rowNumUpdate('${ pagingConfig.tableNm }')"></div>
						</div>
					</td>
				</tr>
				<tr>
					<c:forEach var="list" items="${ titleList }">
						<th>${ list.titleNm }</th>
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
							<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/actor_list/people_id/1"> 1 </a></span>...
						</c:if>
						<c:forEach var="i" begin="${ paging.start }" end="${ paging.end }" step="1">
							<c:if test="${ pagingConfig.pageNum ne i }">
								<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/actor_list/people_id/${ i }"> ${ i } </a></span>
							</c:if>
							<c:if test="${ pagingConfig.pageNum eq i }">
								<span style="padding:0px 10px;"> ${ i } </span>
							</c:if>
						</c:forEach>
						<c:if test="${ paging.last ne 0 }">
							...<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/actor_list/people_id/${ paging.last }"> ${ paging.last } </a></span>
						</c:if>
					</div>
					</td>
				</tr>
				<!-- 각 href수정 -->
			</table>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>