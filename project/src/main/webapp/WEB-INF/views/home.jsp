<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	const gdgd = () => {
		console.log("gdgd");
		fetch("https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=06b0d392aa1b49e2fb5ce1afbfa89f5b&targetDt=20231009",
		        {
		            method: "get"
		        })
		        .then(res => res.json())
		        .then(result => {
		            console.log(result);
		        })
	} 
	
</script>
</head>
<body>
	<button type="button" onclick="gdgd();">눌러봥!</button>
</body>
</html>