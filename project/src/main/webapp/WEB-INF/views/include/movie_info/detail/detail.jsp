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
	<div align="center" style="width:100%;">
    	<div align="center" style="width:80%; display:flex;">
        	<div align="left" style="width:100%;">
	        	<div style="display:flex; justify-content:center;">
		        	<div style="display:flex;">
			        	<div style="margin-top:10px;">
			        		<!-- 영화 포스터 -->
			        		<%@ include file="./detail_poster.jsp" %>
				        	
				        	
					        <div style="display:flex; align-items:center; width:100%;">
					        	<div style="display:flex; justify-content: center; align-items:center; width:100%;">
					         		<div>
					         			<!-- 영화 별점 Text -->
					         			<%@ include file="./detail_rating_text.jsp" %>
						            	
						            	<!-- 영화 평점 그래프 -->
						            	<%@ include file="./detail_rating_graph.jsp" %>
										<!-- 영화 평점 그래프 -->
					            	</div>
				            	</div>
	            			</div>
				         </div>
				         <div style="display:flex; margin-left:200px; padding-top:0px;">
				         	<div>
				         		<!-- 영화 상세 정보 Text -->
				         		<%@ include file="./detail_info_text.jsp" %>
								
								<!-- 광고 -->
								<div class="ad-div">
									<a href="https://www.xexymix.com/"><img src="/resources/img/ad.png"></a>
								</div>
					            
								<!-- 코멘트 작성 & 수정 -->
								<%@ include file="./detail_comment.jsp" %>
							</div>	
			        	</div>
			    	</div>
		    	</div>	     	
        	</div>
    	</div>
	</div>
</body>
</html>