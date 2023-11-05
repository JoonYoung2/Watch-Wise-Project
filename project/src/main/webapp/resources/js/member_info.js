function increaseLikeCount(userEmail, movieId, cnt) {
	let num = Number(cnt);
	let comment = document.querySelectorAll(".comment");
	let count = document.querySelectorAll(".comment_like_count");
	let data = { userEmail, movieId };
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
			comment[num].innerHTML = msg;
			countMsg += "♥ x " + response.result;
			count[num].innerHTML = countMsg;
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
	let data = { userEmail, movieId };
	let msg = "";
	let countMsg = "";

	$.ajax({
		url: '/decreaseLikeCount', // 서버의 엔드포인트 URL
		type: 'POST', // HTTP 요청 메서드 (GET 또는 POST 중 선택)
		data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환
		contentType: 'application/json', // 데이터 타입 설정
		success: (response) => {
			msg += "<button class='likeButton' onclick='increaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>♡</button>";
			comment[num].innerHTML = msg;
			countMsg += "♥ x " + response.result;
			count[num].innerHTML = countMsg;
		},
		error: () => {
			console.log(error);
		}
	});
	console.log("바깥-------------------댓글의 좋아요 수를 1 감소시킵니다.");
}

function moveToMovieInfo(movieId) {
	let path = "/movieInfo?movieId=" + movieId;
	window.location.href = path;
}

function likeAdd(movieId, cnt) {
	let num = Number(cnt);
	let likeSpan = document.querySelectorAll(".liked-movie-list-heart");
	let msg = "";
	$.ajax({
		url: "/movieLikeAdd",
		method: "GET",
		data: {
			movieId
		},
		success: (response) => {
			msg += "<img style='width:40px;' src='/resources/img/likeColor.png' onclick='likeCancel(" + movieId + ", " + cnt + ")'>";
			likeSpan[num].innerHTML = msg;
		},
		error: () => {
			console.log(error);
		}
	});
	console.log("바깥-------------------영화 좋아요 수를 1 증가시킵니다.");

}

function likeCancel(movieId, cnt) {
	let num = Number(cnt);
	let likeSpan = document.querySelectorAll(".liked-movie-list-heart");
	let msg = "";
	$.ajax({
		url: "/movieLikeCancel",
		method: "GET",
		data: {
			movieId
		},
		success: (response) => {
			msg += "<img style='width:40px;' src='/resources/img/like.png' onclick='likeAdd(" + movieId + ", " + cnt + ");'>"
			likeSpan[num].innerHTML = msg;
		},
		error: () => {
			console.log(error);
		}
	});
	console.log("바깥-------------------영화 좋아요 수를 1 감소시킵니다.");

}