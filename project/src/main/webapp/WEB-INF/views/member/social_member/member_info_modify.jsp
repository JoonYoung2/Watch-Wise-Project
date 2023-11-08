<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/common.js"></script>
<script src="/resources/js/search_common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">

<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>

<div  style="background-color:rgb(244, 244, 247); height:1000px;">
    <div class="user-info-modify-card-frame" style="position: absolute; top: 55%; left: 50%; transform: translate(-50%, -50%); background-color: white; width: 20%; height: 20%; padding: 20px;">
		<div class="form-tag-for-modify-info" style="position: absolute; top: 50%; left:50%; transform:translate(-50%, -50%); width:85%; height:85%;">
			
			<form action="/socialModifyInfoDo" method="post" style="position:absolute; top: 55%; left:50%; transform:translate(-50%, -50%); width:80%; height:80%;">
				<div align="center" style="margin-bottom:40px;">
					<table>
						<tr>
							<th style="width:100px; height:50px;">이메일</th>
							<td>
							<div style="display:flex;">
								<div>
									<input style="height:20px; font-weight:bold; border:none;" type="text" name="userEmail" value="${dto.userEmail}" readonly> 
								</div>
								<!-- 
								<div style="font-size:15px;">
									이메일 수정은 불가합니다.
								</div>
								 -->
							</div>
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input style="height:20px;" type="text" name="userName" value="${dto.userName }"></td>
						</tr>
					</table>
				</div>
				<div align="center">
					<input type="submit" value="수정하기">
				</div>	
			</form>
			
		</div>
	</div>
</div>
</body>
</html>