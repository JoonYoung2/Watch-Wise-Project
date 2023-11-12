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
							<div><input type="text" placeholder="ID 또는 영화명을 입력해주세요"></div>
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
						<td>${ list.movieId }</td>
						<td>${ list.movieNm }</td>
						<td>${ list.openDt }</td>
						<td>${ list.likeNum }</td>
						<td>수정</td>
						<td>삭제</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="${ titleList.size() + 2 }">
					<div align="center" style="width:100%;">
						<c:if test="${ pagingConfig.pageNum > 5 }">
							<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/movie_list/open_dt/1"> 1 </a></span>...
						</c:if>
						<c:forEach var="i" begin="${ paging.start }" end="${ paging.end }" step="1">
							<c:if test="${ pagingConfig.pageNum ne i }">
								<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/movie_list/open_dt/${ i }"> ${ i } </a></span>
							</c:if>
							<c:if test="${ pagingConfig.pageNum eq i }">
								<span style="padding:0px 10px;"> ${ i } </span>
							</c:if>
						</c:forEach>
						<c:if test="${ paging.last ne 0 }">
							...<span style="padding:0px 10px;"><a style="all:unset; cursor:pointer; color:blue;" href="/admin/movie_list/open_dt/${ paging.last }"> ${ paging.last } </a></span>
						</c:if>
						<a style="all:unset; cursor:pointer;" onclick="insertForm();">등록</a>
					</div>
					</td>
				</tr>
			</table>
			<form id="insertForm" action="insertMovieInfo" method="post" style="display:none;">
				<input type="text" name="movieId" placeholder="영화ID (ex-12345678)" style="width:300px;"><br>
				<input type="text" name="movieNm" placeholder="영화제목" style="width:300px;"><br>
				<input type="text" name="movieNmEn" placeholder="영화영어제목" style="width:300px;"><br>
				<input type="text" name="prdtYear" placeholder="제작년도(ex-1900)" style="width:300px;"><br>
				<input type="text" name="openDt" placeholder="개봉일(ex-19001010)" style="width:300px;"><br>
				<input type="text" name="typeNm" placeholder="종류명" style="width:300px;"><br>
				<input type="text" name="nations" placeholder="제작국가" style="width:300px;"><br>
				<input type="text" name="genreNm" placeholder="장르" style="width:300px;"><br>
				<input type="text" name="posterUrl" placeholder="포스터URL" style="width:300px;"><br>
				<input type="text" name="showTime" placeholder="상영시간" style="width:300px;"><br>
				<input type="text" name="actors" placeholder="배우명(ex-송중기,송혜교)" style="width:300px;"><br>
				<input type="text" name="acst" placeholder="역할명(ex-경찰1,경찰2)" style="width:300px;"><br>
				<input type="text" name="watchGradeNm" placeholder="제한연령" style="width:300px;"><br>
				<input type="hidden" name="docid" value="nan">
				<input type="hidden" name="likeNum" value="0">
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