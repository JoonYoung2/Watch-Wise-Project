function openModalForReport(author, comment, commentId, movieId, cnt){
	
	console.log("dkdkdkdkdk");
	console.log("author: ", author);
	console.log("comment: ", comment);
	console.log("commentId: ", commentId);
	console.log("movieId: ", movieId);
	console.log("cnt: ", cnt);
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	
	let authorArea = document.getElementById('storage-for-author');
	let commentArea = document.getElementById('storage-for-comment');
	let commentIdArea = document.getElementById('storage-for-comment-id');
	let movieIdArea = document.getElementById('storage-for-movieId');
	let cntArea = document.getElementById('storage-for-cnt');
	console.log("commentId",commentId);
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
	authorArea.value = author;
	commentArea.value = comment;
	commentIdArea.value = commentId;
	movieIdArea.value = movieId;
	cntArea.value = cnt;
	console.log(cntArea.value);
}

function closeModalForReport(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}

	let badWord = document.getElementById('bad-words');
	let spam = document.getElementById('spam');
	let falseFact = document.getElementById('false-fact');
	let racism = document.getElementById('racism');
	let spoiler = document.getElementById('spoiler');

//------------------ Report 유형 별 ajax 및 ui 구성 ----- START ---------------------------------	
function reportForBadWord(){
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let commentId = document.getElementById('storage-for-comment-id').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let cnt = document.getElementById('storage-for-cnt').value;
	let num = Number(cnt);
	let reason = '욕설 또는 비속어';
	badWord.style.backgroundColor = "grey";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, commentId, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);	
			 let htmlMsg = "<img src='resources/img/activatedAlert.png' onclick='cancelReportCheck(\"" + author + "\", \"" + comment + "\", \"" + commentId + "\", \"" + movieId + "\", " + cnt + ");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;	
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}
function reportForSpam(){
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let commentId = document.getElementById('storage-for-comment-id').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let cnt = document.getElementById('storage-for-cnt').value;
	let num = Number(cnt);
	let reason = '도배 및 스팸';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "grey";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";
	
	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, commentId, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);		
			 let htmlMsg = "<img src='resources/img/activatedAlert.png' onclick='cancelReportCheck(\"" + author + "\", \"" + comment + "\", \"" + commentId + "\", \"" + movieId + "\", " + cnt + ");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForFalseFact(){
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let commentId = document.getElementById('storage-for-comment-id').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let cnt = document.getElementById('storage-for-cnt').value;
	let num = Number(cnt);
	let reason = '허위 정보';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "grey";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "";
	
	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, commentId, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
			 let htmlMsg = "<img src='resources/img/activatedAlert.png' onclick='cancelReportCheck(\"" + author + "\", \"" + comment + "\", \"" + commentId + "\", \"" + movieId + "\", " + cnt + ");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForRacism(){
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let commentId = document.getElementById('storage-for-comment-id').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let cnt = document.getElementById('storage-for-cnt').value;
	let num = Number(cnt);
	let reason = '차별 / 혐오 발언';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "grey";
	spoiler.style.backgroundColor = "";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, commentId, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
			 let htmlMsg = "<img src='resources/img/activatedAlert.png' onclick='cancelReportCheck(\"" + author + "\", \"" + comment + "\", \"" + commentId + "\", \"" + movieId + "\", " + cnt + ");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}

function reportForSpoiler(){
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	let author = document.getElementById('storage-for-author').value;
	let comment = document.getElementById('storage-for-comment').value;
	let commentId = document.getElementById('storage-for-comment-id').value;
	let movieId = document.getElementById('storage-for-movieId').value;
	let cnt = document.getElementById('storage-for-cnt').value;
	let num = Number(cnt);
	let reason = '스포일러';
	badWord.style.backgroundColor = "";
	spam.style.backgroundColor = "";
	falseFact.style.backgroundColor = "";
	racism.style.backgroundColor = "";
	spoiler.style.backgroundColor = "grey";

	$.ajax({
		url: '/reportComment',
		type:'POST',
		data:{
			author, comment, commentId, movieId, reason
		},
		success: (response) =>{
			 let msg = response.msg;
			 alert(msg);
			 let htmlMsg = "<img src='resources/img/activatedAlert.png' onclick='cancelReportCheck(\"" + author + "\", \"" + comment + "\", \"" + commentId + "\", \"" + movieId + "\", " + cnt + ");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;
		},
		error:() =>{
			console.log(error);
		}
	});
	
	setTimeout(() => {
    	closeModalForReport();
  	}, 500);
}
//------------------ Report 유형 별 ajax 및 ui 구성 ----- END ---------------------------------	
function cancelReportCheck(userEmail, reviewComment, commentId, movieId, cnt){
	var result = confirm("신고를 취소하시겠습니까?");
	if (result == true) {
		cancelReport(userEmail, reviewComment, commentId, movieId, cnt);
	} else {
	    // 사용자가 취소를 선택한 경우 실행되는 코드
	    console.log("사용자가 취소를 선택했습니다.");
	}
}

function cancelReport(userEmail, reviewComment, commentId, movieId, cnt){
	let num = Number(cnt);//?

	let author = document.getElementById('storage-for-author').value;
	let alertImgArea = document.querySelectorAll('.forAlertImg');
	$.ajax({
		url: 'cancelReport',
	 	type: 'GET',
	 	data:{
			 movieId, userEmail
		 },
		 success: (response) =>{
			 let msg = response.msg;
			 console.log(msg);
			 alert(msg);
			 let htmlMsg = "<img src='resources/img/alert.png' onclick='openModalForReport(\"" + userEmail + "\", \"" + reviewComment + "\", \"" + commentId + "\", " + movieId + " , \"" + cnt + "\");' style='cursor:pointer; margin-left:10px;  width:25px;'>";
			 alertImgArea[num].innerHTML=htmlMsg;
		 },
		 error:() => {
			 console.log(error);
		 }
	});

    closeModalForReport();
}

//------------------------------reported_comments.jsp-----------------------
function checkForDelete(commentId, email, pageNum){
	if (confirm("해당 데이터를 삭제하시겠습니까?")) {
		location.href="/admin/deleteReportedData?commentId=" + commentId + "&email=" + email + "&pageNum=" + pageNum;
	}
}

function openModalForReportersList(blackListDto, cnt){
	let num = Number(cnt);
	let body = document.getElementById('bodyForShadow');
	let modalClass = document.querySelectorAll('.modal');
//	let madalTitle = document.getElementById('modalMovieTitle');
//	let textBox = document.getElementById('movieComment');
//	let hiddenMovieId = document.getElementById('modalMovieId');
	
	modalClass[num].style.display = 'block';
	body.style.display = 'block';
	modalClass[num].style.zIndex = '2';
	body.style.zIndex = '1';
//	madalTitle.textContent = movieNm;
//	textBox.innerHTML = comment;
//	hiddenMovieId.value=movieId;
	//$ajax
}

function closeModalForReportersList(cnt){
	let num = Number(cnt);
	let body = document.getElementById('bodyForShadow');
	let modalClass = document.querySelectorAll('.modal');
	modalClass[num].style.display = 'none';
	body.style.display = 'none';
	modalClass[num].style.zIndex = '-2';
	body.style.zIndex = '-2';
}

function openBlackModal(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForBlack');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
}

function closeModalForBlack(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForBlack');
	
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}