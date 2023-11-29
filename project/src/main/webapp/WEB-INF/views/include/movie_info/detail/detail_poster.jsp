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
	<c:if test="${movieInfo.posterUrl[0] ne 'nan' }">
       	<div align="right">
           	<img style="width:350px;" src="${ movieInfo.posterUrl[0] }">
       	</div>
   	</c:if>
    <c:if test="${movieInfo.posterUrl[0] eq 'nan' }">
    	<div align="right" style="margin-right:60px;">
        	<div style="width:350px; height:497.88px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
    	</div>
    </c:if>
</body>
</html>