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
	<c:if test="${ peopleInfo.peopleCast[0] ne 'nan' }">
                     
		<c:if test="${ i * 4 - 1 <= peopleInfo.end }">
	    	<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ i * 4 - 1 }" step="1">
	        	<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] ne '0' }">
	            	<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
	            		<div>
	                		<c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
								<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
									<img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
								</div>
							</c:if>
							<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
								<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
							</c:if>
	               		</div>
	               		<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
	                 		<div style="padding-top:10px;">
	                     		<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
	                  		</div>
		                  	<div style="padding-top:5px;">
		                    	<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
		                  	</div>
	               		</div>
	            	</a>
	         	</c:if>
	         	<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] eq '0' }">
	            	<div style="display:flex; width:25%;">
	                	<div>
							<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
	               		</div>
	               		<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
	                  		<div style="padding-top:10px;">
	                     		<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
	                  		</div>
	                  		<div style="padding-top:5px;">
	                     		<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
	                  		</div>
	               		</div>
	            	</div>
	         	</c:if>
	      	</c:forEach>
	   	</c:if>
	   
	   	<c:if test="${ i * 4 - 1 > peopleInfo.end }">
	   		<c:forEach var="j" begin="${ i * 4 - 4 }" end="${ peopleInfo.end }" step="1">
	      		<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] ne '0' }">
	            	<a href="peopleInfo?peopleId=${ peopleInfo.peopleId[j] }" style="all:unset; cursor:pointer; display:flex; width:25%;">
	               		<div>
	                  		<c:if test="${ peopleInfo.profileUrl[j] ne 'nan' }">
								<div style="width: 80px; height: 80px; overflow: hidden; border-radius:100px;">
									<img style="max-width: 100%;" src="${ peopleInfo.profileUrl[j] }" alt="영화 포스터">
								</div>
							</c:if>
							<c:if test="${ peopleInfo.profileUrl[j] eq 'nan' }">
								<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
							</c:if>
	               		</div>
		               	<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
		                	<div style="padding-top:10px;">
		                    	<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
		                  	</div>
		                  	<div style="padding-top:5px;">
		                    	<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
		                  	</div>
		               	</div>
	            	</a>
	  			</c:if>
	         	<c:if test="${ not empty peopleInfo.peopleCast[j] && peopleInfo.peopleId[j] eq '0' }">
		            <div style="display:flex; width:25%;">
		           		<div>
							<img style="width:80px; height:80px;" src="/resources/img/bean_profile.png">
		             	</div>
		               	<div style="width: 230px; border-bottom: solid 1px rgba(0, 0, 0, 0.1); margin-left:20px;">
		              		<div style="padding-top:10px;">
		                  		<span style="font-size:22px; font-weight:bold;">${ peopleInfo.peopleNm[j] }</span>
		                  	</div>
		                  	<div style="padding-top:5px;">
		                  		<span style="font-size:17px; color:rgba(0, 0, 0, 0.5);">출연 | ${ peopleInfo.peopleCast[j] }</span>
		                  	</div>
		            	</div>
		        	</div>
	    		</c:if>
	      	</c:forEach>
	   	</c:if> 
	</c:if>
</body>
</html>