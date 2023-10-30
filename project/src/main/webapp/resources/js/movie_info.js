let postersLeftBtnId = document.getElementById("postersLeftBtn");

let postersRightBtnId = document.getElementById("postersRightBtn");

let postersClass = document.querySelectorAll(".posters");

let postersPageNum = 1;

let postersCnt = 0;

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

function rating(score, movieId) {  //평점
	let selectedScore = Number(score);
	let msgId = document.getElementById("msg");
	let data = {reviewScore : selectedScore, movieId};
	$.ajax({
		url: '/getReivewScore', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
			console.log(response);
			msgId.innerHTML = response.msg;
		},
		error: () => {
			console.log(error);
		}
	});
}

function showModifyForm(){  //코멘트
	console.log("gdgd");
	let modifyFormId = document.getElementById("modify-form");
	modifyFormId.style.display='block';		
}

// 코멘트 좋아요 START

function increaseLikeCount(userEmail, movieId, cnt) {
	let num = Number(cnt);
	let comment = document.querySelectorAll(".comment");	
	let data = {userEmail, movieId};
	let msg = "";
	$.ajax({
		url: '/increaseLikeCount', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
		msg += "<button class='likeButton' onclick='decreaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>좋아요 취소</button>";
			comment[num].innerHTML=msg;
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
	let data = {userEmail, movieId};
	let msg = "";
	$.ajax({
		url: '/decreaseLikeCount', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
        contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
		msg += "<button class='likeButton' onclick='increaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>좋아요</button>";
			comment[num].innerHTML=msg;
		},
		error: () => {
			console.log(error);
		}
	});
    console.log("바깥-------------------댓글의 좋아요 수를 1 감소시킵니다.");
}

// 코멘트 좋아요 END