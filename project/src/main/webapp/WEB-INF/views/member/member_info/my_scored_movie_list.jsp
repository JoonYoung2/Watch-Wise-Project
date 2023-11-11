<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">

<div style="font-size:20px; margin-top:25px; margin-bottom:25px;">
	평가한 영화 목록 여기 부분 제외하고 한번 테이블로 해보자!! 
</div>

<div style="background-color:grey;">
	<c:forEach var="entry" items="${lists}">
		<div style="margin-left:20px; margin-right:20px;">
			<h2 style="font-size:19px; margin-top:8px; margin-bottom:8px; float:left;">5.0 평가함</h2>
			<span style="font-size:15px; color: rgb(160, 160, 160); margin: 12px 0px 12px 6px;">10</span>
			<div style="float:right;">
				<div style="margin:12px 0px;">
	<!-- 더보기로 링크걸기 -->	<a href="/viewMoviesIn'${entry.key}'" style="color:rgb(255, 47, 110); text-decoration: none;">더보기</a>
				</div>
			</div>
		</div>
		<c:forEach var="item" items="${entry.value}"> 
			<div style="position:relative;">
				<div style="overflow: hidden;">
					<div>
						<div>
							<div style="margin:0px 20px;">
								<ul style="white-space-collapse:collapse; text-wrap:nowrap;">
									<li style="display: inline-block; vertical-align: top; padding:0px 5px; margin:0px 0px 24px;">
										<a href="/movieInfo?movieId='${item.movieReviewDto.movieId}'"  style="width:100%; text-decoration:none; display:inline-block;">
											<div style="position:relative; width:100%; padding-bottom: 145.37%;">
												<div style="position:absolute; width:100%; height:100%; border:1px solid rgb(234, 233, 232); border-radius:5px;">
													
													<img style="width:100%; height:100%; object-fit:cover;"src="${item.movieInfoDto.posterUrl}">
												
												</div>
											</div>
											<div style="margin:5px 10px 0px 0px;">
												<div style="font-size:16px; font-weight:500; overflow:hidden; text-overflow:ellipsis;">
													${item.movieInfoDto.movieNm}
												</div>
											</div>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:forEach>
</div>



	
<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>