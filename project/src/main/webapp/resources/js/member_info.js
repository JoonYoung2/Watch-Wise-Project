//--------------------------------------member info 페이지-----------------------------------------

function moveToLikedMovieList(){
	let path = "/userLikedMovieList";
	window.location.href = path;
}

function moveToLikedActorList(){
	let path = "/userLikedActorList";
	window.location.href = path;
}

function moveToLikedCommentList(){
	let path = "/userLikedCommentList";
	window.location.href = path;
}

//모달
var openModalButton = document.getElementById('openModalButton');
var closeModalButton = document.getElementById('closeModalButton');
var modal = document.getElementById('myModal');
var body = document.getElementById('bodyForShadow');
var headerBody = document.getElementById('header-body');


openModalButton.addEventListener('click', function() {
    modal.style.display = 'block';
    body.style.display = 'block';
 	modal.style.zIndex = '2';
 	body.style.zIndex = '1';
});

closeModalButton.addEventListener('click', function() {
    modal.style.display = 'none';
    body.style.display = 'none';
    modal.style.zIndex = '-2';
    body.style.zIndex = '-2';
});



//--------------------------------------좋아요한 코멘트 리스트-----------------------------------------

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
			msg += "<img class='likeButton' src='resources/img/likeColor.png' style='width:30px; margin-left:15px;' onclick='decreaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>";		
			comment[num].innerHTML = msg;
			countMsg += response.result;
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
			msg += "<img class='likeButton' src='resources/img/like.png' style='width:30px;margin-left:15px;' onclick='increaseLikeCount(\"" + userEmail + "\", \"" + movieId + "\", " + cnt + ");'>";
			comment[num].innerHTML = msg;
			countMsg += response.result;
			count[num].innerHTML = countMsg;
		},
		error: () => {
			console.log(error);
		}
	});
	console.log("바깥-------------------댓글의 좋아요 수를 1 감소시킵니다.");
}


//--------------------------------------좋아요한 영화 리스트-----------------------------------------

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

//--------------------------------------좋아요한 배우 리스트-----------------------------------------

function moveToActorInfo(peopleId) {
	let path = "peopleInfo?peopleId=" + peopleId;
	window.location.href = path;
}

function actorLikeCancel(peopleId, cnt){
	let num = Number(cnt);
	let likeActor = document.querySelectorAll(".liked-actor-list-heart");
	let msg="";
	$.ajax({
		url: "/peopleLikeCancel",
		method: "GET",
		data: {
			peopleId
		},
		success: (response) =>{
			msg +="<img src='/resources/img/like.png' onclick='actorLikeAdd(" + peopleId + ", " + cnt + ");' style='cursor:pointer; width:20px; position:absolute; bottom:20px; right:60px;'>";
			likeActor[num].innerHTML = msg;
		},
		error: ()=>{
			console.log(error);
		}
	});
	console.log("바깥-------------------배우 좋아요 수를 1 감소시킵니다.");
}

function actorLikeAdd(peopleId, cnt){
	let num = Number(cnt);
	let likeActor = document.querySelectorAll(".liked-actor-list-heart");
	let msg="";
	$.ajax({
		url: "/peopleLikeAdd",
		method: "GET",
		data: {
			peopleId
		},
		success: (response) =>{
			msg += "<img src='/resources/img/likeColor.png' onclick='actorLikeCancel(" + peopleId + ", " + cnt + ");' style='cursor:pointer; width:20px; position:absolute; bottom:20px; right:60px;'>";
			likeActor[num].innerHTML = msg;
		},
		error: ()=>{
			console.log(error);
		}
	});
	console.log("바깥-------------------배우 좋아요 수를 1 증가시킵니다.");
}