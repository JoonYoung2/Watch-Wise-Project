let reviewScoreId = document.getElementById("reviewScore");
let reviewScoreValue = 0.0;
let gradeCheckValue = document.getElementById("gradeCheck").value;

window.onload = () => {
	reviewScoreValue = Number(reviewScoreId.value);
	
	let msg = document.getElementById("msg");
	if(reviewScoreValue != 0.0){
		if(reviewScoreValue == 0.5){
			msg.innerHTML = "최악이에요";
		}else if(reviewScoreValue == 1.0){
			msg.innerHTML = "싫어요";
		}else if(reviewScoreValue == 1.5){
			msg.innerHTML = "재미없어요";
		}else if(reviewScoreValue == 2.0){
			msg.innerHTML = "별로예요";
		}else if(reviewScoreValue == 2.5){
			msg.innerHTML = "부족해요";
		}else if(reviewScoreValue == 3.0){
			msg.innerHTML = "보통이에요";
		}else if(reviewScoreValue == 3.5){
			msg.innerHTML = "볼만해요";
		}else if(reviewScoreValue == 4.0){
			msg.innerHTML = "재미있어요";
		}else if(reviewScoreValue == 4.5){
			msg.innerHTML = "훌륭해요";
		}else if(reviewScoreValue == 5.0){
			msg.innerHTML = "최고예요";
		}
	}

	// chart movie
	let chartMovieValue = document.getElementById("chartMovie").value;
	let tmp = chartMovieValue.replaceAll("{", "").replaceAll("}", "").replaceAll(" ", "").split(",").sort();
	for(var i = 1; i < 10; i++){
		if(tmp[i][0] == tmp[i-1][0]){
			let swap = tmp[i];
			tmp[i] = tmp[i-1];
			tmp[i-1] = swap;
		}
	}

	let chartCnt = [];
	let maxScore = 0;
	let reviewCnt = 0;
	for(var i = 0; i < tmp.length; i++){
		chartCnt[i] = tmp[i].split('=')[1];
		if(maxScore < chartCnt[i]){
			maxScore = Number(chartCnt[i]);
		}
		if(chartCnt[i] != "nan"){
			let graphTableDivId = document.getElementById("graph-table-div");
			graphTableDivId.style.display="";
			reviewCnt++;
		}
	}
	
	let graphBarCntClass = document.querySelectorAll(".graph-bar-cnt");
	let graphBarContentClass = document.querySelectorAll(".graph-bar-content");
	graphBarContentClass[0].style.display="";
	graphBarContentClass[9].style.display="";
	for(var i = 0; i < graphBarCntClass.length; i++){
		console.log(graphBarCntClass.length);
		if(reviewCnt != 0){
			if(chartCnt[i] == "nan"){
				chartCnt[i] = 3;
			}
		}else{
			chartCnt[i] = 0;
		}
	}
	let heightNum = 100;
	let chartHeight = ["", "", "", "", "", "", "", "", "", ""];
	for(var i = 0; i < 10; i++){
		let maxNum = 0;
		let cnt = 0;
		for(var j = 0; j < 10; j++){
			if(maxNum < chartCnt[j]){
				maxNum = Number(chartCnt[j]);
			}
		}
		
		for(var j = 0; j < 10; j++){
			if(maxNum == Number(chartCnt[j])){
				chartHeight[j] = heightNum; 
				chartCnt[j] = -1;
				cnt++;
				if(cnt > 1){
					i++;
				}
			}
		}
		heightNum = heightNum - (10*cnt);
		console.log(heightNum);
	}
	for(var i = 0; i < 10; i++){
		console.log(chartHeight[i]);
		graphBarCntClass[i].style.height=chartHeight[i]+"px";
		if(100 == chartHeight[i]){
			graphBarContentClass[i].style.display="";
			graphBarCntClass[i].style.backgroundColor="rgba(255, 10, 10, 0.8)";
		}
	}
	
	// chart movie
}

let postersLeftBtnId = document.getElementById("postersLeftBtn");

let postersRightBtnId = document.getElementById("postersRightBtn");

let postersClass = document.querySelectorAll(".posters");

let postersPageNum = 1;

let postersCnt = 0;

// LIKE Method START---------------------------------------------------------------------------------------

const unregisterLickClick = () => {
	alert("로그인 후 좋아요를 할 수 있습니다.");
	return;
}

const likeAdd = async (movieId) => {
    const likeDiv = document.getElementById("likeDiv");
    let msg = "";
    $.ajax({
        url: "http://localhost:8080/movieLikeAdd",
        method: "GET",
        data: {
            movieId
        },
        success: function (response) {
            var likeNum = response.likeNum;

                msg += "<div style='display:flex; justify-content:center; align-items:center;' class='likeCancel' onclick='likeCancel("+movieId+");'>";
                msg += "<div style='display:flex; align-items:center;'><img style='width:40px; padding-left:7px;' src='/resources/img/likeColor.png'><span style='padding-left:7px; font-size:30px;'>("+likeNum+")</span></div>";
                msg += "</div>";
                likeDiv.innerHTML=msg;
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}

const likeCancel = async (movieId) => {
    const likeDiv = document.getElementById("likeDiv");
    let msg = "";
    $.ajax({
        url: "http://localhost:8080/movieLikeCancel",
        method: "GET",
        data: {
            movieId
        },
        success: function (response) {
            var likeNum = response.likeNum;

            console.log("서버 응답 - likeNum: " + likeNum);
                msg += "<div style='display:flex; justify-content:center; align-items:center;' class='likeAdd' onclick='likeAdd("+movieId+");'>";
                msg += "<div style='display:flex; align-items:center;'><img style='width:40px; padding-left:7px;' src='/resources/img/like.png'><span style='padding-left:7px; font-size:30px;'>("+likeNum+")</span></div>";
                msg += "</div>";
                likeDiv.innerHTML=msg;
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}
// LIKE Method END-----------------------------------------------------------------------------------------------

const postersLeftBtn = () => {
	postersPageNum--;
	if (postersPageNum == 1) {
		postersLeftBtnId.style.display = "none";
		postersRightBtnId.style.display = "block";
		for (var i = 0; i < postersClass.length; i++) {
			if (i >= postersPageNum * 5 - 5 && i < postersPageNum * 5) {
				postersClass[i].style.display = "";
			} else {
				postersClass[i].style.display = "none";
			}
		}
	} else {
		postersLeftBtnId.style.display = "block";
		postersRightBtnId.style.display = "block";
		for (var i = 0; i < postersClass.length; i++) {
			if (i >= postersPageNum * 5 - 5 && i < postersPageNum * 5) {
				postersClass[i].style.display = "";
			} else {
				postersClass[i].style.display = "none";
			}
		}
	}
}

const postersRightBtn = (cnt) => {
	postersCnt = Number(cnt);
	let pageNum = Math.ceil(postersCnt / 5);
	postersPageNum++;
	if (postersPageNum == pageNum) {
		postersLeftBtnId.style.display = "block";
		postersRightBtnId.style.display = "none";
		for (var i = 0; i < postersClass.length; i++) {
			if (i >= postersPageNum * 5 - 5 && i < postersPageNum * 5) {
				postersClass[i].style.display = "";
			} else {
				postersClass[i].style.display = "none";
			}
		}
	} else {
		postersLeftBtnId.style.display = "block";
		postersRightBtnId.style.display = "block";
		for (var i = 0; i < postersClass.length; i++) {
			if (i >= postersPageNum * 5 - 5 && i < postersPageNum * 5) {
				postersClass[i].style.display = "";
			} else {
				postersClass[i].style.display = "none";
			}
		}
	}
}

//------------------------------------ 평점 / 코멘트 START --------------------------------------------------

let gradeCnt = Number(document.getElementById("gradeCnt").value);

function rating(score, movieId) {  //평점
	let allGradeAvgId = document.getElementById("allGradeAvg");
	let selectBoxId = document.getElementById("select-box");
	let gradeCntId = document.getElementById("gradeCount");
	let msgId = document.getElementById("msg");
	let selectedScore = Number(score);
	let data = {reviewScore : selectedScore, movieId};
	let gradeMsg = "";
	$.ajax({
		url: '/getReivewScore', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
			msgId.innerHTML = response.msg;
			let gradeAvg = response.gradeAvg + "";
			if(gradeAvg.length > 3){
				gradeAvg = gradeAvg.substring(0, 3);
			}
			if(gradeAvg.length == 1){
				gradeAvg += ".0";
			}
			if(selectedScore == 0.0){
				selectBoxId.style.color = "black";
				msgId.innerHTML = "평가해주세요";
				selectBoxId.innerHTML = `<option value="0.0">★ 0.0</option>
						        <option value="0.5">★ 0.5</option>
						        <option value="1.0">★ 1.0</option>
						        <option value="1.5">★ 1.5</option>
						        <option value="2.0">★ 2.0</option>
						        <option value="2.5">★ 2.5</option>
						        <option value="3.0">★ 3.0</option>
						        <option value="3.5">★ 3.5</option>
						        <option value="4.0">★ 4.0</option>
						        <option value="4.5">★ 4.5</option>
						        <option value="5.0">★ 5.0</option>`;
				gradeMsg += "(";
				gradeMsg += gradeCnt - 1;
				gradeMsg += "명)";
				gradeCnt = gradeCnt - 1;
				gradeCntId.innerHTML = gradeMsg;
				gradeCheckValue = 0;
				reviewScoreValue = selectedScore;
				selectBoxId.style.color="red";
				allGradeAvgId.style.color="red";
				allGradeAvgId.innerHTML = "★ " + gradeAvg;
			}else{
				selectBoxId.style.color = "orange";
				allGradeAvgId.innerHTML = "★ " + gradeAvg;
				if(reviewScoreValue == 0.0 && gradeCheckValue == 0){
					gradeMsg += "(";
					gradeMsg += gradeCnt + 1;
					gradeMsg += "명)";
					gradeCnt = gradeCnt + 1;
					gradeCntId.innerHTML = gradeMsg;
					reviewScoreValue = selectedScore;
					gradeCheckValue = 1;
				}else{
					if(gradeCheckValue == 0){
						gradeMsg += "(";
						gradeMsg += gradeCnt + 1;
						gradeMsg += "명)";
						gradeCnt = gradeCnt + 1;
						gradeCntId.innerHTML = gradeMsg;
						reviewScoreValue = selectedScore;
					}
					gradeCheckValue = 1;
				}
			}
		},
		error: () => {
			console.log(error);
		}
	});
}


//function showModifyForm(){  //코멘트
//	console.log("gdgd");
//	let modifyFormId = document.getElementById("modify-form");
//	modifyFormId.style.display='block';		
//}



//댓글 작성 시
function openCommentWritingModal(movieNm, movieId){
	console.log("여기 ----온다");
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForWritingComment');
	let madalTitle = document.getElementById('modalMovieTitleForWriting');
	let hiddenMovieId = document.getElementById('modalMovieIdForWritingComment');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
	madalTitle.textContent = movieNm;
	hiddenMovieId.value=movieId;
	//$ajax
}

function closeModalForWriting(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForWritingComment');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}

function saveMovieComment(){
	let newComment = document.getElementById('movieCommentForWriting').value;
	let movieTitle = document.getElementById('modalMovieTitleForWriting').textContent;
	let movieId = document.getElementById('modalMovieIdForWritingComment').value;
	
	$.ajax({
		type: "POST",
		url: "/saveMovieCommentFromMovieInfo",
		data: {
			movieId: movieId,
			reviewComment: newComment
		},
		success: function (response){
			let msg = response.msg;
			alert(msg);
			location.href="/movieInfo?movieId="+movieId;
		},
		error: function (error){
			console.error("Error occured.");
		}
	});
	closeModalForWriting();
}

//댓글 수정용
function openModifyModal(movieNm, movieId, comment){
	console.log("여기 온다");
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForModifyComment');
	let madalTitle = document.getElementById('modalMovieTitleForModify');
	let textBox = document.getElementById('movieCommentForModify');
	let hiddenMovieId = document.getElementById('modalMovieIdForModify');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
	madalTitle.textContent = movieNm;
	textBox.innerHTML = comment;
	hiddenMovieId.value=movieId;
	//$ajax
}

function closeModalForModify(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modalForModifyComment');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}

function updateMovieComment(){
	let newComment = document.getElementById('movieCommentForModify').value;
	let movieTitle = document.getElementById('modalMovieTitleForModify').textContent;
	let movieId = document.getElementById('modalMovieIdForModify').value;
	
	$.ajax({
		type: "POST",
		url: "/updateMovieCommentFromMyCommentList",
		data: {
			movieId: movieId,
			reviewComment: newComment
		},
		success: function (response){
			let msg = response.msg;
			alert(msg);
			location.href="/movieInfo?movieId="+movieId;
		},
		error: function (error){
			console.error("Error occured.");
		}
	});
	closeModal();
}







// 코멘트 좋아요 -- (movie comment detail) START

function increaseLikeCount(userEmail, movieId, cnt) { // 작성자 이메일
	console.log("함수로 넘어옴.")
	console.log("userEmail ",userEmail);
	console.log("movieId ",movieId);
	console.log("cnt ",cnt);
	let num = Number(cnt);
	let comment = document.querySelectorAll(".comment");
	let count = document.querySelectorAll(".comment_like_count");	
	let data = {userEmail, movieId};
	let msg = "";
	let countMsg = "";
	$.ajax({
		url: '/increaseLikeCount', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {//response : {result: 1}
		console.log(response.result);
		msg += "<img class='likeButton' src='/resources/img/likeColor.png' style='cursor:pointer; width:25px;' onclick='decreaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>";
			comment[num].innerHTML=msg;
			countMsg += response.result;
			count[num].innerHTML=countMsg;
		},
		error: () => {
			console.log(error);
		}
	});			

    console.log("바깥-------------------댓글의 좋아요 수를 1 증가시킵니다.");
}

function decreaseLikeCount(userEmail, movieId, cnt) {
	let num = Number(cnt);
	let comment = document.querySelectorAll(".comment");
	let count = document.querySelectorAll(".comment_like_count");	
	let data = {userEmail, movieId};
	let msg = "";
	let countMsg = "";

	$.ajax({
		url: '/decreaseLikeCount', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
			msg += "<img class='likeButton' src='resources/img/like.png' style='cursor:pointer; width:25px;' onclick='increaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>";
			comment[num].innerHTML=msg;
		countMsg += response.result;
			count[num].innerHTML=countMsg;
		},
		error: () => {
			console.log(error);
		}
	});
    console.log("바깥-------------------댓글의 좋아요 수를 1 감소시킵니다.");
}
// 코멘트 좋아요 END

//코멘트 detail START-----------------------------------------------------------
function openModalForCommentDetail(name, comment, date){
	console.log("comment : ",comment);
	console.log("name : ",name);
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal-for-comment-detail');
	
	let userNameArea = document.getElementById('user-name-for-comment-detail');
	let commentArea = document.getElementById('comment-detail-area');
	
	modal.style.display = 'block';
	body.style.display = 'block';
	modal.style.zIndex = '2';
	body.style.zIndex = '1';
	
	userNameArea.innerText = name;
	commentArea.innerText = comment;
}

function closeModalForCommentDetail(){
	let body = document.getElementById('bodyForShadow');
	let modal = document.getElementById('modal-for-comment-detail');
	modal.style.display = 'none';
	body.style.display = 'none';
	modal.style.zIndex = '-2';
	body.style.zIndex = '-2';
}











