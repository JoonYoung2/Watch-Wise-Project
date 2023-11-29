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
	<div class="rating-page">
   		<div>
			<c:if test="${not empty sessionScope.userEmail }">
				<div align="center" id="review">
					<c:if test="${ ifWroteComment.reviewScore ne 0.0 && not empty ifWroteComment.reviewScore }">
						<select id="select-box" class="rating-selected-box" name="rating" onchange="rating(this.value,'${movieInfo.movieId }');">
					        <option value="0.0" ${ifWroteComment.reviewScore == 0.0 ? 'selected' : ''}>취소하기</option>
					        <option value="0.5" ${ifWroteComment.reviewScore == 0.5 ? 'selected' : ''}>★ 0.5</option>
					        <option value="1.0" ${ifWroteComment.reviewScore == 1.0 ? 'selected' : ''}>★ 1.0</option>
					        <option value="1.5" ${ifWroteComment.reviewScore == 1.5 ? 'selected' : ''}>★ 1.5</option>
					        <option value="2.0" ${ifWroteComment.reviewScore == 2.0 ? 'selected' : ''}>★ 2.0</option>
					        <option value="2.5" ${ifWroteComment.reviewScore == 2.5 ? 'selected' : ''}>★ 2.5</option>
					        <option value="3.0" ${ifWroteComment.reviewScore == 3.0 ? 'selected' : ''}>★ 3.0</option>
					        <option value="3.5" ${ifWroteComment.reviewScore == 3.5 ? 'selected' : ''}>★ 3.5</option>
					        <option value="4.0" ${ifWroteComment.reviewScore == 4.0 ? 'selected' : ''}>★ 4.0</option>
					        <option value="4.5" ${ifWroteComment.reviewScore == 4.5 ? 'selected' : ''}>★ 4.5</option>
					        <option value="5.0" ${ifWroteComment.reviewScore == 5.0 ? 'selected' : ''}>★ 5.0</option>
						</select>
						<div align="center" id="msg" style="font-size:13px;"></div>
					</c:if>
					<c:if test="${ ifWroteComment.reviewScore eq 0.0 || empty ifWroteComment.reviewScore }">
						<select id="select-box" class="rating-select-box" name="rating" onchange="rating(this.value,'${movieInfo.movieId }');">
					        <option value="0.0" ${ifWroteComment.reviewScore == 0.0 ? 'selected' : ''}>★ 0.0</option>
					        <option value="0.5" ${ifWroteComment.reviewScore == 0.5 ? 'selected' : ''}>★ 0.5</option>
					        <option value="1.0" ${ifWroteComment.reviewScore == 1.0 ? 'selected' : ''}>★ 1.0</option>
					        <option value="1.5" ${ifWroteComment.reviewScore == 1.5 ? 'selected' : ''}>★ 1.5</option>
					        <option value="2.0" ${ifWroteComment.reviewScore == 2.0 ? 'selected' : ''}>★ 2.0</option>
					        <option value="2.5" ${ifWroteComment.reviewScore == 2.5 ? 'selected' : ''}>★ 2.5</option>
					        <option value="3.0" ${ifWroteComment.reviewScore == 3.0 ? 'selected' : ''}>★ 3.0</option>
					        <option value="3.5" ${ifWroteComment.reviewScore == 3.5 ? 'selected' : ''}>★ 3.5</option>
					        <option value="4.0" ${ifWroteComment.reviewScore == 4.0 ? 'selected' : ''}>★ 4.0</option>
					        <option value="4.5" ${ifWroteComment.reviewScore == 4.5 ? 'selected' : ''}>★ 4.5</option>
					        <option value="5.0" ${ifWroteComment.reviewScore == 5.0 ? 'selected' : ''}>★ 5.0</option>
						</select>
						<div align="center" id="msg" style="font-size:13px;">평가해주세요</div>
					</c:if>
				</div>
			</c:if>
		</div>							    			
	</div>
</body>
</html>