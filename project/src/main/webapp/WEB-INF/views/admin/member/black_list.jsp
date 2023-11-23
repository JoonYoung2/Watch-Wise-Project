<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<%@ include file="../include/header.jsp" %>
<hr style="border:1px solid #ccc;">

<c:if test="${not empty msg}">
	<script>
		alert('${msg}');
	</script>
</c:if>

<div>
	<h4 style="text-align:center;">블랙리스트 명단</h4>
</div>
<br>
<div style="width:600px; height:500px;margin: 0 auto;">
	<table border="1" style="width:100%; text-align:center;">
		<tr style="background-color:lightgrey;">
			<th style="width:50px;">No.</th><th style="width:300px;">유저 이메일 주소</th><th>총 신고 건수</th><th>블랙리스트 해제</th>
		</tr>
		<c:forEach var="show" items="${showDatas}">
			<tr onmouseover="this.style.backgroundColor='lightgrey'" onmouseout="this.style.backgroundColor='transparent'">
				<td>${show.rowNum }</td>
				<td>${show.authorEmail }</td>
				<td><a href="/admin/showReportedComments?authorEmail=${show.authorEmail }&pageNum=${pageNum}">${show.reportedCommentAmount }</a></td>
				<td style="text-align:center;"> <a href="/admin/deleteFromBlackList?author=${show.authorEmail }&currentPage=${pageNum}"> <button style="width:60px; height:25px; cursor:pointer; font-size:12px;">해제</button></a>  </td>
			</tr>
		</c:forEach>
	</table>
</div>


<div style="width:300px; height:50px; text-align:center; margin-left:41%;">
<div class="container">	
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item previous">
                <a class="page-link" href="/admin/black_list_waiting?currentPage=${list.startPage-15}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach var="pNo" begin="${list.startPage}" end="${list.endPage}" step="1">
                <li class="page-item <c:if test='${param.currentPage eq pNo}'>active</c:if>">
                    <a class="page-link" href="/admin/black_list_waiting?currentPage=${pNo}">${pNo}</a>
                </li>
            </c:forEach>
            <li class="page-item next">
                <a class="page-link" href="/admin/black_list_waiting?currentPage=${list.startPage+15}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>
</div>
</body>
</html>