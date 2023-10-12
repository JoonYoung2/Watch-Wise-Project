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
	<div id = "imgId"></div>
	<div id = "weeklyId"></div>
	<div id = "weeklyId2"></div>
	<div id = "weeklyId3"></div>
	
	<button id="btn1" style="display:none;" type="button" onclick="dailyList();">dailyList!</button>
	<button id="btn2" style="display:none;" type="button" onclick="weeklyList();">주간List</button>
	<button id="btn3" style="display:none;" type="button" onclick="weeklyList2();">주말간List</button>
	<button id="btn4" style="display:none;" type="button" onclick="weeklyList3();">주중간List</button>
	<a href="test">API TEST</a>
<!-- 	<script src="/resources/js/example.js"></script> -->
</body>
</html>