// Paging Method START

let memberCommendLeftBtnId = document.getElementById("memberCommendLeftBtn");
let searchMoviesLeftBtnId = document.getElementById("searchMoviesLeftBtn");

let memberCommendRightBtnId = document.getElementById("memberCommendRightBtn");
let searchMoviesRightBtnId = document.getElementById("searchMoviesRightBtn");

let memberCommendClass = document.querySelectorAll(".memberCommend");
let searchMoviesClass = document.querySelectorAll(".searchMovies");

let memberCommendPageNum = 1;
let searchMoviesPageNum = 1;

let memberCommendCnt = 0;
let searchMoviesCnt = 0;

const memberCommendLeftBtn = () => {
    memberCommendPageNum--;
    if(memberCommendPageNum == 1){
        memberCommendLeftBtnId.style.display = "none";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }else{
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }
}

const memberCommendRightBtn = (cnt) => {
    memberCommendCnt = Number(cnt);
    let pageNum = Math.ceil(memberCommendCnt / 5);
    memberCommendPageNum++;
    if(memberCommendPageNum == pageNum){
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "none";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }else{
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }
}

const searchMoviesLeftBtn = () => {
    searchMoviesPageNum--;
    if(searchMoviesPageNum == 1){
        searchMoviesLeftBtnId.style.display = "none";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }
}

const searchMoviesRightBtn = (cnt) => {
    searchMoviesCnt = Number(cnt);
    let pageNum = Math.ceil(searchMoviesCnt / 5);
    searchMoviesPageNum++;
    if(searchMoviesPageNum == pageNum){
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "none";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }
}

// Paging Method END