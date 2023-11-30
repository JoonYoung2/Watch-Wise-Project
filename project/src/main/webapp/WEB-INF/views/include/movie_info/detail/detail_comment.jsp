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
	<hr style="border:1px solid #ccc;">
	<c:if test="${empty sessionScope.userEmail }">
       	<div style="width:700px;">
       		
       	</div>
       </c:if>
       
	<c:if test="${not empty sessionScope.userEmail }">
    	<div  style="font-size:15px; margin:10px; margin-top:20px;">
			내가 작성한 코멘트
		</div>
		<c:choose>
			<c:when test="${ifWroteComment.reviewComment eq 'nan' || ifWroteComment.reviewComment eq null}">
				<div style="margin:auto; display:flex; width:700px; background-color: #F8F8F8; border: 2px solid #ccc; border-radius: 5px; padding: 10px;">
				
					<div style="margin:10px; width:75%; word-wrap:break-word;">
						아직 코멘트를 작성하지 않았어요. 코멘트를 작성해주세요.
					</div>
					
					<div align="center" style="width:25%; display: flex;  justify-content: center; align-items: center;">
						<c:choose>
							
							<c:when test="${sessionScope.isBlack eq 1 }">
								<button onclick="openBlackModal()">코멘트 작성하기</button>
							</c:when>
							
							<c:otherwise>
								<button onclick="openCommentWritingModal('${ movieInfo.movieNm }', '${ movieInfo.movieId }')">코멘트 작성하기</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div> 
			</c:when>
			<c:otherwise>
			  
				<div style="margin:auto; display:flex; width:700px; background-color: #F8F8F8; border-radius: 5px; padding: 10px;">
				
					<div style="margin:10px; width:85%; word-wrap:break-word;">
				        <span style="color:#424242; display:block;"><b>${ifWroteComment.reviewComment}</b></span>
				        <span style="font-size: 0.8em; color: #888; display:block;">${ifWroteComment.reviewCommentDate}</span>
					</div>
					
					<div align="center" style="width:15%; display: flex;  justify-content: center; align-items: center;">
						<c:choose>
							
							<c:when test="${sessionScope.isBlack eq 1 }">
								<img src="resources/img/thinPencil.png" onclick="openBlackModal()" style="cursor:pointer; width:20px; margin-right:15px;"/>
								|
								<img src="resources/img/bin.png" onclick="openBlackModal()" style="padding-left:8px; cursor:pointer; width:20px; margin-left:10px;"/>
							</c:when>
							<c:otherwise>
								<img src="resources/img/thinPencil.png" onclick="openModifyModal('${ movieInfo.movieNm }' , '${movieInfo.movieId }', '${ifWroteComment.reviewComment}');" style="cursor:pointer; width:20px; margin-right:15px;"/>
								|
								<img src="resources/img/bin.png" onclick="location.href='/deleteComment?id=${ifWroteComment.id }&movieId=${movieInfo.movieId }'" style="padding-left:8px; cursor:pointer; width:20px; margin-left:10px;"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>   
			</c:otherwise>
		</c:choose>
	</c:if>
	<br><br>
</body>
</html>