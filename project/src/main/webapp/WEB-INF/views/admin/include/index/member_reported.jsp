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
	<div class="page-width">
		<div class="member-reported">
			<div>
				<h4 class="reported-title">블랙리스트 대기 명단</h4>
			</div>
			<br>
			<div class="reported-size">
				<table class="reported-table">
					<tr class="reported-title-tr">
						<th style="width:50px;">No.</th><th style="width:300px;">유저 이메일 주소</th><th>총 신고 건수</th><th>블랙리스트 적용</th>
					</tr>
					<c:forEach var="show" items="${showDatas}">
						<tr onmouseover="this.style.backgroundColor='lightgrey'" onmouseout="this.style.backgroundColor='transparent'">
							<td>${show.rowNum }</td>
							<td>${show.authorEmail }</td>
							<td><a class="reported-count" onmouseover="this.style.color='red'" onmouseout="this.style.color='black'" href="/admin/showReportedComments?authorEmail=${show.authorEmail }&pageNum=1"><div>${show.reportedCommentAmount }</div></a></td>
							<td style="text-align:center;"> <a class="reported-btn" onmouseover="this.style.color='red'" onmouseout="this.style.color='black'" href="/admin/addToBlackList?author=${show.authorEmail }&currentPage=5000"><div>적용</div></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>