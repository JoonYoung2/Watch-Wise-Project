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
<link rel="stylesheet" href="/resources/css/home.css">
<title>Insert title here</title>
	<c:if test="${signOutAlert == true }"><!--redirect 하면 알림 안뜸.-->
		<script type="text/javascript">
			alert('로그아웃 되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${unregisterAlert == true }">
		<script type="text/javascript">
			alert('회원 탈퇴가 완료되었습니다.');
		</script>	
	</c:if>
	
	<c:if test="${not empty msg }">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>

<c:set var="daily" value="${ movieInfoMap['daily'] }"/>
<c:set var="weekly0" value="${ movieInfoMap['weekly0'] }"/>
<c:set var="weekly1" value="${ movieInfoMap['weekly1'] }"/>
<c:set var="weekly2" value="${ movieInfoMap['weekly2'] }"/>
<c:set var="upcoming" value="${ movieInfoMap['upcoming'] }"/>
<c:set var="recentlyKo" value="${ movieInfoMap['recentlyKorea'] }"/>
<c:set var="recentlyFo" value="${ movieInfoMap['recentlyForeign'] }"/>
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
<c:if test="${ daily.size() > 0 }">
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">일간 Best 10</span></div>
		<div style="display:flex; position:relative">
		
			<button id="dailyLeftBtn" class="leftBtn" type="button" onclick="dailyLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ daily }">
			<% if(cnt < 5){ %>
			<a class="dailyTop" href="/movieInfo?movieId=${ list.movieId }" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a class="dailyTop" href="/movieInfo?movieId=${ list.movieId }" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<button id="dailyRightBtn" class="rightBtn" type="button" onclick="dailyRightBtn();">▶</button>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ weekly0.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주간 Best 10</span></div>
		<div style="display:flex; position:relative"">
		
			<button id="weeklyLeftBtn0" class="leftBtn" onclick="weeklyLeftBtn0();">◀</button>
			
			<c:forEach var="list" items="${ weekly0 }">
			<% if(cnt < 5){ %>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop0" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop0" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn0" type="button" class="rightBtn" onclick="weeklyRightBtn0();">▶</button>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ weekly1.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주말 Best 10</span></div>
		<div style="display:flex; position:relative">
		
			<button id="weeklyLeftBtn1" class="leftBtn" onclick="weeklyLeftBtn1();">◀</button>
			
			<c:forEach var="list" items="${ weekly1 }">
			<% if(cnt < 5){ %>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop1" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop1" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn1" class="rightBtn" type="button" onclick="weeklyRightBtn1();">▶</button>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ weekly2.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
	rankCnt = 1;
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">주중 Best 10</span></div>
		<div style="display:flex; position:relative">
		
			<button id="weeklyLeftBtn2" class="leftBtn" onclick="weeklyLeftBtn2();">◀</button>
			
			<c:forEach var="list" items="${ weekly2 }">
			<% if(cnt < 5){ %>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop2" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="weeklyTop2" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px; position:relative;">
					<div class="rankDiv"><%= rankCnt++ %></div>
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<button id="weeklyRightBtn2" type="button" class="rightBtn" onclick="weeklyRightBtn2();">▶</button>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ recentlyKo.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">최근 개봉된 한국 영화</span></div>
		<div style="display:flex; position:relative">
		
			<button id="recentlyKoLeftBtn" class="leftBtn" onclick="recentlyKoLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ recentlyKo }">
			<% 
				if(cnt < 5){ 
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyKo" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyKo" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<c:if test="${ list.gradeCheck eq true }">
							<span style="color:orange;">평가함 ★ ${ list.gradeAvg }</span>
						</c:if>
						<c:if test="${ list.gradeCheck ne true }">
							<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
						</c:if>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<% if(cnt > 5){ %>
			<button id="recentlyKoRightBtn" class="rightBtn" type="button" onclick="recentlyKoRightBtn('<%=cnt%>');">▶</button>
			<% } %>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ recentlyFo.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">최근 개봉된 외국 영화</span></div>
		<div style="display:flex; position:relative">
		
			<button id="recentlyFoLeftBtn" class="leftBtn" onclick="recentlyFoLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ recentlyFo }">
			<% 
				if(cnt < 5){ 
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyFo" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }">
					<div align="left">
						<c:if test="${ list.movieNm.length() > 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="recentlyFo" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px;">
					<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<span style="color:red;">평균 ★ ${ list.gradeAvg }</span>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<% if(cnt > 5){ %>
			<button id="recentlyFoRightBtn" class="rightBtn" type="button" onclick="recentlyFoRightBtn('<%=cnt%>');">▶</button>
			<% } %>
		</div>
	</div>
</div>
</c:if>

<c:if test="${ upcoming.size() > 0 }">
<br><br><br>
<% 
	cnt = 0; 
%>
<div align="center" style="width:100%;">
	<div align="center" style="width:80%;">
		<div align="left" style="width:100%; padding-bottom:10px;"><span style="font-size:30px; font-weight:bold;">Upcoming Movies</span></div>
		<div style="display:flex; position:relative">
		
			<button id="upcomingLeftBtn" class="leftBtn" onclick="upcomingLeftBtn();">◀</button>
			
			<c:forEach var="list" items="${ upcoming }">
			<% 
				if(cnt < 5){ 
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="upcoming" style="all:unset; cursor:pointer; display:;">
				<div style="padding-right:10px;">
					<c:if test="${ list.posterUrl ne 'nan' }">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					</c:if>
					<c:if test="${ list.posterUrl eq 'nan' }">
						<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
					</c:if>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 15) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 15 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<span style="color:blue;">D-${ list.getDDay() }</span>
					</div>
				</div>
			</a>
			<%
			}else{
			%>
			<a href="/movieInfo?movieId=${ list.movieId }" class="upcoming" style="all:unset; cursor:pointer; display:none;">
				<div style="padding-right:10px;">
					<c:if test="${ list.posterUrl ne 'nan' }">
						<img style="width:${imgWidth}px; height:${ imgHeight }px;" src="${ list.posterUrl }"><br>
					</c:if>
					<c:if test="${ list.posterUrl eq 'nan' }">
						<div style="width:${imgWidth}px; height:${ imgHeight }px; border:1px solid rgba(0, 0, 0, 0.1); display:flex; justify-content:center; align-items:center;">이미지가 없습니다.</div>
					</c:if>
					<div align="left">
						<c:if test="${ list.movieNm.length() > 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm.substring(0, 18) }...</span><br>	
						</c:if>
						<c:if test="${ list.movieNm.length() <= 18 }">
							<span style="font-size:18px; font-weight:bold">${ list.movieNm }</span><br>
						</c:if>
						${ list.openDt.substring(0, 4) } ・ ${ list.nations }<br>
						<span style="color:blue;">D-${ list.getDDay() }</span>
					</div>
				</div>
			</a>
			<%
				}
			cnt++;
			%>
				
			</c:forEach>
			<% if(cnt > 5){ %>
			<button id="upcomingRightBtn" class="rightBtn" type="button" onclick="upcomingRightBtn('<%=cnt%>');">▶</button>
			<% } %>
		</div>
	</div>
</div>
</c:if>

<script src="/resources/js/common.js"></script>
<script src="/resources/js/home.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>