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
	<div align="center">
    	<span>
    		<c:if test="${ ifWroteComment.reviewScore eq 0.0 || empty ifWroteComment.reviewScore }">
    			<b> <span id="allGradeAvg" style="color:red; font-size:25px;">★ ${ gradeNum.substring(0, 3) }</span></b><span> / 5.0</span> <div align="center">평균 별점 <span id="gradeCount">(${ gradeInfo.gradeCnt }명)</span></div>
    			<input type="hidden" id="gradeCheck" value="0">
    		</c:if>
    		<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
    			<b> <span id="allGradeAvg" style="color:red; font-size:25px;">★ ${ gradeNum.substring(0, 3) }</span></b> / 5.0 <br> <div align="center">평균 별점 <span id="gradeCount">(${ gradeInfo.gradeCnt }명)</span></div>
    			<input type="hidden" id="gradeCheck" value="1">
    		</c:if> 
    	</span>
   	</div>
   	<br>
</body>
</html>