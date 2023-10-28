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
	<%@ include file="/WEB-INF/views/header.jsp"%>

	<div align="center" style="width: 100%;">
		<div align="center" style="width: 80%;">
			<c:if test="${ not empty searchList1 }">
				<c:forEach var="movieInfo" items="${ searchList1 }">

					<div style="display: flex;">
						<c:if test="${movieInfo.posterUrl ne 'nan' }">
							<div align="center" style="width: 50%;">
								<img style="width: 40%;" src="${ movieInfo.posterUrl }">
							</div>
						</c:if>
						<c:if test="${movieInfo.posterUrl eq 'nan' }">
							<div align="center" style="width: 50%;">
								<div
									style="width: 300px; height: 400px; border: 1px solid rgba(0, 0, 0, 0.1); display: flex; justify-content: center; align-items: center;">이미지가
									없습니다.</div>
							</div>
						</c:if>

						<div align="left" style="width: 50%;">
							<br>
							<br>
							<br> <span style="font-size: 40px; font-weight: bold;">${ movieInfo.movieNm }</span><br>
							<br>
							<c:if test="${ movieInfo.movieNmEn ne 'nan' }">
								<span style="font-size: 18px;">${ movieInfo.movieNmEn }</span>
								<br>
							</c:if>
							<c:if test="${ movieInfo.openDt ne 'nan' }">
								<span style="font-size: 18px;">${ movieInfo.openDt.substring(0, 4) }</span>
							</c:if>
							<c:if test="${ movieInfo.genreNm ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
							</c:if>
							<c:if test="${ movieInfo.nations ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
							</c:if>
							<br> <span style="font-size: 18px;">${ movieInfo.showTime }</span>
							<c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
							</c:if>
							<br>
							<c:if test="${ movieInfo.openDt ne 'nan' }">
								<span style="font-size: 18px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년
									${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span>
							</c:if>
						</div>
					</div>
					<c:if test="${ movieInfo.movieActorList.size() > 0 }">
						<c:forEach var="peopleInfo" items="${ movieInfo.movieActorList }">
							<c:if test="${ not empty peopleInfo.cast && peopleInfo.peopleId ne '0' }">
								<a href="peopleInfo?peopleId=${ peopleInfo.peopleId }" style="all: unset; cursor: pointer; display: flex; width: 25%;">
									<div>
										<img style="width: 80px; height: 80px;"
											src="/resources/img/bean_profile.png">
									</div>
									<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left: 20px;">
										<div style="padding-top: 10px;">
											<span style="font-size: 22px; font-weight: bold;">${ peopleInfo.peopleNm[j] }</span>
										</div>
										<div style="padding-top: 5px;">
											<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연
												| ${ peopleInfo.peopleCast[j] }</span>
										</div>
									</div>
								</a>
							</c:if>
							<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] eq '0' }">
								<div style="display: flex; width: 25%;">
									<div>
										<img style="width: 80px; height: 80px;" src="/resources/img/bean_profile.png">
									</div>
									<div
										style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left: 20px;">
										<div style="padding-top: 10px;">
											<span style="font-size: 22px; font-weight: bold;">${ peopleInfo.peopleNm[j] }</span>
										</div>
										<div style="padding-top: 5px;">
											<span style="font-size: 17px; color: rgba(0, 0, 0, 0.5);">출연
												| ${ peopleInfo.peopleCast[j] }</span>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:if>

			<c:if test="${ not empty searchList2 }">
				<c:forEach var="movieInfo" items="${ searchList2 }">
					<div style="display: flex;">
						<c:if test="${movieInfo.posterUrl ne 'nan' }">
							<div align="center" style="width: 50%;">
								<img style="width: 40%;" src="${ movieInfo.posterUrl }">
							</div>
						</c:if>
						<c:if test="${movieInfo.posterUrl eq 'nan' }">
							<div align="center" style="width: 50%;">
								<div
									style="width: 300px; height: 400px; border: 1px solid rgba(0, 0, 0, 0.1); display: flex; justify-content: center; align-items: center;">이미지가
									없습니다.</div>
							</div>
						</c:if>

						<div align="left" style="width: 50%;">
							<br>
							<br>
							<br> <span style="font-size: 40px; font-weight: bold;">${ movieInfo.movieNm }</span><br>
							<br>
							<c:if test="${ movieInfo.movieNmEn ne 'nan' }">
								<span style="font-size: 18px;">${ movieInfo.movieNmEn }</span>
								<br>
							</c:if>
							<c:if test="${ movieInfo.openDt ne 'nan' }">
								<span style="font-size: 18px;">${ movieInfo.openDt.substring(0, 4) }</span>
							</c:if>
							<c:if test="${ movieInfo.genreNm ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.genreNm.replaceAll(",", "/") }</span>
							</c:if>
							<c:if test="${ movieInfo.nations ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.nations.replaceAll(",", "/") }</span>
							</c:if>
							<br> <span style="font-size: 18px;">${ movieInfo.showTime }</span>
							<c:if test="${ movieInfo.watchGradeNm ne 'nan' }">
							・ <span style="font-size: 18px;">${ movieInfo.watchGradeNm.replaceAll(",", "/") }</span>
							</c:if>
							<br>
							<c:if test="${ movieInfo.openDt ne 'nan' }">
								<span style="font-size: 18px;">개봉일 : ${ movieInfo.openDt.substring(0, 4) }년
									${ movieInfo.openDt.substring(4, 6) }월 ${ movieInfo.openDt.substring(6, 8) }일</span>
							</c:if>
						</div>
					</div>
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
					<div style="display: flex;">
						<c:forEach var="movieInfoList"
							items="${ peopleList.movieInfoList }">
							<c:if test="${ movieInfoList.posterUrl ne 'nan' }">
								<div>
									<img style="width: 300px; height: 428.16px;"
										src="${ movieInfoList.posterUrl }">
								</div>
							</c:if>
							<c:if test="${ movieInfoList.posterUrl eq 'nan' }">
								<div
									style="display: flex; justify-content: center; align-items: center; width: 300px; height: 428.16px; border: 1px solid rgba(0, 0, 0, 0.1);">
									이미지가 없습니다.</div>
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
					<div style="display: flex;">
						<c:forEach var="movieInfoList"
							items="${ peopleList.movieInfoList }">
							<c:if test="${ movieInfoList.posterUrl ne 'nan' }">
								<div>
									<img style="width: 300px; height: 428.16px;"
										src="${ movieInfoList.posterUrl }">
								</div>
							</c:if>
							<c:if test="${ movieInfoList.posterUrl eq 'nan' }">
								<div
									style="display: flex; justify-content: center; align-items: center; width: 300px; height: 428.16px; border: 1px solid rgba(0, 0, 0, 0.1);">
									이미지가 없습니다.</div>
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