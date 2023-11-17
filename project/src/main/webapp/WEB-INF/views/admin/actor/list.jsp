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
			
			<a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/people_id/1?query=">ID순</a> | <a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/people_nm/1?query=">이름 순</a> | <a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/like_num/1?query=">좋아요 순</a>
			<br><br><br>
			
			<table>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div class="auto-table-header">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="ID 또는 배우명을 입력해주세요" onkeydown="search(event,'${ autoPaging.listNm }', '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
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
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.peopleId }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.peopleNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.peopleNmEn }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.sex }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.movieId }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.movieNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.likeNum }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.profileUrl }">
					<tr>
						<td class="table-width-50">${ list.num }</td>
						<td class="table-width-50">${ list.peopleId }</td>
						<td class="table-width-300">${ list.peopleNm }</td>
						<td class="table-width-50">${ list.sex }</td>
						<td class="table-width-50">${ list.likeNum }</td>
						<td class="table-width-50"><button type="button" style="all:unset; cursor:pointer;" onclick="updateForm('<%=cnt%>')">수정</button></td>
						<td class="table-width-50">
							<!-- 파라미터 수정 -->
							<c:if test="${ not empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}?peopleId=${list.peopleId}" style="all:unset; cursor:pointer;">삭제</a>
							</c:if>
							<c:if test="${ empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan?peopleId=${list.peopleId}" style="all:unset; cursor:pointer;">삭제</a>
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
			
			<c:if test="${ not empty query }">
				<form id="insertForm" action="/insert/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}" method="post" style="display:none;">
			</c:if>
			<c:if test="${ empty query }">
				<form id="insertForm" action="/insert/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan" method="post" style="display:none;">
			</c:if>
				<input type="hidden" class="insert-input" name="likeNum" placeholder="" value="0"><br>
				<input type="number" class="insert-input" name="PeopleId" placeholder="ID를 입력해주세요(ex-12345678 필수)"><br>
				<input type="text" class="insert-input" name="peopleNm" placeholder="배우명을 입력해주세요(필수)"><br>
				<input type="text" class="insert-input" name="peopleNmEn" placeholder="배우영어명을 입력해주세요"><br>
				<input type="text" class="insert-input" name="sex" placeholder="성별을 입력해주세요"><br>
				<input type="text" class="insert-input" name="movieId" placeholder="출연 영화의 ID를 입력해주세요"><br>
				<input type="text" class="insert-input" name="movieNm" placeholder="출연 영화명을 입력해주세요"><br>
				<input type="text" class="insert-input" name="profileUrl" placeholder="프로필URL을 입력해주세요"><br>
				<button class="insert-btn">등록</button><button class="insert-btn" type="button" onclick="insertClose();">닫기</button>
			</form>
			
			
			<c:if test="${ not empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}" method="post" style="display:none;">
			</c:if>
			<c:if test="${ empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan" method="post" style="display:none;">
			</c:if>
				<input type="number" class="update-input" name="PeopleId" id="PeopleId" placeholder="ID를 입력해주세요(ex-12345678)" style="background-color:#eee;" readonly><br>
				<input type="text" class="update-input" name="peopleNm" id="peopleNm" placeholder="배우명을 입력해주세요(필수)"><br>
				<input type="text" class="update-input" name="peopleNmEn" id="peopleNmEn" placeholder="배우영어명을 입력해주세요"><br>
				<input type="text" class="update-input" name="sex" id="sex" placeholder="성별을 입력해주세요"><br>
				<input type="text" class="update-input" name="movieId" id="movieId" placeholder="출연 영화의 ID를 입력해주세요"><br>
				<input type="text" class="update-input" name="movieNm" id="movieNm" placeholder="출연 영화명을 입력해주세요"><br>
				<input type="number" class="update-input" name="likeNum" id="likeNum" placeholder="좋아요 수를 입력해주세요"><br>
				<input type="text" class="update-input" name="profileUrl" id="profileUrl" placeholder="프로필URL을 입력해주세요"><br>
				<button class="update-btn">수정</button><button class="update-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
			
			
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>