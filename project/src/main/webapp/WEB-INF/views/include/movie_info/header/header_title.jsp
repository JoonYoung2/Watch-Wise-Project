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
	<div style="display:flex;">
   		<div style=" height:60px;">
      		<div style="font-size:40px; font-weight:bold; margin-bottom:15px;">${ movieInfo.movieNm }</div>
      	</div>
      	<div id="likeDiv" style="display:flex; margin-left:30px; align-items:center;">
	    	<c:if test="${ not empty sessionScope.userEmail }">
	    		<c:if test="${ movieLikedCheck eq 0 }">
	            	<div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="likeAdd('${ movieInfo.movieId }');">
		            	<div style="display:flex; align-items:center;">
		           	  		<img style="width:40px; padding-left:7px;" src="/resources/img/like.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
		            	</div>
	            	</div>
	           </c:if>
	   		<c:if test="${ movieLikedCheck eq 1 }">
	       		<div style="display:flex; justify-content:center; align-items:center;" class="likeCancel" onclick="likeCancel('${ movieInfo.movieId }');">
	              		<div style="display:flex; align-items:center;">
	                 		<img style="width:40px; padding-left:7px;" src="/resources/img/likeColor.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
	                 	</div>
	             </div>
	           </c:if>
	        </c:if>
	        <c:if test="${ empty sessionScope.userEmail }">
	           <div style="display:flex; justify-content:center; align-items:center;" class="likeAdd" onclick="unregisterLickClick();">
	           		<div style="display:flex; align-items:center;">
	              		<img style="width:40px; padding-left:7px;" src="/resources/img/like.png"><span style="padding-left:7px; font-size:30px;">(${ movieInfo.likeNum })</span>
	              	</div>
	           </div>
	        </c:if>
    	</div>
	</div>
</body>
</html>