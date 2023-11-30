<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="display:flex; margin-left:10px;">
       	<br><br>
       	<div style="width:60%; text-align:left; margin-top:10px;">
	        <c:if test="${ movieInfo.movieNmEn ne 'nan' }">
	           <span style="font-size:20px;">${ movieInfo.movieNmEn }</span><br>
	        </c:if>
	        <c:if test="${ movieInfo.openDt ne 'nan' }">
	           <span style="font-size:20px;">${ movieInfo.openDt.substring(0, 4) }</span>
	        </c:if>
	        <c:if test="${ movieInfo.genreNm ne 'nan' }">
	           	・ <span style="font-size:20px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
	        </c:if>
	        <c:if test="${ movieInfo.nations ne 'nan' }">
	           	・ <span style="font-size:20px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
	        </c:if>
	        <br>
	        <span style="font-size:20px;">${ movieInfo.showTime }</span>
	        <c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
	           	・ <span style="font-size:20px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
	        </c:if>
	        <br>
	        <c:if test="${ movieInfo.openDt ne 'nan' }">
	           <span style="font-size:20px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년 ${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span><br><br><br>
	        </c:if>
		</div>
	</div>
</body>
</html>