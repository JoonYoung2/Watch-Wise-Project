<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<<<<<<< HEAD:project/src/main/webapp/WEB-INF/views/home.jsp
	<div id = "imgId"></div>
	<div id = "weeklyId"></div>
	<div id = "weeklyId2"></div>
	<div id = "weeklyId3"></div>
	
	<button id="btn1" style="display:none;" type="button" onclick="dailyList();">dailyList!</button>
	<button id="btn2" style="display:none;" type="button" onclick="weeklyList();">주간List</button>
	<button id="btn3" style="display:none;" type="button" onclick="weeklyList2();">주말간List</button>
	<button id="btn4" style="display:none;" type="button" onclick="weeklyList3();">주중간List</button>
	<a href="test">API TEST</a>
	<form action="dailyMovieUpdate" method="post">
		<input type="hidden" id="movieNms" name="movieNms">
		<button>오늘의 영화 업데이트</button>
	</form>
	<form action="weeklyMovieUpdate" method="post">
		<input type="hidden" id="weeklyMovies" name="movieNms">
		<button>주간 영화 업데이트</button>
	</form>
	<form action="weekly2MovieUpdate" method="post">
		<input type="hidden" id="weekly2Movies" name="movieNms">
		<button>주말간 영화 업데이트</button>
	</form>
	<form action="weekly3MovieUpdate" method="post">
		<input type="hidden" id="weekly3Movies" name="movieNms">
		<button>주중간 영화 업데이트</button>
	</form>
	<a href="posterUrlUpdate">Poster Nan Research</a>
	<a href="getAllMovieNotNan">movie all list</a>
	<iframe width="640" height="360" src="https://www.kmdb.or.kr/trailer/trailerPlayPop?pFileNm=MK014164_P03.mp4" frameborder="0" allowfullscreen></iframe>
</body>
</html>