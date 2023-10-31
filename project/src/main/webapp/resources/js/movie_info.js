let postersLeftBtnId = document.getElementById("postersLeftBtn");

let postersRightBtnId = document.getElementById("postersRightBtn");

let postersClass = document.querySelectorAll(".posters");

let postersPageNum = 1;

let postersCnt = 0;

// LIKE Method START

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
                msg += "<img style='width:16px;' src='/resources/img/likeColor.png'> <span style='padding-left:7px;'>좋아요 "+likeNum+"명이 이 인물을 좋아합니다.</span>";
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
                msg += "<img style='width:16px;' src='/resources/img/like.png'> <span style='padding-left:7px;'>좋아요 "+likeNum+"명이 이 인물을 좋아합니다.</span>";
                msg += "</div>";
                likeDiv.innerHTML=msg;
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}
// LIKE Method END

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
			
			scoreMsg="<span><b> ★ "+ dto.reviewScore+"</b> / 5.0 </span><br>";
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
		msg += "<button class='likeButton' onclick='decreaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>♥</button>";
			comment[num].innerHTML=msg;
		countMsg +=  "♥ x "+response.result;
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
		msg += "<button class='likeButton' onclick='increaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>♡</button>";
			comment[num].innerHTML=msg;
		countMsg +=  "♥ x "+response.result;
			count[num].innerHTML=countMsg;
		},
		error: () => {
			console.log(error);
		}
	});
    console.log("바깥-------------------댓글의 좋아요 수를 1 감소시킵니다.");
}

// 코멘트 좋아요 END