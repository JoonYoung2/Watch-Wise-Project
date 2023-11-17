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
			<a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/movie_id/1?query=">ID 순</a> | <a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/movie_nm/1?query=">영화명 순</a> | <a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/open_dt/1?query=">개봉일 순</a> | <a href="/admin/${ autoPaging.listNm }/${ autoPaging.tableNm }/like_num/1?query=">좋아요 순</a>
			<br><br><br>
			
			<table>
				<tr>
					<td colspan="${ autoPaging.titleList.size() + 2 }">
						<div class="auto-table-header">
							<div><input id="query" class="header-search" value="${ query }"  type="text" placeholder="ID 또는 영화명을 입력해주세요" onkeydown="search(event,'${ autoPaging.listNm }', '${ autoPaging.tableNm }', '${ autoPaging.orderByColumn }')"></div>
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
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.movieId }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.movieNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.movieNmEn }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.prdtYear }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.openDt }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.typeNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.nations }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.genreNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.posterUrl }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.showTime }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.actors }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.cast }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.watchGradeNm }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.docid }">
					<input type="hidden" class="content-values<%= cnt %>" value="${ list.likeNum }">
					<tr>
						<td class="table-width-50">${ list.num }</td>
						<td class="table-width-100">${ list.movieId }</td>
						<td class="table-width-500">${ list.movieNm }</td>
						<td class="table-width-100">${ list.openDt }</td>
						<td class="table-width-50">${ list.likeNum }</td>
						<td class="table-width-50"><button type="button" style="all:unset; cursor:pointer;" onclick="updateForm('<%=cnt%>')">수정</button></td>
						<td class="table-width-50">
							<c:if test="${ not empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}?movieId=${list.movieId}" style="all:unset; cursor:pointer;">삭제</a>
							</c:if>
							<c:if test="${ empty query }">
								<a href="/delete/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan?movieId=${list.movieId}" style="all:unset; cursor:pointer;">삭제</a>
							</c:if>
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
				<input type="text" class="insert-input" name="movieId" placeholder="영화ID (ex-12345678)"><br>
				<input type="text" class="insert-input" name="movieNm" placeholder="영화제목"><br>
				<input type="text" class="insert-input" name="movieNmEn" placeholder="영화영어제목"><br>
				<input type="number" class="insert-input" name="prdtYear" placeholder="제작년도(ex-1900)"><br>
				<input type="number" class="insert-input" name="openDt" placeholder="개봉일(ex-19001010)"><br>
				<input type="text" class="insert-input" name="typeNm" placeholder="종류명"><br>
				<input type="text" class="insert-input" name="nations" placeholder="제작국가"><br>
				<input type="text" class="insert-input" name="genreNm" placeholder="장르"><br>
				<input type="text" class="insert-input" name="posterUrl" placeholder="포스터URL"><br>
				<input type="number" class="insert-input" name="showTime" placeholder="상영시간" value="0"><br>
				<input type="text" class="insert-input" name="actors" placeholder="배우명(ex-송중기,송혜교)"><br>
				<input type="text" class="insert-input" name="cast" placeholder="역할명(ex-경찰1,경찰2)"><br>
				<input type="text" class="insert-input" name="watchGradeNm" placeholder="제한연령"><br>
				<input type="hidden" name="docid" value="nan">
				<input type="hidden" name="likeNum" value="0">
				<button class="insert-btn">등록</button><button class="insert-btn" type="button" onclick="insertClose();">닫기</button>
			</form>
			<c:if test="${ not empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/${query}" method="post" style="display:none;">
			</c:if>
			<c:if test="${ empty query }">
				<form id="updateForm" action="/update/${ autoPaging.listNm }/${ autoPaging.tableNm }/${ autoPaging.orderByColumn }/${ autoPaging.pageNum }/nan" method="post" style="display:none;">
			</c:if>
				<input type="text" class="update-input" name="movieId" id="movieId" placeholder="영화ID (ex-12345678)" style="background-color:#eee;" readonly><br>
				<input type="text" class="update-input" name="movieNm" id="movieNm" placeholder="영화제목"><br>
				<input type="text" class="update-input" name="movieNmEn" id="movieNmEn" placeholder="영화영어제목"><br>
				<input type="text" class="update-input" name="prdtYear" id="prdtYear" placeholder="제작년도(ex-1900)"><br>
				<input type="text" class="update-input" name="openDt" id="openDt" placeholder="개봉일(ex-19001010)"><br>
				<input type="text" class="update-input" name="typeNm" id="typeNm" placeholder="종류명"><br>
				<input type="text" class="update-input" name="nations" id="nations" placeholder="제작국가"><br>
				<input type="text" class="update-input" name="genreNm" id="genreNm" placeholder="장르"><br>
				<input type="text" class="update-input" name="posterUrl" id="posterUrl" placeholder="포스터URL"><br>
				<input type="number" class="update-input" name="showTime" id="showTime" placeholder="상영시간"><br>
				<input type="text" class="update-input" name="actors" id="actors" placeholder="배우명(ex-송중기,송혜교)"><br>
				<input type="text" class="update-input" name="cast" id="cast" placeholder="역할명(ex-경찰1,경찰2)"><br>
				<input type="text" class="update-input" name="watchGradeNm" id="watchGradeNm" placeholder="제한연령"><br>
				<input type="text" class="update-input" name="docid" id="docid" placeholder="docid"><br>
				<input type="text" class="update-input" name="likeNum" id="likeNum" placeholder="좋아요 수"><br>
				<button class="update-btn">수정</button><button class="update-btn" type="button" onclick="updateClose();">닫기</button>
			</form>
		</div>
	</div>
	<script src="/resources/js/admin/table_common.js"></script>
</body>
</html>