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
	<c:choose>
		<c:when test="${sessionScope.isBlack eq 1 }">
	    	<div style="width:30%; font-size:13px; margin-right:5px;">
				<img src="/resources/img/thinPencil.png" onclick="openBlackModal()" style="cursor:pointer;width:25px; margin-bottom:10px;"><br>
				코멘트
			</div>
		</c:when>
		<c:otherwise>
    		<c:choose>
	    		<c:when test="${ifWroteComment.reviewComment eq 'nan' || ifWroteComment.reviewComment eq null}"><!-- 코멘트 처음 작성하는 경우 -->
		    		<div style="width:30%; font-size:13px; margin-right:5px;">
		    			<img src="/resources/img/thinPencil.png" onclick="openCommentWritingModal('${ movieInfo.movieNm }', '${ movieInfo.movieId }')" style="cursor:pointer;width:25px; margin-bottom:10px;"><br>
		    			코멘트
		    		</div>
	    		</c:when>
	    		<c:otherwise>
		    		<div style="width:30%; font-size:13px; margin-right:5px;">
		    			<img src="/resources/img/thinPencil.png" onclick="openModifyModal('${ movieInfo.movieNm }' , '${movieInfo.movieId }', '${ifWroteComment.reviewComment}');" style=" cursor:pointer;width:25px; margin-bottom:10px;"><br>
		    			코멘트
		    		</div>
	    		</c:otherwise>
	    	</c:choose>
    	</c:otherwise>
   	</c:choose>
</body>
</html>