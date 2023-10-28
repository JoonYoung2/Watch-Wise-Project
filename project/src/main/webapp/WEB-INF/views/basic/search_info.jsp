<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">

		<c:if test="${ not empty searchList1 }">
			<c:forEach var="movieInfo" items="${ searchList1 }">
				${ movieInfo.movieNm }<br>
				<c:if test="${ movieInfo.movieActorList.size() > 0 }">
					<c:forEach var="actors" items="${ movieInfo.movieActorList }">
						${ actors.peopleNm },
					</c:forEach>
				</c:if>
			</c:forEach>
		</c:if>
		
		<c:if test="${ not empty searchList2 }">
			<c:forEach var="movieInfo" items="${ searchList2 }">
				${ movieInfo.movieNm }<br>
				<c:if test="${ movieInfo.movieActorList.size() > 0 }">
					<c:forEach var="actors" items="${ movieInfo.movieActorList }">
						${ actors.peopleNm },
					</c:forEach>
					<br>
				</c:if>
			</c:forEach>
		</c:if>
		
		<c:if test="${ not empty searchList3 }">
			<c:forEach var="peopleList" items="${ searchList3 }">
				${ peopleList.peopleId }<br>
				${ peopleList.peopleNm }<br>
				${ peopleList.peopleNmEn }<br>
				${ peopleList.sex }<br>
				${ peopleList.likeNum }<br>
				<div style="display:flex;">
					<c:forEach var="movieInfoList" items="${ peopleList.movieInfoList }">
						<c:if test="${ movieInfoList.posterUrl ne 'nan' }">
							<div>
								<img style="width:300px; height:428.16px;" src="${ movieInfoList.posterUrl }">
							</div>
						</c:if>
						<c:if test="${ movieInfoList.posterUrl eq 'nan' }">
							<div style="display:flex; justify-content:center; align-items:center; width:300px; height:428.16px; border: 1px solid rgba(0, 0, 0, 0.1);">
								이미지가 없습니다.
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</c:if>
		
		<c:if test="${ not empty searchList4 }">
			<c:forEach var="peopleList" items="${ searchList4 }">
				${ peopleList.peopleId }<br>
				${ peopleList.peopleNm }<br>
				${ peopleList.peopleNmEn }<br>
				${ peopleList.sex }<br>
				${ peopleList.likeNum }<br>
				<div style="display:flex;">
					<c:forEach var="movieInfoList" items="${ peopleList.movieInfoList }">
						<c:if test="${ movieInfoList.posterUrl ne 'nan' }">
							<div>
								<img style="width:300px; height:428.16px;" src="${ movieInfoList.posterUrl }">
							</div>
						</c:if>
						<c:if test="${ movieInfoList.posterUrl eq 'nan' }">
							<div style="display:flex; justify-content:center; align-items:center; width:300px; height:428.16px; border: 1px solid rgba(0, 0, 0, 0.1);">
								이미지가 없습니다.
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_info.js"></script>
</body>
</html>