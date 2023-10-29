// Paging Method START

let movieInfoLeftBtnId = document.getElementById("movieInfoLeftBtn");

let movieInfoRightBtnId = document.getElementById("movieInfoRightBtn");

let movieInfoClass = document.querySelectorAll(".movieInfo");

let movieInfoPageNum = 1;

let movieInfoCnt = 0;

const movieInfoLeftBtn = () => {
    movieInfoPageNum--;
    if(movieInfoPageNum == 1){
        movieInfoLeftBtnId.style.display = "none";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }else{
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }
}

const movieInfoRightBtn = (cnt) => {
    movieInfoCnt = Number(cnt);
    let pageNum = Math.ceil(movieInfoCnt / 5);
    movieInfoPageNum++;
    if(movieInfoPageNum == pageNum){
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "none";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }else{
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }
}

// Paging Method END

// LIKE Method START

const unregisterLickClick = () => {
	alert("로그인 후 좋아요를 할 수 있습니다.");
	return;
}

const likeAdd = async (peopleId) => {
    const likeDiv = document.getElementById("likeDiv");
    let msg = "";
    $.ajax({
        url: "http://localhost:8080/peopleLikeAdd",
        method: "GET",
        data: {
            peopleId
        },
        success: function (response) {
            var likeNum = response.likeNum;

                msg += "<div style='display:flex; justify-content:center; align-items:center;' class='likeCancel' onclick='likeCancel("+peopleId+");'>";
                msg += "<img style='width:16px;' src='/resources/img/likeColor.png'> <span style='padding-left:7px;'>좋아요 "+likeNum+"명이 이 인물을 좋아합니다.</span>";
                msg += "</div>";
                likeDiv.innerHTML=msg;
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}

const likeCancel = async (peopleId) => {
    const likeDiv = document.getElementById("likeDiv");
    let msg = "";
    $.ajax({
        url: "http://localhost:8080/peopleLikeCancel",
        method: "GET",
        data: {
            peopleId
        },
        success: function (response) {
            var likeNum = response.likeNum;

            console.log("서버 응답 - likeNum: " + likeNum);
                msg += "<div style='display:flex; justify-content:center; align-items:center;' class='likeAdd' onclick='likeAdd("+peopleId+");'>";
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