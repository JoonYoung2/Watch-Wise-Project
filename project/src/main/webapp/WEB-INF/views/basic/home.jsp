<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
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
<c:set var="daily" value="${ movieInfoMap['daily'] }"/>
<c:set var="weekly0" value="${ movieInfoMap['weekly0'] }"/>
<c:set var="weekly1" value="${ movieInfoMap['weekly1'] }"/>
<c:set var="weekly2" value="${ movieInfoMap['weekly2'] }"/>
<c:set var="upcoming" value="${ movieInfoMap['upcoming'] }"/>
<c:set var="imgWidth" value="300"/>
<c:set var="imgHeight" value="428.16"/>
<% 
	int cnt = 0; 
	int rankCnt = 1;
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
	int todayDate = Integer.parseInt(dtFormat.format(cal.getTime()));
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">일간 Best 10</span></div>
		<div style="display:flex; justify-content:space-between">
			<button id="dailyLeftBtn" type="button" style="all:unset; cursor:pointer; font-size:30px; width:60px; height:60px; border:1px solid rgba(0, 0, 0, 0.2); border-radius:50px; position:absolute; top:270px; left:150px; display:none;" onclick="dailyLeftBtn();">&lt</button>
			<c:forEach var="list" items="${ daily }">
			<% if(cnt < 5){ %>
			<a class="dailyTop" href="#" style="all:unset; cursor:pointer; display:;">
				<div>
					<div>Rank : <%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a class="dailyTop" href="#" style="all:unset; cursor:pointer; display:none;">
				<div>
					<div>Rank : <%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<button id="dailyRightBtn" type="button" style="all:unset; cursor:pointer; background-color:white; width:60px; height:60px; border:1px solid rgba(0, 0, 0, 0.2); border-radius:50px; font-size:30px; position:absolute; top:270px; right:160px; display:;" onclick="dailyRightBtn();">></button>
		</div>
	</div>
</div>

<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주간 Best 10</span></div>
		<div style="display:flex;">
		
			<button id="weeklyLeftBtn0" style="all:unset; cursor:pointer; font-size:30px; display:none;" onclick="weeklyLeftBtn0();">◀</button>
			
			<c:forEach var="list" items="${ weekly0 }">
			<% if(cnt < 5){ %>
				<div class="weeklyTop0" style="display:;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
			}else{
			%>
				<div class="weeklyTop0" style="display:none;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
				}
			cnt++;
			rankCnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn0" type="button" style="all:unset; cursor:pointer; font-size:30px; display:;" onclick="weeklyRightBtn0();">▶</button>
		</div>
	</div>
</div>

<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주말 Best 10</span></div>
		<div style="display:flex;">
		<button id="weeklyLeftBtn1" style="all:unset; cursor:pointer; font-size:30px; display:none;" onclick="weeklyLeftBtn1();">◀</button>
			<c:forEach var="list" items="${ weekly1 }">
			<% if(cnt < 5){ %>
				<div class="weeklyTop1" style="display:;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
			}else{
			%>
				<div class="weeklyTop1" style="display:none;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
				}
			cnt++;
			rankCnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn1" type="button" style="all:unset; cursor:pointer; font-size:30px; display:;" onclick="weeklyRightBtn1();">▶</button>
		</div>
	</div>
</div>
<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주중 Best 10</span></div>
		<div style="display:flex;">
		<button id="weeklyLeftBtn2" style="all:unset; cursor:pointer; font-size:30px; display:none;" onclick="weeklyLeftBtn2();">◀</button>
			<c:forEach var="list" items="${ weekly2 }">
			<% if(cnt < 5){ %>
				<div class="weeklyTop2" style="display:;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
			}else{
			%>
				<div class="weeklyTop2" style="display:none;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						평균 ★ 0.0
					</div>
				</div>
			<%
				}
			cnt++;
			rankCnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn2" type="button" style="all:unset; cursor:pointer; font-size:30px; display:;" onclick="weeklyRightBtn2();">▶</button>
		</div>
	</div>
</div>

<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Upcoming Movies</span></div>
		<div style="display:flex;">
		
			<button id="upcomingLeftBtn" style="all:unset; cursor:pointer; font-size:30px; display:none;" onclick="upcomingLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ upcoming }">
			<% 
				if(cnt < 5){ 
			%>
				<div class="upcoming" style="display:;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						D-${ list.getDDay() }
					</div>
				</div>
			<%
			}else{
			%>
				<div class="upcoming" style="display:none;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						D-${ list.getDDay() }
					</div>
				</div>
			<%
				}
			cnt++;
			rankCnt++;
			%>
				
			</c:forEach>
			<button id="upcomingRightBtn" type="button" style="all:unset; cursor:pointer; font-size:30px; display:;" onclick="upcomingRightBtn();">▶</button>
		</div>
	</div>
</div>

<script src="/resources/js/home.js"></script>
</body>
</html>