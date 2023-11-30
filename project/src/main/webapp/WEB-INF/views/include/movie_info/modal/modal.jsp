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
	<!-- 댓글 작성용 modal -->
<div id="modalForWritingComment" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitleForWriting" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModalForWriting();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		<textarea style="width:340px; height:250px;"id="movieCommentForWriting"></textarea>
	   		<input type="hidden" id="modalMovieIdForWritingComment" />
	   		
	   	</div>
	   	<div>
	   		<button style="margin-top:10px; margin-left:40%" onclick="saveMovieComment();">작성하기</button>
	   	</div>
  	</div>
</div>
<!-- 댓글 수정용 modal -->
<div id="modalForModifyComment" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitleForModify" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModalForModify();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		<textarea style="width:340px; height:250px;"id="movieCommentForModify"></textarea>
	   		<input type="hidden" id="modalMovieIdForModify" />
	   		
	   	</div>
	   	<div>
	   		<button style="margin-top:10px; margin-left:40%" onclick="updateMovieComment();">수정하기</button>
	   	</div>
  	</div>
</div>
<!-- 댓글 신고용 modal -->
 <div id="modal" style="position: fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
 		<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="modalMovieTitle" style="font-size:25px; margin-left:8px; margin-top:5px; font-weight:bold;">신고사유</span>
			<span class="closeModalButton" onclick="closeModalForReport();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
		<div>&nbsp 
			<input type="hidden" id="storage-for-author" />
			<input type="hidden" id="storage-for-comment" />
			<input type="hidden" id="storage-for-comment-id" />
			<input type="hidden" id="storage-for-movieId" />
			<input type="hidden" id="storage-for-cnt"/>
		</div>
		<div>
		    <div id="bad-words" onclick="reportForBadWord();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-top:1px solid rgb(0,0,0); border-bottom: 1px solid rgb(0,0,0);">
		    	욕설 또는 비속어
		    </div>
		    <div id="spam" onclick="reportForSpam();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	도배 및 스팸
		    </div>
		    <div id="false-fact" onclick="reportForFalseFact();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	허위 정보
		    </div>
		    <div id="racism" onclick="reportForRacism();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	차별 / 혐오 발언
		    </div>
		    <div id="spoiler" onclick="reportForSpoiler();" style="cursor:pointer; font-size:20px; padding:5px; width: 97%; height:30px; top: 10%; border-bottom: 1px solid rgb(0,0,0);">
		    	스포일러
		    </div>
	    </div>
 	</div>
 </div>
<!-- 블랙리스트에게 댓글 기능 사용 불가 알림용 modal -->
 <div id="modalForBlack" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:200px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:150px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span class="closeModalButton" onclick="closeModalForBlack();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div>
	   		${sessionScope.userEmail} 님은 <br>적절하지 않은 댓글 활동으로 인하여<br> 댓글 기능을 더 이상 이용할 수 없습니다.
	   	</div>
  	</div>
</div>

<!-- 코멘트 디테일용 -->
<div id = "modal-for-comment-detail" style=" position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); width:400px; height:400px;background-color:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); border:1px solid #ccc;border-radius:4px; z-index:-2; display:none;">
    <div class="modal-content" style="background-color:white; position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:350px; height:350px; border-radius:5px;">
    	<div class="top" style="display:flex; width: 100%; height:60px; top: 5%;"><!-- top  -->
			<span id="user-name-for-comment-detail" style="font-size:15px; font-weight:bold;"></span>
			<span class="closeModalButton" onclick="closeModalForCommentDetail();" style="margin-left:auto; font-size:20px; cursor:pointer;">&times;</span>
	    </div>
	    <div id="comment-detail-area" style="width:100%; word-wrap:break-word;">
	    	
	    </div>
	</div>
</div>


 <div id="bodyForShadow" class="bodyForShadow" 
 style="content: ''; position:fixed;  z-index:-2;  display:none; top: 0; 
 left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.7); z-index: 1;">
 </div>
</body>
</html>