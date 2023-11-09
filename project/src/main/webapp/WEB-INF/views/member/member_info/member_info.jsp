<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div id="bodyForShadow" class="bodyForShadow" style="content: '';
    position:fixed;  z-index:-2;  display:none;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 1;">
</div>
<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
<%@ include file="/WEB-INF/views/header.jsp" %>
<hr style="border:1px solid #ccc; margin:0px;">


<div style="position: relative; background-color: rgb(244, 244, 247); height: 855px; z-index: 0;">
    <div class="user-info-main-card-frame" style="position: relative; top: 50px; background-color: white; width: 700px; height: 350px; margin: 0 auto; padding: 20px;">
		
		<div style="display:flex; justify-content:center">
			<div style="position:relative; margin-right:500px; margin-top:80px;">
				<img style="width:100px;" src="resources/img/bean_profile.png"/>
			</div>
			<div style="margin-top:20px; margin-right:0px;">
					<img id="openModalButton" style="width:30px; cursor:pointer;" src="resources/img/setting.png"/>
			</div>
		</div>
		
		<div style="margin-left:40px; margin-top:20px;">
			<div style="font-size:25px; display:flex;">
				<div>
					<b>${dto.userName }</b>
				</div>
				<div style="margin-left:10px;">
					<c:choose>
						<c:when test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }"><!-- local이거나 admin이면 -->
							<a href="/pwCheck">
						</c:when>
						<c:otherwise>
							<a href="socialMemberInfoModify">
						</c:otherwise>
						</c:choose>
							<img src="resources/img/pencil.png" style="width:20px;"/>
							</a>
				</div>
			</div>
			<div>
				${dto.userEmail}
			</div>
		</div>
		
		<div style="display: flex; border-top: 1px solid rgb(244, 244, 247); margin-top:40px;">
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">평가</span><br>
				<span>${map['score']}</span>
			</a>
			<div style="background-color: rgb(232, 232, 239); width: 1px; height: 60px;"></div>
			<a href="/userReviewList" style="flex: 1; text-align: center; text-decoration:none; margin-top:10px;">
				<span style="font-size:18px;">코멘트</span><br>
				<span>${map['comment']}</span>
			</a>
		</div>
	</div>
	
	<div style="height:10px;"></div>
	
	<div class="user-info-like-card-frame" style="position: relative; top: 50px; background-color: white; width: 700px; height: 200px; margin: 0 auto; padding: 20px;">
		<div style="display:flex; font-size:25px; margin-top:-10px; margin-left:25px; margin-bottom:15px;">
			<div style="margin-top:10px;">
				<b>좋아요</b>
			</div>
			<div style="margin-top:12px; margin-left:10px;">
				<img src="resources/img/likeColor.png" style="width:30px;"/>
			</div>
		</div>
		
		<div onclick="moveToLikedMovieList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px; margin-bottom:10px;">
				좋아한 영화
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedMovie']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
		<div onclick="moveToLikedActorList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px; margin-bottom:10px;">
				좋아한 배우
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedActor']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
		<div onclick="moveToLikedCommentList();" style="display:flex; font-size:20px; margin-left:25px; border-top: 1px solid rgb(244, 244, 247); cursor:pointer;">
			<div style="margin-top:10px;">
				좋아한 코멘트
			</div>
			<div style="margin-left:10px; margin-top:8px; color:grey;">
				(${map['likedComment']})
			</div>
			<div style="margin-left: auto; margin-top:10px;">
				<img src="resources/img/next.png" style="width:8px;"/>&nbsp	
			</div>
		</div>
		
	</div>
</div>


<div id="myModal" class="modal" style=" position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:600px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:520px; border-radius:5px;">
    	
    	<div class="top" style="display:flex; width: 100%; height:80px; top: 5%;"><!-- top  -->
			<span style="font-size:30px; font-weight:bold; margin-left:125px;">Setting</span>
			<span id="closeModalButton" class="close" style="margin-left:auto; font-size:30px; cursor:pointer;">&times;</span>
	    </div>
	    
	    <div class="search-area" style="width: 100%; height:30px; top: 10%;">
	    	검색설정
	    </div>
	    <a href="searchHistoryView" style="all:unset; cursor:pointer;">
		    <div class="delete-record" style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	검색 기록 보기
		    </div>
	    </a>
	    <div class="save-record" style="padding:5px; display:flex; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
	    	<div style="font-size:20px;">
	    		검색 기록 설정
	    	</div>
	    	<div id="onAndOff" style="margin-left:auto; font-size:18px;"><!-- getElementById(".onAndOff") -->
	    		<c:if test="${ searchHistory eq 0 }">
		    		<div class="on" style="color:#2AAEFA; font-weight:bold; cursor:pointer;" onclick="searchConfig()">
		    			ON
		    		</div>
	    		</c:if>
	    		<c:if test="${ searchHistory eq 1 }">
	    			<div class="on" style="color:red; font-weight:bold; cursor:pointer;" onclick="searchConfig()">
		    			OFF
		    		</div>
	    		</c:if>
	    	</div>
	    </div>
	    <div style="height:50px;">
	    	&nbsp	
	    </div>
	    <div class="customer-service" style="width: 100%; height:30px; top: 10%;">
	    	고객센터
	    </div>
	    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
	    	<a style="cursor:pointer; text-decoration:none; color: inherit;" href="https://forms.gle/PeP2Bgx2ZLAVdyo58">
				DB 수정 / 추가 요청하기
			</a>
	    </div>
	    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
	    	<a style="cursor:pointer; text-decoration:none; color: inherit;" href="https://pf.kakao.com/_tDGjG">
	    		문의하기 / 카카오 채널 추가
	    	</a>
	    </div>
	    <div style="height:50px;">
	    	&nbsp	
	    </div>
	    <div class="customer-service" style="width: 100%; height:30px; top: 10%;">
	    	계정
	    </div>
	    				<!---------------------------------------------------- Kakao ---------------------------------------------------------------------------------- -->
		<c:if test="${sessionScope.userLoginType eq 2 }">
		    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
	   			<a style="cursor:pointer" href="https://kauth.kakao.com/oauth/logout?client_id=36b59ada5e8b70c6afae51b77c038484&logout_redirect_uri=http://localhost:8080/kakaoSignOut" >
					로그아웃
				</a>
		    </div>
		    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="socialConfirmUnregister()">
					탈퇴하기
				</a>
		    </div>				
		</c:if>
				
				<!---------------------------------------------------- Naver ---------------------------------------------------------------------------------- -->
		<c:if test="${sessionScope.userLoginType eq 3 }">
			<div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="naverConfirmSignOut()"><!-- 혹시 탈퇴하거나 할 때 정보 동의 다 철회하고 토큰도 없애고 싶다면 a href ="remove?token=${sessionScope.accessToken }" 으로 하면 된다.-->
					로그아웃
				</a>
		    </div>
		    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="socialConfirmUnregister()">
					탈퇴하기
				</a>
		    </div>	
		</c:if>
					
				<!---------------------------------------------------- Google ---------------------------------------------------------------------------------- -->
				
		<c:if test="${sessionScope.userLoginType eq 4 }">
			<div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="googleConfirmSignOut()">
					로그아웃
				</a>
			</div>
		    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="socialConfirmUnregister()">
					탈퇴하기
				</a>
			</div>
		</c:if>
				
				<!---------------------------------------------------- Local ---------------------------------------------------------------------------------- -->
				
		<c:if test="${sessionScope.userLoginType eq 0 || sessionScope.userLoginType eq 1  }">
			<div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a style="cursor:pointer" onclick="localConfirmSignOut()">
					로그아웃
				</a>
			</div>
		    <div style="font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
				<a href="/unregister">탈퇴하기</a>
			</div>
		</c:if>
		<!-- -------------------------------------------------------------------------------------------------------------------------------------------------- -->
    </div>                                       
</div>


<script src="/resources/js/common.js"></script>
<script src="/resources/js/member_info.js"></script>
<script src="/resources/js/search_common.js"></script>
</body>
</html>